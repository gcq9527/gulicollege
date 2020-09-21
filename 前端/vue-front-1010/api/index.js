import request from '@/utils/request'
export default {

  // 查询前两条banner数据
  getIndexData() {
    return request({
      url : '/eduservice/indexfront/getAllBanner',
      method: 'get'
    })
  }
}