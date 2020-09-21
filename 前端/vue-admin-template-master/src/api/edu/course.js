import request from '@/utils/request'

export default {
  // 查询全部分类数据
  addCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/course/addCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  getListTeacher() {
    return request({
      url: `/eduservice/edu-teacher/findAll`,
      method: 'get'
    })
  },
  // 根据课程id查询课程基本信息
  getCourseInfoId(id) {
    return request({
      url: `/eduservice/course/getCourseInfo/` + id,
      method: 'get'
    })
  },
  // 修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: `/eduservice/course/updateCourseInfo`,
      method: 'post',
      data: courseInfo
    })
  },
  // 课程信息返回
  getPublihCourseInfo(id) {
    return request({
      url: `/eduservice/course/getPublishCourseInfo/` + id,
      method: 'get'
    })
  },
  publishCourse(id) {
    return request({
      url: `/eduservice/course/publishCourse/` + id,
      method: 'post'
    })
  },
  // TODO 完成分页 课程列表
  getListCourse() {
    return request({
      url: `/eduservice/course/getCourseList`,
      method: 'get'
    })
  },
  PageCourseList(current,limit,courseQuery) {
    return request({
      url: `/eduservice/course/pageCourseList/${current}/${limit}`,
      method: 'post',
      data: courseQuery
    })
  },
  // 根据课程id删除
  deleteByCourseId(courseId) {
    return request({
      url: `/eduservice/course/`+ courseId,
      method: 'delete'
    })
  },
 
}
