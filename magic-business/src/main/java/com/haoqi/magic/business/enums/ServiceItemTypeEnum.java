package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 查询服务项名称
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum ServiceItemTypeEnum {
    /**
     * 维保
     */
    INSURANCE_RECORD(1, "维保"),
    EMISSION(2, "排放"),
    OUT_DANGER(3, "出险"),
    VEHICLE_IDENTIFICATION(4, "车型识别"),
    FAST_EVALUATION(5, "快速评估");;

    ServiceItemTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        ServiceItemTypeEnum[] paymentModeEnums = ServiceItemTypeEnum.values();
        for (ServiceItemTypeEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
