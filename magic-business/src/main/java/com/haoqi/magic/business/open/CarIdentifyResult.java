package com.haoqi.magic.business.open;

import lombok.Data;

/**
 * <p>
 * 车辆信息表
 * </p>
 *
 * @author yanhao
 * @since 2019-05-14
 */
@Data
public class CarIdentifyResult  {
    /**
     * 车型id
     */
    private Long sysCarModelId;
    /**
     * 【冗余字段】车型名称
     */
    private String sysCarModelName;
    /**
     * 【冗余字段】车辆品牌名称
     */
    private String sysCarBrandName;
    /**
     * 【冗余字段】车系名称
     */
    private String sysCarSeriesName;
    /**
     * vin码
     */
    private String vin;

    /**
     * 【来自数据字典】排放标准
     */
    private String emissionStandardCode;

    private String emissionStandard;
    /**
     * 排量 数字
     */
    private Double displacement;
    /**
     * 【数据字典】变速箱 手动 自动
     */
    private String gearBoxCode;

    private String gearBox;
    /***
     * 排量类型 : 0L 1T
     */
    private String displacementType;
    /***
     * 区别配置
     */
    private DiffParams diffParams;
}
