var call = require("../../utils/request")
var help = require("../../utils/help")
let userId = ''
Page({

  /**
   * 页面的初始数据
   */
  data: {

    // --- 用户信息 --- //
    userInfo: {},

    // --- 面板 --- //
    user_panel: [
      {
        url: "/pages/subscription/index",
        icon: "/images/user_panel_03.svg",
        title: "我的订阅外刊"
      }, {
        url: "/pages/feedback_list/index",
        icon: "/images/user_panel_07.svg",
        title: "我的意见反馈"
      }, {
        url: "/pages/personage/index",
        icon: "/images/user_panel_07.svg",
        title: "我的个人信息"
      }, {
        url: "/pages/feedback/feedback",
        icon: "/images/user_panel_03.svg",
        title: "意见反馈"
      }
    ],
    regDate: ''
  },

  onLoad: function () {
    this.getInit();
  },

  /**
* 获取登录用户信息
*/
  getInit() {
    userId = wx.getStorageSync("token")
    if (userId == null || userId == undefined || userId == '') {
      wx.reLaunch({
        url: '/pages/login/index'
      })
    } else {
      this.getInfo();
    }
  },

  getInfo() {
    call.getData('wx/getUserInfo?id=' + userId, this.onSuccessclassAll, this.onFaiclassAll);
  },
  onSuccessclassAll(res) {
    if (res.code == 20000) {
      this.setData({
        userInfo: res.data.row
      })
    }
  },
  onFaiclassAll() {
    help.show("网络请求失败");
  },

  // 退出登录
  out() {
    wx.showModal({
      title: "是否退出登录",
      success(res) {
        if (res.confirm) {
          wx.removeStorageSync('token')
          help.okShow("退出成功");
          setTimeout(function () {
            wx.navigateTo({
              url: '/pages/login/index',
            })
          }, 1000)
        }
      }

    })

  },


})