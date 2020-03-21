package com.haoqi.magic.gateway.error;

import com.google.common.collect.Maps;
import com.haoqi.rigger.core.error.ErrorCodeEnum;
import com.haoqi.rigger.core.error.RiggerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.reactive.function.server.*;

import java.util.Map;
import java.util.Objects;

/**
 * @author twg
 * @since 2018/12/29
 */
@Slf4j
public class ErrorHandler extends DefaultErrorHandler {
    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resourceProperties the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     */
    public ErrorHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties, ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, boolean includeStackTrace) {
        Throwable throwable = super.getError(request);
        return response(throwable, this.buildMessage(request, throwable));
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes
     */
    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus
     *
     * @param errorAttributes
     */
    @Override
    protected ErrorCodeEnum getHttpStatus(Map<String, Object> errorAttributes) {
        int statusCode = (int) errorAttributes.get("code");
        return ErrorCodeEnum.valueOf(statusCode);
    }


    /**
     * 构建异常信息
     *
     * @param request
     * @param ex
     * @return
     */
    private String buildMessage(ServerRequest request, Throwable ex) {
        StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName());
        message.append(" ");
        message.append(request.uri());
        message.append("]");
        if (ex != null) {
            message.append(": ");
            message.append(ex.getMessage());
        }
        return message.toString();
    }

    /**
     * 构建返回的JSON数据格式
     *
     * @param throwable    状态码
     * @param errorMessage 异常信息
     * @return
     */
    public static Map<String, Object> response(Throwable throwable, String errorMessage) {
        log.error("Request error [{}]，throwable : {}", errorMessage, throwable.getStackTrace()[0]);
        Map<String, Object> map = Maps.newHashMap();
        map.put("success", false);
        if (throwable instanceof HttpStatusCodeException) {
            map.put("code", ((HttpStatusCodeException) throwable).getStatusCode().value());
            map.put("message", (throwable).getMessage());
        } else if (throwable instanceof RiggerException) {
            map.put("code", ((RiggerException) throwable).getCode());
            map.put("message", (throwable).getMessage());
        } else {
            map.put("code", ErrorCodeEnum.SYSTEM_EXCEPTION.getCode());
            map.put("message", Objects.isNull((throwable).getMessage()) ? ErrorCodeEnum.SYSTEM_EXCEPTION.getMessage() : (throwable).getMessage());
        }
        return map;
    }
}
