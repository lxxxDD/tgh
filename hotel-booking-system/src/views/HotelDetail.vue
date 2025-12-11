<template>
  <div class="hotel-detail">
    <!-- 加载状态 -->
    <LoadingSpinner v-if="hotelStore.loading" />

    <!-- 酒店详情内容 -->
    <div v-else-if="hotel" class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- 返回按钮 -->
      <div class="mb-6">
        <button 
          @click="goBack"
          class="flex items-center text-gray-600 hover:text-primary-600 transition-colors"
        >
          <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7"></path>
          </svg>
          返回列表
        </button>
      </div>

      <!-- 酒店基本信息 -->
      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8 mb-8">
        <!-- 酒店图片 -->
        <div class="lg:col-span-2">
          <div class="relative">
            <img 
              :src="hotel.images[0]" 
              :alt="hotel.name"
              loading="lazy"
              decoding="async"
              class="w-full h-96 object-cover rounded-xl"
            >
            <div class="absolute top-4 right-4">
              <button 
                @click="toggleFavorite"
                class="bg-white bg-opacity-90 p-2 rounded-full shadow-lg hover:bg-opacity-100 transition-all"
                :title="isFavorite ? '取消收藏' : '收藏酒店'"
              >
                <svg class="w-6 h-6" :class="isFavorite ? 'text-red-500' : 'text-gray-700'" :fill="isFavorite ? 'currentColor' : 'none'" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
                </svg>
              </button>
            </div>
          </div>
        </div>

        <!-- 酒店信息 -->
        <div>
          <div class="card">
            <h1 class="text-3xl font-bold text-gray-900 mb-4">{{ hotel.name }}</h1>
            
            <div class="flex items-center mb-4">
              <div class="flex items-center mr-4">
                <span class="text-yellow-400 text-xl mr-2">
                  {{ '★'.repeat(hotel.starRating) }}
                </span>
                <span class="text-gray-600">{{ hotel.starRating }}星级酒店</span>
              </div>
            </div>
            
            <div class="flex items-center mb-4">
              <span class="text-yellow-400 text-xl mr-2">★</span>
              <span class="text-lg font-semibold text-gray-900 mr-2">{{ hotel.avgRating }}</span>
              <span class="text-gray-600">({{ hotel.reviewCount }}条评价)</span>
            </div>
            
            <div class="flex items-center text-gray-600 mb-4">
              <svg class="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
              </svg>
              {{ hotel.address }}
            </div>
            
            <div class="border-t border-gray-200 pt-4">
              <div class="flex justify-between items-center mb-4">
                <div>
                  <span class="text-3xl font-bold text-primary-600">¥{{ hotel.minPrice }}</span>
                  <span class="text-gray-500 ml-1">起/晚</span>
                </div>
              </div>
              
              <button 
                @click="scrollToRooms"
                class="w-full btn-accent text-lg font-semibold py-3"
              >
                查看房间
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- 酒店详情标签页 -->
      <div class="card mb-8">
        <div class="border-b border-gray-200 mb-6">
          <nav class="flex space-x-8">
            <button 
              v-for="tab in tabs" 
              :key="tab.id"
              @click="activeTab = tab.id"
              :class="[
                'py-4 px-1 border-b-2 font-medium text-sm transition-colors',
                activeTab === tab.id
                  ? 'border-primary-600 text-primary-600'
                  : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
              ]"
            >
              {{ tab.name }}
            </button>
          </nav>
        </div>

        <!-- 概览标签 -->
        <div v-if="activeTab === 'overview'">
          <div class="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div>
              <h3 class="text-lg font-semibold text-gray-900 mb-4">酒店介绍</h3>
              <p class="text-gray-600 leading-relaxed">{{ hotel.description }}</p>
              
              <h3 class="text-lg font-semibold text-gray-900 mb-4 mt-6">基本信息</h3>
              <div class="space-y-3">
                <div class="flex justify-between">
                  <span class="text-gray-600">入住时间：</span>
                  <span class="text-gray-900">{{ hotel.checkInTime }}后</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">退房时间：</span>
                  <span class="text-gray-900">{{ hotel.checkOutTime }}前</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">联系电话：</span>
                  <span class="text-gray-900">{{ hotel.phone }}</span>
                </div>
                <div class="flex justify-between">
                  <span class="text-gray-600">邮箱：</span>
                  <span class="text-gray-900">{{ hotel.email }}</span>
                </div>
              </div>
            </div>
            
            <div>
              <h3 class="text-lg font-semibold text-gray-900 mb-4">设施服务</h3>
              <div class="grid grid-cols-2 gap-4">
                <div 
                  v-for="facility in hotel.facilities" 
                  :key="facility"
                  class="flex items-center p-3 bg-gray-50 rounded-lg"
                >
                  <div class="w-8 h-8 bg-primary-100 rounded-full flex items-center justify-center mr-3">
                    <svg class="w-4 h-4 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                    </svg>
                  </div>
                  <span class="text-gray-700">{{ getFacilityName(facility) }}</span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 房间类型标签 -->
        <div v-else-if="activeTab === 'rooms'" id="rooms-section">
          <h3 class="text-lg font-semibold text-gray-900 mb-6">房间类型</h3>
          <div class="space-y-6">
            <div 
              v-for="room in rooms" 
              :key="room.id"
              class="border border-gray-200 rounded-lg p-6 hover:shadow-md transition-shadow"
            >
              <div class="flex flex-col md:flex-row gap-6">
                <div class="md:w-1/3">
                  <img 
                    :src="room.images?.[0] || 'https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=600&h=400&fit=crop'" 
                    :alt="room.roomType"
                    loading="lazy"
                    decoding="async"
                    class="w-full h-48 md:h-32 object-cover rounded-lg"
                  >
                </div>
                
                <div class="md:w-2/3">
                  <div class="flex justify-between items-start mb-3">
                    <h4 class="text-xl font-semibold text-gray-900">{{ room.roomType }}</h4>
                    <div class="text-right">
                      <div class="text-2xl font-bold text-primary-600">¥{{ room.price }}</div>
                      <div class="text-sm text-gray-500">
                        <span v-if="room.originalPrice > room.price" class="line-through">
                          ¥{{ room.originalPrice }}
                        </span>
                      </div>
                    </div>
                  </div>
                  
                  <div class="grid grid-cols-2 gap-4 mb-4">
                    <div class="text-sm text-gray-600">
                      <span class="font-medium">面积：</span>{{ room.area }}㎡
                    </div>
                    <div class="text-sm text-gray-600">
                      <span class="font-medium">床型：</span>{{ room.bedType }}
                    </div>
                    <div class="text-sm text-gray-600">
                      <span class="font-medium">可住：</span>{{ room.maxOccupancy }}人
                    </div>
                    <div class="text-sm text-gray-600">
                      <span class="font-medium">状态：</span>
                      <span :class="room.availability ? 'text-green-600' : 'text-red-600'">
                        {{ room.availability ? '可预订' : '已满房' }}
                      </span>
                    </div>
                  </div>
                  
                  <div class="mb-4" v-if="room.amenities?.length">
                    <h5 class="text-sm font-medium text-gray-700 mb-2">房间设施：</h5>
                    <div class="flex flex-wrap gap-2">
                      <span 
                        v-for="amenity in room.amenities" 
                        :key="amenity"
                        class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded-full"
                      >
                        {{ amenity }}
                      </span>
                    </div>
                  </div>
                  
                  <div class="flex justify-between items-center">
                    <div class="text-sm text-gray-600">
                      剩余 {{ room.stock || 0 }} 间房
                    </div>
                    <button 
                      @click="bookRoom(room)"
                      :disabled="!room.availability"
                      :class="[
                        'px-6 py-2 rounded-lg font-medium transition-colors',
                        room.availability
                          ? 'bg-accent-500 hover:bg-accent-600 text-white'
                          : 'bg-gray-300 text-gray-500 cursor-not-allowed'
                      ]"
                    >
                      {{ room.availability ? '立即预订' : '已满房' }}
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 用户评价标签 -->
        <div v-else-if="activeTab === 'reviews'">
          <h3 class="text-lg font-semibold text-gray-900 mb-6">用户评价</h3>
          <div class="space-y-6">
            <div 
              v-for="review in reviews" 
              :key="review.id"
              class="border-b border-gray-200 pb-6 last:border-b-0"
            >
              <div class="flex items-start space-x-4">
                <div class="w-12 h-12 bg-gray-300 rounded-full flex items-center justify-center">
                  <span class="text-lg font-semibold text-gray-700">
                    {{ (review.userName || '匿').charAt(0) }}
                  </span>
                </div>
                
                <div class="flex-1">
                  <div class="flex items-center justify-between mb-2">
                    <div>
                      <h4 class="font-semibold text-gray-900">{{ review.userName || '匿名用户' }}</h4>
                      <div class="flex items-center mt-1">
                        <div class="flex items-center mr-2">
                          <span class="text-yellow-400 text-sm">
                            {{ '★'.repeat(review.rating) }}
                          </span>
                        </div>
                        <span class="text-sm text-gray-500">{{ review.reviewDate }}</span>
                      </div>
                    </div>
                  </div>
                  
                  <p class="text-gray-600 leading-relaxed">{{ review.content }}</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 位置地图标签 -->
        <div v-else-if="activeTab === 'location'">
          <h3 class="text-lg font-semibold text-gray-900 mb-6">酒店位置</h3>
          <div class="bg-gray-100 rounded-lg h-96 flex items-center justify-center">
            <div class="text-center">
              <svg class="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
              </svg>
              <p class="text-gray-600 mb-2">酒店位置地图</p>
              <p class="text-sm text-gray-500">经度: {{ hotel.longitude }}, 纬度: {{ hotel.latitude }}</p>
            </div>
          </div>
          
          <div class="mt-6">
            <h4 class="font-semibold text-gray-900 mb-3">交通信息</h4>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div class="flex items-center p-3 bg-gray-50 rounded-lg">
                <svg class="w-5 h-5 text-primary-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                </svg>
                <div>
                  <div class="font-medium text-gray-900">机场距离</div>
                  <div class="text-sm text-gray-600">约30分钟车程</div>
                </div>
              </div>
              
              <div class="flex items-center p-3 bg-gray-50 rounded-lg">
                <svg class="w-5 h-5 text-primary-600 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"></path>
                </svg>
                <div>
                  <div class="font-medium text-gray-900">火车站距离</div>
                  <div class="text-sm text-gray-600">约15分钟车程</div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 预订模态框 -->
    <div v-if="showBookingModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-lg p-8 max-w-2xl w-full mx-4 max-h-screen overflow-y-auto">
        <div class="flex justify-between items-center mb-6">
          <h2 class="text-2xl font-bold text-gray-900">预订房间</h2>
          <button 
            @click="showBookingModal = false"
            class="text-gray-400 hover:text-gray-600"
          >
            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
            </svg>
          </button>
        </div>
        
        <form @submit.prevent="submitBooking">
          <!-- 房间信息 -->
          <div class="bg-gray-50 rounded-lg p-4 mb-6">
            <h3 class="font-semibold text-gray-900 mb-2">{{ selectedRoom?.roomType }}</h3>
            <div class="flex justify-between items-center">
              <span class="text-gray-600">价格：</span>
              <span class="text-xl font-bold text-primary-600">¥{{ selectedRoom?.price }}/晚</span>
            </div>
          </div>
          
          <!-- 入住信息 -->
          <div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">入住日期</label>
              <el-date-picker
                v-model="bookingForm.checkInDate"
                type="date"
                placeholder="选择入住日期"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                :disabled-date="disabledCheckInDate"
                class="!w-full !h-[42px]"
              />
              <p class="text-xs text-gray-500 mt-1">入住时间：14:00后</p>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">退房日期</label>
              <el-date-picker
                v-model="bookingForm.checkOutDate"
                type="date"
                placeholder="选择退房日期"
                format="YYYY-MM-DD"
                value-format="YYYY-MM-DD"
                :disabled-date="disabledCheckOutDate"
                class="!w-full !h-[42px]"
              />
              <p class="text-xs text-gray-500 mt-1">退房时间：14:00前</p>
            </div>
          </div>
          
          <!-- 客人信息 -->
          <div class="space-y-4 mb-6">
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">入住人姓名</label>
              <input 
                v-model="bookingForm.guestName" 
                type="text" 
                class="input-field"
                placeholder="请输入入住人真实姓名"
                required
              >
            </div>
            
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">联系电话</label>
                <input 
                  v-model="bookingForm.guestPhone" 
                  type="tel" 
                  class="input-field"
                  placeholder="请输入手机号码"
                  required
                >
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">身份证号</label>
                <input 
                  v-model="bookingForm.guestIdCard" 
                  type="text" 
                  class="input-field"
                  placeholder="请输入身份证号码"
                  required
                >
              </div>
            </div>
            
            <div>
              <label class="block text-sm font-medium text-gray-700 mb-2">特殊要求</label>
              <textarea 
                v-model="bookingForm.specialRequests" 
                rows="3" 
                class="input-field"
                placeholder="如有特殊要求请在此说明（选填）"
              ></textarea>
            </div>
          </div>
          
          <!-- 订单摘要 -->
          <div class="bg-gray-50 rounded-lg p-4 mb-6">
            <h3 class="font-semibold text-gray-900 mb-3">订单摘要</h3>
            <div class="space-y-2 text-sm">
              <div class="flex justify-between">
                <span class="text-gray-600">入住天数：</span>
                <span class="text-gray-900">{{ calculateNights(bookingForm.checkInDate, bookingForm.checkOutDate) }} 晚</span>
              </div>
              <div class="flex justify-between">
                <span class="text-gray-600">房间单价：</span>
                <span class="text-gray-900">¥{{ selectedRoom?.price }}</span>
              </div>
              <div class="border-t border-gray-200 pt-2 mt-2">
                <div class="flex justify-between font-semibold">
                  <span class="text-gray-900">总计：</span>
                  <span class="text-primary-600">
                    ¥{{ calculateTotalAmount() }}
                  </span>
                </div>
              </div>
            </div>
          </div>
          
          <div class="flex space-x-4">
            <button 
              type="button"
              @click="showBookingModal = false"
              class="flex-1 px-4 py-3 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors"
            >
              取消
            </button>
            <button 
              type="submit"
              class="flex-1 btn-accent text-lg font-semibold py-3"
            >
              确认预订
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useHotelStore, useBookingStore, useUserStore } from '../stores'
import { calculateNights } from '../utils'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import { ElMessage } from 'element-plus'
import { checkFavorite, addFavorite, removeFavorite } from '../api/favorite'
import { getHotelReviews } from '../api/review'

const route = useRoute()
const router = useRouter()
const hotelStore = useHotelStore()
const bookingStore = useBookingStore()
const userStore = useUserStore()

const hotel = computed(() => hotelStore.currentHotel)
const today = new Date().toISOString().split('T')[0]

// 标签页配置
const tabs = [
  { id: 'overview', name: '酒店概览' },
  { id: 'rooms', name: '房间类型' },
  { id: 'reviews', name: '用户评价' },
  { id: 'location', name: '位置信息' }
]

const activeTab = ref('overview')
const showBookingModal = ref(false)
const selectedRoom = ref(null)
const isFavorite = ref(false)

// 预订表单
const bookingForm = ref({
  checkInDate: '',
  checkOutDate: '',
  guestName: '',
  guestPhone: '',
  guestIdCard: '',
  specialRequests: ''
})

// 房间列表（从后端获取）
const rooms = computed(() => hotelStore.currentRooms || [])

// 评价数据
const reviews = ref([])

// 方法
const goBack = () => {
  router.push('/hotels')
}

const scrollToRooms = () => {
  activeTab.value = 'rooms'
  const element = document.getElementById('rooms-section')
  if (element) {
    element.scrollIntoView({ behavior: 'smooth' })
  }
}

// 获取今天日期字符串 (YYYY-MM-DD)
const getTodayStr = () => {
  const today = new Date()
  return today.toISOString().split('T')[0]
}

// 获取明天日期字符串
const getTomorrowStr = () => {
  const tomorrow = new Date()
  tomorrow.setDate(tomorrow.getDate() + 1)
  return tomorrow.toISOString().split('T')[0]
}

// 获取指定日期+1天
const getNextDayStr = (dateStr) => {
  const date = new Date(dateStr)
  date.setDate(date.getDate() + 1)
  return date.toISOString().split('T')[0]
}

// 禁用入住日期（今天之前不可选）
const disabledCheckInDate = (date) => {
  const today = new Date()
  today.setHours(0, 0, 0, 0)
  return date.getTime() < today.getTime()
}

// 禁用退房日期（入住日期之前不可选）
const disabledCheckOutDate = (date) => {
  if (!bookingForm.value.checkInDate) {
    const today = new Date()
    today.setHours(0, 0, 0, 0)
    return date.getTime() < today.getTime()
  }
  const checkIn = new Date(bookingForm.value.checkInDate)
  checkIn.setHours(0, 0, 0, 0)
  return date.getTime() <= checkIn.getTime()
}

const bookRoom = (room) => {
  if (!room.availability) return
  
  // 检查是否登录
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录后再预订')
    router.push({ name: 'Login', query: { redirect: route.fullPath } })
    return
  }
  
  selectedRoom.value = room
  showBookingModal.value = true
  
  // 自动填入用户信息，默认入住=今天，退房=明天
  const user = userStore.user
  bookingForm.value = {
    checkInDate: getTodayStr(),
    checkOutDate: getTomorrowStr(),
    guestName: user?.realName || '',
    guestPhone: user?.phone || '',
    guestIdCard: user?.idCard || '',
    specialRequests: ''
  }
}

// 监听入住日期变化，自动设置退房日期为入住+1天
watch(() => bookingForm.value.checkInDate, (newVal) => {
  if (newVal) {
    const nextDay = getNextDayStr(newVal)
    // 如果退房日期 <= 入住日期，自动设置为入住+1天
    if (!bookingForm.value.checkOutDate || bookingForm.value.checkOutDate <= newVal) {
      bookingForm.value.checkOutDate = nextDay
    }
  }
})

const submitBooking = async () => {
  // 前端验证
  const todayStr = getTodayStr()
  const checkInDate = bookingForm.value.checkInDate
  const checkOutDate = bookingForm.value.checkOutDate
  
  if (!checkInDate) {
    ElMessage.error('请选择入住日期')
    return
  }
  if (!checkOutDate) {
    ElMessage.error('请选择退房日期')
    return
  }
  if (checkInDate < todayStr) {
    ElMessage.error('入住日期不能是过去日期')
    return
  }
  if (checkOutDate <= checkInDate) {
    ElMessage.error('退房日期必须大于入住日期')
    return
  }
  if (!selectedRoom.value?.id) {
    ElMessage.error('请选择房间类型')
    return
  }
  
  try {
    const bookingData = {
      hotelId: hotel.value.id,
      roomTypeId: selectedRoom.value.id,  // 后端需要 roomTypeId
      hotelName: hotel.value.name,
      roomType: selectedRoom.value.roomType,
      ...bookingForm.value,
      totalAmount: calculateTotalAmount()
    }
    
    await bookingStore.createBooking(bookingData)
    
    ElMessage.success('预订成功！')
    showBookingModal.value = false
    router.push('/bookings')
  } catch (error) {
    console.error('预订失败:', error)
    ElMessage.error(error.message || '预订失败，请重试')
  }
}

const calculateTotalAmount = () => {
  if (!selectedRoom.value || !bookingForm.value.checkInDate || !bookingForm.value.checkOutDate) {
    return 0
  }
  
  const nights = calculateNights(bookingForm.value.checkInDate, bookingForm.value.checkOutDate)
  return selectedRoom.value.price * nights
}

const getFacilityName = (facility) => {
  const names = {
    wifi: '免费WiFi',
    parking: '停车场',
    gym: '健身房',
    pool: '游泳池',
    restaurant: '餐厅',
    spa: 'SPA',
    bar: '酒吧',
    garden: '花园'
  }
  return names[facility] || facility
}

// 加载收藏状态
const loadFavoriteStatus = async () => {
  if (!userStore.isLoggedIn) return
  try {
    const hotelId = route.params.id
    const result = await checkFavorite(hotelId)
    isFavorite.value = result
  } catch (error) {
    console.error('获取收藏状态失败:', error)
  }
}

// 切换收藏状态
const toggleFavorite = async () => {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录后再收藏')
    router.push({ name: 'Login', query: { redirect: route.fullPath } })
    return
  }
  
  try {
    const hotelId = route.params.id
    if (isFavorite.value) {
      await removeFavorite(hotelId)
      isFavorite.value = false
      ElMessage.success('已取消收藏')
    } else {
      await addFavorite(hotelId)
      isFavorite.value = true
      ElMessage.success('收藏成功')
    }
  } catch (error) {
    ElMessage.error(error.message || '操作失败')
  }
}

onMounted(async () => {
  const hotelId = route.params.id
  await hotelStore.fetchHotelDetail(hotelId)
  await hotelStore.fetchHotelRooms(hotelId)
  await loadFavoriteStatus()
  
  // 获取酒店评价
  try {
    const res = await getHotelReviews(hotelId, { page: 1, size: 10 })
    reviews.value = res?.records || []
  } catch (e) {
    console.error('获取评价失败:', e)
  }
})
</script>

<style scoped>
.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
