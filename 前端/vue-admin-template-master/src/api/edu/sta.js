import request from '@/utils/request'

export default {
  // 生成统计数据
  createReg(day) {
    return request({
      url: '/staservice/sta/registerCount/' + day,
      method: 'post'
    })
  },
  showData(searchObj) {
    return request({
      url: `/staservice/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
      method: 'get'
    })
  }
}
