package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author twg
 * @since 2019/12/5
 */
@Data
public class CarOrderParamVO implements Serializable {
    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空")
    @ApiModelProperty(value = "订单id")
    private Long id;

    /**
     * 申请信息
     */
    @ApiModelProperty(value = "申请信息")
    private String applyInfo;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 争议项
     */
    private List<DisputeItemVO> disputeItems;
}
