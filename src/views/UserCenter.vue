<template>
  <div class="user-center">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- 页面标题 -->
      <div class="mb-8">
        <h1 class="text-3xl font-bold text-gray-900 mb-2">个人中心</h1>
        <p class="text-gray-600">管理您的个人信息和偏好设置</p>
      </div>

      <div class="grid grid-cols-1 lg:grid-cols-4 gap-8">
        <!-- 侧边导航 -->
        <div class="lg:col-span-1">
          <div class="card">
            <div class="text-center mb-6">
              <div class="w-20 h-20 bg-primary-100 rounded-full flex items-center justify-center mx-auto mb-4">
                <span class="text-2xl font-bold text-primary-600">
                  {{ userStore.user?.username?.charAt(0).toUpperCase() || 'U' }}
                </span>
              </div>
              <h3 class="text-lg font-semibold text-gray-900">
                {{ userStore.user?.username || '用户名' }}
              </h3>
              <p class="text-sm text-gray-600">{{ userStore.user?.email || '邮箱' }}</p>
            </div>
            
            <nav class="space-y-2">
              <button 
                v-for="tab in tabs" 
                :key="tab.id"
                @click="activeTab = tab.id"
                :class="[
                  'w-full text-left px-4 py-3 rounded-lg font-medium transition-colors flex items-center',
                  activeTab === tab.id
                    ? 'bg-primary-100 text-primary-700'
                    : 'text-gray-700 hover:bg-gray-100'
                ]"
              >
                <svg class="w-5 h-5 mr-3" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" :d="tab.icon"></path>
                </svg>
                {{ tab.name }}
              </button>
            </nav>
          </div>
        </div>

        <!-- 主要内容区域 -->
        <div class="lg:col-span-3">
          <!-- 个人信息 -->
          <div v-if="activeTab === 'profile'" class="card">
            <h2 class="text-xl font-semibold text-gray-900 mb-6">个人信息</h2>
            
            <form @submit.prevent="updateProfileHandler">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">用户名</label>
                  <input 
                    v-model="profileForm.username" 
                    type="text" 
                    class="input-field"
                    required
                  >
                </div>
                
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">邮箱</label>
                  <input 
                    v-model="profileForm.email" 
                    type="email" 
                    class="input-field"
                    required
                  >
                </div>
                
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">真实姓名</label>
                  <input 
                    v-model="profileForm.realName" 
                    type="text" 
                    class="input-field"
                  >
                </div>
                
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">手机号码</label>
                  <input 
                    v-model="profileForm.phone" 
                    type="tel" 
                    class="input-field"
                  >
                </div>
                
                <div class="md:col-span-2">
                  <label class="block text-sm font-medium text-gray-700 mb-2">身份证号</label>
                  <input 
                    v-model="profileForm.idCard" 
                    type="text" 
                    class="input-field"
                  >
                </div>
              </div>
              
              <div class="flex justify-end mt-6">
                <button 
                  type="submit"
                  :disabled="savingProfile"
                  class="btn-primary px-6 py-2"
                >
                  {{ savingProfile ? '保存中...' : '保存修改' }}
                </button>
              </div>
            </form>
          </div>

          <!-- 修改密码 -->
          <div v-else-if="activeTab === 'password'" class="card">
            <h2 class="text-xl font-semibold text-gray-900 mb-6">修改密码</h2>
            
            <form @submit.prevent="changePassword">
              <div class="space-y-6 max-w-md">
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">当前密码</label>
                  <input 
                    v-model="passwordForm.currentPassword" 
                    type="password" 
                    class="input-field"
                    required
                  >
                </div>
                
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">新密码</label>
                  <input 
                    v-model="passwordForm.newPassword" 
                    type="password" 
                    class="input-field"
                    required
                  >
                </div>
                
                <div>
                  <label class="block text-sm font-medium text-gray-700 mb-2">确认新密码</label>
                  <input 
                    v-model="passwordForm.confirmPassword" 
                    type="password" 
                    class="input-field"
                    required
                  >
                </div>
              </div>
              
              <div class="flex justify-end mt-6">
                <button 
                  type="submit"
                  :disabled="changingPassword"
                  class="btn-primary px-6 py-2"
                >
                  {{ changingPassword ? '修改中...' : '修改密码' }}
                </button>
              </div>
            </form>
          </div>

          <!-- 我的收藏 -->
          <div v-else-if="activeTab === 'favorites'" class="card">
            <h2 class="text-xl font-semibold text-gray-900 mb-6">我的收藏</h2>
            
            <div v-if="favorites.length > 0" class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div 
                v-for="hotel in favorites" 
                :key="hotel.id"
                class="border border-gray-200 rounded-lg p-4 hover:shadow-md transition-shadow"
              >
                <div class="flex gap-4">
                  <img 
                    :src="hotel.images[0]" 
                    :alt="hotel.name"
                    class="w-24 h-24 object-cover rounded-lg"
                  >
                  <div class="flex-1">
                    <h3 class="font-semibold text-gray-900 mb-2">{{ hotel.name }}</h3>
                    <div class="flex items-center mb-2">
                      <span class="text-yellow-400 mr-1">★</span>
                      <span class="text-sm text-gray-600">{{ hotel.avgRating }}</span>
                      <span class="text-sm text-gray-500 ml-2">
                        ¥{{ hotel.minPrice }}起/晚
                      </span>
                    </div>
                    <div class="flex space-x-2">
                      <button 
                        @click="goToHotel(hotel.id)"
                        class="text-primary-600 hover:text-primary-700 text-sm font-medium"
                      >
                        查看详情
                      </button>
                      <button 
                        @click="removeFavorite(hotel.id)"
                        class="text-red-600 hover:text-red-700 text-sm font-medium"
                      >
                        取消收藏
                      </button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            
            <div v-else class="text-center py-12">
              <svg class="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"></path>
              </svg>
              <h3 class="text-lg font-medium text-gray-900 mb-2">暂无收藏</h3>
              <p class="text-gray-600 mb-6">您还没有收藏任何酒店</p>
              <router-link 
                to="/hotels"
                class="btn-primary"
              >
                去发现酒店
              </router-link>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores'
import { updateUserProfile, updatePassword, getUserProfile } from '@/api/auth'
import { getFavoriteList, removeFavorite as removeFavoriteApi } from '@/api/favorite'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()
const userStore = useUserStore()

// 标签页配置
const tabs = [
  { 
    id: 'profile', 
    name: '个人信息',
    icon: 'M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z'
  },
  { 
    id: 'password', 
    name: '修改密码',
    icon: 'M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z'
  },
  { 
    id: 'favorites', 
    name: '我的收藏',
    icon: 'M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z'
  },
]

const activeTab = ref('profile')

// 表单数据
const profileForm = ref({
  username: '',
  email: '',
  realName: '',
  phone: '',
  idCard: ''
})

const passwordForm = ref({
  currentPassword: '',
  newPassword: '',
  confirmPassword: ''
})

// 状态
const savingProfile = ref(false)
const changingPassword = ref(false)

// 收藏列表数据
const favorites = ref([])
const loadingFavorites = ref(false)

// 方法
const updateProfileHandler = async () => {
  savingProfile.value = true
  try {
    await updateUserProfile({
      email: profileForm.value.email,
      phone: profileForm.value.phone || '',
      realName: profileForm.value.realName || '',
      idCard: profileForm.value.idCard || ''
    })
    
    // 更新本地用户信息
    userStore.user = {
      ...userStore.user,
      ...profileForm.value
    }
    
    ElMessage.success('个人信息更新成功')
  } catch (error) {
    console.error('更新失败:', error)
    ElMessage.error(error.response?.data?.message || error.message || '更新失败，请重试')
  } finally {
    savingProfile.value = false
  }
}

const changePassword = async () => {
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) {
    ElMessage.warning('新密码和确认密码不匹配')
    return
  }
  
  if (passwordForm.value.newPassword.length < 6) {
    ElMessage.warning('密码长度至少6位')
    return
  }
  
  changingPassword.value = true
  try {
    await updatePassword({
      currentPassword: passwordForm.value.currentPassword,
      newPassword: passwordForm.value.newPassword,
      confirmPassword: passwordForm.value.confirmPassword
    })
    
    // 重置表单
    passwordForm.value = {
      currentPassword: '',
      newPassword: '',
      confirmPassword: ''
    }
    
    ElMessage.success('密码修改成功')
  } catch (error) {
    console.error('密码修改失败:', error)
    ElMessage.error(error.response?.data?.message || error.message || '密码修改失败，请重试')
  } finally {
    changingPassword.value = false
  }
}

const goToHotel = (hotelId) => {
  router.push(`/hotel/${hotelId}`)
}

const removeFavorite = async (hotelId) => {
  try {
    await ElMessageBox.confirm('确定要取消收藏这个酒店吗？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await removeFavoriteApi(hotelId)
    favorites.value = favorites.value.filter(hotel => hotel.id !== hotelId)
    ElMessage.success('已取消收藏')
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error(error.response?.data?.message || error.message || '取消收藏失败')
    }
  }
}

const loadUserData = async () => {
  try {
    // 获取最新用户信息（request.js拦截器已处理，直接返回data内容）
    const userData = await getUserProfile()
    if (userData) {
      userStore.user = userData
      profileForm.value = {
        username: userData.username || '',
        email: userData.email || '',
        realName: userData.realName || '',
        phone: userData.phone || '',
        idCard: userData.idCard || ''
      }
    }
  } catch (error) {
    console.error('加载用户信息失败:', error)
    // 使用本地存储的用户信息
    if (userStore.user) {
      profileForm.value = {
        username: userStore.user.username || '',
        email: userStore.user.email || '',
        realName: userStore.user.realName || '',
        phone: userStore.user.phone || '',
        idCard: userStore.user.idCard || ''
      }
    }
  }
}

const loadFavorites = async () => {
  loadingFavorites.value = true
  try {
    const res = await getFavoriteList({ page: 1, size: 100 })
    // 后端 PageVO 返回 records 字段
    const records = res?.records || []
    console.log('收藏列表数据:', res, records)
    favorites.value = records.map(item => ({
      id: item.hotelId,
      name: item.name,
      avgRating: item.avgRating || 0,
      minPrice: item.minPrice || 0,
      images: item.images || ['https://images.unsplash.com/photo-1564501049412-61c2a3083791?w=400&h=300&fit=crop']
    }))
  } catch (error) {
    console.error('加载收藏列表失败:', error)
    ElMessage.error(error.response?.data?.message || error.message || '加载收藏列表失败')
  } finally {
    loadingFavorites.value = false
  }
}

onMounted(() => {
  loadUserData()
  loadFavorites()
})
</script>