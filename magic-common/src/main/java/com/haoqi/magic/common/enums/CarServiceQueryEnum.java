package com.haoqi.magic.common.enums;

import lombok.Getter;

/**
 * 车况服务查询
 * 60维保成本价，61排放成本价，62出险成本价，63车型识别成本价，64快速估值成本价
 * 1维保查询，2排放查询，3出险查询，4车型识别查询，5快速评估查询
 *
 * @author twg
 * @since 2018/8/7
 */
@Getter
public enum CarServiceQueryEnum {
    /**
     * 维保成本价
     */
    VIBO(60, "维保成本价"),
    /**
     * 禁用
     */
    DISCHARGE(61, "排放成本价"),
    DANGER(62, "排放成本价"),
    VIN(63, "车型识别成本价"),
    CARCOSTPRICE(64, "快速估值成本价"),
    ;

    CarServiceQueryEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    private Integer key;
    private String name;

}
