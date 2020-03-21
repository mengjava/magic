package com.haoqi.magic.auth.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.haoqi.magic.auth.filter.CustomAppMobileLoginAuthenticationProcessingFilter;
import com.haoqi.magic.auth.filter.CustomMobileLoginAuthenticationProcessingFilter;
import com.haoqi.magic.auth.filter.CustomUserLoginAuthenticationProcessingFilter;
import com.haoqi.magic.auth.provider.JdbcClientDetailsService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.security.builder.ClientDetailsConfigurerBuilder;
import com.haoqi.rigger.security.config.SecurityConfiguration;
import com.haoqi.rigger.security.oauth2.filter.CustomErrorFilter;
import com.haoqi.rigger.security.web.CustomSimpleUrlAuthenticationFailureHandler;
import com.haoqi.rigger.security.web.CustomSimpleUrlAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author twg
 * @since 2019/8/29
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class AuthSecurityConfiguration extends SecurityConfiguration {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private MobileAuthenticationSecurityConfig mobileAuthenticationSecurityConfig;

    @Autowired
    private AppMobileAuthenticationSecurityConfig appMobileAuthenticationSecurityConfig;

    @Autowired
    private UserLoginAuthenticationSecurityConfig userLoginAuthenticationSecurityConfig;


    @Override
    public void configure(HttpSecurity http) throws Exception {

        //json 模式，前后端分离
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/**/login","/webjars/**")
                .permitAll()
                .and().exceptionHandling()
                //登录后访问拒绝异常处理
//                    .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(new CustomHttpStatusEntryPoint(HttpStatus.OK))
                .and().logout().deleteCookies("USERTOKEN")
                .addLogoutHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                    String token = request.getHeader("USERTOKEN");
                    if (StrUtil.isNotBlank(token) && redisTemplate.hasKey(token)) {
                        redisTemplate.delete(token);
                    }
                })
                .clearAuthentication(true)
                //自定义logout处理，返回json
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setStatus(HttpStatus.OK.value());
                    response.setCharacterEncoding(Charset.defaultCharset().name());
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8.getType());
                    response.getWriter().write(JSON.toJSONString(Result.buildSuccessResult("logout success")));
                    response.getWriter().flush();
                })
                .and()
                .apply(mobileAuthenticationSecurityConfig)
                .and()
                .apply(appMobileAuthenticationSecurityConfig)
                .and()
                .apply(userLoginAuthenticationSecurityConfig)
                .and()
                .csrf().disable()
                //对于需要返回json格式的头信息的异常处理配置
                .addFilterAfter(customErrorFilter(), ExceptionTranslationFilter.class)
                .addFilterAt(userLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(appMobileLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(mobileLoginFilter(), UsernamePasswordAuthenticationFilter.class);
        // @formatter:on
    }


    private class CustomHttpStatusEntryPoint implements AuthenticationEntryPoint {
        private final HttpStatus httpStatus;

        private CustomHttpStatusEntryPoint(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
        }

        @Override
        public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8.getType());
            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
            response.setStatus(HttpStatus.OK.value());
            response.getWriter().write(JSON.toJSONString(Result.buildErrorResult(httpStatus.value(), StrUtil.isNotBlank(authException.getMessage()) ? authException.getMessage() : "用户名或密码错误")));
        }
    }

    private Filter mobileLoginFilter() throws Exception {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        CustomMobileLoginAuthenticationProcessingFilter mobileLoginAuthenticationProcessingFilter = new CustomMobileLoginAuthenticationProcessingFilter(redisTemplate, "/m/login");
        mobileLoginAuthenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        //对于一个基于rest的web服务返回应该是200,应该开启
        mobileLoginAuthenticationProcessingFilter.setAuthenticationSuccessHandler(new CustomSimpleUrlAuthenticationSuccessHandler());
        mobileLoginAuthenticationProcessingFilter.setAuthenticationFailureHandler(new CustomSimpleUrlAuthenticationFailureHandler(HttpStatus.OK, "用户名或密码错误！"));
        filters.add(mobileLoginAuthenticationProcessingFilter);
        filter.setFilters(filters);
        return filter;
    }

    /**
     * app 非检测端登录
     *
     * @return
     * @throws Exception
     */
    private Filter appMobileLoginFilter() throws Exception {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        CustomAppMobileLoginAuthenticationProcessingFilter mobileLoginAuthenticationProcessingFilter = new CustomAppMobileLoginAuthenticationProcessingFilter(redisTemplate, "/a/login");
        mobileLoginAuthenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        //对于一个基于rest的web服务返回应该是200,应该开启
        mobileLoginAuthenticationProcessingFilter.setAuthenticationSuccessHandler(new CustomSimpleUrlAuthenticationSuccessHandler());
        mobileLoginAuthenticationProcessingFilter.setAuthenticationFailureHandler(new CustomSimpleUrlAuthenticationFailureHandler(HttpStatus.OK, "用户名或密码错误！"));
        filters.add(mobileLoginAuthenticationProcessingFilter);
        filter.setFilters(filters);
        return filter;
    }

    private Filter userLoginFilter() throws Exception {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        CustomUserLoginAuthenticationProcessingFilter userLoginAuthenticationProcessingFilter = new CustomUserLoginAuthenticationProcessingFilter(redisTemplate, "/u/login");
        userLoginAuthenticationProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        //对于一个基于rest的web服务返回应该是200,应该开启
        userLoginAuthenticationProcessingFilter.setAuthenticationSuccessHandler(new CustomSimpleUrlAuthenticationSuccessHandler());
        userLoginAuthenticationProcessingFilter.setAuthenticationFailureHandler(new CustomSimpleUrlAuthenticationFailureHandler(HttpStatus.OK, "用户名或密码错误！"));
        filters.add(userLoginAuthenticationProcessingFilter);
        filter.setFilters(filters);
        return filter;
    }

    private Filter customErrorFilter() {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(Arrays.asList(new CustomErrorFilter()));
        return filter;
    }


    @Bean
    public ClientDetailsConfigurerBuilder clientDetailsConfigurerBuilder() {
        return new CustomClientDetailsConfigurerBuilder();
    }

    @Bean
    public ClientDetailsService JdbcClientDetailsService() {
        return new JdbcClientDetailsService();
    }


    private class CustomClientDetailsConfigurerBuilder implements ClientDetailsConfigurerBuilder {
        @Override
        public void build(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(JdbcClientDetailsService());
        }
    }


}
