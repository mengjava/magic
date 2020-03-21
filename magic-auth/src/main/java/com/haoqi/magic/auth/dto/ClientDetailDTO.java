package com.haoqi.magic.auth.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author twg
 * @since 2019/2/27
 */
@Data
public class ClientDetailDTO implements Serializable {
    /**
     * 客户端key
     */
    private String clientId;
    /**
     * 客户访问资源
     */
    private String resourceIds;
    /**
     * 客户端密码
     */
    private String clientSecret;
    /**
     * 客户端范围：read、write
     */
    private String scope;
    /**
     * 客户端授权类型
     */
    private String authorizedGrantTypes;
    /**
     * 客户端为authorization_code的重定向URL
     */
    private String webServerRedirectUri;
    /**
     * 权限
     */
    private String authorities;
    /**
     * 令牌的有效期
     */
    private Integer accessTokenValidity;
    /**
     * 刷新令牌的有效期
     */
    private Integer refreshTokenValidity;
}
