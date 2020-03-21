package com.haoqi.magic.gateway.filter;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.haoqi.magic.gateway.client.SystemServiceClient;
import com.haoqi.rigger.common.SecurityConstants;
import com.haoqi.rigger.config.IgnoreUrlsConfiguration;
import com.haoqi.rigger.core.exception.AuthenticationException;
import com.haoqi.rigger.core.exception.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.MultiValueMap;
import org.springframework.util.PathMatcher;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @author twg
 * @since 2019/1/7
 * API端鉴权
 */
@Slf4j
public class AuthAccessTokenFilter implements GlobalFilter, Ordered {
    private static final String EXPIRED_CAPTCHA_ERROR = "验证码已过期，请重新获取";

    @Value("${security.validate.code:false}")
    private boolean isValidate;
    @Autowired
    private PathMatcher pathMatcher;
    @Autowired
    private RedisTemplate redisTemplate;
    @Value("${spring.redis.prefix:}")
    private String prefix;

    @Autowired
    private IgnoreUrlsConfiguration ignoreUrls;

    @Autowired
    private SystemServiceClient systemServiceClient;

//    @Autowired
//    private RedisTokeStore redisTokeStore;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        boolean isFilter = false;
        for (String url : ignoreUrls.getAnon()) {
            if (pathMatcher.match(url, exchange.getRequest().getURI().getPath())) {
                isFilter = true;
                break;
            }
        }
        if (isFilter && isValidateCode(exchange)) {
            return chain.filter(exchange);
        }
        ServerHttpRequest request = exchange.getRequest();
//        addRequestHeader(request);
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }


    private boolean isValidateCode(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        if (isValidate && checkClientIdValidateCodeIsRequired(request) &&
                request.getURI().getPath().contains(SecurityConstants.OAUTH_TOKEN_URL)) {
            MultiValueMap<String, String> params = request.getQueryParams();
            if (!params.containsKey(SecurityConstants.AUTH_CODE)) {
                throw new ValidateCodeException("请输入验证码");
            }
            if (!params.containsKey("randomStr")) {
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

    private boolean checkClientIdValidateCodeIsRequired(ServerHttpRequest request) {
        String header = request.getHeaders().getFirst(SecurityConstants.REQ_HEADER);
        if (StrUtil.isNotBlank(header) && header.startsWith(SecurityConstants.CLIENT_SPLIT)) {
            String basicToken = new String(Base64.decodeStr(StrUtil.subAfter(header, SecurityConstants.CLIENT_SPLIT, true)));
            int delim = basicToken.indexOf(StrUtil.COLON);
            if (delim == -1) {
                throw new AuthenticationException("Invalid basic authentication token");
            }
        }
        return true;
    }

    /*private void addRequestHeader(ServerHttpRequest request) {
        String accessToken = getTokenFromHeader(request);
        String accessTokenKey = String.format("%s:%s:%s", prefix, SecurityConstants.APP_OAUTH, accessToken);
        OAuthAuthentication authAuthentication = (OAuthAuthentication) redisTemplate.opsForValue().get(accessTokenKey);
        if (authAuthentication == null) {
            log.error("Get UserDetails By AccessToken [{}] is null.");
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), null, null, Charset.defaultCharset());
        }
        DefaultUserDetails details = (DefaultUserDetails) authAuthentication.getUserDetails();
        if (details == null) {
            log.error("Get UserDetails By AccessToken [{}] is null.");
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), null, null, Charset.defaultCharset());
        }
        String username = details.getUsername();
        MDCUserUtil.setUser(username);
        Collection<? extends GrantedRole> roles = details.getAuthorities();
        if (CollectionUtil.isEmpty(roles)) {
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase(), null, null, Charset.defaultCharset());
        }
        List<String> roleList = Lists.newLinkedList();
        if (CollectionUtil.isNotEmpty(roles)) {
            roleList = roles.stream().map(o -> o.getRole()).filter(StrUtil::isNotBlank).collect(Collectors.toList());;
        }
        String ro = CollectionUtil.join(roleList, StrUtil.COMMA);
        Result<UserDTO> result = userServiceClient.getUserByName(username);
        if (log.isDebugEnabled()) {
            log.debug("通过帐号：{}，获取信息:{}", username, result.toString());
        }
        Assert.isTrue(result.isSuccess(), "认证失败");
        UserDTO userDTO = result.getData();
        Result<List<MenuDTO>> menuResult = userServiceClient.findMenuByRoles(userDTO.getId(), roleList.toArray(new String[]{}));
        Assert.isTrue(menuResult.isSuccess(), "无此操作权限，请稍后再试");
        List<MenuDTO> menus = menuResult.getData();
        Assert.isTrue(isMatcherRequestUri(request, menus), "无此操作权限，请稍后再试");

        redisTokeStore.refreshExpiration(accessToken);

        request.mutate()
                .header(SecurityConstants.USER_INFO_HEADER, URLUtil.encode(toStringUserMap(details)))
                .header(SecurityConstants.ROLE_HEADER, ro).build();

    }*/


    /*private Boolean isMatcherRequestUri(ServerHttpRequest request, List<MenuDTO> menus) {
        for (MenuDTO menu : menus) {
            if (StrUtil.isNotBlank(menu.getUrl()) && pathMatcher.match(menu.getUrl(), request.getURI().getPath())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }*/

    private String getTokenFromHeader(ServerHttpRequest request) {
        MultiValueMap<String, String> valueMap = request.getHeaders();
        if (CollectionUtil.isEmpty(valueMap) || !valueMap.containsKey(SecurityConstants.REQ_HEADER) || !valueMap.getFirst(SecurityConstants.REQ_HEADER).startsWith(SecurityConstants.TOKEN_SPLIT)) {
            throw HttpClientErrorException.Unauthorized.create(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase(), null, null, Charset.defaultCharset());
        }
        return StringUtils.substringAfter(valueMap.getFirst(SecurityConstants.REQ_HEADER), SecurityConstants.TOKEN_SPLIT);
    }

    /*private String toStringUserMap(DefaultUserDetails details) {
        Map<String, Object> userMap = Maps.newHashMap();
        userMap.put("id", details.getId());
        userMap.put("userName", details.getUsername());
        userMap.put("userLevel", details.getUserLevel());
        return JSONUtil.toJsonStr(userMap);
    }*/


}