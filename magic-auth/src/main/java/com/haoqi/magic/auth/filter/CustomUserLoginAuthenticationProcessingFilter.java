package com.haoqi.magic.auth.filter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.haoqi.rigger.security.authentication.UserNamePasswordAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author twg
 * @since 2019/5/29
 */
@Slf4j
public class CustomUserLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private boolean postOnly = true;
    private final RedisTemplate<String, Object> redisTemplate;

    public CustomUserLoginAuthenticationProcessingFilter(RedisTemplate<String, Object> redisTemplate, String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (Objects.isNull(username)) {
            throw new AuthenticationServiceException("username must required");
        }
        if (Objects.isNull(password)) {
            throw new AuthenticationServiceException("password must required");
        }
        UserNamePasswordAuthenticationToken authRequest = new UserNamePasswordAuthenticationToken(
                username, password);
        Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);
        if (Objects.nonNull(authentication)) {
            if (authentication.getDetails() instanceof Map) {
                Map userMap = (Map) authentication.getDetails();
                String userType = request.getHeader("USERTYPE");
                if (StrUtil.isNotBlank(userType) && !DigestUtil.bcryptCheck(((Integer) userMap.get("userType")).toString(), userType)) {
                    throw new BadCredentialsException("username or password error");
                }
                Cookie cookie = new Cookie("USERTOKEN", (String) userMap.get("token"));
                cookie.setSecure(request.isSecure());
                LocalDateTime now = LocalDateTime.now();
                int expire = (int) (now.plusDays(1).toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC));
                cookie.setMaxAge(expire);
                response.addCookie(cookie);
                redisTemplate.opsForValue().set(cookie.getValue(), userMap, expire, TimeUnit.SECONDS);
            }
        }
        return authentication;
    }
}
