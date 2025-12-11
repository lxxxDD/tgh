package com.hotelbooking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录响应视图对象
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "登录响应")
public class LoginVO {

    @Schema(description = "用户ID", example = "10001")
    private Long userId;

    @Schema(description = "用户名", example = "zhangsan")
    private String username;

    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;

    @Schema(description = "访问Token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;

    @Schema(description = "刷新Token", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String refreshToken;

    @Schema(description = "Token过期时间", example = "2024-01-24T15:00:32")
    private LocalDateTime tokenExpire;

    @Schema(description = "刷新Token过期时间", example = "2024-01-31T14:30:32")
    private LocalDateTime refreshTokenExpire;
}