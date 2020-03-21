package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单审核日志json
 *
 * @author twg
 * @since 2019/12/24
 */
@Data
public class OrderAuditLogDTO implements Serializable {
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id")
    private Long orderId;

    /**
     * 审核操作类型
     */
    @ApiModelProperty(value = "审核操作类型")
    private Integer auditType;
    /**
     * 审核类型
     */
    @ApiModelProperty(value = "审核类型")
    private String auditTypeName;
    /**
     * 审核结果
     */
    @ApiModelProperty(value = "审核结果")
    private String auditResult;
    /**
     * 审核备注
     */
    @ApiModelProperty(value = "审核备注")
    private String auditRemark;

    /**
     * 审核人
     */
    @ApiModelProperty(value = "审核人")
    private String auditUser;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    private String auditTime;
}
