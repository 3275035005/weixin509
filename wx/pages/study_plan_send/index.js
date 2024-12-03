var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
var userId = '';

Page({

    /**
     * 页面的初始数据
     */
    data: {
      title: '',
      completeWord: '',
      typeIndex:0,
      startDate:'请选择计划开始日期',
      typeList:[
        {id: '0', title:'请选择计划类型'},{id: '1', title:'周计划'},{id: '2', title:'日计划'}
      ],
      lexiconList:[],
      lexiconIndex:0
    },


    onLoad: function () {
        this.getInit();
        this.getLexiconAll()
    },

    /**
     * 获取登录用户信息
     */
    getInit() {
        userId = wx.getStorageSync("token")
        // 用户信息不存在跳转登录页面
        if (userId == null || userId == undefined || userId == '') {
            // 跳转到登录页面
            wx.reLaunch({
                url: '/pages/login/index'
            })
        }
        
    },
    getLexiconAll(){
      call.getData('wx/getLexiconAll', this.onSuccessLexiconAll, this.onFail);
    },

    onSuccessLexiconAll(res){
      let test = [{id:0, title: '请选择题库'}]
      this.setData({
        lexiconList: [...test, ...res.data.list]
      })
    },

    updateBtn() {
        if (this.data.title == "") {
            wx.showToast({
                title: '请输入计划名称',
                icon: 'none',
                duration: 1000
            })
            return false;
        }
       
        
        if (this.data.completeWord == "" || this.data.completeWord == 0) {
            wx.showToast({
                title: '请输入计划完成单词量',
                icon: 'none',
                duration: 1000
            })
            return false;
        }
        if (this.data.typeIndex == 0) {
            wx.showToast({
                title: '请选择计划类型',
                icon: 'none',
                duration: 1000
            })
            return false;
        }

        if (this.data.lexiconIndex == 0) {
          wx.showToast({
              title: '请选择题库',
              icon: 'none',
              duration: 1000
          })
          return false;
      }
      if (this.data.startDate=='请选择计划开始日期') {
        wx.showToast({
            title: '请选择计划开始日期',
            icon: 'none',
            duration: 1000
        })
        return false;
    }
        wx.showLoading({
            title: '发起中...'
        });
        call.request('wx/sendStudyPlan', {
             title: this.data.title,
             completeWord: this.data.completeWord,
             type: this.data.typeList[this.data.typeIndex].id,
             lexiconId: this.data.lexiconList[this.data.lexiconIndex].id,
             startDate: this.data.startDate,
             userId: userId
        }, this.onSuccess, this.onFail);
    },

    onSuccess(res) {
        wx.hideLoading();
        if (res.code == 20000) {
            help.show('创建成功')
            setTimeout(function () {
                wx.reLaunch({
                    url: '/pages/study_plan/index'
                })
            }, 2000)
        } else {
            help.show(res.message)
        }
    },

    onFail() {
        wx.hideLoading();
        help.show("网络请求超时,请稍后再试")
    },

    // 计划类型筛选
    typeChange(e){
      this.setData({
        typeIndex : e.detail.value
      })
    },

     // 题库筛选
     lexiconChange(e){
      this.setData({
        lexiconIndex : e.detail.value
      })
    },
	// 开始时间
	startDateChange(e){
		this.setData({
			startDate : e.detail.value
		})
  },
})