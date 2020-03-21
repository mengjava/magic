package com.haoqi.magic.system.config;

import com.haoqi.magic.system.common.sms.BuYunSmsUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ClassName:com.haoyun.common.util.sms <br/>
 * Function: 调用步云短信发送接口<br/>
 * Date:     2018/5/17 15:59 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Configuration
public class BuYunSmsConfig {

    @Bean
    @ConfigurationProperties(prefix = "buyun.sms")
    public BuYunSmsUtil buYunSmsUtil() {
        return new BuYunSmsUtil();
    }
}
