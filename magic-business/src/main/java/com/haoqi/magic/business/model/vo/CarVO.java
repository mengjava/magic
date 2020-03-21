package com.haoqi.magic.business.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class CarVO implements Serializable {
    /**
     * 车商
     */
    @NotNull(message = "车商不能为空")
    private Long csCarDealerId;

    /**
     * 车型车款
     */
    @NotNull(message = "车型车款不能为空")
    private Long sysCarModelId;

    /**
     * vin码
     */
    @NotBlank(message = "VIN码不能为空")
    private String vin;

    /**
     * 初始登记日期
     */
    @NotNull(message = "初始登记日期不能为空")
    private Date initDate;

    /**
     * 使用性质（1营运/0非营运)
     */
    @NotNull(message = "使用性质不能为空")
    private Integer useType;

    /**
     * 行驶里程（万公里）
     */
    @NotNull(message = "行驶里程不能为空")
    private BigDecimal travelDistance;

    /**
     * 出厂日期
     */
    @NotNull(message = "出厂日期不能为空")
    private Date productDate;
    /**
     * 车辆所在地
     */
    @NotBlank(message = "车辆所在地不能为空")
    private String locate;
    /**
     * 车辆注册地
     */
    @NotBlank(message = "车辆注册地不能为空")
    private String registerLocate;
    /**
     * 车牌号
     */
    @NotBlank(message = "车牌号不能为空")
    private String plateNo;
    /**
     * 【来自数据字典】排放标准
     */
    @NotBlank(message = "排放标准不能为空")
    private String emissionStandardCode;
    /**
     * 汽车厂商
     */
    @NotBlank(message = "汽车厂商不能为空")
    private String carFactory;
    /**
     * 排量
     */
    @NotNull(message = "排量不能为空")
    private Double displacement;
    /**
     * 【数据字典】变速箱
     */
    @NotBlank(message = "变速箱不能为空")
    private String gearBoxCode;
    /**
     * 【数据字典】车辆类型
     */
    @NotBlank(message = "车辆类型不能为空")
    private String carTypeCode;
    /**
     * 【数据字典】驱动方式
     */
    @NotBlank(message = "驱动方式不能为空")
    private String driveMethodCode;
    /**
     * 【数据字典】燃油类型
     */
    @NotBlank(message = "燃油类型不能为空")
    private String fuelTypeCode;
    /**
     * 【来自数据字典】颜色
     */
    @NotBlank(message = "颜色不能为空")
    private String colorCode;
    /**
     * 座位数
     */
    @NotBlank(message = "座位数不能为空")
    private Integer seatNum;
    /**
     * 轮胎规格
     */
    @NotBlank(message = "轮胎规格不能为空")
    private String tyreType;
    /**
     * 整车型号
     */
    @NotBlank(message = "整车型号不能为空")
    private String carVersion;
    /**
     * 发动机号
     */
    @NotBlank(message = "发动机号不能为空")
    private String engineNo;
    /**
     * 过户次数
     */
    @NotNull(message = "过户次数不能为空")
    private Integer transferNum;
    /**
     * 过户类型
     */
    @NotBlank(message = "过户类型不能为空")
    private String transferType;
    /**
     * 现使用方（归属1个人，0单位）
     */
    @NotNull(message = "现使用方不能为空")
    private Integer belongTo;
    /**
     * 是否进口（1：是，0：否）
     */
    @NotNull(message = "是否进口不能为空")
    private Integer importType;
    /**
     * 是否有备胎（1:有，0：无）
     */
    @NotNull(message = "是否有备胎不能为空")
    private Integer spareWheel;
    /**
     * 零售价格（元）
     */
    @NotNull(message = "零售价格不能为空")
    private BigDecimal price;
    /**
     * 新车指导价格（元）
     */
    @NotNull(message = "新车指导价格不能为空")
    private BigDecimal suggestPrice;
    /**
     * 批发价格（元）
     */
    @NotNull(message = "批发价格不能为空")
    private BigDecimal wholesalePrice;
    /**
     * 是否有一口价（1：有，0：无）
     */
    @NotNull(message = "是否有一口价不能为空")
    private Integer haveFixedPrice;
    /**
     * 是否有促销价格（1：有，0：无）
     */
    @NotNull(message = "是否有促销价格不能为空")
    private Integer havePromotePrice;
    /**
     * 状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
     */
    private Integer publishStatus;
    /**
     * 【冗余字段】调拨状态（1：已申请，0：未申请，2：申请通过，-1：申请拒绝，-2取消）
     */
    private Integer transferStatus;
    /**
     * 浏览次数
     */
    private Integer scanNum;
}
