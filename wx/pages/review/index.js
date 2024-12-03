
//获取应用实例

const app = getApp()
var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
var userId = ''
Page({
  data: {
    list: [],
    id:'',
    index: 0
  },
  
  onLoad() {
    this.getInit();
  },
  getInit() {
    userId = wx.getStorageSync("token")
    // 用户信息不存在跳转登录页面
    if (userId == null || userId == undefined || userId == '') {
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
      })
    }
    call.getData('wx/getReviewByUserId/'+userId, this.onSuccessAll, this.onFaiAll);
  },

  onSuccessAll(res) {
    if (res.code == 20000) {
      this.setData({
        list: res.data.list
      })
    }
  },
  onFaiAll() {
    help.show("网络请求失败");
  },
  // 跳转单词
  selectWordName(){
    wx.navigateTo({
      url: '../word_info/index?id=' + this.data.list[this.data.index].wordName
    })
  },
  no(){ // 不认识
  
    call.request('wx/noWord',{
      lexiconInfoId: this.data.list[this.data.index].lexiconInfoId,
      userId: userId
    }, this.onSuccess1All, this.onFaiAll);
  },
  yes(){ // 认识
    call.request('wx/yesWord',{
      lexiconInfoId: this.data.list[this.data.index].lexiconInfoId,
      userId: userId
    }, this.onSuccess1All, this.onFaiAll);
  },

  onSuccess1All(res) {
    if(this.data.list.length === (this.data.index + 1)){
      help.show("已经是最后一页了");
    }else{
      this.setData({
        index: this.data.index + 1
      })
    }
  },
    // 上一页
    prevImg() {
      if(0 === this.data.index){
        help.show("已经是第一页了");
      }else{
        this.setData({
          index: this.data.index - 1
        })
      }
    },
    nextImg(){
      if(this.data.list.length === (this.data.index + 1)){
        help.show("已经是最后一页了");
      }else{
        this.setData({
          index: this.data.index + 1
        })
      }
    },
})
