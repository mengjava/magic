package com.haoqi.magic.business.model.vo;


import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * ClassName:com.haoqi.magic.system.model.vo <br/>
 * Function: <br/>
 * Date:     2019/4/26 10:24 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsParamVO extends Page {

    private static final long serialVersionUID = -1499501174790245866L;

    /**
     * 主键
     */
    private Long id;


    //自定义参数名称
    @ApiModelProperty(value = "自定义参数名称",required = true)
    @NotBlank(message = "自定义参数名称不能为空")
    private String paramName;

    //最小零售价格（元）
    @ApiModelProperty(value = "最小零售价格（元）")
    private BigDecimal minPrice;
    @ApiModelProperty(value = "最小零售价格（元）区间开始")
    private BigDecimal minPriceStart;
    @ApiModelProperty(value = "最小零售价格（元）区间结束")
    private BigDecimal minPriceEnd;

    //最大零售价格（元）
    @ApiModelProperty(value = "最大零售价格（元）")
    private BigDecimal maxPrice;
    @ApiModelProperty(value = "最大零售价格（元）区间开始")
    private BigDecimal maxPriceStart;
    @ApiModelProperty(value = "最大零售价格（元）区间结束")
    private BigDecimal maxPriceEnd;


    //最小行驶里程（万公里）
    @ApiModelProperty(value = "最小行驶里程（万公里）")
    private BigDecimal minTravelDistance;
    @ApiModelProperty(value = "最小行驶里程（万公里）区间开始")
    private BigDecimal minTravelDistanceStart;
    @ApiModelProperty(value = "最小行驶里程（万公里）区间结束")
    private BigDecimal minTravelDistanceEnd;

    //最大行驶里程（万公里）
    @ApiModelProperty(value = "最大行驶里程（万公里）")
    private BigDecimal maxTravelDistance;
    @ApiModelProperty(value = "最大行驶里程（万公里）区间开始")
    private BigDecimal maxTravelDistanceStart;
    @ApiModelProperty(value = "最大行驶里程（万公里）区间结束")
    private BigDecimal maxTravelDistanceEnd;


    //车辆年限(单位：年）
    @ApiModelProperty(value = "车辆年限(单位：年）")
    private Integer carAge;

    //【数据字典】车辆类型
    @ApiModelProperty(value = "车辆类型")
    private String carTypeCode;

    //审核通过时间（单位:天）
    @ApiModelProperty(value = "审核通过时间（单位:天）")
    private Integer auditTime;


    //过户次数
    @ApiModelProperty(value = "过户次数")
    private Integer transferNum;

    //是否诚信联盟（0：不是，1：是）
    @ApiModelProperty(value = "是否诚信联盟")
    private Integer creditUnion;

    //拼接sql字符串
    @ApiModelProperty(value = "拼接sql字符串")
    private String sqlStr;

}
