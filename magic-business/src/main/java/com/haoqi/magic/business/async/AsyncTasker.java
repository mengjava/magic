package com.haoqi.magic.business.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 异步处理器
 **/
@Component
public class AsyncTasker {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 无返回参数
     *
     * @throws InterruptedException
     */
    @Async("taskExecutor")
    public void execut(Tasker tasker) {
        long start = System.currentTimeMillis();
        tasker.run();
        long end = System.currentTimeMillis();
        logger.info("execut finished, time elapsed: {} ms.", end - start);
    }

    /**
     * 有返回参数
     *
     * @return
     * @throws InterruptedException
     */
    @Async("taskExecutor")
    public Future<Object> executFuture(Tasker tasker) {
        long start = System.currentTimeMillis();
        Object result = tasker.run();
        long end = System.currentTimeMillis();
        logger.info("executFuture finished, time elapsed: {} ms.", end - start);
        return new AsyncResult<>(result);
    }
}
