package com.haoqi.magic.business.service.impl;

import com.haoqi.magic.business.model.entity.CsOrderDispute;
import com.haoqi.magic.business.mapper.CsOrderDisputeMapper;
import com.haoqi.magic.business.service.ICsOrderDisputeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 争议表（每个订单多条争议记录） 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-12-12
 */
@Service
public class CsOrderDisputeServiceImpl extends ServiceImpl<CsOrderDisputeMapper, CsOrderDispute> implements ICsOrderDisputeService {

}
