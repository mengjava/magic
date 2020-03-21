package com.haoqi.magic.system.client;

import com.haoqi.magic.system.client.fallback.BusinessServiceClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author huming
 * @date 2019/5/14 14:38
 */
@FeignClient(name = "magic-business", fallback = BusinessServiceClientFallBack.class)
public interface BusinessServiceClient {
}
