package com.hotelbooking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 收藏视图对象
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "收藏信息")
public class FavoriteVO {

    @Schema(description = "收藏ID", example = "1")
    private Long id;

    @Schema(description = "酒店ID", example = "1")
    private Long hotelId;

    @Schema(description = "酒店名称", example = "北京国际大酒店")
    private String name;

    @Schema(description = "地址", example = "北京市朝阳区建国门外大街1号")
    private String address;

    @Schema(description = "星级评分", example = "5")
    private Integer starRating;

    @Schema(description = "平均评分", example = "4.5")
    private BigDecimal avgRating;

    @Schema(description = "评价数量", example = "128")
    private Integer reviewCount;

    @Schema(description = "最低价格", example = "388")
    private BigDecimal minPrice;

    @Schema(description = "图片列表")
    private List<String> images;

    @Schema(description = "收藏时间", example = "2024-01-20T10:30:00")
    private LocalDateTime createTime;
}