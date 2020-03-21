package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CsLoanCreditDTO;
import com.haoqi.magic.business.model.entity.CsLoanCredit;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分期表 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
public interface CsLoanCreditMapper extends BaseMapper<CsLoanCredit> {

    /**
     * 分页获取分期数据
     * @param query
     * @param condition
     * @return
     * @author huming
     * @date 2019/5/5 10:07
     */
    List<CsLoanCreditDTO> findPage(Query query, Map condition);

    /**
     * 通过ID、代理商ID更新分期数据
     * @param filter
     * @return
     * @author huming
     * @date 2019/5/5 10:14
     */
    Boolean updateCsLoanCreditById(CsLoanCredit filter);
}
