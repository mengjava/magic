package com.haoqi.magic.system.model.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 */
@Getter
public enum ConfigNameEnum {
	SECURITY_DEPOSIT(10, "买入"),
	WITHDRAW(12, "卖出"),
	WITHDRAWAL_AMOUNT(20, "最低提现金额"),
	WITHDRAWAL_MODE(21, "提现模式"),
	DEFAULT_CYCLE(30, "买家付款违约周期"),
	TIMEOUT_PERIOD(31, "过户超时周期"),
	COMPENSATION_AMOUNT(40, "终审赔偿金额"),
	PAYMENT_CYCLE(50, "二次提醒付款周期"),
	INSURANCE_COST(60, "维保成本价"),
	EMISSION_COST(61, "排放成本"),
	DANGER_COST(62, "出险成本"),
	IDENTIFY_COST(63, "车型识别成本"),
	VALUATION_COST(64, "快速估值成本"),
	TEL(70, "客服电话"),
	REVIEW_AMOUN(80, "复检金额");


	ConfigNameEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }
	public static String getValue(Integer key) {
		ConfigNameEnum[] typeEnums = ConfigNameEnum.values();
		for (ConfigNameEnum typeEnum : typeEnums) {
			if (typeEnum.getKey().equals(key)) {
				return typeEnum.getName();
			}
		}
		return "";
	}

    private Integer key;
    private String name;

}
