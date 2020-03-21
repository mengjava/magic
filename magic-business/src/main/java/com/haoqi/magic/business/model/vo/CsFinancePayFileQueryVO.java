package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 财务打款表附件查询条件
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
@Data
public class CsFinancePayFileQueryVO extends Page {

    private static final long serialVersionUID = 1L;
    /**
     * 订单ID
     */
    @NotNull(message = "订单id不能为空")
    private Long csCarOrderId;
    /**
     * 类型（1：买家车款，2卖家车款，3收款)
     */
    @ApiModelProperty(value = "类型（1：买家车款，2卖家车款，3收款)")
    private Integer payMoneyType;

    @ApiModelProperty(value = "1:打款列表 2:收款列表")
    private Integer type;

}
