package com.haoqi.magic.common.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 * 启用、禁用 ENUM
 */
@Getter
public enum EnableDisableEnum {
    /**
     * 启用
     */
    ENABLE(0, "启用"),
    /**
     * 禁用
     */
    DISABLE(1, "禁用");

    EnableDisableEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    private Integer key;
    private String name;

}
