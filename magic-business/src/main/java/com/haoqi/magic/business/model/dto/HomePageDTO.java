package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 功能描述:
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/7 14:39
 * @Description:
 */
@Getter
@Setter
public class HomePageDTO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键id")
    private Long id;
    /**
     * 车商id
     */
    private Long csCarDealerId;
    /**
     * 车型id
     */
    @ApiModelProperty(value = "车型id")
    private Long sysCarModelId;
    /**
     * 车辆编号(自动生成，唯一标识）
     */
    @ApiModelProperty(value = "车辆编号")
    private String carNo;
    /**
     * 【冗余字段】车型名称
     */
    @ApiModelProperty(value = "车型名称")
    private String sysCarModelName;
    /**
     * 初始登记日期
     */
    @ApiModelProperty(value = "初始登记日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date initDate;
    /**
     * 行驶里程（万公里）
     */
    @ApiModelProperty(value = "行驶里程（万公里）")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal travelDistance;

    @ApiModelProperty(value = "表显示里程")
    private BigDecimal instrumentShowDistance;
    /**
     * 出厂日期
     */
    @ApiModelProperty(value = "出厂日期")
    private Date productDate;
    /**
     * 车辆所在地
     */
    @ApiModelProperty(value = "车辆所在地")
    private String locate;

    /**
     * 【来自数据字典】颜色
     */
    //@ApiModelProperty(value = "颜色")
    //private String colorCode;
    /**
     * 零售价格（元）
     */
    @ApiModelProperty(value = "零售价格（元）")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal price;
    //金额处理(非会员 会员)
    @ApiModelProperty(value = "APP零售价格（元）")
    private String priceStr;
    /**
     * 新车指导价格（元）
     */
    @ApiModelProperty(value = "新车指导价格（元）")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigDecimal suggestPrice;
    /**
     * 批发价格（元）
     */
    //private BigDecimal wholesalePrice;
    /**
     * 是否有一口价（1：有，0：无）
     */
    @ApiModelProperty(value = "是否有一口价（1：有，0：无）")
    private Integer haveFixedPrice;
    /**
     * 是否有促销价格（1：有，0：无）
     */
    @ApiModelProperty(value = "是否有促销价格（1：有，0：无）")
    private Integer havePromotePrice;
    /**
     * 浏览次数
     */
    @ApiModelProperty(value = "浏览次数")
    private int scanNum;
    /**
     * 浏览次数基数，随机生成
     */
    private int scanBaseNum;

    /***
     * 标签名称list (0首付,)
     */
    @ApiModelProperty(value = "标签名称list ")
    private List<TagsDTO> tagNames;
    /**
     * 左前45度缩略文件名
     */
    @ApiModelProperty(value = "左前45度缩略文件名")
    private String iconFileName;
    /**
     * 左前45度缩略分组名
     */
    private String iconFileGroup;
    /**
     * 左前45度缩略文件路径
     */
    @ApiModelProperty(value = "左前45度缩略文件路径")
    private String iconFilePath;

    /***
     * 是否诚信联盟（0：不是，1：是）
     *
     */
    @ApiModelProperty(value = "是否诚信联盟（0：不是，1：是）")
    private Integer creditUnion;

    /**
     * 【来自数据字典】排放标准
     */
    @ApiModelProperty(value = "【来自数据字典】排放标准")
    private String emissionStandardCode;

    @ApiModelProperty(value = "排放标准名")
    private String emissionStandardCodeName;


    public Integer getScanNum() {
        return scanNum + scanBaseNum;
    }

}
