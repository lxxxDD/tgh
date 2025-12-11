package com.hotelbooking.controller;

import com.hotelbooking.common.Result;
import com.hotelbooking.dto.ForgotPasswordDTO;
import com.hotelbooking.dto.LoginDTO;
import com.hotelbooking.dto.RegisterDTO;
import com.hotelbooking.dto.ResetPasswordDTO;
import com.hotelbooking.dto.ResetPasswordSimpleDTO;
import com.hotelbooking.dto.UpdatePasswordDTO;
import com.hotelbooking.dto.UpdateUserProfileDTO;
import com.hotelbooking.service.SysUserService;
import com.hotelbooking.utils.SecurityUtils;
import com.hotelbooking.vo.LoginVO;
import com.hotelbooking.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 认证控制器
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "用户认证", description = "用户认证相关接口")
public class AuthController {

    @Autowired
    private SysUserService userService;

    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "新用户注册账号")
    public Result<LoginVO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        LoginVO loginVO = userService.register(registerDTO);
        return Result.success("注册成功", loginVO);
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户账号密码登录")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = userService.login(loginDTO);
        return Result.success("登录成功", loginVO);
    }

    @GetMapping("/user/profile")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户详细信息")
    public Result<UserVO> getUserProfile() {
        Long userId = SecurityUtils.getCurrentUserId();
        UserVO userVO = userService.getUserInfo(userId);
        return Result.success(userVO);
    }

    @PutMapping("/user/profile")
    @Operation(summary = "更新用户信息", description = "更新当前用户信息")
    public Result<Void> updateUserProfile(@Valid @RequestBody UpdateUserProfileDTO updateDTO) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.updateUserProfile(userId, updateDTO);
        return Result.success("个人信息更新成功", null);
    }

    @PostMapping("/user/password")
    @Operation(summary = "修改密码", description = "修改当前用户密码")
    public Result<Void> updatePassword(@Valid @RequestBody UpdatePasswordDTO updateDTO) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.updatePassword(userId, updateDTO);
        return Result.success("密码修改成功", null);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "忘记密码", description = "发送密码重置令牌")
    public Result<String> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotDTO) {
        String token = userService.forgotPassword(forgotDTO.getEmail());
        // 实际项目中token应该通过邮件发送，这里直接返回用于测试
        return Result.success("重置令牌已生成，请查收邮件", token);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "重置密码", description = "使用令牌重置密码")
    public Result<Void> resetPassword(@Valid @RequestBody ResetPasswordDTO resetDTO) {
        // 验证两次密码是否一致
        if (!resetDTO.getNewPassword().equals(resetDTO.getConfirmPassword())) {
            return Result.error(400, "两次输入的密码不一致");
        }
        userService.resetPassword(resetDTO.getToken(), resetDTO.getNewPassword());
        return Result.success("密码重置成功", null);
    }

    @GetMapping("/check-username")
    @Operation(summary = "检查用户名是否存在", description = "检查用户名是否已被注册")
    public Result<Boolean> checkUsername(@RequestParam String username) {
        boolean exists = userService.isUsernameExists(username);
        return Result.success(exists);
    }

    @GetMapping("/check-email")
    @Operation(summary = "检查邮箱是否存在", description = "检查邮箱是否已被注册")
    public Result<Boolean> checkEmail(@RequestParam String email) {
        boolean exists = userService.isEmailExists(email);
        return Result.success(exists);
    }

    @PostMapping("/reset-password-simple")
    @Operation(summary = "简化重置密码", description = "通过用户名和邮箱验证重置密码")
    public Result<Void> resetPasswordSimple(@Valid @RequestBody ResetPasswordSimpleDTO resetDTO) {
        userService.resetPasswordByUsernameAndEmail(
            resetDTO.getUsername(), 
            resetDTO.getEmail(), 
            resetDTO.getNewPassword()
        );
        return Result.success("密码重置成功", null);
    }
}