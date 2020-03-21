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
 * 服务费设置表
 * </p>
 *
 * @author mengyao
 * @since 2019-12-18
 */
@Data
@Accessors(chain = true)
public class CsServiceFee extends Model<CsServiceFee> {

    private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	/**
	 * 创建人
	 */
	@TableField(value = "creator", fill = FieldFill.INSERT)
	private Long creator;
	/**
	 * 修改人
	 */
	@TableField(value = "modifier", fill = FieldFill.INSERT_UPDATE)
	private Long modifier;
	/**
	 * 创建时间
	 */
	@TableField(value = "gmt_create", fill = FieldFill.INSERT)
	private Date gmtCreate;
	/**
	 * 修改时间
	 */
	@TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
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
     * 区域
     */
    @TableField("sys_area_id")
    private Long sysAreaId;
    /**
     * 最小价格
     */
    @TableField("min_money")
    private BigDecimal minMoney;
    /**
     * 最大价格
     */
    @TableField("max_money")
    private BigDecimal maxMoney;
    /**
     * 服务费比例
     */
    @TableField("fee_ratio")
    private BigDecimal feeRatio;
    /**
     * 单笔服务费
     */
    @TableField("fee_money")
    private BigDecimal feeMoney;
    /**
     * 1：单笔/0服务费比例，默认0
     */
    @TableField("type")
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
