package com.haoqi.magic.system.common.enums;

import lombok.Getter;

/**
 * ClassName:com.haoqi.magic.system.common.enums <br/>
 * Function: <br/>
 * Date:     2019/4/26 16:17 <br/>
 *
 * @author 孟瑶
 * @see
 * @since JDK 1.8
 */
@Getter
public enum RegionLevelTypeEnum {
	PROVINCE(1, "省份"),
    CITY(2, "地市");

    RegionLevelTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public static String getTypeName(Integer key) {
        RegionLevelTypeEnum[] typeEnums = RegionLevelTypeEnum.values();
        for (RegionLevelTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }


    private Integer key;
    private String name;
}
