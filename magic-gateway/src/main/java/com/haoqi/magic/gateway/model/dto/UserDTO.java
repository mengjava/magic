package com.haoqi.magic.gateway.model.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.io.Serializable;
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
}
