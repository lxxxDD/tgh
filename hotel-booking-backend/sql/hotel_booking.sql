-- Create Database
CREATE DATABASE IF NOT EXISTS hotel_booking DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE hotel_booking;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像URL',
  `status` int(11) DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ----------------------------
-- Table structure for hotel
-- ----------------------------
DROP TABLE IF EXISTS `hotel`;
CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '酒店ID',
  `name` varchar(100) NOT NULL COMMENT '酒店名称',
  `address` varchar(255) NOT NULL COMMENT '地址',
  `description` text COMMENT '酒店描述',
  `star_rating` int(11) DEFAULT NULL COMMENT '星级评分',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `check_in_time` varchar(20) DEFAULT NULL COMMENT '入住时间',
  `check_out_time` varchar(20) DEFAULT NULL COMMENT '退房时间',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `avg_rating` decimal(2,1) DEFAULT 0.0 COMMENT '平均评分',
  `review_count` int(11) DEFAULT 0 COMMENT '评价数量',
  `min_price` decimal(10,2) DEFAULT NULL COMMENT '最低价格',
  `status` int(11) DEFAULT 1 COMMENT '状态：1-营业，0-停业',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店表';

-- ----------------------------
-- Table structure for room_type
-- ----------------------------
DROP TABLE IF EXISTS `room_type`;
CREATE TABLE `room_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '房间类型ID',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店ID',
  `room_type` varchar(50) NOT NULL COMMENT '房间类型名称',
  `area` decimal(10,2) DEFAULT NULL COMMENT '面积（平方米）',
  `bed_type` varchar(50) DEFAULT NULL COMMENT '床型',
  `max_occupancy` int(11) DEFAULT NULL COMMENT '最大入住人数',
  `price` decimal(10,2) NOT NULL COMMENT '价格',
  `original_price` decimal(10,2) DEFAULT NULL COMMENT '原价',
  `availability` int(11) DEFAULT 1 COMMENT '是否可预订：1-可订，0-满房',
  `stock` int(11) DEFAULT 0 COMMENT '库存数量',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间类型表';

-- ----------------------------
-- Table structure for booking_order
-- ----------------------------
DROP TABLE IF EXISTS `booking_order`;
CREATE TABLE `booking_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店ID',
  `room_type_id` bigint(20) NOT NULL COMMENT '房间类型ID',
  `hotel_name` varchar(100) DEFAULT NULL COMMENT '酒店名称',
  `room_type` varchar(50) DEFAULT NULL COMMENT '房间类型',
  `guest_name` varchar(50) DEFAULT NULL COMMENT '入住人姓名',
  `guest_phone` varchar(20) DEFAULT NULL COMMENT '入住人电话',
  `guest_id_card` varchar(20) DEFAULT NULL COMMENT '入住人身份证号',
  `check_in_date` date NOT NULL COMMENT '入住日期',
  `check_out_date` date NOT NULL COMMENT '退房日期',
  `nights` int(11) DEFAULT NULL COMMENT '入住天数',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `status` varchar(20) DEFAULT 'pending' COMMENT '订单状态',
  `booking_time` datetime DEFAULT NULL COMMENT '预订时间',
  `special_requests` varchar(255) DEFAULT NULL COMMENT '特殊要求',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- Table structure for hotel_review
-- ----------------------------
DROP TABLE IF EXISTS `hotel_review`;
CREATE TABLE `hotel_review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评价ID',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `rating` int(11) DEFAULT NULL COMMENT '评分1-5',
  `content` text COMMENT '评价内容',
  `review_date` date DEFAULT NULL COMMENT '评价日期',
  `status` int(11) DEFAULT 1 COMMENT '状态：1-显示，0-隐藏',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店评价表';

-- ----------------------------
-- Table structure for user_favorite
-- ----------------------------
DROP TABLE IF EXISTS `user_favorite`;
CREATE TABLE `user_favorite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_hotel` (`user_id`,`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户收藏表';

-- ----------------------------
-- Table structure for hotel_facility
-- ----------------------------
DROP TABLE IF EXISTS `hotel_facility`;
CREATE TABLE `hotel_facility` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设施ID',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店ID',
  `facility` varchar(50) DEFAULT NULL COMMENT '设施标识',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店设施表';

-- ----------------------------
-- Table structure for hotel_image
-- ----------------------------
DROP TABLE IF EXISTS `hotel_image`;
CREATE TABLE `hotel_image` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `hotel_id` bigint(20) NOT NULL COMMENT '酒店ID',
  `image_url` varchar(255) DEFAULT NULL COMMENT '图片URL',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店图片表';

-- ----------------------------
-- Table structure for room_amenity
-- ----------------------------
DROP TABLE IF EXISTS `room_amenity`;
CREATE TABLE `room_amenity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '设施ID',
  `room_type_id` bigint(20) NOT NULL COMMENT '房间类型ID',
  `amenity` varchar(50) DEFAULT NULL COMMENT '设施名称',
  PRIMARY KEY (`id`),
  KEY `idx_room_type_id` (`room_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房间设施表';

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Initial Data
-- ----------------------------

-- Users (Password: 123456)
INSERT INTO \sys_user\ (\username\, \password\, \email\, \phone\, \eal_name\, \status\) VALUES 
('admin', '\\\.zmdr9k7uOCQb376NoUnutj8iAt6aBECYnzhCc85.d52w23.', 'admin@example.com', '13800138000', 'Admin', 1),
('test', '\\\.zmdr9k7uOCQb376NoUnutj8iAt6aBECYnzhCc85.d52w23.', 'test@example.com', '13900139000', 'Test User', 1);

-- Hotels
INSERT INTO \hotel\ (\
ame\, \ddress\, \description\, \star_rating\, \latitude\, \longitude\, \check_in_time\, \check_out_time\, \phone\, \email\, \min_price\, \status\) VALUES 
('Grand Luxury Hotel', '123 Main Street, Central District', 'A luxurious 5-star hotel in the heart of the city.', 5, 31.2304, 121.4737, '14:00', '12:00', '021-12345678', 'info@grandluxury.com', 800.00, 1),
('Seaside Resort', '456 Beach Road, Coastal Area', 'Relaxing resort with ocean views.', 4, 18.2528, 109.5119, '15:00', '11:00', '0898-87654321', 'book@seasideresort.com', 500.00, 1),
('Business Inn', '789 Tech Park, Innovation Zone', 'Convenient hotel for business travelers.', 3, 39.9042, 116.4074, '14:00', '12:00', '010-11223344', 'contact@businessinn.com', 300.00, 1);

-- Hotel Facilities
INSERT INTO \hotel_facility\ (\hotel_id\, \acility\) VALUES 
(1, 'WiFi'), (1, 'Swimming Pool'), (1, 'Gym'), (1, 'Parking'), (1, 'Restaurant'),
(2, 'WiFi'), (2, 'Beach Access'), (2, 'Spa'), (2, 'Bar'),
(3, 'WiFi'), (3, 'Meeting Room'), (3, 'Parking');

-- Hotel Images (Placeholders)
INSERT INTO \hotel_image\ (\hotel_id\, \image_url\, \sort_order\) VALUES 
(1, 'https://images.unsplash.com/photo-1566073771259-6a8506099945', 1),
(1, 'https://images.unsplash.com/photo-1582719508461-905c673771fd', 2),
(2, 'https://images.unsplash.com/photo-1571896349842-68c8949139f1', 1),
(3, 'https://images.unsplash.com/photo-1566665797739-1674de7a421a', 1);

-- Room Types
INSERT INTO \oom_type\ (\hotel_id\, \oom_type\, \rea\, \ed_type\, \max_occupancy\, \price\, \original_price\, \vailability\, \stock\) VALUES 
(1, 'Deluxe King Room', 45.00, 'King Size', 2, 1200.00, 1500.00, 1, 10),
(1, 'Executive Suite', 80.00, 'King Size', 3, 2500.00, 3000.00, 1, 5),
(2, 'Ocean View Twin', 35.00, 'Twin Beds', 2, 600.00, 800.00, 1, 20),
(3, 'Standard Room', 25.00, 'Queen Size', 2, 350.00, 400.00, 1, 30);

-- Room Amenities
INSERT INTO \oom_amenity\ (\oom_type_id\, \menity\) VALUES 
(1, 'Bathtub'), (1, 'Mini Bar'), (1, 'City View'),
(2, 'Living Room'), (2, 'Jacuzzi'), (2, 'Executive Lounge Access'),
(3, 'Balcony'), (3, 'Sea View'),
(4, 'Desk'), (4, 'Shower');

