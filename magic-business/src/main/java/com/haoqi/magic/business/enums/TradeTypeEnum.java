package com.haoqi.magic.business.enums;

import lombok.Getter;

/**
 * 交易类型
 *
 * @author twg
 * @since 2019/11/29
 */
@Getter
public enum TradeTypeEnum {
    /**
     * 1维保查询，2排放查询，3出险查询，4车型识别查询，5快速评估查询，7车款，8复检，10冻结保证金，11，提现，12 开通会员，13余额充值， 20赔偿，21违约退车，22，协商平退，
     */
    MAINTENANCE_TYPE(1, "维保"),
    EMISSION_TYPE(2, "排放"),
    INSURANCE_TYPE(3, "出险"),
    CAR_MODEL_TYPE(4, "车型识别"),
    EVALUATE_TYPE(5, "估值"),
    CAR_MONEY_TYPE(7, "车款"),
    CAR_RECHECK_TYPE(8, "复检"),
    FREEZE_AMOUNT_TYPE(10, "冻结保证金"),
    CASH_TYPE(11, "提现"),
    OPEN_VIP_TYPE(12, "会员充值"),
    BALANCE_RECHARGE_TYPE(13, "余额充值"),
    INDEMNIFY_TYPE(20, "赔偿金"),
    BREAK_CAR_TYPE(21, "违约保证金"),
    DRAW_BACK_TYPE(25, "解冻保证金"),
    ;

    TradeTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }


    public static String getNameByKey(Integer key) {
        TradeTypeEnum[] paymentModeEnums = TradeTypeEnum.values();
        for (TradeTypeEnum paymentModeEnum : paymentModeEnums) {
            if (paymentModeEnum.getKey().equals(key)) {
                return paymentModeEnum.getName();
            }
        }
        return "";
    }

    private Integer key;
    private String name;
}
