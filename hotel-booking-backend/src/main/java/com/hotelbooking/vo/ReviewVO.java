package com.hotelbooking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 评价视图对象
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "评价信息")
public class ReviewVO {

    @Schema(description = "评价ID", example = "1001")
    private Long id;

    @Schema(description = "用户名", example = "张三")
    private String userName;

    @Schema(description = "用户头像", example = "https://example.com/avatar.jpg")
    private String userAvatar;

    @Schema(description = "评分", example = "5")
    private Integer rating;

    @Schema(description = "评价内容", example = "酒店位置很好，房间干净整洁，服务人员态度友好。")
    private String content;

    @Schema(description = "评价日期", example = "2024-01-15")
    private LocalDate reviewDate;

    @Schema(description = "状态：1-显示，0-隐藏", example = "1")
    private Integer status;
}