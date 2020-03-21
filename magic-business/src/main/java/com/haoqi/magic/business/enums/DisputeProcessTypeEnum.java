package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 不属实1，赔偿2，买家违约3、卖家违约4、协商平退5
 *
 * @author twg
 */
@Getter
public enum DisputeProcessTypeEnum {

    /**
     * 不属实
     */
    NOT_TRUE_TYPE(1, "不属实"),
    INDEMNIFY_TYPE(2, "赔偿"),
    BUYER_BREAK_CAR_TYPE(3, "买家违约"),
    SELLER_BREAK_CAR_TYPE(4, "卖家违约"),
    NEGOTIATE_BACK_TYPE(5, "协商平退"),;

    DisputeProcessTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    private Integer key;
    private String name;

    public static String getNameByKey(Integer key) {
        DisputeProcessTypeEnum[] typeEnums = DisputeProcessTypeEnum.values();
        for (DisputeProcessTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }

}
