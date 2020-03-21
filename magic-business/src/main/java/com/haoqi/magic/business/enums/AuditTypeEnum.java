package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 审核类型
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum AuditTypeEnum {
    /**
     * 撤销买入审核
     */
    AUDIT_CANCEL_BUYING(0, "撤销买入审核"),
    AUDIT_TRANSFER(1, "过户审核"),
    AUDIT_DISPUTE_HANDLE(2, "争议处理"),
    AUDIT_DISPUTE_FIRST(3, "争议初审"),
    AUDIT_DISPUTE_FINISH(4, "争议终审"),
    ;

    AuditTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        AuditTypeEnum[] paymentModeEnums = AuditTypeEnum.values();
        for (AuditTypeEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
