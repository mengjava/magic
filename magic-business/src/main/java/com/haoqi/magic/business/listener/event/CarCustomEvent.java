package com.haoqi.magic.business.listener.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 功能描述:
 * 意向车源
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/6/3 10:48
 * @Description:
 */
@Data
public class CarCustomEvent extends ApplicationEvent {

    //车辆id
    private Long carId;
    //车商id
    private Long csCarDealerId;

    public CarCustomEvent(Object source, Long carId, Long csCarDealerId) {
        super(source);
        this.carId = carId;
        this.csCarDealerId = csCarDealerId;
    }
}
