import { defineStore } from 'pinia'
import { login as apiLogin, register as apiRegister, getUserProfile } from '@/api/auth'
import { getHotelList, getHotelDetail, getHotelRooms } from '@/api/hotel'
import { getBookingList, createBooking as apiCreateBooking, cancelBooking as apiCancelBooking } from '@/api/booking'

// 用户状态管理
export const useUserStore = defineStore('user', {
  state: () => ({
    user: null,
    isAuthenticated: false,
    token: null,
    role: null,
    showLoginModal: false
  }),
  
  getters: {
    isLoggedIn: (state) => state.isAuthenticated && !!state.token,
    isAdmin: (state) => state.role === 'admin'
  },
  
  actions: {
    // 登录
    async login(loginData) {
      try {
        const data = await apiLogin(loginData)
        this.user = data.user
        this.token = data.token
        this.role = data.role
        this.isAuthenticated = true
        localStorage.setItem('token', data.token)
        localStorage.setItem('role', data.role)
        return data
      } catch (error) {
        console.error('登录失败:', error)
        throw error
      }
    },
    
    // 注册
    async register(registerData) {
      try {
        const data = await apiRegister(registerData)
        this.user = data.user
        this.token = data.token
        this.role = data.role || 'user'
        this.isAuthenticated = true
        localStorage.setItem('token', data.token)
        localStorage.setItem('role', this.role)
        return data
      } catch (error) {
        console.error('注册失败:', error)
        throw error
      }
    },
    
    // 退出登录
    logout() {
      this.user = null
      this.isAuthenticated = false
      this.token = null
      this.role = null
      localStorage.removeItem('token')
      localStorage.removeItem('role')
    },
    
    // 从本地存储加载用户信息
    async loadUserFromStorage() {
      const token = localStorage.getItem('token')
      const role = localStorage.getItem('role')
      if (token) {
        this.token = token
        this.role = role
        this.isAuthenticated = true
        try {
          // 获取用户信息
          const user = await getUserProfile()
          this.user = user
          this.role = user.role || role
        } catch (error) {
          // token 失效，清除登录状态
          this.logout()
        }
      }
    }
  }
})

// 酒店状态管理
export const useHotelStore = defineStore('hotel', {
  state: () => ({
    hotels: [],
    currentHotel: null,
    currentRooms: [],
    loading: false,
    totalCount: 0,
    currentPage: 1,
    pageSize: 10
  }),
  
  actions: {
    // 获取酒店列表（带参数，由后端处理筛选/排序/分页）
    async fetchHotelsWithParams(params = {}) {
      this.loading = true
      try {
        const data = await getHotelList(params)
        // API 返回格式: {total, page, size, pages, list}
        this.hotels = data.list || data.records || data || []
        this.totalCount = data.total || 0
        this.currentPage = data.page || 1
        this.pageSize = data.size || 10
      } catch (error) {
        console.error('获取酒店列表失败:', error)
        this.hotels = []
        this.totalCount = 0
      } finally {
        this.loading = false
      }
    },
    
    // 获取酒店列表（无参数，获取全部）
    async fetchHotels() {
      await this.fetchHotelsWithParams({})
    },
    
    // 获取酒店详情
    async fetchHotelDetail(id) {
      this.loading = true
      try {
        this.currentHotel = await getHotelDetail(id)
      } catch (error) {
        console.error('获取酒店详情失败:', error)
      } finally {
        this.loading = false
      }
    },
    
    // 获取酒店房间列表
    async fetchHotelRooms(id) {
      try {
        this.currentRooms = await getHotelRooms(id)
      } catch (error) {
        console.error('获取房间列表失败:', error)
      }
    }
  }
})

// 预定状态管理
export const useBookingStore = defineStore('booking', {
  state: () => ({
    bookings: [],
    currentBooking: null,
    loading: false,
    totalCount: 0
  }),
  
  actions: {
    // 获取订单列表（由后端处理筛选/分页）
    async fetchBookings(params = {}) {
      this.loading = true
      try {
        const data = await getBookingList(params)
        // API 返回格式: {total, page, size, pages, list}
        this.bookings = data.list || data.records || data || []
        this.totalCount = data.total || 0
      } catch (error) {
        console.error('获取订单列表失败:', error)
        this.bookings = []
        this.totalCount = 0
      } finally {
        this.loading = false
      }
    },
    
    // 创建预订
    async createBooking(bookingData) {
      try {
        const data = await apiCreateBooking(bookingData)
        this.bookings.unshift(data)
        return data
      } catch (error) {
        console.error('创建预订失败:', error)
        throw error
      }
    },
    
    // 取消订单
    async cancelBooking(id) {
      try {
        await apiCancelBooking(id)
        const booking = this.bookings.find(b => b.id === id)
        if (booking) {
          booking.status = 'cancelled'
        }
      } catch (error) {
        console.error('取消订单失败:', error)
        throw error
      }
    }
  }
})
