<template>
  <div>
    <el-upload
      action=""
      ref="upload"
      :multiple="true"
      :auto-upload="false"
      name="photos"
      accept="alt"
      list-type="picture-card"
      :on-preview="handlePictureCardPreview"
      :on-remove="handleRemove"
      :before-upload=getDataForUpload>
      <i slot="default" class="el-icon-plus"></i>
      <img class="el-upload-list__item-thumbnail" alt="">
    </el-upload>
    <el-dialog :visible.sync="dialogVisible">
      <img width="100%" :src="dialogImageUrl" alt="">
    </el-dialog>
    <div class="upload-tags">
      <el-tag :key="tag"
        v-for="tag in dynamicTags"
        closable
        :disable-transitions="false"
        @close="handleClose(tag)"
        type="success">
        {{tag}}
      </el-tag>
      <el-input
        class="input-new-tag"
        v-if="inputVisible"
        v-model="inputValue"
        ref="saveTagInput"
        size="small"
        @keyup.enter.native="handleInputConfirm"
        @blur="handleInputConfirm"
      >
      </el-input>
    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
    </div>
    <div class="upload-progress">
      <el-progress type="circle" :percentage="percent" :width="50"></el-progress>
    </div>
    <el-button style="margin-left: 10px;" size="small" type="success" @click="uploadPercent">上传到服务器</el-button>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "wallpaper-upload",
  data() {
    return{
      percent: 0,
      dialogImageUrl: '',
      dialogVisible: false,
      chatTimer:null,
      formData:new FormData(),
      dynamicTags: ['动漫', '图片'],
      inputVisible: false,
      inputValue: ''
    }
  },
  methods:{
    //查看上传的状态并输入到主页
    uploadPercent(){
      this.resetPercent();
      this.showStatus();
      this.$refs.upload.submit();
      axios({
        method:"post",
        url:"http://localhost:8082/images/upload",
        data:this.formData,
        processData:false,
        contentType: false,
      })
    },
    //每隔0.1秒获取上传状态
    showStatus(){
      let data=0;
      this.quite=false;
      this.chatTimer = setInterval(() => {
          axios.get('http://localhost:8082/images/upload/percent')
          .then(function (respone){
            data=respone.data;
          })
        if(data == -1){
          clearInterval(this.chatTimer);
          return;
        }
        if (data >= 100) {
          data = 100;//不能大于100
          this.resetPercent();
          clearInterval(this.chatTimer);
        }
        console.log(data)
        this.percent=data;
      }, 200);
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    getDataForUpload(file){
      this.formData.append("photos",file);
      this.formData.append("tags",this.dynamicTags)
    },
    //清空session中的值
    resetPercent(){
      axios.get("http://localhost:8082/images/percent/reset").then(function (respone){
      });
    },
    handleClose(tag) {
        this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
      },

    showInput() {
        this.inputVisible = true;
        this.$nextTick(_ => {
          this.$refs.saveTagInput.$refs.input.focus();
        });
      },

    handleInputConfirm() {
        let inputValue = this.inputValue;
        if (inputValue) {
          this.dynamicTags.push(inputValue);
        }
        this.inputVisible = false;
        this.inputValue = '';
      }
  },
  beforeDestroy() {
    clearInterval(this.chatTimer);
  }
}
</script>

<style scoped>
.upload-progress{
  margin: 5px;
}

.el-tag + .el-tag {
    margin-left: 10px;
  }
  .button-new-tag {
    margin-left: 10px;
    height: 32px;
    line-height: 30px;
    padding-top: 0;
    padding-bottom: 0;
  }
  .input-new-tag {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  .upload-tags{
    margin: 5px;
  }
</style>
