package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * @author 空调 0手动 1自动,3无
 * @since 2019/5/5
 */
@Getter
public enum AirConditionTypeEnum {

    ZEOR(0, "手动"),
    ONE(1, "自动"),
    TWO(3, "无"),;

    AirConditionTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(Integer key) {
        AirConditionTypeEnum[] typeEnums = AirConditionTypeEnum.values();
        for (AirConditionTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getValue();
            }
        }
        return "";
    }

    public static Integer getKey(String value) {
        AirConditionTypeEnum[] typeEnums = AirConditionTypeEnum.values();
        for (AirConditionTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum.getKey();
            }
        }
        return AirConditionTypeEnum.ONE.getKey();
    }

    private Integer key;
    private String value;
}
