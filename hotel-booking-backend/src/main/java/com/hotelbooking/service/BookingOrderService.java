package com.hotelbooking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hotelbooking.dto.CreateBookingDTO;
import com.hotelbooking.entity.BookingOrder;
import com.hotelbooking.vo.BookingOrderVO;
import com.hotelbooking.vo.PageVO;

/**
 * 订单Service接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
public interface BookingOrderService extends IService<BookingOrder> {

    /**
     * 创建预订
     */
    BookingOrderVO createBooking(Long userId, CreateBookingDTO createDTO);

    /**
     * 获取用户订单列表
     */
    PageVO<BookingOrderVO> getUserOrders(Long userId, String status, Integer page, Integer size);

    /**
     * 获取订单详情
     */
    BookingOrderVO getOrderDetail(Long userId, Long orderId);

    /**
     * 取消订单
     */
    void cancelOrder(Long userId, Long orderId);
}