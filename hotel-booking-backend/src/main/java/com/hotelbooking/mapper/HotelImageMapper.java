package com.hotelbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelbooking.entity.HotelImage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 酒店图片Mapper接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Mapper
public interface HotelImageMapper extends BaseMapper<HotelImage> {
}