package com.haoqi.magic.business.service.impl;

import com.haoqi.magic.business.model.entity.CsOrderAudit;
import com.haoqi.magic.business.mapper.CsOrderAuditMapper;
import com.haoqi.magic.business.service.ICsOrderAuditService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.rigger.core.error.RiggerException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 审核信息表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-12-05
 */
@Service
public class CsOrderAuditServiceImpl extends ServiceImpl<CsOrderAuditMapper, CsOrderAudit> implements ICsOrderAuditService {

    @Override
    public CsOrderAudit getByOrderId(Long orderId) {
        return Optional.ofNullable(super.selectById(orderId)).orElseThrow(() -> new RiggerException("该订单审核信息不存在"));
    }
}
