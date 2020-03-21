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
 * 争议项管理
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
@Data
@Accessors(chain = true)
public class CsDisputeItem extends Model<CsDisputeItem> {

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
     * 争议项名称
     */
    @TableField("item_name")
    private String itemName;
    /**
     * 类型：1买家，2卖家，3PC,默认为1
     */
    @TableField("type")
    private Integer type;
    /**
     * 是否支持文本（0：不支持，1：支持，默认为0）
     */
    @TableField("text_flag")
    private Integer textFlag;
    /**
     * 是否复检（0：不复检，1：复检，默认为0）【只有主争议项才有该值】
     */
    @TableField("recheck_flag")
    private Integer recheckFlag;
    /**
     * 父争议性id
     */
    @TableField("parent_id")
    private Long parentId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
