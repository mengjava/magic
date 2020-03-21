package com.haoqi.magic.business.listener;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haoqi.magic.business.async.AsyncTasker;
import com.haoqi.magic.business.async.Tasker;
import com.haoqi.magic.business.enums.ProcessStatusEnum;
import com.haoqi.magic.business.listener.event.CarCustomEvent;
import com.haoqi.magic.business.mapper.CsCustomServiceMapper;
import com.haoqi.magic.business.model.entity.CsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 功能描述:
 * 意向车源(登录点击车商信息插入意向车源)
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/29 17:30
 * @Description:
 */
@Component
public class CarCustomListener implements ApplicationListener<CarCustomEvent> {

    @Autowired
    private AsyncTasker asyncTasker;

    @Autowired
    private CsCustomServiceMapper csCustomServiceMapper;


    @Override
    public void onApplicationEvent(CarCustomEvent carCustomEvent) {
        //车辆id
        Long carId = carCustomEvent.getCarId();
        //车商id
        Long csCarDealerId = carCustomEvent.getCsCarDealerId();
        if (Objects.nonNull(carId) && Objects.nonNull(csCarDealerId)) {
            asyncTasker.execut(new CarCustomListenerTasker(carId, csCarDealerId));
        }

    }

    class CarCustomListenerTasker implements Tasker {

        private Long carId;
        private Long csCarDealerId;

        public CarCustomListenerTasker(Long carId, Long csCarDealerId) {
            this.carId = carId;
            this.csCarDealerId = csCarDealerId;
        }

        @Override
        public Object run() {
            CsCustomService custom = new CsCustomService();
            custom.setCsCarInfoId(carId);
            custom.setCsBuyDealerId(csCarDealerId);
            List<CsCustomService> csCustomServices = csCustomServiceMapper.selectList(new EntityWrapper<>(custom).orderBy("gmt_create", false));
            if (Objects.isNull(csCustomServices) || csCustomServices.size() <= 0) {
                insertCsCustomService(custom);
            } else if (Objects.nonNull(csCustomServices) && csCustomServices.size() > 0) {
                CsCustomService customService = csCustomServices.get(0);
                if (ProcessStatusEnum.UNPROCESSED.getKey().equals(customService.getProcessStatus())) {
                    CsCustomService update = new CsCustomService();
                    update.setId(customService.getId());
                    update.setGmtModified(DateUtil.date());
                    update.setModifier(0L);
                    update.setIntentionTime(DateUtil.date());
                    csCustomServiceMapper.updateById(update);
                } else {
                    insertCsCustomService(custom);
                }
            }
            return null;
        }
    }

    private void insertCsCustomService(CsCustomService custom) {
        custom.setIntentionTime(DateUtil.date());
        custom.setProcessStatus(ProcessStatusEnum.UNPROCESSED.getKey());
        custom.setCreator(0L);
        custom.setModifier(0L);
        custom.setGmtCreate(DateUtil.date());
        custom.setGmtModified(DateUtil.date());
        csCustomServiceMapper.insert(custom);
    }
}
