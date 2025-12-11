package com.hotelbooking.controller;

import com.hotelbooking.common.Result;
import com.hotelbooking.service.UserFavoriteService;
import com.hotelbooking.utils.SecurityUtils;
import com.hotelbooking.vo.FavoriteVO;
import com.hotelbooking.vo.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 收藏控制器
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@RestController
@RequestMapping("/favorites")
@Tag(name = "收藏管理", description = "收藏相关接口")
public class FavoriteController {

    @Autowired
    private UserFavoriteService favoriteService;

    @GetMapping
    @Operation(summary = "获取用户收藏列表", description = "获取当前用户收藏的酒店列表")
    public Result<PageVO<FavoriteVO>> getUserFavorites(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        Long userId = SecurityUtils.getCurrentUserId();
        PageVO<FavoriteVO> pageVO = favoriteService.getUserFavorites(userId, page, size);
        return Result.success(pageVO);
    }

    @PostMapping("/{hotelId}")
    @Operation(summary = "添加收藏", description = "收藏指定酒店")
    public Result<Void> addFavorite(@PathVariable Long hotelId) {
        Long userId = SecurityUtils.getCurrentUserId();
        favoriteService.addFavorite(userId, hotelId);
        return Result.success("收藏成功", null);
    }

    @DeleteMapping("/{hotelId}")
    @Operation(summary = "取消收藏", description = "取消收藏指定酒店")
    public Result<Void> removeFavorite(@PathVariable Long hotelId) {
        Long userId = SecurityUtils.getCurrentUserId();
        favoriteService.removeFavorite(userId, hotelId);
        return Result.success("取消收藏成功", null);
    }

    @GetMapping("/check/{hotelId}")
    @Operation(summary = "检查收藏状态", description = "检查当前用户是否已收藏指定酒店")
    public Result<Boolean> checkFavorite(@PathVariable Long hotelId) {
        Long userId = SecurityUtils.getCurrentUserId();
        boolean isFavorite = favoriteService.isFavorite(userId, hotelId);
        return Result.success(isFavorite);
    }
}