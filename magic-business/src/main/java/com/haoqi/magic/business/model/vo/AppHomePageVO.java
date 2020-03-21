package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 功能描述:
 * APP首页搜索查询参数
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/7 10:04
 * @Description:
 */
@Data
public class AppHomePageVO extends HomePageBaseVO {

    //车辆品牌模糊搜索
    @ApiModelProperty(value = "关键字搜索-暂时品牌")
    private String keyword;

    @ApiModelProperty(value = "品牌")
    private String carBrand;

    @ApiModelProperty(value = "车系")
    private String carSeries;

   /* @ApiModelProperty(value = "车型")
    private String carModel;*/
    /**
     * 最小价格
     */
    @ApiModelProperty(value = "最小价格(单位万)")
    private BigDecimal minPrice;
    /***
     * 最大价格
     */
    @ApiModelProperty(value = "最大价格(单位万)")
    private BigDecimal maxPrice;
    /**
     * 最小注册年限
     */
    //private String minRegisteAge;
    /**
     * 最大注册年限
     */
    //private String maxRegisteAge;
    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;
    /***
     * 车龄
     */
    @ApiModelProperty(value = "车龄")
    private String age;
    /***
     * 排量
     */
    @ApiModelProperty(value = "排量")
    private String displacement;
    /**
     * 行驶里程
     */
    @ApiModelProperty(value = "行驶里程")
    private String travelDistance;
    /***
     * 车辆类型
     */
    @ApiModelProperty(value = "车辆类型")
    private String carType;
    /***
     * 排放标准
     */
    @ApiModelProperty(value = "排放标准")
    private String emissionStandard;
    /***
     * 变速箱
     */
    @ApiModelProperty(value = "变速箱")
    private String gearBox;
    /**
     * 座位数
     */
    @ApiModelProperty(value = "座位数")
    private String seatNum;
    /***
     * 驱动方式
     */
    @ApiModelProperty(value = "驱动方式")
    private String driveMethod;
    /***
     * 燃油类型
     */
    @ApiModelProperty(value = "燃油类型")
    private String fuelType;
    /**
     * 是否进口
     */
    @ApiModelProperty(value = "是否进口")
    private String importType;


    @ApiModelProperty(value = "1:买车 2:每日促销 3:一口价")
    private Integer type;

    /***
     * 最新发布 价格最高 价格最低 车龄最短 里程最短 诚信联盟
     */
    @ApiModelProperty(value = "最新发布:1 价格最高:2 价格最低:3 车龄最短:4 里程最短:5 诚信联盟:6")
    private Integer tagType;


    /**
     * 车辆年龄（主要是为了车辆定制中的[匹配车辆]）
     */
    @ApiModelProperty(value = "车辆年龄（主要是为了车辆定制中的[匹配车辆]）")
    private Integer ageNum;

    /**
     * 行驶里数（主要是为了车辆定制中的[匹配车辆]）
     */
    @ApiModelProperty(value = "行驶里数（主要是为了车辆定制中的[行驶里数]）")
    private BigDecimal travelDistanceNum;


    /**************************以下为新加参数********************************/

    @ApiModelProperty(value = "最小车龄")
    private Integer minAgeNum;
    @ApiModelProperty(value = "最大车龄")
    private Integer maxAgeNum;


}