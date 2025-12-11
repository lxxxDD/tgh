package com.hotelbooking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotelbooking.common.ErrorCode;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.UserFavorite;
import com.hotelbooking.exception.BusinessException;
import com.hotelbooking.mapper.HotelMapper;
import com.hotelbooking.mapper.UserFavoriteMapper;
import com.hotelbooking.service.UserFavoriteService;
import com.hotelbooking.vo.FavoriteVO;
import com.hotelbooking.vo.PageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户收藏Service实现类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Service
public class UserFavoriteServiceImpl extends ServiceImpl<UserFavoriteMapper, UserFavorite> implements UserFavoriteService {

    @Autowired
    private UserFavoriteMapper favoriteMapper;

    @Autowired
    private HotelMapper hotelMapper;

    @Override
    public PageVO<FavoriteVO> getUserFavorites(Long userId, Integer page, Integer size) {
        // 使用 LambdaQueryWrapper 分页查询收藏记录
        Page<UserFavorite> pageParam = new Page<>(page, size);
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .orderByDesc(UserFavorite::getCreateTime);
        Page<UserFavorite> favoritePage = favoriteMapper.selectPage(pageParam, wrapper);
        
        // 组装 VO
        List<FavoriteVO> voList = new ArrayList<>();
        for (UserFavorite fav : favoritePage.getRecords()) {
            Hotel hotel = hotelMapper.selectById(fav.getHotelId());
            if (hotel != null) {
                FavoriteVO vo = new FavoriteVO();
                vo.setId(fav.getId());
                vo.setHotelId(fav.getHotelId());
                vo.setName(hotel.getName());
                vo.setAddress(hotel.getAddress());
                vo.setStarRating(hotel.getStarRating());
                vo.setAvgRating(hotel.getAvgRating());
                vo.setReviewCount(hotel.getReviewCount());
                vo.setMinPrice(hotel.getMinPrice());
                vo.setCreateTime(fav.getCreateTime());
                voList.add(vo);
            }
        }
        
        PageVO<FavoriteVO> pageVO = new PageVO<>();
        pageVO.setTotal(favoritePage.getTotal());
        pageVO.setPage((int) favoritePage.getCurrent());
        pageVO.setSize((int) favoritePage.getSize());
        pageVO.setPages((int) favoritePage.getPages());
        pageVO.setRecords(voList);
        
        return pageVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFavorite(Long userId, Long hotelId) {
        // 检查酒店是否存在
        Hotel hotel = hotelMapper.selectById(hotelId);
        if (hotel == null) {
            throw new BusinessException(ErrorCode.HOTEL_NOT_FOUND, "酒店不存在");
        }
        
        // 检查是否已经收藏
        if (isFavorite(userId, hotelId)) {
            throw new BusinessException(ErrorCode.FAVORITE_EXISTS, "已经收藏过该酒店");
        }
        
        // 添加收藏
        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(userId);
        favorite.setHotelId(hotelId);
        favorite.setCreateTime(LocalDateTime.now());
        
        favoriteMapper.insert(favorite);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFavorite(Long userId, Long hotelId) {
        // 检查是否存在收藏记录
        QueryWrapper<UserFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("hotel_id", hotelId);
        UserFavorite favorite = favoriteMapper.selectOne(queryWrapper);
        
        if (favorite == null) {
            throw new BusinessException(ErrorCode.FAVORITE_NOT_FOUND, "收藏记录不存在");
        }
        
        // 删除收藏记录
        favoriteMapper.deleteById(favorite.getId());
    }

    @Override
    public boolean isFavorite(Long userId, Long hotelId) {
        QueryWrapper<UserFavorite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId).eq("hotel_id", hotelId);
        return favoriteMapper.selectCount(queryWrapper) > 0;
    }
}