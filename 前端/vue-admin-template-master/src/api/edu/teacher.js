import request from '@/utils/request'

export default {
  // 分页
  getTeacherListPage(current, limit, teacherQuery) {
    return request({
      // url: '/table/list/' + current + "/" +limit,
      url: `/eduservice/edu-teacher/PageTeacherCondition/${current}/${limit}`,
      method: 'post',
      data: teacherQuery // teacherQuery条件对象 后端使用RequestBody接收数据
    })
  },
  // 删除讲师
  deleteTeacherById(id) {
    return request({
      // url: '/table/list/' + current + "/" +limit,
      url: `/eduservice/edu-teacher/${id}`,
      method: 'delete',
    })
  },
  // 添加
  addTeacher(teacher) {
    return request({
      // url: '/table/list/' + current + "/" +limit,
      url: `/eduservice/edu-teacher/addTeacher`,
      method: 'post',
      data: teacher
    })
  },
  TeacherById(id) {
    return request({
      // url: '/table/list/' + current + "/" +limit,
      url: `/eduservice/edu-teacher/getTeacher/${id}`,
      method: 'get',
    })
  },
  updateTeacher(teacher) {
    return request({
      // url: '/table/list/' + current + "/" +limit,
      url: `/eduservice/edu-teacher/updateTeacher/`,
      method: 'Post',
      data: teacher
    })
  }
}
