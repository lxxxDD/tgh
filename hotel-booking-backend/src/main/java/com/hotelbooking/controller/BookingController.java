package com.hotelbooking.controller;

import com.hotelbooking.common.Result;
import com.hotelbooking.dto.CreateBookingDTO;
import com.hotelbooking.service.BookingOrderService;
import com.hotelbooking.utils.SecurityUtils;
import com.hotelbooking.vo.BookingOrderVO;
import com.hotelbooking.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 预订控制器
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@RestController
@RequestMapping("/bookings")
@Tag(name = "订单管理", description = "订单相关接口")
public class BookingController {

    @Autowired
    private BookingOrderService bookingOrderService;

    @PostMapping
    @Operation(summary = "创建预订", description = "用户提交酒店预订请求")
    public Result<BookingOrderVO> createBooking(@Valid @RequestBody CreateBookingDTO createDTO) {
        Long userId = SecurityUtils.getCurrentUserId();
        BookingOrderVO orderVO = bookingOrderService.createBooking(userId, createDTO);
        return Result.success("预订成功", orderVO);
    }

    @GetMapping
    @Operation(summary = "获取用户订单列表", description = "获取当前用户的所有订单，支持状态筛选")
    public Result<PageVO<BookingOrderVO>> getUserOrders(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Long userId = SecurityUtils.getCurrentUserId();
        PageVO<BookingOrderVO> pageVO = bookingOrderService.getUserOrders(userId, status, page, size);
        return Result.success(pageVO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取订单详情", description = "获取单个订单的详细信息")
    public Result<BookingOrderVO> getOrderDetail(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        BookingOrderVO orderVO = bookingOrderService.getOrderDetail(userId, id);
        return Result.success(orderVO);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "取消订单", description = "取消已确认的订单")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        bookingOrderService.cancelOrder(userId, id);
        return Result.success("订单取消成功", null);
    }
}