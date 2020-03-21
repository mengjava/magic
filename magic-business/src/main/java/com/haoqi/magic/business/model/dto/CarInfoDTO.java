package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能描述:
 * web端车辆详情页
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/29 16:27
 * @Description:
 */
@Data
public class CarInfoDTO implements Serializable {

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
    private Long csCarDealerId;

    /*
    @ApiModelProperty(value = "车商")
    private String carDealer;*/

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date initDate;

    /**
     * 使用性质（1营运/0非营运)
     */
    @ApiModelProperty(value = "使用性质（1非运营 、2运营 、3营转非 、4租赁 、5教练车 、6家用车 、7出租车 、8公务车、9其他 ）")
    private Integer useType;

    /**
     * 行驶里程（万公里）
     */
    @ApiModelProperty(value = "行驶里程")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal travelDistance;

    @ApiModelProperty(value = "表显示里程")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal instrumentShowDistance;

    /**
     * 出厂日期
     */
    @ApiModelProperty(value = "出厂日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date productDate;
    /**
     * 车辆所在地
     */
    @ApiModelProperty(value = "车辆所在地")
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
    private String plateNo;
    /**
     * 【来自数据字典】排放标准
     */
    @ApiModelProperty(value = "【来自数据字典】排放标准")
    private String emissionStandardCode;
    /**
     * 汽车厂商
     */
    @ApiModelProperty(value = "汽车厂商")
    private String carFactory;
    /**
     * 排量
     */
    @ApiModelProperty(value = "排量")
    @JsonSerialize(using = ToStringSerializer.class)
    private Double displacement;
    /***
     * 排量类型 0:L 1:T
     */
    private String displacementType;
    /**
     * 【数据字典】变速箱
     */
    @ApiModelProperty(value = "【数据字典】变速箱")
    private String gearBoxCode;
    /**
     * 【数据字典】车辆类型
     */
    @ApiModelProperty(value = "【数据字典】车辆类型")
    private String carTypeCode;
    /**
     * 【数据字典】驱动方式
     */
    @ApiModelProperty(value = "【数据字典】驱动方式")
    private String driveMethodCode;
    /**
     * 【数据字典】燃油类型
     */
    @ApiModelProperty(value = "【数据字典】燃油类型")
    private String fuelTypeCode;
    /**
     * 【来自数据字典】颜色
     */
    @ApiModelProperty(value = "【来自数据字典】颜色")
    private String colorCode;
    /**
     * 座位数
     */
    @ApiModelProperty(value = "座位数")
    private Integer seatNum;
    /**
     * 轮胎规格
     */
    @ApiModelProperty(value = "轮胎规格")
    private String tyreType;
    /**
     * 整车型号
     */
    @ApiModelProperty(value = "整车型号")
    private String carVersion;
    /**
     * 发动机号
     */
    @ApiModelProperty(value = "发动机号")
    private String engineNo;
    /**
     * 过户次数
     */
    @ApiModelProperty(value = "过户次数")
    private Integer transferNum;
    /**
     * 过户类型
     */
    @ApiModelProperty(value = "过户类型")
    private String transferType;
    /**
     * 现使用方（归属1个人，0单位）
     */
    @ApiModelProperty(value = "现使用方（归属1个人，0单位）")
    private Integer belongTo;
    /**
     * 是否进口（1：是，0：否）
     */
    @ApiModelProperty(value = "进口（1：是，0：否）")
    private Integer importType;
    /**
     * 是否有备胎（1:有，0：无）
     */
    @ApiModelProperty(value = "备胎（1:有，0：无）")
    private Integer spareWheel;
    /**
     * 零售价格（元）
     */
    @ApiModelProperty(value = "零售价格")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal price;
    //金额app展示
    private String priceStr;
    /**
     * 新车指导价格（元）
     */
    @ApiModelProperty(value = "新车指导价格")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal suggestPrice;
    /**
     * 批发价格（元）
     */
    @ApiModelProperty(value = "批发价格")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal wholesalePrice;
    /**
     * 是否有一口价（1：有，0：无）
     */
    @ApiModelProperty(value = "一口价（1：有，0：无）")
    private Integer haveFixedPrice;
    /**
     * 是否有促销价格（1：有，0：无）
     */
    @ApiModelProperty(value = "促销价格（1：有，0：无）")
    private Integer havePromotePrice;
    /**
     * 状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
     */
   /* private Integer publishStatus;*/

    /**
     * 浏览次数
     */
    private Integer scanNum;
    /* *
      * 买方信息
      */
    @ApiModelProperty(value = "买方负责")
    private String buyerInfo;
    /**
     * 卖方信息
     */
    @ApiModelProperty(value = "卖方负责")
    private String sellerInfo;
    /* *
      * 其他信息
      */
    @ApiModelProperty(value = "其他信息")
    private String otherInfo;

    /**
     * 审核时间/上架时间
     */
    /*@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date auditTime;*/

    /**
     * 提交时间/发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty(value = "提交时间/发布时间")
    private Date publishTime;

    /**
     * 下架时间
     */
    /*private Date pullOffTime;*/

    /***
     * 比新车省约计算公式= 新车指导价 - 零售价 +  (新车指导价 /1.17)，直接取整
     */
    @ApiModelProperty(value = "比新车省约计算公式")
    private BigDecimal provincialPrice;

    /**
     * 车辆照片
     */
    private List<CarFileDTO> carFiles;

    /***
     * 标签名称list (0首付,)
     */
    @ApiModelProperty(value = "标签名称list ")
    private List<TagsDTO> tagNames;

    @ApiModelProperty(value = "是否诚信联盟（0：不是，1：是）")
    private Integer creditUnion;

    /**
     * （1------两厢车 ， 2------3厢车）
     */
    @ApiModelProperty(value = "1------两厢车 ， 2------3厢车")
    private Integer carTrunkType;

    /**
     * 车模型图片名称
     */
    @ApiModelProperty(value = "车模型图片名称")
    private String carModelFileName;
    /**
     * 车模型图片文件路径
     */
    @ApiModelProperty(value = "车模型图片文件路径")
    private String carModelFilePath;


    /**
     * 状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）
     */
    @ApiModelProperty(value = "状态: （0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）")
    private Integer publishStatus;

    /**
     * 车辆交易标识（1：交易中，0：未交易，2：交易结束，默认为0）
     */
    @ApiModelProperty(value = "车辆交易标识（1：交易中，0：未交易，2：交易结束，默认为0）")
    private Integer tradeFlag;

    @ApiModelProperty(value = "买入按钮: 1:可买 0不可买")
    private Integer buyButton = 0;

    /**
     * 【冗余】维保URL
     */
    @ApiModelProperty(value = "维保URL")
    private String maintenanceUrl;
    /**
     * 【冗余】出险URL
     */
    @ApiModelProperty(value = "出险URL")
    private String insuranceUrl;

    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceFree;

    @ApiModelProperty(value = "服务费 1：单笔/0服务费比例")
    private Integer serviceType;

}
