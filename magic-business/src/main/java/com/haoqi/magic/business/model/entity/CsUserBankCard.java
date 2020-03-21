package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 银行卡表
 * </p>
 *
 * @author mengyao
 * @since 2019-12-16
 */
@Data
@Accessors(chain = true)
public class CsUserBankCard extends Model<CsUserBankCard> {

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
     * 用户Id
     */
    @TableField("sys_user_id")
    private Long sysUserId;
    /**
     * 【数据字典】银行类型code
     */
    @TableField("bank_code")
    private String bankCode;
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
