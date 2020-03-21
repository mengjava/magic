package com.haoqi.magic.system.mapper;

import com.haoqi.magic.system.model.entity.SysCarSeries;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车系表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface SysCarSeriesMapper extends BaseMapper<SysCarSeries> {
    /***
     * 分页获取车系列表
     * @param query
     * @param condition
     * @return
     */
    List<SysCarSeries> selectCarSeriesPage(Query query, Map condition);
    /**
     * 通过车品牌id获取车系 正序车系排序
     *
     * @param brandId
     * @return
     */
    List<SysCarSeries> selectCarSeriesListByBrandId(@Param("brandId") Integer brandId);

    /**
     * 最大
     * @return
     */
    Integer selectMaxSeriesId();
}
