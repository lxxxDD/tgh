package com.hotelbooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 酒店查询DTO
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "酒店查询参数")
public class HotelQueryDTO {

    @Schema(description = "酒店名称", example = "文华东方")
    private String name;

    @Schema(description = "城市名称", example = "北京")
    private String city;

    @Schema(description = "入住日期", example = "2024-01-25")
    private String checkIn;

    @Schema(description = "退房日期", example = "2024-01-27")
    private String checkOut;

    @Schema(description = "价格范围", example = "300-600")
    private String priceRange;

    @Schema(description = "最低星级", example = "4")
    private Integer starRating;

    @Schema(description = "设施（多选用逗号分隔）", example = "免费WiFi,游泳池,健身房")
    private String amenities;

    @Schema(description = "排序字段：price/rating", example = "price")
    private String sortBy;

    @Schema(description = "页码，默认1", example = "1")
    private Integer page = 1;

    @Schema(description = "每页条数，默认10", example = "10")
    private Integer size = 10;

    /**
     * 获取设施列表（将逗号分隔的字符串转为List）
     */
    public List<String> getAmenitiesList() {
        if (amenities == null || amenities.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(amenities.split(","));
    }
}