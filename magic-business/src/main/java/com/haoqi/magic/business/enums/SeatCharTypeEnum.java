package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 座椅材质
 */
@Getter
public enum SeatCharTypeEnum {
    /**
     * 240001 真皮
     * 240002 织物
     * 240003 混合材质
     * 240004 改装
     */
    ZEOR("240001", "真皮"),
    ONE("240002", "织物"),
    TWO("240003", "混合材质"),
    THREE("240004", "改装"),;

    SeatCharTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(String key) {
        SeatCharTypeEnum[] typeEnums = SeatCharTypeEnum.values();
        for (SeatCharTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getValue();
            }
        }
        return "";
    }

    public static String getKey(String value) {
        SeatCharTypeEnum[] typeEnums = SeatCharTypeEnum.values();
        for (SeatCharTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum.getKey();
            }
        }
        return SeatCharTypeEnum.ONE.getKey();
    }

    private String key;
    private String value;
}
