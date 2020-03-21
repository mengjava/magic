package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.entity.CsOrderAudit;

/**
 * <p>
 * 审核信息表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-12-05
 */
public interface ICsOrderAuditService extends IService<CsOrderAudit> {

    /**
     * 通过订单id，获取订单审核信息
     *
     * @param orderId
     * @return
     */
    CsOrderAudit getByOrderId(Long orderId);

}
