package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 用户类型（1:超级管理员，2：普通管理，3：检测员，4：商家，5:买家（普通客户），6：复检员）
 */
@Getter
public enum UserTypeEnum {

    /**
     *超级管理员
     */
    USER_SUPER_ADMIN(1, "超级管理员"),
    USER_ADMIN(2, "普通管理"),
    USER_CHECK(3, "检测员"),
    USER_DEALER(4, "商家"),
    USER_BUYER(5, "买家（普通客户）"),
    USER_REAGENTS(6, "复检员");

    private Integer key;
    private String name;

    UserTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public static String getTypeName(Integer key) {
        UserTypeEnum[] typeEnums = UserTypeEnum.values();
        for (UserTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }
}
