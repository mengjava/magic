package com.haoqi.magic.system.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户分页信息
 * @author huming
 * @date 2019/1/14 11:05
 */
@Data
public class UserPageVO extends Page {

    @ApiModelProperty(value = "用户名")
    private String loginName;
    @ApiModelProperty(value = "按用户名、角色名搜索")
    private String userName;

	@ApiModelProperty(value = "手机号")
	private String tel;

	@ApiModelProperty(value = "vip标识（1：体验会员，2充值会员，0：否，默认为0）")
	private Integer vipFlag;

	/**
	 * 帐号状态
	 */
	@ApiModelProperty(value = "（用户状态（是否启用0-正常，1-失效，2-过期，3-锁定，4-密码过期）")
	private Integer isEnabled;
}
