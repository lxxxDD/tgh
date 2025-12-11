package com.hotelbooking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 房间类型实体类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("room_type")
public class RoomType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 房间类型ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 酒店ID
     */
    @TableField("hotel_id")
    private Long hotelId;

    /**
     * 房间类型名称
     */
    @TableField("room_type")
    private String roomType;

    /**
     * 面积（平方米）
     */
    @TableField("area")
    private BigDecimal area;

    /**
     * 床型
     */
    @TableField("bed_type")
    private String bedType;

    /**
     * 最大入住人数
     */
    @TableField("max_occupancy")
    private Integer maxOccupancy;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 原价
     */
    @TableField("original_price")
    private BigDecimal originalPrice;

    /**
     * 是否可预订：1-可订，0-满房
     */
    @TableField("availability")
    private Integer availability;

    /**
     * 库存数量
     */
    @TableField("stock")
    private Integer stock;
}