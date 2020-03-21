package com.haoqi.magic.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.haoqi.magic.gateway.client.SystemServiceClient;
import com.haoqi.magic.gateway.model.dto.MenuDTO;
import com.haoqi.magic.gateway.model.dto.RoleDTO;
import com.haoqi.magic.gateway.model.dto.UserDTO;
import com.haoqi.rigger.common.SecurityConstants;
import com.haoqi.rigger.config.IgnoreUrlsConfiguration;
import com.haoqi.rigger.config.core.JsonRedisTemplate;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.exception.ValidateCodeException;
import com.haoqi.rigger.core.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author twg
 * @since 2019/1/7
 * web端鉴权
 */
@Slf4j
@Component
@RefreshScope
public class WebAuthAccessTokenFilter implements GlobalFilter, Ordered {
    private static final String EXPIRED_CAPTCHA_ERROR = "验证码已过期，请重新获取！";
    private static final String UNAUTHORIZED_ERROR = "无此操作权限，请稍后再试！";

    @Value("${security.validate.code:false}")
    private boolean isValidate;

    @Autowired
    private PathMatcher pathMatcher;

    @Autowired
    private JsonRedisTemplate<String, Object> redisTemplate;

    @Value("${spring.redis.prefix:}")
    private String prefix;

    @Autowired
    private IgnoreUrlsConfiguration ignoreUrls;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        boolean isFilter = false;
        for (String url : ignoreUrls.getAnon()) {
            if (pathMatcher.match(url, exchange.getRequest().getURI().getPath())) {
                isFilter = true;
                break;
            }
        }
        ServerHttpRequest request = exchange.getRequest();
        //App端header key 存在的话
        final String acceptHeader = request.getHeaders().getFirst(SecurityConstants.APP_ACCEPT_HEADER);
        boolean isWeb = !StrUtil.containsAnyIgnoreCase(acceptHeader, SecurityConstants.APP_ACCEPT_VALUE);
        if (!isWeb) {
            return chain.filter(exchange);
        }
        if (isFilter && isValidateCode(exchange)) {
            addUserInfoToHeader(request);
            return chain.filter(exchange);
        }
        addRequestHeader(request);
        return chain.filter(exchange.mutate().request(request).build());
    }


    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


    private boolean isValidateCode(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (isValidate && ReUtil.isMatch("^([A-Za-z0-9_]*/)*(login)+(/)*([A-Za-z\\/-～=&%_]*)$", request.getURI().getPath())) {
            MultiValueMap<String, String> params = request.getQueryParams();
            if (!params.containsKey(SecurityConstants.AUTH_CODE) || !params.containsKey("randomStr")) {
                throw new ValidateCodeException("请输入验证码");
            }
            String key = String.format("%s:%s:%s", prefix, SecurityConstants.DEFAULT_CODE_KEY, params.getFirst("randomStr"));
            if (!redisTemplate.hasKey(key)) {
                throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
            }
            String codeObj = (String) redisTemplate.opsForValue().get(key);
            redisTemplate.delete(key);
            if (StrUtil.isBlank(codeObj)) {
                throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
            }
            if (!StrUtil.equals(codeObj, params.getFirst(SecurityConstants.AUTH_CODE))) {
                throw new ValidateCodeException("验证码错误，请重新输入");
            }
        }
        return true;
    }

    /**
     * 请求头中添加用户信息
     *
     * @param request
     */
    private void addUserInfoToHeader(ServerHttpRequest request) {
        MultiValueMap<String, String> valueMap = request.getHeaders();
        if (CollectionUtil.isEmpty(valueMap) || !valueMap.containsKey("USERTOKEN")) {
            return;
        }
        Map userMap = (Map) redisTemplate.opsForValue().get(valueMap.getFirst("USERTOKEN"));
        if (CollectionUtil.isNotEmpty(userMap)) {
            String userName = (String) userMap.get("userName");
            UserUtil.setUser(userName);
            request.mutate()
                    .header(SecurityConstants.USER_INFO_HEADER, URLUtil.encode(JSONUtil.toJsonStr(userMap)));
        }
    }

    /**
     * 通过请求头中的USERTOKEN信息，判断是否是合法用户
     *
     * @param request
     */
    private void addRequestHeader(ServerHttpRequest request) {
        MultiValueMap<String, String> valueMap = request.getHeaders();
        if (CollectionUtil.isEmpty(valueMap) || !valueMap.containsKey("USERTOKEN") || StrUtil.isBlank(valueMap.getFirst("USERTOKEN"))) {
            log.error("USERTOKEN must be required");
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_ERROR, null, null, Charset.defaultCharset());
        }
        Map userMap = (Map) redisTemplate.opsForValue().get(valueMap.getFirst("USERTOKEN"));
        if (Objects.isNull(userMap)) {
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_ERROR, null, null, Charset.defaultCharset());
        }
        String userName = (String) userMap.get("userName");
        Integer userType = (Integer) userMap.get("userType");
        UserUtil.setUser(userName);
        //通过用户类型来限制前端是否车商登录后操作
        if (valueMap.containsKey("USERTYPE")) {
            String user_type = valueMap.getFirst("USERTYPE");
            if (!DigestUtil.bcryptCheck(userType.toString(), user_type)) {
                throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_ERROR, null, null, Charset.defaultCharset());
            }
        }
        Result<UserDTO> result = systemServiceClient.getUserRolesByName(userName);
        if (!result.isSuccess() || result.getData() == null || !result.getData().getType().equals(userType)) {
            log.error("userName [{}] userType [{}] not exist or not equals", userName, userType);
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_ERROR, null, null, Charset.defaultCharset());
        }
        List<String> roles = result.getData().getRoles().stream().map(RoleDTO::getRoleCode).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(roles)) {
            Result<List<MenuDTO>> menuResult = systemServiceClient.findMenuByRoles(result.getData().getId(), roles.toArray(new String[]{}));
            if (!menuResult.isSuccess() || !isMatcherRequestUri(request, menuResult.getData())) {
                throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, UNAUTHORIZED_ERROR, null, null, Charset.defaultCharset());
            }
        }
        refreshTokenExpire((String) userMap.get("token"));
        userMap.put("realName", result.getData().getUsername());
        request.mutate()
                .header(SecurityConstants.USER_INFO_HEADER, URLUtil.encode(JSONUtil.toJsonStr(userMap)))
                .header(SecurityConstants.ROLE_HEADER, CollectionUtil.join(roles, StrUtil.COMMA)).build();

    }

    private void refreshTokenExpire(String token) {
        LocalDateTime now = LocalDateTime.now();
        int expire = (int) (now.plusDays(1).toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC));
        redisTemplate.expire(token, expire, TimeUnit.SECONDS);
    }


    private Boolean isMatcherRequestUri(ServerHttpRequest request, List<MenuDTO> menus) {
        for (MenuDTO menu : menus) {
            if (StrUtil.isNotBlank(menu.getUrl()) && pathMatcher.match(menu.getUrl(), request.getURI().getPath())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}