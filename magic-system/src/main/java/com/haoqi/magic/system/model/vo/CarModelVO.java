package com.haoqi.magic.system.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yanhao on 2019/4/28.
 */
@Getter
@Setter
public class CarModelVO implements Serializable{


    /**
     * 主键
     */
    private Long id;

    /**
     * 车辆品牌id
     */
    private Integer brandId;
    /**
     * 车辆品牌名称
     */
    private String brandName;
    /**
     * 车系id
     */
    private Integer seriesId;
    /**
     * 车系名称
     */
    private String seriesName;
    /**
     * 车系分组名
     */
    private String seriesGroupName;
    /**
     * 车型id
     */
    private Integer modelId;
    /**
     * 车型名称
     */
    @NotEmpty
    private String modelName;
    /**
     * 价格（单位：万元）
     */
    private BigDecimal price;
    /**
     * 排量
     */
    private String liter;
    /**
     * 手动/自动/手自一体
     */
    private String gearType;
    /**
     * 年份
     */
    private String modelYear;
    /**
     * 合资/进口/国产
     */
    private String makerType;
    /**
     * 国标
     */
    private String dischargeStandard;
    /**
     * 座位数
     */
    private String seatNumber;
    /**
     * 上牌最小时间
     */
    private String minRegYear;
    /**
     * 上牌最大时间
     */
    private String maxRegYear;


}
