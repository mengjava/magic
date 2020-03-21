package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CarAuditDTO;
import com.haoqi.magic.business.model.entity.CsCarAudit;
import com.haoqi.rigger.mybatis.Query;

import java.util.Optional;

/**
 * <p>
 * 车辆信息审核表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
public interface ICsCarAuditService extends IService<CsCarAudit> {

    /**
     * 添加车辆审核记录
     *
     * @param carAudit
     * @return
     */
    Boolean add(CarAuditDTO carAudit);

    /***
     * 添加上下架 审核记录
     * @param carAudit
     * @return
     */
    Boolean addAuditPullUp(CarAuditDTO carAudit);

    /**
     * 获取最近车辆的审核信息
     *
     * @param carId 车辆id
     * @return
     */
    Optional<CarAuditDTO> getLastCarAuditByCarId(Long carId);

    /***
     * 分页查询车辆审核记录
     * @param query
     * @return
     */
    Page getCarAuditListByCarId(Query query);
}
