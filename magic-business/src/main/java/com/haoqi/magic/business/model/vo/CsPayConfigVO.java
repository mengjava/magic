package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/12/12 0012 14:06
 * @Description:
 */
@Data
public class CsPayConfigVO implements Serializable {
	private static final long serialVersionUID = 5475714552276647171L;
	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 是否删除 0否1是
	 */
	@ApiModelProperty(value = "是否禁用 0否1是")
	private Integer isDeleted;
	/**
	 * 产品名称
	 */
	@ApiModelProperty(value = "产品名称")
	private String productName;
	/**
	 * 产品code
	 */
	@ApiModelProperty(value = "产品code")
	private String productCode;
	/**
	 * 支付类别（0：代付，1：协议支付，默认为0）
	 */
	@ApiModelProperty(value = "支付类别（0：代付，1：协议支付，默认为0）")
	private Integer payType;
	/**
	 * 客户展示名称
	 */
	@ApiModelProperty(value = "支付名称")
	private String showName;
	/**
	 * 文件路径
	 */
	@ApiModelProperty(value = "logo路径")
	private String filePath;
}
