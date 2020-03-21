package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 不属实1，赔偿2，买家违约3、卖家违约4、协商平退5
 *
 * @author twg
 */
@Getter
public enum DisputeProcessTypeStrEnum {


    /**
     * 1) 买家违约、卖家违约、协商平退：打款金额 = 车价 + 服务费
     * 2）赔偿：打款金额 = 赔偿金
     */
    NOT_TRUE_TYPE(1, "不属实", ""),
    INDEMNIFY_TYPE(2, "赔偿", "赔偿金"),
    BUYER_BREAK_CAR_TYPE(3, "买家违约", "车价 + 服务费"),
    SELLER_BREAK_CAR_TYPE(4, "卖家违约", "车价 + 服务费"),
    NEGOTIATE_BACK_TYPE(5, "协商平退", "车价 + 服务费"),;

    DisputeProcessTypeStrEnum(Integer key, String name, String desc) {
        this.key = key;
        this.name = name;
        this.desc = desc;
    }

    private Integer key;
    private String name;
    private String desc;

    public static String getNameByKey(Integer key) {
        DisputeProcessTypeStrEnum[] typeEnums = DisputeProcessTypeStrEnum.values();
        for (DisputeProcessTypeStrEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }

    public static String getDescByKey(Integer key) {
        DisputeProcessTypeStrEnum[] typeEnums = DisputeProcessTypeStrEnum.values();
        for (DisputeProcessTypeStrEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getDesc();
            }
        }
        return "";
    }

}
