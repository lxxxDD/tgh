<template>
  <div 
    class="card card-hover"
    @click="goToHotelDetail"
  >
    <div class="aspect-w-16 aspect-h-9 mb-4">
      <img 
        :src="hotel.images[0]" 
        :alt="hotel.name"
        class="w-full h-48 object-cover rounded-lg"
        @error="handleImageError"
      >
    </div>
    
    <div class="flex justify-between items-start mb-2">
      <h3 class="text-lg font-semibold text-gray-900 line-clamp-1">{{ hotel.name }}</h3>
      <div class="flex items-center">
        <span class="text-yellow-400 mr-1">★</span>
        <span class="text-sm text-gray-600">{{ hotel.avgRating }}</span>
      </div>
    </div>
    
    <div class="flex items-center text-gray-600 text-sm mb-3">
      <svg class="w-4 h-4 mr-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z"></path>
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z"></path>
      </svg>
      <span class="line-clamp-1">{{ hotel.address }}</span>
    </div>
    
    <div class="flex items-center mb-3">
      <div class="flex items-center mr-4">
        <span class="text-yellow-400 mr-1">
          {{ '★'.repeat(hotel.starRating) }}
        </span>
        <span class="text-sm text-gray-600">{{ hotel.starRating }}星级</span>
      </div>
      <div class="flex space-x-1">
        <span 
          v-for="facility in hotel.facilities.slice(0, 2)" 
          :key="facility"
          class="px-2 py-1 bg-gray-100 text-gray-600 text-xs rounded-full"
        >
          {{ getFacilityName(facility) }}
        </span>
        <span v-if="hotel.facilities.length > 2" class="text-xs text-gray-500">
          +{{ hotel.facilities.length - 2 }}
        </span>
      </div>
    </div>
    
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
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const props = defineProps({
  hotel: {
    type: Object,
    required: true
  }
})

const goToHotelDetail = () => {
  router.push(`/hotel/${props.hotel.id}`)
}

const handleImageError = (event) => {
  event.target.src = 'https://via.placeholder.com/400x300?text=Hotel+Image'
}

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
</script>

<style scoped>
.line-clamp-1 {
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>