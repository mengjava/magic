package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsFinancePageDTO;
import com.haoqi.magic.business.model.entity.CsFinancePayAction;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 财务打款动作表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2020-01-17
 */
public interface CsFinancePayActionMapper extends BaseMapper<CsFinancePayAction> {


    /**
     * 打款列表
     *
     * @param query
     * @param condition
     * @return
     */
    List<CsFinancePageDTO> selectFinanceByPage(Query query, Map condition);
}
