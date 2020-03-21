package com.haoqi.magic.business.config;

import cn.hutool.core.util.StrUtil;
import com.haoqi.rigger.mybatis.provider.OrderNumberProvider;
import com.haoqi.rigger.mybatis.provider.SimpleOrderNumberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author twg
 * @since 2018/8/16
 */
@Configuration
public class OrderNumberConfig {

    @Bean
    public OrderNumberProvider orderNumberProvider(RedisTemplate redisTemplate, Environment environment) {
        SimpleOrderNumberService orderNumberService = new SimpleOrderNumberService();
        orderNumberService.setRedisTemplate(redisTemplate);
        orderNumberService.setNumberPrefix("CS");
        orderNumberService.setRandomNumberLength(4);
        orderNumberService.setPrefix(environment.getRequiredProperty("spring.redis.prefix") +
                StrUtil.COLON + orderNumberService.getNumberPrefix() + StrUtil.COLON);
        return orderNumberService;
    }

    @Bean
    public OrderNumberProvider paymentOrderNumberProvider(RedisTemplate redisTemplate, Environment environment) {
        SimpleOrderNumberService orderNumberService = new SimpleOrderNumberService();
        orderNumberService.setRedisTemplate(redisTemplate);
        orderNumberService.setNumberPrefix("P");
        orderNumberService.setRandomNumberLength(4);
        orderNumberService.setPrefix(environment.getRequiredProperty("spring.redis.prefix") +
                StrUtil.COLON + orderNumberService.getNumberPrefix() + StrUtil.COLON);
        return orderNumberService;
    }

    @Bean
    public OrderNumberProvider carOrderNumberProvider(RedisTemplate redisTemplate, Environment environment) {
        SimpleOrderNumberService orderNumberService = new SimpleOrderNumberService();
        orderNumberService.setRedisTemplate(redisTemplate);
        orderNumberService.setNumberPrefix("D");
        orderNumberService.setRandomNumberLength(4);
        orderNumberService.setPrefix(environment.getRequiredProperty("spring.redis.prefix") +
                StrUtil.COLON + orderNumberService.getNumberPrefix() + StrUtil.COLON);
        return orderNumberService;
    }
}
