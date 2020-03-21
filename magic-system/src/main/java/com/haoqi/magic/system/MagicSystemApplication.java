package com.haoqi.magic.system;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.haoqi.magic.*.mapper")
@EnableMethodCache(basePackages = "com.haoqi.magic.system")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class MagicSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MagicSystemApplication.class, args);
	}

}
