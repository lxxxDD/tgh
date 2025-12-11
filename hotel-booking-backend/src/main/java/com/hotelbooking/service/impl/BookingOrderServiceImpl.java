package com.hotelbooking.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotelbooking.common.ErrorCode;
import com.hotelbooking.dto.CreateBookingDTO;
import com.hotelbooking.entity.BookingOrder;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.RoomType;
import com.hotelbooking.enums.OrderStatus;
import com.hotelbooking.exception.BusinessException;
import com.hotelbooking.mapper.BookingOrderMapper;
import com.hotelbooking.mapper.HotelMapper;
import com.hotelbooking.mapper.RoomTypeMapper;
import com.hotelbooking.service.BookingOrderService;
import com.hotelbooking.vo.BookingOrderVO;
import com.hotelbooking.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * 订单Service实现类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Service
public class BookingOrderServiceImpl extends ServiceImpl<BookingOrderMapper, BookingOrder> implements BookingOrderService {

    @Autowired
    private BookingOrderMapper orderMapper;

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BookingOrderVO createBooking(Long userId, CreateBookingDTO createDTO) {
        // 参数验证
        validateBookingDates(createDTO.getCheckInDate(), createDTO.getCheckOutDate());
        
        // 检查酒店是否存在
        Hotel hotel = hotelMapper.selectById(createDTO.getHotelId());
        if (hotel == null) {
            throw new BusinessException(ErrorCode.HOTEL_NOT_FOUND, "酒店不存在");
        }
        
        // 检查房间类型是否存在
        RoomType roomType = roomTypeMapper.selectById(createDTO.getRoomTypeId());
        if (roomType == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "房间类型不存在");
        }
        
        // 检查房间是否属于该酒店
        if (!roomType.getHotelId().equals(createDTO.getHotelId())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "房间类型不属于该酒店");
        }
        
        // 检查库存
        if (roomType.getStock() <= 0) {
            throw new BusinessException(ErrorCode.STOCK_INSUFFICIENT, "房间库存不足");
        }
        
        // 计算入住天数
        int nights = (int) ChronoUnit.DAYS.between(createDTO.getCheckInDate(), createDTO.getCheckOutDate());
        
        // 计算订单总金额
        BigDecimal totalAmount = roomType.getPrice().multiply(BigDecimal.valueOf(nights));
        
        // 创建订单
        BookingOrder order = new BookingOrder();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setHotelId(createDTO.getHotelId());
        order.setRoomTypeId(createDTO.getRoomTypeId());
        order.setHotelName(hotel.getName());
        order.setRoomType(roomType.getRoomType());
        order.setGuestName(createDTO.getGuestName());
        order.setGuestPhone(createDTO.getGuestPhone());
        order.setGuestIdCard(createDTO.getGuestIdCard());
        order.setCheckInDate(createDTO.getCheckInDate());
        order.setCheckOutDate(createDTO.getCheckOutDate());
        order.setNights(nights);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING.getCode());
        order.setBookingTime(LocalDateTime.now());
        order.setSpecialRequests(createDTO.getSpecialRequests());
        
        // 保存订单
        orderMapper.insert(order);
        
        // 扣减库存
        roomType.setStock(roomType.getStock() - 1);
        roomTypeMapper.updateById(roomType);
        
        // 返回订单详情
        return getOrderDetail(userId, order.getId());
    }

    @Override
    public PageVO<BookingOrderVO> getUserOrders(Long userId, String status, Integer page, Integer size) {
        // 构建分页对象
        Page<BookingOrder> pageParam = new Page<>(page, size);
        
        // 使用 LambdaQueryWrapper 构建查询条件
        LambdaQueryWrapper<BookingOrder> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BookingOrder::getUserId, userId);
        
        // 状态筛选
        if (status != null && !status.isEmpty() && !"all".equals(status)) {
            wrapper.eq(BookingOrder::getStatus, status);
        }
        
        // 按预订时间倒序
        wrapper.orderByDesc(BookingOrder::getBookingTime);
        
        // 执行分页查询
        IPage<BookingOrder> orderPage = orderMapper.selectPage(pageParam, wrapper);
        
        // 转换为 VO，并填充酒店地址
        java.util.List<BookingOrderVO> voList = orderPage.getRecords().stream().map(order -> {
            BookingOrderVO vo = new BookingOrderVO();
            BeanUtil.copyProperties(order, vo);
            // 查询酒店地址
            Hotel hotel = hotelMapper.selectById(order.getHotelId());
            if (hotel != null) {
                vo.setHotelAddress(hotel.getAddress());
            }
            return vo;
        }).collect(java.util.stream.Collectors.toList());
        
        PageVO<BookingOrderVO> pageVO = new PageVO<>();
        pageVO.setTotal(orderPage.getTotal());
        pageVO.setPage((int) orderPage.getCurrent());
        pageVO.setSize((int) orderPage.getSize());
        pageVO.setPages((int) orderPage.getPages());
        pageVO.setRecords(voList);
        
        return pageVO;
    }

    @Override
    public BookingOrderVO getOrderDetail(Long userId, Long orderId) {
        // 直接查询订单
        BookingOrder order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");
        }
        
        // 检查是否是当前用户的订单
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权限查看该订单");
        }
        
        // 转换为 VO
        BookingOrderVO vo = new BookingOrderVO();
        BeanUtil.copyProperties(order, vo);
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder(Long userId, Long orderId) {
        BookingOrder order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");
        }
        
        // 检查是否是当前用户的订单
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权限取消该订单");
        }
        
        // 检查订单状态
        if (!OrderStatus.CONFIRMED.getCode().equals(order.getStatus())) {
            throw new BusinessException(ErrorCode.ORDER_STATUS_ERROR, "只有已确认的订单才能取消");
        }
        
        // 检查取消时间限制（入住前24小时）
        LocalDateTime cancelDeadline = order.getCheckInDate().atStartOfDay().minusHours(24);
        if (LocalDateTime.now().isAfter(cancelDeadline)) {
            throw new BusinessException(ErrorCode.ORDER_CANCEL_TIME_LIMIT, "入住前24小时内不能取消订单");
        }
        
        // 更新订单状态
        order.setStatus(OrderStatus.CANCELLED.getCode());
        updateById(order);
        
        // 恢复库存
        RoomType roomType = roomTypeMapper.selectById(order.getRoomTypeId());
        if (roomType != null) {
            roomType.setStock(roomType.getStock() + 1);
            roomTypeMapper.updateById(roomType);
        }
    }

    /**
     * 验证预订日期
     */
    private void validateBookingDates(LocalDate checkInDate, LocalDate checkOutDate) {
        LocalDate today = LocalDate.now();
        
        // 检查入住日期是否早于今天
        if (checkInDate.isBefore(today)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "入住日期不能早于今天");
        }
        
        // 检查退房日期是否晚于入住日期
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "退房日期必须晚于入住日期");
        }
    }

    /**
     * 生成订单号
     */
    private String generateOrderNo() {
        return "BK" + DateUtil.format(LocalDateTime.now(), "yyyyMMddHHmmss") + RandomUtil.randomNumbers(4);
    }
}