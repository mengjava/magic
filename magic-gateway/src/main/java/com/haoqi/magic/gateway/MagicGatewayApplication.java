package com.haoqi.magic.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MagicGatewayApplication {

    @Bean
    public PathMatcher pathMatcher() {
        return new AntPathMatcher();
    }

    public static void main(String[] args) {
        SpringApplication.run(MagicGatewayApplication.class, args);
    }

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            if (CorsUtils.isCorsRequest(request)) {
                ServerHttpResponse response = ctx.getResponse();
                HttpHeaders headers = response.getHeaders();
                if (log.isDebugEnabled()) {
                    log.debug(" origin : {}", request.getHeaders().get(HttpHeaders.ORIGIN).get(0));
                }
                headers.setAccessControlAllowOrigin((request.getHeaders().get(HttpHeaders.ORIGIN)).get(0));
                headers.setAccessControlAllowCredentials(true);
                headers.setAccessControlMaxAge(Integer.MAX_VALUE);
                headers.setAccessControlAllowHeaders(Arrays.asList("*", "usertoken","usertype", HttpHeaders.CONTENT_TYPE));
                headers.setAccessControlAllowMethods(Arrays.asList(HttpMethod.OPTIONS,
                        HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST,
                        HttpMethod.DELETE, HttpMethod.PUT));
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }
            }
            return chain.filter(ctx);
        };
    }

}
