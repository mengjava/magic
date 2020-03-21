package com.haoqi.magic.business.job;


import cn.hutool.core.util.StrUtil;
import com.haoqi.magic.business.client.PayCenterBusinessServiceClient;
import com.haoqi.magic.business.model.dto.PayBaseDTO;
import com.haoqi.magic.business.model.dto.PayResultDTO;
import com.haoqi.magic.business.service.ICsCarOrderService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 支付结果获取 5s一次
 *
 * @author twg
 * @since 2019/12/27
 */
@Slf4j
@Component
public class PaymentResultCallJobHandler {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${appKey:PAYAPP0014}")
    private String appKey;
    @Value("${appSecret:1d734176d03b49678074e748f3b680bc}")
    private String appSecret;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;

    @Autowired
    private ICsCarOrderService orderService;

    @Autowired
    private PayCenterBusinessServiceClient payCenterBusinessServiceClient;

    @XxlJob("paymentResultCallJobHandler")
    public ReturnT<String> paymentResultCallJobHandler(String s) throws Exception {
        XxlJobLogger.log("payment result call task ...{}", s);
        List<Object> list = redisTemplate.opsForList().range(StrUtil.format("{}:thirdSerialNo", prefix), 0, -1);
        list.forEach(o -> {
            log.info(">>>>>>>>>>>>>>> paymentResultCallJobHandler 第三方流水号：" + o + " >>>>>>>>>>>>>>> ");
            PayBaseDTO payBaseDTO = new PayBaseDTO();
            payBaseDTO.setAppKey(appKey);
            payBaseDTO.setAppSecret(appSecret);
            payBaseDTO.setSerialNo(o.toString());
            try {
                Result<Map<String, PayResultDTO>> payResult = payCenterBusinessServiceClient.queryResult(payBaseDTO);
                if (payResult.isSuccess()) {
                    Map<String, PayResultDTO> map = payResult.getData();
                    PayResultDTO result = map.get(o.toString());
                    if (!CommonConstant.STATUS_NORMAL.equals(result.getStatus())) {
                        orderService.payCallbackHandler(o.toString(), result.getStatus(), result.getPayEndTime(), result.getRemark());
                    }
                }
            } catch (Exception e) {
                log.error("支付回调后异常：{}", e);
            }

        });
        return ReturnT.SUCCESS;
    }
}
