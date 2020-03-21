package com.haoqi.magic.auth.config;

import com.haoqi.rigger.security.config.OAuth2Configuration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author twg
 * @since 2019/8/29
 */
@Configuration
@EnableAuthorizationServer
public class AuthOAuth2Configuration extends OAuth2Configuration {
}
