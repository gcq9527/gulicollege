import request from '@/utils/request'
export default {

  // 生成订单
  createOrders(courseId) {
    return request({
      url : `/eduorder/order/createOrder/${courseId}`,
      method: 'post'
    })
  },
  // 根据订单id查询信息
  getOrdersInfo(orderId) {
    return request({
      url : `/eduorder/order/getOrderInfo/${orderId}`,
      method: 'get'
    })
  },
  //创建订单
  createNative(orderNo) {
    return request({
      url : `/eduorder/pay/createNative/${orderNo}`,
      method: 'get'
    })
  },
  // 查询叮当支付状态
  queryPayStatus(orderNo) {
    return request({
      url :  `/eduorder/pay/queryPayStatus/` + orderNo,
      method: 'get'
    })
  }

  
}