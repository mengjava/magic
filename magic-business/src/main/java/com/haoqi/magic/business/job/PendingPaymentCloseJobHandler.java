package com.haoqi.magic.business.job;

import com.haoqi.magic.business.service.ICsAccountDetailService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 修改支付订单状态
 *
 * @author twg
 * @since 2020/1/9
 */
@Slf4j
@Component
public class PendingPaymentCloseJobHandler {

    @Autowired
    private ICsAccountDetailService accountDetailService;

    @XxlJob("pendingPaymentCloseJobHandler")
    public ReturnT<String> handler(String s) throws Exception {
        XxlJobLogger.log("pendingPaymentCloseJobHandler call task ...{}", s);
        accountDetailService.paymentInHandler();
        return ReturnT.SUCCESS;
    }
}
