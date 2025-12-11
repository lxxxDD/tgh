package com.hotelbooking.exception;

import com.hotelbooking.common.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private Integer code;
    private String message;

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }



    public BusinessException(String message) {
        super(message);
        this.code = 500;
        this.message = message;
    }
}