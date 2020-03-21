package com.haoqi.magic.system.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统文件版本详情记录表
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class SysFileVersionDetail extends Model<SysFileVersionDetail> {

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
     * 资源文件唯一标识
     */
    @TableField("file")
    private String file;
    /**
     * 资源名称
     */
    @TableField("name")
    private String name;
    /**
     * 请求文件地址
     */
    @TableField("url")
    private String url;
    /**
     * 文件内容MD5字符串
     */
    @TableField("file_md5")
    private String fileMd5;
    /**
     * 分组名称
     */
    @TableField("group_name")
    private String groupName;
    /**
     * 文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 文件ID
     */
    @TableField("file_id")
    private String fileId;
    /**
     * 来源于哪张表-表名
     */
    @TableField("source_name")
    private String sourceName;
    /**
     * 启动文件列表MD5
     */
    @TableField("group_type")
    private String groupType;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
