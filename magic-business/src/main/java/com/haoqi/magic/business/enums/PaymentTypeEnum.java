package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 打款方式 1：先打款后过户，0：先过户后打款
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum PaymentTypeEnum {

    ZEOR_PAYMENT(0, "先过户后打款"),
    ONE_PAYMENT(1, "先打款后过户"),;

    PaymentTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        PaymentTypeEnum[] paymentModeEnums = PaymentTypeEnum.values();
        for (PaymentTypeEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
