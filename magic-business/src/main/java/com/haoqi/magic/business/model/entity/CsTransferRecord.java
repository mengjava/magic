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
 * 调拨记录表
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class CsTransferRecord extends Model<CsTransferRecord> {

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
     * 车辆基础信息表id
     */
    @TableField("cs_car_info_id")
    private Long csCarInfoId;
    /**
     * 调拨经销商Id(from)
     */
    @TableField("cs_car_dearler_id_from")
    private Long csCarDearlerIdFrom;
    /**
     * 【冗余】调拨经销商名称(from)
     */
    @TableField("cs_car_dearler_name_from")
    private String csCarDearlerNameFrom;
    /**
     * 【冗余】调拨经销商名称(to)
     */
    @TableField("cs_car_dearler_name_to")
    private String csCarDearlerNameTo;
    /**
     * 调拨经销商Id(to)
     */
    @TableField("cs_car_dearler_id_to")
    private Long csCarDearlerIdTo;
    /**
     * 调拨状态（1：已申请，2：申请通过，-1：申请拒绝，取消调拨-2）
     */
    @TableField("transfer_status")
    private Integer transferStatus;
    /**
     * 调拨发起用户id
     */
    @TableField("transfer_user_id")
    private Long transferUserId;
    /**
     * 调拨审核用户id
     */
    @TableField("transfer_audit_user_id")
    private Long transferAuditUserId;
    /**
     * 调拨发起时间
     */
    @TableField("transfer_time")
    private Date transferTime;
    /**
     * 调拨审核时间
     */
    @TableField("transfer_audit_time")
    private Date transferAuditTime;
    /**
     * 调拨取消时间
     */
    @TableField("transfer_cancel_time")
    private Date transferCancelTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
