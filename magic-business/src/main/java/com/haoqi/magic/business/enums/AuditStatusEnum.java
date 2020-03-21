package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 * 审核状态
 */
@Getter
public enum AuditStatusEnum {
    /**
     * 拒绝
     */
    REJECTION(-1, "申请拒绝"),
    /**
     * 提交
     */
    SUBMITTED(0, "已申请"),
    /**
     * 通过
     */
    PASS(1, "申请通过");

    AuditStatusEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
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
