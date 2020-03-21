package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 筛选管理表
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
@Data
@Accessors(chain = true)
public class CsFilter extends Model<CsFilter> {

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
     * 筛选名称
     */
    @TableField("filter_name")
    private String filterName;
    /**
     * 1车系，2品牌，3价格
     */
    @TableField("filter_type")
    private Integer filterType;
    /**
     * 排序号
     */
    @TableField("order_no")
    private Integer orderNo;
    /**
     * 品牌id
     */
    @TableField("sys_car_brand_id")
    private Long sysCarBrandId;
    /**
     * 【冗余】品牌名称
     */
    @TableField("sys_car_brand_name")
    private String sysCarBrandName;
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
     * 车系id
     */
    @TableField("sys_car_series")
    private Long sysCarSeries;
    /**
     * 【冗余】车系名称
     */
    @TableField("sys_car_series_name")
    private String sysCarSeriesName;
    /**
     * 最小价格（单位：万元）
     */
    @TableField("min_price")
    private BigDecimal minPrice;
    /**
     * 最大价格（单位：万元）
     */
    @TableField("max_price")
    private BigDecimal maxPrice;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
