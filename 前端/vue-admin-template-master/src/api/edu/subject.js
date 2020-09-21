import request from '@/utils/request'

export default {
  // 查询全部分类数据
  getAllSubject() {
    return request({
      url: `/eduservice/edu-subject/getAllSubject`,
      method: 'get'
    })
  }
 
}
