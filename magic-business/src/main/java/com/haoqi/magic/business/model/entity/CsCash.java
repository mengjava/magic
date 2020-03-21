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
 * 提现管理表
 * </p>
 *
 * @author mengyao
 * @since 2019-12-23
 */
@Data
@Accessors(chain = true)
public class CsCash extends Model<CsCash> {

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
     * 提现金额
     */
    @TableField("money")
    private BigDecimal money;
    /**
     * 提现人
     */
    @TableField("cash_user")
    private String cashUser;
    /**
     * 提现user_id
     */
    @TableField("cash_user_id")
    private Long cashUserId;
    /**
     * 提现审核人id
     */
    @TableField("cash_audit_user_id")
    private Long cashAuditUserId;
    /**
     * 提现审核人
     */
    @TableField("cash_audit_user")
    private String cashAuditUser;
    /**
     * 提现审核时间
     */
    @TableField("cash_audit_time")
    private Date cashAuditTime;
    /**
     * 提现审核状态(1:通过，2拒绝，0默认）
     */
    @TableField("cash_audit_status")
    private Integer cashAuditStatus;
    /**
     * 提现审核备注
     */
    @TableField("cash_audit_remark")
    private String cashAuditRemark;
    /**
     * 【冗余】银行名称
     */
    @TableField("bank_name")
    private String bankName;
    /**
     * 【冗余】银行卡号
     */
    @TableField("bank_card_no")
    private String bankCardNo;
    /**
     * 【冗余】户名
     */
    @TableField("bank_user_name")
    private String bankUserName;
    /**
     * 【冗余】手机号
     */
    @TableField("bank_user_tel")
    private String bankUserTel;
    /**
     * 【冗余】身份证号（非必填）
     */
    @TableField("bank_user_card_no")
    private String bankUserCardNo;

	/**
	 * 【冗余】账单明细表里的交易流水号
	 */
    @TableField("serial_no")
    private String serialNo;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
