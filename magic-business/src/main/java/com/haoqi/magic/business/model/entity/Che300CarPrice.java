package com.haoqi.magic.business.model.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by yanhao on 2018/11/5.
 */
@Data
public class Che300CarPrice implements Serializable{
    /**
     * 返回结果状态，1表示成功，0表示失败。
     */
    private String status;
    /**
     * 车辆估值（个人交易价）
     */
    @JSONField(name = "eval_price")
    private double evalPrice;
    /**
     * 车况一般个人交易价
     */
    @JSONField(name = "low_price")
    private double lowPrice;
    /**
     * 车况良好个人交易价
     */
    @JSONField(name = "good_price")
    private double goodPrice;
    /**
     * 车况优秀个人交易价 （获取这个价格）
     */
    @JSONField(name = "high_price")
    private double highPrice;
    /**
     * 车商收购价（默认车况*)
     */
    @JSONField(name = "dealer_buy_price")
    private double dealerBuyPrice;
    /**
     * 个人交易价（默认车况）
     */
    @JSONField(name = "individual_price")
    private double individualPrice;
    /**
     * 车商零售价（默认车况）
     */
    @JSONField(name = "dealer_price")
    private double dealerPrice;
    /**
     * 详细估值报告
     */
    private String url;
    /**
     * 车型厂商指导价
     */
    private String price;
    /**
     * 车型排放标准
     */
    @JSONField(name = "discharge_standard")
    private String dischargeStandard;
    /**
     * 车型名称
     */
    private String title;
    /**
     * 车辆品牌logo地址
     */
    @JSONField(name = "car_logo_url")
    private String carLogoUrl;
    /**
     * 失败原因
     */
    @JSONField(name = "error_msg")
    private String errorMsg;



}
