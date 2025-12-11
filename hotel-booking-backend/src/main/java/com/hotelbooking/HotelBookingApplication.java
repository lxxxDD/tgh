package com.hotelbooking;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 酒店预订管理系统启动类
 * 
 * @author hotelbooking
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.hotelbooking.mapper")
@EnableTransactionManagement

public class HotelBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelBookingApplication.class, args);
    }
}