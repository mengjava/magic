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
 * Date:     2019/5/7 11:49 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsTransferRecordDTO implements Serializable {
    private static final long serialVersionUID = -4210319322203941925L;

    private Long id;

    //车型车款
    private String sysCarModelName;

    //表显里程（万公里）
    private BigDecimal instrumentShowDistance;

    //初始登记日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date initDate;

    //排量
    @JsonSerialize(using = ToStringSerializer.class)
    private Double displacement;

    private String displacementType;

    //变数箱（档位）
    private String gearBoxCode;
    private String gearBoxCodeName;

    //【来自数据字典】排放标准
    private String emissionStandardCode;
    private String emissionStandardCodeName;

    //【来自数据字典】颜色
    private String colorCode;
    private String colorCodeName;

    //零售价格（元）
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal price;

    //批发价格（元）
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal wholesalePrice;

    //车辆ID
    private Long csCarInfoId;

    //车商ID（来源）
    private Long csCarDearlerIdFrom;
    //车商名称（来源）
    private String csCarDearlerNameFrom;

    //车商ID（接收）
    private Long csCarDearlerIdTo;
    //车商名称（接收）
    private String csCarDearlerNameTo;

    //调拨发起时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transferTime;

    //调拨审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transferAuditTime;

    //调拨状态（1：已申请，2：申请通过，-1：申请拒绝，取消调拨-2）
    private Integer transferStatus;

    //状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
    private Integer publishStatus;

    //车源状态名称
    private String publishStatusName;

    //车辆编号
    private String carNo;
}
