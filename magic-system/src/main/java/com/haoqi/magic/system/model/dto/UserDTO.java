package com.haoqi.magic.system.model.dto;

import cn.hutool.core.collection.CollectionUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
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
    @ApiModelProperty(value = "（用户状态（是否启用0-正常，1-失效，2-过期，3-锁定，4-密码过期）")
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

    private Long areaId;

    /**
     * 支付密码
     */
    private String payPassword;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "注册时间")
    private Date gmtCreate;


    private Date gmtModified;

    /**
     * 所在城市
     */
    private String area;

    private String tel;

    private Long creator;
    /**
     * 修改人
     */
    private Long modifier;
    /**
     * vip标识（1：体验会员，2充值会员，0：否，默认为0）
     */
    @ApiModelProperty(value = "vip标识（1：体验会员，2充值会员，0：否，默认为0）")
    private Integer vipFlag;

    /**
     * 推荐人
     */
    @ApiModelProperty(value = "（推荐人")
    private String introducer;

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
	 * 开通时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "开通时间")
	private Date beginDate;

    /**
     * 会员过期时间
     */
    @ApiModelProperty(value = "会员过期时间")
	private Date expiredDate;

	/**
	 * 会员周期
	 */
	@ApiModelProperty(value = "会员周期")
	private Integer period;

    /**
     * 商家id
     */
	private Long dealerId;

    /**
     * 会员卡类型
     */
	private Integer vipType;


}
