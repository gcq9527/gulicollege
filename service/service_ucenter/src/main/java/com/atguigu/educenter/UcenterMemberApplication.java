package com.atguigu.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author gcq
 * @Create 2020-09-16
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.atguigu"})
@MapperScan("com.atguigu.educenter.mapper")
public class UcenterMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterMemberApplication.class, args);
    }
}