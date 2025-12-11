package com.hotelbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelbooking.entity.BookingOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单Mapper接口
 * 直接继承 BaseMapper，使用 MyBatis-Plus 分页器查询
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Mapper
public interface BookingOrderMapper extends BaseMapper<BookingOrder> {
}