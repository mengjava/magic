package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: mengyao
 * @Date: 2019/12/12 0012 14:06
 * @Description:
 */
@Data
public class CsPayConfigDTO implements Serializable {
	private static final long serialVersionUID = 5475714552276647171L;
	/**
	 * 主键
	 */
	private Long id;

	@ApiModelProperty(value = "是否禁用  0否1是'")
	private Integer isDeleted;

	/**
	 * 产品名称
	 */
	@NotBlank(message = "产品名称不能为空")
	@ApiModelProperty(value = "产品名称")
	private String productName;
	/**
	 * 产品code
	 */
	@NotBlank(message = "产品code不能为空")
	@ApiModelProperty(value = "产品code")
	private String productCode;
	/**
	 * 支付类别（0：代付，1：协议支付，默认为0）
	 */
	@NotNull(message = "支付类别不能为空")
	@ApiModelProperty(value = "支付类别（0：代付，1：协议支付，默认为0）")
	private Integer payType;
	/**
	 * 客户展示名称
	 */
	@NotBlank(message = "客户展示名称不能为空")
	@ApiModelProperty(value = "客户展示名称 可以改")
	private String showName;

	/**
	 * 文件路径
	 */
	@NotBlank(message = "文件路径不能为空")
	@ApiModelProperty(value = "文件路径")
	private String filePath;
}
