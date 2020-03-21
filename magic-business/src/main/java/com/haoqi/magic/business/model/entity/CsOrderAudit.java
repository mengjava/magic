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
 * 审核信息表
 * </p>
 *
 * @author twg
 * @since 2019-12-05
 */
@Data
@Accessors(chain = true)
public class CsOrderAudit extends Model<CsOrderAudit> {

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
     * 过户审核状态(1:通过，2拒绝，3待审，0默认）
     */
    @TableField("transfer_audit_status")
    private Integer transferAuditStatus;
    /**
     * 过户审核时间
     */
    @TableField("transfer_audit_time")
    private Date transferAuditTime;
    /**
     * 过户审核描述
     */
    @TableField("transfer_audit_remark")
    private String transferAuditRemark;
    /**
     * 过户审核人id
     */
    @TableField("transfer_audit_user_id")
    private Long transferAuditUserId;
    /**
     * 过户审核人
     */
    @TableField("transfer_audit_user")
    private String transferAuditUser;
    /**
     * 争议初审人id
     */
    @TableField("dispute_first_audit_user_id")
    private Long disputeFirstAuditUserId;
    /**
     * 争议初审人
     */
    @TableField("dispute_first_audit_user")
    private String disputeFirstAuditUser;
    /**
     * 争议初审时间
     */
    @TableField("dispute_first_audit_time")
    private Date disputeFirstAuditTime;
    /**
     * 争议初审备注
     */
    @TableField("dispute_first_audit_remark")
    private String disputeFirstAuditRemark;
    /**
     * 争议初审状态(1:通过，2拒绝，3 待审，0默认）
     */
    @TableField("dispute_first_audit_status")
    private Integer disputeFirstAuditStatus;
    /**
     * 争议终审人id
     */
    @TableField("dispute_finish_audit_user_id")
    private Long disputeFinishAuditUserId;
    /**
     * 争议终审人
     */
    @TableField("dispute_finish_audit_user")
    private String disputeFinishAuditUser;
    /**
     * 争议终审时间
     */
    @TableField("dispute_finish_audit_time")
    private Date disputeFinishAuditTime;
    /**
     * 争议终审备注
     */
    @TableField("dispute_finish_audit_remark")
    private String disputeFinishAuditRemark;
    /**
     * 争议终审状态(1:通过，2拒绝，3待审，0：默认）
     */
    @TableField("dispute_finish_audit_status")
    private Integer disputeFinishAuditStatus;
    /**
     * 撤销买入审核状态(1:通过，2拒绝，3待审，0默认）
     */
    @TableField("cancel_buy_audit_status")
    private Integer cancelBuyAuditStatus;
    /**
     * 撤销买入审核时间
     */
    @TableField("cancel_buy_audit_audit_time")
    private Date cancelBuyAuditAuditTime;
    /**
     * 撤销买入审核描述
     */
    @TableField("cancel_buy_audit_audit_remark")
    private String cancelBuyAuditAuditRemark;
    /**
     * 撤销买入审核人
     */
    @TableField("cancel_buy_audit_audit_user")
    private String cancelBuyAuditAuditUser;
    /**
     * 撤销买入审核人id
     */
    @TableField("cancel_buy_audit_audit_user_id")
    private Long cancelBuyAuditAuditUserId;

    /**
     * 争议审核json
     */
    @TableField("dispute_audit_json_content")
    private String disputeAuditJsonContent;

    /**
     * 全部审核json
     */
    @TableField("audit_json_content")
    private String auditJsonContent;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
