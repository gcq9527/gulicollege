<template>
  <div class="app-container">
    <!-- 讲师添加 -->
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0"/>
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->
      
      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

          <!-- 头衔缩略图 -->
          <pan-thumb :image="teacher.avatar + ''"/>
        
          <!-- 文件上传按钮 -->
          <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
          </el-button>

          <!--
      v-show：是否显示上传组件
      :key：类似于id，如果一个页面多个图片上传控件，可以做区分
      :url：后台上传的url地址
      @close：关闭上传组件
      @crop-upload-success：上传成功后的回调 
        <input type="file" name="file"/>
      -->
          <image-cropper
                        v-show="imagecropperShow"
                        :width="300"
                        :height="300"
                        :key="imagecropperKey"
                        :url="BASE_API+'/eduoss/fileoss'"
                        field="file"
                        @close="close"
                        @crop-upload-success="cropSuccess"/>
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>

import teacherApi from "@/api/edu/teacher";
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components:{
    ImageCropper,
    PanThumb
  },
  data() {
    return {
      teacher: {
        name: '', // 名字
        sort: 0, //  排序
        level: 1, // 等级
        career: '', // 资历
        intro: '', // 简介
        avatar: '' // 头像
      },
      saveBtnDisabled: false, // 保存按钮是否禁用,
      imagecropperShow: false, // 是否显示 
      imagecropperKey:0, //上传组件key值
      BASE_API:process.env.BASE_API, //获取dev.env.js里面地址
  
    };
  },
  created() {this.init()},
  // 监听 route一旦修改 就调用init方法
  watch:{
    $route(to,from) {
      this.init()
    }
  },
  methods: {
    // 关闭上传弹框方法
    close() {
      this.imagecropperShow = false
      //上传组件初始化
      this.imagecropperKey = this.imagecropperKey+1
    },
    cropSuccess(data) {
      this.imagecropperShow = false
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    init() {
      //判断路径是否有id值
      if (this.$route.params && this.$route.params.id) {
        //从路径中获取id
        const id = this.$route.params.id;
        //根据id查询
        this.getInfo(id);
      } else {
        //没有 清空表单
        this.teacher = {};
      }
    },
    saveOrUpdate() {
      //判断是添加还是修改
      //判断teacher是否有id
      if (!this.teacher.id) {
        this.saveTeacher();
      } else {
        this.updateTeacher();
      }
    },
    updateTeacher() {
      teacherApi
        .updateTeacher(this.teacher)
        .then(res => {
          this.$message({
            message: "修改成功",
            type: "success"
          });
          this.$router.push({ path: "/teacher/table" });
        })
        .catch(res => {});
    },
    // 添加讲师
    saveTeacher() {
      teacherApi
        .addTeacher(this.teacher)
        .then(res => {
          this.$message({
            message: "添加成功",
            type: "success"
          });
          //回到列表页面 路由跳转
          this.$router.push({ path: "/teacher/table" });
        })
        .catch(err => {
          this.$message.error("添加失败");
        });
    },
    getInfo(id) {
      teacherApi
        .TeacherById(id)
        .then(res => {
          this.teacher = res.data.teacher;
        })
        .catch(err => {});
    }
  }
};
</script>