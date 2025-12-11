package com.hotelbooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 简化重置密码DTO
 */
@Data
@Schema(description = "简化重置密码请求")
public class ResetPasswordSimpleDTO {

    @Schema(description = "用户名", required = true)
    private String username;

    @Schema(description = "邮箱", required = true)
    private String email;

    @Schema(description = "新密码", required = true)
    private String newPassword;
}
