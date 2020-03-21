package com.haoqi.magic.business.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/5/7 0007 15:09
 * @Description:
 */
@Data
public class CsCarDealerAuditDTO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 经销商名称/营业执照名称(唯一）
	 */
	@ApiModelProperty(value = "经销商名称/营业执照名称(唯一）")
	private String dealerName;
	/**
	 * 经销商简称
	 */
	@ApiModelProperty(value = "经销商简称")
	private String shortName;
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	private String address;
	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话")
	private String tel;
	/**
	 * 联系人姓名
	 */
	@ApiModelProperty(value = "联系人姓名")
	private String contactName;
	/**
	 * 商家所在地
	 */
	@ApiModelProperty(value = "商家所在地")
	private Long sysAreaId;

	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址中文名称")
	private String sysAreaName;

	/**
	 * 成立日期
	 */
	@ApiModelProperty(value = "成立日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date establishTime;
	/**
	 * 营业执照编码（唯一）
	 */
	@ApiModelProperty(value = "营业执照编码")
	private String licenceNo;
	/**
	 * 审核明细，追加json格式存储（审核人/审核时间/审核操作/审核备注）
	 */
	@ApiModelProperty(value = "审核明细")
	private String auditDetail;
	/**
	 * 固定电话
	 */
	@ApiModelProperty(value = "固定电话")
	private String fixPhone;
	/**
	 * 营业执照文件名
	 */
	@ApiModelProperty(value = "营业执照文件名")
	private String fileName;
	/**
	 * 营业执照分组名
	 */
	@ApiModelProperty(value = "营业执照分组名")
	private String fileGroup;
	/**
	 * 营业执照文件路径
	 */
	@ApiModelProperty(value = "营业执照文件路径")
	private String filePath;


	@ApiModelProperty(value = "文件服务器地址")
	private String pictureURL;

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

	/**
	 * 状态（-1：申请拒绝，0：已申请，1：申请通过）
	 */
	@ApiModelProperty(value = "状态（-1：申请拒绝，0：已申请，1：申请通过")
	private Integer status;

	/**
	 * 省份code
	 */
	@ApiModelProperty(value = "省份code")
	private String provinceCode;

	/**
	 * 地市code
	 */
	@ApiModelProperty(value = "地市code")
	private String cityCode;
}
