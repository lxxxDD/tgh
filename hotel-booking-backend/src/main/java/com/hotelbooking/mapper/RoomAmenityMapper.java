package com.hotelbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelbooking.entity.RoomAmenity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房间设施Mapper接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Mapper
public interface RoomAmenityMapper extends BaseMapper<RoomAmenity> {
}