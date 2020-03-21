/**
 * Copyright (C), 2018-2019, 浩韵控股科技有限公司
 * FileName: PayBaseDTO
 * Author:   陈泳志
 * Date:     2019/11/5 16:10
 * Description: 支付基础对象
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haoqi.magic.business.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 〈支付基础对象〉<br>
 *
 * @author 陈泳志
 * @create 2019/11/5
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
public class PayBaseDTO implements Serializable {
    /**
     * 应用的appKey
     */
    @NotBlank(message = "应用appKey不能为空")
    private String appKey;

    /**
     * 应用的appSecret
     */
    @NotBlank(message = "应用秘钥不能为空")
    private String appSecret;

    /**
     * 支付金额
     */
    @NotNull(message = "支付金额不能为空")
    @Min(value = 1, message = "支付金额必须大于1分")
    private int money;

    /**
     * 产品code
     */
    @NotNull(message = "产品code不能为空")
    private Integer productCode;


    /**
     * 支付流水号
     */
    @NotBlank(message = "支付流水号不能空")
    private String serialNo;


    private String notifyUrl;

    /**
     * 客户端IP
     */
    private String clientIp;


    /**
     * 商品描述(用于订单确认时回显信息)
     */
    @NotBlank(message = "商品描述")
    private String subject;

    /**
     * 设备信息
     */
    private String deviceInfo;

    /**
     * 订单来源（数据来源（1：Android，2：IOS，3：PC） 参考枚举：DataFromEnum）
     */
    @NotBlank(message = "订单来源")
    private Integer dataFrom;


}