var call = require("../../utils/request.js")
var help = require("../../utils/help.js")

Page({
    data: {
        name:'',// 姓名
        phone:'', // 手机号
        username:'', // 账号
        password:'', // 密码
    },
    // 注册
    register_button(){
      if(this.data.phone == ""){
        wx.showToast({
          title: '手机号不能为空',
          icon: 'none',
          duration: 1000
        })
        return false;
      }
      if(this.data.name == ""){
      wx.showToast({
          title: '姓名不能为空',
          icon: 'none',
          duration: 1000
      })
      return false;
      }
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
        title: '注册中...'
      });
      call.request('wx/register',{
        name: this.data.name,
        phone: this.data.phone,
        username:this.data.username,
        password:this.data.password
      }, this.onSuccess, this.onFail);
    },

    onSuccess(res) {
    
      wx.hideLoading();
      if(res.code == 20000){
      help.show("注册成功")
      setTimeout(function(){ // 下单成功跳转页面
        wx.reLaunch({
          url: '/pages/login/index'
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
    
})