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
 * 数据字典表
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Data
@Accessors(chain = true)
public class SysDict extends Model<SysDict> {

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
     * 是否删除 0否1是
     */
    @TableField("is_deleted")
    private Integer isDeleted;
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
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * key
     */
    @TableField("keyworld")
    private String keyworld;
    /**
     * value
     */
    @TableField("value_desc")
    private String valueDesc;
    /**
     * 父级id
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 数据字典类级别
     */
    @TableField("class_level")
    private Integer classLevel;
    /**
     * 排序
     */
    @TableField("class_order")
    private Integer classOrder;
    /**
     * 类别
     */
    @TableField("class")
    private String classType;

    @TableField("class_flag")
    private String classFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
