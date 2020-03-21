package com.haoqi.magic.auth.config;

import com.haoqi.magic.auth.provider.UserLoginAuthenticationProvider;
import com.haoqi.magic.auth.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * @author twg
 * @since 2019/4/19
 */
@Component
public class UserLoginAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    @Autowired
    private IUserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        UserLoginAuthenticationProvider loginAuthenticationProvider = new UserLoginAuthenticationProvider(userService, passwordEncoder);
        builder.authenticationProvider(loginAuthenticationProvider);
    }
}
