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
 * 城市区域表
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Data
@Accessors(chain = true)
public class SysArea extends Model<SysArea> {

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
     * 省份名称
     */
    @TableField("province_name")
    private String provinceName;
    /**
     * 地市名称
     */
    @TableField("city_name")
    private String cityName;
    /**
     * 省份code
     */
    @TableField("province_code")
    private String provinceCode;
    /**
     * 地市code
     */
    @TableField("city_code")
    private String cityCode;
    /**
     * 地域级别（1：一级省份，2：二级地市）
     */
    @TableField("level")
    private Integer level;
    /**
     * 大区id
     */
    @TableField("sys_region_id")
    private Long sysRegionId;
    /**
     * 城市首字母
     */
    @TableField("city_initial")
    private String cityInitial;
    /**
     * 热门城市 （1：是，0：否，默认为0）
     */
    @TableField("hot_city")
    private Integer hotCity;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
