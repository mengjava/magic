package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 打款状态
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum PaymentStatusEnum {
    /**
     * 未打款
     */
    UN_PAYMENT(0, "未打款"),
    HAS_PAYMENT(1, "已打款"),
    ;

    PaymentStatusEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        PaymentStatusEnum[] paymentModeEnums = PaymentStatusEnum.values();
        for (PaymentStatusEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
