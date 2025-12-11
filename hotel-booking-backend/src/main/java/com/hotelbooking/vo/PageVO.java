package com.hotelbooking.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 分页视图对象
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Schema(description = "分页响应")
public class PageVO<T> {

    @Schema(description = "总记录数", example = "150")
    private Long total;

    @Schema(description = "当前页码", example = "1")
    private Integer page;

    @Schema(description = "每页条数", example = "10")
    private Integer size;

    @Schema(description = "总页数", example = "15")
    private Integer pages;

    @Schema(description = "数据列表")
    private List<T> records;
}