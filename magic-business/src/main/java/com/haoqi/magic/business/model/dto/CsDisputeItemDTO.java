package com.haoqi.magic.business.model.dto;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 争议项管理DTO
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
@Data
public class CsDisputeItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date gmtModified;
    /**
     * 注释
     */
    private String remark;
    /**
     * 是否删除 0否1是
     */
    private Integer isDeleted;
    /**
     * 争议项名称
     */
    @ApiModelProperty(value = "争议项名称")
    private String itemName;
    /**
     * 类型：1买家，2卖家，3PC,默认为1
     */
    @ApiModelProperty(value = "类型：1买家，2卖家，3PC,默认为1")
    private Integer type;
    /**
     * 是否支持文本（0：不支持，1：支持，默认为0）
     */
    @ApiModelProperty(value = "是否支持文本（0：不支持，1：支持，默认为0）")
    private Integer textFlag;
    /**
     * 是否复检（0：不复检，1：复检，默认为0）【只有主争议项才有该值】
     */
    @ApiModelProperty(value = "是否复检（0：不复检，1：复检，默认为0）【只有主争议项才有该值】")
    private Integer recheckFlag;
    /**
     * 父争议性id
     */
    @ApiModelProperty(value = "父争议性id")
    private Long parentId;
}
