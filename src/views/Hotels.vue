<template>
  <div class="hotels">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- 页面标题 -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">酒店搜索</h1>
        <p class="text-gray-600">找到您心仪的完美住宿</p>
      </div>

      <div class="flex flex-col lg:flex-row gap-8">
        <!-- 筛选侧边栏 -->
        <div class="lg:w-1/4">
          <div class="card sticky top-24">
            <h2 class="text-lg font-semibold text-gray-900 mb-6">筛选条件</h2>
            
            <!-- 价格范围 -->
            <div class="mb-6">
              <h3 class="text-sm font-medium text-gray-700 mb-3">价格范围</h3>
              <div class="space-y-2">
                <label class="flex items-center cursor-pointer">
                  <input 
                    v-model="priceRange" 
                    type="radio" 
                    value="0-300"
                    @click="toggleRadio('priceRange', '0-300')"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600">¥0 - ¥300</span>
                </label>
                <label class="flex items-center cursor-pointer">
                  <input 
                    v-model="priceRange" 
                    type="radio" 
                    value="300-600"
                    @click="toggleRadio('priceRange', '300-600')"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600">¥300 - ¥600</span>
                </label>
                <label class="flex items-center cursor-pointer">
                  <input 
                    v-model="priceRange" 
                    type="radio" 
                    value="600-1000"
                    @click="toggleRadio('priceRange', '600-1000')"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600">¥600 - ¥1000</span>
                </label>
                <label class="flex items-center cursor-pointer">
                  <input 
                    v-model="priceRange" 
                    type="radio" 
                    value="1000-9999"
                    @click="toggleRadio('priceRange', '1000-9999')"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600">¥1000+</span>
                </label>
              </div>
            </div>
            
            <!-- 星级评分 -->
            <div class="mb-6">
              <h3 class="text-sm font-medium text-gray-700 mb-3">星级评分</h3>
              <div class="space-y-2">
                <label class="flex items-center cursor-pointer">
                  <input 
                    v-model="starRating" 
                    type="radio" 
                    :value="5"
                    @click="toggleRadio('starRating', 5)"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600 flex items-center">
                    <span class="text-yellow-400 mr-1">★★★★★</span>
                    五星级
                  </span>
                </label>
                <label class="flex items-center cursor-pointer">
                  <input 
                    v-model="starRating" 
                    type="radio" 
                    :value="4"
                    @click="toggleRadio('starRating', 4)"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600 flex items-center">
                    <span class="text-yellow-400 mr-1">★★★★</span>
                    四星级及以上
                  </span>
                </label>
                <label class="flex items-center cursor-pointer">
                  <input 
                    v-model="starRating" 
                    type="radio" 
                    :value="3"
                    @click="toggleRadio('starRating', 3)"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600 flex items-center">
                    <span class="text-yellow-400 mr-1">★★★</span>
                    三星级及以上
                  </span>
                </label>
              </div>
            </div>
            
            <!-- 设施服务 -->
            <div class="mb-6">
              <h3 class="text-sm font-medium text-gray-700 mb-3">设施服务</h3>
              <div class="space-y-2">
                <label class="flex items-center">
                  <input 
                    v-model="selectedAmenities" 
                    type="checkbox" 
                    value="wifi"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600">免费WiFi</span>
                </label>
                <label class="flex items-center">
                  <input 
                    v-model="selectedAmenities" 
                    type="checkbox" 
                    value="parking"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600">停车场</span>
                </label>
                <label class="flex items-center">
                  <input 
                    v-model="selectedAmenities" 
                    type="checkbox" 
                    value="gym"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600">健身房</span>
                </label>
                <label class="flex items-center">
                  <input 
                    v-model="selectedAmenities" 
                    type="checkbox" 
                    value="pool"
                    class="text-primary-600 focus:ring-primary-500"
                  >
                  <span class="ml-2 text-sm text-gray-600">游泳池</span>
                </label>
              </div>
            </div>
            
            <button 
              @click="clearFilters"
              class="w-full px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors"
            >
              清除筛选
            </button>
          </div>
        </div>

        <!-- 酒店列表 -->
        <div class="lg:w-3/4">
          <!-- 搜索结果统计 -->
          <div class="flex justify-between items-center mb-6">
            <p class="text-gray-600">
              共找到 <span class="font-semibold text-gray-900">{{ total }}</span> 家酒店
            </p>
            
            <div class="flex items-center space-x-4">
              <label class="text-sm text-gray-600 whitespace-nowrap">排序方式:</label>
              <select 
                v-model="sortBy" 
                class="input-field text-sm py-1"
              >
                <option value="price">价格</option>
                <option value="rating">评分</option>
                <option value="distance">距离</option>
              </select>
            </div>
          </div>

          <!-- 加载状态 -->
          <LoadingSpinner v-if="hotelStore.loading" text="正在搜索酒店..." />

          <!-- 酒店卡片列表 -->
          <div v-else class="space-y-6">
            <div 
              v-for="hotel in hotelStore.hotels" 
              :key="hotel.id"
              class="card card-hover"
              @click="goToHotelDetail(hotel.id)"
            >
              <div class="flex flex-col md:flex-row gap-6">
                <!-- 酒店图片 -->
                <div class="md:w-1/3">
                  <img 
                    :src="hotel.images[0]" 
                    :alt="hotel.name"
                    loading="lazy"
                    decoding="async"
                    class="w-full h-48 md:h-32 object-cover rounded-lg"
                  >
                </div>
                
                <!-- 酒店信息 -->
                <div class="md:w-2/3">
                  <div class="flex justify-between items-start mb-2">
                    <h3 class="text-xl font-semibold text-gray-900">{{ hotel.name }}</h3>
                    <div class="flex items-center">
                      <span class="text-yellow-400 mr-1">★</span>
                      <span class="text-sm text-gray-600">{{ hotel.avgRating }}</span>
                      <span class="text-xs text-gray-500 ml-1">({{ hotel.reviewCount }})</span>
                    </div>
                  </div>
                  
                  <div class="flex items-center text-sm text-gray-600 mb-2">
                    <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
                    </svg>
                    {{ hotel.address }}
                  </div>
                  
                  <div class="flex items-center mb-3">
                    <div class="flex items-center mr-4">
                      <span class="text-yellow-400 mr-1">
                        {{ '★'.repeat(hotel.starRating) }}
                      </span>
                      <span class="text-sm text-gray-600">{{ hotel.starRating }}星级</span>
                    </div>
                    <div class="flex space-x-2">
                      <span 
                        v-for="facility in hotel.facilities.slice(0, 3)" 
                        :key="facility"
                        class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded-full"
                      >
                        {{ getFacilityName(facility) }}
                      </span>
                      <span v-if="hotel.facilities.length > 3" class="text-xs text-gray-500">
                        +{{ hotel.facilities.length - 3 }}
                      </span>
                    </div>
                  </div>
                  
                  <p class="text-gray-600 text-sm mb-4 line-clamp-2">{{ hotel.description }}</p>
                  
                  <div class="flex justify-between items-center">
                    <div>
                      <span class="text-2xl font-bold text-primary-600">¥{{ hotel.minPrice }}</span>
                      <span class="text-gray-500 text-sm ml-1">起/晚</span>
                    </div>
                    <button class="btn-primary">
                      查看详情
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>

          <!-- 分页 -->
          <div v-if="totalPages > 1" class="flex justify-center mt-8">
            <nav class="flex space-x-2">
              <button 
                @click="handlePageChange(currentPage - 1)"
                :disabled="currentPage === 1"
                class="px-3 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                上一页
              </button>
              
              <button 
                v-for="page in visiblePages" 
                :key="page"
                @click="handlePageChange(page)"
                :class="[
                  'px-3 py-2 border rounded-md text-sm font-medium transition-colors',
                  currentPage === page 
                    ? 'border-primary-600 bg-primary-600 text-white' 
                    : 'border-gray-300 text-gray-700 hover:bg-gray-50'
                ]"
              >
                {{ page }}
              </button>
              
              <button 
                @click="handlePageChange(currentPage + 1)"
                :disabled="currentPage === totalPages"
                class="px-3 py-2 border border-gray-300 rounded-md text-sm font-medium text-gray-700 hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                下一页
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
import { useRouter } from 'vue-router'
import { useHotelStore } from '../stores'
import LoadingSpinner from '../components/LoadingSpinner.vue'

const router = useRouter()
const hotelStore = useHotelStore()

// 筛选条件
const priceRange = ref('')
const starRating = ref(null)
const selectedAmenities = ref([])
const sortBy = ref('price')

// 点击已选中的radio可以取消选择
const toggleRadio = (field, value) => {
  if (field === 'priceRange') {
    if (priceRange.value === value) {
      priceRange.value = ''
    }
  } else if (field === 'starRating') {
    if (starRating.value === value) {
      starRating.value = null
    }
  }
}

// 分页
const currentPage = ref(1)
const pageSize = ref(6)
const total = ref(0)

// 获取酒店列表
const fetchHotels = async () => {
  const params = {
    page: currentPage.value,
    size: pageSize.value,
    sortBy: sortBy.value
  }
  // 价格范围
  if (priceRange.value) {
    params.priceRange = priceRange.value
  }
  // 星级
  if (starRating.value) {
    params.starRating = starRating.value
  }
  // 设施
  if (selectedAmenities.value.length > 0) {
    params.amenities = selectedAmenities.value.join(',')
  }
  
  await hotelStore.fetchHotelsWithParams(params)
  total.value = hotelStore.totalCount
}

// 计算总页数
const totalPages = computed(() => Math.ceil(total.value / pageSize.value) || 1)

// 可见页码
const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  return pages
})

// 跳转酒店详情
const goToHotelDetail = (hotelId) => {
  router.push(`/hotel/${hotelId}`)
}

// 清除筛选条件
const clearFilters = () => {
  priceRange.value = ''
  starRating.value = null
  selectedAmenities.value = []
  currentPage.value = 1
  fetchHotels()
}

// 设施名称映射
const getFacilityName = (facility) => {
  const names = {
    wifi: 'WiFi',
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

// 页码变化
const handlePageChange = (page) => {
  currentPage.value = page
  fetchHotels()
}

// 监听筛选条件变化，重新请求数据
watch([priceRange, starRating, selectedAmenities, sortBy], () => {
  currentPage.value = 1
  fetchHotels()
})

onMounted(() => {
  fetchHotels()
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
