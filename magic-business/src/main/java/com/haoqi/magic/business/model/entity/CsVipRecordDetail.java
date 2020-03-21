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
 * 用户会员查询记录表
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@Data
@Accessors(chain = true)
public class CsVipRecordDetail extends Model<CsVipRecordDetail> {

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
     * "查询服务项名称（1维保，2排放，3出险，4车型识别，5快速评估）
     */
    @TableField("service_item_name")
    private String serviceItemName;
    /**
     * 金额
     */
    @TableField("money")
    private BigDecimal money;
    /**
     * 【冗余】用户名
     */
    @TableField("username")
    private String username;
    /**
     * 状态（1：成功，默认，0：失败）
     */
    @TableField("status")
    private Integer status;
    /**
     * 第三方返回结果（维保URL，出险URL，排放，快速评估价格，车型识别结果）
     */
    @TableField("result")
    private String result;
    /**
     * vin
     */
    @TableField("vin")
    private String vin;
    /**
     * 【冗余】用户Id
     */
    @TableField("sys_user_id")
    private Long sysUserId;
    /**
     * 成本价
     */
    @TableField("cost_price")
    private BigDecimal costPrice;
    /**
     * 三方唯一编码
     */
    @TableField("outer_no")
    private String outerNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
