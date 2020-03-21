package com.haoqi.magic.auth.service.impl;

import com.google.common.collect.Lists;
import com.haoqi.magic.auth.client.SystemServiceClient;
import com.haoqi.magic.auth.dto.UserDTO;
import com.haoqi.magic.auth.service.IUserService;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.security.core.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author twg
 * @since 2019/4/25
 */
@Slf4j
@Component
public class UserServiceImpl implements IUserService {
    @Autowired
    private SystemServiceClient systemServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        UserDTO user = getUserDTO(username);
        List<GrantedAuthority> roles = Lists.newArrayList();
        user.getRoles().forEach(roleDTO -> {
            roles.add(new SimpleGrantedAuthority(roleDTO.getRoleCode()));
        });
        return new User(user.getLoginName(), user.getPassword(), roles, user.getId(), user.getUsername(),
                null, user.getType(), user.getSalt(), user.getHeadImageUrl(), !user.isNonExpired(),
                !user.isPasswordNonExpired(), !user.isNonLocked(), !user.isDisabled());
    }

    @Override
    public UserDetails loadUserNotAdminByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = getUserDTO(username);
        if (UserLevelEnum.SUPER_ADMIN_LEVEL.getLevel().equals(user.getType()) ||
                UserLevelEnum.NORMAL_ADMIN_LEVEL.getLevel().equals(user.getType()) ||
                UserLevelEnum.INSPECTOR_LEVEL.getLevel().equals(user.getType())) {
            throw new UsernameNotFoundException("该帐号不存在");
        }
        List<GrantedAuthority> roles = Lists.newArrayList();
        user.getRoles().forEach(roleDTO -> {
            roles.add(new SimpleGrantedAuthority(roleDTO.getRoleCode()));
        });
        return new User(user.getLoginName(), user.getPassword(), roles, user.getId(), user.getUsername(),
                null, user.getType(), user.getSalt(), user.getHeadImageUrl(), !user.isNonExpired(),
                !user.isPasswordNonExpired(), !user.isNonLocked(), !user.isDisabled());
    }



    @Override
    public UserDetails loadInspectorUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = getUserDTO(username);
        if (!UserLevelEnum.INSPECTOR_LEVEL.getLevel().equals(user.getType())) {
            throw new UsernameNotFoundException("该帐号不存在");
        }
        List<GrantedAuthority> roles = Lists.newArrayList();
        user.getRoles().forEach(roleDTO -> {
            roles.add(new SimpleGrantedAuthority(roleDTO.getRoleCode()));
        });
        return new User(user.getLoginName(), user.getPassword(), roles, user.getId(), user.getUsername(),
                null, user.getType(), user.getSalt(), user.getHeadImageUrl(), !user.isNonExpired(),
                !user.isPasswordNonExpired(), !user.isNonLocked(), !user.isDisabled());
    }



    private UserDTO getUserDTO(String username) {
        Result<UserDTO> result = systemServiceClient.getUserRolesByName(username);
        if (!result.isSuccess() || result.getData() == null) {
            if (log.isDebugEnabled()) {
                log.debug("通过用户名：{}，获取用户密码、角色等信息为空！", username);
            }
            throw new UsernameNotFoundException("该帐号不存在");
        }
        UserDTO user = result.getData();
        if (user.isDisabled()) {
            throw new DisabledException("该帐号已被禁用");
        }
        return user;
    }
}
