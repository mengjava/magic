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
 * 品牌表
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Data
@Accessors(chain = true)
public class SysCarBrand extends Model<SysCarBrand> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 公司id
     */
    @TableField("comp_id")
    private Long compId;
    /**
     * 公司名
     */
    @TableField("comp_name")
    private String compName;
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
     * 品牌id
     */
    @TableField("brand_id")
    private Integer brandId;
    /**
     * 品牌名称
     */
    @TableField("brand_name")
    private String brandName;
    /**
     * 品牌首字母
     */
    @TableField("brand_initial")
    private String brandInitial;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
