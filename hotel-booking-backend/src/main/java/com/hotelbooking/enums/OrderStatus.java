package com.hotelbooking.enums;

/**
 * 订单状态枚举
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
public enum OrderStatus {
    
    /**
     * 待确认
     */
    PENDING("pending", "待确认"),
    
    /**
     * 已确认
     */
    CONFIRMED("confirmed", "已确认"),
    
    /**
     * 已完成
     */
    COMPLETED("completed", "已完成"),
    
    /**
     * 已取消
     */
    CANCELLED("cancelled", "已取消");

    private final String code;
    private final String description;

    OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据code获取枚举
     */
    public static OrderStatus fromCode(String code) {
        for (OrderStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}