package com.haoqi.magic.system.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: mengyao
 * @Date: 2019/4/28 0028 16:57
 * @Description:
 */
@Data
public class SysCityDTO implements Serializable {
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "cityId")
	private Long id;

	/**
	 * 城市名称
	 */
	@ApiModelProperty(value = "城市名称")
	private String CityName;
	/**
	 * 城市code
	 */
	@ApiModelProperty(value = "城市code")
	private String CityCode;

}
