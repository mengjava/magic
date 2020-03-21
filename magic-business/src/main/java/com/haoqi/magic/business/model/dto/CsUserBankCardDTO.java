package com.haoqi.magic.business.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/12/16 0016 15:15
 * @Description:
 */
@Data
public class CsUserBankCardDTO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 用户Id
	 */
	@ApiModelProperty(value = "用户Id")
	private Long sysUserId;
	/**
	 * 【数据字典】银行类型code
	 */
	@ApiModelProperty(value = "【数据字典】银行类型code")
	@NotBlank(message = "银行类型code不能为空")
	private String bankCode;
	/**
	 * 银行名称
	 */
	@ApiModelProperty(value = "银行名称")
	@NotBlank(message = "银行名称不能为空")
	private String bankName;
	/**
	 * 银行卡号
	 */
	@ApiModelProperty(value = "银行卡号")
	@NotBlank(message = "银行卡号不能为空")
	private String bankCardNo;
	/**
	 * 户名
	 */
	@ApiModelProperty(value = "户名")
	@NotBlank(message = "户名不能为空")
	private String bankUserName;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	@NotBlank(message = "手机号不能为空")
	private String bankUserTel;
	/**
	 * 身份证号（非必填）
	 */
	@ApiModelProperty(value = "身份证号（非必填）")
	private String bankUserCardNo;
}
