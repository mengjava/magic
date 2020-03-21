package com.haoqi.magic.gateway.route;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.haoqi.rigger.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author twg
 * @since 2019/4/17
 */
@Slf4j
@Component
public class GatewayDynamicRouteServiceByNacos {
    @Value("${spring.cloud.nacos.config.server-addr:127.0.0.1:8848}")
    private String serveraddr;
    @Value("${spring.cloud.nacos.config.group:DEFAULT_GROUP}")
    private String group;
    @Value("${spring.cloud.nacos.config.file-extension:properties}")
    private String fileExtension;
    @Value("${spring.application.route}")
    private String dataId;
    @Value("${spring.profiles.active:}")
    private String active;

    @Autowired
    private GatewayDynamicRouteService gatewayDynamicRouteService;

    @PostConstruct
    public void init() {
        try {
//            dataId = StrUtil.isBlank(active) ? dataId : dataId + StrUtil.DASHED + active + StrUtil.DOT + fileExtension;
            dataId = dataId + StrUtil.DOT + fileExtension;
            ConfigService configService = NacosFactory.createConfigService(serveraddr);
            String content = configService.getConfig(dataId, group, 5000);
            if (log.isDebugEnabled()) {
                log.debug("Dynamic Route Config：{} env：{}", content, active);
            }
            configService.addListener(dataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    List<GatewayRouteDefinition> routeDefinitions = JSON.parseArray(configInfo, GatewayRouteDefinition.class);
                    routeDefinitions.forEach(routeDefinition -> {
                        Result<String> result = gatewayDynamicRouteService.update(routeDefinition);
                        if (log.isDebugEnabled()) {
                            log.debug("Dynamic Route Url {} is Updated {}", routeDefinition.getUri(), result.getMessage());
                        }
                    });

                }
            });
        } catch (NacosException e) {
            log.error("GatewayDynamicRouteServiceByNacos error.", e);
        }

    }
}
