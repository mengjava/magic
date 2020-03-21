package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class CarPageVO extends Page {
    @ApiModelProperty(value = "品牌")
    private String carBrand;
    @ApiModelProperty(value = "VIN码")
    private String vin;
    @ApiModelProperty(value = "车商")
    private Long carDealerId;
    @ApiModelProperty(value = "车牌号")
    private String plateNo;
    @ApiModelProperty(value = "状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）")
    private Integer publishStatus;
    @ApiModelProperty(value = "调拨状态")
    private Integer transferStatus;

    @ApiModelProperty(value = "车辆编号")
    private String carNo;
    @ApiModelProperty(value = "车款车型")
    private String sysCarModelName;
    @ApiModelProperty(value = "开始排量")
    private Double startDisplacement;
    @ApiModelProperty(value = "结束排量")
    private Double endDisplacement;
    @ApiModelProperty(value = "排量类型 0:L 1:T")
    private String displacementType;
    @ApiModelProperty(value = "档位")
    private String gearBoxCode;
    @ApiModelProperty(value = "排放标准")
    private String emissionStandardCode;
    @ApiModelProperty(value = "颜色")
    private String colorCode;
    @ApiModelProperty(value = "初登日期开始时间")
    private String startInitDate;
    @ApiModelProperty(value = "初登日期结束时间")
    private String endInitDate;
    @ApiModelProperty(value = "开始表显里数(万公里)")
    //private BigDecimal startInstrumentShowDistance;
    private BigDecimal startTravelDistance;
    @ApiModelProperty(value = "结束表显里数(万公里)")
    private BigDecimal endTravelDistance;

    @ApiModelProperty(value = "开始零售价格（元）")
    private BigDecimal startPrice;
    @ApiModelProperty(value = "结束零售价格（元）")
    private BigDecimal endPrice;
    @ApiModelProperty(value = "开始批发价格（元）")
    private BigDecimal startWholesalePrice;
    @ApiModelProperty(value = "结束批发价格（元）")
    private BigDecimal endWholesalePrice;
    //pad和web公用,通过传类型区分排序
    private Integer newAudit;
}
