package com.haoqi.magic.system.model.enums;

import lombok.Getter;

/**
 * @author twg
 * @since 2018/8/7
 */
@Getter
public enum UserRoleEnum {
	INSPECTOR(1, "jianceyuan"),
	AUDITOR(2, "shenheyuan");

	UserRoleEnum(Integer key, String name) {
		this.key = key;
		this.name = name;
	}

	public static String getTypeName(Integer key) {
		UserRoleEnum[] typeEnums = UserRoleEnum.values();
		for (UserRoleEnum typeEnum : typeEnums) {
			if (typeEnum.getKey().equals(key)) {
				return typeEnum.getName();
			}
		}
		return "";
	}


	private Integer key;
	private String name;

}
