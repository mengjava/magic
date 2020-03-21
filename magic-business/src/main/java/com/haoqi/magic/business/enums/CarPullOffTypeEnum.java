package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 功能描述:
 *
 * @Author: yanhao
 * @Date: 2019/12/4 14:21
 * @Param:
 * @Description:
 */
@Getter
public enum CarPullOffTypeEnum {

    PULLOFF_PC_TYPE(1, "PC"),
    PULLOFF_CHECK_TYPE(2, "检测员"),
    PULLOFF_USER_TYPE(3, "用户自己"),;

    private Integer key;
    private String value;

    CarPullOffTypeEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
