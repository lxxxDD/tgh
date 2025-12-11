import request from '@/utils/request'

/**
 * 获取收藏列表
 * @param {Object} params - { page, size }
 */
export function getFavoriteList(params) {
  return request.get('/favorites', { params })
}

/**
 * 添加收藏
 * @param {Number} hotelId - 酒店ID
 */
export function addFavorite(hotelId) {
  return request.post(`/favorites/${hotelId}`)
}

/**
 * 取消收藏
 * @param {Number} hotelId - 酒店ID
 */
export function removeFavorite(hotelId) {
  return request.delete(`/favorites/${hotelId}`)
}

/**
 * 检查是否已收藏
 * @param {Number} hotelId - 酒店ID
 */
export function checkFavorite(hotelId) {
  return request.get(`/favorites/check/${hotelId}`)
}
