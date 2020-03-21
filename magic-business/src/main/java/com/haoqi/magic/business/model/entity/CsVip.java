package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 会员配置表
 * </p>
 *
 * @author mengyao
 * @since 2019-12-03
 */
@Data
@Accessors(chain = true)
public class CsVip extends Model<CsVip> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 创建人
	 */
	@TableField(value = "creator",fill = FieldFill.INSERT)
	private Long creator;
	/**
	 * 修改人
	 */
	@TableField(value = "modifier",fill = FieldFill.INSERT_UPDATE)
	private Long modifier;
	/**
	 * 创建时间
	 */
	@TableField(value = "gmt_create",fill = FieldFill.INSERT)
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	@TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
	private Date gmtModified;
    /**
     * 注释
     */
    @TableField("remark")
    private String remark;
    /**
     * 是否删除 0否1是
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 会员类别（非会员：0，体验会员：1，充值会员2）
     */
    @TableField("type")
    private Integer type;
    /**
     * 会员名称
     */
    @TableField("name")
    private String name;
    /**
     * 会员周期（单位：天）
     */
    @TableField("period")
    private Integer period;
    /**
     * 免维保（数）
     */
    @TableField("maintenance_free_num")
    private Integer maintenanceFreeNum;
    /**
     * 免排放（数）
     */
    @TableField("emission_free_num")
    private Integer emissionFreeNum;
    /**
     * 免出险（数）
     */
    @TableField("insurance_free_num")
    private Integer insuranceFreeNum;
    /**
     * 免车型识别(数)
     */
    @TableField("car_model_free_num")
    private Integer carModelFreeNum;
    /**
     * 免快速评估(数)
     */
    @TableField("evaluate_free_num")
    private Integer evaluateFreeNum;
    /**
     * 维保(元/次):
     */
    @TableField("maintenance_price")
    private BigDecimal maintenancePrice;
    /**
     * 排放(元/次):
     */
    @TableField("emission_price")
    private BigDecimal emissionPrice;
    /**
     * 出险(元/次)
     */
    @TableField("insurance_price")
    private BigDecimal insurancePrice;
    /**
     * 车型识别(元/次)
     */
    @TableField("car_model_price")
    private BigDecimal carModelPrice;
    /**
     * 快速评估(元/次)
     */
    @TableField("evaluate_price")
    private BigDecimal evaluatePrice;
    /**
     * 是否展示（1：展示，默认，0：不展示）
     */
    @TableField("is_show")
    private Integer isShow;
    /**
     * 金额
     */
    @TableField("money")
    private BigDecimal money;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
