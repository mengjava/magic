package com.haoqi.magic.business.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/12/23 0023 16:05
 * @Description:
 */
@Data
public class CsCashVO implements Serializable {

	private static final long serialVersionUID = 6705293426671031474L;

	private Long id;

	/**
	 * 提现人
	 */
	@ApiModelProperty(value = "姓名")
	private String cashUser;

	/**
	 * 【冗余】手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String bankUserTel;

	/**
	 * 【冗余】户名
	 */
	@ApiModelProperty(value = "打款银行")
	private String bankName;
	/**
	 * 【冗余】户名
	 */
	@ApiModelProperty(value = "银行户名")
	private String bankUserName;

	/**
	 * 银行卡号
	 */
	@ApiModelProperty(value = "银行账户")
	private String bankCardNo;

	/**
	 * 提现金额
	 */
	@ApiModelProperty(value = "提现金额")
	private BigDecimal money;

	/**
	 * 提现审核状态(1:通过，2拒绝，0默认）
	 */
	@ApiModelProperty(value = "提现审核状态(1:通过，2拒绝，0默认）")
	private Integer cashAuditStatus;


	/**
	 * 提现审核人
	 */
	@ApiModelProperty(value = "提现审核人")
	private String cashAuditUser;

	/**
	 * 申请提现时间
	 */
	@ApiModelProperty(value = "申请提现时间")
	@JsonSerialize(using = DateTimeJsonSerializer.class)
	private Date gmtCreate;

	/**
	 * 提现审核时间
	 */
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "提现审核时间")
	private Date cashAuditTime;

	/**
	 * 提现审核备注
	 */
	@ApiModelProperty(value = "提现审核备注")
	private String cashAuditRemark;



}
