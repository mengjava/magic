package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 争议状态
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum DisputeFlagEnum {
    /**
     * 未争议
     */
    NON_DISPUTE(0, "未争议"),
    IN_DISPUTE(1, "争议中"),
    DISPUTED(2, "争议完成"),;

    DisputeFlagEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        DisputeFlagEnum[] paymentModeEnums = DisputeFlagEnum.values();
        for (DisputeFlagEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
