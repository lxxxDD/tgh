package com.hotelbooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 更新用户信息DTO
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "更新用户信息请求")
public class UpdateUserProfileDTO {

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", required = true, example = "newemail@example.com")
    private String email;

    @Pattern(regexp = "^(1[3-9]\\d{9})?$", message = "手机号格式不正确")
    @Schema(description = "手机号", example = "13900139000")
    private String phone;

    @Size(max = 50, message = "真实姓名长度不能超过50位")
    @Schema(description = "真实姓名", example = "张三")
    private String realName;

    @Pattern(regexp = "^\\d{17}[\\dXx]$", message = "身份证号格式不正确")
    @Schema(description = "身份证号", example = "110101199001011234")
    private String idCard;
}