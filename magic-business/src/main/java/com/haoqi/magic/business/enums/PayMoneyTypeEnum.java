package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 打款方式类型（1：买家车款，2卖家车款，3收款)
 *
 * @Author: yanhao
 * @Date: 2019/12/17 15:10
 * @Param:
 * @Description:
 */
@Getter
public enum PayMoneyTypeEnum {

    PAY_BUYER_TYPE(1, "买家车款"),
    PAY_SALER_TYPE(2, "卖家车款"),
    PAY_INCOME_TYPE(3, "收款"),;

    private Integer key;
    private String name;

    PayMoneyTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public String getNameByKey(Integer key) {
        PayMoneyTypeEnum[] payMoneyTypeEnums = PayMoneyTypeEnum.values();
        for (PayMoneyTypeEnum paymentModeEnum : payMoneyTypeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }
}
