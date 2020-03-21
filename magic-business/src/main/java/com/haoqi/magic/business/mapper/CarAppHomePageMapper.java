package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.AppCarPageDTO;
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
public interface CarAppHomePageMapper {
    /***
     * 首页参数查询基类
     * @param query
     * @param condition
     * @return
     */
    List<HomePageDTO> selectIndexPageParam(Query query, Map condition);

    /**
     * APP卖车列表
     * @param query
     * @param condition
     * @return
     */
    List<AppCarPageDTO> selectByPage(Query query, Map condition);
}
