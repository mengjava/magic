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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 客户服务（咨询）表
 * </p>
 *
 * @author mengyao
 * @since 2019-05-14
 */
@Data
@Accessors(chain = true)
public class CsCustomService extends Model<CsCustomService> {

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
     * 意向经销商id
     */
    @TableField("cs_buy_dealer_id")
    private Long csBuyDealerId;
    /**
     * 处理状态（1：已处理，0:未处理，默认为0）
     */
    @TableField("process_status")
    private Integer processStatus;
    /**
     * 处理时间
     */
    @TableField("process_time")
    private Date processTime;
    /**
     * 处理备注意见
     */
    @TableField("process_remark")
    private String processRemark;
    /**
     * 处理备注意见
     */
    @TableField("intention_time")
    private Date intentionTime;
	/**
	 * 处理用户id
	 */
    @TableField("process_user_id")
    private Long processUserId;
	/**
	 * 【冗余字段】处理用户名称
	 */
    @TableField("process_login_name")
    private String processLoginName;
	/**
	 * 【冗余字段】处理用户姓名
	 */
    @TableField("process_user_name")
    private String processUserName;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
