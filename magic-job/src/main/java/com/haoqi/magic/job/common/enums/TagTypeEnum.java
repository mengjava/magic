package com.haoqi.magic.job.common.enums;

import lombok.Getter;

/**
 * ClassName:com.haoqi.magic.system.common.enums <br/>
 * Function: 标签位置<br/>
 * Date:     2019/4/26 16:17 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Getter
public enum TagTypeEnum {
    //1筛选里的标签，2详情，3今日推荐
    FILTER_TAG(1, "筛选标签"),
    DETAIL_TAG(2, "详情标签"),
    TODAY_TAG(3, "今日推荐标签");

    TagTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    public static String getTypeName(Integer key) {
        TagTypeEnum[] typeEnums = TagTypeEnum.values();
        for (TagTypeEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getName();
            }
        }
        return "";
    }


    private Integer key;
    private String name;
}
