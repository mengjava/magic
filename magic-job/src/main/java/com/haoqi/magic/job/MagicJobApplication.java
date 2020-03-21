package com.haoqi.magic.job;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.haoqi.magic.*.mapper")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class MagicJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(MagicJobApplication.class, args);
    }

}
