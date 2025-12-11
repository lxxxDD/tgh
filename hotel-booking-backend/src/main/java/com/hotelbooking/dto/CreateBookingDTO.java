package com.hotelbooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 创建预订DTO
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "创建预订请求")
public class CreateBookingDTO {

    @NotNull(message = "酒店ID不能为空")
    @Positive(message = "酒店ID必须为正数")
    @Schema(description = "酒店ID", required = true, example = "1")
    private Long hotelId;

    @NotNull(message = "房间类型ID不能为空")
    @Positive(message = "房间类型ID必须为正数")
    @Schema(description = "房间类型ID", required = true, example = "101")
    private Long roomTypeId;

    @NotNull(message = "入住日期不能为空")
    @FutureOrPresent(message = "入住日期不能是过去日期")
    @Schema(description = "入住日期", required = true, example = "2024-02-01")
    private LocalDate checkInDate;

    @NotNull(message = "退房日期不能为空")
    @Future(message = "退房日期必须是未来日期")
    @Schema(description = "退房日期", required = true, example = "2024-02-03")
    private LocalDate checkOutDate;

    @NotBlank(message = "入住人姓名不能为空")
    @Size(max = 50, message = "入住人姓名长度不能超过50位")
    @Schema(description = "入住人姓名", required = true, example = "张三")
    private String guestName;

    @NotBlank(message = "入住人电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "入住人电话格式不正确")
    @Schema(description = "入住人电话", required = true, example = "13800138000")
    private String guestPhone;

    @NotBlank(message = "入住人身份证号不能为空")
    @Pattern(regexp = "^\\d{17}[\\dXx]$", message = "入住人身份证号格式不正确")
    @Schema(description = "入住人身份证号", required = true, example = "110101199001011234")
    private String guestIdCard;

    @Size(max = 500, message = "特殊要求长度不能超过500位")
    @Schema(description = "特殊要求", example = "希望安排安静房间，不要靠近电梯")
    private String specialRequests;
}