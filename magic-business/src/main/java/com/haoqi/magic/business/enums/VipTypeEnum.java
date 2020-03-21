package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 会员类型
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum VipTypeEnum {
    /**
     * 非会员
     */
    NON_VIP(0, "非会员"),
    EXPERIENCE_VIP(1, "体验会员"),
    RECHARGE_VIP(2, "充值会员"),;

    VipTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        VipTypeEnum[] paymentModeEnums = VipTypeEnum.values();
        for (VipTypeEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
