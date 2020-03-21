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
 * 车辆检测项
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class CsCarCheckItem extends Model<CsCarCheckItem> {

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
     * 检测项名称
     */
    @TableField("name")
    private String name;
    /**
     * 检测项code
     */
    @TableField("code")
    private String code;
    /**
     * 父类检测项id
     */
    @TableField("parent_id")
    private Long parentId;
    /**
     * 排序
     */
    @TableField("order_no")
    private Integer orderNo;
    /**
     * 1事故检测，2外观检测，3检测信息
     */
    @TableField("type")
    private Integer type;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
