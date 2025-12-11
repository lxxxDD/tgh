package com.hotelbooking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hotelbooking.entity.UserFavorite;
import com.hotelbooking.vo.FavoriteVO;
import com.hotelbooking.vo.PageVO;

/**
 * 用户收藏Service接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
public interface UserFavoriteService extends IService<UserFavorite> {

    /**
     * 获取用户收藏列表
     */
    PageVO<FavoriteVO> getUserFavorites(Long userId, Integer page, Integer size);

    /**
     * 添加收藏
     */
    void addFavorite(Long userId, Long hotelId);

    /**
     * 取消收藏
     */
    void removeFavorite(Long userId, Long hotelId);

    /**
     * 检查是否已收藏
     */
    boolean isFavorite(Long userId, Long hotelId);
}