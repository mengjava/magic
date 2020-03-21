package com.haoqi.magic.gateway.route;

import com.google.common.collect.Lists;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;

/**
 * @author twg
 * @see org.springframework.cloud.gateway.route.RouteDefinition
 * @since 2019/1/18
 */
@Data
public class GatewayRouteDefinition {
    private String id = UUID.randomUUID().toString();

    @NotEmpty(message = "路由断言规则不能为空")
    private List<GatewayPredicateDefinition> predicates = Lists.newArrayList();

    private List<GatewayFilterDefinition> filters = Lists.newArrayList();

    @NotBlank(message = "路由规则转发的目标uri不能为空")
    private String uri;

    private int order = 0;

}
