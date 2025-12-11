package com.hotelbooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * 创建评价DTO
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "创建评价请求")
public class CreateReviewDTO {

    @NotNull(message = "订单ID不能为空")
    @Positive(message = "订单ID必须为正数")
    @Schema(description = "订单ID", required = true, example = "202401201425301234")
    private Long orderId;

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最小为1")
    @Max(value = 5, message = "评分最大为5")
    @Schema(description = "评分（1-5）", required = true, example = "5")
    private Integer rating;

    @NotBlank(message = "评价内容不能为空")
    @Size(min = 10, max = 500, message = "评价内容长度必须在10-500位之间")
    @Schema(description = "评价内容", required = true, example = "酒店位置很好，房间干净整洁，服务人员态度友好。")
    private String content;
}