//pages/index/index.js
Page({
  data: {
  },

  
  /**
   * 最先执行
   */
  onLoad: function () {
    // this.getInit();
  },

    /**
   * 获取登录用户信息
   */
  getInit(){
    let user = wx.getStorageSync("token")
     // 用户信息不存在跳转登录页面
     if(user == null || user == undefined || user == ''){
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
     })
  }
  },

  //词典搜索
  home_bingtap_image1:function() {
    wx.navigateTo({
      url: '/pages/word/word',
    })
  },
  //我的生词本
  home_bingtap_image2:function() {
    wx.navigateTo({
      url: '/pages/vocabulary_list/index',
    })
  },
  //外刊新闻
  home_bingtap_image3:function() {
    wx.navigateTo({
      url: '/pages/magazine_classified_list/index',
    })
  },
   home_bingtap_image4:function() {
    wx.navigateTo({
      url: '/pages/lexicon_list/index',
    })
  },
   home_bingtap_image5:function() {
    wx.navigateTo({
      url: '/pages/study_plan/index',
    })
  },
   home_bingtap_image6:function() {
    wx.navigateTo({
      url: '/pages/review/index',
    })
  }
})
