import request from '@/utils/request'
export default {

  // 分页查询课程评论
  PageComment(page,limit) {
    return request({
      url : `/eduservice/comment/PageComment/${page}/${limit}`,
      method: 'get'
    })
  },
  addComment(comment) {
    return request({
      url : `/eduservice/comment/saveComment`,
      method: 'post',
      data: comment
    })
  }
 
  
}