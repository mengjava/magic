package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * ClassName:com.haoqi.magic.system.common.enums <br/>
 * Function: <br/>
 * Date:     2019/4/26 16:17 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Getter
public enum FilterTypeEnum {
    //1车系，2品牌，3价格
    CAR_SERIAL(1, "车系", "series"),
    BRAND(2, "品牌", "brand"),
    PRICE(3, "价格", "price");

    FilterTypeEnum(Integer key, String name, String value) {
        this.key = key;
        this.name = name;
        this.value = value;
    }

    public static String getTypeValue(Integer key) {
        FilterTypeEnum[] typeEnums = FilterTypeEnum.values();
        for (FilterTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getValue();
            }
        }
        return "";
    }


    private Integer key;
    private String name;
    private String value;
}
