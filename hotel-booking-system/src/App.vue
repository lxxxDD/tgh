<template>
  <div id="app" class="min-h-screen bg-gray-50 flex flex-col">
    <!-- 导航栏 -->
    <nav 
      class="fixed w-full z-50 transition-all duration-300 border-b border-white/10"
      :class="[
        isScrolled 
          ? 'bg-white/80 backdrop-blur-md shadow-lg py-2' 
          : 'bg-white py-4'
      ]"
    >
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex justify-between items-center h-16">
          <!-- Logo -->
          <div class="flex-shrink-0 flex items-center">
            <router-link to="/" class="flex items-center gap-2 group">
              <div class="w-10 h-10 bg-gradient-to-br from-primary-600 to-primary-500 rounded-xl flex items-center justify-center shadow-lg shadow-primary-500/30 group-hover:scale-105 transition-transform duration-300">
                <span class="text-white font-bold text-xl">H</span>
              </div>
              <span class="text-2xl font-bold bg-clip-text text-transparent bg-gradient-to-r from-gray-900 to-gray-600">
                HotelBooking
              </span>
            </router-link>
          </div>

          <!-- Desktop Menu -->
          <div class="hidden md:flex items-center space-x-1">
            <template v-for="item in navItems" :key="item.path">
              <router-link 
                :to="item.path"
                class="px-4 py-2 rounded-lg text-sm font-medium transition-all duration-200 relative group"
                :class="[
                  $route.path === item.path 
                    ? 'text-primary-600 bg-primary-50' 
                    : 'text-gray-600 hover:text-primary-600 hover:bg-gray-50'
                ]"
              >
                {{ item.name }}
                <span 
                  v-if="$route.path === item.path"
                  class="absolute bottom-0 left-1/2 transform -translate-x-1/2 w-1 h-1 bg-primary-600 rounded-full mb-1"
                ></span>
              </router-link>
            </template>
          </div>

          <!-- User Menu -->
          <div class="hidden md:flex items-center space-x-4">
            <template v-if="userStore.isAuthenticated">
              <div class="relative" ref="dropdownRef">
                <button 
                  @click="toggleUserMenu"
                  class="flex items-center gap-3 pl-1 pr-2 py-1 rounded-full border border-gray-200 hover:border-gray-300 hover:shadow-md transition-all duration-200 bg-white"
                >
                  <div class="w-8 h-8 bg-gradient-to-br from-primary-100 to-primary-200 text-primary-700 rounded-full flex items-center justify-center text-sm font-bold">
                    {{ userStore.user?.username?.charAt(0).toUpperCase() || 'U' }}
                  </div>
                  <span class="text-sm font-medium text-gray-700 max-w-[100px] truncate">
                    {{ userStore.user?.username }}
                  </span>
                  <svg 
                    class="w-4 h-4 text-gray-400 transition-transform duration-200"
                    :class="{ 'rotate-180': showUserMenu }"
                    fill="none" viewBox="0 0 24 24" stroke="currentColor"
                  >
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 9l-7 7-7-7" />
                  </svg>
                </button>
                
                <!-- Dropdown -->
                <transition
                  enter-active-class="transition ease-out duration-200"
                  enter-from-class="transform opacity-0 scale-95"
                  enter-to-class="transform opacity-100 scale-100"
                  leave-active-class="transition ease-in duration-75"
                  leave-from-class="transform opacity-100 scale-100"
                  leave-to-class="transform opacity-0 scale-95"
                >
                  <div 
                    v-if="showUserMenu" 
                    class="absolute right-0 mt-2 w-56 bg-white rounded-xl shadow-xl border border-gray-100 py-2 origin-top-right ring-1 ring-black ring-opacity-5 focus:outline-none"
                  >
                    <div class="px-4 py-3 border-b border-gray-50">
                      <p class="text-xs text-gray-500 uppercase tracking-wider font-semibold">Signed in as</p>
                      <p class="text-sm font-medium text-gray-900 truncate mt-1">{{ userStore.user?.email }}</p>
                    </div>
                    
                    <div class="py-1">
                      <router-link 
                        to="/user" 
                        class="group flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 hover:text-primary-600"
                        @click="showUserMenu = false"
                      >
                        <svg class="mr-3 h-5 w-5 text-gray-400 group-hover:text-primary-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                        </svg>
                        个人中心
                      </router-link>
                      <router-link 
                        to="/bookings" 
                        class="group flex items-center px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 hover:text-primary-600"
                        @click="showUserMenu = false"
                      >
                        <svg class="mr-3 h-5 w-5 text-gray-400 group-hover:text-primary-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                        </svg>
                        我的订单
                      </router-link>
                    </div>
                    
                    <div class="py-1 border-t border-gray-50">
                      <button 
                        @click="handleLogout"
                        class="group flex w-full items-center px-4 py-2 text-sm text-red-600 hover:bg-red-50"
                      >
                        <svg class="mr-3 h-5 w-5 text-red-400 group-hover:text-red-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1" />
                        </svg>
                        退出登录
                      </button>
                    </div>
                  </div>
                </transition>
              </div>
            </template>
            <template v-else>
              <div class="flex items-center gap-3">
                <router-link 
                  to="/login"
                  class="text-gray-600 hover:text-primary-600 font-medium text-sm transition-colors"
                >
                  登录
                </router-link>
                <router-link 
                  to="/register"
                  class="px-5 py-2.5 bg-primary-600 hover:bg-primary-700 text-white text-sm font-bold rounded-full shadow-lg shadow-primary-500/30 transition-all hover:-translate-y-0.5"
                >
                  注册账号
                </router-link>
              </div>
            </template>
          </div>

          <!-- Mobile Menu Button -->
          <div class="md:hidden flex items-center">
            <button @click="mobileMenuOpen = !mobileMenuOpen" class="text-gray-600 hover:text-gray-900 p-2">
              <svg class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path v-if="!mobileMenuOpen" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
                <path v-else stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>
        </div>
      </div>

      <!-- Mobile Menu -->
      <transition
        enter-active-class="transition duration-200 ease-out"
        enter-from-class="transform -translate-y-4 opacity-0"
        enter-to-class="transform translate-y-0 opacity-100"
        leave-active-class="transition duration-150 ease-in"
        leave-from-class="transform translate-y-0 opacity-100"
        leave-to-class="transform -translate-y-4 opacity-0"
      >
        <div v-if="mobileMenuOpen" class="md:hidden bg-white border-b border-gray-100 shadow-lg absolute w-full">
          <div class="px-4 pt-2 pb-4 space-y-1">
            <router-link 
              v-for="item in navItems" 
              :key="item.path"
              :to="item.path"
              class="block px-3 py-2 rounded-md text-base font-medium"
              :class="[
                $route.path === item.path 
                  ? 'text-primary-600 bg-primary-50' 
                  : 'text-gray-600 hover:text-primary-600 hover:bg-gray-50'
              ]"
              @click="mobileMenuOpen = false"
            >
              {{ item.name }}
            </router-link>
            
            <div class="border-t border-gray-100 pt-4 mt-4">
              <template v-if="userStore.isAuthenticated">
                <div class="flex items-center px-3 mb-3">
                  <div class="w-10 h-10 bg-primary-100 rounded-full flex items-center justify-center text-primary-600 font-bold mr-3">
                    {{ userStore.user?.username?.charAt(0).toUpperCase() }}
                  </div>
                  <div>
                    <div class="font-medium text-gray-900">{{ userStore.user?.username }}</div>
                    <div class="text-sm text-gray-500">{{ userStore.user?.email }}</div>
                  </div>
                </div>
                <router-link to="/user" class="block px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:bg-gray-50" @click="mobileMenuOpen = false">个人中心</router-link>
                <router-link to="/bookings" class="block px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:bg-gray-50" @click="mobileMenuOpen = false">我的订单</router-link>
                <button @click="handleLogout" class="block w-full text-left px-3 py-2 rounded-md text-base font-medium text-red-600 hover:bg-red-50">退出登录</button>
              </template>
              <template v-else>
                <router-link to="/login" class="block px-3 py-2 rounded-md text-base font-medium text-gray-600 hover:bg-gray-50" @click="mobileMenuOpen = false">登录</router-link>
                <router-link to="/register" class="block px-3 py-2 rounded-md text-base font-medium text-primary-600 hover:bg-primary-50" @click="mobileMenuOpen = false">注册账号</router-link>
              </template>
            </div>
          </div>
        </div>
      </transition>
    </nav>

    <!-- Main Content -->
    <main class="flex-grow pt-16">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>

    <!-- Footer -->
    <footer class="bg-gray-900 text-white pt-16 pb-8 mt-auto">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-12 mb-12">
          <!-- Brand -->
          <div>
            <div class="flex items-center gap-2 mb-6">
              <div class="w-8 h-8 bg-primary-600 rounded-lg flex items-center justify-center">
                <span class="text-white font-bold text-lg">H</span>
              </div>
              <span class="text-xl font-bold text-white">HotelBooking</span>
            </div>
            <p class="text-gray-400 text-sm leading-relaxed mb-6">
              致力于为您提供全球最优质的住宿预订服务。无论商务出行还是休闲度假，我们都是您值得信赖的伙伴。
            </p>
            <div class="flex space-x-4">
              <!-- Social Icons -->
              <a href="#" class="w-10 h-10 rounded-full bg-gray-800 flex items-center justify-center text-gray-400 hover:bg-primary-600 hover:text-white transition-all">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true"><path fill-rule="evenodd" d="M22 12c0-5.523-4.477-10-10-10S2 6.477 2 12c0 4.991 3.657 9.128 8.438 9.878v-6.987h-2.54V12h2.54V9.797c0-2.506 1.492-3.89 3.777-3.89 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V12h2.773l-.443 2.89h-2.33v6.988C18.343 21.128 22 16.991 22 12z" clip-rule="evenodd" /></svg>
              </a>
              <a href="#" class="w-10 h-10 rounded-full bg-gray-800 flex items-center justify-center text-gray-400 hover:bg-primary-600 hover:text-white transition-all">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 24 24" aria-hidden="true"><path fill-rule="evenodd" d="M12.315 2c2.43 0 2.784.013 3.808.06 1.064.049 1.791.218 2.427.465a4.902 4.902 0 011.772 1.153 4.902 4.902 0 011.153 1.772c.247.636.416 1.363.465 2.427.048 1.067.06 1.407.06 4.123v.08c0 2.643-.012 2.987-.06 4.043-.049 1.064-.218 1.791-.465 2.427a4.902 4.902 0 01-1.153 1.772 4.902 4.902 0 01-1.772 1.153c-.636.247-1.363.416-2.427.465-1.067.048-1.407.06-4.123.06h-.08c-2.643 0-2.987-.012-4.043-.06-1.064-.049-1.791-.218-2.427-.465a4.902 4.902 0 01-1.772-1.153 4.902 4.902 0 01-1.153-1.772c-.247-.636-.416-1.363-.465-2.427-.047-1.024-.06-1.379-.06-3.808v-.63c0-2.43.013-2.784.06-3.808.049-1.064.218-1.791.465-2.427a4.902 4.902 0 011.153-1.772A4.902 4.902 0 015.468 2.373c.636-.247 1.363-.416 2.427-.465C8.901 2.013 9.256 2 11.685 2h.63zm-.081 1.802h-.468c-2.456 0-2.784.011-3.807.058-.975.045-1.504.207-1.857.344-.467.182-.8.398-1.15.748-.35.35-.566.683-.748 1.15-.137.353-.3.882-.344 1.857-.047 1.023-.058 1.351-.058 3.807v.468c0 2.456.011 2.784.058 3.807.045.975.207 1.504.344 1.857.182.466.399.8.748 1.15.35.35.683.566 1.15.748.353.137.882.3 1.857.344 1.054.048 1.37.058 4.041.058h.08c2.597 0 2.917-.01 3.96-.058.976-.045 1.505-.207 1.858-.344.466-.182.8-.398 1.15-.748.35-.35.566-.683.748-1.15.137-.353.3-.882.344-1.857.048-1.055.058-1.37.058-4.041v-.08c0-2.597-.01-2.917-.058-3.96-.045-.976-.207-1.505-.344-1.858a3.097 3.097 0 00-.748-1.15 3.098 3.098 0 00-1.15-.748c-.353-.137-.882-.3-1.857-.344-1.023-.047-1.351-.058-3.807-.058zM12 6.865a5.135 5.135 0 110 10.27 5.135 5.135 0 010-10.27zm0 1.802a3.333 3.333 0 100 6.666 3.333 3.333 0 000-6.666zm5.338-3.205a1.2 1.2 0 110 2.4 1.2 1.2 0 010-2.4z" clip-rule="evenodd" /></svg>
              </a>
            </div>
          </div>

          <!-- Quick Links -->
          <div>
            <h3 class="text-sm font-semibold text-white uppercase tracking-wider mb-4">快速链接</h3>
            <ul class="space-y-3">
              <li><router-link to="/" class="text-gray-400 hover:text-white transition-colors">首页</router-link></li>
              <li><router-link to="/hotels" class="text-gray-400 hover:text-white transition-colors">酒店搜索</router-link></li>
              <li><router-link to="/bookings" class="text-gray-400 hover:text-white transition-colors">我的订单</router-link></li>
              <li><router-link to="/user" class="text-gray-400 hover:text-white transition-colors">个人中心</router-link></li>
            </ul>
          </div>

          <!-- Support -->
          <div>
            <h3 class="text-sm font-semibold text-white uppercase tracking-wider mb-4">客户支持</h3>
            <ul class="space-y-3">
              <li><a href="#" class="text-gray-400 hover:text-white transition-colors">帮助中心</a></li>
              <li><a href="#" class="text-gray-400 hover:text-white transition-colors">隐私政策</a></li>
              <li><a href="#" class="text-gray-400 hover:text-white transition-colors">服务条款</a></li>
              <li><a href="#" class="text-gray-400 hover:text-white transition-colors">联系我们</a></li>
            </ul>
          </div>

          <!-- Contact -->
          <div>
            <h3 class="text-sm font-semibold text-white uppercase tracking-wider mb-4">联系方式</h3>
            <ul class="space-y-3 text-gray-400">
              <li class="flex items-start">
                <svg class="w-5 h-5 mr-3 text-primary-500 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                </svg>
                <span>上海市浦东新区世纪大道88号</span>
              </li>
              <li class="flex items-center">
                <svg class="w-5 h-5 mr-3 text-primary-500 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                </svg>
                <span>contact@hotelbooking.com</span>
              </li>
              <li class="flex items-center">
                <svg class="w-5 h-5 mr-3 text-primary-500 flex-shrink-0" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                </svg>
                <span>400-123-4567</span>
              </li>
            </ul>
          </div>
        </div>

        <div class="border-t border-gray-800 pt-8 flex flex-col md:flex-row justify-between items-center">
          <p class="text-gray-500 text-sm mb-4 md:mb-0">
            © 2024 Hotel Booking System. All rights reserved.
          </p>
          <div class="flex space-x-6 text-sm text-gray-500">
            <span class="hover:text-white cursor-pointer transition-colors">沪ICP备12345678号</span>
            <span class="hover:text-white cursor-pointer transition-colors">隐私安全</span>
          </div>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from './stores'
import { onClickOutside } from '@vueuse/core'

const router = useRouter()
const userStore = useUserStore()

const navItems = [
  { name: '首页', path: '/' },
  { name: '酒店搜索', path: '/hotels' },
  { name: '我的订单', path: '/bookings' }
]

const isScrolled = ref(false)
const showUserMenu = ref(false)
const mobileMenuOpen = ref(false)
const dropdownRef = ref(null)

onClickOutside(dropdownRef, () => {
  showUserMenu.value = false
})

const toggleUserMenu = () => {
  showUserMenu.value = !showUserMenu.value
}

const handleScroll = () => {
  isScrolled.value = window.scrollY > 20
}

const handleLogout = () => {
  userStore.logout()
  showUserMenu.value = false
  mobileMenuOpen.value = false
  router.push('/')
}

onMounted(() => {
  window.addEventListener('scroll', handleScroll)
  userStore.loadUserFromStorage()
})

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>