package com.haoqi.magic.common.enums;


import lombok.Getter;

/**
 * Created by twg on 2018/6/20.
 */
@Getter
public enum UserLevelEnum {
    //1:超级管理员，2：普通管理，3：检测员，4：商家，5：普通用户
    SUPER_ADMIN_LEVEL(1, "超级管理员"),
    NORMAL_ADMIN_LEVEL(2, "普通管理"),
    INSPECTOR_LEVEL(3, "检测员"),
    SELLER_LEVEL(4, "商家"),
    USER_LEVEL(5, "普通用户"),
    USER_REAGENTS(6, "复检员"),
    ;

    UserLevelEnum(Integer level, String levelName) {
        this.level = level;
        this.levelName = levelName;
    }

    private Integer level;
    private String levelName;
}
