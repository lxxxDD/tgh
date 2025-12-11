-- 初始化数据脚本

-- 插入测试用户
INSERT INTO sys_user (username, password, email, phone, real_name, id_card, avatar, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXiEkQ8qML8nBpdjdAVSgNXTqFK', 'admin@hotelbooking.com', '13800138000', '管理员', '110101199001011234', 'https://example.com/avatar.jpg', 1),
('testuser', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKXiEkQ8qML8nBpdjdAVSgNXTqFK', 'test@example.com', '13900139000', '测试用户', '110101199001011235', 'https://example.com/avatar.jpg', 1);

-- 插入测试酒店
INSERT INTO hotel (name, address, description, star_rating, latitude, longitude, check_in_time, check_out_time, phone, email, avg_rating, review_count, min_price, status) VALUES
('北京国际大酒店', '北京市朝阳区建国门外大街1号', '五星级豪华酒店，位于市中心商务区，交通便利，设施齐全', 5, 39.9042, 116.4074, '15:00', '12:00', '010-12345678', 'info@bjhotel.com', 4.5, 128, 388, 1),
('上海浦东香格里拉大酒店', '上海市浦东新区陆家嘴环路33号', '位于陆家嘴金融贸易区，俯瞰黄浦江美景', 5, 31.2304, 121.4737, '15:00', '12:00', '021-68828888', 'info@shhotel.com', 4.7, 256, 588, 1),
('广州白天鹅宾馆', '广州市荔湾区沙面南街1号', '历史悠久的五星级酒店，位于美丽的沙面岛', 5, 23.0965, 113.3212, '15:00', '12:00', '020-81886968', 'info@gzhotel.com', 4.6, 198, 488, 1);

-- 插入酒店设施
INSERT INTO hotel_facility (hotel_id, facility) VALUES
(1, 'wifi'), (1, 'parking'), (1, 'gym'), (1, 'pool'), (1, 'restaurant'),
(2, 'wifi'), (2, 'parking'), (2, 'gym'), (2, 'pool'), (2, 'spa'),
(3, 'wifi'), (3, 'parking'), (3, 'gym'), (3, 'restaurant'), (3, 'bar');

-- 插入酒店图片
INSERT INTO hotel_image (hotel_id, image_url, sort_order) VALUES
(1, 'https://images.unsplash.com/photo-1564501049412-61c2a3083791', 1),
(1, 'https://images.unsplash.com/photo-1571896349842-33c89424de2d', 2),
(2, 'https://images.unsplash.com/photo-1566073771259-6a8506099945', 1),
(2, 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa', 2),
(3, 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4', 1),
(3, 'https://images.unsplash.com/photo-1571003123894-1f0594d2b5d9', 2);

-- 插入房间类型
INSERT INTO room_type (hotel_id, room_type, area, bed_type, max_occupancy, price, original_price, availability, stock) VALUES
(1, '标准大床房', 30.00, '1.8米大床', 2, 388.00, 488.00, 1, 10),
(1, '豪华双床房', 35.00, '1.2米双床', 2, 468.00, 568.00, 1, 8),
(1, '行政套房', 60.00, '2米大床', 3, 888.00, 1088.00, 1, 5),
(2, '江景大床房', 40.00, '2米大床', 2, 588.00, 688.00, 1, 12),
(2, '豪华套房', 80.00, '2米大床', 4, 1288.00, 1588.00, 1, 3),
(3, '园景双床房', 35.00, '1.2米双床', 2, 488.00, 588.00, 1, 15);

-- 插入房间设施
INSERT INTO room_amenity (room_type_id, amenity) VALUES
(1, '空调'), (1, '电视'), (1, '独立卫浴'), (1, '免费WiFi'),
(2, '空调'), (2, '电视'), (2, '独立卫浴'), (2, '免费WiFi'), (2, '迷你吧'),
(3, '空调'), (3, '电视'), (3, '独立卫浴'), (3, '免费WiFi'), (3, '迷你吧'), (3, '客厅');