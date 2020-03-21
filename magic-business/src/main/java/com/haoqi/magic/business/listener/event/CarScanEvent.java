package com.haoqi.magic.business.listener.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * Created by yanhao on 2019/5/29.
 */
@Data
public class CarScanEvent extends ApplicationEvent {

    private Long carId;

    public CarScanEvent(Object source, Long carId) {
        super(source);
        this.carId = carId;
    }
}
