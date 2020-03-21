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
 * 车辆信息审核表
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
@Data
@Accessors(chain = true)
public class CsCarAudit extends Model<CsCarAudit> {

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
     * 车辆基础信息表id
     */
    @TableField("cs_car_info_id")
    private Long csCarInfoId;
    /**
     * 审核时间
     */
    @TableField("audit_time")
    private Date auditTime;
    /**
     * 1:通过。-1拒绝
     */
    @TableField("audit_status")
    private Integer auditStatus;
    /**
     * 审核用户id
     */
    @TableField("audit_user_id")
    private Long auditUserId;

    /**
     * 审核员帐号
     */
    @TableField("audit_login_name")
    private String auditLoginName;

    /**
     * 审核员姓名
     */
    @TableField("audit_user_name")
    private String auditUserName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
