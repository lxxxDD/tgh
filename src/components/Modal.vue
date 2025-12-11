<template>
  <div 
    v-if="show" 
    class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50"
    @click.self="close"
  >
    <div 
      class="bg-white rounded-lg shadow-xl max-h-screen overflow-y-auto"
      :class="sizeClasses"
    >
      <!-- 头部 -->
      <div 
        v-if="showHeader" 
        class="flex items-center justify-between p-6 border-b border-gray-200"
      >
        <h2 class="text-xl font-semibold text-gray-900">
          <slot name="title">{{ title }}</slot>
        </h2>
        <button 
          v-if="showClose"
          @click="close"
          class="text-gray-400 hover:text-gray-600 transition-colors"
        >
          <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
          </svg>
        </button>
      </div>
      
      <!-- 内容 -->
      <div class="p-6">
        <slot></slot>
      </div>
      
      <!-- 底部 -->
      <div 
        v-if="showFooter"
        class="flex justify-end space-x-3 p-6 border-t border-gray-200 bg-gray-50"
      >
        <slot name="footer">
          <button 
            @click="close"
            class="px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors"
          >
            取消
          </button>
          <button 
            @click="confirm"
            class="btn-primary"
          >
            确认
          </button>
        </slot>
      </div>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  show: {
    type: Boolean,
    default: false
  },
  title: {
    type: String,
    default: ''
  },
  size: {
    type: String,
    default: 'md', // sm, md, lg, xl
    validator: (value) => ['sm', 'md', 'lg', 'xl'].includes(value)
  },
  showHeader: {
    type: Boolean,
    default: true
  },
  showFooter: {
    type: Boolean,
    default: true
  },
  showClose: {
    type: Boolean,
    default: true
  },
  closeOnOverlay: {
    type: Boolean,
    default: true
  }
})

const emit = defineEmits(['close', 'confirm'])

const sizeClasses = computed(() => {
  const sizes = {
    sm: 'w-full max-w-sm mx-4',
    md: 'w-full max-w-md mx-4',
    lg: 'w-full max-w-lg mx-4',
    xl: 'w-full max-w-xl mx-4'
  }
  return sizes[props.size]
})

const close = () => {
  if (props.closeOnOverlay) {
    emit('close')
  }
}

const confirm = () => {
  emit('confirm')
}
</script>