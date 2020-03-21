package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 车窗玻璃
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/8/15 11:35
 * @Description:
 */
@Getter
public enum GlassTypeEnum {
    /**
     * 220001 两门电动
     * 220002 手动
     * 220003 四门电动
     * 220004 加装
     */
    ZEOR("220001", "两门电动"),
    ONE("220002", "手动"),
    TWO("220003", "四门电动"),
    THREE("220004", "加装"),;

    GlassTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValue(Integer key) {
        GlassTypeEnum[] typeEnums = GlassTypeEnum.values();
        for (GlassTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getValue();
            }
        }
        return "";
    }

    public static String getKey(String value) {
        GlassTypeEnum[] typeEnums = GlassTypeEnum.values();
        for (GlassTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum.getKey();
            }
        }
        return GlassTypeEnum.ONE.getKey();
    }

    private String key;
    private String value;
}
