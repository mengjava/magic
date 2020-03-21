package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CarConfigDTO;
import com.haoqi.magic.business.model.entity.CsCarConfig;

import java.util.Optional;

/**
 * <p>
 * 配置信息 服务类
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
public interface ICsCarConfigService extends IService<CsCarConfig> {

    /**
     * 通过车辆id，获取车辆配置信息
     *
     * @param carId
     * @return
     */
    Optional<CarConfigDTO> getConfigByCarId(Long carId);

}
