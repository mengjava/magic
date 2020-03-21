package com.haoqi.magic.system.model.entity;

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
 * 系统用户表
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@Data
@Accessors(chain = true)
public class SysUser extends Model<SysUser> {

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
     * 账号
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 密码
     */
    @TableField("password")
    private String password;
    /**
     * 姓名
     */
    @TableField("username")
    private String username;
    /**
     * 用户状态（是否启用0-正常，1-失效，2-过期，3-锁定，4-密码过期）
     */
    @TableField("is_enabled")
    private Integer isEnabled;
    /**
     * 手机号码
     */
    @TableField("tel")
    private String tel;
    /**
     * 用户类型（1:超级管理员，2：普通管理，3：检测员，4：商家）
     */
    @TableField("type")
    private Integer type;
    /**
     * 盐值
     */
    @TableField("salt")
    private String salt;
    /**
     * 头像url地址
     */
    @TableField("head_image_url")
    private String headImageUrl;

    /**
     * 推荐人
     */
    @TableField("introducer")
    private String introducer;

    /**
     * vip标识（1：是，0：否，默认为0）
     */
    @TableField("vip_flag")
    private Integer vipFlag;

    /**
     * 支付密码
     */
    @TableField("pay_password")
    private String payPassword;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
