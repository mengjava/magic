package com.haoqi.magic.job.mapper;

import com.haoqi.magic.job.model.dto.CarDTO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆服务 Mapper 接口
 * </p>
 *@author huming
 * @date 2019/5/13 14:05
 */
public interface CsCarMapper {

    /**
     * 根据SQL条件获取车辆信息
     * @return
     * @author huming
     * @date 2019/5/13 14:09
     */
    List<CarDTO> getCarWithSqlStr(Query query, Map condition);

    /**
     * 获取全部车源数量
     * @return
     * @author huming
     * @date 2019/5/14 9:51
     */
    Integer getCarTotalCount();
}
