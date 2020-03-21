package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商家表
 * </p>
 *
 * @author mengyao
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class CsCarDealer extends Model<CsCarDealer> {

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
     * 经销商名称/营业执照名称(唯一）
     */
    @TableField("dealer_name")
    private String dealerName;
    /**
     * 经销商简称
     */
    @TableField("short_name")
    private String shortName;
    /**
     * 地址
     */
    @TableField("address")
    private String address;
    /**
     * 电话
     */
    @TableField("tel")
    private String tel;
    /**
     * 联系人姓名
     */
    @TableField("contact_name")
    private String contactName;
    /**
     * 商家所在地
     */
    @TableField("sys_area_id")
    private Long sysAreaId;
    /**
     * 成立日期
     */
    @TableField("establish_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date establishTime;
    /**
     * 营业执照编码（唯一）
     */
    @TableField("licence_no")
    private String licenceNo;
    /**
     * 状态（-1：申请拒绝，0：已申请，1：申请通过）
     */
    @TableField("status")
    private Integer status;
    /**
     * 审核明细，追加json格式存储（审核人/审核时间/审核操作/审核备注）
     */
    @TableField("audit_detail")
    private String auditDetail;
    /**
     * 是否诚信联盟（0：不是，1：是）
     */
    @TableField("credit_union")
    private Integer creditUnion;
    /**
     * 绑定的用户id
     */
    @TableField("sys_user_id")
    private Long sysUserId;
    /**
     * 固定电话
     */
    @TableField("fix_phone")
    private String fixPhone;
    /**
     * 首字母
     */
    @TableField("car_dearler_initial")
    private String carDearlerInitial;
    /**
     * 拼音
     */
    @TableField("car_dearler_pinyin")
    private String carDearlerPinyin;
    /**
     * 营业执照文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 营业执照分组名
     */
    @TableField("file_group")
    private String fileGroup;
    /**
     * 营业执照文件路径
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 最后一个审核时间
     */
    @TableField("last_audit_time")
    private Date lastAuditTime;
    /**
     * 最后一次审核用户id
     */
    @TableField("last_audit_user_id")
    private Long lastAuditUserId;
    /**
     * 最后一次审核用户名称
     */
    @TableField("last_audit_login_name")
    private String lastAuditLoginName;
    /**
     * 最后一次审核用户名称
     */
    @TableField("is_enabled")
    private Integer isEnabled;

	/**
	 * 法人身份证正面照文件名
	 */
	@TableField(value = "card_front_file_name")
	private String cardFrontFileName;

	/**
	 * 法人身份证正面照分组名
	 */
	@TableField(value = "card_front_file_group")
	private String cardFrontFileGroup;

	/**
	 * 法人身份证正面照文件路径
	 */
	@TableField(value = "card_front_file_path")
	private String cardFrontFilePath;
	/**
	 * 交易收款方式（1：先打款后过户，0：先过户后打款，默认0）
	 */
	@TableField(value = "payment_type")
	private Integer paymentType;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
