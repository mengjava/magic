package com.haoqi.magic.business.model.vo;


import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * ClassName:com.haoqi.magic.business.model.vo <br/>
 * Function: <br/>
 * Date:     2019/5/5 11:17 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsCustomBuiltVO extends Page {
    private static final long serialVersionUID = 2903077511007671009L;

    //主键
    private Long id;

    // 品牌id
    @ApiModelProperty(value = "品牌id",required = true)
    @NotNull(message = "品牌id不能为空")
    private Long sysCarBrandId;

    //【冗余】品牌名称
    @ApiModelProperty(value = "品牌名称",required = true)
    @NotBlank(message = "品牌名称不能为空")
    private String sysCarBrandName;

    //车龄(单位：年）
    @ApiModelProperty(value = "车龄",required = true)
    @NotNull(message = "车龄不能为空")
    private Integer age;

    //【来自数据字典】颜色
    @ApiModelProperty(value = "颜色")
    private String colorCode;

    //最小价格（单位：万元）
    @ApiModelProperty(value = "车辆价格-区间开始")
    private BigDecimal minPrice;

    //最大价格（单位：万元）
    @ApiModelProperty(value = "车辆价格-区间结束")
    private BigDecimal maxPrice;

    //【来自数据字典】排放标准
    @ApiModelProperty(value = "排放标准")
    private String emissionStandardCode;

    //行驶里程（万公里）
    @ApiModelProperty(value = "行驶里程")
    @Length(max = 2, message = "行驶里程限制在100以内")
    private String travelDistance;

    //定制经销商id
    private Long csCarDealerId;

    //申请定制时间--开始
    @ApiModelProperty(value = "申请定制时间--开始")
    private String applyTimeStart;

    //申请定制时间--结束
    @ApiModelProperty(value = "申请定制时间--结束")
    private String applyTimeEnd;

    @ApiModelProperty(value = "经销商名称")
    private String dealerName;

}
