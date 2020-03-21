package com.haoqi.magic.job.service;

import com.haoqi.magic.job.model.dto.CarDTO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * ClassName:com.haoqi.magic.job.service <br/>
 * Function: 车辆信息服务<br/>
 * Date:     2019/5/13 11:26 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public interface ICsCarService {

    /**
     * 根据SQL条件获取车辆信息
     * @param query
     * @return
     * @author huming
     * @date 2019/5/13 11:55
     */
    List<CarDTO> getCarWithSqlStr(Query query);

    /**
     * 获取全部车源数量
     * @return
     * @author huming
     * @date 2019/5/14 9:49
     */
    Integer getCarTotalCount();
}
