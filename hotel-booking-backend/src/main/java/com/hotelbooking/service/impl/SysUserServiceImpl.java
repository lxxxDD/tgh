package com.hotelbooking.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hotelbooking.common.ErrorCode;
import com.hotelbooking.common.Result;
import com.hotelbooking.dto.LoginDTO;
import com.hotelbooking.dto.RegisterDTO;
import com.hotelbooking.dto.UpdatePasswordDTO;
import com.hotelbooking.dto.UpdateUserProfileDTO;
import com.hotelbooking.entity.SysUser;
import com.hotelbooking.enums.UserStatus;
import com.hotelbooking.exception.BusinessException;
import com.hotelbooking.mapper.SysUserMapper;
import com.hotelbooking.service.SysUserService;
import com.hotelbooking.utils.JwtUtils;
import com.hotelbooking.vo.LoginVO;
import com.hotelbooking.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 用户Service实现类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO register(RegisterDTO registerDTO) {
        // 检查用户名是否已存在
        if (isUsernameExists(registerDTO.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS, "用户名已存在");
        }

        // 检查邮箱是否已存在
        if (isEmailExists(registerDTO.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_EXISTS, "邮箱已存在");
        }

        // 创建新用户
        SysUser user = new SysUser();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setPhone(registerDTO.getPhone());
        user.setStatus(UserStatus.ENABLED.getCode());

        // 保存用户
        userMapper.insert(user);

        // 生成Token
        String accessToken = jwtUtils.generateAccessToken(user.getId(), user.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(user.getId(), user.getUsername());

        // 构建响应
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setEmail(user.getEmail());
        loginVO.setToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        loginVO.setTokenExpire(jwtUtils.getAccessTokenExpiration());
        loginVO.setRefreshTokenExpire(jwtUtils.getRefreshTokenExpiration());

        return loginVO;
    }

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 根据用户名或邮箱查找用户
        SysUser user = findByUsernameOrEmail(loginDTO.getUsername());
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 检查用户状态
        if (user.getStatus() == UserStatus.DISABLED.getCode()) {
            throw new BusinessException(ErrorCode.ACCOUNT_LOCKED, "账号已被禁用");
        }

        // 验证密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR, "密码错误");
        }

        // 生成Token
        String accessToken = jwtUtils.generateAccessToken(user.getId(), user.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(user.getId(), user.getUsername());

        // 构建响应
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setEmail(user.getEmail());
        loginVO.setToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        loginVO.setTokenExpire(jwtUtils.getAccessTokenExpiration());
        loginVO.setRefreshTokenExpire(jwtUtils.getRefreshTokenExpiration());

        return loginVO;
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        UserVO userVO = new UserVO();
        BeanUtil.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserProfile(Long userId, UpdateUserProfileDTO updateDTO) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 检查邮箱是否被其他用户使用
        if (!user.getEmail().equals(updateDTO.getEmail())) {
            QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
            queryWrapper.ne("id", userId).eq("email", updateDTO.getEmail());
            if (userMapper.selectCount(queryWrapper) > 0) {
                throw new BusinessException(ErrorCode.EMAIL_EXISTS, "邮箱已被其他用户使用");
            }
        }

        // 更新用户信息
        user.setEmail(updateDTO.getEmail());
        user.setPhone(updateDTO.getPhone());
        user.setRealName(updateDTO.getRealName());
        user.setIdCard(updateDTO.getIdCard());

        userMapper.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, UpdatePasswordDTO updateDTO) {
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 验证当前密码
        if (!passwordEncoder.matches(updateDTO.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR, "当前密码错误");
        }

        // 检查新密码是否与当前密码相同
        if (passwordEncoder.matches(updateDTO.getNewPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "新密码不能与当前密码相同");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(updateDTO.getNewPassword()));
        userMapper.updateById(user);
    }

    @Override
    public SysUser findByUsernameOrEmail(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username)
                .or()
                .eq("email", username);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public boolean isUsernameExists(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    @Override
    public boolean isEmailExists(String email) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        return userMapper.selectCount(queryWrapper) > 0;
    }

    // 简单的内存存储重置令牌（生产环境应使用Redis）
    private static final java.util.Map<String, Long> resetTokens = new java.util.concurrent.ConcurrentHashMap<>();
    private static final java.util.Map<String, LocalDateTime> tokenExpiry = new java.util.concurrent.ConcurrentHashMap<>();

    @Override
    public String forgotPassword(String email) {
        // 查找用户
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        SysUser user = userMapper.selectOne(queryWrapper);
        
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "该邮箱未注册");
        }

        // 生成重置令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        
        // 存储令牌（有效期30分钟）
        resetTokens.put(token, user.getId());
        tokenExpiry.put(token, LocalDateTime.now().plusMinutes(30));

        // 实际项目中这里应该发送邮件
        // 这里直接返回令牌用于测试
        return token;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String token, String newPassword) {
        // 验证令牌
        Long userId = resetTokens.get(token);
        if (userId == null) {
            throw new BusinessException(ErrorCode.BAD_REQUEST, "无效的重置令牌");
        }

        // 检查令牌是否过期
        LocalDateTime expiry = tokenExpiry.get(token);
        if (expiry == null || LocalDateTime.now().isAfter(expiry)) {
            resetTokens.remove(token);
            tokenExpiry.remove(token);
            throw new BusinessException(ErrorCode.BAD_REQUEST, "重置令牌已过期");
        }

        // 查找用户
        SysUser user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户不存在");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateById(user);

        // 删除已使用的令牌
        resetTokens.remove(token);
        tokenExpiry.remove(token);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPasswordByUsernameAndEmail(String username, String email, String newPassword) {
        // 根据用户名和邮箱查找用户
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("email", email);
        SysUser user = userMapper.selectOne(queryWrapper);
        
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND, "用户名或邮箱不匹配");
        }

        // 更新密码
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updateById(user);
    }
}