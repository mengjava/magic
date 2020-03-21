package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author mengyao
 * @since 2019/5/5
 */
@Data
public class CsConsoleDTO implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

	/**
	 * 车辆编号
	 */
    @ApiModelProperty(value = "车辆编号")
    private String carNo;

	/**
	 * vin
	 */
	@ApiModelProperty(value = "vin")
	private String vin;

	/**
	 * 车辆号码
	 */
	@ApiModelProperty(value = "车辆号码")
	private String plateNo;

	/**
	 * 车款车型
	 */
	@ApiModelProperty(value = "车款车型")
	private String sysCarModelName;

	/**
	 * 初始登记日期
	 */
	@ApiModelProperty(value = "初始登记日期")
	private Date initDate;

	/**
	 * 行驶里程（万公里）
	 */
	@ApiModelProperty(value = "表显里程(万公里)")
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal travelDistance;

	/**
	 * 排量
	 */
	@ApiModelProperty(value = "排量")
	@JsonSerialize(using = ToStringSerializer.class)
	private Double displacement;


	/**
	 * 【数据字典】变速箱
	 */
	@ApiModelProperty(value = "【数据字典】变速箱")
	private String gearBoxCode;

	/**
	 * 【来自数据字典】排放标准
	 */
	@ApiModelProperty(value = "【来自数据字典】排放标准")
	private String emissionStandardCode;

	/**
	 * 【来自数据字典】颜色
	 */
	@ApiModelProperty(value = "【来自数据字典】颜色")
	private String colorCode;


	/**
	 * 零售价格（元）
	 */
	@ApiModelProperty(value = "零售价格")
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal price;


	/**
	 * 批发价格（元）
	 */
	@ApiModelProperty(value = "批发价格")
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal wholesalePrice;

	/**
	 * 新车指导价
	 */
	@ApiModelProperty(value = "新车指导价")
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal suggestPrice;


	/**
	 * 车商
	 */
	@ApiModelProperty(value = "订单编号")
	private Long csCarDealerId;

	@ApiModelProperty(value = "车商")
	private String carDealer;


	/**
	 * 状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
	 */
	@ApiModelProperty(value = "订单编号")
	private Integer publishStatus;



	/**
	 * 提交时间/发布时间
	 */
	@ApiModelProperty(value = "提交时间/发布时间")
	private Date publishTime;


	/**
	 * 审核时间/上架时间
	 */
	@ApiModelProperty(value = "审核时间/上架时间")
	private Date auditTime;

	/**
	 * 检测员
	 */
	@ApiModelProperty(value = "检测员")
	private String checkLoginName;
	/**
	 * 检测员id
	 */
	@ApiModelProperty(value = "检测员id")
	private Long checkUserId;

	/**
	 * 审核员
	 */
	@ApiModelProperty(value = "审核员")
	private String auditLoginName;

	/**
	 * 排量类型 0:L 1:T
	 */
	@ApiModelProperty(value = "排量类型 0:L 1:T")
	private String displacementType;

	/**
	 * 管理员调拨处理时间
	 */
	@ApiModelProperty(value = "管理员调拨处理时间")
	private Date transferHandleTime;


}
