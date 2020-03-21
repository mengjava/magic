package com.haoqi.magic.business.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.common.utils.KeyValueDescUtil;
import com.haoqi.magic.business.enums.CarPublishStatusEnum;
import com.haoqi.magic.business.enums.CarTransferRecordStatusEnum;
import com.haoqi.magic.business.mapper.CsCarSourceMapper;
import com.haoqi.magic.business.model.dto.CarDTO;
import com.haoqi.magic.business.model.dto.CsCarSourceDTO;
import com.haoqi.magic.business.model.dto.SysDictDTO;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.haoqi.magic.business.model.vo.CsCarSourceVO;
import com.haoqi.magic.business.service.ICsCarInfoService;
import com.haoqi.magic.business.service.ICsCarSourceService;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * ClassName:com.haoqi.magic.business.service.impl <br/>
 * Function: 车商--车源管理<br/>
 * Date:     2019/5/8 10:24 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Service
public class CsCarSourceServiceImpl implements ICsCarSourceService {

    @Autowired
    private CsCarSourceMapper csCarSourceMapper;


    @Autowired
    private ICsCarInfoService csCarInfoService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Override
    public Page findPage(Map<String, Object> params) {
        Query query = new Query(params);
        List<CsCarSourceDTO> list = csCarSourceMapper.findPage(query, query.getCondition());
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> result = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        Result<List<SysDictDTO>> resultBox = systemServiceClient.getDictByClass(DictClassEnum.TRANSMISSION_100000.getClassCode());
        list.forEach(e -> {
            e.setColorCodeName(KeyValueDescUtil.handleValueDesc(e.getColorCode(), colorCode.getData()));
            e.setEmissionStandardCodeName(KeyValueDescUtil.handleValueDesc(e.getEmissionStandardCode(), result.getData()));
            e.setGearBoxCodeName(KeyValueDescUtil.handleValueDesc(e.getGearBoxCode(), resultBox.getData()));
            e.setPublishStatusName(CarPublishStatusEnum.getValue(e.getPublishStatus()));
        });
        return query.setRecords(list);
    }


    @Override
    public Boolean pullOffCar(CsCarSourceVO vo) {
        //1、参数校验
        Assert.notNull(vo.getId(), "下架车源：车辆ID不能为空");
        Assert.notNull(vo.getCsCarDealerId(), "下架车源：经销商ID不能为空");
        Optional<CarDTO> optional = csCarInfoService.getCarById(vo.getId());
        if (!vo.getCsCarDealerId().equals(optional.get().getCsCarDealerId())) {
            throw new RiggerException("下架车源：请勿操作其他车商数据");
        }
        if (!CarPublishStatusEnum.PURT_AWAY.getKey().equals(optional.get().getPublishStatus())) {
            throw new RiggerException("下架车源：车辆状态已变更");
        }
        //2、下架车源
        CsCarInfo entity = new CsCarInfo();
        entity.setPublishStatus(CarPublishStatusEnum.SOLD_OUT.getKey());
        entity.setPullOffTime(new Date());
        csCarInfoService.update(entity, new EntityWrapper<CsCarInfo>()
                                            .eq("id", vo.getId())
                                            .eq("publish_status", CarPublishStatusEnum.PURT_AWAY.getKey()));
        return Boolean.TRUE;
    }

    @Override
    public Boolean transferCar(CsCarSourceVO vo) {
        Assert.notNull(vo.getId(), "调拨车源：车辆ID不能为空");
        Assert.notNull(vo.getCsCarDealerId(), "下架车源：经销商ID不能为空");
        Optional<CarDTO> optional = csCarInfoService.getCarById(vo.getId());
        if (!vo.getCsCarDealerId().equals(optional.get().getCsCarDealerId())) {
            throw new RiggerException("下架车源：请勿操作其他车商数据");
        }
        if (!CarPublishStatusEnum.PURT_AWAY.getKey().equals(optional.get().getPublishStatus())) {
            throw new RiggerException("下架车源：车辆状态已变更");
        }
        //2、调拨状态
        CsCarInfo entity = new CsCarInfo();
        entity.setPublishStatus(CarPublishStatusEnum.ALLOT.getKey());
        entity.setTransferStatus(CarTransferRecordStatusEnum.APPLY_AGREE.getKey());
        entity.setTransferHandleTime(DateUtil.date());
        csCarInfoService.update(entity, new EntityWrapper<CsCarInfo>()
                .eq("id", vo.getId())
                .eq("publish_status", CarPublishStatusEnum.PURT_AWAY.getKey()));
        return Boolean.TRUE;
    }
}
