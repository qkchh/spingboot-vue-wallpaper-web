<template>
<div class="login-div">
    <div class="login-center">
      <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="用户名" prop="username">
          <el-input type="text"  v-model="ruleForm.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">注册</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
    </el-form>
    </div>
</div>
</template>

<script>

import axios from "axios";
import Qs from 'qs'

export default {
  name: "wallpaper-register",
  components:{

  },
   data() {
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入密码'));
        } else {
          if (this.ruleForm.checkPass !== '') {
            this.$refs.ruleForm.validateField('checkPass');
          }
          callback();
        }
      };
      var validateUsername = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入用户名'));
        } else {
          if (this.ruleForm.checkUsername !== '') {
            this.$refs.ruleForm.validateField('checkUsername');
          }
          callback();
        }
      };
       var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };


      return {
        ruleForm: {
          pass: '',
          username: '',
          checkPass:''
        },
        rules: {
          pass: [
            { validator: validatePass, trigger: 'blur' }
          ],
          username :[
            { validator: validateUsername, trigger: 'blur' }
          ],
          checkPass: [
            { validator: validatePass2, trigger: 'blur' }
          ]
        }
      };
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
           let data={
              userPassword:this.ruleForm.pass,
              userName:this.ruleForm.username
            }
            data=Qs.stringify(data)
            console.log(data)
            axios.post('http://localhost:8082/user/register',data).then(function(respone){
              console.log(respone)
              if(respone.data.flag){
                alert(respone.data.errorMsg);
                location.reload();
              }else {
                alert(respone.data.errorMsg);
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
}
</script>

<style scoped>
.login-div{
    height: 100%;
}
.login-center{
    margin: 0 auto;
    background-color: white;
    padding: 8% 17% 3% 0%;
    border-radius: 10px;
}
</style>
