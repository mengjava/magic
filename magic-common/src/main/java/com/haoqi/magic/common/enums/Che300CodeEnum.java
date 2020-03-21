package com.haoqi.magic.common.enums;

import lombok.Getter;

/**
 * 功能描述:
 * 错误状态枚举
 *
 * @Author: yanhao
 * @Date: 2020-02-21  15:17
 * @Param:
 * @Description:
 */
@Getter
public enum Che300CodeEnum {
    CODE_2000(2000, "通知成功"),
    CODE_2001(2001, "查询中"),
    CODE_2002(2002, "无数据"),
    CODE_9000(9000, "通知失败");

    Che300CodeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    private Integer key;
    private String name;

}
