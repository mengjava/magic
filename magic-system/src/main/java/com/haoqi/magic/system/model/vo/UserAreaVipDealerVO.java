package com.haoqi.magic.system.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author twg
 * @since 2019/12/29
 */
@Data
public class UserAreaVipDealerVO implements Serializable {
    private Long id;
    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String username;
    /**
     * 帐号等级
     */
    private Integer type;

    /**
     * 所在城市
     */
    @ApiModelProperty(value = "城市信息")
    private String area;

    @ApiModelProperty(value = "手机号")
    private String tel;

    /**
     * vip标识（1：体验会员，2充值会员，0：否，默认为0）
     */
    @ApiModelProperty(value = "vip标识（1：体验会员，2充值会员，0：否，默认为0）")
    private Integer vipFlag;


    /**
     * 会员过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "会员过期时间")
    private Date expiredDate;


    /**
     * 商家id
     */
    @ApiModelProperty(value = "商家id")
    private Long dealerId;

    /**
     * 用户头像
     */
    private String headImageUrl;

    private Long areaId;

    /**
     * 是否存在支付密码
     */
    private Boolean hasPayPassword;

    /**
     * 会员卡类型
     */
    private Integer vipType;

}
