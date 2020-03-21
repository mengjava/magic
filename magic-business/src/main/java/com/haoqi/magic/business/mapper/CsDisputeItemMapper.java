package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsDisputeItemDTO;
import com.haoqi.magic.business.model.entity.CsDisputeItem;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 争议项管理 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
public interface CsDisputeItemMapper extends BaseMapper<CsDisputeItem> {
    /**
     * 分页查询争议项
     *
     * @param query
     * @param condition
     * @return
     */
    List<CsDisputeItemDTO> selectPageByParam(Query query, Map condition);

    /**
     * 删除id和父级id
     *
     * @param id
     * @return
     */
    Integer deleteByIdAndParentId(@Param("id") Long id);

    /**
     * 列表查询
     *
     * @param params
     * @return
     */
    List<CsDisputeItemDTO> selectListByParam(Map<String, Object> params);
}
