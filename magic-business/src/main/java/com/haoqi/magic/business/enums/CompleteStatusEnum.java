package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 完成状态
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum CompleteStatusEnum {
    /**
     * 已完成
     */
    UN_COMPLETED(0, "未完成"),
    COMPLETED(1, "已完成"),
    ;

    CompleteStatusEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        CompleteStatusEnum[] paymentModeEnums = CompleteStatusEnum.values();
        for (CompleteStatusEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
