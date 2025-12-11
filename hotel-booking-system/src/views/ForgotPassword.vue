<template>
  <div class="min-h-screen flex items-center justify-center relative overflow-hidden bg-gray-900">
    <!-- 背景图片 -->
    <img 
      src="https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80" 
      alt="Background" 
      class="absolute inset-0 w-full h-full object-cover opacity-40"
    />
    <div class="absolute top-0 left-0 w-full h-full bg-gradient-to-b from-transparent to-black/70"></div>

    <div class="relative w-full max-w-md p-4 z-10">
      <div class="bg-white/10 backdrop-blur-md rounded-2xl shadow-2xl border border-white/20 p-8 animate-fade-in-up">
        <div class="text-center mb-8">
          <h2 class="text-3xl font-bold text-white mb-2">
            {{ step === 1 ? '忘记密码' : '重置密码' }}
          </h2>
          <p class="text-gray-300 text-sm">
            {{ step === 1 ? '请输入您的注册邮箱获取验证码' : '设置您的新密码' }}
          </p>
        </div>
        
        <!-- 第一步 -->
        <form v-if="step === 1" @submit.prevent="handleForgot" class="space-y-6">
          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">邮箱地址</label>
            <input
              v-model="form.email"
              type="email"
              placeholder="请输入注册邮箱"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50': errors.email }"
            />
            <p v-if="errors.email" class="mt-1 text-xs text-red-400 ml-1">{{ errors.email }}</p>
          </div>

          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3.5 px-4 bg-primary-600 hover:bg-primary-500 text-white font-bold rounded-xl shadow-lg shadow-primary-600/30 transition-all transform hover:-translate-y-0.5 disabled:opacity-50 disabled:cursor-not-allowed flex justify-center items-center"
          >
            <svg v-if="loading" class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            {{ loading ? '发送中...' : '发送验证码' }}
          </button>
        </form>

        <!-- 第二步 -->
        <form v-else @submit.prevent="handleReset" class="space-y-6 animate-fade-in">
          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">验证码/令牌</label>
            <input
              v-model="resetForm.token"
              type="text"
              placeholder="请输入邮件中的验证码"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50': resetErrors.token }"
            />
            <p v-if="resetErrors.token" class="mt-1 text-xs text-red-400 ml-1">{{ resetErrors.token }}</p>
          </div>

          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">新密码</label>
            <input
              v-model="resetForm.newPassword"
              type="password"
              placeholder="请输入新密码"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50': resetErrors.newPassword }"
            />
            <p v-if="resetErrors.newPassword" class="mt-1 text-xs text-red-400 ml-1">{{ resetErrors.newPassword }}</p>
          </div>

          <div class="group">
            <label class="block text-sm font-medium text-gray-200 mb-1 ml-1">确认新密码</label>
            <input
              v-model="resetForm.confirmPassword"
              type="password"
              placeholder="请再次输入新密码"
              class="w-full px-4 py-3 bg-black/20 border border-white/10 rounded-xl focus:ring-2 focus:ring-primary-500 focus:border-transparent text-white placeholder-gray-400 outline-none transition-all"
              :class="{ 'border-red-500/50': resetErrors.confirmPassword }"
            />
            <p v-if="resetErrors.confirmPassword" class="mt-1 text-xs text-red-400 ml-1">{{ resetErrors.confirmPassword }}</p>
          </div>

          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3.5 px-4 bg-primary-600 hover:bg-primary-500 text-white font-bold rounded-xl shadow-lg shadow-primary-600/30 transition-all transform hover:-translate-y-0.5 disabled:opacity-50 disabled:cursor-not-allowed flex justify-center items-center"
          >
            <svg v-if="loading" class="animate-spin -ml-1 mr-3 h-5 w-5 text-white" fill="none" viewBox="0 0 24 24">
              <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
              <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
            </svg>
            {{ loading ? '重置中...' : '重置密码' }}
          </button>

          <div class="text-center">
            <button 
              type="button" 
              @click="step = 1" 
              class="text-sm text-gray-400 hover:text-white transition-colors"
            >
              返回上一步
            </button>
          </div>
        </form>

        <div class="mt-8 pt-6 border-t border-white/10 text-center">
          <router-link to="/login" class="text-primary-400 hover:text-primary-300 text-sm font-medium transition-colors">
            返回登录页
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { forgotPassword, resetPassword } from '@/api/auth'
import { ElMessage } from 'element-plus'

const router = useRouter()
const loading = ref(false)
const step = ref(1)

const form = reactive({ email: '' })
const errors = reactive({ email: '' })

const resetForm = reactive({ token: '', newPassword: '', confirmPassword: '' })
const resetErrors = reactive({ token: '', newPassword: '', confirmPassword: '' })

const validateEmail = () => {
  errors.email = ''
  if (!form.email) {
    errors.email = '请输入邮箱'
    return false
  }
  if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(form.email)) {
    errors.email = '邮箱格式不正确'
    return false
  }
  return true
}

const validateReset = () => {
  let valid = true
  Object.keys(resetErrors).forEach(k => resetErrors[k] = '')
  
  if (!resetForm.token) { resetErrors.token = '请输入验证码'; valid = false }
  if (!resetForm.newPassword || resetForm.newPassword.length < 6) { 
    resetErrors.newPassword = '密码至少6位'; valid = false 
  }
  if (resetForm.confirmPassword !== resetForm.newPassword) { 
    resetErrors.confirmPassword = '密码不一致'; valid = false 
  }
  return valid
}

const handleForgot = async () => {
  if (!validateEmail()) return
  loading.value = true
  try {
    const res = await forgotPassword({ email: form.email })
    resetForm.token = res.data // 仅用于演示环境自动填充
    ElMessage.success('验证码已发送，请查收邮件')
    step.value = 2
  } catch (error) {
    ElMessage.error(error.message || '请求失败，请稍后重试')
  } finally {
    loading.value = false
  }
}

const handleReset = async () => {
  if (!validateReset()) return
  loading.value = true
  try {
    await resetPassword(resetForm)
    ElMessage.success('密码重置成功，请重新登录')
    router.push('/login')
  } catch (error) {
    ElMessage.error(error.message || '重置失败')
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.animate-fade-in-up {
  animation: fadeInUp 0.5s ease-out forwards;
}
.animate-fade-in {
  animation: fadeIn 0.3s ease-out forwards;
}
@keyframes fadeInUp {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}
</style>