package com.haoqi.magic.common.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 */
@Getter
public enum AdvertStatusEnum {
    ON_SHELVES(1, "上架"),
    OFF_SHELVES(2, "下架");

    AdvertStatusEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    private Integer key;
    private String name;

}
