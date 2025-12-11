<template>
  <div class="bookings bg-gray-50 min-h-screen py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- 页面标题 -->
      <div class="flex justify-between items-end mb-8">
        <div>
          <h1 class="text-3xl font-bold text-gray-900">我的订单</h1>
          <p class="text-gray-500 mt-2">查看并管理您的所有酒店预订</p>
        </div>
        <button @click="fetchBookings" class="text-primary-600 hover:text-primary-700 font-medium text-sm flex items-center">
          <svg class="w-4 h-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
          </svg>
          刷新列表
        </button>
      </div>

      <!-- 状态筛选 Tab -->
      <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-2 mb-8 flex flex-wrap gap-2 overflow-x-auto">
        <button 
          v-for="status in statusFilters" 
          :key="status.value"
          @click="selectedStatus = status.value"
          :class="[
            'px-4 py-2 rounded-lg font-medium text-sm transition-all whitespace-nowrap',
            selectedStatus === status.value
              ? 'bg-primary-50 text-primary-700 shadow-sm ring-1 ring-primary-200'
              : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900'
          ]"
        >
          {{ status.label }}
        </button>
      </div>

      <!-- 加载状态 -->
      <LoadingSpinner v-if="bookingStore.loading" text="正在加载订单..." />

      <!-- 订单列表 -->
      <div v-else-if="filteredBookings.length > 0" class="space-y-6">
        <div 
          v-for="booking in filteredBookings" 
          :key="booking.id"
          class="bg-white rounded-xl shadow-sm border border-gray-200 overflow-hidden hover:shadow-md transition-shadow duration-300"
        >
          <div class="p-6">
            <div class="flex flex-col lg:flex-row lg:items-start justify-between gap-6">
              <!-- 左侧：酒店信息 -->
              <div class="flex-1">
                <div class="flex items-start justify-between mb-4 lg:hidden">
                  <span 
                    :class="[
                      'px-3 py-1 rounded-full text-xs font-semibold uppercase tracking-wide',
                      getStatusBadgeClass(booking.status)
                    ]"
                  >
                    {{ getStatusText(booking.status) }}
                  </span>
                  <span class="text-lg font-bold text-primary-600">¥{{ booking.totalAmount }}</span>
                </div>

                <h3 class="text-xl font-bold text-gray-900 mb-2 hover:text-primary-600 cursor-pointer transition-colors" @click="viewBookingDetail(booking)">
                  {{ booking.hotelName }}
                </h3>
                
                <div class="flex items-center text-gray-500 text-sm mb-4">
                  <svg class="w-4 h-4 mr-1.5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                  </svg>
                  <span>{{ booking.hotelAddress || '地址待查询' }}</span>
                </div>

                <div class="grid grid-cols-2 md:grid-cols-4 gap-4 p-4 bg-gray-50 rounded-lg">
                  <div>
                    <p class="text-xs text-gray-500 mb-1">入住日期</p>
                    <p class="font-semibold text-gray-900">{{ formatDate(booking.checkInDate) }}</p>
                  </div>
                  <div>
                    <p class="text-xs text-gray-500 mb-1">退房日期</p>
                    <p class="font-semibold text-gray-900">{{ formatDate(booking.checkOutDate) }}</p>
                  </div>
                  <div>
                    <p class="text-xs text-gray-500 mb-1">房型/晚数</p>
                    <p class="font-semibold text-gray-900">{{ booking.roomType }} / {{ booking.nights }}晚</p>
                  </div>
                  <div>
                    <p class="text-xs text-gray-500 mb-1">入住人</p>
                    <p class="font-semibold text-gray-900">{{ booking.guestName }}</p>
                  </div>
                </div>
              </div>

              <!-- 右侧：状态与操作 (Desktop) -->
              <div class="hidden lg:flex flex-col items-end justify-between min-h-[140px]">
                <div class="text-right">
                  <span 
                    :class="[
                      'px-3 py-1 rounded-full text-xs font-semibold uppercase tracking-wide',
                      getStatusBadgeClass(booking.status)
                    ]"
                  >
                    {{ getStatusText(booking.status) }}
                  </span>
                  <div class="mt-2 text-2xl font-bold text-primary-600">¥{{ booking.totalAmount }}</div>
                </div>
                
                <div class="flex space-x-3 mt-4">
                  <button 
                    v-if="booking.status === 'confirmed'"
                    @click="cancelBooking(booking)"
                    class="px-4 py-2 text-sm text-red-600 hover:bg-red-50 rounded-lg transition-colors font-medium"
                  >
                    取消订单
                  </button>
                  <button 
                    @click="viewBookingDetail(booking)"
                    class="px-5 py-2 text-sm bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 rounded-lg transition-colors font-medium shadow-sm"
                  >
                    查看详情
                  </button>
                </div>
              </div>

              <!-- 移动端操作栏 -->
              <div class="flex justify-end space-x-3 mt-4 lg:hidden border-t border-gray-100 pt-4">
                <button 
                  v-if="booking.status === 'confirmed'"
                  @click="cancelBooking(booking)"
                  class="px-4 py-2 text-sm text-red-600 hover:bg-red-50 rounded-lg transition-colors font-medium"
                >
                  取消
                </button>
                <button 
                  @click="viewBookingDetail(booking)"
                  class="px-5 py-2 text-sm bg-white border border-gray-300 hover:bg-gray-50 text-gray-700 rounded-lg transition-colors font-medium shadow-sm"
                >
                  详情
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 空状态 -->
      <div v-else class="text-center py-20 bg-white rounded-xl border border-gray-200 shadow-sm">
        <div class="w-20 h-20 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-6">
          <svg class="w-10 h-10 text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
          </svg>
        </div>
        <h3 class="text-xl font-bold text-gray-900 mb-2">暂无{{ getStatusLabel(selectedStatus) }}订单</h3>
        <p class="text-gray-500 mb-8">开启您的下一段旅程吧</p>
        <router-link to="/hotels" class="btn-primary inline-flex items-center">
          <svg class="w-5 h-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
          </svg>
          去预订酒店
        </router-link>
      </div>
    </div>

    <!-- 订单详情模态框 -->
    <div v-if="showDetailModal && selectedBooking" class="fixed inset-0 bg-black/60 backdrop-blur-sm flex items-center justify-center z-50 p-4">
      <div class="bg-white rounded-2xl w-full max-w-2xl max-h-[90vh] overflow-y-auto shadow-2xl animate-fade-in-up">
        <div class="sticky top-0 bg-white px-6 py-4 border-b border-gray-100 flex justify-between items-center z-10">
          <h2 class="text-xl font-bold text-gray-900">订单详情</h2>
          <button @click="showDetailModal = false" class="p-2 hover:bg-gray-100 rounded-full transition-colors text-gray-500">
            <svg class="w-6 h-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
        
        <div class="p-6 space-y-8">
          <!-- 核心信息 -->
          <div class="flex flex-col sm:flex-row justify-between items-start gap-4 pb-6 border-b border-gray-100">
            <div>
              <p class="text-sm text-gray-500 mb-1">订单号 #{{ selectedBooking.id }}</p>
              <h3 class="text-2xl font-bold text-gray-900 mb-2">{{ selectedBooking.hotelName }}</h3>
              <span :class="['px-3 py-1 rounded-full text-sm font-medium', getStatusBadgeClass(selectedBooking.status)]">
                {{ getStatusText(selectedBooking.status) }}
              </span>
            </div>
            <div class="text-right">
              <p class="text-sm text-gray-500 mb-1">订单总额</p>
              <p class="text-3xl font-bold text-primary-600">¥{{ selectedBooking.totalAmount }}</p>
            </div>
          </div>

          <!-- 详细信息网格 -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-x-8 gap-y-6">
            <div class="space-y-4">
              <h4 class="font-bold text-gray-900 border-l-4 border-primary-500 pl-3">预订信息</h4>
              <div class="space-y-3 text-sm">
                <div class="flex justify-between"><span class="text-gray-500">房间类型</span><span class="font-medium">{{ selectedBooking.roomType }}</span></div>
                <div class="flex justify-between"><span class="text-gray-500">入住日期</span><span class="font-medium">{{ formatDate(selectedBooking.checkInDate) }}</span></div>
                <div class="flex justify-between"><span class="text-gray-500">退房日期</span><span class="font-medium">{{ formatDate(selectedBooking.checkOutDate) }}</span></div>
                <div class="flex justify-between"><span class="text-gray-500">入住天数</span><span class="font-medium">{{ selectedBooking.nights }} 晚</span></div>
              </div>
            </div>

            <div class="space-y-4">
              <h4 class="font-bold text-gray-900 border-l-4 border-primary-500 pl-3">入住人信息</h4>
              <div class="space-y-3 text-sm">
                <div class="flex justify-between"><span class="text-gray-500">姓名</span><span class="font-medium">{{ selectedBooking.guestName }}</span></div>
                <div class="flex justify-between"><span class="text-gray-500">电话</span><span class="font-medium">{{ selectedBooking.guestPhone }}</span></div>
                <div class="flex justify-between"><span class="text-gray-500">身份证</span><span class="font-medium">{{ selectedBooking.guestIdCard }}</span></div>
                <div class="flex flex-col mt-2">
                  <span class="text-gray-500 mb-1">特殊要求</span>
                  <span class="font-medium bg-gray-50 p-2 rounded text-gray-700">{{ selectedBooking.specialRequests || '无' }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        
        <div class="bg-gray-50 px-6 py-4 flex justify-end gap-3 border-t border-gray-200">
          <button @click="showDetailModal = false" class="px-5 py-2.5 border border-gray-300 rounded-lg text-gray-700 hover:bg-white transition-colors font-medium">
            关闭
          </button>
          <button 
            v-if="selectedBooking.status === 'confirmed'"
            @click="cancelBooking(selectedBooking)"
            class="px-5 py-2.5 bg-red-600 hover:bg-red-700 text-white rounded-lg transition-colors font-medium shadow-sm"
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
import { useBookingStore } from '../stores'
import { formatDate } from '../utils'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const bookingStore = useBookingStore()
const selectedStatus = ref('')
const showDetailModal = ref(false)
const selectedBooking = ref(null)

const statusFilters = [
  { value: '', label: '全部' },
  { value: 'pending', label: '待确认' },
  { value: 'confirmed', label: '已确认' },
  { value: 'completed', label: '已完成' },
  { value: 'cancelled', label: '已取消' }
]

// 过滤逻辑
const filteredBookings = computed(() => {
  if (!selectedStatus.value) return bookingStore.bookings
  return bookingStore.bookings.filter(b => b.status === selectedStatus.value)
})

const fetchBookings = async () => {
  await bookingStore.fetchBookings()
}

// Helper methods
const getStatusLabel = (val) => {
  const found = statusFilters.find(s => s.value === val)
  return found && found.value ? found.label : ''
}

const getStatusBadgeClass = (status) => {
  const map = {
    pending: 'bg-yellow-100 text-yellow-800',
    confirmed: 'bg-green-100 text-green-700',
    completed: 'bg-blue-50 text-blue-700',
    cancelled: 'bg-gray-100 text-gray-500'
  }
  return map[status] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status) => {
  const map = { pending: '待确认', confirmed: '已确认', completed: '已完成', cancelled: '已取消' }
  return map[status] || status
}


const viewBookingDetail = (booking) => {
  selectedBooking.value = booking
  showDetailModal.value = true
}

const cancelBooking = async (booking) => {
  try {
    await ElMessageBox.confirm('确定要取消这个订单吗？取消后无法恢复。', '取消确认', {
      confirmButtonText: '确定取消',
      cancelButtonText: '暂不取消',
      confirmButtonClass: 'el-button--danger',
      type: 'warning'
    })
    await bookingStore.cancelBooking(booking.id)
    ElMessage.success('订单已成功取消')
    showDetailModal.value = false
  } catch (error) {
    if (error !== 'cancel') ElMessage.error(error.message || '操作失败，请重试')
  }
}

onMounted(() => fetchBookings())
</script>

<style scoped>
.animate-fade-in-up {
  animation: fadeInUp 0.3s ease-out forwards;
}
@keyframes fadeInUp {
  from { opacity: 0; transform: scale(0.95); }
  to { opacity: 1; transform: scale(1); }
}
</style>