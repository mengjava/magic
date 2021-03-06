package com.haoqi.magic.gateway.route;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author twg
 * @since 2019/1/18
 */
@Data
public class GatewayPredicateDefinition {
    @NotBlank(message = "路由规则名不能为空")
    private String name;
    private Map<String, String> args = new LinkedHashMap<>();
}
