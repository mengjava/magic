package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 订单状态
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum OrderStatusEnum {
    /**
     * 买入
     */
    BUYING(1, "买入"),
    CANCEL_BUYING_APPLY(4, "撤销买入申请"),
    CANCEL_BUYING_PASS(7, "撤销买入通过"),
    REFUSE_TO_SELL(10, "拒绝卖出"),
    TO_SELL(13, "卖出"),
    PAID(16, "买家支付"),
    TRANSFERRED(19, "过户"),
    PAYMENT(22, "打款"),
    COMPLETE(25, "完成"),
    ;

    OrderStatusEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        OrderStatusEnum[] paymentModeEnums = OrderStatusEnum.values();
        for (OrderStatusEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
