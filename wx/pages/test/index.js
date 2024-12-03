Page({
  data: {
    swiper: {
      imgUrls: [
        '/images/body.png',
        '/images/body.png',
        '/images/body.png'
      ],
      indicatorDots: false,
      autoplay: false,
      interval: 5000,
      duration: 1000,
      current: 0,
    }
  },
  prevImg: function(){
    var swiper = this.data.swiper;
    var current = swiper.current;
    swiper.current = current>0 ? current-1 : swiper.imgUrls.length-1;
    this.setData({
      swiper: swiper,
    })
  },
  nextImg: function () {
    var swiper = this.data.swiper;
    var current = swiper.current;
    swiper.current = current < (swiper.imgUrls.length - 1) ? current + 1 : 0;
    this.setData({
      swiper: swiper,
    })
  }
})