package com.haoqi.magic.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author twg
 * @since 2019/4/25
 */
public interface IUserService extends UserDetailsService {

    /**
     * 获取非管理员、检测员用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserNotAdminByUsername(String username) throws UsernameNotFoundException;

    /**
     * 获取检测员用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadInspectorUserByUsername(String username) throws UsernameNotFoundException;

}
