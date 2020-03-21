/**
 * Copyright (C), 2018-2019, 浩韵控股科技有限公司
 * FileName: PayOrderDTO
 * Author:   陈泳志
 * Date:     2019/11/27 11:00
 * Description: 支付订单DTO
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈支付订单DTO〉<br> 
 *
 * @author 陈泳志
 * @create 2019/11/27
 * @since 1.0.0
 */
@Data
public class PayOrderDTO implements Serializable {

	private static final long serialVersionUID = -2640535896412019035L;

	/**
	 * 商户名称
	 */
	private String merchantName;

	/**
	 * 商户号（对应微信、支付宝申请注册的商户号）
	 */
	private String merchantNo;

	/**
	 * 支付流水号
	 */
	private String serialNo;

	/**
	 * 订单生成时间
	 */
	private String createTime;

	/**
	 * 订单金额（单位：分）
	 */
	private int money;

	/**
	 * 支付方式
	 */
	private String payWay;

	/**
	 * 支付状态（参考枚举）
	 */
	private Integer status;

	/**
	 * 请求内容
	 */
	private String body;
}