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
 * 争议表（每个订单多条争议记录）
 * </p>
 *
 * @author twg
 * @since 2019-12-12
 */
@Data
@Accessors(chain = true)
public class CsOrderDispute extends Model<CsOrderDispute> {

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
     * 订单ID
     */
    @TableField("cs_car_order_id")
    private Long csCarOrderId;
    /**
     * 争议项id
     */
    @TableField("cs_dispute_item_id")
    private Long csDisputeItemId;
    /**
     * 支持文本的项填值
     */
    @TableField("text_decs")
    private String textDecs;
    /**
     * 复检结果（1：属实，2：不属实，0，默认值）
     */
    @TableField("recheck_result")
    private Integer recheckResult;
    /**
     * 赔偿金额
     */
    @TableField("money")
    private BigDecimal money;
    /**
     * 争议项拼接展示字符串
     */
    @TableField("show_text")
    private String showText;
    /**
     * 争议项父级id
     */
    @TableField("cs_dispute_item_parent_id")
    private Long csDisputeItemParentId;
    /**
     * 是否复检（0：不复检，1：复检，默认为0）【只有主争议项才有该值】
     */
    @TableField("recheck_flag")
    private Integer recheckFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
