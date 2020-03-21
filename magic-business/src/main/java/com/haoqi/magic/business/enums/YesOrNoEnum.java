package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 是否
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum YesOrNoEnum {
    /**
     * 是
     */
    YES(1, "是"),
    NO(0, "否"),
    ;

    YesOrNoEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        YesOrNoEnum[] paymentModeEnums = YesOrNoEnum.values();
        for (YesOrNoEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
