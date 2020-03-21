package com.haoqi.magic.system.common.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 车辆检测项枚举
 * @auther: yanhao
 * @param:
 * @date: 2019/5/5 18:57
 * @Description:
 */
@Getter
public enum CarCheckItemTypeEnum {
    //1事故检测，2外观检测，3检测信息
    //incident detection
    INCIDENT_CHECK(1, "事故检测"),
    //Appearance detection
    APPEARANCE_CHECK(2, "外观检测"),
    //Detection information
    INFORMATION_CHECK(3, "检测信息");

    CarCheckItemTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public static String getTypeName(Integer key) {
        CarCheckItemTypeEnum[] typeEnums = CarCheckItemTypeEnum.values();
        for (CarCheckItemTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }
    private Integer key;
    private String name;
}
