package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 争议项类型：1买家，2卖家，3PC,默认为1
 *
 * @Author: yanhao
 * @Date: 2019/11/29 15:21
 * @Param:
 * @Description:
 */
@Getter
public enum DisputeItemTypeEnum {

    /**
     * 买家
     */
    BUYER_TYPE(1, "买家"),
    SALER_TYPE(2, "卖家"),
    PC_TYPE(3, "PC"),;

    DisputeItemTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    private Integer key;
    private String value;

    public static String getValue(Integer key) {
        DisputeItemTypeEnum[] typeEnums = DisputeItemTypeEnum.values();
        for (DisputeItemTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getValue();
            }
        }
        return "";
    }

    public static Integer getKey(String value) {
        DisputeItemTypeEnum[] typeEnums = DisputeItemTypeEnum.values();
        for (DisputeItemTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getValue().equals(value)) {
                return typeEnum.getKey();
            }
        }
        return DisputeItemTypeEnum.BUYER_TYPE.getKey();
    }
}
