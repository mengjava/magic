package com.haoqi.magic.auth.filter;

import com.haoqi.magic.auth.provider.AppMobileAuthenticationToken;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.SecureUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
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
 * @see com.haoqi.rigger.security.oauth2.filter.MobileLoginAuthenticationProcessingFilter
 * 自定义手机端认证过滤器
 * @since 2019/5/13
 */
@Slf4j
public class CustomAppMobileLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private boolean postOnly = true;
    private final RedisTemplate<String, Object> redisTemplate;

    public CustomAppMobileLoginAuthenticationProcessingFilter(RedisTemplate<String, Object> redisTemplate, String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String username = request.getParameter("mobile");
            String password = request.getParameter("password");
            if (Objects.isNull(username)) {
                throw new AuthenticationServiceException("手机号不能为空");
            } else if (Objects.isNull(password)) {
                throw new AuthenticationServiceException("密码不能为空");
            } else {
                try {
                    String context = SecureUtils.decryptRSAByPrivateKey(password, Constants.privateKey);
                    AppMobileAuthenticationToken authRequest = new AppMobileAuthenticationToken(username, context);
                    Authentication authentication = this.getAuthenticationManager().authenticate(authRequest);
                    if (Objects.nonNull(authentication)) {
                        Map map = (Map) authentication.getDetails();
                        LocalDateTime now = LocalDateTime.now();
                        int expire = (int) (now.plusDays(7).toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC));
                        this.redisTemplate.opsForValue().set((String) map.get("token"), map, expire, TimeUnit.SECONDS);
                    }
                    return authentication;
                } catch (Exception e) {
                    log.error("attemptAuthentication error.", e);
                    throw new AuthenticationServiceException(e.getMessage());
                }

            }
        }
    }
}
