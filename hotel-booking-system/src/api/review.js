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

/**
 * 获取最新评价（用于首页展示）
 * @param {Number} limit - 获取数量，默认6条
 */
export function getLatestReviews(limit = 6) {
  return request.get('/reviews/latest', { params: { limit } })
}
