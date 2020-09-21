<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程名称" />
      </el-form-item>

      <el-form-item>
        <el-select v-model="courseQuery.status" clearable placeholder="课程状态">
           <el-option value="Normal" label="已发布"/>
          <el-option value="Draft" label="未发布"/>
        </el-select>
      </el-form-item>


      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table :data="list" border fit highlight-current-row>
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">{{ (page - 1) * limit + scope.$index + 1 }}</template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="200" />

      <el-table-column label="课程状态" width="80">
        <template slot-scope="scope">{{ scope.row.status==='Normal' ?'已发布':'未发布' }}</template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" />

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column prop="viewCount" label="浏览数量" width="60" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
           <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲信息</el-button>
          </router-link>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="removeDataById(scope.row.id)"
          >删除课程信息</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>
<script>
import course from "@/api/edu/course";

export default {
  // 核心代码位置
  data() {
    return {
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      courseQuery: {}
    };
  },
  created() {
    //页面渲染前执行
    this.getList();
  },
  methods: {
    // 讲师列表的方法
    getList(page = 1) {
      this.page = page;
      course
        .PageCourseList(this.page,this.limit,this.courseQuery)
        .then(response => {
          this.total = response.data.total;
          this.list = response.data.rows
        })
        .catch(error => {
          console.log(error);
        });
    },
    resetData() {
      this.courseQuery = {};

      this.getList();
    },
    // 删除课程
    removeDataById(id) {
      this.$confirm("是否删除该条记录", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "error"
      }).then(() => {
          course
            .deleteByCourseId(id)
            .then(response => {
              this.$message({
                type: "success",
                message: "删除成功!"
              });
              this.getList()
            })
            .catch(error => {
                this.$message.error('删除失败');
            });
            //刷新
            this.getList()
        })
      
    }
  }
};
</script>
