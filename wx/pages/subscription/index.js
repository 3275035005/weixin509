var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
let userId = ''
Page({

  data: {
    list: [],
    current: 0,
    classifiedList: [
      {
        id: 0,
        title: '全部'
      }
    ],
  },
  onLoad: function () {
    this.getInit();
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
    this.getCategoryList();
    this.getSubscriptionMagazineByUserId ('');
  },

  // 根据分类查询我的外刊
  getSubscriptionMagazineByUserId(id) {
    call.getData('wx/getSubscriptionMagazineByUserId?userId=' + userId+"&classifiedId="+id, this.onSuccessCourseAll, this.onFaiCourseAll);
  },

  onSuccessCourseAll(res) {
    if (res.code == 20000) {
      this.setData({
        list: res.data.list
      })
    }
  },

  onFaiCourseAll() {
    help.show("网络请求失败");
  },


  // 查询所有外刊分类
  getCategoryList() {
    call.getData('wx/getClassifiedAll', this.onSuccessCategoryAll, this.onFaiCategoryAll);
  },
  onSuccessCategoryAll(res) {
    if (res.code == 20000) {
      this.setData({
        classifiedList: this.data.classifiedList.concat(res.data.row)
      })
    }
  },
  onFaiCategoryAll() {
    help.show("网络请求失败");
  },

  cash_tab__change(e) {
    let size = e.target.dataset.index

    this.setData({
      current: size
    })
    if (size == 0) {
      this.getSubscriptionMagazineByUserId('');
    } else {
      this.getSubscriptionMagazineByUserId(this.data.classifiedList[size].id);
    }

  }

})