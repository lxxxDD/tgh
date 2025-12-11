import { createRouter, createWebHistory } from 'vue-router'
import { ElMessage } from 'element-plus'

const Home = () => import('../views/Home.vue')
const Hotels = () => import('../views/Hotels.vue')
const HotelDetail = () => import('../views/HotelDetail.vue')
const Bookings = () => import('../views/Bookings.vue')
const UserCenter = () => import('../views/UserCenter.vue')
const Login = () => import('../views/Login.vue')
const Register = () => import('../views/Register.vue')
const ForgotPassword = () => import('../views/ForgotPassword.vue')
const AdminDashboard = () => import('../views/admin/AdminDashboard.vue')

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/hotels',
    name: 'Hotels',
    component: Hotels
  },
  {
    path: '/hotel/:id',
    name: 'HotelDetail',
    component: HotelDetail
  },
  {
    path: '/bookings',
    name: 'Bookings',
    component: Bookings,
    meta: { requiresAuth: true }
  },
  {
    path: '/user',
    name: 'UserCenter',
    component: UserCenter,
    meta: { requiresAuth: true }
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { guestOnly: true }
  },
  {
    path: '/register',
    name: 'Register',
    component: Register,
    meta: { guestOnly: true }
  },
  {
    path: '/forgot-password',
    name: 'ForgotPassword',
    component: ForgotPassword,
    meta: { guestOnly: true }
  },
  {
    path: '/admin',
    name: 'AdminDashboard',
    component: AdminDashboard,
    meta: { requiresAuth: true, requiresAdmin: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  const isLoggedIn = !!token
  const role = localStorage.getItem('role')

  // 需要登录的页面
  if (to.meta.requiresAuth && !isLoggedIn) {
    ElMessage.warning('请先登录')
    next({ name: 'Login', query: { redirect: to.fullPath } })
    return
  }

  // 需要管理员权限的页面
  if (to.meta.requiresAdmin && role !== 'admin') {
    ElMessage.error('无权限访问管理后台')
    next({ name: 'Home' })
    return
  }

  // 已登录用户不能访问的页面（登录、注册、忘记密码）
  if (to.meta.guestOnly && isLoggedIn) {
    next({ name: 'Home' })
    return
  }

  next()
})

export default router
