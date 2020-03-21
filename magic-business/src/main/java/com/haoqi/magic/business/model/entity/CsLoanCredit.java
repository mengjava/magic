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
 * 分期表
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class CsLoanCredit extends Model<CsLoanCredit> {

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
     * 姓名
     */
    @TableField("customer_name")
    private String customerName;
    /**
     * 客户 手机号
     */
    @TableField("customer_tel")
    private String customerTel;
    /**
     * 身份证
     */
    @TableField("card_no")
    private String cardNo;
    /**
     * 上牌地
     */
    @TableField("sys_area_id")
    private Long sysAreaId;

    /**
     * 【冗余字段】上牌地名称
     */
    @TableField("sys_area_name")
    private String sysAreaName;

    /**
     * 【来源于数据字典】工作情况
     */
    @TableField("work_code")
    private String workCode;
    /**
     * 【来源于数据字典】收入情况
     */
    @TableField("income_code")
    private String incomeCode;
    /**
     * 经销商id
     */
    @TableField("cs_car_dealer_id")
    private Long csCarDealerId;
    /**
     * 状态（1：已提交/2:已处理）
     */
    @TableField("status")
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
