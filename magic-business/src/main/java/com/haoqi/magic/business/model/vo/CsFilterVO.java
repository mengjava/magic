package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * ClassName:com.haoqi.magic.system.model.vo <br/>
 * Function: <br/>
 * Date:     2019/4/26 15:37 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsFilterVO extends Page {
    private static final long serialVersionUID = 7102070647333281005L;

    //主键
    private Long id;



    //筛选名称
    @ApiModelProperty(value = "筛选名称",required = true)
    @NotBlank(message = "标签名称不能为空")
    private String filterName;

    //1车系，2品牌，3价格
    @ApiModelProperty(value = "筛选类型,1车系，2品牌，3价格",required = true)
    @NotNull(message = "筛选类型不能为空")
    private Integer filterType;

    //排序号
    @ApiModelProperty(value = "排序号",required = true)
    @NotNull(message = "排序号不能为空")
    private Integer orderNo;

    //品牌id
    @ApiModelProperty(value = "品牌id")
    private Long sysCarBrandId;

    //【冗余】品牌名称
    @ApiModelProperty(value = "【冗余】品牌名称")
    private String sysCarBrandName;

    //文件名
    @ApiModelProperty(value = "文件名")
    private String fileName;

    //分组名
    @ApiModelProperty(value = "分组名")
    private String fileGroup;

    //文件路径
    @ApiModelProperty(value = "文件路径")
    private String filePath;

    //车系id
    @ApiModelProperty(value = "车系id")
    private Long sysCarSeries;

    //【冗余】车系名称
    @ApiModelProperty(value = "【冗余】车系名称")
    private String sysCarSeriesName;

    //最小价格（单位：万元）
    @ApiModelProperty(value = "最小价格（单位：万元）")
    private BigDecimal minPrice;

    //最大价格（单位：万元）
    @ApiModelProperty(value = "最大价格（单位：万元）")
    private BigDecimal maxPrice;

}
