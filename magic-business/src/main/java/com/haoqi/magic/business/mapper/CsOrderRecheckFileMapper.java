package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CsOrderRecheckFileDTO;
import com.haoqi.magic.business.model.entity.CsOrderRecheckFile;
import com.haoqi.rigger.mybatis.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 复检附件 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
public interface CsOrderRecheckFileMapper extends BaseMapper<CsOrderRecheckFile> {
    /**
     * 通过订单id获取复检项
     *
     * @param orderId
     * @return
     */
    List<CsOrderRecheckFileDTO> findWithCheckItemByOrderId(@Param("orderId") Long orderId);

    /**
     * 获取订单下检测项
     *
     * @param orderId
     * @param checkItemIds
     * @return
     */
    List<CsOrderRecheckFileDTO> findWithCheckItemByOrderIdItemIds(@Param("orderId") Long orderId, @Param("checkItemIds") List<Long> checkItemIds);

    /**
     * 复检分页列表
     *
     * @param query
     * @param condition
     * @return
     */
    List<CsOrderRecheckFileDTO> findPage(Query query, Map condition);
}
