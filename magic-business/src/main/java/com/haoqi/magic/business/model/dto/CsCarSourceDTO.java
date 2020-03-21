package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ClassName:com.haoqi.magic.business.model.dto <br/>
 * Function: <br/>
 * Date:     2019/5/8 10:38 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsCarSourceDTO implements Serializable {
    private static final long serialVersionUID = 3016152030403648903L;


    private Long id;

    //车辆品牌名称
    private String sysCarModelName;

    //表显里程（万公里）
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal instrumentShowDistance;

    //初始登记日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date initDate;

    //排量
    @JsonSerialize(using = ToStringSerializer.class)
    private Double displacement;
    private String displacementType;

    //【来自数据字典】排放标准
    private String emissionStandardCode;
    private String emissionStandardCodeName;

    //变数箱（档位）
    private String gearBoxCode;
    private String gearBoxCodeName;

    //【来自数据字典】颜色
    private String colorCode;
    private String colorCodeName;

    //零售价格（元）
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal price;

    //批发价格（元）
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal wholesalePrice;

    //新车指导价格（元）
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal suggestPrice;

    //状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
    private Integer publishStatus;
    private String publishStatusName;

    //发布时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date publishTime;

    //审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date auditTime;

    //车辆编号
    private String carNo;

}
