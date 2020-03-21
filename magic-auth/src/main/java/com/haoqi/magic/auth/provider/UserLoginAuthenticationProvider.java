package com.haoqi.magic.auth.provider;

import cn.hutool.core.util.RandomUtil;
import com.haoqi.rigger.core.exception.PasswordExpiredException;
import com.haoqi.rigger.core.exception.UserDisabledException;
import com.haoqi.rigger.core.exception.UserExpiredException;
import com.haoqi.rigger.core.exception.UserLockedException;
import com.haoqi.rigger.security.authentication.AbstractAuthenticationProvider;
import com.haoqi.rigger.security.authentication.UserNamePasswordAuthenticationToken;
import com.haoqi.rigger.security.core.DefaultUserDetails;
import com.haoqi.rigger.security.core.User;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author twg
 * @since 2019/4/18
 */
public class UserLoginAuthenticationProvider extends AbstractAuthenticationProvider {
    private final PasswordEncoder passwordEncoder;

    public UserLoginAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        super(userDetailsService);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication buildAuthenticationToken(Authentication authentication, UserDetails userDetail) {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        if (userDetail.isEnabled() && !userDetail.isAccountNonExpired() &&
                !userDetail.isAccountNonLocked() && !userDetail.isCredentialsNonExpired()) {
            throw new UserDisabledException("该账号已失效");
        } else if (userDetail.isAccountNonExpired() && !userDetail.isEnabled() &&
                !userDetail.isAccountNonLocked() && !userDetail.isCredentialsNonExpired()) {
            throw new UserExpiredException("该账号已过期");
        } else if (userDetail.isAccountNonLocked() && !userDetail.isEnabled() &&
                !userDetail.isAccountNonExpired() && !userDetail.isCredentialsNonExpired()) {
            throw new UserLockedException("该账号已锁定");
        } else if (userDetail.isCredentialsNonExpired() && !userDetail.isEnabled() &&
                !userDetail.isAccountNonExpired() && !userDetail.isAccountNonLocked()) {
            throw new PasswordExpiredException("该账号密码已过期");
        } else {
            DefaultUserDetails details = (DefaultUserDetails) userDetail;
            if (!passwordEncoder.matches(password + details.getSalt(), userDetail.getPassword())) {
                throw new BadCredentialsException("用户名或者密码错误");
            }
        }
        UserNamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UserNamePasswordAuthenticationToken(username, password, userDetail.getAuthorities());
        Map<String, Object> userMap = new HashMap(3);
        String token = RandomUtil.simpleUUID();
        userMap.put("token", token);
        userMap.put("id", ((User) userDetail).getId());
        userMap.put("userName", authentication.getPrincipal());
        userMap.put("userType", ((User) userDetail).getUserType());
        userMap.put("userLevel", ((User) userDetail).getUserLevel());
        usernamePasswordAuthenticationToken.setDetails(userMap);
        return usernamePasswordAuthenticationToken;
    }

    @Override
    protected UserDetails loadUser(String username) {
        return getUserDetailsService().loadUserByUsername(username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserNamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
