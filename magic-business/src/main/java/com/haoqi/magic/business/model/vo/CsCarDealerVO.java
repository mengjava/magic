package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/5/5 0005 10:28
 * @Description:
 */
@Data
public class CsCarDealerVO {

	/**
	 * 经销商简称
	 */
	@ApiModelProperty(value = "公司简称",required = true)
	@NotBlank(message = "公司简称不能为空")
	private String shortName;

	/**
	 * 经销商名称/营业执照名称(唯一）
	 */
	@ApiModelProperty(value = "营业执照名称",required = true)
	private String dealerName;

	/**
	 * 地址
	 */
	@ApiModelProperty(value = "公司地址",required = true)
	private String address;

	/**
	 * 商家所在地
	 */
	@ApiModelProperty(value = "商家所在地",required = true)
	@NotNull(message = "商家所在地不能为空")
	private Long sysAreaId;

	/**
	 * 成立日期
	 */
	@ApiModelProperty(value = "公司成立日期",required = true)
	private Date establishTime;


	/**
	 * 营业执照编码（唯一）
	 */
	@ApiModelProperty(value = "营业执照号码",required = true)
	private String licenceNo;

	/**
	 * 电话
	 */
	@NotBlank(message = "联系人手机不能为空")
	@ApiModelProperty(value = "联系人手机",required = true)
	private String tel;

	/**
	 * 联系人姓名
	 */
	@NotBlank(message = "联系人姓名不能为空")
	@ApiModelProperty(value = "联系人姓名",required = true)
	private String contactName;

	/**
	 * 验证码
	 */
	@NotBlank(message = "验证码不能为空")
	@ApiModelProperty(value = "验证码",required = true)
	private String messageCode;

	/**
	 * 营业执照文件名
	 */
	@ApiModelProperty(value = "营业执照文件名",required = true)
	private String fileName;

	/**
	 * 营业执照分组名
	 */
	private String fileGroup;

	/**
	 * 营业执照文件路径
	 */
	@ApiModelProperty(value = "营业执照文件路径",required = true)
	private String filePath;

	/**
	 * 固定电话
	 */
	@ApiModelProperty(value = "固定电话",required = true)
	private String fixPhone;

	/**
	 * 法人身份证正面照文件名
	 */
	@ApiModelProperty(value = "法人身份证正面照文件名")
	private String cardFrontFileName;

	/**
	 * 法人身份证正面照分组名
	 */
	@ApiModelProperty(value = "法人身份证正面照分组名")
	private String cardFrontFileGroup;

	/**
	 * 法人身份证正面照文件路径
	 */
	@ApiModelProperty(value = "法人身份证正面照文件路径")
	private String cardFrontFilePath;


}
