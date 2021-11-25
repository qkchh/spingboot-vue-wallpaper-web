<template>
  <div id="app">
      <el-header><Header/></el-header>
     <el-main><router-view></router-view></el-main>
  </div>
</template> 

<script>
import Header from "./components/header.vue"

export default {
  name: 'App',
  components:{
      Header
  }
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
.el-header{
    height: 60px;
    background-color: aliceblue;
    position: relative;
    z-index: 100;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
  }
  .el-main{
    background-color: aliceblue;
    position: fixed;
    box-sizing: border-box;
    height: 100%;
    width: 100%;
  }
</style>
