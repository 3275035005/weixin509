
var call = require("../../utils/request")
var help = require("../../utils/help")
var token =''
Page({

  data: {
    tabList: [{
      name: '当前未回复',
      status: '0'
    }, {
      name: '当前已回复',
      status: '1'
    }],
    applyData: [], // 申请记录
    status: 0,// 处理状态
    
  },


  /**
   * 最先执行
   */
  onLoad: function () {
    // 获取登录状态
    this.getInit();
    // 查询申请数据
    this.getApplyData();
  },

  /**
   * 获取登录用户信息
   */
  getInit(){
    token = wx.getStorageSync("token")
     // 用户信息不存在跳转登录页面
     if(token == null || token == undefined || token == ''){
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
     })
  }
  },

  /* 选择处理状态 */
  selectStatus(e) {
    this.setData({
      status : e.detail.status
    })
    this.getApplyData();
  },


  /* 查询数据 */
 getApplyData() {
    call.request(`wx/getFeedback`,{
      userId: token,
      status: this.data.status,
    } , this.onSuccessInit, this.onFailEquipmentAll);
  },
  onSuccessInit(res) {
    console.log(res)
    if(res.code == 20000){
      this.setData({
        applyData: res.data.list
      })
    }
  },

  /* 查看申报表详情 */
  navDetail(e) {
    wx.navigateTo({
      url: '/pages/feedback_detail/index?id=' + e.currentTarget.dataset.id
    }) 
  }

})