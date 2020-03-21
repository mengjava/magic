package com.haoqi.magic.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.mapper.CsCarConfigMapper;
import com.haoqi.magic.business.model.dto.CarConfigDTO;
import com.haoqi.magic.business.model.entity.CsCarConfig;
import com.haoqi.magic.business.service.ICsCarConfigService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 配置信息 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@Service
public class CsCarConfigServiceImpl extends ServiceImpl<CsCarConfigMapper, CsCarConfig> implements ICsCarConfigService {

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Override
    public Optional<CarConfigDTO> getConfigByCarId(Long carId) {
        CsCarConfig carConfig = this.selectOne(new EntityWrapper<CsCarConfig>().eq("cs_car_info_id", carId));
        if (Objects.isNull(carConfig)) {
            return Optional.empty();
        }
        CarConfigDTO configDTO = BeanUtils.beanCopy(carConfig, CarConfigDTO.class);
        setDictValue(configDTO);
        return Optional.of(configDTO);
    }

    private void setDictValue(CarConfigDTO carConfig) {
        //【数据字典】车窗玻璃类型
        carConfig.setWindowGlassCodeName(getDictValue(carConfig.getWindowGlassCode()));
        //【数据字典】天窗类型
        carConfig.setSkyWindowCodeName(getDictValue(carConfig.getSkyWindowCode()));
        //【数据字典】座椅材料
        carConfig.setSeatMaterialCodeName(getDictValue(carConfig.getSeatMaterialCode()));
        //【数据字典】座椅调节方式
        carConfig.setSeatAdjustTypeCodeName(getDictValue(carConfig.getSeatAdjustTypeCode()));
        //【数据字典】座椅功能
        carConfig.setSeatFunctionCodeName(getDictValue(carConfig.getSeatFunctionCode()));
        //【数据字典】倒车雷达
        carConfig.setPdcCodeName(getDictValue(carConfig.getPdcCode()));
        //【数据字典】倒车影像
        carConfig.setRvcCodeName(getDictValue(carConfig.getRvcCode()));
        //【数据字典】轮毂
        carConfig.setHubCodeName(getDictValue(carConfig.getHubCode()));
    }

    /**
     * 通过code获取字典值
     *
     * @param code 字典码
     * @return
     */
    private String getDictValue(String code) {
        return StrUtil.isBlank(code) ? "" : getValue(code);
    }

    private String getValue(String code) {
        Result<String> result = systemServiceClient.getDictValueDesc(code);
        if (result.isSuccess()) {
            return result.getData();
        }
        return "";
    }


}
