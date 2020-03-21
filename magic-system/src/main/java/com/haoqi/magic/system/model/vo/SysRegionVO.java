package com.haoqi.magic.system.model.vo;

import com.haoqi.rigger.core.page.Page;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @Author: mengyao
 * @Date: 2019/4/28 0028 16:29
 * @Description:
 */
@Data
public class SysRegionVO extends Page {


	/**
	 * 创建人
	 */

	private Long creator;
	/**
	 * 修改人
	 */
	private Long modifier;
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	private Date gmtModified;



	/**
	 * 大区id
	 */
	private Long id;
	/**
	 * 大区
	 */
	@NotNull(message = "大区不能为空")
	private String name;

	/**
	 * 省份List
	 */
	@NotNull(message = "省份不能为空")
	private List<String> provinceCode;

}
