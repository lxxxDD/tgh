package com.hotelbooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 修改密码DTO
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "修改密码请求")
public class UpdatePasswordDTO {

    @NotBlank(message = "当前密码不能为空")
    @Schema(description = "当前密码", required = true, example = "Abc123456")
    private String currentPassword;

    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, message = "新密码至少6位")
    @Schema(description = "新密码", required = true, example = "NewPass789")
    private String newPassword;

    @NotBlank(message = "确认密码不能为空")
    @Schema(description = "确认新密码", required = true, example = "NewPass789")
    private String confirmPassword;
}