package com.haoqi.magic.gateway.error;

import com.haoqi.rigger.core.error.ErrorCodeEnum;
import org.apache.commons.logging.Log;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpLogging;
import org.springframework.http.HttpStatus;
import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static org.springframework.web.reactive.function.server.RequestPredicates.all;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * @author twg
 * @since 2019/1/16
 */
public class DefaultErrorHandler extends AbstractErrorWebExceptionHandler {


    private static final Log logger = HttpLogging
            .forLogName(DefaultErrorWebExceptionHandler.class);


    private final ErrorProperties errorProperties;

    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resourceProperties the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     */
    public DefaultErrorHandler(ErrorAttributes errorAttributes,
                               ResourceProperties resourceProperties, ErrorProperties errorProperties,
                               ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.errorProperties = errorProperties;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(
            ErrorAttributes errorAttributes) {
        return route(acceptsTextHtml(), this::renderErrorView).andRoute(all(),
                this::renderErrorResponse);
    }

    /**
     * Render the error information as an HTML view.
     *
     * @param request the current request
     * @return a {@code Publisher} of the HTTP response
     */
    protected Mono<ServerResponse> renderErrorView(ServerRequest request) {
        boolean includeStackTrace = isIncludeStackTrace(request, MediaType.TEXT_HTML);
        Map<String, Object> error = getErrorAttributes(request, includeStackTrace);
        ErrorCodeEnum errorStatus = getHttpStatus(error);
        ServerResponse.BodyBuilder responseBody = ServerResponse.status(errorStatus.getCode())
                .contentType(MediaType.TEXT_HTML);
        return Flux
                .just("error/" + errorStatus.getCode(), "error/error")
                .flatMap((viewName) -> renderErrorView(viewName, responseBody, error))
                .switchIfEmpty(this.errorProperties.getWhitelabel().isEnabled()
                        ? renderDefaultErrorView(responseBody, error)
                        : Mono.error(getError(request)))
                .next().doOnNext((response) -> logError(request));
    }

    /**
     * Render the error information as a JSON payload.
     *
     * @param request the current request
     * @return a {@code Publisher} of the HTTP response
     */
    protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        boolean includeStackTrace = isIncludeStackTrace(request, MediaType.ALL);
        Map<String, Object> error = getErrorAttributes(request, includeStackTrace);
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject(error))
                .doOnNext((resp) -> logError(request));
    }

    /**
     * Determine if the stacktrace attribute should be included.
     *
     * @param request  the source request
     * @param produces the media type produced (or {@code MediaType.ALL})
     * @return if the stacktrace attribute should be included
     */
    protected boolean isIncludeStackTrace(ServerRequest request, MediaType produces) {
        ErrorProperties.IncludeStacktrace include = this.errorProperties
                .getIncludeStacktrace();
        if (include == ErrorProperties.IncludeStacktrace.ALWAYS) {
            return true;
        }
        if (include == ErrorProperties.IncludeStacktrace.ON_TRACE_PARAM) {
            return isTraceEnabled(request);
        }
        return false;
    }

    /**
     * Get the HTTP error status information from the error map.
     *
     * @param errorAttributes the current error information
     * @return the error HTTP status
     */
    protected ErrorCodeEnum getHttpStatus(Map<String, Object> errorAttributes) {
        int statusCode = (int) errorAttributes.get("status");
        return ErrorCodeEnum.valueOf(statusCode);
    }

    /**
     * Predicate that checks whether the current request explicitly support
     * {@code "text/html"} media type.
     * <p>
     * The "match-all" media type is not considered here.
     *
     * @return the request predicate
     */
    protected RequestPredicate acceptsTextHtml() {
        return (serverRequest) -> {
            try {
                List<MediaType> acceptedMediaTypes = serverRequest.headers().accept();
                acceptedMediaTypes.remove(MediaType.ALL);
                MediaType.sortBySpecificityAndQuality(acceptedMediaTypes);
                return acceptedMediaTypes.stream()
                        .anyMatch(MediaType.TEXT_HTML::isCompatibleWith);
            } catch (InvalidMediaTypeException ex) {
                return false;
            }
        };
    }

    /**
     * Log the original exception if handling it results in a Server Error or a Bad
     * Request (Client Error with 400 status code) one.
     *
     * @param request     the source request
     */
    protected void logError(ServerRequest request) {
        Throwable ex = getError(request);
        if (logger.isDebugEnabled()) {
            logger.debug(request.exchange().getLogPrefix() + formatError(ex, request));
        }
    }

    private String formatError(Throwable ex, ServerRequest request) {
        String reason = ex.getClass().getSimpleName() + ": " + ex.getMessage();
        return "Resolved [" + reason + "] for HTTP " + request.methodName() + " "
                + request.path();
    }
}
