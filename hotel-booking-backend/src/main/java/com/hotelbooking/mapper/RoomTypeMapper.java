package com.hotelbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelbooking.entity.RoomType;
import com.hotelbooking.vo.RoomTypeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 房间类型Mapper接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Mapper
public interface RoomTypeMapper extends BaseMapper<RoomType> {

    /**
     * 根据酒店ID查询房间类型列表
     */
    @Select("SELECT id, hotel_id, room_type, area, bed_type, max_occupancy, price, original_price, availability, stock FROM room_type WHERE hotel_id = #{hotelId}")
    List<RoomTypeVO> selectRoomTypeListByHotelId(@Param("hotelId") Long hotelId);
}