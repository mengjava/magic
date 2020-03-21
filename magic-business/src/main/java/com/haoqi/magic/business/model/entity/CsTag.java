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
 * 标签封装展示管理
 * </p>
 *
 * @author huming
 * @since 2019-04-30
 */
@Data
@Accessors(chain = true)
public class CsTag extends Model<CsTag> {

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
     * 1筛选里的标签/2详情/3今日推荐
     */
    @TableField("type")
    private Integer type;
    /**
     * 名称
     */
    @TableField("tag_name")
    private String tagName;
    /**
     * 关联标签
     */
    @TableField("cs_param_id")
    private Long csParamId;
    /**
     * 排序
     */
    @TableField("order_no")
    private Integer orderNo;
    /**
     * 文件名（详情专用）
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 分组名（详情专用）
     */
    @TableField("file_group")
    private String fileGroup;
    /**
     * 文件路径（详情专用）
     */
    @TableField("file_path")
    private String filePath;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
