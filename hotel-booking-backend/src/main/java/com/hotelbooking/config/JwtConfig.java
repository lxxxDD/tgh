package com.hotelbooking.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT配置属性
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    /**
     * JWT密钥
     */
    private String secret;

    /**
     * Access Token过期时间（分钟）
     */
    private Long accessTokenExpire;

    /**
     * Refresh Token过期时间（天）
     */
    private Long refreshTokenExpire;

    /**
     * JWT发行者
     */
    private String issuer;
}