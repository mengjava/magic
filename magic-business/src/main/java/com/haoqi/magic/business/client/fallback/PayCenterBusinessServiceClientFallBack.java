package com.haoqi.magic.business.client.fallback;

import com.haoqi.magic.business.client.PayCenterBusinessServiceClient;
import com.haoqi.magic.business.model.dto.PayBaseDTO;
import com.haoqi.magic.business.model.dto.PayOrderDTO;
import com.haoqi.magic.business.model.dto.PayResultDTO;
import com.haoqi.rigger.core.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author twg
 * @since 2019/4/25
 */
@Component
public class PayCenterBusinessServiceClientFallBack implements PayCenterBusinessServiceClient {

	@Override
	public Result<List<Map<String, String>>> getPayWays(String appKey, String appSecret) {
		return Result.buildErrorResult("获取失败");
	}

	@Override
	public Result<PayOrderDTO> produceOrder(@RequestBody PayBaseDTO param) {
		return Result.buildErrorResult("支付失败");
	}

	@Override
	public Result<Map<String, PayResultDTO>> queryResult(@RequestBody PayBaseDTO param) {
		return Result.buildErrorResult("获取失败");
	}

	@Override
	public Result<PayResultDTO> closePay(@RequestBody PayBaseDTO param) {
		return Result.buildErrorResult("获取失败");
	}
}
