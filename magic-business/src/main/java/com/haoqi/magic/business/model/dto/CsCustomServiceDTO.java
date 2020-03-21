package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/5/17 0017 13:52
 * @Description:
 */
@Data
public class CsCustomServiceDTO implements Serializable {
	private static final long serialVersionUID = -958182484086610384L;

	private Long id;

	@ApiModelProperty(value = "车辆id")
	private Long carId;

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

	@ApiModelProperty(value = "初始登记日期")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date initDate;

	/**
	 * 排量
	 */
	@ApiModelProperty(value = "排量")
	private String gearBoxCode;

	/**
	 * 变速箱
	 */
	@ApiModelProperty(value = "变速箱")
    @JsonSerialize(using = ToStringSerializer.class)
	private Double displacement;

	private String displacementType;


	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "颜色")
	private String colorCode;

	/**
	 * 行驶里数
	 */
	@ApiModelProperty(value = "行驶里数")
	@JsonSerialize(using = ToStringSerializer.class)
	private BigDecimal travelDistance;


	/**
	 * 卖放商家
	 */
	@ApiModelProperty(value = "卖放商家")
	private String shortName;
	/**
	 * 卖方联系人
	 */
	@ApiModelProperty(value = "卖方联系人")
	private String contactName;
	/**
	 * 卖方手机号
	 */
	@ApiModelProperty(value = "卖方手机号")
	private String tel;
	/**
	 * 卖方电话
	 */
	@ApiModelProperty(value = "卖方电话")
	private String fixPhone;
	/**
	 * 意向买方商家
	 */
	@ApiModelProperty(value = "意向买方商家")
	private String buyerShortName;
	/**
	 * 意向买方联系人
	 */
	@ApiModelProperty(value = "意向买方联系人")
	private String buyerContactName;
	/**
	 * 意向买方手机
	 */
	@ApiModelProperty(value = "意向买方手机")
	private String buyerTel;
	/**
	 * 意向买方电话
	 */
	@ApiModelProperty(value = "意向买方电话")
	private String buyerFixPhone;
	/**
	 * 车辆状态
	 */
	@ApiModelProperty(value = "车辆状态")
	private Integer publishStatus;
	/**
	 * 处理状态
	 */
	@ApiModelProperty(value = "处理状态")
	private Integer processStatus;
	/**
	 * 意向时间
	 */
	@ApiModelProperty(value = "意向时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date intentionTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
	private Date gmtModified;

	/**
	 * 卖方
	 */
	@ApiModelProperty(value = "卖方id")
	private Long csCarDealerId;

	/**
	 * 买方
	 */
	@ApiModelProperty(value = "买方id")
	private Long csBuyDealerId;

	@ApiModelProperty(value = "处理备注")
	private String processRemark;

	@ApiModelProperty(value = "是否下架")
	private  Integer isPullOff;

	@ApiModelProperty(value = "检测员")
	private String checkLoginName;

	@ApiModelProperty(value = "检测员id")
	private Long checkUserId;

	@ApiModelProperty(value = "审核员")
	private String processUserName;

}
