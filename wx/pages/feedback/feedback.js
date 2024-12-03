var call = require("../../utils/request")
var help = require("../../utils/help")
var token = '';
Page({
  data: {
    content:'', // 反馈内容
  },

  onLoad: function() {
     this.getInit();
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

  
  /* 反馈内容 */
  setContent(e) {
    this.setData({
      content: e.detail.value
    })
  },

  /* 提交申报表 */
  inApplyData() {

    wx.showLoading({
        title: '正在提交...',
        mask: true
      })

      call.request('wx/sendFeedback' , {
          content: this.data.content,
          userId: token
      }, this.onSuccessSubmit, this.onFailSubmit);
  },
  onSuccessSubmit(res) {
    if(res.code == 20000){
      // 关闭加载框
      wx.hideLoading();
      help.okShow("反馈成功");
      setTimeout(function () {
        // 跳转到首页
        wx.reLaunch({
          url: '../myself/myself',
        })
      }, 2000)
    }else{
      help.show("申请失败!");
    }
  },
  onFailSubmit() {
    help.show("网络请求失败");
  },

})