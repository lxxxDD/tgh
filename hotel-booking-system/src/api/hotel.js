import request from '@/utils/request'

/**
 * 获取酒店列表
 * @param {Object} params - 查询参数 { city, checkIn, checkOut, priceRange, starRating, amenities, sortBy, page, size }
 */
export function getHotelList(params = {}) {
  return request.get('/hotels', { params })
}

/**
 * 获取酒店详情
 * @param {Number} id - 酒店ID
 */
export function getHotelDetail(id) {
  return request.get(`/hotels/${id}`)
}

/**
 * 获取酒店房间列表
 * @param {Number} id - 酒店ID
 */
export function getHotelRooms(id) {
  return request.get(`/hotels/${id}/rooms`)
}
