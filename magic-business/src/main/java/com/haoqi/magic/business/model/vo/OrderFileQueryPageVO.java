package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by yanhao on 2019/12/11.15:14
 */
@Getter
@Setter
public class OrderFileQueryPageVO extends Page {

    @ApiModelProperty(value = "订单id")
    @NotNull(message = "订单id不能为空")
    private Long csCarOrderId;

    @ApiModelProperty(value = "附件类型 1：过户，2：买家打款，3，卖家打款")
    private Integer type;
}
