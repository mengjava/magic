package com.haoqi.magic.business.model.vo;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户会员关联表
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@Data
@Accessors(chain = true)
public class CsUserVipVO extends Model<CsUserVipVO> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 会员金额
     */
    @ApiModelProperty(value = "会员金额")
    private BigDecimal money;

    /**
     * 免维保（数）
     */
    @ApiModelProperty(value = "免维保（数）")
    private Integer maintenanceFreeNum;
    /**
     * 免排放（数）
     */
    @ApiModelProperty(value = "免排放(数)")
    private Integer emissionFreeNum;
    /**
     * 免出险（数）
     */
    @ApiModelProperty(value = "免出险(数)")
    private Integer insuranceFreeNum;
    /**
     * 免车型识别(数)
     */
    @ApiModelProperty(value = "免车型识别(数)")
    private Integer carModelFreeNum;
    /**
     * 免快速评估(数)
     */
    @ApiModelProperty(value = "免快速评估(数)")
    private Integer evaluateFreeNum;

	/**
	 * 维保(元/次):
	 */
	@ApiModelProperty(value = "维保(元/次):")
	private BigDecimal maintenancePrice;
	/**
	 * 排放(元/次):
	 */
	@ApiModelProperty(value = "排放(元/次):")
	private BigDecimal emissionPrice;
	/**
	 * 出险(元/次)
	 */
	@ApiModelProperty(value = "出险(元/次)")
	private BigDecimal insurancePrice;
	/**
	 * 车型识别(元/次)
	 */
	@ApiModelProperty(value = "车型识别(元/次)")
	private BigDecimal carModelPrice;
	/**
	 * 快速评估(元/次)
	 */
	@ApiModelProperty(value = "快速评估(元/次)")
	private BigDecimal evaluatePrice;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
