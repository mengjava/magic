package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 财务打款动作表
 * </p>
 *
 * @author yanhao
 * @since 2020-01-17
 */
@Data
@Accessors(chain = true)
@TableName("cs_finance_pay_action")
public class CsFinancePayAction extends Model<CsFinancePayAction> {

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
     * 打款状态（1：已打款，0：未打款，默认为0）
     */
    @TableField("payment_status")
    private Integer paymentStatus;
    /**
     * 打款时间
     */
    @TableField("payment_time")
    private Date paymentTime;
    /**
     * 打款金额
     */
    @TableField("pay_money")
    private BigDecimal payMoney;
    /**
     * 占位人
     */
    @TableField("operationing_user")
    private String operationingUser;
    /**
     * 占位时间
     */
    @TableField("operationing_time")
    private Date operationingTime;
    /**
     * 订单ID
     */
    @TableField("cs_car_order_id")
    private Long csCarOrderId;
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
     * 款项类型（1车款+服务费 + 复检费 - 过户费，0车款-检测费, 3赔偿金）
     */
    @TableField("pay_type")
    private Integer payType;
    /**
     * 【冗余】交易收款方式（1：先打款后过户，0：先过户后打款，默认0）
     */
    @TableField("payment_type")
    private Integer paymentType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
