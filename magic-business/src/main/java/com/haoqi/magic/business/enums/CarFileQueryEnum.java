package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 */
@Getter
public enum CarFileQueryEnum {
    //基本照片 0 ，事故照片 1外观照片， 2，缺陷照片 3
	BASE_PIC_TYPE(0, "基本照片"),
	ACCIDENT_PIC_TYPE(1, "事故照片"),
	OUTLOOK_PIC_TYPE(2, "外观照片"),
	DEFECT_PIC_TYPE(3, "缺陷照片");

    CarFileQueryEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

    private Integer key;
    private String name;

}
