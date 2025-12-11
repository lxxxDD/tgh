package com.hotelbooking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 房间类型视图对象
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "房间类型信息")
public class RoomTypeVO {

    @Schema(description = "房间类型ID", example = "101")
    private Long id;

    @Schema(description = "房间类型名称", example = "标准大床房")
    private String roomType;

    @Schema(description = "面积（平方米）", example = "30.00")
    private BigDecimal area;

    @Schema(description = "床型", example = "1.8米大床")
    private String bedType;

    @Schema(description = "最大入住人数", example = "2")
    private Integer maxOccupancy;

    @Schema(description = "价格", example = "388.00")
    private BigDecimal price;

    @Schema(description = "原价", example = "488.00")
    private BigDecimal originalPrice;

    @Schema(description = "是否可预订", example = "true")
    private Boolean availability;

    @Schema(description = "库存数量", example = "5")
    private Integer stock;

    @Schema(description = "设施列表")
    private List<String> amenities;

    @Schema(description = "图片列表")
    private List<String> images;
}