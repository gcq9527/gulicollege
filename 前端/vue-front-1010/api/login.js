import request from '@/utils/request'
export default {

  // 登录
  submitLogin(userInfo) {
    return request({
      url : `/educenter/member/login`,
      method: 'post',
      data: userInfo
    })
  },
  // 注册
  getMemberInfo() {
    return request({
      url : `/educenter/member/getMemberInfo`,
      method: 'get'
    })
  }
}