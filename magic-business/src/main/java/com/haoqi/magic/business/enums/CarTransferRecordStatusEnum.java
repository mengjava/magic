package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * @author huming
 * @date 2019/5/6 17:08
 */
@Getter
public enum CarTransferRecordStatusEnum {
    /**
     * "调拨状态（1：已申请，2：申请通过，-1：申请拒绝，取消调拨-2）
     */
    CANCEL_ALLOT(-2,"取消调拨"),
    APPLY_REFUSE(-1,"申请拒绝"),
    APPLY(1,"已申请"),
    APPLY_AGREE(2,"申请通过");

    CarTransferRecordStatusEnum(Integer key, String value){
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
