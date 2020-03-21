package com.haoqi.magic.job.config;


import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.haoqi.magic.job.task.MarkedCarTagJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置zookeeper注册中心
 * @author huming
 * @date 2019/5/13 17:19
 */
@Configuration
public class ElasticJobConfig {

    @Autowired
    private ZookeeperRegistryCenter regCenter;
    /**
     * 配置任务监听器
     * @return
     */
    @Bean
    public ElasticJobListener elasticJobListener() {
        return new MyElasticJobListener();
    }
    /**
     * 配置任务详细信息
     * @param jobClass
     * @param cron
     * @param shardingTotalCount
     * @param shardingItemParameters
     * @return
     */
    private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
                                                         final String cron,
                                                         final int shardingTotalCount,
                                                         final String shardingItemParameters) {
        return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(
                JobCoreConfiguration.newBuilder(jobClass.getName(), cron, shardingTotalCount)
                        .shardingItemParameters(shardingItemParameters).build()
                , jobClass.getCanonicalName())
        ).overwrite(true).build();
    }

    /**
     * 定义定时任务--打标签定时任务
     * @param markedCarTagJob 定时任务
     * @param cron 任务执行时间表达式
     * @param shardingTotalCount 中分片
     * @param shardingItemParameters 分片参数
     * @return
     */
    @Bean(initMethod = "init")
    public JobScheduler simpleJobScheduler(final MarkedCarTagJob markedCarTagJob,
                                           @Value("${markedCarTagJob.cron}") final String cron,
                                           @Value("${markedCarTagJob.shardingTotalCount}") final int shardingTotalCount,
                                           @Value("${markedCarTagJob.shardingItemParameters}") final String shardingItemParameters) {
        //定时任务监听，用于处理任务开始前，开始后进行相关操作
        MyElasticJobListener elasticJobListener = new MyElasticJobListener();
        return new SpringJobScheduler(markedCarTagJob, regCenter,
                getLiteJobConfiguration(markedCarTagJob.getClass(), cron, shardingTotalCount, shardingItemParameters),
                elasticJobListener);
    }
}
