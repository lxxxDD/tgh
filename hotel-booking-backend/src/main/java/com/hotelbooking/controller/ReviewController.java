package com.hotelbooking.controller;

import com.hotelbooking.common.Result;
import com.hotelbooking.dto.CreateReviewDTO;
import com.hotelbooking.service.HotelReviewService;
import com.hotelbooking.utils.SecurityUtils;
import com.hotelbooking.vo.PageVO;
import com.hotelbooking.vo.ReviewVO;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 评价控制器
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@RestController
@RequestMapping("/reviews")
@Tag(name = "评价管理", description = "评价相关接口")
public class ReviewController {

    @Autowired
    private HotelReviewService reviewService;

    @GetMapping("/hotel/{hotelId}")
    @Operation(summary = "获取酒店评价列表", description = "获取指定酒店的用户评价列表")
    public Result<PageVO<ReviewVO>> getHotelReviews(
            @PathVariable Long hotelId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        PageVO<ReviewVO> pageVO = reviewService.getHotelReviews(hotelId, page, size);
        return Result.success(pageVO);
    }

    @PostMapping
    @Operation(summary = "提交评价", description = "用户对已完成的订单提交评价")
    public Result<Void> createReview(@Valid @RequestBody CreateReviewDTO createDTO) {
        Long userId = SecurityUtils.getCurrentUserId();
        reviewService.createReview(userId, createDTO);
        return Result.success("评价提交成功", null);
    }

    @GetMapping("/latest")
    @Operation(summary = "获取最新评价", description = "获取最新的用户评价列表（用于首页展示）")
    public Result<List<ReviewVO>> getLatestReviews(
            @RequestParam(defaultValue = "6") Integer limit) {
        List<ReviewVO> reviews = reviewService.getLatestReviews(limit);
        return Result.success(reviews);
    }
}