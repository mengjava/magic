package com.haoqi.magic.gateway.filter;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author twg
 * @see org.springframework.cloud.gateway.filter.LoadBalancerClientFilter
 * @since 2019/1/17
 */
@Slf4j
public class HttpsToHttpFilter implements GlobalFilter, Ordered {
    /**
     * 在LoadBalancerClientFilter执行之前将https修改为Http
     */
    private static final int HTTPS_TO_HTTP_FILTER_ORDER = 10099;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI ui = request.getURI();
        ServerHttpRequest.Builder mutate = request.mutate();
        if (StrUtil.isNotBlank(ui.toString()) && ui.toString().startsWith("https")) {
            try {
                URI mutateUri = new URI("http", ui.getUserInfo(), ui.getHost(), ui.getPort(),
                        ui.getPath(), ui.getQuery(), ui.getFragment());
                mutate.uri(mutateUri);
            } catch (URISyntaxException e) {
                log.error("HttpsToHttpFilter error.", e);
                throw new IllegalArgumentException("https to http error.");
            }
        }
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }

    @Override
    public int getOrder() {
        return HTTPS_TO_HTTP_FILTER_ORDER;
    }
}
