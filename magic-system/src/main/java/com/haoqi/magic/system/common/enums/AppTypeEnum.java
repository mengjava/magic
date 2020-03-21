package com.haoqi.magic.system.common.enums;

import lombok.Getter;

/**
 * ClassName:com.haoqi.magic.system.common.enums <br/>
 * Function: APP类型 <br/>
 * Date:     2019/5/5 16:28 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Getter
public enum AppTypeEnum {
    //APP类型 1:IOS 2:安卓,3:IOS-pad 4:安卓-pad
    IOS("1", "IOS"),
    ANDROID("2", "安卓"),
    IOS_PAD("3", "IOS-pad"),
    ANDROID_PAD("4", "安卓-pad"),
    ;

    AppTypeEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public static String getTypeName(String key) {
        AppTypeEnum[] typeEnums = AppTypeEnum.values();
        for (AppTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }


    private String key;
    private String name;
}
