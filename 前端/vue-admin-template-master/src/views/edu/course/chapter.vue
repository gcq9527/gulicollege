<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-button type="text" @click="openChapterDialog()">添加章节</el-button>
    <!-- 章节 -->
    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
            <el-button style type="text" @click="openVideo(chapter.id)">添加小节</el-button>
            <el-button style type="text" @click="openEditChatper(chapter.id)">编辑</el-button>
            <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <span class="acts">
                <el-button style type="text" @click="openEditVideo(video.id)">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.free">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video/uploadAliyVideo'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，
                <br />支持3GP、ASF、AVI、DAT、DV、FLV、F4V、
                <br />GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、
                <br />MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、
                <br />SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";
export default {
  data() {
    return {
      saveVideoBtnDisabled: false,
      saveBtnDisabled: false,
      chapterVideoList: [],
      courseId: "",
      chapter: {
        // 章节数据
        title: "",
        sort: 0
      },
      video: {
        title: "",
        sort: 0,
        free: 0,
        videoSourceId: "",
        videoOriginalName: "" // 视频名称
      },
      fileList: [], //上传文件列表
      BASE_API: process.env.BASE_API, // 接口API地址
      dialogChapterFormVisible: false, //章节弹框
      dialogVideoFormVisible: false //小节弹框
    };
  },
  created() {
    //获取路由的id值
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
      console.log(this.courseId);
      //根据课程id查询章节和小节
      this.getChapterVideo();
    }
  },
  methods: {
    //上传视频成功调用的方法
    handleVodUploadSuccess(response, file, fileList) {
     //上传视频id赋值
            this.video.videoSourceId = response.data.videoId
            //上传视频名称赋值
            this.video.videoOriginalName = file.name
    },
    // 
    handleUploadExceed() {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },
    // 删除视频
    handleVodRemove() {
      //删除视频
      video.delteAliyunVod(this.video.videoSourceId).then(res => {
        this.$message({
          type: "success",
          message: "删除视频成功!"
        });
        // 文件列表清空
        this.fileList = [];
        //上传视频id赋值
        this.video.videoSourceId = ''
        //上传视频名称赋值
        this.video.videoOriginalName = ''
      });
    },
    handleUploadExceed() {
      console.log(1)
    },
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定删除 ${file.name}?`);
    },
    // 修改小节内容
    openEditVideo(videoId) {
      this.dialogVideoFormVisible = true;
      video.getVideo(videoId).then(res => {
        this.video = res.data.video;
      });
    },
    // 更改小节
    updateVideo() {
      video.updateVideo(this.video).then(res => {
        //关闭弹框
        this.dialogVideoFormVisible = false;
        this.$notify({
          title: "成功",
          message: "修改成功",
          type: "success"
        });
        //刷新
        this.getChapterVideo();
      });
    },
    // 删除小节
    removeVideo(videoId) {
      this.$confirm("是否删除该小节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        video.deleteVideo(videoId).then(res => {
          this.$notify({
            title: "成功",
            message: "删除成功",
            type: "success"
          });
          //刷新
          this.getChapterVideo();
        });
      });
    },
    // 保存小节
    saveOrUpdateVideo() {
      if (!this.video.id) {
        this.addVideo();
      } else {
        this.updateVideo();
      }
    },
    //小节操作
    openVideo(chapterId) {
      
      this.dialogVideoFormVisible = true;
      this.fileList = []
      this.video = {};
      // 设置章节id
      this.video.chapterId = chapterId;
    },
    // 添加小节
    addVideo() {
      this.video.courseId = this.courseId;
      video.addVideo(this.video).then(res => {
        this.dialogVideoFormVisible = false;
        this.$message({
          type: "success",
          message: "添加成功!"
        });
        //刷新
        this.getChapterVideo();
      });
    },
    //章节操作
    // 删除章节
    removeChapter(chapterId) {
      this.$confirm("是否删除该章节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        chapter.deleteChapter(chapterId).then(res => {
          this.$message({
            type: "success",
            message: "删除成功!"
          });
          //刷新
          this.getChapterVideo();
        });
      });
    },
    //修改当前
    openEditChatper(chapterId) {
      // 弹框
      this.dialogChapterFormVisible = true;
      chapter.getChapter(chapterId).then(res => {
        this.chapter = res.data.chapter;
      });
    },
    // 打开对话框
    openChapterDialog() {
      // 弹框
      this.dialogChapterFormVisible = true;
      // 数据清空
      this.chapter.title = "";
      this.chapter.sort = 0;
    },
    // 添加章节
    addChapter() {
      //设置课程id到chapter对象里面
      this.chapter.courseId = this.courseId;
      chapter.addChapter(this.chapter).then(response => {
        //关闭弹框
        this.dialogChapterFormVisible = false;
        //提示
        this.$message({
          type: "success",
          message: "添加章节成功!"
        });
        //刷新页面
        this.getChapterVideo();
      });
    },
    // 根据id判断石头添加或修改
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.addChapter();
      } else {
        this.updateChapter();
      }
    },
    // 修改章节
    updateChapter() {
      chapter.updateChapter(this.chapter).then(res => {
        //关闭弹框
        this.dialogChapterFormVisible = false;
        //提示
        this.$message({
          type: "success",
          message: "修改章节成功!"
        });
        //刷新页面
        this.getChapterVideo();
      });
    },
    // 根据课程id获取章节和小节内容
    getChapterVideo() {
      chapter
        .getAllChapterVideo(this.courseId)
        .then(res => {
          this.chapterVideoList = res.data.allChapterVideo;
        })
        .catch(err => {
          console.log("查询章节出错");
        });
    },
    previous() {
      this.$router.push({ path: "/course/info/" + this.courseId });
    },
    next() {
      //跳转到第二步
      // alert(this.courseId)
      this.$router.push({ path: "/course/publish/" + this.courseId });
    }
  }
};
</script>
<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li {
  position: relative;
}
.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>