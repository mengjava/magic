package com.haoqi.magic.business;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.alicp.jetcache.anno.config.EnableMethodCache;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.haoqi.magic.*.mapper")
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@EnableMethodCache(basePackages = "com.haoqi.magic.business")
public class MagicBusinessApplication {

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(10000);
        clientHttpRequestFactory.setReadTimeout(10000);
        return new RestTemplate(clientHttpRequestFactory);
    }

    public static void main(String[] args) {
        SpringApplication.run(MagicBusinessApplication.class, args);
    }


}
