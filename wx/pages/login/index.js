var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
Page({

    /**
     * 页面的初始数据
     */
    data: {
        username:'',     //手机号
        password:'',  // 密码
    },


  
    // 用户名登录 - 提交表单信息
    login_Username_Button(){
      if(this.data.username == ""){
      wx.showToast({
          title: '账号不能为空',
          icon: 'none',
          duration: 1000
      })
      return false;
      }
      if(this.data.password == ""){
        wx.showToast({
          title: '密码不能为空',
          icon: 'none',
          duration: 1000
        })
        return false;
          }
          wx.showLoading({
            title: '正在登录...'
          });
          call.request('wx/login',{
              username:this.data.username,
              password:this.data.password
          }, this.onSuccess, this.onFail);
    },

    onSuccess(res) {
      console.log(res)
      wx.hideLoading();
      if(res.code == 20000){
          wx.setStorageSync('token', res.data.token)
          wx.reLaunch({
              url: '/pages/home/index'
          })
      }else{
          help.show(res.message)
      }
    },

    onFail() {
      wx.hideLoading();
      help.show("网络请求超时,请稍后再试")
    },

    // 跳转注册页面
    login_register(){
      wx.navigateTo({
        url: '/pages/register/index'
      })
    },
    //获取input输入框的值
    getPasswordValue:function(e){
      this.setData({
        password:e.detail.value
      })
  },
  getUsernameValue:function(e){
        this.setData({
            username:e.detail.value 
        })
    },
  

})