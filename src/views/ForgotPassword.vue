<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-primary-500 to-purple-600 p-5">
    <div class="w-full max-w-md bg-white rounded-2xl shadow-2xl p-8">
      <!-- 第一步：输入邮箱获取令牌 -->
      <template v-if="step === 1">
        <h2 class="text-center text-2xl font-bold text-gray-800 mb-2">忘记密码</h2>
        <p class="text-center text-gray-500 text-sm mb-8">请输入您的注册邮箱，我们将为您生成重置令牌</p>
        
        <form @submit.prevent="handleForgot" class="space-y-5">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">邮箱</label>
            <input
              v-model="form.email"
              type="email"
              placeholder="请输入注册邮箱"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all outline-none"
              :class="{ 'border-red-500': errors.email }"
            />
            <p v-if="errors.email" class="mt-1 text-sm text-red-500">{{ errors.email }}</p>
          </div>

          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 px-4 bg-primary-600 hover:bg-primary-700 text-white font-semibold rounded-lg transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="loading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-2 h-5 w-5 text-white" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              处理中...
            </span>
            <span v-else>获取重置令牌</span>
          </button>
        </form>
      </template>

      <!-- 第二步：输入令牌和新密码 -->
      <template v-else>
        <h2 class="text-center text-2xl font-bold text-gray-800 mb-2">重置密码</h2>
        <p class="text-center text-gray-500 text-sm mb-8">请输入收到的重置令牌和新密码</p>
        
        <form @submit.prevent="handleReset" class="space-y-5">
          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">重置令牌</label>
            <input
              v-model="resetForm.token"
              type="text"
              placeholder="请输入重置令牌"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all outline-none"
              :class="{ 'border-red-500': resetErrors.token }"
            />
            <p v-if="resetErrors.token" class="mt-1 text-sm text-red-500">{{ resetErrors.token }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">新密码</label>
            <input
              v-model="resetForm.newPassword"
              type="password"
              placeholder="至少6位"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all outline-none"
              :class="{ 'border-red-500': resetErrors.newPassword }"
            />
            <p v-if="resetErrors.newPassword" class="mt-1 text-sm text-red-500">{{ resetErrors.newPassword }}</p>
          </div>

          <div>
            <label class="block text-sm font-medium text-gray-700 mb-1">确认新密码</label>
            <input
              v-model="resetForm.confirmPassword"
              type="password"
              placeholder="请再次输入新密码"
              class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-primary-500 focus:border-transparent transition-all outline-none"
              :class="{ 'border-red-500': resetErrors.confirmPassword }"
            />
            <p v-if="resetErrors.confirmPassword" class="mt-1 text-sm text-red-500">{{ resetErrors.confirmPassword }}</p>
          </div>

          <button
            type="submit"
            :disabled="loading"
            class="w-full py-3 px-4 bg-primary-600 hover:bg-primary-700 text-white font-semibold rounded-lg transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span v-if="loading" class="flex items-center justify-center">
              <svg class="animate-spin -ml-1 mr-2 h-5 w-5 text-white" fill="none" viewBox="0 0 24 24">
                <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
              </svg>
              处理中...
            </span>
            <span v-else>重置密码</span>
          </button>

          <div class="text-center">
            <button type="button" @click="step = 1" class="text-primary-600 hover:text-primary-700 text-sm font-medium">
              ← 返回上一步
            </button>
          </div>
        </form>
      </template>

      <!-- 返回首页 -->
      <div class="text-center mt-6 pt-6 border-t border-gray-200">
        <router-link to="/" class="text-primary-600 hover:text-primary-700 text-sm font-medium">
          返回首页
        </router-link>
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

const form = reactive({
  email: ''
})

const errors = reactive({
  email: ''
})

const resetForm = reactive({
  token: '',
  newPassword: '',
  confirmPassword: ''
})

const resetErrors = reactive({
  token: '',
  newPassword: '',
  confirmPassword: ''
})

// 验证邮箱表单
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

// 验证重置密码表单
const validateReset = () => {
  let valid = true
  Object.keys(resetErrors).forEach(key => resetErrors[key] = '')
  
  if (!resetForm.token) {
    resetErrors.token = '请输入重置令牌'
    valid = false
  }
  
  if (!resetForm.newPassword) {
    resetErrors.newPassword = '请输入新密码'
    valid = false
  } else if (resetForm.newPassword.length < 6) {
    resetErrors.newPassword = '密码至少6位'
    valid = false
  }
  
  if (!resetForm.confirmPassword) {
    resetErrors.confirmPassword = '请确认密码'
    valid = false
  } else if (resetForm.confirmPassword !== resetForm.newPassword) {
    resetErrors.confirmPassword = '两次输入的密码不一致'
    valid = false
  }
  
  return valid
}

const handleForgot = async () => {
  if (!validateEmail()) return

  loading.value = true
  try {
    const res = await forgotPassword({ email: form.email })
    // 测试环境直接显示令牌，生产环境应该通过邮件发送
    resetForm.token = res.data
    ElMessage.success('重置令牌已生成，请查看下方或邮箱')
    step.value = 2
  } catch (error) {
    ElMessage.error(error.response?.data?.message || error.message || '获取重置令牌失败')
  } finally {
    loading.value = false
  }
}

const handleReset = async () => {
  if (!validateReset()) return

  loading.value = true
  try {
    await resetPassword({
      token: resetForm.token,
      newPassword: resetForm.newPassword,
      confirmPassword: resetForm.confirmPassword
    })
    ElMessage.success('密码重置成功，请重新登录')
    router.push('/')
  } catch (error) {
    ElMessage.error(error.response?.data?.message || error.message || '密码重置失败')
  } finally {
    loading.value = false
  }
}
</script>
