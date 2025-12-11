<template>
  <div class="min-h-screen flex items-center justify-center relative overflow-hidden bg-gray-900">
    <!-- 背景图片 -->
    <img 
      src="https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80" 
      alt="Background" 
      class="absolute inset-0 w-full h-full object-cover opacity-40"
    />
    
    <div class="absolute top-0 left-0 w-full h-full bg-gradient-to-b from-transparent to-black/70"></div>

    <div class="relative w-full max-w-md p-4 z-10 my-8">
      <div class="bg-white/10 backdrop-blur-md rounded-2xl shadow-2xl border border-white/20 p-8 animate-fade-in-up">
        <div class="text-center mb-8">
          <h2 class="text-3xl font-bold text-white mb-2">创建账号</h2>
          <p class="text-gray-300 text-sm">加入我们，开启您的旅程</p>
        </div>
        
        <form @submit.prevent="handleRegister" class="space-y-5">
          <!-- 用户名 -->
          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">用户名</label>
            <input
              v-model="form.username"
              type="text"
              placeholder="3-20位字母数字下划线"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50 focus:ring-red-500': errors.username }"
            />
            <p v-if="errors.username" class="mt-1 text-xs text-red-400 ml-1">{{ errors.username }}</p>
          </div>

          <!-- 邮箱 -->
          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">邮箱</label>
            <input
              v-model="form.email"
              type="email"
              placeholder="name@example.com"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50 focus:ring-red-500': errors.email }"
            />
            <p v-if="errors.email" class="mt-1 text-xs text-red-400 ml-1">{{ errors.email }}</p>
          </div>

          <!-- 手机号 -->
          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">手机号 <span class="text-gray-400 text-xs font-normal">(选填)</span></label>
            <input
              v-model="form.phone"
              type="tel"
              placeholder="请输入手机号"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50 focus:ring-red-500': errors.phone }"
            />
            <p v-if="errors.phone" class="mt-1 text-xs text-red-400 ml-1">{{ errors.phone }}</p>
          </div>

          <!-- 密码 -->
          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">密码</label>
            <input
              v-model="form.password"
              type="password"
              placeholder="至少6位"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50 focus:ring-red-500': errors.password }"
            />
            <p v-if="errors.password" class="mt-1 text-xs text-red-400 ml-1">{{ errors.password }}</p>
          </div>

          <!-- 确认密码 -->
          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">确认密码</label>
            <input
              v-model="form.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50 focus:ring-red-500': errors.confirmPassword }"
            />
            <p v-if="errors.confirmPassword" class="mt-1 text-xs text-red-400 ml-1">{{ errors.confirmPassword }}</p>
          </div>

          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3.5 px-4 mt-2 bg-primary-600 hover:bg-primary-500 text-white font-bold rounded-xl shadow-lg shadow-primary-600/30 transition-all transform hover:-translate-y-0.5 disabled:opacity-50 disabled:cursor-not-allowed flex justify-center items-center"
          >
            <svg v-if="loading" class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            {{ loading ? '注册中...' : '立即注册' }}
          </button>
        </form>

        <div class="mt-6 text-center">
          <p class="text-gray-400 text-sm">
            已有账号？
            <router-link to="/login" class="text-primary-400 hover:text-primary-300 font-medium transition-colors ml-1">
              直接登录
            </router-link>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores'
import { ElMessage } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)

const form = reactive({
  username: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: ''
})

const errors = reactive({
  username: '',
  email: '',
  phone: '',
  password: '',
  confirmPassword: ''
})

const validate = () => {
  let valid = true
  Object.keys(errors).forEach(key => errors[key] = '')
  
  if (!form.username) {
    errors.username = '请输入用户名'
    valid = false
  } else if (!/^[a-zA-Z0-9_]{3,20}$/.test(form.username)) {
    errors.username = '3-20位字母、数字或下划线'
    valid = false
  }
  
  if (!form.email) {
    errors.email = '请输入邮箱'
    valid = false
  } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = '邮箱格式无效'
    valid = false
  }
  
  if (form.phone && !/^1[3-9]\d{9}$/.test(form.phone)) {
    errors.phone = '手机号格式无效'
    valid = false
  }
  
  if (!form.password) {
    errors.password = '请输入密码'
    valid = false
  } else if (form.password.length < 6) {
    errors.password = '密码长度至少6位'
    valid = false
  }
  
  if (form.confirmPassword !== form.password) {
    errors.confirmPassword = '两次密码输入不一致'
    valid = false
  }
  
  return valid
}

const handleRegister = async () => {
  if (!validate()) return

  loading.value = true
  try {
    await userStore.register(form)
    ElMessage.success('注册成功，欢迎加入！')
    router.push('/')
  } catch (error) {
    ElMessage.error(error.message || '注册失败，请稍后重试')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.animate-fade-in-up {
  animation: fadeInUp 0.6s ease-out forwards;
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>