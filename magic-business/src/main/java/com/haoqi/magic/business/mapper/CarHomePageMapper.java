package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.HomePageDTO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
public interface CarHomePageMapper {
    /***
     * 首页参数查询基类
     * @param query
     * @param condition
     * @return
     */
    List<HomePageDTO> selectIndexPageParam(Query query, Map condition);

    /***
     * 随机挑选4条诚信联盟数据 (优化后sql)
     * @param query
     * @param condition
     * @return
     */
    List<HomePageDTO> selectCreditUnionPage(Query query, Map condition);
}
