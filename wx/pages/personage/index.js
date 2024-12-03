var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
var user= ''
Page({
    data: {
      id:'', 
      phone:'', // 手机号
      username:'', // 账号
      password:'', // 密码
      name:''
    },
    onLoad() {
      this.getInit();

    },
  /**
   * 获取登录用户信息
   */
  getInit(){
    user = wx.getStorageSync("token")
     // 用户信息不存在跳转登录页面
     if(user == null || user == undefined || user == ''){
      // 跳转到登录页面
      wx.reLaunch({
        url: '/pages/login/index'
     })
  }
  this.getUserInfo(user);
},

  getUserInfo(userId){
    call.getData('wx/getUserInfo?id=' + userId, this.onSuccessclassAll, this.onFaiclassAll);
  },
  onSuccessclassAll(res) {
    if (res.code == 20000) {
      this.setData({
        id:res.data.row.id,
        phone:res.data.row.phone,
        username:res.data.row.username,
        name:res.data.row.name,
      });
    }
  },
  onFaiclassAll() {
    help.show("网络请求失败");
  },


    // 修改
    sub(){
      wx.showLoading({
        title: '修改中...'
      });
      call.request('wx/updateUserInfo',{
        id: this.data.id,
        phone: this.data.phone,
        password:this.data.password,
        name:this.data.name,
      }, this.onSuccess, this.onFail);
    },

    onSuccess(res) {
    
      wx.hideLoading();
      if(res.code == 20000){
      help.show("修改成功")
      setTimeout(function(){
        wx.reLaunch({
          url: "/pages/myself/myself"
      })
      },2000)
         
      }else{
          help.show(res.msg)
      }
    },
    onFail() {
      wx.hideLoading();
      help.show("网络请求超时,请稍后再试")
    },
    
    //获取input输入框的值
    getPasswordValue:function(e){
      this.setData({
        password:e.detail.value
      })
    },
    getNameValue:function(e){
      this.setData({
        name:e.detail.value
      })
    },
    getPhoneValue:function(e){
        this.setData({
            phone:e.detail.value 
        })
    },
    getUsernameValue:function(e){
      this.setData({
        username:e.detail.value 
      })
  },

  getName:function(e){
    this.setData({
      name:e.detail.value 
    })
},
    
})