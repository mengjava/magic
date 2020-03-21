package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
     * 权限标识集合
     */
    private String[] permissions;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 所在城市
     */
    private String area;
    private Long areaId;
    private String tel;

    /**
     * vip标识（1：是，0：否，默认为0）
     */
    private Integer vipFlag;
    /**
     * 账户id
     */
    private Long accountId;
    /**
     * 账户余额
     */
    private BigDecimal balanceMoney;
    /**
     * 冻结金额
     */
    private BigDecimal freezeMoney;

    /**
     * 支付密码
     */
    private String payPassword;
}
