package com.haoqi.magic.common.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 * 开启、关闭 ENUM
 */
@Getter
public enum OpenCloseEnum {
    /**
     * 开启
     */
    OPEN(1, "开启"),
    /**
     * 关闭
     */
    CLOSE(0, "关闭");

    OpenCloseEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    private Integer key;
    private String name;

}
