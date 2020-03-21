package com.haoqi.magic.system.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	 * 省份名称
	 */
	@ApiModelProperty(value = "省份名称")
	private String provinceName;
	/**
	 * 地市名称
	 */
	@ApiModelProperty(value = "地市名称")
	private String cityName;
	/**
	 * 省份code
	 */
	@ApiModelProperty(value = "省份code")
	private String provinceCode;
	/**
	 * 地市code
	 */
	@ApiModelProperty(value = "地市code")
	private String cityCode;
}
