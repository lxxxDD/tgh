package com.hotelbooking.enums;

/**
 * 酒店状态枚举
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
public enum HotelStatus {
    
    /**
     * 停业
     */
    CLOSED(0, "停业"),
    
    /**
     * 营业
     */
    OPEN(1, "营业");

    private final Integer code;
    private final String description;

    HotelStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据code获取枚举
     */
    public static HotelStatus fromCode(Integer code) {
        for (HotelStatus status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        return null;
    }
}