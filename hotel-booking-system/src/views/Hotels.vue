<template>
  <div class="hotels bg-gray-50 min-h-screen">
    <!-- 顶部搜索栏背景 -->
    <div class="bg-white shadow-sm border-b border-gray-200 sticky top-16 z-20">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
        <div class="flex flex-col md:flex-row md:items-center justify-between gap-4">
          <div>
            <h1 class="text-2xl font-bold text-gray-900">酒店搜索</h1>
            <p class="text-sm text-gray-500 mt-1">
              共找到 <span class="font-bold text-primary-600">{{ total }}</span> 家符合条件的酒店
            </p>
          </div>
          
          <div class="flex items-center space-x-3">
            <label class="text-sm font-medium text-gray-700 whitespace-nowrap">排序:</label>
            <select 
              v-model="sortBy" 
              class="input-field py-2 pr-8 text-sm bg-gray-50 border-gray-200 focus:bg-white transition-colors"
            >
              <option value="price">价格从低到高</option>
              <option value="rating">评分从高到低</option>
              <option value="distance">距离最近</option>
            </select>
          </div>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div class="flex flex-col lg:flex-row gap-8">
        <!-- 筛选侧边栏 -->
        <div class="lg:w-1/4">
          <div class="bg-white rounded-xl shadow-sm border border-gray-200 p-6 sticky top-36">
            <div class="flex justify-between items-center mb-6">
              <h2 class="text-lg font-bold text-gray-900 flex items-center">
                <svg class="w-5 h-5 mr-2 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 4a1 1 0 011-1h16a1 1 0 011 1v2.586a1 1 0 01-.293.707l-6.414 6.414a1 1 0 00-.293.707V17l-4 4v-6.586a1 1 0 00-.293-.707L3.293 7.293A1 1 0 013 6.586V4z" />
                </svg>
                筛选条件
              </h2>
              <button 
                v-if="hasActiveFilters"
                @click="clearFilters"
                class="text-sm text-primary-600 hover:text-primary-700 font-medium transition-colors"
              >
                清除全部
              </button>
            </div>
            
            <!-- 价格范围 -->
            <div class="mb-8 border-b border-gray-100 pb-6 last:border-0 last:pb-0">
              <h3 class="text-sm font-semibold text-gray-900 mb-4 uppercase tracking-wider">价格区间</h3>
              <div class="space-y-3">
                <label 
                  v-for="range in priceRanges" 
                  :key="range.value"
                  class="flex items-center cursor-pointer group"
                >
                  <input 
                    v-model="priceRange" 
                    type="radio" 
                    :value="range.value"
                    @click="toggleRadio('priceRange', range.value)"
                    class="w-4 h-4 text-primary-600 border-gray-300 focus:ring-primary-500 transition-all cursor-pointer"
                  >
                  <span class="ml-3 text-sm text-gray-600 group-hover:text-primary-600 transition-colors">{{ range.label }}</span>
                </label>
              </div>
            </div>
            
            <!-- 星级评分 -->
            <div class="mb-8 border-b border-gray-100 pb-6 last:border-0 last:pb-0">
              <h3 class="text-sm font-semibold text-gray-900 mb-4 uppercase tracking-wider">酒店星级</h3>
              <div class="space-y-3">
                <label 
                  v-for="star in [5, 4, 3]" 
                  :key="star"
                  class="flex items-center cursor-pointer group"
                >
                  <input 
                    v-model="starRating" 
                    type="radio" 
                    :value="star"
                    @click="toggleRadio('starRating', star)"
                    class="w-4 h-4 text-primary-600 border-gray-300 focus:ring-primary-500 transition-all cursor-pointer"
                  >
                  <div class="ml-3 flex items-center text-sm text-gray-600 group-hover:text-primary-600 transition-colors">
                    <span class="flex text-yellow-400 mr-2">
                      <span v-for="i in star" :key="i">★</span>
                    </span>
                    {{ star }}星级
                  </div>
                </label>
              </div>
            </div>
            
            <!-- 设施服务 -->
            <div class="mb-6">
              <h3 class="text-sm font-semibold text-gray-900 mb-4 uppercase tracking-wider">设施服务</h3>
              <div class="space-y-3">
                <label 
                  v-for="item in amenitiesOptions" 
                  :key="item.value"
                  class="flex items-center cursor-pointer group"
                >
                  <input 
                    v-model="selectedAmenities" 
                    type="checkbox" 
                    :value="item.value"
                    class="w-4 h-4 text-primary-600 border-gray-300 rounded focus:ring-primary-500 transition-all cursor-pointer"
                  >
                  <span class="ml-3 text-sm text-gray-600 group-hover:text-primary-600 transition-colors">{{ item.label }}</span>
                </label>
              </div>
            </div>
          </div>
        </div>

        <!-- 酒店列表 -->
        <div class="lg:w-3/4">
          <!-- 加载状态 -->
          <LoadingSpinner v-if="hotelStore.loading" text="正在搜索最适合您的酒店..." />

          <!-- 酒店列表 -->
          <div v-else-if="hotelStore.hotels.length > 0" class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <HotelCard 
              v-for="(hotel, index) in hotelStore.hotels" 
              :key="hotel.id"
              :hotel="hotel"
              class="animate-fade-in-up"
              :style="{ animationDelay: `${index * 100}ms` }"
            />
          </div>

          <!-- 空状态 -->
          <EmptyState v-else>
            <template #title>未找到符合条件的酒店</template>
            <template #description>建议您调整筛选条件或扩大搜索范围重试</template>
            <template #action>
              <button @click="clearFilters" class="btn-primary mt-4">清除所有筛选</button>
            </template>
          </EmptyState>

          <!-- 分页 -->
          <div v-if="totalPages > 1" class="flex justify-center mt-12">
            <nav class="flex items-center bg-white rounded-lg shadow-sm border border-gray-200 p-1">
              <button 
                @click="handlePageChange(currentPage - 1)"
                :disabled="currentPage === 1"
                class="p-2 rounded-md hover:bg-gray-100 disabled:opacity-30 disabled:cursor-not-allowed transition-colors"
              >
                <svg class="w-5 h-5 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 19l-7-7 7-7" />
                </svg>
              </button>
              
              <div class="flex space-x-1 px-2">
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="handlePageChange(page)"
                  :class="[
                    'w-8 h-8 rounded-md text-sm font-medium transition-all duration-200',
                    currentPage === page 
                      ? 'bg-primary-600 text-white shadow-md' 
                      : 'text-gray-600 hover:bg-gray-100'
                  ]"
                >
                  {{ page }}
                </button>
              </div>
              
              <button 
                @click="handlePageChange(currentPage + 1)"
                :disabled="currentPage === totalPages"
                class="p-2 rounded-md hover:bg-gray-100 disabled:opacity-30 disabled:cursor-not-allowed transition-colors"
              >
                <svg class="w-5 h-5 text-gray-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
                </svg>
              </button>
            </nav>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useHotelStore } from '../stores'
import LoadingSpinner from '../components/LoadingSpinner.vue'
import HotelCard from '../components/HotelCard.vue'
import EmptyState from '../components/EmptyState.vue'

const router = useRouter()
const route = useRoute()
const hotelStore = useHotelStore()

// 筛选状态
const priceRange = ref('')
const starRating = ref(null)
const selectedAmenities = ref([])
const sortBy = ref('price')

// 分页状态
const currentPage = ref(1)
const pageSize = ref(6)
const total = computed(() => hotelStore.totalCount)

// 配置项
const priceRanges = [
  { value: '0-300', label: '¥0 - ¥300' },
  { value: '300-600', label: '¥300 - ¥600' },
  { value: '600-1000', label: '¥600 - ¥1000' },
  { value: '1000-9999', label: '¥1000以上' }
]

const amenitiesOptions = [
  { value: 'wifi', label: '免费WiFi' },
  { value: 'parking', label: '停车场' },
  { value: 'gym', label: '健身房' },
  { value: 'pool', label: '游泳池' }
]

// 计算属性
const totalPages = computed(() => Math.ceil(total.value / pageSize.value) || 1)
const hasActiveFilters = computed(() => priceRange.value || starRating.value || selectedAmenities.value.length > 0)

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  for (let i = start; i <= end; i++) pages.push(i)
  return pages
})

// 方法
const toggleRadio = (field, value) => {
  if (field === 'priceRange') {
    priceRange.value = priceRange.value === value ? '' : value
  } else if (field === 'starRating') {
    starRating.value = starRating.value === value ? null : value
  }
}

const clearFilters = () => {
  priceRange.value = ''
  starRating.value = null
  selectedAmenities.value = []
  currentPage.value = 1
  fetchHotels()
}

const fetchHotels = async () => {
  const params = {
    page: currentPage.value,
    size: pageSize.value,
    sortBy: sortBy.value,
    keyword: route.query.keyword,
    checkIn: route.query.checkIn,
    checkOut: route.query.checkOut
  }
  
  if (priceRange.value) params.priceRange = priceRange.value
  if (starRating.value) params.starRating = starRating.value
  if (selectedAmenities.value.length > 0) params.amenities = selectedAmenities.value.join(',')
  
  await hotelStore.fetchHotelsWithParams(params)
}

const handlePageChange = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page
    fetchHotels()
    window.scrollTo({ top: 0, behavior: 'smooth' })
  }
}

// 监听器
watch([priceRange, starRating, selectedAmenities, sortBy], () => {
  currentPage.value = 1
  fetchHotels()
})

// 监听路由参数变化（例如从首页搜索跳转）
watch(() => route.query, () => {
  currentPage.value = 1
  fetchHotels()
})

onMounted(() => {
  fetchHotels()
})
</script>

<style scoped>
.animate-fade-in-up {
  animation: fadeInUp 0.5s ease-out forwards;
  opacity: 0;
  transform: translateY(20px);
}

@keyframes fadeInUp {
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>