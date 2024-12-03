Component({
  properties: {
    tabList: { // tab数据列表
      type: Array,
      value: [{name: 'tab-1'}, {name: 'tab-2'}, {name: 'tab-3'}]
    },
    currentIndex: { // 当前索引
      type: Number,
      value: 0,
    },
    bgColor: { // 背景颜色
      type: String,
      value: 'white'
    },
    textColor: { // 文字颜色
      type: String,
      value: 'black'
    },
    activeColor: { // 选中颜色
      type: String,
      value: 'orange'
    },
    fontSize: { // 字体大小
      type: String,
      value: '28rpx',
    },
    textAlign: { // 对齐方式
      type: String,
      value: 'center',
    },
    lrPadding: { // tab-item 左右padding
      type: String,
      value: '40rpx',
    },
    fontWeight: { // 文字加粗
      type: String,
      value: ''
    },
    activeLineHeight: { // 底部活动线高度
      type: String,
      value: '4rpx'
    },
    activeLineWidth: { // 底部活动线宽度
      type: String,
      value: '40rpx'
    },
  },
  data: {
    scrollLeft: 0,
    currentIndex: 0,
  },
  methods: {
    selectTab(e) {
      const { index } = e.currentTarget.dataset;
      this.setData({
        currentIndex: index,
        scrollLeft: (index - 1) * 60
      })
      this.triggerEvent('select', {...this.data.tabList[index], index: index });
    }
  }
})