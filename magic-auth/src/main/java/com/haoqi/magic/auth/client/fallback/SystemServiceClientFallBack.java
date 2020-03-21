package com.haoqi.magic.auth.client.fallback;

import com.haoqi.magic.auth.client.SystemServiceClient;
import com.haoqi.magic.auth.dto.ClientDetailDTO;
import com.haoqi.rigger.core.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author twg
 * @since 2019/4/25
 */
@Component
public class SystemServiceClientFallBack implements SystemServiceClient {
    @Override
    public Result getUserRolesByName(@PathVariable("username") String username) {
        return Result.buildErrorResult("");
    }

    @Override
    public Result<ClientDetailDTO> clientDetail(@PathVariable("clientId") String clientId) {
        return null;
    }
}
