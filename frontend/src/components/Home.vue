<template>
  <div>
    <b-carousel id="carousel1"
                style="text-shadow: 1px 1px 2px #333;"
                controls
                indicators
                background="#ababab"
                :interval="4000"
                img-width="1024"
                img-height="480"
                v-model="slide"
                @sliding-start="onSlideStart"
                @sliding-end="onSlideEnd"
    >

      <!-- Text slides with image -->
      <b-carousel-slide caption="마이다스 카페란?"
                        text="최고급 원두만을 사용하였습니다."
                        img-src="https://picsum.photos/1024/480/?image=52"
      ></b-carousel-slide>
      <!-- Text slides with image -->
      <b-carousel-slide caption="온라인 주문"
                        text="매장 가지말고 인터넷으로 주문하세요!"
                        img-src="https://picsum.photos/1024/480/?image=52"
      ></b-carousel-slide>



    </b-carousel>


  </div>
</template>

<script>
export default {
  data () {
    return {
      slide: 0,
      sliding: null,
    }
  },
  methods: {
    onSlideStart (slide) {
      this.sliding = true
    },
    onSlideEnd (slide) {
      this.sliding = false
    }
  }
};
</script>

<style scoped>
</style>
