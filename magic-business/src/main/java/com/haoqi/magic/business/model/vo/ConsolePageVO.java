package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: mengyao
 * @Date: 2019/5/10 0010 16:56
 * @Description:
 */
@Data
public class ConsolePageVO extends Page {
    @ApiModelProperty(value = "车辆编号")
    private String carNo;

    @ApiModelProperty(value = "vin")
    private String vin;

    @ApiModelProperty(value = "车辆号码")
    private String plateNo;

    @ApiModelProperty(value = "车款车型")
    private String sysCarModelName;

    @ApiModelProperty(value = "排放标准")
    private String emissionStandardCode;

    @ApiModelProperty(value = "档位")
    private String gearBoxCode;

    @ApiModelProperty(value = "车辆状态")
    private Integer publishStatus;

    @ApiModelProperty(value = "车商")
    private Integer carDealerId;

    @ApiModelProperty(value = "发布开始时间")
    private String publishStartDate;
    @ApiModelProperty(value = "发布结束时间")
    private String publishEndDate;

    @ApiModelProperty(value = "审核开始时间")
    private String auditStartDate;
    @ApiModelProperty(value = "审核结束时间")
    private String auditEndDate;

    @ApiModelProperty(value = "调拨开始时间")
    private String transferHandleStartDate;
    @ApiModelProperty(value = "调拨结束时间")
    private String transferHandleEndDate;


    @ApiModelProperty(value = "检测员Id")
    private Long checkUserId;

    @ApiModelProperty(value = "审核员Id")
    private Long auditUserId;


}
