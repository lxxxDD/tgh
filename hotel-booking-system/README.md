# 酒店预订管理系统 - Vue 3 前端

一个基于 Vue 3 + Vite 的现代酒店预订管理系统前端应用。

## 技术栈

- **Vue 3** - 渐进式 JavaScript 框架
- **Vite** - 下一代前端构建工具
- **Vue Router** - Vue.js 官方路由管理器
- **Pinia** - Vue 状态管理库
- **Tailwind CSS** - 实用优先的 CSS 框架
- **Anime.js** - JavaScript 动画库

## 功能特性

### 核心功能
- 🏨 酒店搜索和筛选
- 📅 日期选择和预订管理
- 🔐 用户认证和授权
- 👤 个人中心管理
- 📱 响应式设计
- 🎨 现代化UI设计

### 页面结构
- **首页** - 酒店搜索和推荐展示
- **酒店列表** - 高级筛选和排序功能
- **酒店详情** - 详细信息、房间类型和预订
- **我的订单** - 订单管理和状态跟踪
- **个人中心** - 用户信息管理和设置

## 项目结构

```
src/
├── components/          # 可复用组件
│   ├── HotelCard.vue
│   ├── LoadingSpinner.vue
│   ├── EmptyState.vue
│   └── Modal.vue
├── views/              # 页面组件
│   ├── Home.vue
│   ├── Hotels.vue
│   ├── HotelDetail.vue
│   ├── Bookings.vue
│   └── UserCenter.vue
├── stores/             # 状态管理
│   └── index.js
├── router/             # 路由配置
│   └── index.js
├── utils/              # 工具函数
│   └── index.js
├── api/                # API接口
├── assets/             # 静态资源
├── App.vue             # 根组件
├── main.js             # 入口文件
└── style.css           # 全局样式
```

## 快速开始

### 安装依赖

```bash
npm install
```

### 开发环境运行

```bash
npm run dev
```

### 生产环境构建

```bash
npm run build
```

### 预览构建结果

```bash
npm run preview
```

## 接口预留

项目中预留了与后端API对接的位置，主要接口包括：

### 用户管理
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/register` - 用户注册
- `GET /api/user/profile` - 获取用户信息
- `PUT /api/user/profile` - 更新用户信息

### 酒店管理
- `GET /api/hotels` - 获取酒店列表
- `GET /api/hotels/{id}` - 获取酒店详情
- `GET /api/hotels/{id}/rooms` - 获取酒店房间列表

### 预订管理
- `POST /api/bookings` - 创建预订
- `GET /api/bookings` - 获取用户预订列表
- `DELETE /api/bookings/{id}` - 取消预订

## 设计特色

### 视觉设计
- 采用现代简约设计风格
- 深蓝色主色调，体现专业和可信赖
- 橙色强调色，增加活力感
- 精心设计的卡片式布局

### 交互体验
- 平滑的页面切换动画
- 响应式交互反馈
- 直观的用户界面
- 优雅的加载状态

### 技术特点
- 组件化开发，代码复用性高
- 响应式设计，适配多设备
- 状态管理清晰，数据流明确
- TypeScript支持，类型安全

## 开发说明

### 组件使用
项目中提供了多个可复用组件：

- **HotelCard** - 酒店卡片组件
- **LoadingSpinner** - 加载动画组件
- **EmptyState** - 空状态组件
- **Modal** - 模态框组件

### 状态管理
使用 Pinia 进行状态管理，主要包含：

- **UserStore** - 用户状态管理
- **HotelStore** - 酒店数据管理
- **BookingStore** - 预订状态管理

### 样式系统
基于 Tailwind CSS 构建，提供：
- 统一的设计规范
- 响应式布局支持
- 自定义组件样式
- 动画效果支持

## 浏览器支持

- Chrome (最新版本)
- Firefox (最新版本)
- Safari (最新版本)
- Edge (最新版本)

## 贡献指南

欢迎提交 Issue 和 Pull Request 来改进项目。

## 许可证

MIT License