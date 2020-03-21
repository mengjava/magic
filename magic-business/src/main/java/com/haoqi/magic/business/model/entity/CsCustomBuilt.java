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
 * 客户定制表
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@Data
@Accessors(chain = true)
public class CsCustomBuilt extends Model<CsCustomBuilt> {

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
     * 车龄(单位：年）
     */
    @TableField("age")
    private Integer age;
    /**
     * 【来自数据字典】颜色
     */
    @TableField("color_code")
    private String colorCode;
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
    /**
     * 【来自数据字典】排放标准
     */
    @TableField("emission_standard_code")
    private String emissionStandardCode;
    /**
     * 行驶里程（万公里）
     */
    @TableField("travel_distance")
    private String travelDistance;
    /**
     * 定制经销商id
     */
    @TableField("cs_car_dealer_id")
    private Long csCarDealerId;
    /**
     * 定制提交时间
     */
    @TableField("custom_built_time")
    private Date customBuiltTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
