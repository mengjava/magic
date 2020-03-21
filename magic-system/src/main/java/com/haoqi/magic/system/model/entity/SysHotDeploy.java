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
 * APP热更新管理
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
@Data
@Accessors(chain = true)
public class SysHotDeploy extends Model<SysHotDeploy> {

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
     * 是否删除 0否1是
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    /**
     * APP名称
     */
    @TableField("name")
    private String name;
    /**
     * APP类型 1:IOS 2:安卓,3:pad
     */
    @TableField("type")
    private String type;
    /**
     * 版本号
     */
    @TableField("version")
    private String version;
    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 文件分组
     */
    @TableField("file_group")
    private String fileGroup;
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 文件md5(文件名+版本+版本号md5)
     */
    @TableField("md5")
    private String md5;
    /**
     * 请求文件地址
     */
    @TableField("url")
    private String url;
    /**
     * 项目标识（来自数据字典）
     */
    @TableField("project_code")
    private String projectCode;
    /**
     * app类型唯一标识(项目名生成)
     */
    @TableField("app_key")
    private String appKey;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
