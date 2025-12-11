import request from '@/utils/request'

/**
 * 用户登录
 * @param {Object} data - { username, password }
 */
export function login(data) {
  return request.post('/auth/login', data)
}

/**
 * 用户注册
 * @param {Object} data - { username, password, email, phone }
 */
export function register(data) {
  return request.post('/auth/register', data)
}

/**
 * 获取用户信息
 */
export function getUserProfile() {
  return request.get('/auth/user/profile')
}

/**
 * 更新用户信息
 * @param {Object} data - { email, phone, realName, idCard }
 */
export function updateUserProfile(data) {
  return request.put('/auth/user/profile', data)
}

/**
 * 修改密码
 * @param {Object} data - { currentPassword, newPassword }
 */
export function updatePassword(data) {
  return request.post('/auth/user/password', data)
}

/**
 * 忘记密码 - 发送重置令牌
 * @param {Object} data - { email }
 */
export function forgotPassword(data) {
  return request.post('/auth/forgot-password', data)
}

/**
 * 重置密码
 * @param {Object} data - { token, newPassword, confirmPassword }
 */
export function resetPassword(data) {
  return request.post('/auth/reset-password', data)
}

/**
 * 检查用户名是否存在
 * @param {string} username
 */
export function checkUsername(username) {
  return request.get('/auth/check-username', { params: { username } })
}

/**
 * 检查邮箱是否存在
 * @param {string} email
 */
export function checkEmail(email) {
  return request.get('/auth/check-email', { params: { email } })
}

/**
 * 简化重置密码（用户名+邮箱验证）
 * @param {Object} data - { username, email, newPassword }
 */
export function resetPasswordSimple(data) {
  return request.post('/auth/reset-password-simple', data)
}
