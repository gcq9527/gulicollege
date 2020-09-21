import request from '@/utils/request'
export default {

  // 根据手机发送验证码
  sendCode(phone) {
    return request({
      url : `/edumsm/msm/send/${phone}`,
      method: 'get'
    })
  },
  // 注册
  registerMember(fromItem) {
    return request({
      url : `/educenter/member/register`,
      method: 'post',
      data: fromItem
    })
  }
}