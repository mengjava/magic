package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 消息推送类型
 * 1买入/2撤销买入通过/ 3撤销买入拒绝（买入）/4卖出/5卖出拒绝
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum MessagePushTypeEnum {
    /**
     * 是
     */
    BUYING(1, "买入"),
    CANCEL_BUYING_PASS(2, "撤销买入通过"),
    CANCEL_BUYING_REFUSE(3, "撤销买入拒绝"),
    TO_SELL(4, "卖出"),
    REFUSE_TO_SELL(5, "卖出拒绝"),
    ;

    MessagePushTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        MessagePushTypeEnum[] paymentModeEnums = MessagePushTypeEnum.values();
        for (MessagePushTypeEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
