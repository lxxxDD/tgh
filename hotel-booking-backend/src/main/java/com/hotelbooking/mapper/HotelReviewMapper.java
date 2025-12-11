package com.hotelbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hotelbooking.entity.HotelReview;
import com.hotelbooking.vo.ReviewVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 酒店评价Mapper接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Mapper
public interface HotelReviewMapper extends BaseMapper<HotelReview> {

    /**
     * 分页查询酒店评价列表
     */
    IPage<ReviewVO> selectReviewPageByHotelId(Page<ReviewVO> page, @Param("hotelId") Long hotelId);
}