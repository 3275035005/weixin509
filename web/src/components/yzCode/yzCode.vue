<template>
  <!-- 自定义一个绘制随机验证码的组件，其本质是使用canvas绘制 -->
  <div class="s-canvas">
    <canvas id="s-canvas" :width="contentWidth" :height="contentHeight" ></canvas>
  </div>
</template>

<script>
export default {
  name: 'SIdentify',
  props: {
    identifyCode: {  // 需要展示的验证码
      type: String,
      default: '1234'
    },
    fontSizeMin: {  // 字体大小 最小值
      type: Number,
      default: 16
    },
    fontSizeMax: {	// 字体大小 最大值
      type: Number,
      default: 40
    },
    backgroundColorMin: { // 背景颜色色值 最小值 最小0
      type: Number,
      default: 180
    },
    backgroundColorMax: { // 背景颜色色值 最大值 最大255
      type: Number,
      default: 240
    },
    colorMin: {		// 字体颜色色值最小值 最小0
      type: Number,
      default: 50
    },
    colorMax: {    // 字体颜色色值最大值 最大255
      type: Number,
      default: 160
    },
    lineColorMin: {  // 干扰线颜色色值最小值 最小值0
      type: Number,
      default: 40
    },
    lineColorMax: {  // 干扰线颜色色值最大值 最大值255
      type: Number,
      default: 180
    },
    dotColorMin: {  // 干扰点颜色色值最小值 最小值0
      type: Number,
      default: 0
    },
    dotColorMax: {  // 干扰点颜色色值最大值 最大值255
      type: Number,
      default: 255
    },
    contentWidth: {  // 画布宽度
      type: Number,
      default: 112
    },
    contentHeight: { // 画布高度
      type: Number,
      default: 50
    }
  },
  methods: {
    // 生成一个随机数
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min)
    },
    // 生成一个随机的颜色
    randomColor(min, max) {
      var r = this.randomNum(min, max)
      var g = this.randomNum(min, max)
      var b = this.randomNum(min, max)
      return 'rgb(' + r + ',' + g + ',' + b + ')'
    },
    drawPic() {
      var canvas = document.getElementById('s-canvas')
      var ctx = canvas.getContext('2d')
      ctx.textBaseline = 'bottom'
      // 绘制背景
      ctx.fillStyle = this.randomColor(
        this.backgroundColorMin,
        this.backgroundColorMax
      )
      ctx.fillRect(0, 0, this.contentWidth, this.contentHeight)
      // 绘制文字
      for (let i = 0; i < this.identifyCode.length; i++) {
        this.drawText(ctx, this.identifyCode[i], i)
      }
      // this.drawLine(ctx)
      // this.drawDot(ctx)
    },
    drawText(ctx, txt, i) {
      ctx.fillStyle = this.randomColor(this.colorMin, this.colorMax)
      ctx.font =
        this.randomNum(this.fontSizeMin, this.fontSizeMax) + 'px SimHei'
      var x = (i + 1) * (this.contentWidth / (this.identifyCode.length + 1))
      var y = this.randomNum(this.fontSizeMax, this.contentHeight - 5)
      var deg = this.randomNum(-45, 45)
      // 修改坐标原点和旋转角度
      ctx.translate(x, y)
      ctx.rotate(deg * Math.PI / 180)
      ctx.fillText(txt, 0, 0)
      // 恢复坐标原点和旋转角度
      ctx.rotate(-deg * Math.PI / 180)
      ctx.translate(-x, -y)
    },
    // drawLine(ctx) {
    // 	// 绘制干扰线
    // 	for (let i = 0; i < 8; i++) {
    // 		ctx.strokeStyle = this.randomColor(
    // 			this.lineColorMin,
    // 			this.lineColorMax
    // 		)
    // 		ctx.beginPath()
    // 		ctx.moveTo(
    // 			this.randomNum(0, this.contentWidth),
    // 			this.randomNum(0, this.contentHeight)
    // 		)
    // 		ctx.lineTo(
    // 			this.randomNum(0, this.contentWidth),
    // 			this.randomNum(0, this.contentHeight)
    // 		)
    // 		ctx.stroke()
    // 	}
    // },
    // drawDot(ctx) {
    // 	// 绘制干扰点
    // 	for (let i = 0; i < 100; i++) {
    // 		ctx.fillStyle = this.randomColor(0, 255)
    // 		ctx.beginPath()
    // 		ctx.arc(
    // 			this.randomNum(0, this.contentWidth),
    // 			this.randomNum(0, this.contentHeight),
    // 			1,
    // 			0,
    // 			2 * Math.PI
    // 		)
    // 		ctx.fill()
    // 	}
    // }
  },
  // 监听--watch中包含immediate属性和deep属性。immediate属性：immediate:true，首次绑定的时候执行watch中的方法；immediate:false，当数据发生变化时执行watch中的方法（首次绑定时不执行）
  watch: {
    identifyCode: {
      handler() {
        this.drawPic()
      },
      immediate: false
    }
  },
  // mounted 完成挂载：在这发起后端请求，拿回数据，配合路由钩子做一些事情
  mounted() {
    this.drawPic()
  }
}
</script>

<style>
#s-canvas{
  height:100% ;
}
</style>
