<template>
<div>
  <div style="width:1000px;margin:0 auto">
    <el-carousel :interval="4000" type="card" height="300px">
    <el-carousel-item  v-for="src in carouselSrc" :key="src" >
        <el-image class="images-head"  :src="src"></el-image>
    </el-carousel-item>
  </el-carousel>
  </div>
  <div class="container-fluid">
        <div class="clearfix visible-xs-block">
          <div v-for="(src,index) in srcList" :key="src" class="index-image-div">
              <el-image class="images" :src="src" lazy :preview-src-list="srcList">
              </el-image>
              <div>
                <p>作者：{{ author[index] }}</p>
<!--                <p>作者：叶凌云</p><p>大小：3840 x 2160</p>-->
              </div>
            </div>
        </div>
  </div>
</div>

</template>

<script>
import axios from "axios";
import Qs from 'qs';

export default {
  name: "wallpaper-main",
  data () {
    return {
      count: 10,
      loading: false,
      srcList:[],
      value1: null,
      value2: null,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900'],
      author: ["叶凌云","叶凌云","叶凌云","叶凌云"],
      carouselSrc:[]
    }
  },
  mounted() {
    axios.get('http://localhost:8082/images/getImages').then((respone)=>{
      respone.data.data.forEach((src)=>{
        this.srcList.push("https://wallpaper-one.oss-cn-beijing.aliyuncs.com/wallpaper/"+src.imageSrc);
        this.author.push(src.imageAuthor);
      })
    })

    axios.get('http://localhost:8082/images/getFiveImages').then((respone)=>{
      respone.data.data.forEach((src)=>{
        this.carouselSrc.push("https://wallpaper-one.oss-cn-beijing.aliyuncs.com/wallpaper/"+src.imageSrc);
      })
    })
  }
}
</script>

<style scoped>
.images-head{
  height: 300px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.5);
  border-radius: 4px;
}
.images{
  height: 300px;
  margin: 10px;
  border-radius: 4px;
}
.index-image-div{
  display: inline-block;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.5);
  border-radius: 4px;
  margin: 10px;
}

</style>
