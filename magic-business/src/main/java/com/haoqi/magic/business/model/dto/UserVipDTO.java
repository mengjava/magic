package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author twg
 * @since 2019/12/25
 */
@Data
public class UserVipDTO implements Serializable {

    private Long id;
    /**
     * 免维保（数）
     */
    private Integer maintenanceFreeNum;
    /**
     * 免排放（数）
     */
    private Integer emissionFreeNum;
    /**
     * 免出险（数）
     */
    private Integer insuranceFreeNum;
    /**
     * 免车型识别(数)
     */
    private Integer carModelFreeNum;
    /**
     * 免快速评估(数)
     */
    private Integer evaluateFreeNum;
    /**
     * 维保(元/次):
     */
    private BigDecimal maintenancePrice;
    /**
     * 排放(元/次):
     */
    private BigDecimal emissionPrice;
    /**
     * 出险(元/次)
     */
    private BigDecimal insurancePrice;
    /**
     * 车型识别(元/次)
     */
    private BigDecimal carModelPrice;
    /**
     * 快速评估(元/次)
     */
    private BigDecimal evaluatePrice;
}
