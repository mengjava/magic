package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 过户状态
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum TransferStatusEnum {
    /**
     * 未过户
     */
    UN_TRANSFERRED(0, "未过户"),
    TRANSFER(1, "过户中"),
    TRANSFERRED(2, "已过户"),
    ;

    TransferStatusEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        TransferStatusEnum[] paymentModeEnums = TransferStatusEnum.values();
        for (TransferStatusEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
