package com.haoqi.magic.business.model.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * <p>
 * 争议项管理DTO
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
@Data
public class CsDisputeItemVO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键
     */
    private Long id;

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
    @NotEmpty(message = "争议项名称不能为空")
    private String itemName;
    /**
     * 类型：1买家，2卖家，3PC,默认为1
     */
    @NotNull(message = "类型不能为空")
    private Integer type;
    /**
     * 是否支持文本（0：不支持，1：支持，默认为0）
     */
    private Integer textFlag;
    /**
     * 是否复检（0：不复检，1：复检，默认为0）【只有主争议项才有该值】
     */
    private Integer recheckFlag;
    /**
     * 父争议性id
     */
    private Long parentId;
}
