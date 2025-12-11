package com.hotelbooking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotelbooking.common.ErrorCode;
import com.hotelbooking.dto.CreateReviewDTO;
import com.hotelbooking.entity.BookingOrder;
import com.hotelbooking.entity.HotelReview;
import com.hotelbooking.enums.OrderStatus;
import com.hotelbooking.exception.BusinessException;
import com.hotelbooking.mapper.BookingOrderMapper;
import com.hotelbooking.mapper.HotelReviewMapper;
import com.hotelbooking.service.HotelReviewService;
import com.hotelbooking.vo.PageVO;
import com.hotelbooking.vo.ReviewVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import com.hotelbooking.entity.SysUser;
import com.hotelbooking.mapper.SysUserMapper;

/**
 * 酒店评价Service实现类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Service
public class HotelReviewServiceImpl extends ServiceImpl<HotelReviewMapper, HotelReview> implements HotelReviewService {

    @Autowired
    private HotelReviewMapper reviewMapper;

    @Autowired
    private BookingOrderMapper orderMapper;

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public PageVO<ReviewVO> getHotelReviews(Long hotelId, Integer page, Integer size) {
        Page<ReviewVO> pageParam = new Page<>(page, size);
        IPage<ReviewVO> reviewPage = reviewMapper.selectReviewPageByHotelId(pageParam, hotelId);
        
        PageVO<ReviewVO> pageVO = new PageVO<>();
        pageVO.setTotal(reviewPage.getTotal());
        pageVO.setPage((int) reviewPage.getCurrent());
        pageVO.setSize((int) reviewPage.getSize());
        pageVO.setPages((int) reviewPage.getPages());
        pageVO.setRecords(reviewPage.getRecords());
        
        return pageVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createReview(Long userId, CreateReviewDTO createDTO) {
        // 检查订单是否存在
        BookingOrder order = orderMapper.selectById(createDTO.getOrderId());
        if (order == null) {
            throw new BusinessException(ErrorCode.ORDER_NOT_FOUND, "订单不存在");
        }
        
        // 检查是否是当前用户的订单
        if (!order.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN, "无权限评价该订单");
        }
        
        // 检查订单状态
        if (!OrderStatus.COMPLETED.getCode().equals(order.getStatus())) {
            throw new BusinessException(ErrorCode.REVIEW_NOT_ALLOWED, "只有已完成的订单才能评价");
        }
        
        // 检查是否已经评价过
        QueryWrapper<HotelReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", createDTO.getOrderId());
        if (reviewMapper.selectCount(queryWrapper) > 0) {
            throw new BusinessException(ErrorCode.REVIEW_EXISTS, "该订单已经评价过了");
        }
        
        // 创建评价
        HotelReview review = new HotelReview();
        review.setHotelId(order.getHotelId());
        review.setUserId(userId);
        review.setOrderId(createDTO.getOrderId());
        review.setRating(createDTO.getRating());
        review.setContent(createDTO.getContent());
        review.setReviewDate(LocalDate.now());
        review.setStatus(1);
        
        reviewMapper.insert(review);
        
        // 更新酒店评分（异步处理）
        // TODO: 发送消息到消息队列，异步更新酒店评分
    }

    @Override
    public List<ReviewVO> getLatestReviews(Integer limit) {
        // 查询最新评价（按时间倒序，只查询已审核的）
        QueryWrapper<HotelReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1)
                .orderByDesc("review_date")
                .last("LIMIT " + limit);
        List<HotelReview> reviews = reviewMapper.selectList(queryWrapper);
        
        // 转换为VO
        return reviews.stream().map(review -> {
            ReviewVO vo = new ReviewVO();
            vo.setId(review.getId());
            vo.setRating(review.getRating());
            vo.setContent(review.getContent());
            vo.setReviewDate(review.getReviewDate());
            
            // 获取用户名
            SysUser user = userMapper.selectById(review.getUserId());
            if (user != null) {
                vo.setUserName(user.getUsername());
            }
            return vo;
        }).collect(Collectors.toList());
    }
}