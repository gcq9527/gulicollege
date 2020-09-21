import request from '@/utils/request'
export default {

  // 分页查询讲师方法
  getTeacherList(page,limit) {
    return request({
      url : `/eduservice/teacherfront/getTeacherFrontList/${page}/${limit}`,
      method: 'post'
    })
  },
  // 根据id查询出讲师 
  getTeacherInfo(id) {
    return request({
      url : `/eduservice/teacherfront/getTeacherFronInfo/${id}`,
      method: 'get'
    })
  }
}