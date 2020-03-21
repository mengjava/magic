package com.haoqi.magic.system.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 车型表
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Data
@Accessors(chain = true)
public class SysCarModel extends Model<SysCarModel> {

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
     * 车辆品牌id
     */
    @TableField("brand_id")
    private Integer brandId;
    /**
     * 车辆品牌名称
     */
    @TableField("brand_name")
    private String brandName;
    /**
     * 车系id
     */
    @TableField("series_id")
    private Integer seriesId;
    /**
     * 车系名称
     */
    @TableField("series_name")
    private String seriesName;
    /**
     * 车系分组名
     */
    @TableField("series_group_name")
    private String seriesGroupName;
    /**
     * 车型id
     */
    @TableField("model_id")
    private Integer modelId;
    /**
     * 车型名称
     */
    @TableField("model_name")
    private String modelName;
    /**
     * 价格（单位：万元）
     */
    @TableField("price")
    private BigDecimal price;
    /**
     * 排量
     */
    @TableField("liter")
    private String liter;
    /**
     * 手动/自动/手自一体
     */
    @TableField("gear_type")
    private String gearType;
    /**
     * 年份
     */
    @TableField("model_year")
    private String modelYear;
    /**
     * 合资/进口/国产
     */
    @TableField("maker_type")
    private String makerType;
    /**
     * 国标
     */
    @TableField("discharge_standard")
    private String dischargeStandard;
    /**
     * 座位数
     */
    @TableField("seat_number")
    private String seatNumber;
    /**
     * 上牌最小时间
     */
    @TableField("min_reg_year")
    private String minRegYear;
    /**
     * 上牌最大时间
     */
    @TableField("max_reg_year")
    private String maxRegYear;

    /***
     * 排放标准字段code
     */
    private String emissionStandardCode;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
