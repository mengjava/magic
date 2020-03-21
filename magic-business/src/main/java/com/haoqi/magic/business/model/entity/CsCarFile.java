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
 * 车辆图片信息
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class CsCarFile extends Model<CsCarFile> {

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
     * 文件名
     */
    @TableField("file_name")
    private String fileName;
    /**
     * 分组名
     */
    @TableField("file_group")
    private String fileGroup;
    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;
    /**
     * 缩略文件名
     */
    @TableField("icon_file_name")
    private String iconFileName;
    /**
     * 缩略分组名
     */
    @TableField("icon_file_group")
    private String iconFileGroup;
    /**
     * 缩略文件路径
     */
    @TableField("icon_file_path")
    private String iconFilePath;
    /**
     * 【来自数据字典】文件类型code（N_,Y_,N和Y开头的控制是否必填）
     */
    @TableField("file_child_type_code")
    private String fileChildTypeCode;
    /**
     * 1:基本图片，2手续图片
     */
    @TableField("file_type")
    private Integer fileType;
    /**
     * 车辆基础信息表id
     */
    @TableField("cs_car_info_id")
    private Long csCarInfoId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
