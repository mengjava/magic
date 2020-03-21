package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 复检结果
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum RecheckResultEnum {
    /**
     * 维保
     */
    NORMAL(1, "正常"),
    UN_NORMAL(2, "异常"),
    ;

    RecheckResultEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        RecheckResultEnum[] paymentModeEnums = RecheckResultEnum.values();
        for (RecheckResultEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
