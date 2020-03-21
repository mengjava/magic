package com.haoqi.magic.business.model.dto;

import com.haoqi.rigger.core.page.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/12/23 0023 16:05
 * @Description:
 */
@Data
public class CsCashDTO  extends Page implements Serializable  {
	private static final long serialVersionUID = 275922347426346832L;

	@NotNull
	private Long id;

	@ApiModelProperty(value = "开始申请提现时间")
	private Date startGmtCreate;

	@ApiModelProperty(value = "结束申请提现时间")
	private Date endGmtCreate;
	/**
	 * 提现金额
	 */
	@ApiModelProperty(value = "开始提现金额")
	private BigDecimal startMoney;
	/**
	 * 提现金额
	 */
	@ApiModelProperty(value = "结束提现金额")
	private BigDecimal endMoney;
	/**
	 * 提现人
	 */
	@ApiModelProperty(value = "姓名")
	private String cashUser;
	/**
	 * 提现审核时间
	 */
	@ApiModelProperty(value = "开始审核时间")
	private Date startCashAuditTime;
	/**
	 * 提现审核时间
	 */
	@ApiModelProperty(value = "结束审核时间")
	private Date endCashAuditTime;
	/**
	 * 提现审核状态(1:通过，2拒绝，0默认）
	 */
	@ApiModelProperty(value = "提现审核状态(1:通过，2拒绝，0默认）")
	private Integer cashAuditStatus;
	/**
	 * 【冗余】银行卡号
	 */
	@ApiModelProperty(value = "银行卡号")
	private String bankCardNo;
	/**
	 * 【冗余】户名
	 */
	@ApiModelProperty(value = "银行户名")
	private String bankUserName;
	/**
	 * 【冗余】手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String bankUserTel;


	@ApiModelProperty(value = "提现审核备注 审核时用")
	@NotBlank
	private String cashAuditRemark;




}
