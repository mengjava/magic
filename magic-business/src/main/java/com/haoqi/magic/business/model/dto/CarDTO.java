package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.haoqi.rigger.core.serializer.DateJsonSerializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class CarDTO implements Serializable {
    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date gmtModified;

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
    @NotBlank(message = "VIN码不能为空")
    private String vin;

    /**
     * 初始登记日期
     */
    @ApiModelProperty(value = "初始登记日期")
    @NotNull(message = "初始登记日期不能为空")
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date initDate;

    /**
     * 使用性质（1营运/0非营运)
     */
    @ApiModelProperty(value = "使用性质（1非运营 、2运营 、3营转非 、4租赁 、5教练车 、6家用车 、7出租车 、8公务车、9其他 ）")
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
    @NotNull(message = "表显里程不能为空")
    private BigDecimal instrumentShowDistance;

    /**
     * 出厂日期
     */
    @ApiModelProperty(value = "出厂日期")
    @NotNull(message = "出厂日期不能为空")
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
    @NotBlank(message = "车辆注册地不能为空")
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
    @NotBlank(message = "排放标准不能为空")
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
    @NotNull(message = "排量不能为空")
    @Min(value = 0, message = "排量格式必须在0-10之间")
    @Max(value = 10, message = "排量格式必须在0-10之间")
    @Digits(integer = 9, fraction = 9, message = "排量格式不对")
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
    @NotBlank(message = "颜色不能为空")
    private String colorCode;

    @ApiModelProperty(value = "颜色名")
    private String colorCodeName;
    /**
     * 座位数
     */
    @ApiModelProperty(value = "座位数")
    @NotNull(message = "座位数不能为空")
    private Integer seatNum;
    /**
     * 轮胎规格
     */
    @ApiModelProperty(value = "轮胎规格")
    @NotBlank(message = "轮胎规格不能为空")
    private String tyreType;
    /**
     * 整车型号
     */
    @ApiModelProperty(value = "整车型号")
    @NotBlank(message = "整车型号不能为空")
    private String carVersion;
    /**
     * 发动机号
     */
    @ApiModelProperty(value = "发动机号")
    @NotBlank(message = "发动机号不能为空")
    private String engineNo;
    /**
     * 过户次数
     */
    @ApiModelProperty(value = "过户次数")
    @NotNull(message = "过户次数不能为空")
    private Integer transferNum;
    /**
     * 过户类型
     */
    @ApiModelProperty(value = "过户类型")
    @NotBlank(message = "过户类型不能为空")
    private String transferType;
    /**
     * 现使用方（归属1个人，0单位）
     */
    @ApiModelProperty(value = "现使用方（归属1个人，0单位）")
    @NotNull(message = "现使用方不能为空")
    private Integer belongTo;
    /**
     * 是否进口（1：是，0：否）
     */
    @ApiModelProperty(value = "进口（1：是，0：否）")
    @NotNull(message = "是否进口不能为空")
    private Integer importType;
    /**
     * 是否有备胎（1:有，0：无）
     */
    @ApiModelProperty(value = "备胎（1:有，0：无）")
    @NotNull(message = "是否有备胎不能为空")
    private Integer spareWheel;
    /**
     * 零售价格（元）
     */
    @ApiModelProperty(value = "零售价格")
    @NotNull(message = "零售价格不能为空")
    @DecimalMax(value = "999999", message = "零售价格太长")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal price;
    /**
     * 新车指导价格（元）
     */
    @ApiModelProperty(value = "新车指导价格(万元)")
    @NotNull(message = "新车指导价格不能为空")
    @DecimalMax(value = "999999", message = "新车指导价格太长")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal suggestPrice;
    /**
     * 批发价格（元）
     */
    @ApiModelProperty(value = "批发价格")
    @NotNull(message = "批发价格不能为空")
    @DecimalMax(value = "999999", message = "批发价格太长")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal wholesalePrice;
    /**
     * 是否有一口价（1：有，0：无）
     */
    @ApiModelProperty(value = "一口价（1：有，0：无）")
    @NotNull(message = "是否有一口价不能为空")
    private Integer haveFixedPrice;
    /**
     * 是否有促销价格（1：有，0：无）
     */
    @ApiModelProperty(value = "促销价格（1：有，0：无）")
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

    /**
     * 买方信息
     */
    @ApiModelProperty(value = "买方信息")
    private String buyerInfo;
    /**
     * 卖方信息
     */
    @ApiModelProperty(value = "卖方信息")
    private String sellerInfo;
    /**
     * 其他信息
     */
    @ApiModelProperty(value = "其他信息")
    private String otherInfo;

    @ApiModelProperty(value = "左前45度缩略文件路径")
    private String iconFilePath;

    @ApiModelProperty(value = "文件服务器地址")
    private String pictureURL;


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
     * 下架时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date pullOffTime;

    /**
     * 检测时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date checkTime;

    /**
     * 检测员id
     */
    private Long checkUserId;

    /**
     * 检测员用户名称
     */
    private String checkLoginName;

    /**
     * 车辆审核员id
     */
    private Long auditUserId;

    /**
     * 车辆审核备注
     */
    private String auditRemark;

    /**
     * 车辆审核员用户名称
     */
    private String auditLoginName;

    /**
     * 车辆配置信息
     */
    @Valid
    private CarConfigDTO carConfig;

    /**
     * 车辆手续信息
     */
    @Valid
    private CarProcedureDTO carProcedure;

    /**
     * 车辆照片
     */
    @Valid
    private List<CarFileDTO> carFiles;

    /**
     * 车辆检查信息：事故、外观、检测
     */
    private List<CarCheckDTO> carChecks;

	/**
	 * 下架备注
	 */
	private String remark;

    /**
     * 车辆交易标识（1：交易中，0：未交易，2：交易结束，默认为0）
     */
    private Integer tradeFlag;
    /**
     *（1------两厢车 ， 2------3厢车）
     */
    private Integer carTrunkType;
    /**
     * 车模型图片名称
     */
    private String carModelFileName;
    /**
     * 车模型图片文件路径
     */
    private String carModelFilePath;
}
