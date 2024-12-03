var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
let userId = ''
let magazineId = ''
Page({

  /**
   * 页面的初始数据
   */
  data: {

    dataVo: {},
    content: ''
  },

  onLoad: function (options) {
    const { id } = options;
    magazineId = id;
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
    this.getMagazineById();
  },
  getMagazineById() {
    call.getData('wx/getMagazineById/' + magazineId + '/' + userId, this.onSuccessclassAll, this.onFaiclassAll);
  },
  onSuccessclassAll(res) {
    if (res.code == 20000) {
      this.setData({
        dataVo: res.data.list,
        content: help.formatRichText(res.data.list.content)
      })
    }
  },
  onFaiclassAll() {
    help.show("网络请求失败");
  },


  // 订阅外刊
  isSubscription() {
    call.request('wx/subscriptionMagazine',{
      magazineId: this.data.dataVo.id,
      userId: userId
    }, this.onIsSubscriptionSuccess, this.onIsSubscriptionFail);
  },
  onIsSubscriptionSuccess(res) {
    help.show('订阅成功')
    this.getMagazineById();
  },

  onIsSubscriptionFail() {
    help.show("网络请求超时,请稍后再试")
  },



  // 取消订阅外刊
  noSubscription() {
    call.getData('wx/cancelSubscriptionMagazine/' + this.data.dataVo.subscriptionId, this.onNoSubscriptionSuccess, this.onNoSubscriptionFail);
  },
  onNoSubscriptionSuccess(res) {
    help.show('取消订阅成功')
    this.getMagazineById();
  },

  onNoSubscriptionFail() {
    help.show("网络请求超时,请稍后再试")
  },

})