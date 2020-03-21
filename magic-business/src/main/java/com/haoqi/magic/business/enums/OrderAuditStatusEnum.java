package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 * 过户、争议、撤销审核状态
 */
@Getter
public enum OrderAuditStatusEnum {
    /**
     * 拒绝
     */
    REJECTION(2, "审核退回"),
    /**
     * 提交
     */
    SUBMITTED(0, "待审"),
    /**
     * 通过
     */
    PASS(1, "审核通过");

    OrderAuditStatusEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public static String getNameByKey(Integer key) {
        OrderAuditStatusEnum[] orderAuditStatusEnums = OrderAuditStatusEnum.values();
        for (OrderAuditStatusEnum orderAuditStatusEnum : orderAuditStatusEnums) {
            if (orderAuditStatusEnum.getKey().equals(key)) {
                return orderAuditStatusEnum.getName();
            }
        }
        return "";
    }


    /**
     * 键
     */
    private Integer key;
    /**
     * 键值
     */
    private String name;

}
