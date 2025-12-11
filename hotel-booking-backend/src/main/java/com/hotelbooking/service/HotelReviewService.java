package com.hotelbooking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hotelbooking.dto.CreateReviewDTO;
import com.hotelbooking.entity.HotelReview;
import com.hotelbooking.vo.PageVO;
import com.hotelbooking.vo.ReviewVO;

/**
 * 酒店评价Service接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
public interface HotelReviewService extends IService<HotelReview> {

    /**
     * 获取酒店评价列表
     */
    PageVO<ReviewVO> getHotelReviews(Long hotelId, Integer page, Integer size);

    /**
     * 创建评价
     */
    void createReview(Long userId, CreateReviewDTO createDTO);

    /**
     * 获取最新评价（用于首页展示）
     */
    java.util.List<ReviewVO> getLatestReviews(Integer limit);
}