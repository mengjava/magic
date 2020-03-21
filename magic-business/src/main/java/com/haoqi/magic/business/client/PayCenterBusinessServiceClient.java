package com.haoqi.magic.business.client;

import com.haoqi.magic.business.client.fallback.PayCenterBusinessServiceClientFallBack;
import com.haoqi.magic.business.model.dto.PayBaseDTO;
import com.haoqi.magic.business.model.dto.PayOrderDTO;
import com.haoqi.magic.business.model.dto.PayResultDTO;
import com.haoqi.rigger.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author twg
 * @since 2019/1/8
 */
@FeignClient(name = "paycenter-business", fallback = PayCenterBusinessServiceClientFallBack.class)
public interface PayCenterBusinessServiceClient {


    /**
     * 功能描述: 获取支付名称列表接口
     *
     * @param appKey
     * @param appSecret
     * @return com.haoqi.rigger.core.Result<java.util.List<java.util.Map<java.lang.String,java.lang.String>>>
     * @auther mengyao
     * @date 2019/12/12 0012 下午 3:17
     */
    @PostMapping("/payCenter/getPayWays")
    Result<List<Map<String, String>>> getPayWays(@RequestParam("appKey") String appKey, @RequestParam("appSecret") String appSecret);

    /**
     * 创建支付订单
     *
     * @param param
     * @return
     */
    @PostMapping("/payCenter/produceOrder")
    Result<PayOrderDTO> produceOrder(@RequestBody PayBaseDTO param);

    /**
     * 查询支付结果
     *
     * @param param
     * @return
     */
    @PostMapping("/payCenter/queryResult")
    Result<Map<String, PayResultDTO>> queryResult(@RequestBody PayBaseDTO param);

    /**
     * 取消支付
     *
     * @param param
     * @return
     */
    @PostMapping("/payCenter/closePay")
    Result<PayResultDTO> closePay(@RequestBody PayBaseDTO param);

}
