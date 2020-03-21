package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 功能描述:
 * 争议项分页查询
 *
 * @Author: yanhao
 * @Date: 2019/11/29 16:06
 * @Param:
 * @Description:
 */
@Data
public class CsDisputeItemPageVO extends Page {

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
     * 父争议性id
     */
    @ApiModelProperty(value = "父争议性id : 父类为0 子类为父类id")
    private Long parentId;
    /**
     * 是否复检（0：不复检，1：复检，默认为0）【只有主争议项才有该值】
     */
    @ApiModelProperty(value = "是否复检（0：不复检，1：复检，默认为0）【只有主争议项才有该值】")
    private Integer recheckFlag;
    /**
     * 是否删除 0否1是
     */
    @ApiModelProperty(value = "是否删除 0否1是")
    private Integer isDeleted;
}
