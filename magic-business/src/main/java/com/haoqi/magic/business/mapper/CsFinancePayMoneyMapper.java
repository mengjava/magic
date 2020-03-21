package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsFinancePageDTO;
import com.haoqi.magic.business.model.dto.CsFinancePayMoneyDTO;
import com.haoqi.magic.business.model.entity.CsFinancePayMoney;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 财务打款表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
public interface CsFinancePayMoneyMapper extends BaseMapper<CsFinancePayMoney> {
    /**
     * 分页查询财务打款记录
     *
     * @param query
     * @param condition
     * @return
     */
    List<CsFinancePayMoneyDTO> selectByPage(Query query, Map condition);

    /**
     * 打款列表
     *
     * @param query
     * @param condition
     * @return
     */
    List<CsFinancePageDTO> selectFinanceByPage(Query query, Map condition);
}
