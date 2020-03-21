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
 * 财务打款表
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
@Data
@Accessors(chain = true)
public class CsFinancePayMoney extends Model<CsFinancePayMoney> {

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
     * 订单ID
     */
    @TableField("cs_car_order_id")
    private Long csCarOrderId;
    /**
     * 打款/收款 日期
     */
    @TableField("pay_date")
    private Date payDate;
    /**
     * 打款金额
     */
    @TableField("pay_money")
    private BigDecimal payMoney;
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
     * 类型（1：买家车款，2卖家车款，3收款)
     */
    @TableField("pay_money_type")
    private Integer payMoneyType;
    /**
     * 【收款专用】收款项类型（1车款+服务费，0卖家退车款）
     */
    @TableField("receive_item_type")
    private Integer receiveItemType;
    /**
     * 【冗余】打款操作人
     */
    @TableField("operator_user")
    private String operatorUser;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
