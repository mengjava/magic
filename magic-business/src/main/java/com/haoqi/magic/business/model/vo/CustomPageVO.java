package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/5/17 0017 11:27
 * @Description:
 */
@Data
public class CustomPageVO  extends Page {


	@ApiModelProperty(value = "车辆编号")
	private String carNo;

	@ApiModelProperty(value = "vin")
	private String vin;


	@ApiModelProperty(value = "车辆号码")
	private String plateNo;

	@ApiModelProperty(value = "车辆号码")
	private String sellDealerId;


	@ApiModelProperty(value = "车款车型")
	private String sysCarModelName;

	@ApiModelProperty(value = "卖方手机号")
	private String tel;

	@ApiModelProperty(value = "意向买方手机")
	private String buyerTel;

	@ApiModelProperty(value = "处理状态")
	private Integer processStatus;

	@ApiModelProperty(value = "车源状态")
	private Integer publishStatus;

	@ApiModelProperty(value = "意向开始时间")
	private String planStartDate ;

	@ApiModelProperty(value = "意向结束时间")
	private String planEndDate ;

	@ApiModelProperty(value = "更新开始时间")
	private String updateStartDate ;

	@ApiModelProperty(value = "更新结束时间")
	private String updateEndDate ;




}
