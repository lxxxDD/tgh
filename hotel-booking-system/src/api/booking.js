import request from '@/utils/request'

/**
 * 创建预订
 * @param {Object} data - 预订信息
 */
export function createBooking(data) {
  return request.post('/bookings', data)
}

/**
 * 获取用户订单列表
 * @param {Object} params - { status, page, size }
 */
export function getBookingList(params) {
  return request.get('/bookings', { params })
}

/**
 * 获取订单详情
 * @param {Number} id - 订单ID
 */
export function getBookingDetail(id) {
  return request.get(`/bookings/${id}`)
}

/**
 * 取消订单
 * @param {Number} id - 订单ID
 */
export function cancelBooking(id) {
  return request.delete(`/bookings/${id}`)
}
