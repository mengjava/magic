package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * ClassName:com.haoqi.magic.business.model.vo <br/>
 * Function: 车商-车源管理<br/>
 * Date:     2019/5/8 11:15 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsCarSourcePageVO extends Page {
    private static final long serialVersionUID = -3828987006607695866L;

    @ApiModelProperty(value = "车型车款")
    private String sysCarModelName;

    //排量--开始
    @ApiModelProperty(value = "排量--开始")
    private Double displacementStart;

    //排量--结束
    @ApiModelProperty(value = "排量--结束")
    private Double displacementEnd;

    private String displacementType;

    //【来自数据字典】颜色
    @ApiModelProperty(value = "颜色")
    private String colorCode;

    //批发价格（元）--开始
    @ApiModelProperty(value = "批发价格（元）--开始")
    private BigDecimal wholesalePriceStart;
    //批发价格（元）--结束
    @ApiModelProperty(value = "批发价格（元）--结束")
    private BigDecimal wholesalePriceEnd;


    //行驶里程（万公里）--开始
    @ApiModelProperty(value = "行驶里程（万公里）--开始")
    private BigDecimal travelDistanceStart;
    //行驶里程（万公里）--结束
    @ApiModelProperty(value = "行驶里程（万公里）--结束")
    private BigDecimal travelDistanceEnd;

    //首次上牌时间--开始
    @ApiModelProperty(value = "首次上牌时间--开始")
    private String initDateStart;
    //首次上牌时间--结束
    @ApiModelProperty(value = "首次上牌时间--结束")
    private String initDateEnd;


    //零售价格（元）--开始
    @ApiModelProperty(value = "零售价格（元）--开始")
    private BigDecimal priceStart;
    //零售价格（元）--结束
    @ApiModelProperty(value = "零售价格（元）--结束")
    private BigDecimal priceEnd;

    //档位
    @ApiModelProperty(value = "档位")
    private String gearBoxCode;

    //排放标准
    @ApiModelProperty(value = "排放标准")
    private String emissionStandardCode;

    // 状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
    @ApiModelProperty(value = "状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）")
    private Integer publishStatus;
}
