package com.hotelbooking.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 房间设施实体类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@TableName("room_amenity")
public class RoomAmenity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设施ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 房间类型ID
     */
    @TableField("room_type_id")
    private Long roomTypeId;

    /**
     * 设施名称
     */
    @TableField("amenity")
    private String amenity;
}