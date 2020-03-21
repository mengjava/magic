package com.haoqi.magic.auth.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.haoqi.rigger.common.CommonConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author twg
 * @since 2019/1/8
 */
@Data
public class UserDTO implements Serializable {
    private Long id;
    /**
     * 帐号
     */
    private String loginName;
    /**
     * 帐号密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String username;
    /**
     * 帐号等级
     */
    private Integer type;
    /**
     * 盐值
     */
    private String salt;

    /**
     * 帐号状态
     */
    private Integer isEnabled;

    /**
     * 用户头像
     */
    private String headImageUrl;
    /**
     * 角色列表
     */
    private List<RoleDTO> roles = Lists.newArrayList();

    /**
     * 角色id集合
     */
    private String[] roleIds;

    public String[] getRoleIds() {
        if (CollectionUtil.isNotEmpty(roles)) {
            final List<String> roleIds = Lists.newArrayList();
            for (RoleDTO role : roles) {
                roleIds.add(role.getId().toString());
            }
            return roleIds.toArray(new String[roleIds.size()]);
        }
        return roleIds;
    }

    /**
     * 角色code集合
     */
    private String[] roleCodes;

    public String[] getRoleCodes() {
        if (CollectionUtil.isNotEmpty(roles)) {
            final List<String> roleIds = Lists.newArrayList();
            for (RoleDTO role : roles) {
                roleIds.add(role.getRoleCode());
            }
            return roleIds.toArray(new String[roleIds.size()]);
        }
        return roleCodes;
    }

    /**
     * 权限标识集合
     */
    private String[] permissions;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    public boolean isNonExpired(){
        return CommonConstant.STATUS_EXPIRE.equals(isEnabled) ? true : false;
    }

    public boolean isNonLocked(){
        return CommonConstant.STATUS_LOCK.equals(isEnabled) ? true : false;
    }

    public boolean isPasswordNonExpired(){
        return CommonConstant.STATUS_PASSWORD_EXPIRE.equals(isEnabled) ? true : false;
    }

    public boolean isDisabled() {
        return CommonConstant.STATUS_DEL.equals(isEnabled) ? true : false;
    }
}
