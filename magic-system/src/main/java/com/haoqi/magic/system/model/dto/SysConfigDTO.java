package com.haoqi.magic.system.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/11/29 0029 17:23
 * @Description:
 */
@Data
public class SysConfigDTO implements Serializable {
	private static final long serialVersionUID = 447069388483802219L;

	/**
	 * 主键
	 */
	@NotNull(message = "id不能为空")
	private Long id;
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
	 * 注释
	 */
	private String remark;
	/**
	 * 是否删除 0否1是
	 */
	private Integer isDeleted;
	/**
	 * 值
	 */
	@ApiModelProperty(value = "value值 设置时传参用 必传")
	@NotBlank(message = "值不能为空")
	private String globalValue;
	/**
	 * 类别(1:保证金金额，2提现，3违约超时,4争议，5推送）
	 */
	@ApiModelProperty(value = "类别  用来搜索 传id")
	private Integer type;
	/**
	 * 名称（10：跨城拨打电话，11跨城买入，12跨城卖出，13跨城拨打电话未交易退回周期,20间隔提现周期，30买家付款违约周期，31过户超时周期，40初审赔付金额范围，41复检价格，50二次提醒付款周期，60维保成本价，61排放成本价，62出险成本价，63车型识别成本价，64快速估值成本价）
	 */
	@ApiModelProperty(value = "名称 用来搜索 传id 。 设置时必传")
	private Integer name;


}
