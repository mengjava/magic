package com.haoqi.magic.job.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.haoqi.magic.job.service.ICsMarkedCarTagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 打标签任务
 *
 * @author huming
 * @date 2019/5/13 17:36
 */
@Component
@Slf4j
public class MarkedCarTagJob implements SimpleJob {

    //打标签
    @Autowired
    private ICsMarkedCarTagService csMarkedCarTagService;

    @Override
    public void execute(ShardingContext shardingContext) {
        /**
         * 可以根据分片项执行业务
         * (当主机数量不同的时候elastic-job会自动选举每个服务器执行的分片项)
         */
        log.info("begin to  execute  MarkedCarTagJob ShardingTotalCount:{},ShardingItem:{}"
                , shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
        switch (shardingContext.getShardingItem()) {
            case 0:
                csMarkedCarTagService.markCarTag(shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
                break;
            case 1:
                csMarkedCarTagService.markCarTag(shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
                break;
            case 2:
                csMarkedCarTagService.markCarTag(shardingContext.getShardingTotalCount(), shardingContext.getShardingItem());
                break;
            default:
                break;
        }

    }
}
