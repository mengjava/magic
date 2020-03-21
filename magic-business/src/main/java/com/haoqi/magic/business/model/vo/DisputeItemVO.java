package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author twg
 * @since 2019/12/6
 */
@Data
public class DisputeItemVO implements Serializable {

    /**
     * 争议项id
     */
    @ApiModelProperty(value = "争议项id")
    private Long id;

    /**
     * 父争议项+字争议项名
     */
    @ApiModelProperty(value = "父争议项+字争议项名")
    private String showText;
    /**
     * 父争议项id
     */
    @ApiModelProperty(value = "父争议项id")
    private Long parentId;

    /**
     * 是否复检标志
     */
    @ApiModelProperty(value = "是否复检标志")
    private Integer recheckFlag;

    /**
     * 争议项为其它时，输入的文本信息
     */
    @ApiModelProperty(value = "争议项为其它时，输入的文本信息")
    private String textDecs;

    /**
     * 赔偿金额
     */
    @ApiModelProperty(value = "赔偿金额: app不传")
    private BigDecimal money;

    /**
     * 争议复检处理结果
     */
    @ApiModelProperty(value = "争议复检处理结果 app不传")
    private Integer recheckResult;

    /**
     * 注释
     */
    private String remark;
}
