package com.haoqi.magic.job.config;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zookeeper配置
 * @author huming
 * @date 2019/5/13 17:19
 */
@Configuration
public class ElasticRegCenterConfig {
    /**
     * 配置zookeeper
     * @param serverList zookeeper服务地址，多个用“,”逗号分隔
     * @param namespace 设置命名空间
     * @return
     */
    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter regCenter(
            @Value("${regCenter.serverList}") final String serverList,
            @Value("${regCenter.namespace}") final String namespace) {
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(serverList, namespace));
    }
}
