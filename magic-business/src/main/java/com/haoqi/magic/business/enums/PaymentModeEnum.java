package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 支付模式
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum PaymentModeEnum {
    /**
     * 支付宝支付
     */
    ALIPAY_MODE(1, "支付宝支付"),
    WECHAT_MODE(2, "微信支付"),
    FLASH_MODE(3, "闪付支付"),
    BALANCE_MODE(4, "余额支付"),;

    PaymentModeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public String getNameByKey(Integer key) {
        PaymentModeEnum[] paymentModeEnums = PaymentModeEnum.values();
        for (PaymentModeEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
