-- 检查并添加缺失字段
ALTER TABLE hotel ADD COLUMN IF NOT EXISTS check_in_time VARCHAR(20) DEFAULT '14:00' COMMENT '入住时间';
ALTER TABLE hotel ADD COLUMN IF NOT EXISTS check_out_time VARCHAR(20) DEFAULT '12:00' COMMENT '退房时间';
ALTER TABLE hotel ADD COLUMN IF NOT EXISTS phone VARCHAR(20) DEFAULT NULL COMMENT '联系电话';
ALTER TABLE hotel ADD COLUMN IF NOT EXISTS email VARCHAR(100) DEFAULT NULL COMMENT '邮箱';

-- 更新所有酒店的基本信息
UPDATE hotel SET 
    check_in_time = '14:00',
    check_out_time = '12:00',
    phone = '400-888-8888',
    email = 'service@hotel.com'
WHERE check_in_time IS NULL OR check_in_time = '';

-- 查看更新结果
SELECT id, name, check_in_time, check_out_time, phone, email FROM hotel;
