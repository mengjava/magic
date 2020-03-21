package com.haoqi.magic.business.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.mapper.CsCarProcedureMapper;
import com.haoqi.magic.business.model.dto.CarProcedureDTO;
import com.haoqi.magic.business.model.entity.CsCarProcedure;
import com.haoqi.magic.business.service.ICsCarProcedureService;
import com.haoqi.rigger.common.util.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 手续信息 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@Service
public class CsCarProcedureServiceImpl extends ServiceImpl<CsCarProcedureMapper, CsCarProcedure> implements ICsCarProcedureService {

    @Override
    public Optional<CarProcedureDTO> getProcedureByCarId(Long carId) {
        CsCarProcedure carProcedure = this.selectOne(new EntityWrapper<CsCarProcedure>().eq("cs_car_info_id", carId));
        if (Objects.isNull(carProcedure)){
            return Optional.empty();
        }
        return Optional.of(BeanUtils.beanCopy(carProcedure, CarProcedureDTO.class));
    }
}
