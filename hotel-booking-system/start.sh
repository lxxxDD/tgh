#!/bin/bash

echo "🚀 启动酒店预订管理系统"
echo "========================"

# 检查Node.js是否安装
if ! command -v node &> /dev/null; then
    echo "❌ Node.js 未安装，请先安装Node.js"
    exit 1
fi

# 检查npm是否安装
if ! command -v npm &> /dev/null; then
    echo "❌ npm 未安装，请先安装npm"
    exit 1
fi

echo "✅ Node.js 和 npm 已安装"
echo ""

# 安装依赖
echo "📦 正在安装项目依赖..."
npm install

if [ $? -eq 0 ]; then
    echo "✅ 依赖安装成功"
    echo ""
    
    # 启动开发服务器
    echo "🌟 正在启动开发服务器..."
    echo ""
    echo "📝 项目信息："
    echo "   - 访问地址: http://localhost:3000"
    echo "   - 测试账号: admin / password"
    echo ""
    echo "🎨 功能特色："
    echo "   - 现代化UI设计"
    echo "   - 响应式布局"
    echo "   - 丰富的交互动画"
    echo "   - 完整的用户流程"
    echo ""
    
    npm run dev
else
    echo "❌ 依赖安装失败，请检查网络连接"
    exit 1
fi