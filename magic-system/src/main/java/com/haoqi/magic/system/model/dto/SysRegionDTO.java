package com.haoqi.magic.system.model.dto;

import com.haoqi.magic.system.model.entity.SysArea;
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
public class SysRegionDTO implements Serializable {

	@ApiModelProperty(value = "大区id")
	private Long id;
	/**
	 * 大区名称
	 */
	@ApiModelProperty(value = "大区名称")
	private String name;
	/**
	 * 省份
	 */
	@ApiModelProperty(value = "省份名称,CODE等数据")
	private List<SysAreaDTO>  areaList;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	private Date gmtModified;
}
