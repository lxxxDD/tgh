<template>
  <div class="home">
    <!-- Hero区域 -->
    <section class="relative bg-gradient-to-br from-primary-900 via-primary-800 to-primary-700 text-white">
      <div class="absolute inset-0 bg-black bg-opacity-20"></div>
      <div class="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24 lg:py-32">
        <div class="text-center mb-12">
          <h1 class="text-4xl md:text-6xl font-bold mb-6 animate-on-scroll">
            发现完美住宿体验
          </h1>
          <p class="text-xl md:text-2xl text-blue-100 mb-8 animate-on-scroll">
            在全球数千家酒店中找到您的理想之选
          </p>
        </div>
        
        <!-- 搜索表单 -->
        <div class="max-w-4xl mx-auto">
          <div class="bg-white rounded-2xl shadow-2xl p-8 animate-on-scroll">
            <form @submit.prevent="searchHotels" class="grid grid-cols-1 md:grid-cols-4 gap-4">
              <div class="md:col-span-1">
                <label class="block text-sm font-medium text-gray-700 mb-2">目的地</label>
                <input 
                  v-model="searchForm.destination" 
                  type="text" 
                  placeholder="城市或酒店名称"
                  class="input-field"
                >
              </div>
              
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">入住日期</label>
                <el-date-picker
                  v-model="searchForm.checkIn"
                  type="date"
                  placeholder="选择入住日期"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
                  :disabled-date="disabledCheckInDate"
                  class="!w-full"
                />
              </div>
              
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-2">退房日期</label>
                <el-date-picker
                  v-model="searchForm.checkOut"
                  type="date"
                  placeholder="选择退房日期"
                  format="YYYY-MM-DD"
                  value-format="YYYY-MM-DD"
                  :disabled-date="disabledCheckOutDate"
                  class="!w-full"
                />
              </div>
              
              <div class="flex items-end">
                <button 
                  type="submit"
                  class="w-full btn-accent text-lg font-semibold py-3"
                >
                  搜索酒店
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </section>

    <!-- 特色服务 -->
    <section class="py-16 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-12">
          <h2 class="section-title animate-on-scroll">为什么选择我们</h2>
          <p class="text-gray-600 text-lg animate-on-scroll">专业的服务，优质的体验</p>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div class="text-center animate-on-scroll">
            <div class="w-16 h-16 bg-primary-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"></path>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-900 mb-2">价格保证</h3>
            <p class="text-gray-600">最优惠的价格，透明的费用结构</p>
          </div>
          
          <div class="text-center animate-on-scroll">
            <div class="w-16 h-16 bg-primary-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-900 mb-2">安全预订</h3>
            <p class="text-gray-600">安全可靠的支付系统，保障您的权益</p>
          </div>
          
          <div class="text-center animate-on-scroll">
            <div class="w-16 h-16 bg-primary-100 rounded-full flex items-center justify-center mx-auto mb-4">
              <svg class="w-8 h-8 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18.364 5.636l-3.536 3.536m0 5.656l3.536 3.536M9.172 9.172L5.636 5.636m3.536 9.192L5.636 18.364M12 2.25a9.75 9.75 0 109.75 9.75A9.75 9.75 0 0012 2.25z"></path>
              </svg>
            </div>
            <h3 class="text-xl font-semibold text-gray-900 mb-2">24/7服务</h3>
            <p class="text-gray-600">全天候客服支持，随时为您解决问题</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 热门酒店 -->
    <section class="py-16 bg-gray-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center mb-12">
          <div>
            <h2 class="section-title animate-on-scroll">热门酒店推荐</h2>
            <p class="text-gray-600 animate-on-scroll">精选优质酒店，为您提供舒适的住宿体验</p>
          </div>
          <router-link 
            to="/hotels" 
            class="btn-primary animate-on-scroll"
          >
            查看更多
          </router-link>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <div 
            v-for="hotel in featuredHotels" 
            :key="hotel.id"
            class="card card-hover animate-on-scroll"
            @click="goToHotelDetail(hotel.id)"
          >
            <div class="aspect-w-16 aspect-h-9 mb-4">
              <img 
                :src="hotel.images[0]" 
                :alt="hotel.name"
                loading="lazy"
                decoding="async"
                class="w-full h-48 object-cover rounded-lg"
              >
            </div>
            
            <div class="flex justify-between items-start mb-2">
              <h3 class="text-lg font-semibold text-gray-900 line-clamp-1">{{ hotel.name }}</h3>
              <div class="flex items-center">
                <span class="text-yellow-400 mr-1">★</span>
                <span class="text-sm text-gray-600">{{ hotel.avgRating }}</span>
              </div>
            </div>
            
            <p class="text-gray-600 text-sm mb-3 line-clamp-2">{{ hotel.description }}</p>
            
            <div class="flex justify-between items-center">
              <div>
                <span class="text-2xl font-bold text-primary-600">¥{{ hotel.minPrice }}</span>
                <span class="text-gray-500 text-sm ml-1">起</span>
              </div>
              <div class="flex items-center text-sm text-gray-500">
                <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
                </svg>
                {{ hotel.address.split('市')[0] }}市
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 用户评价 -->
    <section class="py-16 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-12">
          <h2 class="section-title animate-on-scroll">用户评价</h2>
          <p class="text-gray-600 text-lg animate-on-scroll">听听他们怎么说</p>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div class="card animate-on-scroll">
            <div class="flex items-center mb-4">
              <div class="w-12 h-12 bg-gray-300 rounded-full flex items-center justify-center mr-4">
                <span class="text-lg font-semibold text-gray-700">李</span>
              </div>
              <div>
                <h4 class="font-semibold text-gray-900">李先生</h4>
                <div class="flex items-center">
                  <span class="text-yellow-400 mr-1">★★★★★</span>
                </div>
              </div>
            </div>
            <p class="text-gray-600 italic">
              "预订过程非常简单，酒店选择丰富，价格合理。客服响应迅速，解决问题很专业。"
            </p>
          </div>
          
          <div class="card animate-on-scroll">
            <div class="flex items-center mb-4">
              <div class="w-12 h-12 bg-gray-300 rounded-full flex items-center justify-center mr-4">
                <span class="text-lg font-semibold text-gray-700">王</span>
              </div>
              <div>
                <h4 class="font-semibold text-gray-900">王女士</h4>
                <div class="flex items-center">
                  <span class="text-yellow-400 mr-1">★★★★★</span>
                </div>
              </div>
            </div>
            <p class="text-gray-600 italic">
              "界面设计很漂亮，操作流畅。酒店信息详细准确，帮我找到了心仪的酒店。"
            </p>
          </div>
          
          <div class="card animate-on-scroll">
            <div class="flex items-center mb-4">
              <div class="w-12 h-12 bg-gray-300 rounded-full flex items-center justify-center mr-4">
                <span class="text-lg font-semibold text-gray-700">张</span>
              </div>
              <div>
                <h4 class="font-semibold text-gray-900">张先生</h4>
                <div class="flex items-center">
                  <span class="text-yellow-400 mr-1">★★★★☆</span>
                </div>
              </div>
            </div>
            <p class="text-gray-600 italic">
              "经常出差都在用这个平台，服务稳定可靠，价格透明，是商务出行的好帮手。"
            </p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useHotelStore } from '../stores'
import { ElMessage } from 'element-plus'

const router = useRouter()
const hotelStore = useHotelStore()

const today = new Date()
today.setHours(0, 0, 0, 0)

// 禁用入住日期：今天之前的日期
const disabledCheckInDate = (time) => {
  return time.getTime() < today.getTime()
}

// 禁用退房日期：入住日期之前的日期（含入住日期）
const disabledCheckOutDate = (time) => {
  if (searchForm.value.checkIn) {
    const checkInDate = new Date(searchForm.value.checkIn)
    return time.getTime() <= checkInDate.getTime()
  }
  return time.getTime() < today.getTime()
}

const searchForm = ref({
  destination: '',
  checkIn: '',
  checkOut: '',
  guests: 2
})

const featuredHotels = ref([])

const searchHotels = () => {
  // 构建查询参数
  const query = {}
  if (searchForm.value.destination) {
    query.keyword = searchForm.value.destination
  }
  if (searchForm.value.checkIn) {
    query.checkIn = searchForm.value.checkIn
  }
  if (searchForm.value.checkOut) {
    query.checkOut = searchForm.value.checkOut
  }
  
  router.push({ path: '/hotels', query })
}

const goToHotelDetail = (hotelId) => {
  router.push(`/hotel/${hotelId}`)
}

const initScrollAnimation = () => {
  const observerOptions = {
    threshold: 0.1,
    rootMargin: '0px 0px -50px 0px'
  }

  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('visible')
      }
    })
  }, observerOptions)

  document.querySelectorAll('.animate-on-scroll').forEach(el => {
    observer.observe(el)
  })
}

onMounted(async () => {
  // 获取热门酒店数据
  await hotelStore.fetchHotels()
  // 确保 hotels 是数组再调用 slice
  const hotels = Array.isArray(hotelStore.hotels) ? hotelStore.hotels : []
  featuredHotels.value = hotels.slice(0, 3)
  
  // 初始化滚动动画
  setTimeout(initScrollAnimation, 100)
})
</script>

<style scoped>
.line-clamp-1 {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
