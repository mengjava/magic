package com.haoqi.magic.gateway.client.fallback;

import com.haoqi.magic.gateway.client.SystemServiceClient;
import com.haoqi.magic.gateway.model.dto.MenuDTO;
import com.haoqi.magic.gateway.model.dto.UserDTO;
import com.haoqi.rigger.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author twg
 * @since 2019/1/8
 */
@Slf4j
@Component
public class SystemServiceClientFallBack implements SystemServiceClient {

    @Override
    public Result<UserDTO> getUserRolesByName(@PathVariable("username") String username) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<UserDTO> getUserByName(@PathVariable("loginName") String username) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<List<MenuDTO>> findMenuByRoles(@RequestParam("userId") Long userId, @RequestParam("roleCodes") String... roleCodes) {
        return Result.buildErrorResult("获取失败");
    }
}
