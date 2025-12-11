package com.hotelbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelbooking.entity.HotelFacility;
import org.apache.ibatis.annotations.Mapper;

/**
 * 酒店设施Mapper接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Mapper
public interface HotelFacilityMapper extends BaseMapper<HotelFacility> {
}