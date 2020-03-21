package com.haoqi.magic.auth.client;

import com.haoqi.magic.auth.client.fallback.SystemServiceClientFallBack;
import com.haoqi.magic.auth.dto.ClientDetailDTO;
import com.haoqi.magic.auth.dto.UserDTO;
import com.haoqi.rigger.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author twg
 * @since 2019/1/8
 */
@FeignClient(name = "magic-system", fallback = SystemServiceClientFallBack.class)
public interface SystemServiceClient {

    /**
     * 获取用户、角色信息
     *
     * @param username
     * @return
     */
    @GetMapping("/user/getUserRolesByName/{username}")
    Result<UserDTO> getUserRolesByName(@PathVariable("username") String username);


    /**
     * 获取客户端认证信息
     *
     * @param clientId
     * @return
     */
    @GetMapping("/oauthClientDetails/{clientId}")
    Result<ClientDetailDTO> clientDetail(@PathVariable("clientId") String clientId);
}
