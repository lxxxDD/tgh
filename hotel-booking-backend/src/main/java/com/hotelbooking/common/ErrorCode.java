package com.hotelbooking.common;

/**
 * 错误码常量接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
public interface ErrorCode {

    // 通用错误码
    int SUCCESS = 200;
    int BAD_REQUEST = 400;
    int UNAUTHORIZED = 401;
    int FORBIDDEN = 403;
    int NOT_FOUND = 404;
    int CONFLICT = 409;
    int INTERNAL_SERVER_ERROR = 500;

    // 用户相关错误码
    int USER_NOT_FOUND = 1001;
    int USERNAME_EXISTS = 1002;
    int EMAIL_EXISTS = 1003;
    int PASSWORD_ERROR = 1004;
    int ACCOUNT_LOCKED = 1005;

    // 酒店相关错误码
    int HOTEL_NOT_FOUND = 2001;

    // 订单相关错误码
    int ORDER_NOT_FOUND = 3001;
    int ORDER_STATUS_ERROR = 3002;
    int ORDER_CANCEL_TIME_LIMIT = 3003;
    int STOCK_INSUFFICIENT = 3004;

    // 评价相关错误码
    int REVIEW_NOT_ALLOWED = 4001;
    int REVIEW_EXISTS = 4002;

    // 收藏相关错误码
    int FAVORITE_EXISTS = 5001;
    int FAVORITE_NOT_FOUND = 5002;
}