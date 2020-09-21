<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a href="#" title class="c-999 fsize14">{{courseWebVo.subjectLevelOne}}</a>
        \
        <span class="c-333 fsize14">{{courseWebVo.subjectLevelTwo}}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section class="p-h-video-box" id="videoPlay">
            <img height="357px" width="640px" :src="courseWebVo.cover" :alt="courseWebVo.title" class="dis c-v-pic" />
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{courseWebVo.title}}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;">￥{{courseWebVo.price}}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{courseWebVo.teacherName}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#">收藏</a>
              </span>
            </section>
            <section class="c-attr-mt" v-if="isbuy || Number(courseWebVo.price) == 0" >
              <a  href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
            </section>
            <section class="c-attr-mt" v-else >
              <a @click="createOrders()" href="#" title="立即购买" class="comm-btn c-btn-3">立即购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br />
                <h6 class="c-fff f-fM mt10">{{courseWebVo.buyCount}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br />
                <h6 class="c-fff f-fM mt10">20</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br />
                <h6 class="c-fff f-fM mt10">501</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="courseWebVo.description">{{courseWebVo.description}}</p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 文件目录 -->
                          <li
                            class="lh-menu-stair"
                            v-for="chapter in chapterVideoList"
                            :key="chapter.id"
                          >
                            <a href="javascript: void(0)" title="第一章" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>
                              {{chapter.title}}
                            </a>
                            <ol class="lh-menu-ol" style="display: block;">
                              <li
                                class="lh-menu-second ml30"
                                v-for="video in chapter.children"
                                :key="video.id"
                              >
                                <a :href="'/player/' + video.videoSourceId" target="_blank">
                                  <span class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>
                                  {{video.title}}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
                <!-- 课程评论 -->
                <div>
                  <!-- 1 -->
                   <h6 class="c-i-content c-infor-title">
                    <span>课程评论</span>
                  </h6>
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 文件目录 -->
                          <li  class="lh-menu-stair">
                            <a href="javascript: void(0)" title="第一章" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>
                              课程评论
                            </a>
                            <ol class="lh-menu-ol" style="display: block;">
                              <li
                                class="lh-menu-second ml30"
                                v-for="item in  data.items" :key="item.id"  >
                                <a  target="_blank">
                                  <span class="fr">
                                      <i class="free-icon vam mr10">
                                      {{item.gmtCreate}}</i>
                                    <span>
                                      <el-avatar :size="25" :src="item.avatar"></el-avatar>
                                    </span>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>
                                  
                                  {{item.nickname}}
                                  :
                                  {{item.content}}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  <!-- 2 -->
                 
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
               
                      <el-form ref="form" :model="CommentVo" label-width="80px">
                        <el-form-item label="评论内容">
                          <el-input type="textarea" v-model="CommentVo.content"></el-input>
                        </el-form-item>
                        <el-form-item>
                          <el-button type="primary" @click="onSubmit">提交</el-button>
                          <el-button>取消</el-button>
                        </el-form-item>
                      </el-form>
                    </section>
                  </div>
                </div>
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="courseWebVo.avatar" width="50" height="50" alt />
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{courseWebVo.teacherName}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseWebVo.intro}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)"
            >首</a>
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(data.current-1)"
            >&lt;</a>
            <a
              v-for="page in data.pages"
              :key="page"
              :class="{current: data.current == page, undisable: data.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)"
            >{{ page }}</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(data.current+1)"
            >&gt;</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(data.pages)"
            >末</a>
            <div class="clear" />
          </div>
        </div>
        <!-- 公共分页 结束 -->
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>

<script>
import courseApi from "@/api/course";
import commentApi from "@/api/comment";
import ordersApi  from '@/api/order'

import cookie from "js-cookie";
export default {
 
  data() {
    return {
      page: 1, //当前页
      CommentVo: {
        content: "", //评论内容
        courseId:"", // 课程id
        teacherId:"", // 讲师id
        memberId:""
      },
      data: {}, //评论列表
      courseWebVo: {},
      chapterVideoList: {},
      courseId: "",
      loginInfo: {
        id: "",
        age: "",
        avatar: "",
        mobile: "",
        nickname: "",
        sex: ""
      },
      isbuy:false
    };
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id;
    }
    // 初始化评论列表
    this.initCommentList();
    //课程信息
    this.initCourseInfo();
  },
  methods: {
    // 初始化
    initCourseInfo() {
      console.log(123)
      var userStr = cookie.get('guli_ucenter')
      if(userStr) {
        // alret(userStr)
      } else {
        // alret(userStr)
      }
        courseApi.getCourseInfo(this.courseId).then(res => {
        this.courseWebVo = res.data.data.courseWebVo;
        this.chapterVideoList = res.data.data.chapterVideoList;
        this.isbuy = res.data.data.isBuy;
      });
    },

    // 初始化评论列表
    initCommentList() {
      commentApi.PageComment(1, 4).then(res => {
        this.data = res.data.data;
      });
    },
    // 分页查询课程评论
    gotoPage(page) {
      console.log("分页");
      commentApi.PageComment(page, 4).then(res => {
        this.data = res.data.data;
      });
    },
    // 评论
    onSubmit() {
      // 判断用户是否登录
      var userStr = cookie.get('guli_ucenter')
      // 字符串转换为json对象(js对象)
      if(userStr == null) {
         this.$confirm('登录才能评论, 是否跳转到登录页?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          window.location.href="/login";
          // return;
        })
      } else {
        // 获取id进行插入记录
      this.loginInfo = JSON.parse(userStr);
      var memberid = this.loginInfo.id
      // 会员id
      this.CommentVo.memberId = memberid
      // 拿到课程id
      this.CommentVo.courseId = this.courseId
      // 讲师id
      this.CommentVo.teacherId = this.courseWebVo.teacherId
      console.log(this.CommentVo)
      commentApi.addComment(this.CommentVo)
        .then(res =>{
          // 刷新页面
          this.CommentVo = {}
          this.initCommentList()
        })
      }
     
    },
    // 生成订单
    createOrders() {
      ordersApi.createOrders(this.courseId)
        .then(res => {
          // 返回订单号
          // 生成订单之后，跳转订单显示页面
          this.$router.push({path:'/orders/' + res.data.data.orderId})
        })
    }
    }
};
</script>
