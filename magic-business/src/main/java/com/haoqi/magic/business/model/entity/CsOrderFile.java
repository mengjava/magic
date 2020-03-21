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
 * 附件
 * </p>
 *
 * @author yanhao
 * @since 2019-12-11
 */
@Data
@Accessors(chain = true)
public class CsOrderFile extends Model<CsOrderFile> {

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
     * 订单ID
     */
    @TableField("cs_car_order_id")
    private Long csCarOrderId;
    /**
     * 附件类型（1：过户，2：买家打款，3，卖家打款）
     */
    @TableField("type")
    private Integer type;
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
