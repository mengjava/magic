package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * @author mengyao
 * @since 2019/5/5
 */
@Getter
public enum ProcessStatusEnum {
    /**
     *
     */
    PROCESSED(1,"已处理"),
	UNPROCESSED(0,"未处理");

    ProcessStatusEnum(Integer key, String value){
        this.key = key;
        this.value = value;
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
