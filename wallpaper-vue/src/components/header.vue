<template>
  <div>
    <div style="width: 15%">
      <el-button icon="el-icon-s-grid" circle class="index-menu" @click="drawer = true"></el-button>
    <el-drawer
      title="wallpaper"
      :visible.sync="drawer"
      :direction="direction"
      :show-close=false
      style="width: 60%"
      :modal-append-to-body="false">
      <ul class="index-ul">
        <li><el-dropdown-item icon="el-icon-star-off">我的收藏</el-dropdown-item></li>
        <li><el-dropdown-item icon="el-icon-star-off">我的收藏</el-dropdown-item></li>
        <li><el-dropdown-item icon="el-icon-star-off">我的收藏</el-dropdown-item></li>
      </ul>
    </el-drawer>
    <!--    主页-->
     <router-link to="/">
        <el-button type="text" class="index-wallpaper">wallpaper</el-button>
      </router-link>
    </div>
<!--搜索框-->
  <el-input placeholder="请输入内容" class="input-with-select" v-model="q">
    <el-button slot="append" icon="el-icon-search" @click="query"></el-button>
  </el-input>
<!--    投稿作品-->
    <el-dropdown trigger="click" class="index-upload">
      <el-button round >投稿作品<i class="el-icon-arrow-down el-icon-arrow-up"></i></el-button>
      <el-dropdown-menu slot="dropdown">
        <router-link to='/upload' style="text-decoration: none">
         <el-dropdown-item icon="el-icon-picture" class="index-upload-item">壁纸</el-dropdown-item>
        </router-link>
        <el-dropdown-item icon="el-icon-s-management">小说</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
<!--    头像-->
  <div id="index-avatar-div">
    <div class="index-lo-re" ref="lore">
      <!-- <router-link to="/dialog"><el-button type="text">登录</el-button></router-link> -->
      <el-button type="text" @click="dialogVisible = true">登录</el-button>
      <el-button type="text" @click="dialogVisibleRegister = true">注册</el-button>
    </div>
      <el-dropdown trigger="click"  class="index-avatar" ref="avatar">
          <el-avatar shape="square" :size="size" :src="squareUrl"></el-avatar>
        <el-dropdown-menu slot="dropdown">
          <div>
            <el-avatar shape="circle" :size="size" :src="squareUrl" style="margin-left: 10%"></el-avatar>
          </div>
          <span style="margin-left: 10%;font-size: 1.2em;margin-top: 10px;margin-bottom: 20px;display: block">{{ username }}</span>
          <el-dropdown-item icon="el-icon-user" style="width: 130px">个人中心</el-dropdown-item>
          <el-dropdown-item icon= "el-icon-setting">投稿管理</el-dropdown-item>
          <el-dropdown-item icon="el-icon-star-off">我的收藏</el-dropdown-item>
          <el-dropdown-item icon="el-icon-refresh-left"><span @click="quite()">退出登录</span></el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
<!-- 登录对话框 -->
      <div>
        <el-dialog title="登录"
        :visible.sync="dialogVisible"
        width="30%"
        class="login-dialog"
        :modal-append-to-body="false">
        <Login/>
      </el-dialog>
      </div>
      <!-- 注册对话框 -->
      <div>
        <el-dialog title="注册"
        :visible.sync="dialogVisibleRegister"
        width="30%"
        class="login-dialog"
        :modal-append-to-body="false">
        <Register/>
      </el-dialog>
      </div>
  </div>
</template>

<script>
import axios from 'axios';
import Register from '../components/register.vue'
import Login from '../views/login.vue'

export default {
  name: "wallpaper-header",
  data (){
    return {
      size: 50,
      squareUrl: 'https://wallpaper-one.oss-cn-beijing.aliyuncs.com/wallpaper/headportrait.png',
      drawer: false,
      direction: 'ltr',
      username:'用户名',
      dialogVisible:false,
      dialogVisibleRegister:false,
      q:''
    };
  },components:{
    Register,Login
  },
  methods:{
    getUser(){

    },
    isLogin(){
      this.axios.get('http://localhost:8082/user/isLogin').then((respone)=>{
        if(!respone.data.flag){

        }else {
          this.username=respone.data.data.userName;
          document.getElementsByClassName("index-lo-re")[0].style.display='none'
          document.getElementsByClassName("index-avatar")[0].style.display='block'
        }
      })
    },
    quite(){
      axios.get('http://localhost:8082/user/quite').then(()=>{
          location.reload()
      })
    }
  },
  mounted(){
    this.isLogin();

    // this.$refs.lore.style.display = 'none'
    // this.$refs.avatar.style.display='block';
    // console.log(this)
  }
}
</script>

<style scoped>
.index-menu{
  position: absolute;
  left: 2%;
  top: 50%;
  margin-top: -20px;
}
.index-wallpaper{
  position: absolute;
  left: 6%;
  top: 50%;
  margin-top: -25px;
  font-size: 1.3em;
  color: black;
}


.input-with-select{
  width: 40%;
  position: absolute;
  left: 15%;
  top: 50%;
  margin-top: -20px;
}

.index-upload{
  position: absolute;
  right: 8%;
  top: 50%;
  margin-top: -20px;
}
.index-avatar{
  display: none;
}
#index-avatar-div{
  position: absolute;
  right: 2%;
  top: 50%;
  margin-top: -25px;
}
.index-lo-re{
  line-height: 50px;
}

.index-upload-item{
  width: 100px;
}
.index-ul{
  padding-left: 0;
}
.index-ul li{
  list-style: none;
  height: 10%;
}
.login-dialog{
  border-radius: 20px;
}
</style>
