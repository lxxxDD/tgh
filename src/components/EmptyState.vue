<template>
  <div class="text-center py-12">
    <div class="w-16 h-16 mx-auto mb-4 text-gray-400">
      <slot name="icon">
        <svg class="w-full h-full" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"></path>
        </svg>
      </slot>
    </div>
    
    <h3 class="text-lg font-medium text-gray-900 mb-2">
      <slot name="title">暂无数据</slot>
    </h3>
    
    <p class="text-gray-600 mb-6">
      <slot name="description">没有找到相关数据</slot>
    </p>
    
    <slot name="action">
      <button 
        @click="$emit('action')"
        class="btn-primary"
      >
        重新加载
      </button>
    </slot>
  </div>
</template>

<script setup>
defineEmits(['action'])
</script>