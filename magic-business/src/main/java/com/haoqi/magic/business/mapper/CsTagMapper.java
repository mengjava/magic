package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CsTagParamDTO;
import com.haoqi.magic.business.model.entity.CsTag;
import com.haoqi.magic.business.model.vo.CsTagVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签展示管理 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-04-30
 */
public interface CsTagMapper
        extends BaseMapper<CsTag> {

    /**
     * 分页获取标签数据
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/4/30 14:45
     */
    List<CsTag> findPage(Query query, Map condition);

    /**
     * 通过条件获取标签数据
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/30 14:52
     */
    List<CsTag> getCsTagWithCondition(CsTagVO vo);

    /**
     * 通过标签id获取命中的标签sql
     * @param id
     * @return
     */
    String selectCsSqlStrByTagId(Long id);

    /**
     * 根据位置获取对应的标签以及自定义参数
     * @param param
     * @return
     * @author huming
     * @date 2019/5/14 14:04
     */
    List<CsTagParamDTO> getAllDetailTag(Map<String, Object> param);
}
