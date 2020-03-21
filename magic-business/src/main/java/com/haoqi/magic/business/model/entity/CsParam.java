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
 * 自定义参数管理表
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
@Data
@Accessors(chain = true)
public class CsParam extends Model<CsParam> {

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
     * 自定义参数名称
     */
    @TableField("param_name")
    private String paramName;
    /**
     * 最小零售价格（元）
     */
    @TableField("min_price")
    private BigDecimal minPrice;
    /**
     * 最大零售价格（元）
     */
    @TableField("max_price")
    private BigDecimal maxPrice;
    /**
     * 最小行驶里程（万公里）
     */
    @TableField("min_travel_distance")
    private BigDecimal minTravelDistance;
    /**
     * 最大行驶里程（万公里）
     */
    @TableField("max_travel_distance")
    private BigDecimal maxTravelDistance;
    /**
     * 车辆年限(单位：年）
     */
    @TableField("car_age")
    private Integer carAge;
    /**
     * 【数据字典】车辆类型
     */
    @TableField("car_type_code")
    private String carTypeCode;
    /**
     * 审核通过时间（单位:天）
     */
    @TableField("audit_time")
    private Integer auditTime;
    /**
     * 过户次数
     */
    @TableField("transfer_num")
    private Integer transferNum;
    /**
     * 是否诚信联盟（0：不是，1：是）
     */
    @TableField("credit_union")
    private Integer creditUnion;
    /**
     * 拼接sql字符串
     */
    @TableField("sql_str")
    private String sqlStr;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
