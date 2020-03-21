package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: mengyao
 * @Date: 2019/5/7 0007 14:23
 * @Description:
 */
@Data
public class CsCarDealerPageVO extends Page {

	/**
	 * 经销商简称
	 */
	@ApiModelProperty(value = "公司简称")
	private String shortName;

	/**
	 * 状态（-1：申请拒绝，0：已申请，1：申请通过）
	 */
	@ApiModelProperty(value = "状态（-1：申请拒绝，0：已申请，1：申请通过）")
	private Integer status;


	@ApiModelProperty(value = "提交开始时间")
	private String startDate;

	@ApiModelProperty(value = "提交结束时间")
	private String endDate;

	@ApiModelProperty(value = "会员类别（非会员：0，体验会员：1，充值会员2）")
	private Integer vipType;

	@ApiModelProperty(value = "会员周期（单位：天）")
	private Integer vipPeriod;

	@ApiModelProperty(value = "交易收款方式（1：先打款后过户，0：先过户后打款，默认0）")
	private Integer paymentType;

	@ApiModelProperty(value = "审核开始时间")
	private String startLastAuditTime;

	@ApiModelProperty(value = "审核结束时间")
	private String endLastAuditTime;

}
