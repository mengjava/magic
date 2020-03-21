package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CarProcedureDTO;
import com.haoqi.magic.business.model.entity.CsCarProcedure;

import java.util.Optional;

/**
 * <p>
 * 手续信息 服务类
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
public interface ICsCarProcedureService extends IService<CsCarProcedure> {


    /**
     * 通过车辆id，获取车辆手续信息
     *
     * @param carId
     * @return
     */
    Optional<CarProcedureDTO> getProcedureByCarId(Long carId);

}
