<template>
  <div class="home">
    <!-- Hero区域 -->
    <section class="relative bg-gradient-to-br from-gray-900/80 via-primary-900/70 to-primary-800/60 text-white overflow-hidden">
      <!-- 动态背景装饰 -->
      <div class="absolute inset-0">
        <img 
          src="https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80" 
          alt="Luxury Hotel" 
          class="w-full h-full object-cover opacity-40"
        />
        <div class="absolute top-0 right-0 w-96 h-96 bg-white opacity-5 rounded-full blur-3xl transform translate-x-1/2 -translate-y-1/2"></div>
        <div class="absolute bottom-0 left-0 w-64 h-64 bg-accent-500 opacity-10 rounded-full blur-3xl transform -translate-x-1/3 translate-y-1/3"></div>
      </div>

      <div class="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24 lg:py-32">
        <div class="text-center mb-12">
          <h1 class="text-4xl md:text-6xl font-bold mb-6 animate-fade-in-up tracking-tight">
            发现您的<span class="text-transparent bg-clip-text bg-gradient-to-r from-accent-400 to-yellow-300">完美住宿</span>体验
          </h1>
          <p class="text-xl md:text-2xl text-blue-100 mb-8 animate-fade-in-up animation-delay-100 max-w-2xl mx-auto">
            从豪华度假村到舒适民宿，全球数千家精选住所等您探索
          </p>
        </div>
        
        <!-- 搜索表单 -->
        <div class="max-w-5xl mx-auto">
          <div class="bg-white/95 backdrop-blur-md rounded-2xl shadow-2xl p-6 md:p-8 animate-fade-in-up animation-delay-200 border border-white/20">
            <form @submit.prevent="searchHotels" class="grid grid-cols-1 md:grid-cols-12 gap-4">
              <!-- 目的地 -->
              <div class="md:col-span-4 group">
                <label class="block text-sm font-semibold text-gray-700 mb-2 ml-1">目的地</label>
                <div class="relative transition-all duration-300 focus-within:transform focus-within:-translate-y-1">
                  <div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
                    <svg class="h-5 w-5 text-gray-400 group-focus-within:text-primary-600 transition-colors" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                    </svg>
                  </div>
                  <input 
                    v-model="searchForm.destination" 
                    type="text" 
                    placeholder="你想去哪里？"
                    class="w-full pl-11 pr-4 py-3 border border-gray-200 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent outline-none transition-shadow bg-gray-50 focus:bg-white text-gray-900 placeholder-gray-400"
                  >
                </div>
              </div>
              
              <!-- 入住日期 -->
              <div class="md:col-span-3">
                <label class="block text-sm font-semibold text-gray-700 mb-2 ml-1">入住日期</label>
                <div class="relative transition-all duration-300 focus-within:transform focus-within:-translate-y-1">
                  <el-date-picker
                    v-model="searchForm.checkIn"
                    type="date"
                    placeholder="选择日期"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    :disabled-date="disabledCheckInDate"
                    class="!w-full !h-[46px] !rounded-xl !shadow-none !border-gray-200"
                    :prefix-icon="CalendarIcon"
                  />
                </div>
              </div>
              
              <!-- 退房日期 -->
              <div class="md:col-span-3">
                <label class="block text-sm font-semibold text-gray-700 mb-2 ml-1">退房日期</label>
                <div class="relative transition-all duration-300 focus-within:transform focus-within:-translate-y-1">
                  <el-date-picker
                    v-model="searchForm.checkOut"
                    type="date"
                    placeholder="选择日期"
                    format="YYYY-MM-DD"
                    value-format="YYYY-MM-DD"
                    :disabled-date="disabledCheckOutDate"
                    class="!w-full !h-[46px] !rounded-xl !shadow-none !border-gray-200"
                    :prefix-icon="CalendarIcon"
                  />
                </div>
              </div>
              
              <!-- 搜索按钮 -->
              <div class="md:col-span-2 flex items-end">
                <button 
                  type="submit"
                  class="w-full h-[46px] bg-gradient-to-r from-accent-500 to-accent-600 hover:from-accent-600 hover:to-accent-700 text-white text-lg font-bold rounded-xl shadow-lg shadow-accent-500/30 transition-all transform hover:-translate-y-1 hover:shadow-xl flex items-center justify-center group"
                >
                  <span class="mr-2">搜索</span>
                  <svg class="w-5 h-5 transform group-hover:translate-x-1 transition-transform" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                  </svg>
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </section>

    <!-- 特色服务 -->
    <section class="py-20 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <span class="text-primary-600 font-semibold tracking-wider uppercase text-sm">Our Services</span>
          <h2 class="text-3xl md:text-4xl font-bold text-gray-900 mt-2 mb-4 animate-on-scroll">为什么选择我们</h2>
          <div class="w-20 h-1 bg-primary-500 mx-auto rounded-full"></div>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8 lg:gap-12">
          <!-- 服务卡片 1 -->
          <div class="group p-8 rounded-2xl bg-gray-50 hover:bg-white hover:shadow-xl transition-all duration-300 border border-transparent hover:border-gray-100 animate-on-scroll text-center">
            <div class="w-16 h-16 bg-primary-100 text-primary-600 rounded-2xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 group-hover:bg-primary-600 group-hover:text-white transition-all duration-300">
              <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1"></path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-3">价格保证</h3>
            <p class="text-gray-600 leading-relaxed">我们实时监控全网价格，确保您获得最优惠的预订费率，无任何隐藏费用。</p>
          </div>
          
          <!-- 服务卡片 2 -->
          <div class="group p-8 rounded-2xl bg-gray-50 hover:bg-white hover:shadow-xl transition-all duration-300 border border-transparent hover:border-gray-100 animate-on-scroll text-center" style="animation-delay: 100ms;">
            <div class="w-16 h-16 bg-blue-100 text-blue-600 rounded-2xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 group-hover:bg-blue-600 group-hover:text-white transition-all duration-300">
              <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"></path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-3">安全预订</h3>
            <p class="text-gray-600 leading-relaxed">采用银行级加密技术，保障您的支付安全和个人隐私，让您预订无忧。</p>
          </div>
          
          <!-- 服务卡片 3 -->
          <div class="group p-8 rounded-2xl bg-gray-50 hover:bg-white hover:shadow-xl transition-all duration-300 border border-transparent hover:border-gray-100 animate-on-scroll text-center" style="animation-delay: 200ms;">
            <div class="w-16 h-16 bg-accent-100 text-accent-600 rounded-2xl flex items-center justify-center mx-auto mb-6 group-hover:scale-110 group-hover:bg-accent-600 group-hover:text-white transition-all duration-300">
              <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M18.364 5.636l-3.536 3.536m0 5.656l3.536 3.536M9.172 9.172L5.636 5.636m3.536 9.192L5.636 18.364M12 2.25a9.75 9.75 0 109.75 9.75A9.75 9.75 0 0012 2.25z"></path>
              </svg>
            </div>
            <h3 class="text-xl font-bold text-gray-900 mb-3">24/7 服务</h3>
            <p class="text-gray-600 leading-relaxed">全天候多语言客服支持，无论您身在何处，我们随时待命解决您的问题。</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 热门酒店 -->
    <section class="py-20 bg-gray-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex flex-col md:flex-row justify-between items-end mb-12">
          <div class="text-center md:text-left w-full md:w-auto">
            <h2 class="text-3xl font-bold text-gray-900 mb-2 animate-on-scroll">热门酒店推荐</h2>
            <p class="text-gray-600 animate-on-scroll">精选高分评价酒店，为您提供极致舒适体验</p>
          </div>
          <router-link 
            to="/hotels" 
            class="hidden md:flex items-center text-primary-600 font-semibold hover:text-primary-700 transition-colors animate-on-scroll group"
          >
            查看更多
            <svg class="w-5 h-5 ml-1 transform group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 8l4 4m0 0l-4 4m4-4H3" />
            </svg>
          </router-link>
        </div>
        
        <!-- 酒店列表 (复用 HotelCard) -->
        <div v-if="featuredHotels.length > 0" class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8">
          <HotelCard 
            v-for="(hotel, index) in featuredHotels" 
            :key="hotel.id" 
            :hotel="hotel"
            class="animate-on-scroll h-full"
            :style="{ 'animation-delay': `${index * 100}ms` }"
          />
        </div>
        
        <!-- 加载骨架屏 -->
        <div v-else class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <div v-for="i in 3" :key="i" class="bg-white rounded-xl shadow-sm overflow-hidden h-96 animate-pulse">
            <div class="h-48 bg-gray-200"></div>
            <div class="p-6 space-y-4">
              <div class="h-6 bg-gray-200 rounded w-3/4"></div>
              <div class="h-4 bg-gray-200 rounded w-1/2"></div>
              <div class="h-4 bg-gray-200 rounded w-full"></div>
            </div>
          </div>
        </div>

        <div class="mt-10 text-center md:hidden">
          <router-link to="/hotels" class="btn-primary w-full py-3">查看更多酒店</router-link>
        </div>
      </div>
    </section>

    <!-- 用户评价 -->
    <section class="py-20 bg-white">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="text-center mb-16">
          <h2 class="text-3xl font-bold text-gray-900 mb-4 animate-on-scroll">用户心声</h2>
          <p class="text-gray-600 text-lg animate-on-scroll">听听真实用户的入住体验</p>
        </div>
        
        <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
          <!-- 动态评价卡片 -->
          <div v-for="(review, index) in latestReviews" :key="review.id"
               class="bg-gray-50 rounded-2xl p-8 hover:shadow-xl transition-all duration-300 transform hover:-translate-y-1 border border-gray-100 animate-on-scroll"
               :style="{ animationDelay: index * 100 + 'ms' }">
            <div class="flex items-center mb-6">
              <div :class="['w-12 h-12 bg-gradient-to-br rounded-full flex items-center justify-center text-white font-bold text-xl shadow-lg', colors[index % colors.length]]">
                {{ getInitial(review.userName) }}
              </div>
              <div class="ml-4">
                <h4 class="font-bold text-gray-900">{{ review.userName || '匿名用户' }}</h4>
                <div class="flex text-yellow-400 text-sm">{{ getStars(review.rating) }}</div>
              </div>
            </div>
            <div class="relative">
              <svg class="absolute -top-4 -left-2 w-8 h-8 text-gray-200 transform -scale-x-100" fill="currentColor" viewBox="0 0 24 24"><path d="M14.017 21L14.017 18C14.017 16.0547 15.1924 14.6328 16.6353 13.98C15.7642 13.9469 14.7305 14.2273 14.017 15.1562V9.5C14.017 7.02734 15.9395 5.5 17.5 5.5C19.9863 5.5 21 8.23242 21 10.75V21H14.017ZM5.0166 21L5.0166 18C5.0166 16.0547 6.19189 14.6328 7.63525 13.98C6.76416 13.9469 5.73047 14.2273 5.0166 15.1562V9.5C5.0166 7.02734 6.93945 5.5 8.5 5.5C10.9863 5.5 12 8.23242 12 10.75V21H5.0166Z"></path></svg>
              <p class="text-gray-600 italic relative z-10 pl-4">"{{ review.content }}"</p>
            </div>
          </div>
          
          <!-- 无评价时显示默认内容 -->
          <template v-if="latestReviews.length === 0">
            <div v-for="i in 3" :key="i" class="bg-gray-50 rounded-2xl p-8 border border-gray-100 animate-pulse">
              <div class="flex items-center mb-6">
                <div class="w-12 h-12 bg-gray-300 rounded-full"></div>
                <div class="ml-4 space-y-2">
                  <div class="h-4 bg-gray-300 rounded w-20"></div>
                  <div class="h-3 bg-gray-300 rounded w-16"></div>
                </div>
              </div>
              <div class="space-y-2">
                <div class="h-3 bg-gray-300 rounded w-full"></div>
                <div class="h-3 bg-gray-300 rounded w-3/4"></div>
              </div>
            </div>
          </template>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, defineComponent, h } from 'vue'
import { useRouter } from 'vue-router'
import { useHotelStore } from '../stores'
import HotelCard from '../components/HotelCard.vue'
import { getLatestReviews } from '../api/review'

// 图标组件
const CalendarIcon = defineComponent({ 
  render: () => h('svg', { 
    class: 'w-4 h-4 text-gray-500', 
    fill: 'none', 
    viewBox: '0 0 24 24', 
    stroke: 'currentColor' 
  }, [
    h('path', { 
      'stroke-linecap': 'round', 
      'stroke-linejoin': 'round', 
      'stroke-width': '2', 
      d: 'M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z' 
    })
  ]) 
})

const router = useRouter()
const hotelStore = useHotelStore()

const today = new Date()
today.setHours(0, 0, 0, 0)

const searchForm = ref({
  destination: '',
  checkIn: '',
  checkOut: '',
  guests: 2
})

const featuredHotels = ref([])
const latestReviews = ref([])

// 禁用日期逻辑
const disabledCheckInDate = (time) => time.getTime() < today.getTime()
const disabledCheckOutDate = (time) => {
  if (searchForm.value.checkIn) {
    const checkInDate = new Date(searchForm.value.checkIn)
    return time.getTime() <= checkInDate.getTime()
  }
  return time.getTime() < today.getTime()
}

const searchHotels = () => {
  const query = {}
  if (searchForm.value.destination) query.keyword = searchForm.value.destination
  if (searchForm.value.checkIn) query.checkIn = searchForm.value.checkIn
  if (searchForm.value.checkOut) query.checkOut = searchForm.value.checkOut
  router.push({ path: '/hotels', query })
}

// 滚动动画观察器
const initScrollObserver = () => {
  const observer = new IntersectionObserver((entries) => {
    entries.forEach(entry => {
      if (entry.isIntersecting) {
        entry.target.classList.add('is-visible')
        observer.unobserve(entry.target)
      }
    })
  }, { threshold: 0.1, rootMargin: '0px 0px -50px 0px' })

  document.querySelectorAll('.animate-on-scroll').forEach(el => observer.observe(el))
}

// 获取评价星星
const getStars = (rating) => {
  const full = Math.floor(rating)
  const half = rating % 1 >= 0.5 ? 1 : 0
  return '★'.repeat(full) + (half ? '☆' : '') + '☆'.repeat(5 - full - half)
}

// 获取用户名首字
const getInitial = (name) => name ? name.charAt(0) : '匿'

// 颜色列表
const colors = ['from-primary-500 to-primary-600', 'from-accent-500 to-accent-600', 'from-green-500 to-teal-600']

onMounted(async () => {
  await hotelStore.fetchHotels()
  // 确保数据存在且为数组
  const hotels = Array.isArray(hotelStore.hotels) ? hotelStore.hotels : []
  featuredHotels.value = hotels.slice(0, 3)
  
  // 获取最新评价
  try {
    const res = await getLatestReviews(3)
    latestReviews.value = res || []
  } catch (e) {
    console.error('获取评价失败:', e)
  }
  
  // 延迟初始化动画，等待DOM渲染
  setTimeout(initScrollObserver, 100)
})
</script>

<style scoped>
/* 初始状态 */
.animate-fade-in-up {
  opacity: 0;
  animation: fadeInUp 0.8s ease-out forwards;
}

.animate-on-scroll {
  opacity: 0;
  transform: translateY(30px);
  transition: all 0.8s ease-out;
}

/* 激活状态 */
.animate-on-scroll.is-visible {
  opacity: 1;
  transform: translateY(0);
}

.animation-delay-100 { animation-delay: 0.1s; transition-delay: 0.1s; }
.animation-delay-200 { animation-delay: 0.2s; transition-delay: 0.2s; }

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(30px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>