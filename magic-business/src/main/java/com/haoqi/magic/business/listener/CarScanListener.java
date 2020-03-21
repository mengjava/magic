package com.haoqi.magic.business.listener;

import com.haoqi.magic.business.async.AsyncTasker;
import com.haoqi.magic.business.async.Tasker;
import com.haoqi.magic.business.listener.event.CarScanEvent;
import com.haoqi.magic.business.mapper.CsCarInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 功能描述:
 * 车辆信息浏览监听器
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/29 17:30
 * @Description:
 */
@Component
public class CarScanListener implements ApplicationListener<CarScanEvent> {

    @Autowired
    private AsyncTasker asyncTasker;

    @Autowired
    private CsCarInfoMapper csCarInfoMapper;

    @Override
    public void onApplicationEvent(CarScanEvent carScanEvent) {
        Long carId = carScanEvent.getCarId();
        if (Objects.nonNull(carId)) {
            asyncTasker.execut(new CarScanListenerTasker(carId));
        }
    }

    class CarScanListenerTasker implements Tasker {
        private Long carId;

        public CarScanListenerTasker(Long carId) {
            this.carId = carId;
        }

        @Override
        public Object run() {
            csCarInfoMapper.updateScanNum(carId);
            return null;
        }
    }
}
