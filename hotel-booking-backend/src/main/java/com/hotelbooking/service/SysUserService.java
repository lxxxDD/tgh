package com.hotelbooking.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hotelbooking.dto.LoginDTO;
import com.hotelbooking.dto.RegisterDTO;
import com.hotelbooking.dto.UpdatePasswordDTO;
import com.hotelbooking.dto.UpdateUserProfileDTO;
import com.hotelbooking.entity.SysUser;
import com.hotelbooking.vo.LoginVO;
import com.hotelbooking.vo.UserVO;

/**
 * 用户Service接口
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 用户注册
     */
    LoginVO register(RegisterDTO registerDTO);

    /**
     * 用户登录
     */
    LoginVO login(LoginDTO loginDTO);

    /**
     * 获取用户信息
     */
    UserVO getUserInfo(Long userId);

    /**
     * 更新用户信息
     */
    void updateUserProfile(Long userId, UpdateUserProfileDTO updateDTO);

    /**
     * 修改密码
     */
    void updatePassword(Long userId, UpdatePasswordDTO updateDTO);

    /**
     * 根据用户名或邮箱查找用户
     */
    SysUser findByUsernameOrEmail(String username);

    /**
     * 检查用户名是否存在
     */
    boolean isUsernameExists(String username);

    /**
     * 检查邮箱是否存在
     */
    boolean isEmailExists(String email);

    /**
     * 发送密码重置邮件（生成重置令牌）
     */
    String forgotPassword(String email);

    /**
     * 重置密码
     */
    void resetPassword(String token, String newPassword);

    /**
     * 通过用户名和邮箱验证重置密码
     */
    void resetPasswordByUsernameAndEmail(String username, String email, String newPassword);
}