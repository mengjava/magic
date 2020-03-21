package com.haoqi.magic.system.model.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 */
@Getter
public enum ConfigTypeEnum {
	SECURITY_DEPOSIT(1, "保证金金额"),
	WITHDRAW(2, "提现"),
	OVERTIME(3, "违约超时"),
	DISPUTE(4, "争议"),
	PUSH(5, "推送"),
	CAR_INFORMATION(6, "车况查询"),
	PHONE(7, "电话"),
	REVIEW(8, "复检");


	ConfigTypeEnum(Integer key, String name) {
        this.key = key;
        this.name = name;
    }

	public static String getValue(Integer key) {
		ConfigTypeEnum[] typeEnums = ConfigTypeEnum.values();
		for (ConfigTypeEnum typeEnum : typeEnums) {
			if (typeEnum.getKey().equals(key)) {
				return typeEnum.getName();
			}
		}
		return "";
	}
    private Integer key;
    private String name;

}
