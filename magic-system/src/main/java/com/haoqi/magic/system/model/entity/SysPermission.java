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
 * 权限表
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@Data
@Accessors(chain = true)
public class SysPermission extends Model<SysPermission> {

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
     * 权限名称
     */
    @TableField("menu_name")
    private String menuName;
    /**
     * 0菜单1权限
     */
    @TableField("menu_type")
    private Integer menuType;
    /**
     * 请求地址
     */
    @TableField("menu_url")
    private String menuUrl;
    /**
     * 按钮请求权限码
     */
    @TableField("permission")
    private String permission;
    /**
     * 父Id
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 样式
     */
    @TableField("menu_icon")
    private String menuIcon;
    /**
     * 排序
     */
    @TableField("menu_sort")
    private Integer menuSort;
    /**
     * 是否显示
     */
    @TableField("is_show")
    private Integer isShow;

    /**
     * 菜单权限请求方法:GET,POST,PUT,DELETE
     */
    @TableField("menu_method")
    private String menuMethod;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
