package com.hotelbooking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 酒店视图对象
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "酒店信息")
public class HotelVO {

    @Schema(description = "酒店ID", example = "1")
    private Long id;

    @Schema(description = "酒店名称", example = "北京国际大酒店")
    private String name;

    @Schema(description = "地址", example = "北京市朝阳区建国门外大街1号")
    private String address;

    @Schema(description = "酒店描述", example = "五星级豪华酒店，位于市中心商务区...")
    private String description;

    @Schema(description = "星级评分", example = "5")
    private Integer starRating;

    @Schema(description = "纬度", example = "39.9042")
    private BigDecimal latitude;

    @Schema(description = "经度", example = "116.4074")
    private BigDecimal longitude;

    @Schema(description = "入住时间", example = "15:00")
    private String checkInTime;

    @Schema(description = "退房时间", example = "12:00")
    private String checkOutTime;

    @Schema(description = "联系电话", example = "010-12345678")
    private String phone;

    @Schema(description = "邮箱", example = "info@hotel.com")
    private String email;

    @Schema(description = "平均评分", example = "4.5")
    private BigDecimal avgRating;

    @Schema(description = "评价数量", example = "128")
    private Integer reviewCount;

    @Schema(description = "最低价格", example = "388")
    private BigDecimal minPrice;

    @Schema(description = "设施列表")
    private List<String> facilities;

    @Schema(description = "图片列表")
    private List<String> images;
}