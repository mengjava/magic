package com.haoqi.magic.gateway.route;

import com.google.common.collect.Lists;
import com.haoqi.rigger.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @author twg
 * @see org.springframework.cloud.gateway.actuate.GatewayControllerEndpoint
 * @since 2019/1/18
 */
@Service
public class GatewayDynamicRouteService implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;
    /**
     * 默认保存在内存中，实现RouteDefinitionRepository，可以保存在redis、或者数据库中
     * @see GatewayAutoConfiguration#inMemoryRouteDefinitionRepository()
     */
    @Autowired
    private RouteDefinitionRepository routeDefinitionWriter;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }

    /**
     * 动态添加路由
     *
     * @param gatewayRouteDefinition
     * @param bindingResult
     * @return
     */
    public Result<String> add(GatewayRouteDefinition gatewayRouteDefinition) {
        RouteDefinition routeDefinition = buildingRouteDefinition(gatewayRouteDefinition);
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return Result.buildSuccessResult("路由添加成功");
    }


    /**
     * 通过路由ID，删除路由
     *
     * @param id
     * @return
     */
    public Result<String> delete(String id) {
        routeDefinitionWriter.delete(Mono.just(id));
        return Result.buildSuccessResult("路由删除成功");
    }

    /**
     * 更新路由
     *
     * @param gatewayRouteDefinition
     * @return
     */
    public Result<String> update(GatewayRouteDefinition gatewayRouteDefinition) {
        RouteDefinition routeDefinition = buildingRouteDefinition(gatewayRouteDefinition);
        routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return Result.buildSuccessResult("路由更新成功");
    }


    private RouteDefinition buildingRouteDefinition(GatewayRouteDefinition gatewayRouteDefinition) {
        RouteDefinition routeDefinition = new RouteDefinition();
        routeDefinition.setId(gatewayRouteDefinition.getId());
        routeDefinition.setOrder(gatewayRouteDefinition.getOrder());
        routeDefinition.setUri(URI.create(gatewayRouteDefinition.getUri()));
        List<PredicateDefinition> predicateDefinitionList = Lists.newArrayList();
        List<GatewayPredicateDefinition> predicateDefinitions = gatewayRouteDefinition.getPredicates();
        for (GatewayPredicateDefinition gatewayPredicateDefinition : predicateDefinitions) {
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setName(gatewayPredicateDefinition.getName());
            predicateDefinition.setArgs(gatewayPredicateDefinition.getArgs());
            predicateDefinitionList.add(predicateDefinition);
        }
        routeDefinition.setPredicates(predicateDefinitionList);

        List<FilterDefinition> filterDefinitions = Lists.newArrayList();
        List<GatewayFilterDefinition> gatewayFilterDefinitions = gatewayRouteDefinition.getFilters();
        for (GatewayFilterDefinition gatewayFilterDefinition : gatewayFilterDefinitions) {
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setName(gatewayFilterDefinition.getName());
            filterDefinition.setArgs(gatewayFilterDefinition.getArgs());
            filterDefinitions.add(filterDefinition);
        }
        routeDefinition.setFilters(filterDefinitions);
        return routeDefinition;

    }


}
