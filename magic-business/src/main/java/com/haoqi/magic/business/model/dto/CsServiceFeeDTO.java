package com.haoqi.magic.business.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/12/18 0018 11:33
 * @Description:
 */
@Data
public class CsServiceFeeDTO extends Page implements Serializable {
	private static final long serialVersionUID = -8519919668346812101L;

	/**
	 * id
	 */
	private Long id;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gmtModified;
	/**
	 * 区域
	 */
	@ApiModelProperty(value = "区域",required = true)
	private Long sysAreaId;

	/**
	 * 最小价格
	 */
	@ApiModelProperty(value = "最小价格",required = true)
	private BigDecimal minMoney;
	/**
	 * 最大价格
	 */
	@ApiModelProperty(value = "最大价格",required = true)
	private BigDecimal maxMoney;
	/**
	 * 服务费比例
	 */
	@ApiModelProperty(value = "服务费比例")
	private BigDecimal feeRatio;
	/**
	 * 单笔服务费
	 */
	@ApiModelProperty(value = "单笔服务费")
	private BigDecimal feeMoney;
	/**
	 * 1：单笔/0服务费比例，默认0
	 */
	@ApiModelProperty(value = "1：单笔/0服务费比例，默认0 新增编辑的时候必传",required = true)
	private Integer type;

	@ApiModelProperty(value = "城市")
	private String cityName;

	@ApiModelProperty(value = "省份")
	private String provinceName;

	@ApiModelProperty(value = "省份code")
	private String provinceCode;

}
