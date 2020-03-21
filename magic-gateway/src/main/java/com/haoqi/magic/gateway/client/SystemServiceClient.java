package com.haoqi.magic.gateway.client;

import com.haoqi.magic.gateway.client.fallback.SystemServiceClientFallBack;
import com.haoqi.magic.gateway.model.dto.MenuDTO;
import com.haoqi.magic.gateway.model.dto.UserDTO;
import com.haoqi.rigger.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author twg
 * @since 2019/4/22
 */
@FeignClient(name = "magic-system", fallback = SystemServiceClientFallBack.class)
public interface SystemServiceClient {

    @GetMapping("/user/getUserRolesByName/{username}")
    Result<UserDTO> getUserRolesByName(@PathVariable("username") String username);

    /**
     * 通过用户名获取用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/user/getUserByName/{loginName}")
    Result<UserDTO> getUserByName(@PathVariable("loginName") String username);

    /**
     * 通过用户、角色，获取菜单信息
     *
     * @param userId
     * @param roleCodes
     * @return
     */
    @PostMapping("/permission/findMenuByRoles")
    Result<List<MenuDTO>> findMenuByRoles(@RequestParam("userId") Long userId, @RequestParam("roleCodes") String... roleCodes);
}
