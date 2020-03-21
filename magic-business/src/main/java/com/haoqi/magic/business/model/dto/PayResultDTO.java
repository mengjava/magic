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
import java.util.Date;

/**
 * 〈支付结果DTO〉<br>
 *
 * @author 陈泳志
 * @create 2019/11/27
 * @since 1.0.0
 */
@Data
public class PayResultDTO implements Serializable {

	private static final long serialVersionUID = -5307446225632352916L;

	/**
	 * 系统流水号
	 */
	private String serialNo;

	/**
	 * 商户名称
	 */
	private String merchantName;

	/**
	 * 第三方(返回)支付流水号
	 */
	private String thirdSerialNo;

	/**
	 * 订单金额
	 */
	private long money;

	/**
	 * 支付方式
	 */
	private String payWay;

	/**
	 * 支付状态（参考枚举:PayStatuEnum）
	 */
	private Integer status;

	/**
	 * 支付完成时间
	 */
	private Date payEndTime;

	/**
	 *银行名称
	 */
	private String bankName;

	/**
	 *账号
	 */
	private String accountNo;

	/**
	 *账户名
	 */
	private String accountName;

	/**
	 *账号属性
	 */
	private String accountProp;

	/**
	 *
	 */
	private String remark;

}