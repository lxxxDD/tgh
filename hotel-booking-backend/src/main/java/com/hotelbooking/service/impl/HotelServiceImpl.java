package com.hotelbooking.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotelbooking.common.ErrorCode;
import com.hotelbooking.dto.HotelQueryDTO;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.HotelFacility;
import com.hotelbooking.entity.HotelImage;
import com.hotelbooking.exception.BusinessException;
import com.hotelbooking.mapper.HotelFacilityMapper;
import com.hotelbooking.mapper.HotelImageMapper;
import com.hotelbooking.mapper.HotelMapper;
import com.hotelbooking.mapper.RoomTypeMapper;
import com.hotelbooking.service.HotelService;
import com.hotelbooking.vo.HotelVO;
import com.hotelbooking.vo.PageVO;
import com.hotelbooking.vo.RoomTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 酒店Service实现类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private HotelFacilityMapper facilityMapper;

    @Autowired
    private HotelImageMapper imageMapper;

    @Autowired
    private RoomTypeMapper roomTypeMapper;

    @Override
    public PageVO<HotelVO> getHotelList(HotelQueryDTO queryDTO) {
        // 构建分页对象
        Page<Hotel> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
        
        // 使用 LambdaQueryWrapper 构建查询条件
        LambdaQueryWrapper<Hotel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Hotel::getStatus, 1);  // 只查询上架的酒店
        
        // 酒店名称搜索
        if (queryDTO.getName() != null && !queryDTO.getName().isEmpty()) {
            wrapper.like(Hotel::getName, queryDTO.getName());
        }
        // 城市/地址搜索
        if (queryDTO.getCity() != null && !queryDTO.getCity().isEmpty()) {
            wrapper.like(Hotel::getAddress, queryDTO.getCity());
        }
        // 星级筛选
        if (queryDTO.getStarRating() != null) {
            wrapper.ge(Hotel::getStarRating, queryDTO.getStarRating());
        }
        // 价格范围筛选
        if (queryDTO.getPriceRange() != null && !queryDTO.getPriceRange().isEmpty()) {
            String[] range = queryDTO.getPriceRange().split("-");
            if (range.length == 2) {
                wrapper.between(Hotel::getMinPrice, new BigDecimal(range[0]), new BigDecimal(range[1]));
            }
        }
        // 排序
        if ("price".equals(queryDTO.getSortBy())) {
            wrapper.orderByAsc(Hotel::getMinPrice);
        } else if ("rating".equals(queryDTO.getSortBy())) {
            wrapper.orderByDesc(Hotel::getAvgRating);
        } else {
            wrapper.orderByDesc(Hotel::getCreateTime);
        }
        
        // 执行分页查询
        IPage<Hotel> hotelPage = hotelMapper.selectPage(page, wrapper);
        
        // 转换为 VO 并填充设施和图片
        List<HotelVO> voList = hotelPage.getRecords().stream().map(hotel -> {
            HotelVO vo = new HotelVO();
            BeanUtil.copyProperties(hotel, vo);
            
            // 获取设施列表
            List<HotelFacility> facilities = facilityMapper.selectList(
                new QueryWrapper<HotelFacility>().eq("hotel_id", hotel.getId())
            );
            if (CollectionUtil.isNotEmpty(facilities)) {
                vo.setFacilities(facilities.stream()
                        .map(HotelFacility::getFacility)
                        .collect(Collectors.toList()));
            }
            
            // 获取图片列表
            List<HotelImage> images = imageMapper.selectList(
                new QueryWrapper<HotelImage>().eq("hotel_id", hotel.getId())
                    .orderByAsc("sort_order")
            );
            if (CollectionUtil.isNotEmpty(images)) {
                vo.setImages(images.stream()
                        .map(HotelImage::getImageUrl)
                        .collect(Collectors.toList()));
            }
            return vo;
        }).collect(Collectors.toList());
        
        // 构建分页响应
        PageVO<HotelVO> pageVO = new PageVO<>();
        pageVO.setTotal(hotelPage.getTotal());
        pageVO.setPage((int) hotelPage.getCurrent());
        pageVO.setSize((int) hotelPage.getSize());
        pageVO.setPages((int) hotelPage.getPages());
        pageVO.setRecords(voList);
        
        return pageVO;
    }

    @Override
    public HotelVO getHotelDetail(Long hotelId) {
        Hotel hotel = getById(hotelId);
        if (hotel == null) {
            throw new BusinessException(ErrorCode.HOTEL_NOT_FOUND, "酒店不存在");
        }
        
        HotelVO hotelVO = new HotelVO();
        BeanUtil.copyProperties(hotel, hotelVO);
        
        // 获取设施列表
        List<HotelFacility> facilities = facilityMapper.selectList(
            new QueryWrapper<HotelFacility>().eq("hotel_id", hotelId)
        );
        if (CollectionUtil.isNotEmpty(facilities)) {
            hotelVO.setFacilities(facilities.stream()
                    .map(HotelFacility::getFacility)
                    .collect(Collectors.toList()));
        }
        
        // 获取图片列表
        List<HotelImage> images = imageMapper.selectList(
            new QueryWrapper<HotelImage>().eq("hotel_id", hotelId)
                .orderByAsc("sort_order")
        );
        if (CollectionUtil.isNotEmpty(images)) {
            hotelVO.setImages(images.stream()
                    .map(HotelImage::getImageUrl)
                    .collect(Collectors.toList()));
        }
        
        return hotelVO;
    }

    @Override
    public List<RoomTypeVO> getHotelRooms(Long hotelId) {
        // 检查酒店是否存在
        if (getById(hotelId) == null) {
            throw new BusinessException(ErrorCode.HOTEL_NOT_FOUND, "酒店不存在");
        }
        
        return roomTypeMapper.selectRoomTypeListByHotelId(hotelId);
    }

    @Override
    public void updateHotelRating(Long hotelId) {
        // 重新计算酒店平均评分
        // 这里应该调用评价服务来计算，暂时留空
    }
}