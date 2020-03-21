package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author twg
 * @since 2019/12/6
 */
@Data
public class AccountDetailDTO implements Serializable {

    /**
     * 创建时间
     */
    private Date gmtCreate;
    /**
     * 交易金额(单位：元）
     */
    private BigDecimal money;
    /**
     * 交易类型1：维保查询，2排放查询，3出险查询，4车型识别查询，5快速评估查询，10冻结保证金，11，提现，12 开通会员，13余额充值， 20赔偿，21违约退车，22，协商平退
     */
    private Integer tradeType;
    /**
     * 收支类型，1:收入，2：支出
     */
    private Integer type;
    /**
     * 交易状态1：成功，2：失败, 3 交易中
     */
    private Integer status;
    /**
     * 交易流水号
     */
    private String serialNo;

    /**
     * 支付方式（1：微信，2支付宝,3...）
     */
    private Integer payType;
    /**
     * 交易说明
     */
    private String tradeRemark;

    /**
     * 支付时间
     */
    private Date payTime;
}
