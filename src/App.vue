<template>
  <div id="app" class="min-h-screen bg-gray-50">
    <!-- 导航栏 -->
    <nav class="bg-white shadow-sm border-b border-gray-200 sticky top-0 z-50">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <!-- Logo -->
          <div class="flex items-center">
            <router-link to="/" class="flex items-center space-x-2">
              <div class="w-8 h-8 bg-primary-600 rounded-lg flex items-center justify-center">
                <span class="text-white font-bold text-lg">H</span>
              </div>
              <span class="text-xl font-bold text-gray-900">酒店预订</span>
            </router-link>
          </div>

          <!-- 导航链接 -->
          <div class="hidden md:flex items-center space-x-8">
            <router-link 
              to="/" 
              class="text-gray-700 hover:text-primary-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
              :class="{ 'text-primary-600': $route.path === '/' }"
            >
              首页
            </router-link>
            <router-link 
              to="/hotels" 
              class="text-gray-700 hover:text-primary-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
              :class="{ 'text-primary-600': $route.path === '/hotels' }"
            >
              酒店
            </router-link>
            <router-link 
              to="/bookings" 
              class="text-gray-700 hover:text-primary-600 px-3 py-2 rounded-md text-sm font-medium transition-colors"
              :class="{ 'text-primary-600': $route.path === '/bookings' }"
            >
              我的订单
            </router-link>
          </div>

          <!-- 用户菜单 -->
          <div class="flex items-center space-x-4">
            <template v-if="userStore.isAuthenticated">
              <div class="relative">
                <button 
                  @click="toggleUserMenu"
                  class="flex items-center space-x-2 text-gray-700 hover:text-primary-600 transition-colors"
                >
                  <div class="w-8 h-8 bg-gray-300 rounded-full flex items-center justify-center">
                    <span class="text-sm font-medium">{{ userStore.user?.username?.charAt(0) || 'U' }}</span>
                  </div>
                  <span class="hidden md:block text-sm font-medium">{{ userStore.user?.username || '用户' }}</span>
                </button>
                
                <!-- 下拉菜单 -->
                <div v-if="showUserMenu" class="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg border border-gray-200 py-1 z-50">
                  <router-link 
                    to="/user" 
                    class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 transition-colors"
                    @click="showUserMenu = false"
                  >
                    个人中心
                  </router-link>
                  <button 
                    @click="logout"
                    class="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 transition-colors"
                  >
                    退出登录
                  </button>
                </div>
              </div>
            </template>
            <template v-else>
              <router-link 
                to="/login"
                class="btn-primary"
              >
                登录
              </router-link>
            </template>
          </div>
        </div>
      </div>
    </nav>

    <!-- 主要内容区域 -->
    <main class="flex-1">
      <router-view />
    </main>

    <!-- 页脚 -->
    <footer class="bg-white border-t border-gray-200 mt-16">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div class="grid grid-cols-1 md:grid-cols-4 gap-8">
          <div class="col-span-1 md:col-span-2">
            <div class="flex items-center space-x-2 mb-4">
              <div class="w-8 h-8 bg-primary-600 rounded-lg flex items-center justify-center">
                <span class="text-white font-bold text-lg">H</span>
              </div>
              <span class="text-xl font-bold text-gray-900">酒店预订系统</span>
            </div>
            <p class="text-gray-600 text-sm">
              专业的酒店预订平台，为您提供优质的住宿体验。
            </p>
          </div>
          
          <div>
            <h3 class="text-sm font-semibold text-gray-900 uppercase tracking-wider mb-4">快速链接</h3>
            <ul class="space-y-2">
              <li><router-link to="/hotels" class="text-gray-600 hover:text-primary-600 text-sm">酒店搜索</router-link></li>
              <li><router-link to="/bookings" class="text-gray-600 hover:text-primary-600 text-sm">我的订单</router-link></li>
            </ul>
          </div>
          
          <div>
            <h3 class="text-sm font-semibold text-gray-900 uppercase tracking-wider mb-4">联系我们</h3>
            <ul class="space-y-2 text-sm text-gray-600">
              <li>客服热线：400-123-4567</li>
              <li>邮箱：service@hotel-booking.com</li>
              <li>工作时间：24小时服务</li>
            </ul>
          </div>
        </div>
        
        <div class="border-t border-gray-200 mt-8 pt-8 text-center">
          <p class="text-gray-500 text-sm">
            © 2024 酒店预订系统. 保留所有权利.
          </p>
        </div>
      </div>
    </footer>

    <!-- 登录模态框 -->
    <div v-if="showLoginModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white rounded-lg p-8 max-w-md w-full mx-4">
        <div class="text-center mb-6">
          <h2 class="text-2xl font-bold text-gray-900">登录</h2>
          <p class="text-gray-600 mt-2">请输入您的登录信息</p>
        </div>
        
        <form @submit.prevent="login">
          <div class="mb-4">
            <label class="block text-sm font-medium text-gray-700 mb-2">用户名</label>
            <input 
              v-model="loginForm.username" 
              type="text" 
              class="input-field"
              placeholder="请输入用户名"
              required
            >
          </div>
          
          <div class="mb-6">
            <label class="block text-sm font-medium text-gray-700 mb-2">密码</label>
            <input 
              v-model="loginForm.password" 
              type="password" 
              class="input-field"
              placeholder="请输入密码"
              required
            >
          </div>
          
          <div class="flex space-x-4">
            <button 
              type="button"
              @click="showLoginModal = false"
              class="flex-1 px-4 py-2 border border-gray-300 rounded-lg text-gray-700 hover:bg-gray-50 transition-colors"
            >
              取消
            </button>
            <button 
              type="submit"
              class="flex-1 btn-primary"
            >
              登录
            </button>
          </div>
        </form>
        
        <div class="mt-4 text-center space-y-2">
          <div class="flex justify-center space-x-4 text-sm">
            <router-link 
              to="/register" 
              @click="showLoginModal = false"
              class="text-primary-600 hover:text-primary-700"
            >
              注册账号
            </router-link>
            <span class="text-gray-300">|</span>
            <router-link 
              to="/forgot-password" 
              @click="showLoginModal = false"
              class="text-primary-600 hover:text-primary-700"
            >
              忘记密码
            </router-link>
          </div>
          <p class="text-sm text-gray-500">
            测试账号：admin / 123456
          </p>
        </div>
      </div>
    </div>

    <!-- 点击外部关闭下拉菜单 -->
    <div v-if="showUserMenu" @click="showUserMenu = false" class="fixed inset-0 z-40"></div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useUserStore } from './stores'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

const userStore = useUserStore()
const router = useRouter()

const showUserMenu = ref(false)
const showLoginModal = ref(false)
const loginForm = ref({
  username: '',
  password: ''
})

const toggleUserMenu = () => {
  showUserMenu.value = !showUserMenu.value
}

const login = async () => {
  try {
    await userStore.login({
      username: loginForm.value.username,
      password: loginForm.value.password
    })
    showLoginModal.value = false
    loginForm.value = { username: '', password: '' }
  } catch (error) {
    ElMessage.error(error.response?.data?.message || error.message || '用户名或密码错误')
  }
}

const logout = () => {
  userStore.logout()
  showUserMenu.value = false
  router.push('/')
}

onMounted(() => {
  userStore.loadUserFromStorage()
})
</script>