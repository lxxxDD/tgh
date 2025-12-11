import axios from 'axios'

/**
 * 创建 axios 实例
 */
const service = axios.create({
  baseURL: '/api',
  timeout: 10000
})

/**
 * 请求拦截器
 */
service.interceptors.request.use(
  config => {
    // 从 localStorage 获取 token
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

/**
 * 响应拦截器
 */
service.interceptors.response.use(
  response => {
    const res = response.data
    // 业务状态码判断
    if (res.code === 200) {
      return res.data
    } else {
      console.error('业务错误:', res.message)
      return Promise.reject(new Error(res.message || '请求失败'))
    }
  },
  error => {
    console.error('响应错误:', error)
    // 提取后端返回的错误信息
    const errorMessage = error.response?.data?.message || error.message || '请求失败'
    // 401 未授权，跳转登录
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      window.location.href = '/'
    }
    return Promise.reject(new Error(errorMessage))
  }
)

export default service
