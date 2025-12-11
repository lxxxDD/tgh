package com.hotelbooking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 订单视图对象
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "订单信息")
public class BookingOrderVO {

    @Schema(description = "订单ID", example = "202401201425301234")
    private Long id;

    @Schema(description = "订单号", example = "BK20240120142530001")
    private String orderNo;

    @Schema(description = "用户ID", example = "10001")
    private Long userId;

    @Schema(description = "酒店ID", example = "1")
    private Long hotelId;

    @Schema(description = "房间类型ID", example = "101")
    private Long roomTypeId;

    @Schema(description = "酒店名称", example = "北京国际大酒店")
    private String hotelName;

    @Schema(description = "酒店地址", example = "北京市朝阳区建国门外大街1号")
    private String hotelAddress;

    @Schema(description = "房间类型", example = "标准大床房")
    private String roomType;

    @Schema(description = "入住人姓名", example = "张三")
    private String guestName;

    @Schema(description = "入住人电话", example = "13800138000")
    private String guestPhone;

    @Schema(description = "入住人身份证号", example = "110101199001011234")
    private String guestIdCard;

    @Schema(description = "入住日期", example = "2024-02-01")
    private LocalDate checkInDate;

    @Schema(description = "退房日期", example = "2024-02-03")
    private LocalDate checkOutDate;

    @Schema(description = "入住天数", example = "2")
    private Integer nights;

    @Schema(description = "订单总金额", example = "776.00")
    private BigDecimal totalAmount;

    @Schema(description = "订单状态：pending-待确认，confirmed-已确认，completed-已完成，cancelled-已取消", example = "confirmed")
    private String status;

    @Schema(description = "预订时间", example = "2024-01-20T14:25:30")
    private LocalDateTime bookingTime;

    @Schema(description = "特殊要求", example = "希望安排安静房间")
    private String specialRequests;

    @Schema(description = "备注", example = "客服备注：已安排靠内侧房间")
    private String remark;
}