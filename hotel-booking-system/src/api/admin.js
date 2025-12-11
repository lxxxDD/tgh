import request from '@/utils/request'

/**
 * 管理员API模块
 */

// ============ 酒店管理 ============
/**
 * 获取酒店列表
 */
export function getAdminHotels(params = {}) {
  return request.get('/admin/hotels', { params })
}

/**
 * 获取酒店详情
 */
export function getAdminHotelDetail(id) {
  return request.get(`/admin/hotels/${id}`)
}

/**
 * 新增酒店
 */
export function createHotel(data) {
  return request.post('/admin/hotels', data)
}

/**
 * 更新酒店
 */
export function updateHotel(id, data) {
  return request.put(`/admin/hotels/${id}`, data)
}

/**
 * 删除酒店
 */
export function deleteHotel(id) {
  return request.delete(`/admin/hotels/${id}`)
}

/**
 * 更新酒店状态
 */
export function updateHotelStatus(id, status) {
  return request.put(`/admin/hotels/${id}/status`, null, { params: { status } })
}

// ============ 订单管理 ============
/**
 * 获取订单列表
 */
export function getAdminOrders(params = {}) {
  return request.get('/admin/orders', { params })
}

/**
 * 获取订单详情
 */
export function getAdminOrderDetail(id) {
  return request.get(`/admin/orders/${id}`)
}

/**
 * 更新订单状态
 */
export function updateOrderStatus(id, status) {
  return request.put(`/admin/orders/${id}/status`, null, { params: { status } })
}

// ============ 用户管理 ============
/**
 * 获取用户列表
 */
export function getAdminUsers(params = {}) {
  return request.get('/admin/users', { params })
}

/**
 * 获取用户详情
 */
export function getAdminUserDetail(id) {
  return request.get(`/admin/users/${id}`)
}

/**
 * 更新用户状态
 */
export function updateUserStatus(id, status) {
  return request.put(`/admin/users/${id}/status`, null, { params: { status } })
}

// ============ 评价管理 ============
/**
 * 获取评价列表
 */
export function getAdminReviews(params = {}) {
  return request.get('/admin/reviews', { params })
}

/**
 * 更新评价状态
 */
export function updateReviewStatus(id, status) {
  return request.put(`/admin/reviews/${id}/status`, null, { params: { status } })
}

/**
 * 删除评价
 */
export function deleteReview(id) {
  return request.delete(`/admin/reviews/${id}`)
}
