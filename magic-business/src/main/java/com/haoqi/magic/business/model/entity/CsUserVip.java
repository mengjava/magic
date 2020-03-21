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
 * 用户会员关联表
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@Data
@Accessors(chain = true)
public class CsUserVip extends Model<CsUserVip> {

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
     * 会员id
     */
    @TableField("cs_vip_id")
    private Long csVipId;
    /**
     * 会员金额
     */
    @TableField("money")
    private BigDecimal money;
    /**
     * 到期时间
     */
    @TableField("expired_date")
    private Date expiredDate;
    /**
     * 【冗余】用户名
     */
    @TableField("username")
    private String username;
    /**
     * 开通时间
     */
    @TableField("begin_date")
    private Date beginDate;
    /**
     * 会员卡号
     */
    @TableField("vip_no")
    private String vipNo;
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
