package com.haoqi.magic.business.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 非余额支付订单回调
 *
 * @author twg
 * @since 2019/12/2
 */
@Data
public class PaymentCallBackDTO implements Serializable {
    /**
     * 支付订单流水号
     */
    @NotBlank(message = "支付订单编号不能为空")
    private String serialNo;

    /**
     * 支付状态
     */
    @NotNull(message = "支付状态不能为空")
    private Integer status;


}
