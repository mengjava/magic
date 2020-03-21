package com.haoqi.magic.business.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.enums.AuditStatusEnum;
import com.haoqi.magic.business.enums.CarPublishStatusEnum;
import com.haoqi.magic.business.mapper.CsCarAuditMapper;
import com.haoqi.magic.business.model.dto.CarAuditDTO;
import com.haoqi.magic.business.model.dto.CarDTO;
import com.haoqi.magic.business.model.dto.CarOpenApiDTO;
import com.haoqi.magic.business.model.entity.CsCarAudit;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.haoqi.magic.business.service.ICarOpenApiService;
import com.haoqi.magic.business.service.ICsCarAuditService;
import com.haoqi.magic.business.service.ICsCarInfoService;
import com.haoqi.magic.business.service.ICsHitTagRelativeService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 车辆信息审核表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
@Slf4j
@Service
public class CsCarAuditServiceImpl extends ServiceImpl<CsCarAuditMapper, CsCarAudit> implements ICsCarAuditService {


    @Autowired
    private CsCarAuditMapper carAuditMapper;

    @Autowired
    private ICsCarInfoService carInfoService;

    @Autowired
    private ICsHitTagRelativeService hitTagRelativeService;

    @Autowired
    private ICarOpenApiService carOpenApiService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean add(CarAuditDTO carAuditDTO) {
        Assert.notNull(carAuditDTO.getAuditStatus(), "车辆审核状态不能为空");
        Assert.notNull(carAuditDTO.getAuditUserId(), "车辆审核用户不能为空");
        Assert.notNull(carAuditDTO.getAuditLoginName(), "车辆审核用户帐号不能为空");
        Assert.notNull(carAuditDTO.getCsCarInfoId(), "审核对应的车辆id不能为空");
        boolean pass = AuditStatusEnum.PASS.getKey().equals(carAuditDTO.getAuditStatus());
        carAuditDTO.setAuditTime(DateUtil.date());
        CsCarInfo carInfo = new CsCarInfo();
        carInfo.setId(carAuditDTO.getCsCarInfoId());
        carInfo.setAuditTime(carAuditDTO.getAuditTime());
        carInfo.setPublishStatus(AuditStatusEnum.REJECTION.getKey());
        if (pass) {
            carInfo.setPublishStatus(CarPublishStatusEnum.PURT_AWAY.getKey());
            carInfo.setTradeFlag(Constants.NO);
            CarOpenApiDTO dto = new CarOpenApiDTO();
            Optional<CarDTO> carById = carInfoService.getCarById(carAuditDTO.getCsCarInfoId());
            dto.setVin(carById.get().getVin());
            Assert.notNull(carById.get().getVin(), "车架号不能为空");
            //调用维保接口
            String reportUrl = null;
            try {
                reportUrl = carOpenApiService.getMaintenanceReport(carAuditDTO.getAuditUserId(), dto);
                log.error(reportUrl);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            if (StringUtils.isNotEmpty(reportUrl)) {
                carInfo.setMaintenanceUrl(reportUrl);
            }
            String insuranceReportUrl = null;
            try {
                insuranceReportUrl = carOpenApiService.getInsuranceReport(carAuditDTO.getAuditUserId(), dto);
                log.error(reportUrl);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            if (StringUtils.isNotEmpty(insuranceReportUrl)) {
                carInfo.setInsuranceUrl(insuranceReportUrl);
            }

            hitTagRelativeService.htiTagByCarId(carAuditDTO.getCsCarInfoId());
        }
        carInfoService.updateById(carInfo);
        this.insert(BeanUtils.beanCopy(carAuditDTO, CsCarAudit.class));
        return Boolean.TRUE;
    }

    @Override
    public Optional<CarAuditDTO> getLastCarAuditByCarId(Long carId) {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("carId", carId);
        map.put("auditStatusList", Arrays.asList(-1, 1));
        return Optional.ofNullable(carAuditMapper.getLastCarAuditByCarId(map));
    }

    @Override
    public Page getCarAuditListByCarId(Query query) {
        query.setOrderByField("audit_time").setAsc(false);
        List<CarAuditDTO> list = carAuditMapper.findCarAuditListByCarId(query, query.getCondition());
        list.forEach(a -> {
            if (StringUtils.isEmpty(a.getAuditUserName())) {
                a.setAuditUserName(a.getAuditLoginName());
            }
        });
        return query.setRecords(list);
    }

    @Override
    public Boolean addAuditPullUp(CarAuditDTO carAuditDTO) {
        Assert.notNull(carAuditDTO.getAuditStatus(), "车辆审核状态不能为空");
        Assert.notNull(carAuditDTO.getAuditUserId(), "车辆审核用户不能为空");
        Assert.notNull(carAuditDTO.getAuditLoginName(), "车辆审核用户帐号不能为空");
        Assert.notNull(carAuditDTO.getCsCarInfoId(), "审核对应的车辆id不能为空");
        carAuditDTO.setAuditTime(DateUtil.date());
        this.insertOrUpdate(BeanUtils.beanCopy(carAuditDTO, CsCarAudit.class));
        return Boolean.TRUE;
    }
}
