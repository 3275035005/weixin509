// 引入 echarts 文件
import * as echarts from '../../ec-canvas/echarts';

let rowRows = {
  yesData: [0, 0, 0, 0, 0, 0, 0, 0],
  noData: [0, 0, 0, 0, 0, 0, 0, 0]
};
// 定义 initChart 方法
// initChart 需要传递四个参数 canvas, width, height, dpr
function initChart(canvas, width, height, dpr) {
  // 使用引入的 echarts的init方法对 chart 变量赋值进行初始化
  const chart = echarts.init(canvas, null, {
    width: width,
    height: height,
    devicePixelRatio: dpr // 像素
  });

  canvas.setChart(chart);

  var option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      x: 'center',
      y: 'bottom',
      data: ['认识', '不认识']
    },
    xAxis: {
      type: 'category',
      data: ['7天前', '6天前', '5天前', '4天前', '3天前', '前天', '昨天', '今天'],
      axisLabel: {
         interval: 0 ,// 强制显示所有轴标签
         formatter: function(value) {  //文字竖排
          return value.split('').join('\n')
         },
      },
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '认识',
        type: 'bar',
        stack: '总量',
        data: rowRows.yesData
      },
      {
        name: '不认识',
        type: 'bar',
        stack: '总量',
        data: rowRows.noData
      }
    ]
  }

  chart.setOption(option);

  return chart;
}

var call = require("../../utils/request.js")
var help = require("../../utils/help.js")
Page({

    /**
     * 页面的初始数据
     */
    data: {
      ec: {
        // 使用 onInit 方法定义
        onInit: initChart
      },
      item:[],
      current: 0,
      cash_tab: ['我的学习计划', '学习计划统计'],
      
    },

    onLoad: function () {
      this.getKnowledgeList();
    },
  
    // 查询所有课程类别
    getKnowledgeList() {
      let userId = wx.getStorageSync("token")
      // 用户信息不存在跳转登录页面
      if (userId == null || userId == undefined || userId == '') {
        // 跳转到登录页面
        wx.reLaunch({
          url: '/pages/login/index'
        })
      }
      call.getData('wx/getStudyPlanByUserId/' + userId, this.onSuccessKnowledgeAll, this.onFaiKnowledgeAll);
      call.getData('wx/getCurve/' + userId, this.onSuccessCounselorAll, this.onFaiKnowledgeAll);
    },
  
    onSuccessKnowledgeAll(res) {
      if (res.code == 20000) {
        this.setData({
          item: res.data.list
        })
      }
    },
    onSuccessCounselorAll(res) {
      if (res.code == 20000) {
        rowRows = {
          yesData: res.data.row.yesData,
          noData: res.data.row.noData,
        }
        this.setData({
          ec: {
            // 使用 onInit 方法定义
            onInit: initChart
          }
        })
      }
    },
    onFaiKnowledgeAll() {
      help.show("网络请求失败");
    },

    DevTuchCall:function(e){
      wx.navigateTo({
          url: '/pages/study_plan_send/index'
      })
  },

    // cash_tab__change
    cash_tab__change(e) {
      this.setData({
        current: e.target.dataset.index
      })
    }
})