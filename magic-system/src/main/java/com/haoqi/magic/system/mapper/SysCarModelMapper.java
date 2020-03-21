package com.haoqi.magic.system.mapper;

import com.haoqi.magic.system.model.entity.SysCarModel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车型表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface SysCarModelMapper extends BaseMapper<SysCarModel> {
    /**
     * 分页获取车型
     * @param query
     * @param condition
     * @return
     */
    List<SysCarModel> selectCarModelPage(Query query, Map condition);

    /**
     * 通过车系id 获取车型信息*
     *
     * @param seriesId
     * @return
     */
    List<SysCarModel> selectCarModelListBySeriesId(@Param("seriesId") Integer seriesId);
}
