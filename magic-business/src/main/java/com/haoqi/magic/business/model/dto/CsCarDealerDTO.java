package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.haoqi.magic.business.enums.VipTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/5/7 0007 15:09
 * @Description:
 */
@Data
public class CsCarDealerDTO extends CsCarDealerBaseDTO {


	/**
	 * 成立日期
	 */
	@ApiModelProperty(value = "公司成立日期")
	@JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
	private Date establishTime;

	/**
	 * 状态（-1：申请拒绝，0：已申请，1：申请通过）
	 */
	@ApiModelProperty(value = "状态（-1：申请拒绝，0：已申请，1：申请通过）")
	private Integer status;
	/**
	 * 是否禁用（0-启用，1-禁用）
	 */
	@ApiModelProperty(value = "是否禁用（0-启用，1-禁用）")
	private Integer isEnabled;

	/**
	 * 是否诚信联盟（0：不是，1：是）
	 */
	@ApiModelProperty(value = "是否诚信联盟（0：不是，1：是）")
	private Integer creditUnion;

	/**
	 * 提交日期
	 */
	@ApiModelProperty(value = "提交日期")
	@JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
	private Date gmtCreate;


	/**
	 * 审核日期
	 */
	@ApiModelProperty(value = "审核日期")
	@JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
	private Date lastAuditTime;

	/**
	 * 开通时间
	 */
	@ApiModelProperty(value = "开通时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date beginDate;


	/**
	 * 商家所在地
	 */
	private Long sysAreaId;

	@ApiModelProperty(value = "收款方式（1：先打款后过户，0：先过户后打款，默认0）")
	private Integer paymentType;

	@ApiModelProperty(value = "是否展示（1：展示，默认，0：不展示）")
	private Integer isShow;

	private Integer vipType;

	@ApiModelProperty(value = "会员周期（单位：天）")
	private Integer period;

	@ApiModelProperty(value = "会员Id")
	private Long vipId;

	@ApiModelProperty(value = "会员用户Id")
	private Long useVipId;

	@ApiModelProperty(value = "会员是否禁用 0否1是 ")
	private Integer useVipIsDeleted;

	@ApiModelProperty(value = "会员")
	private String vipTypeStr;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "会员到期时间")
	private Date expiredDate;

	public String getVipTypeStr() {
		if (null != vipType) {
			return VipTypeEnum.getNameByKey(vipType);
		}
		return "";
	}
}
