
//获取应用实例

const app = getApp()
var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
Page({
  data: {
    list: []
  },
  
  onLoad() {
    this.getInit();
  },
  getInit() {
    call.getData('wx/getLexiconList' , this.onSuccessAll, this.onFaiAll);
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

  goToResult(event){
    const { id } = event.currentTarget.dataset;
    wx.navigateTo({
      url: '../lexicon_detail/index?id=' + id
    })
  },
})
