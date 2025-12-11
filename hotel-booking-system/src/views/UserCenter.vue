<template>
  <div class="user-center bg-gray-50 min-h-screen py-8">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <h1 class="text-3xl font-bold text-gray-900 mb-8">账户设置</h1>

      <div class="grid grid-cols-1 lg:grid-cols-12 gap-8">
        <!-- 侧边导航 -->
        <div class="lg:col-span-3">
          <div class="bg-white rounded-2xl shadow-sm border border-gray-200 overflow-hidden sticky top-24">
            <div class="p-6 text-center border-b border-gray-100 bg-gradient-to-b from-primary-50 to-white">
              <div class="w-24 h-24 bg-white rounded-full flex items-center justify-center mx-auto mb-4 shadow-md border-4 border-white text-3xl font-bold text-primary-600">
                {{ userStore.user?.username?.charAt(0).toUpperCase() || 'U' }}
              </div>
              <h3 class="text-lg font-bold text-gray-900 truncate">
                {{ userStore.user?.username || '用户名' }}
              </h3>
              <p class="text-sm text-gray-500 truncate mt-1">{{ userStore.user?.email }}</p>
            </div>
            
            <nav class="p-3 space-y-1">
              <button 
                v-for="tab in tabs" 
                :key="tab.id"
                @click="activeTab = tab.id"
                :class="[
                  'w-full text-left px-4 py-3 rounded-xl font-medium transition-all flex items-center',
                  activeTab === tab.id
                    ? 'bg-primary-50 text-primary-700 shadow-sm'
                    : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900'
                ]"
              >
                <component :is="tab.iconComp" class="w-5 h-5 mr-3" />
                {{ tab.name }}
              </button>
            </nav>
          </div>
        </div>

        <!-- 主要内容区域 -->
        <div class="lg:col-span-9">
          <div class="bg-white rounded-2xl shadow-sm border border-gray-200 min-h-[500px] p-8">
            <!-- 个人信息 -->
            <div v-if="activeTab === 'profile'" class="animate-fade-in">
              <div class="mb-8 pb-4 border-b border-gray-100">
                <h2 class="text-xl font-bold text-gray-900">个人资料</h2>
                <p class="text-gray-500 text-sm mt-1">管理您的基本信息和联系方式</p>
              </div>
              
              <form @submit.prevent="updateProfileHandler" class="max-w-3xl">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
                  <div class="group">
                    <label class="block text-sm font-medium text-gray-700 mb-1">用户名</label>
                    <input v-model="profileForm.username" type="text" class="input-field bg-gray-50 text-gray-500 cursor-not-allowed" disabled>
                  </div>
                  <div class="group">
                    <label class="block text-sm font-medium text-gray-700 mb-1">邮箱</label>
                    <input v-model="profileForm.email" type="email" class="input-field" required>
                  </div>
                  <div class="group">
                    <label class="block text-sm font-medium text-gray-700 mb-1">真实姓名</label>
                    <input v-model="profileForm.realName" type="text" class="input-field" placeholder="用于酒店入住登记">
                  </div>
                  <div class="group">
                    <label class="block text-sm font-medium text-gray-700 mb-1">手机号码</label>
                    <input v-model="profileForm.phone" type="tel" class="input-field" placeholder="接收预订通知">
                  </div>
                  <div class="md:col-span-2 group">
                    <label class="block text-sm font-medium text-gray-700 mb-1">身份证号</label>
                    <input v-model="profileForm.idCard" type="text" class="input-field" placeholder="用于酒店入住登记">
                  </div>
                </div>
                
                <div class="flex justify-end pt-6 border-t border-gray-100">
                  <button type="submit" :disabled="savingProfile" class="btn-primary px-8 py-2.5 shadow-lg shadow-primary-600/20">
                    {{ savingProfile ? '保存中...' : '保存修改' }}
                  </button>
                </div>
              </form>
            </div>

            <!-- 修改密码 -->
            <div v-else-if="activeTab === 'password'" class="animate-fade-in">
              <div class="mb-8 pb-4 border-b border-gray-100">
                <h2 class="text-xl font-bold text-gray-900">安全设置</h2>
                <p class="text-gray-500 text-sm mt-1">更新您的账户密码</p>
              </div>
              
              <form @submit.prevent="changePassword" class="max-w-md">
                <div class="space-y-6 mb-8">
                  <div class="group">
                    <label class="block text-sm font-medium text-gray-700 mb-1">当前密码</label>
                    <input v-model="passwordForm.currentPassword" type="password" class="input-field" required>
                  </div>
                  <div class="group">
                    <label class="block text-sm font-medium text-gray-700 mb-1">新密码</label>
                    <input v-model="passwordForm.newPassword" type="password" class="input-field" required placeholder="至少6位">
                  </div>
                  <div class="group">
                    <label class="block text-sm font-medium text-gray-700 mb-1">确认新密码</label>
                    <input v-model="passwordForm.confirmPassword" type="password" class="input-field" required placeholder="重复新密码">
                  </div>
                </div>
                
                <div class="flex justify-start">
                  <button type="submit" :disabled="changingPassword" class="btn-primary px-8 py-2.5 shadow-lg shadow-primary-600/20">
                    {{ changingPassword ? '更新中...' : '更新密码' }}
                  </button>
                </div>
              </form>
            </div>

            <!-- 我的收藏 -->
            <div v-else-if="activeTab === 'favorites'" class="animate-fade-in">
              <div class="mb-8 pb-4 border-b border-gray-100 flex justify-between items-end">
                <div>
                  <h2 class="text-xl font-bold text-gray-900">我的收藏</h2>
                  <p class="text-gray-500 text-sm mt-1">您收藏的心仪酒店</p>
                </div>
                <span class="text-sm text-gray-500">{{ favorites.length }} 家酒店</span>
              </div>
              
              <div v-if="loadingFavorites" class="flex justify-center py-12">
                <LoadingSpinner />
              </div>

              <div v-else-if="favorites.length > 0" class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <!-- 复用 HotelCard 组件，并添加移除按钮 -->
                <div v-for="hotel in favorites" :key="hotel.id" class="relative group">
                  <HotelCard :hotel="hotel" />
                  <button 
                    @click.stop="removeFavorite(hotel.id)"
                    class="absolute top-2 right-2 bg-white/90 p-2 rounded-full text-red-500 hover:bg-red-50 transition-colors shadow-sm opacity-0 group-hover:opacity-100"
                    title="取消收藏"
                  >
                    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20">
                      <path fill-rule="evenodd" d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z" clip-rule="evenodd" />
                    </svg>
                  </button>
                </div>
              </div>
              
              <div v-else class="text-center py-16 bg-gray-50 rounded-xl border border-dashed border-gray-300">
                <div class="text-gray-400 mb-4">
                  <svg class="w-16 h-16 mx-auto" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z" />
                  </svg>
                </div>
                <h3 class="text-lg font-medium text-gray-900 mb-2">暂无收藏</h3>
                <p class="text-gray-500 mb-6">您还没有收藏任何酒店</p>
                <router-link to="/hotels" class="btn-primary">去发现酒店</router-link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, defineComponent, h } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores'
import { updateUserProfile, updatePassword, getUserProfile } from '@/api/auth'
import { getFavoriteList, removeFavorite as removeFavoriteApi } from '@/api/favorite'
import { ElMessage, ElMessageBox } from 'element-plus'
import HotelCard from '../components/HotelCard.vue'
import LoadingSpinner from '../components/LoadingSpinner.vue'

// 图标组件
const UserIcon = defineComponent({ render: () => h('svg', { fill: 'none', viewBox: '0 0 24 24', stroke: 'currentColor' }, [h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z' })]) })
const LockIcon = defineComponent({ render: () => h('svg', { fill: 'none', viewBox: '0 0 24 24', stroke: 'currentColor' }, [h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z' })]) })
const HeartIcon = defineComponent({ render: () => h('svg', { fill: 'none', viewBox: '0 0 24 24', stroke: 'currentColor' }, [h('path', { 'stroke-linecap': 'round', 'stroke-linejoin': 'round', 'stroke-width': '2', d: 'M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z' })]) })

const router = useRouter()
const userStore = useUserStore()

const tabs = [
  { id: 'profile', name: '个人信息', iconComp: UserIcon },
  { id: 'password', name: '修改密码', iconComp: LockIcon },
  { id: 'favorites', name: '我的收藏', iconComp: HeartIcon },
]

const activeTab = ref('profile')
const profileForm = ref({ username: '', email: '', realName: '', phone: '', idCard: '' })
const passwordForm = ref({ currentPassword: '', newPassword: '', confirmPassword: '' })
const savingProfile = ref(false)
const changingPassword = ref(false)
const favorites = ref([])
const loadingFavorites = ref(false)

// 方法实现保持与原逻辑一致，仅增强 UI 交互反馈
const updateProfileHandler = async () => {
  savingProfile.value = true
  try {
    await updateUserProfile(profileForm.value)
    userStore.user = { ...userStore.user, ...profileForm.value }
    ElMessage.success('个人信息更新成功')
  } catch (error) {
    ElMessage.error(error.message || '更新失败')
  } finally {
    savingProfile.value = false
  }
}

const changePassword = async () => {
  if (passwordForm.value.newPassword !== passwordForm.value.confirmPassword) return ElMessage.warning('密码不一致')
  if (passwordForm.value.newPassword.length < 6) return ElMessage.warning('密码太短')
  
  changingPassword.value = true
  try {
    await updatePassword(passwordForm.value)
    passwordForm.value = { currentPassword: '', newPassword: '', confirmPassword: '' }
    ElMessage.success('密码修改成功')
  } catch (error) {
    ElMessage.error(error.message || '修改失败')
  } finally {
    changingPassword.value = false
  }
}

const removeFavorite = async (id) => {
  try {
    await ElMessageBox.confirm('确定取消收藏？', '提示', { type: 'warning', confirmButtonText: '确定', cancelButtonText: '取消' })
    await removeFavoriteApi(id)
    favorites.value = favorites.value.filter(h => h.id !== id)
    ElMessage.success('已取消收藏')
  } catch (e) { /* ignore */ }
}

const loadData = async () => {
  // 加载用户信息
  try {
    const user = await getUserProfile()
    if (user) {
      userStore.user = user
      profileForm.value = { ...profileForm.value, ...user }
    }
  } catch (e) { console.error(e) }

  // 加载收藏
  loadingFavorites.value = true
  try {
    const res = await getFavoriteList({ page: 1, size: 50 })
    favorites.value = (res.records || []).map(item => ({
      id: item.hotelId,
      name: item.name,
      avgRating: item.avgRating || 0,
      minPrice: item.minPrice || 0,
      images: item.images || [],
      address: item.address || '',
      starRating: item.starRating || 0,
      facilities: item.facilities || []
    }))
  } catch (e) { console.error(e) } finally { loadingFavorites.value = false }
}

onMounted(loadData)
</script>

<style scoped>
.animate-fade-in { animation: fadeIn 0.4s ease-out; }
@keyframes fadeIn { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }
</style>