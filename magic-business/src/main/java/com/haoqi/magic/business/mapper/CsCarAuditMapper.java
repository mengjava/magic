package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CarAuditDTO;
import com.haoqi.magic.business.model.entity.CsCarAudit;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息审核表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
public interface CsCarAuditMapper extends BaseMapper<CsCarAudit> {

    /**
     * 获取最近车辆的审核信息
     *
     * @param
     * @return
     */
    CarAuditDTO getLastCarAuditByCarId(Map map);

    List<CarAuditDTO> findCarAuditListByCarId(Query query, Map condition);
}
