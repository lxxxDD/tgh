package com.hotelbooking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 酒店设施实体类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@TableName("hotel_facility")
public class HotelFacility implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设施ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 酒店ID
     */
    @TableField("hotel_id")
    private Long hotelId;

    /**
     * 设施标识（wifi/parking等）
     */
    @TableField("facility")
    private String facility;
}