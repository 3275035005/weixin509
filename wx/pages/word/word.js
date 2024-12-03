// pages/guanlijintie/guanlijintie.js
var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
let userId = ''
Page({

  /**
   * 页面的初始数据
   */
  data: {

    // 存放数据
    wordData: null,
    shoopingtext: '',
    audioContext: null,
    isPlayUk: true,
    userId:''
  },

  onLoad: function () {
    let userId = wx.getStorageSync("token");
    // 用户信息不存在跳转登录页面
    if (userId == null || userId == undefined || userId == '') {
      wx.reLaunch({
        url: '/pages/login/index'
      })
    }
    this.setData({
      shoopingtext: '',
      userId: userId
    })

    // 创建音频上下文
    this.setData({
      audioContext: wx.createInnerAudioContext()
    });
  },


  // 播放英式：
  audioPauseUk() {
    // 设置音频源
    this.data.audioContext.src = this.data.wordData.voice
    // 播放音乐
    this.data.audioContext.play();
    // 更新播放状态
    this.setData({
      isPlayUk: false
    });
    let that = this;
    setTimeout(function () {
      that.setData({
        isPlayUk: true
      });
    }, 2000)
  },
  search() {
    if (this.data.shoopingtext == null || this.data.shoopingtext == '' || this.data.shoopingtext == undefined) {
      help.show("请输入查询的单词")
    } else {
      this.initData();
    }

  },
  //1: 加载数据
  initData: function () {
    wx.showLoading({
      title: '数据加载中...'
    });
    call.getData('wx/translate?wordName=' + this.data.shoopingtext+'&userId='+this.data.userId, this.onSuccess, this.onFail);
  },
  onSuccess(res) {
    wx.hideLoading();
    let that = this;
    if (res.code == 20000) {
      that.setData({
        wordData: res.data.data
      })
    }else{
      help.show(res.message)
    }
  
  },
  onFail() {
    wx.hideLoading();
    help.show("网络请求超时,请稍后再试")
  },

  //1: 加载数据
  addVocabulary: function () {
    wx.showLoading({
      title: '数据加载中...'
    });
    call.request('wx/sendVocabulary', {
      userId: this.data.userId,
      wordName: this.data.shoopingtext
    }, this.onAddVocabularySuccess, this.onAddVocabularyFail);
  },
  onAddVocabularySuccess(res) {
    this.initData();
    wx.hideLoading();
  },
  onAddVocabularyFail() {
    wx.hideLoading();
    help.show("网络请求超时,请稍后再试")
  },

  //1: 加载数据
  delVocabulary: function () {
    wx.showLoading({
      title: '数据加载中...'
    });
    call.getData('wx/cancelVocabulary/' + this.data.wordData.vocabularyId, this.onDelVocabularySuccess, this.onDelVocabularyFail);
  },
  onDelVocabularySuccess(res) {
    this.initData();
    wx.hideLoading();
  },
  onDelVocabularyFail() {
    wx.hideLoading();
    help.show("网络请求超时,请稍后再试")
  },
  shoppinginput: function (e) {
    this.setData({
      shoopingtext: e.detail.value
    })
  }
})
