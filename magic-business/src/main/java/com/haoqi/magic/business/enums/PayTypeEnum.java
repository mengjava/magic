package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 【收款专用】收款项类型（1车款+服务费+复检费-过户费，0车款-检测费, 3赔偿金）
 *
 * @Author: yanhao
 * @Date: 2020/1/19 9:55
 * @Param:
 * @Description:
 */
@Getter
public enum PayTypeEnum {

    ONE_TYPE(1, "车款+服务费+复检费-过户费"),//买家
    ZEOR_TYPE(0, "车款-检测费"),//卖家
    THREE_TYPE(3, "赔偿金"),;//买家

    PayTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    private Integer key;
    private String name;

    public static String getNameByKey(Integer key) {
        PayTypeEnum[] typeEnums = PayTypeEnum.values();
        for (PayTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }

}
