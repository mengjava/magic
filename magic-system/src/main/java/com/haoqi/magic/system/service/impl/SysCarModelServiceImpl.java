package com.haoqi.magic.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.haoqi.magic.common.enums.EmissionStandardEnum;
import com.haoqi.magic.system.mapper.SysCarModelMapper;
import com.haoqi.magic.system.model.entity.SysCarModel;
import com.haoqi.magic.system.model.vo.CarModelVO;
import com.haoqi.magic.system.service.ISysCarModelService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 车型表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Service
public class SysCarModelServiceImpl extends ServiceImpl<SysCarModelMapper, SysCarModel> implements ISysCarModelService {

    @Autowired
    private SysCarModelMapper sysCarModelMapper;

    @Override
    public Page selectCarModelPage(Map<String, Object> params) {
        Query query = new Query(params);
        query.setOrderByField("model_year").setAsc(true);
        query.setOrderByField("model_id").setAsc(true);
        List<SysCarModel> list = sysCarModelMapper.selectCarModelPage(query, query.getCondition());
        setCarList(list);
        return query.setRecords(list);
    }

    @Override
    @Cached(name = "carModel:modelByModelId", key = "#modelId", expire = 30000)
    public SysCarModel selectCarModelByModelId(Long modelId) {
        Assert.notNull(modelId, "modelId not be null");
        Map<String, Object> map = Maps.newHashMap();
        map.put("model_id", modelId);
        map.put("is_deleted", 0);
        SysCarModel sysCarModel = null;
        List<SysCarModel> sysCarModels = sysCarModelMapper.selectByMap(map);
        if (sysCarModels.size() > 0) {
            sysCarModel = sysCarModels.get(0);
            if (Objects.nonNull(sysCarModel.getDischargeStandard())) {
                sysCarModel.setEmissionStandardCode(EmissionStandardEnum.getTypeCode(sysCarModel.getDischargeStandard()));
            }
        }
        return sysCarModel;
    }

    @Override
    @Cached(name = "carModelList:modelListBySeriesId", key = "#seriesId", expire = 30000)
    public List<SysCarModel> selectCarModelListBySeriesId(Integer seriesId) {
        List<SysCarModel> list = sysCarModelMapper.selectCarModelListBySeriesId(seriesId);
        setCarList(list);
        return list;
    }

    private void setCarList(List<SysCarModel> list) {
        list.forEach(car -> {
            if (Objects.nonNull(car.getDischargeStandard())) {
                car.setEmissionStandardCode(EmissionStandardEnum.getTypeCode(car.getDischargeStandard()));
            }
        });
    }

    @Override
    public Boolean setCarModelValid(Long id, Integer isDeleted) {
        SysCarModel sysCarModel = sysCarModelMapper.selectById(id);
        if (Objects.isNull(sysCarModel)) {
            return Boolean.FALSE;
        }
        sysCarModel.setModifier(1L);
        sysCarModel.setGmtModified(DateUtil.date());
        sysCarModel.setIsDeleted(isDeleted);
        sysCarModelMapper.updateById(sysCarModel);
        return Boolean.TRUE;
    }

    //不能校验车型唯一性
    @Override
    public Boolean addCarModel(CarModelVO vo) {
        SysCarModel carModel = new SysCarModel();
        BeanUtil.copyProperties(vo, carModel);
        carModel.setCompId(1L);
        carModel.setCompName("浩韵控股集团");
        carModel.setCreator(1L);
        carModel.setModifier(1L);
        carModel.setIsDeleted(CommonConstant.STATUS_NORMAL);
        carModel.setGmtModified(DateUtil.date());
        carModel.setGmtCreate(DateUtil.date());
        sysCarModelMapper.insert(carModel);
        return Boolean.TRUE;
    }

    //不能校验车型唯一性
    @Override
    public Boolean updateCarModel(CarModelVO vo) {
        Assert.notNull(vo.getId(), "id carmodel not be null");
        SysCarModel sysCarModel = sysCarModelMapper.selectById(vo.getId());
        if (sysCarModel == null) {
            return Boolean.FALSE;
        }
        sysCarModel.setGmtModified(DateUtil.date());
        sysCarModel.setModifier(1L);
        sysCarModelMapper.updateById(sysCarModel);
        return Boolean.TRUE;
    }
}
