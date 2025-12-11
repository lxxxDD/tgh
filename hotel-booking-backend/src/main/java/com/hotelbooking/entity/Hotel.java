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
 * 酒店实体类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hotel")
public class Hotel extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 酒店ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 酒店名称
     */
    @TableField("name")
    private String name;

    /**
     * 地址
     */
    @TableField("address")
    private String address;

    /**
     * 酒店描述
     */
    @TableField("description")
    private String description;

    /**
     * 星级评分
     */
    @TableField("star_rating")
    private Integer starRating;

    /**
     * 纬度
     */
    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * 经度
     */
    @TableField("longitude")
    private BigDecimal longitude;

    /**
     * 入住时间
     */
    @TableField("check_in_time")
    private String checkInTime;

    /**
     * 退房时间
     */
    @TableField("check_out_time")
    private String checkOutTime;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 平均评分
     */
    @TableField("avg_rating")
    private BigDecimal avgRating;

    /**
     * 评价数量
     */
    @TableField("review_count")
    private Integer reviewCount;

    /**
     * 最低价格
     */
    @TableField("min_price")
    private BigDecimal minPrice;

    /**
     * 状态：1-营业，0-停业
     */
    @TableField("status")
    private Integer status;
}