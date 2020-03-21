package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mengyao
 * @Date: 2019/5/6 0006 17:20
 * @Description:
 */
@Data
public class SysProvinceAndCityDTO implements Serializable {

	/**
	 * 主键
	 */
	private Long id;
	/**
	 * 省份名称
	 */
	private String provinceName;
	/**
	 * 地市名称
	 */
	private String cityName;
	/**
	 * 省份code
	 */
	private String provinceCode;
	/**
	 * 地市code
	 */
	private String cityCode;
}
