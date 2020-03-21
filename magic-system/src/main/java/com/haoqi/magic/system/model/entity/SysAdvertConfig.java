package com.haoqi.magic.system.model.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告配置表
 * </p>
 *
 * @author mengyao
 * @since 2019-04-25
 */
@Data
@Accessors(chain = true)
public class SysAdvertConfig extends Model<SysAdvertConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建人
     */
    @TableField("creator")
    private Long creator;
    /**
     * 修改人
     */
    @TableField("modifier")
    private Long modifier;
    /**
     * 是否删除 0否1是
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 创建时间
     */
    @TableField("gmt_create")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField("gmt_modified")
    private Date gmtModified;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * 标题
     */
    @TableField("title")
    private String title;
    /**
     * 【来自数据字典】投放位置
     */
    @TableField("position_code")
    private String positionCode;
    /**
     * 图片文件路径
     */
    @TableField("picture_path")
    private String picturePath;
    /**
     * 图片文件名称
     */
    @TableField("picture_name")
    private String pictureName;
    /**
     * 广告超级链接
     */
    @TableField("link_url")
    private String linkUrl;
    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;
    /**
     * 状态（1：上架，2：下架）
     */
    @TableField("status")
    private Integer status;
    /**
     * 跳转类型（1：网页，2：本地app页面,0不跳转,网页内编辑）
     */
    @TableField("jump_type")
    private Integer jumpType;
    /**
     * 文本编辑内容
     */
    @TableField("content")
    private String content;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
