package com.haoqi.magic.auth.config;

import com.haoqi.rigger.security.config.ResourceServerConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author twg
 * @since 2019/8/29
 */
@Configuration
@EnableResourceServer
public class AuthResourceServerConfiguration extends ResourceServerConfiguration {
}
