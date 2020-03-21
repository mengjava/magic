package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CsCustomBuiltDTO;
import com.haoqi.magic.business.model.entity.CsCustomBuilt;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户定制表 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
public interface CsCustomBuiltMapper extends BaseMapper<CsCustomBuilt> {

    /**
     * 分页获取定制信息
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/5/5 11:43
     */
    List<CsCustomBuiltDTO> findPage(Query query, Map condition);

    /**
     * 通过ID更新定制信息
     * @param filter
     * @return
     * @author huming
     * @date 2019/5/5 11:43
     */
    Boolean updateCsLoanCreditById(CsCustomBuilt filter);
}
