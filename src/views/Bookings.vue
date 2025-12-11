<template>
  <div class="bookings">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- 页面标题 -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">我的订单</h1>
        <p class="text-gray-600">管理您的酒店预订</p>
      </div>

      <!-- 订单状态筛选 -->
      <div class="card mb-8">
        <div class="flex flex-wrap gap-4">
          <button 
            v-for="status in statusFilters" 
            :key="status.value"
            @click="selectedStatus = status.value"
            :class="[
              'px-4 py-2 rounded-lg font-medium transition-colors',
              selectedStatus === status.value
                ? 'bg-primary-600 text-white'
                : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
            ]"
          >
            {{ status.label }}
            <span class="ml-2 text-sm">
              ({{ getStatusCount(status.value) }})
            </span>
          </button>
        </div>
      </div>

      <!-- 加载状态 -->
      <LoadingSpinner v-if="bookingStore.loading" text="正在加载订单..." />

      <!-- 订单列表 -->
      <div v-else-if="bookingStore.bookings.length > 0" class="space-y-6">
        <div 
          v-for="booking in bookingStore.bookings" 
          :key="booking.id"
          class="card hover:shadow-lg transition-shadow"
        >
          <div class="flex flex-col lg:flex-row lg:items-center justify-between">
            <!-- 订单基本信息 -->
            <div class="flex-1">
              <div class="flex items-start justify-between mb-4">
                <div>
                  <h3 class="text-xl font-semibold text-gray-900 mb-2">{{ booking.hotelName }}</h3>
                  <div class="flex items-center text-gray-600 mb-2">
                    <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
                    </svg>
                    <span class="text-sm">{{ getHotelAddress(booking.hotelId) }}</span>
                  </div>
                </div>
                
                <div class="text-right">
                  <div 
                    :class="[
                      'inline-flex items-center px-3 py-1 rounded-full text-sm font-medium',
                      getStatusBadgeClass(booking.status)
                    ]"
                  >
                    {{ getStatusText(booking.status) }}
                  </div>
                  <div class="text-2xl font-bold text-primary-600 mt-2">
                    ¥{{ booking.totalAmount }}
                  </div>
                </div>
              </div>
              
              <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-4">
                <div class="flex items-center text-gray-600">
                  <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                  </svg>
                  <div>
                    <div class="text-sm text-gray-500">入住日期</div>
                    <div class="font-medium">{{ formatDate(booking.checkInDate) }}</div>
                  </div>
                </div>
                
                <div class="flex items-center text-gray-600">
                  <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                  </svg>
                  <div>
                    <div class="text-sm text-gray-500">退房日期</div>
                    <div class="font-medium">{{ formatDate(booking.checkOutDate) }}</div>
                  </div>
                </div>
                
                <div class="flex items-center text-gray-600">
                  <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                  </svg>
                  <div>
                    <div class="text-sm text-gray-500">入住天数</div>
                    <div class="font-medium">{{ booking.nights }} 晚</div>
                  </div>
                </div>
                
                <div class="flex items-center text-gray-600">
                  <svg class="w-4 h-4 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path>
                  </svg>
                  <div>
                    <div class="text-sm text-gray-500">入住人</div>
                    <div class="font-medium">{{ booking.guestName }}</div>
                  </div>
                </div>
              </div>
              
              <div class="flex items-center justify-between">
                <div class="text-sm text-gray-500">
                  订单号：{{ booking.id }} | 预订时间：{{ formatDateTime(booking.bookingTime) }}
                </div>
                
                <div class="flex space-x-3">
                  <button 
                    @click="viewBookingDetail(booking)"
                    class="text-primary-600 hover:text-primary-700 font-medium text-sm"
                  >
                    查看详情
                  </button>
                  
                  <button 
                    v-if="booking.status === 'confirmed'"
                    @click="cancelBooking(booking)"
                    class="text-red-600 hover:text-red-700 font-medium text-sm"
                  >
                    取消订单
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-else class="text-center py-12">
        <svg class="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
        </svg>
        <h3 class="text-lg font-medium text-gray-900 mb-2">暂无订单</h3>
        <p class="text-gray-600 mb-6">您还没有任何订单记录</p>
        <router-link 
          to="/hotels"
          class="btn-primary"
        >
          开始预订
        </router-link>
      </div>
    </div>

    <!-- 订单详情模态框 -->
    <div v-if="showDetailModal && selectedBooking" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-lg p-8 max-w-2xl w-full mx-4 max-h-screen overflow-y-auto">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-2xl font-bold text-gray-900">订单详情</h2>
          <button 
            @click="showDetailModal = false"
            class="text-gray-400 hover:text-gray-600"
          >
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>
        
        <div class="space-y-6">
          <!-- 酒店信息 -->
          <div class="bg-gray-50 rounded-lg p-4">
            <h3 class="font-semibold text-gray-900 mb-3">酒店信息</h3>
            <div class="space-y-2">
              <div class="flex justify-between">
                <span class="text-gray-600">酒店名称：</span>
                <span class="text-gray-900">{{ selectedBooking.hotelName }}</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">房间类型：</span>
                <span class="text-gray-900">{{ selectedBooking.roomType }}</span>
              </div>
            </div>
          </div>
          
          <!-- 入住信息 -->
          <div class="bg-gray-50 rounded-lg p-4">
            <h3 class="font-semibold text-gray-900 mb-3">入住信息</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div class="space-y-2">
                <div class="flex justify-between">
                  <span class="text-gray-600">入住人：</span>
                  <span class="text-gray-900">{{ selectedBooking.guestName }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">联系电话：</span>
                  <span class="text-gray-900">{{ selectedBooking.guestPhone }}</span>
                </div>
              </div>
              <div class="space-y-2">
                <div class="flex justify-between">
                  <span class="text-gray-600">身份证号：</span>
                  <span class="text-gray-900">{{ selectedBooking.guestIdCard }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">特殊要求：</span>
                  <span class="text-gray-900">{{ selectedBooking.specialRequests || '无' }}</span>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 订单信息 -->
          <div class="bg-gray-50 rounded-lg p-4">
            <h3 class="font-semibold text-gray-900 mb-3">订单信息</h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div class="space-y-2">
                <div class="flex justify-between">
                  <span class="text-gray-600">订单号：</span>
                  <span class="text-gray-900">#{{ selectedBooking.id }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">订单状态：</span>
                  <span :class="getStatusTextColor(selectedBooking.status)">
                    {{ getStatusText(selectedBooking.status) }}
                  </span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">预订时间：</span>
                  <span class="text-gray-900">{{ formatDateTime(selectedBooking.bookingTime) }}</span>
                </div>
              </div>
              <div class="space-y-2">
                <div class="flex justify-between">
                  <span class="text-gray-600">入住日期：</span>
                  <span class="text-gray-900">{{ formatDate(selectedBooking.checkInDate) }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">退房日期：</span>
                  <span class="text-gray-900">{{ formatDate(selectedBooking.checkOutDate) }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">入住天数：</span>
                  <span class="text-gray-900">{{ selectedBooking.nights }} 晚</span>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 费用明细 -->
          <div class="bg-gray-50 rounded-lg p-4">
            <h3 class="font-semibold text-gray-900 mb-3">费用明细</h3>
            <div class="space-y-2">
              <div class="flex justify-between">
                <span class="text-gray-600">房间费用：</span>
                <span class="text-gray-900">¥{{ Math.floor(selectedBooking.totalAmount / selectedBooking.nights) }} × {{ selectedBooking.nights }} 晚</span>
              </div>
              <div class="border-t border-gray-200 pt-2 mt-2">
                <div class="flex justify-between font-semibold">
                  <span class="text-gray-900">总计：</span>
                  <span class="text-primary-600 text-lg">¥{{ selectedBooking.totalAmount }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="flex justify-end space-x-4 mt-8">
          <button 
            @click="showDetailModal = false"
            class="px-6 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors"
          >
            关闭
          </button>
          <button 
            v-if="selectedBooking.status === 'confirmed'"
            @click="cancelBooking(selectedBooking)"
            class="px-6 py-2 bg-red-600 hover:bg-red-700 text-white rounded-lg transition-colors"
          >
            取消订单
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useBookingStore, useHotelStore } from '../stores'
import { formatDate, formatDateTime } from '../utils'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const bookingStore = useBookingStore()
const hotelStore = useHotelStore()

// 状态筛选
const selectedStatus = ref('')
const statusFilters = [
  { value: '', label: '全部订单' },
  { value: 'pending', label: '待确认' },
  { value: 'confirmed', label: '已确认' },
  { value: 'completed', label: '已完成' },
  { value: 'cancelled', label: '已取消' }
]

// 模态框状态
const showDetailModal = ref(false)
const selectedBooking = ref(null)

// 获取订单列表
const fetchBookings = async () => {
  const params = {}
  if (selectedStatus.value) {
    params.status = selectedStatus.value
  }
  await bookingStore.fetchBookings(params)
}

// 监听状态筛选变化，重新请求数据
watch(selectedStatus, () => {
  fetchBookings()
})

// 方法 - 状态数量暂时使用当前列表数据（后续可改为后端返回统计）
const getStatusCount = (status) => {
  if (!status) {
    return bookingStore.totalCount || bookingStore.bookings.length
  }
  // 暂时返回 '-'，后续可改为后端返回各状态统计
  return '-'
}

const getStatusBadgeClass = (status) => {
  const classes = {
    pending: 'bg-yellow-100 text-yellow-800',
    confirmed: 'bg-green-100 text-green-800',
    completed: 'bg-blue-100 text-blue-800',
    cancelled: 'bg-red-100 text-red-800'
  }
  return classes[status] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status) => {
  const texts = {
    pending: '待确认',
    confirmed: '已确认',
    completed: '已完成',
    cancelled: '已取消'
  }
  return texts[status] || status
}

const getStatusTextColor = (status) => {
  const colors = {
    pending: 'text-yellow-800',
    confirmed: 'text-green-800',
    completed: 'text-blue-800',
    cancelled: 'text-red-800'
  }
  return colors[status] || 'text-gray-800'
}

const getHotelAddress = (hotelId) => {
  // 这里应该从hotelStore获取酒店地址
  // 为了演示，使用假数据
  const addresses = {
    1: '北京市朝阳区建国门外大街1号',
    2: '上海市浦东新区陆家嘴金融贸易区',
    3: '广州市越秀区环市东路368号'
  }
  return addresses[hotelId] || '地址信息未找到'
}

const viewBookingDetail = (booking) => {
  selectedBooking.value = booking
  showDetailModal.value = true
}

const cancelBooking = async (booking) => {
  try {
    await ElMessageBox.confirm('确定要取消这个订单吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await bookingStore.cancelBooking(booking.id)
    ElMessage.success('订单取消成功')
    showDetailModal.value = false
  } catch (error) {
    if (error !== 'cancel') {
      console.error('取消订单失败:', error)
      ElMessage.error(error.response?.data?.message || error.message || '取消订单失败，请重试')
    }
  }
}

onMounted(() => {
  fetchBookings()
})
</script>
