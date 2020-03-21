package com.haoqi.magic.system.model.enums;

import lombok.Getter;

/**
 * ClassName:com.haoqi.magic.system.model.enums <br/>
 * Function: 审核通过时间天数<br/>
 * Date:     2019/5/8 14:21 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Getter
public enum ParamAuditEnum {

    FIVE_DAY(1, 5),
    TEN_DAY(2, 10),
    FIFTY_DAY(3, 15),
    TWENTY_DAY(4, 20),
    TWENTY_FIVE_DAY(5, 25),
    OVER_DAY(6, 30);

    ParamAuditEnum(Integer key, Integer value) {
        this.key = key;
        this.value = value;
    }

    public static Integer getValue(Integer key) {
        ParamAuditEnum[] typeEnums = ParamAuditEnum.values();
        for (ParamAuditEnum typeEnum : typeEnums) {
            if (typeEnum.getKey().equals(key)) {
                return typeEnum.getValue();
            }
        }
        return 5;
    }


    private Integer key;
    private Integer value;
}
