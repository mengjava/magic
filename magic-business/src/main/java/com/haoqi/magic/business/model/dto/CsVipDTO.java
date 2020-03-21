package com.haoqi.magic.business.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/12/3 0003 15:00
 * @Description:
 */
@Data
public class CsVipDTO extends Page implements Serializable  {
	private static final long serialVersionUID = -3551790267062521278L;


	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "修改时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gmtModified;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;
	/**
	/**
	 * 是否删除 0否1是
	 */
	@ApiModelProperty(value = "是否删除 0否1是")
	private Integer isDeleted;
	/**
	 * 会员类别（非会员：0，体验会员：1，充值会员2）
	 */
	@NotNull(message = "会员类别不能为空")
	@ApiModelProperty(value = "会员类别（非会员：0，体验会员：1，充值会员2）")
	private Integer type;
	/**
	 * 会员名称
	 */
	@ApiModelProperty(value = "会员名称")
	@NotBlank(message = "会员名称不能为空")
	private String name;
	/**
	 * 会员周期（单位：天）
	 */
	@ApiModelProperty(value = "会员周期（单位：天）")
	@NotNull(message = "会员周期不能为空")
	private Integer period;
	/**
	 * 免维保（数）
	 */
	@ApiModelProperty(value = "免维保（数)")
	@NotNull(message = "免维保不能为空")
	private Integer maintenanceFreeNum;
	/**
	 * 免排放（数）
	 */
	@ApiModelProperty(value = "免排放（数）")
	@NotNull(message = "免排放不能为空")
	private Integer emissionFreeNum;
	/**
	 * 免出险（数）
	 */
	@ApiModelProperty(value = "免出险（数）")
	@NotNull(message = "免排放不能为空")
	private Integer insuranceFreeNum;
	/**
	 * 免车型识别(数)
	 */
	@ApiModelProperty(value = " 免车型识别(数)")
	@NotNull(message = "免车型识别次数不能为空")
	private Integer carModelFreeNum;
	/**
	 * 免快速评估(数)
	 */
	@ApiModelProperty(value = "免快速评估(数)")
	@NotNull(message = "免排放不能为空")
	private Integer evaluateFreeNum;
	/**
	 * 维保(元/次):
	 */
	@NotNull(message = "维保不能为空")
	@ApiModelProperty(value = "维保(元/次)")
	private BigDecimal maintenancePrice;
	/**
	 * 排放(元/次):
	 */
	@NotNull(message = "排放不能为空")
	@ApiModelProperty(value = "排放(元/次)")
	private BigDecimal emissionPrice;
	/**
	 * 出险(元/次)
	 */
	@NotNull(message = "出险不能为空")
	@ApiModelProperty(value = "出险(元/次)")
	private BigDecimal insurancePrice;
	/**
	 * 车型识别(元/次)
	 */
	@NotNull(message = "车型识别不能为空")
	@ApiModelProperty(value = "车型识别(元/次)")
	private BigDecimal carModelPrice;
	/**
	 * 快速评估(元/次)
	 */
	@NotNull(message = "评估不能为空")
	@ApiModelProperty(value = "快速评估(元/次)")
	private BigDecimal evaluatePrice;
	/**
	 * 是否展示（1：展示，默认，0：不展示）
	 */
	@ApiModelProperty(value = "是否展示（1：展示，默认，0：不展示）")
	private Integer isShow;
	/**
	 * 金额
	 */
	@NotNull(message = "金额不能为空")
	@ApiModelProperty(value = "金额")
	private BigDecimal money;

}
