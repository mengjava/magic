package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 功能类别
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum FuncTypeEnum {
    /**
     * 1：买入，2：待付款，3：过户，4：打款，5：完成，6：争议，7：已取消
     */
    BUYING(1, "买入"),
    PAYING(2, "待付款"),
    TRANSFERRED(3, "已过户待打款"),
    PAYMENT(4, "已打款待过户"),
    COMPLETE(5, "完成"),
    ARGUE(6, "争议"),
    CANCEL(7, "已取消"),
    ;

    FuncTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        FuncTypeEnum[] paymentModeEnums = FuncTypeEnum.values();
        for (FuncTypeEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
