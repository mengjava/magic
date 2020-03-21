package com.haoqi.magic.gateway.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONUtil;
import com.haoqi.magic.gateway.client.SystemServiceClient;
import com.haoqi.magic.gateway.model.dto.MenuDTO;
import com.haoqi.magic.gateway.model.dto.RoleDTO;
import com.haoqi.magic.gateway.model.dto.UserDTO;
import com.haoqi.rigger.common.SecurityConstants;
import com.haoqi.rigger.config.IgnoreUrlsConfiguration;
import com.haoqi.rigger.config.core.JsonRedisTemplate;
import com.haoqi.rigger.core.Result;
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
import org.springframework.util.Assert;
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
 * app端鉴权
 */
@Slf4j
@Component
@RefreshScope
public class AppAuthAccessTokenFilter implements GlobalFilter, Ordered {

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

    private Boolean isFilter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
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
            addRequestHeader(request);
        }
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


    private void addRequestHeader(ServerHttpRequest request) {
        MultiValueMap<String, String> valueMap = request.getHeaders();
        if (CollectionUtil.isNotEmpty(valueMap) && valueMap.containsKey("USERTOKEN")) {
            Map<String, Object> userMap = (Map) redisTemplate.opsForValue().get(valueMap.getFirst("USERTOKEN"));
            if (Objects.isNull(userMap)) {
                throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), null, null, Charset.defaultCharset());
            }
            String userName = (String) userMap.get("mobile");
            Integer userType = (Integer) userMap.get("userType");
            UserUtil.setUser(userName);
            Result<UserDTO> result = systemServiceClient.getUserRolesByName(userName);
            if (!result.isSuccess() || result.getData() == null || !result.getData().getType().equals(userType)) {
                log.error("mobile [{}] userType [{}] not exist or not equals", userName, userType);
                throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), null, null, Charset.defaultCharset());
            }
            List<String> roles = result.getData().getRoles().stream().map(RoleDTO::getRoleCode).collect(Collectors.toList());
            if (CollectionUtil.isNotEmpty(roles) && !isFilter) {
                Result<List<MenuDTO>> menuResult = systemServiceClient.findMenuByRoles(result.getData().getId(), roles.toArray(new String[]{}));
                Assert.isTrue(menuResult.isSuccess(), "无此操作权限，请稍后再试");
                Assert.isTrue(isMatcherRequestUri(request, menuResult.getData()), "无此操作权限，请稍后再试");
            }
            refreshTokenExpire((String) userMap.get("token"));
            userMap.put("userName", userName);
            userMap.put("realName", result.getData().getUsername());
            userMap.remove("mobile");
            request.mutate()
                    .header(SecurityConstants.USER_INFO_HEADER, URLUtil.encode(JSONUtil.toJsonStr(userMap)))
                    .header(SecurityConstants.ROLE_HEADER, CollectionUtil.join(roles, StrUtil.COMMA)).build();
        }
    }

    private void refreshTokenExpire(String token) {
        LocalDateTime now = LocalDateTime.now();
        int expire = (int) (now.plusDays(7).toEpochSecond(ZoneOffset.UTC) - now.toEpochSecond(ZoneOffset.UTC));
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