package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.haoqi.rigger.core.serializer.DateJsonSerializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述:
 * app分页
 *
 * @Author: yanhao
 * @Date: 2019/12/11 11:18
 * @Param:
 * @Description:
 */
@Data
public class AppCarPageDTO implements Serializable {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date gmtCreate;

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
    private Long csCarDealerId;

    @ApiModelProperty(value = "车商")
    private String carDealer;

    /**
     * 车型车款
     */
    @ApiModelProperty(value = "车型车款id")
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
     * 行驶里程（万公里）
     */
    @ApiModelProperty(value = "行驶里程")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal travelDistance;

    /**
     * 表显里程（万公里）
     */
    @ApiModelProperty(value = "表显里程")
    @NotNull(message = "表显里程不能为空")
    private BigDecimal instrumentShowDistance;

    /**
     * 出厂日期
     */
    @ApiModelProperty(value = "出厂日期")
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date productDate;
    /**
     * 车辆所在地
     */
    @ApiModelProperty(value = "车辆所在地")
    @NotBlank(message = "车辆所在地不能为空")
    private String locate;
    /**
     * 车辆注册地
     */
    @ApiModelProperty(value = "车辆注册地")
    private String registerLocate;
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
     * 排量
     */
    @ApiModelProperty(value = "排量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Double displacement;

    @ApiModelProperty(value = "排量类型 : 0L 1T")
    private String displacementType;
    /**
     * 【数据字典】变速箱
     */
    @ApiModelProperty(value = "【数据字典】变速箱")
    @NotBlank(message = "变速箱不能为空")
    private String gearBoxCode;

    @ApiModelProperty(value = "变速箱名")
    private String gearBoxCodeName;
    /**
     * 【数据字典】车辆类型
     */
    @ApiModelProperty(value = "【数据字典】车辆类型")
    @NotBlank(message = "车辆类型不能为空")
    private String carTypeCode;

    @ApiModelProperty(value = "车辆类型名")
    private String carTypeCodeName;
    /**
     * 【数据字典】驱动方式
     */
    @ApiModelProperty(value = "【数据字典】驱动方式")
    @NotBlank(message = "驱动方式不能为空")
    private String driveMethodCode;

    @ApiModelProperty(value = "驱动方式名")
    private String driveMethodCodeName;
    /**
     * 【数据字典】燃油类型
     */
    @ApiModelProperty(value = "【数据字典】燃油类型")
    @NotBlank(message = "燃油类型不能为空")
    private String fuelTypeCode;

    @ApiModelProperty(value = "燃油类型名")
    private String fuelTypeCodeName;
    /**
     * 【来自数据字典】颜色
     */
    @ApiModelProperty(value = "【来自数据字典】颜色")
    private String colorCode;

    @ApiModelProperty(value = "颜色名")
    private String colorCodeName;

    /**
     * 发动机号
     */
    @ApiModelProperty(value = "发动机号")
    private String engineNo;
    /**
     * 是否进口（1：是，0：否）
     */
    @ApiModelProperty(value = "进口（1：是，0：否）")
    private Integer importType;
    /**
     * 零售价格（元）
     */
    @ApiModelProperty(value = "零售价格")
    private BigDecimal price;
    /**
     * 新车指导价格（元）
     */
    @ApiModelProperty(value = "新车指导价格(万元)")
    @JsonSerialize(using = ToStringSerializer.class)
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
    @ApiModelProperty(value = "批发价格")
    private Integer publishStatus;
    /**
     * 浏览次数
     */
    private Integer scanNum;


    @ApiModelProperty(value = "左前45度缩略文件路径")
    private String iconFilePath;

    @ApiModelProperty(value = "文件服务器地址")
    private String pictureURL;

    /**
     * 提交时间/发布时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @ApiModelProperty(value = "提交时间/发布时间")
    private Date publishTime;

    /**
     * 车辆交易标识（1：交易中，0：未交易，2：交易结束，默认为0）
     */
    @ApiModelProperty(value = "车辆交易标识（1：交易中，0：未交易，2：交易结束，默认为0）")
    private Integer tradeFlag;

}
