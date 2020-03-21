package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2019/5/5
 */
@Getter
public enum CarPublishStatusEnum {
    /**
     *
     */
    SAVE(0,"保存"),
    PUBLISH(1,"发布"),
    PURT_AWAY(2,"上架"),
    ALLOT(3,"调拨"),
    AUDIT_BACK(-1,"审核退回"),
    SOLD_OUT(-2,"下架");

    CarPublishStatusEnum(Integer key,String value){
        this.key = key;
        this.value = value;
    }

    public static String getValue(Integer key) {
        CarPublishStatusEnum[] typeEnums = CarPublishStatusEnum.values();
        for (CarPublishStatusEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getValue();
            }
        }
        return "";
    }


    /**
     * key
     */
    private Integer key;

    /**
     *
     */
    private String value;



}
