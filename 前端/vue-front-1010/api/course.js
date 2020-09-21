import request from '@/utils/request'
export default {

  // 分页查询讲师方法
  getCourseList(page,limit,searchObj) {
    return request({
      url : `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  // 查询所有分类
  getAllSubject() {
    return request({
      url : `/eduservice/edu-subject/getAllSubject`,
      method: 'get'
    })
  },
  // 课程详情的方法
  getCourseInfo(courseId) {
    return request({
      url : `/eduservice/coursefront/getFrontCourseInfo/${courseId}`,
      method: 'get' 
    })
  }
  
}