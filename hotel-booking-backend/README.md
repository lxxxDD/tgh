# 酒店预订管理系统后端服务

## 项目简介

酒店预订管理系统是一个基于Spring Boot 3.2 + MyBatis-Plus 3.5.3 + MySQL 8 + JWT的现代化酒店预订平台后端服务。

## 技术栈

- **框架**: Spring Boot 3.2.0
- **数据访问**: MyBatis-Plus 3.5.3
- **数据库**: MySQL 8.0

- **认证**: JWT 0.12.3
- **文档**: Knife4j (Swagger 3增强版)
- **安全**: Spring Security 6
- **工具**: Lombok, Hutool, Apache Commons
- **构建工具**: Maven 3.8+
- **Java版本**: JDK 17

## 功能特性

### 用户管理模块
- 用户注册和登录
- JWT Token认证
- 用户信息管理
- 密码修改功能

### 酒店管理模块
- 酒店列表查询（支持多条件筛选和分页）
- 酒店详情查看
- 房间类型管理
- 酒店设施和图片管理

### 订单管理模块
- 创建酒店预订
- 订单状态管理
- 用户订单列表查询
- 订单取消功能

### 收藏管理模块
- 添加/取消收藏
- 收藏列表查询

### 评价管理模块
- 提交酒店评价
- 查看酒店评价列表

## 项目结构

```
hotel-booking-backend/
├── src/main/java/com/hotelbooking/
│   ├── entity/           # 实体类
│   ├── dto/              # 数据传输对象
│   ├── vo/               # 视图对象
│   ├── mapper/           # 数据访问层
│   ├── service/          # 业务逻辑层
│   │   └── impl/         # 业务逻辑实现
│   ├── controller/       # 控制器层
│   ├── config/           # 配置类
│   ├── security/         # 安全配置
│   ├── utils/            # 工具类
│   ├── common/           # 公共类
│   ├── enums/            # 枚举类
│   └── exception/        # 异常处理
├── src/main/resources/
│   ├── mapper/           # MyBatis映射文件
│   ├── application.yml   # 应用配置
│   └── mybatis-config.xml # MyBatis配置
└── pom.xml               # Maven配置
```

## 快速开始

### 环境要求
- JDK 17
- Maven 3.8+
- MySQL 8.0


### 数据库配置
1. 创建数据库
```sql
CREATE DATABASE hotel_booking CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行数据库脚本
```sql
-- 执行项目中的 schema.sql 文件创建表结构
```

### 配置文件
修改 `src/main/resources/application.yml` 文件：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/hotel_booking?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
    username: your_username
    password: your_password
  


jwt:
  secret: your_jwt_secret_key_here
```

### 运行项目
1. 克隆项目
```bash
git clone https://github.com/your-username/hotel-booking-backend.git
cd hotel-booking-backend
```

2. 安装依赖
```bash
mvn clean install
```

3. 运行项目
```bash
mvn spring-boot:run
```

项目启动后，可以通过以下地址访问：
- API文档 (Knife4j): http://localhost:8080/api/doc.html
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- API接口: http://localhost:8080/api

## API接口文档

### 认证接口
- `POST /api/auth/register` - 用户注册
- `POST /api/auth/login` - 用户登录
- `GET /api/auth/user/profile` - 获取用户信息
- `PUT /api/auth/user/profile` - 更新用户信息
- `POST /api/auth/user/password` - 修改密码

### 酒店接口
- `GET /api/hotels` - 获取酒店列表
- `GET /api/hotels/{id}` - 获取酒店详情
- `GET /api/hotels/{id}/rooms` - 获取酒店房间列表

### 订单接口
- `POST /api/bookings` - 创建预订
- `GET /api/bookings` - 获取用户订单列表
- `GET /api/bookings/{id}` - 获取订单详情
- `DELETE /api/bookings/{id}` - 取消订单

### 收藏接口
- `GET /api/favorites` - 获取用户收藏列表
- `POST /api/favorites/{hotelId}` - 添加收藏
- `DELETE /api/favorites/{hotelId}` - 取消收藏

### 评价接口
- `GET /api/reviews/hotel/{hotelId}` - 获取酒店评价列表
- `POST /api/reviews` - 提交评价

## 核心功能说明

### JWT认证
- 使用JWT Token进行用户身份验证
- Access Token有效期30分钟
- Refresh Token有效期7天
- 支持Token刷新机制

### 数据校验
- 使用Jakarta Bean Validation进行参数校验
- 统一的异常处理机制
- 详细的错误信息返回

### 分页查询
- 支持多条件组合筛选
- 支持排序和分页
- 统一的响应格式

### 事务管理
- 使用Spring声明式事务
- 保证数据一致性
- 支持回滚机制

## 开发规范

### 代码规范
- 使用Lombok简化代码
- 遵循Java命名规范
- 添加必要的注释说明

### 接口规范
- RESTful API设计
- 统一的响应格式
- 标准的HTTP状态码

### 安全规范
- 密码使用BCrypt加密
- JWT Token认证
- 输入参数校验
- SQL注入防护

## 部署说明

### 开发环境
```bash
mvn spring-boot:run -Dspring.profiles.active=dev
```

### 生产环境
```bash
mvn clean package
java -jar target/hotel-booking-backend-1.0.0.jar --spring.profiles.active=prod
```

## 贡献指南

1. Fork项目
2. 创建功能分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建Pull Request

## 许可证

本项目采用Apache License 2.0许可证 - 查看 [LICENSE](LICENSE) 文件了解详情。

## 联系方式

- 项目维护者: Hotel Booking Team
- 邮箱: support@hotelbooking.com
- 项目地址: https://github.com/your-username/hotel-booking-backend

## 更新日志

### v1.0.0 (2024-01-20)
- 初始版本发布
- 完成基础功能开发
- 支持用户认证、酒店管理、订单管理等核心功能
- 集成Swagger文档
- 完善异常处理机制