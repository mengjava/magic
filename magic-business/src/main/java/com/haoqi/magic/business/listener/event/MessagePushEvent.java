package com.haoqi.magic.business.listener.event;

import lombok.Data;

/**
 * @author twg
 * @since 2020/1/14
 */
@Data
public class MessagePushEvent<T> {

    private  T data;
    /**
     * Create a new ApplicationEvent.
     *
     */
    public MessagePushEvent(T data) {
        this.data = data;
    }
}
