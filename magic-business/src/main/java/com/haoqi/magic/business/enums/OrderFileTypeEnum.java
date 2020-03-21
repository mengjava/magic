package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 订单附件表 cs_order_file 订单类型枚举
 */
@Getter
public enum OrderFileTypeEnum {

    TRANSFER_FILE_TYPE(1, "过户"),
    BUYER_TYPE(2, "买家打款"),
    SELLER_TYPE(3, "卖家打款");

    OrderFileTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    private Integer key;
    private String name;

    public static String getTypeName(Integer key) {
        OrderFileTypeEnum[] typeEnums = OrderFileTypeEnum.values();
        for (OrderFileTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }
}
