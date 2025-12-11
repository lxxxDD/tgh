<template>
  <div class="flex justify-center items-center py-12">
    <div class="relative">
      <div class="animate-spin rounded-full h-12 w-12 border-b-2 border-primary-600"></div>
      <div class="absolute inset-0 animate-spin rounded-full h-12 w-12 border-t-2 border-primary-400" style="animation-direction: reverse;"></div>
    </div>
    <p v-if="text" class="ml-4 text-gray-600">{{ text }}</p>
  </div>
</template>

<script setup>
const props = defineProps({
  text: {
    type: String,
    default: '加载中...'
  }
})
</script>