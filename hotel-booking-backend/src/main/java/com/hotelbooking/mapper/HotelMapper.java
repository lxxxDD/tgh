package com.hotelbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hotelbooking.entity.Hotel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 酒店Mapper接口
 * 直接继承 BaseMapper，使用 MyBatis-Plus 分页器查询
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Mapper
public interface HotelMapper extends BaseMapper<Hotel> {

    /**
     * 根据酒店ID查询设施列表
     */
    @Select("SELECT facility FROM hotel_facility WHERE hotel_id = #{hotelId}")
    List<String> selectFacilitiesByHotelId(@Param("hotelId") Long hotelId);

    /**
     * 根据酒店ID查询图片列表
     */
    @Select("SELECT image_url FROM hotel_image WHERE hotel_id = #{hotelId} ORDER BY sort_order ASC")
    List<String> selectImagesByHotelId(@Param("hotelId") Long hotelId);
}