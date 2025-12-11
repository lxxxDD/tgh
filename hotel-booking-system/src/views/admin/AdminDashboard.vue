<template>
  <div class="admin-dashboard">
    <!-- 顶部导航 -->
    <div class="admin-header">
      <h1 class="text-2xl font-bold text-gray-800">管理后台</h1>
      <div class="flex items-center gap-4">
        <span class="text-gray-600">欢迎，{{ userStore.user?.username || 'admin' }}</span>
        <el-button type="danger" size="small" @click="handleLogout">退出登录</el-button>
      </div>
    </div>

    <!-- Tab导航 -->
    <el-tabs v-model="activeTab" class="admin-tabs">
      <el-tab-pane label="酒店管理" name="hotels">
        <HotelManagement />
      </el-tab-pane>
      <el-tab-pane label="订单管理" name="orders">
        <OrderManagement />
      </el-tab-pane>
      <el-tab-pane label="用户管理" name="users">
        <UserManagement />
      </el-tab-pane>
      <el-tab-pane label="评价管理" name="reviews">
        <ReviewManagement />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/stores'
import HotelManagement from './components/HotelManagement.vue'
import OrderManagement from './components/OrderManagement.vue'
import UserManagement from './components/UserManagement.vue'
import ReviewManagement from './components/ReviewManagement.vue'

const router = useRouter()
const userStore = useUserStore()
const activeTab = ref('hotels')

onMounted(() => {
  // 检查是否是管理员
  const role = localStorage.getItem('role')
  const token = localStorage.getItem('token')
  if (!token) {
    router.push('/login')
  } else if (role !== 'admin') {
    ElMessage.error('无权限访问管理后台')
    router.push('/')
  }
})

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    userStore.logout()
    ElMessage.success('已退出登录')
    router.push('/login')
  }).catch(() => {})
}
</script>

<style scoped>
.admin-dashboard {
  min-height: 100vh;
  background-color: #f5f7fa;
  padding: 20px;
}

.admin-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: white;
  padding: 16px 24px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

.admin-tabs {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
}

:deep(.el-tabs__content) {
  padding: 20px 0;
}
</style>
