package com.haoqi.magic.business.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 支付订单相关
 *
 * @author twg
 * @since 2019/12/2
 */
@Data
public class PaymentOrderDTO implements Serializable {

    /**
     * 支付订单流水号
     */
    @NotBlank(message = "支付订单编号不能为空")
    private String serialNo;

    /**
     * 交易金额
     */
    private BigDecimal money;

    /**
     * 交易说明
     */
    private String tradeDesc;

    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不能为空")
    private Integer tradeType;


    /**
     * 支付密码
     */
    private String payCode;

    /**
     * 支付状态
     */
    private Integer status;

    private String body;

}
