package com.haoqi.magic.business.listener;

import com.haoqi.magic.business.model.dto.OrderPushMessageDTO;
import com.haoqi.magic.business.service.ICsPushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author twg
 * @since 2020/1/14
 */
@Slf4j
@Component
public class MessagePushListener {

    @Autowired
    private ICsPushMessageService pushMessageService;

    @Async
    @EventListener(value = OrderPushMessageDTO.class)
    public void onApplicationEvent(OrderPushMessageDTO message) {
        log.info(">>>>>>>>>>> MessagePushEvent Thread name {} Run >>>>>>>>>>>>", Thread.currentThread().getName());
        pushMessageService.pushOrderToApp(message);
    }
}
