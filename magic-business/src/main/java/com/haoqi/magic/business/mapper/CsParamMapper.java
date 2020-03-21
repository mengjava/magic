package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.entity.CsParam;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 自定义参数管理表 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
public interface CsParamMapper extends BaseMapper<CsParam> {

    /**
     * 分页获取自定义参数数据
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/4/26 10:41
     */
    List<CsParam> findPage(Query query, Map condition);
}
