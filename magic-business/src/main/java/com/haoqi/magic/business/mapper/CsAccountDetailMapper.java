package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.AccountDetailDTO;
import com.haoqi.magic.business.model.dto.AccountTotalAmountDTO;
import com.haoqi.magic.business.model.entity.CsAccountDetail;
import com.haoqi.rigger.mybatis.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账单明细表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-12-02
 */
public interface CsAccountDetailMapper extends BaseMapper<CsAccountDetail> {

    /**
     * 分页查询账单明细
     *
     * @param query
     * @param condition
     * @return
     */
    List<AccountDetailDTO> findPage(Query query, Map condition);

    /**
     * 统计收入和支出
     *
     * @param timeStart
     * @param timeEnd
     * @param tradeType
     * @param userId
     * @return
     */
    AccountTotalAmountDTO totalAmount(@Param("timeStart") String timeStart,@Param("timeEnd") String timeEnd,
                                      @Param("tradeType") Integer tradeType,@Param("userId") Long userId);

}
