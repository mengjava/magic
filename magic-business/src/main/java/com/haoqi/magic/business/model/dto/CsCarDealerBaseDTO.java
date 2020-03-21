package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mengyao
 * @Date: 2019/5/7 0007 15:09
 * @Description:
 */
@Data
public class CsCarDealerBaseDTO implements Serializable {

	/**
	 * id
	 */
	@ApiModelProperty(value = "id")
	private Long id;
	/**
	 * 经销商简称
	 */
	@ApiModelProperty(value = "公司简称")
	private String shortName;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "商家所在地")
	private String address;

	/**
	 * 经销商名称/营业执照名称(唯一）
	 */
	@ApiModelProperty(value = "经销商名称/营业执照名称(唯一）")
	private String dealerName;

	/**
	 * 联系人姓名
	 */
	@ApiModelProperty(value = "联系人姓名")
	private String contactName;

	/**
	 * 联系人手机
	 */
	@ApiModelProperty(value = "联系人手机")
	private String tel;

	/**
	 * 固定电话
	 */
	@ApiModelProperty(value = "联系人电话")
	private String fixPhone;

}
