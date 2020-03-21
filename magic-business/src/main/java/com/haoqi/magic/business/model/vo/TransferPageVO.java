package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 功能描述:
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/8/15 22:30
 * @Description:
 */
@Data
public class TransferPageVO extends Page {

    @ApiModelProperty(value = "车辆编号")
    private String carNo;

    @ApiModelProperty(value = "vin")
    private String vin;

    @ApiModelProperty(value = "车辆号码")
    private String plateNo;

    @ApiModelProperty(value = "车款车型")
    private String sysCarModelName;

    @ApiModelProperty(value = "车商id")
    private Long carDealerId;

    @ApiModelProperty(value = "开始排量")
    private Double startDisplacement;
    @ApiModelProperty(value = "结束排量")
    private Double endDisplacement;
    @ApiModelProperty(value = "排量: 0:L 1:T")
    private String displacementType;

    @ApiModelProperty(value = "开始表显里数(万公里)里数")
    private BigDecimal startTravelDistance;
    @ApiModelProperty(value = "结束表显里数(万公里)里数")
    private BigDecimal endTravelDistance;


    @ApiModelProperty(value = "变速箱")
    private String gearBoxCode;
    @ApiModelProperty(value = "排放标准")
    private String emissionStandardCode;
    @ApiModelProperty(value = "颜色")
    private String colorCode;

    @ApiModelProperty(value = "初登日期开始时间")
    private String initStartDate;
    @ApiModelProperty(value = "初登日期结束时间")
    private String initEndDate;


    @ApiModelProperty(value = "调拨时间开始时间")
    private String transferStartDate;
    @ApiModelProperty(value = "调拨时间结束时间")
    private String transferEndDate;

    @ApiModelProperty(value = "开始零售价格")
    private BigDecimal startPrice;
    @ApiModelProperty(value = "结束零售价格")
    private BigDecimal endPrice;

    @ApiModelProperty(value = "开始批发价格")
    private BigDecimal startWholesalePrice;
    @ApiModelProperty(value = "结束批发价格")
    private BigDecimal endWholesalePrice;

    @ApiModelProperty(value = "车辆状态")
    private Integer publishStatus;
}
