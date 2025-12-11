package com.hotelbooking.controller;

import com.hotelbooking.common.Result;
import com.hotelbooking.dto.HotelQueryDTO;
import com.hotelbooking.service.HotelService;
import com.hotelbooking.vo.HotelVO;
import com.hotelbooking.vo.PageVO;
import com.hotelbooking.vo.RoomTypeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 酒店控制器
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@RestController
@RequestMapping("/hotels")
@Tag(name = "酒店管理", description = "酒店相关接口")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @GetMapping
    @Operation(summary = "获取酒店列表", description = "根据条件搜索酒店，支持分页和排序")
    public Result<PageVO<HotelVO>> getHotelList(@Valid HotelQueryDTO queryDTO) {
        PageVO<HotelVO> pageVO = hotelService.getHotelList(queryDTO);
        return Result.success(pageVO);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取酒店详情", description = "获取单个酒店的详细信息")
    public Result<HotelVO> getHotelDetail(@PathVariable Long id) {
        HotelVO hotelVO = hotelService.getHotelDetail(id);
        return Result.success(hotelVO);
    }

    @GetMapping("/{id}/rooms")
    @Operation(summary = "获取酒店房间列表", description = "获取指定酒店的所有房间类型")
    public Result<List<RoomTypeVO>> getHotelRooms(@PathVariable Long id) {
        List<RoomTypeVO> roomList = hotelService.getHotelRooms(id);
        return Result.success(roomList);
    }
}