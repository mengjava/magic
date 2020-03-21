package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author twg
 * @since 2019/12/24
 */
@Data
public class PaymentParamVO implements Serializable {

    /**
     * 支付金额
     */
//    @Min(1)
    @ApiModelProperty(value = "支付金额")
    private BigDecimal money;

    /**
     * 交易类型
     */
    @NotNull(message = "交易类型不能为空")
    @ApiModelProperty(value = "交易类型")
    private Integer tradeType;

    /**
     * 支付方式（余额支付为0）
     */
    @NotNull(message = "支付方式不能为空")
    @ApiModelProperty(value = "支付方式（余额支付为0）")
    private Long paymentId;

    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id，有就传")
    private Long orderId;

    /**
     * vipId
     */
    @ApiModelProperty(value = "会员卡id，有就传")
    private Long vipId;

    /**
     * 设备信息
     */
    @ApiModelProperty(value = "设备信息，有就传")
    private String deviceInfo;

    /**
     * 订单来源（数据来源（1：Android，2：IOS，3：PC） 参考枚举：DataFromEnum）
     */
    @ApiModelProperty(value = "订单来源（数据来源（1：Android，2：IOS，3：PC）")
    private Integer dataFrom;

}
