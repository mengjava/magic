package com.haoqi.magic.gateway.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.haoqi.rigger.common.SecurityConstants;
import com.haoqi.rigger.core.error.RiggerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Level;

/**
 * @author twg
 * @since 2019/5/9
 */
@Slf4j
@Component
public class ModifyRequestBodyForAppFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        final Class inClass = String.class;
        final ServerHttpRequest request = exchange.getRequest();
        final String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
        final String acceptHeader = request.getHeaders().getFirst(SecurityConstants.APP_ACCEPT_HEADER);
        //对web端放行
        if (!StrUtil.containsAnyIgnoreCase(acceptHeader, SecurityConstants.APP_ACCEPT_VALUE) ||
                !StrUtil.containsAnyIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE)) {
            return chain.filter(exchange);
        }
        //设备机器编号
        final String devId = request.getHeaders().getFirst("devId");
        //请求时间
        final String time = request.getHeaders().getFirst("time");
        if (StrUtil.hasBlank(time)) {
            throw new RiggerException(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        }
        //校验请求是否超时、参数加密方式
        if (SecurityConstants.REQUEST_TIMEOUT < DateUtil.spendMs(Long.parseLong(time))) {
            throw new RiggerException(HttpStatus.REQUEST_TIMEOUT.value(), HttpStatus.REQUEST_TIMEOUT.getReasonPhrase());
        }
        final ServerRequest serverRequest = new DefaultServerRequest(exchange);
        Mono<?> modifiedBody = serverRequest.bodyToMono(String.class)
                .log("modify_request ==== ", Level.INFO)
                .flatMap(o -> {
                    try {
//                        String context = SecureUtils.decryptRSAByPrivateKey(o, Constants.privateKey);
//                        log.info("body is {} decodeFromString : {}", o, context);
                        return Mono.justOrEmpty(o);
                    } catch (Exception e) {
                        log.error("App 请求内容解密异常", e);
                        throw new RiggerException("App 请求内容解密异常");
                    }
                });
        BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, inClass);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);

        return bodyInserter.insert(outputMessage, new BodyInserterContext())
                .then(Mono.defer(() -> {
                    ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                            exchange.getRequest()) {
                        @Override
                        public HttpHeaders getHeaders() {
                            long contentLength = headers.getContentLength();
                            HttpHeaders httpHeaders = new HttpHeaders();
                            httpHeaders.putAll(super.getHeaders());
                            if (contentLength > 0) {
                                httpHeaders.setContentLength(contentLength);
                            } else {
                                // TODO: this causes a 'HTTP/1.1 411 Length Required'
                                // on httpbin.org
                                httpHeaders.set(HttpHeaders.TRANSFER_ENCODING,
                                        "chunked");
                            }
                            return httpHeaders;
                        }

                        @Override
                        public Flux<DataBuffer> getBody() {
                            return outputMessage.getBody();
                        }
                    };
                    return chain.filter(exchange.mutate().request(decorator).build());
                }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE - 5;
    }
}
