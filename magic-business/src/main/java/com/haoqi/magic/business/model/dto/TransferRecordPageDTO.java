package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.haoqi.rigger.core.serializer.DateJsonSerializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by yanhao on 2019/8/15.
 */
@Data
public class TransferRecordPageDTO implements Serializable {


    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "编号")
    private String carNo;

    @ApiModelProperty(value = "车型名称")
    private String sysCarModelName;
    /**
     * 【冗余字段】车辆品牌名称
     */
    @ApiModelProperty(value = "车辆品牌名称")
    private String sysCarBrandName;
    /**
     * 【冗余字段】车系名称
     */
    @ApiModelProperty(value = "车系名称")
    private String sysCarSeriesName;
    /**
     * 车商
     */
    @ApiModelProperty(value = "车商id")
    @NotNull(message = "车商不能为空")
    private Long csCarDealerId;

    @ApiModelProperty(value = "车商")
    private String carDealer;
    /**
     * 车型车款
     */
    @ApiModelProperty(value = "车型车款id")
    @NotNull(message = "车型车款不能为空")
    private Long sysCarModelId;
    /**
     * vin码
     */
    @ApiModelProperty(value = "vin")
    private String vin;
    /**
     * 初始登记日期
     */
    @ApiModelProperty(value = "初始登记日期")
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date initDate;

    /**
     * 使用性质（1营运/0非营运)
     */
    @ApiModelProperty(value = "使用性质（1运营 2非运营 、3营转非 、4租赁 、5教练车 、6家用车 、7出租车 、8公务车、9其他 ）")
    @NotNull(message = "使用性质不能为空")
    private Integer useType;

    /**
     * 行驶里程（万公里）
     */
    @ApiModelProperty(value = "行驶里程")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal travelDistance;
    /**
     * 表显里程（万公里）
     */
    @ApiModelProperty(value = "表显里程")
    private BigDecimal instrumentShowDistance;
    /**
     * 出厂日期
     */
    @ApiModelProperty(value = "出厂日期")
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date productDate;

    /**
     * 车牌号
     */
    @ApiModelProperty(value = "车牌号")
    @NotBlank(message = "车牌号不能为空")
    private String plateNo;

    @ApiModelProperty(value = "车牌号名")
    private String plateNoName;
    /**
     * 【来自数据字典】排放标准
     */
    @ApiModelProperty(value = "【来自数据字典】排放标准")
    private String emissionStandardCode;

    @ApiModelProperty(value = "排放标准名")
    private String emissionStandardCodeName;
    /**
     * 汽车厂商
     */
    @ApiModelProperty(value = "汽车厂商")
    @NotBlank(message = "汽车厂商不能为空")
    private String carFactory;
    /**
     * 排量
     */
    @ApiModelProperty(value = "排量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Double displacement;

    @ApiModelProperty(value = "排量类型 : 0L 1T")
    private String displacementType;

    /**
     * 【来自数据字典】颜色
     */
    @ApiModelProperty(value = "【来自数据字典】颜色")
    private String colorCode;

    @ApiModelProperty(value = "颜色名")
    private String colorCodeName;
    /**
     * 变数箱
     */
    private String gearBoxCode;
    /**
     * 整车型号
     */
    @ApiModelProperty(value = "整车型号")
    private String carVersion;
    /**
     * 发动机号
     */
    @ApiModelProperty(value = "发动机号")
    @NotBlank(message = "发动机号不能为空")
    private String engineNo;

    /**
     * 零售价格（元）
     */
    @ApiModelProperty(value = "零售价格")
    private BigDecimal price;
    /**
     * 新车指导价格（元）
     */
    @ApiModelProperty(value = "新车指导价格(万元)")
    private BigDecimal suggestPrice;
    /**
     * 批发价格（元）
     */
    @ApiModelProperty(value = "批发价格")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal wholesalePrice;

    /**
     * 状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
     */
    private Integer publishStatus;
    /**
     * 【冗余字段】调拨状态（1：已申请，0：未申请，2：申请通过，-1：申请拒绝，-2取消）
     */
    private Integer transferStatus;

    /**
     * 审核时间/上架时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date auditTime;

    /**
     * 提交时间/发布时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date publishTime;

    /**
     * 管理员调拨处理时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date transferHandleTime;
    /**
     * 检测员id
     */
    private Long checkUserId;

    /**
     * 检测员用户名称
     */
    private String checkLoginName;

    /**
     * 检测员id
     */
    private Long auditUserId;

    /**
     * 检测员用户名称
     */
    private String auditLoginName;

}
