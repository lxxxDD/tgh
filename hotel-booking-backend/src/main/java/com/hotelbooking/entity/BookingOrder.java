package com.hotelbooking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 订单实体类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("booking_order")
public class BookingOrder extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 酒店ID
     */
    @TableField("hotel_id")
    private Long hotelId;

    /**
     * 房间类型ID
     */
    @TableField("room_type_id")
    private Long roomTypeId;

    /**
     * 酒店名称
     */
    @TableField("hotel_name")
    private String hotelName;

    /**
     * 房间类型
     */
    @TableField("room_type")
    private String roomType;

    /**
     * 入住人姓名
     */
    @TableField("guest_name")
    private String guestName;

    /**
     * 入住人电话
     */
    @TableField("guest_phone")
    private String guestPhone;

    /**
     * 入住人身份证号
     */
    @TableField("guest_id_card")
    private String guestIdCard;

    /**
     * 入住日期
     */
    @TableField("check_in_date")
    private LocalDate checkInDate;

    /**
     * 退房日期
     */
    @TableField("check_out_date")
    private LocalDate checkOutDate;

    /**
     * 入住天数
     */
    @TableField("nights")
    private Integer nights;

    /**
     * 订单总金额
     */
    @TableField("total_amount")
    private BigDecimal totalAmount;

    /**
     * 订单状态：pending-待确认，confirmed-已确认，completed-已完成，cancelled-已取消
     */
    @TableField("status")
    private String status;

    /**
     * 预订时间
     */
    @TableField("booking_time")
    private LocalDateTime bookingTime;

    /**
     * 特殊要求
     */
    @TableField("special_requests")
    private String specialRequests;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
}