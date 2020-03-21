package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 账单明细表
 * </p>
 *
 * @author twg
 * @since 2019-12-02
 */
@Data
@Accessors(chain = true)
public class CsAccountDetail extends Model<CsAccountDetail> {

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
     * 账户id
     */
    @TableField("cs_account_id")
    private Long csAccountId;
    /**
     * 交易金额(单位：元）
     */
    @TableField("money")
    private BigDecimal money;
    /**
     * 交易类型1：维保查询，2排放查询，3出险查询，4车型识别查询，5快速评估查询，7车款，8复检，10冻结保证金，11，提现，12 开通会员，13余额充值， 20赔偿，21违约退车，22，协商平退
     */
    @TableField("trade_type")
    private Integer tradeType;
    /**
     * 收支类型，1:收入，2：支出
     */
    @TableField("type")
    private Integer type;
    /**
     * 交易状态1：成功，2：支付中, 3 未知，0待支付
     */
    @TableField("status")
    private Integer status;
    /**
     * 交易流水号
     */
    @TableField("serial_no")
    private String serialNo;
    /**
     * 三方交易流水号
     */
    @TableField("third_serial_no")
    private String thirdSerialNo;
    /**
     * 支付方式（1：微信，2支付宝,3...）
     */
    @TableField("pay_type")
    private Long payType;
    /**
     * 交易说明
     */
    @TableField("trade_remark")
    private String tradeRemark;
    /**
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;
    /**
     * 银行卡号
     */
    @TableField("bank_card_no")
    private String bankCardNo;
    /**
     * 户名
     */
    @TableField("bank_user_name")
    private String bankUserName;
    /**
     * 手机号
     */
    @TableField("bank_user_tel")
    private String bankUserTel;
    /**
     * 身份证号（非必填）
     */
    @TableField("bank_user_card_no")
    private String bankUserCardNo;
    /**
     * 支付时间
     */
    @TableField("pay_time")
    private Date payTime;

    /**
     * 订单
     */
    @TableField("cs_car_order_id")
    private Long csCarOrderId;

    /**
     * 支付方式描述
     */
    @TableField("pay_type_desc")
    private String payTypeDesc;

    /**
     * 会员卡id
     */
    @TableField("cs_vip_id")
    private Long vipId;

    /**
     * 用户id
     */
    @TableField("sys_user_id")
    private Long sysUserId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
