import request from '@/utils/request'

/**
 * 获取酒店评价列表
 * @param {Number} hotelId - 酒店ID
 * @param {Object} params - { page, size }
 */
export function getHotelReviews(hotelId, params) {
  return request.get(`/reviews/hotel/${hotelId}`, { params })
}

/**
 * 提交评价
 * @param {Object} data - { orderId, hotelId, rating, content }
 */
export function createReview(data) {
  return request.post('/reviews', data)
}
