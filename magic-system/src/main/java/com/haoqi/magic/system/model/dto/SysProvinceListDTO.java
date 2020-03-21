package com.haoqi.magic.system.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: mengyao
 * @Date: 2019/4/28 0028 09:42
 * @Description:
 */
@Data
public class SysProvinceListDTO implements Serializable {

	/**
	 * 省份名称
	 */
	@ApiModelProperty(value = "省份名称")
	private String provinceName;
	/**
	 * 省份code
	 */
	@ApiModelProperty(value = "省份code")
	private String provinceCode;
	/**
	 * 省份
	 */
	@ApiModelProperty(value = "城市名称,CODE等数据")
	private List<SysCityDTO>  cityList;

}
