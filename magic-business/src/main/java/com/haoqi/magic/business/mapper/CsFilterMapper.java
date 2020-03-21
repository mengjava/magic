package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CsFilterDTO;
import com.haoqi.magic.business.model.entity.CsFilter;
import com.haoqi.magic.business.model.vo.CsFilterVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 筛选管理表 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
public interface CsFilterMapper extends BaseMapper<CsFilter> {


    /**
     * 分页获取筛选数据
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/4/26 16:12
     */
    List<CsFilter> findPage(Query query, Map condition);

    /**
     * 通过条件获取标签信息
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/20 17:26
     */
    List<CsFilterDTO> getCsFilterWithCondition(CsFilterVO vo);
}
