package com.hotelbooking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hotelbooking.dto.HotelQueryDTO;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.vo.HotelVO;
import com.hotelbooking.vo.PageVO;
import com.hotelbooking.vo.RoomTypeVO;

import java.util.List;

/**
 * 酒店Service接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
public interface HotelService extends IService<Hotel> {

    /**
     * 分页查询酒店列表
     */
    PageVO<HotelVO> getHotelList(HotelQueryDTO queryDTO);

    /**
     * 获取酒店详情
     */
    HotelVO getHotelDetail(Long hotelId);

    /**
     * 获取酒店房间列表
     */
    List<RoomTypeVO> getHotelRooms(Long hotelId);

    /**
     * 更新酒店评分
     */
    void updateHotelRating(Long hotelId);
}