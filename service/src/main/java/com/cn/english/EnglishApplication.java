package com.cn.english;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 项目启动类
 **/
@MapperScan("com.cn.english.mapper") // 配置包扫描
@SpringBootApplication
public class EnglishApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnglishApplication.class);
    }

}
