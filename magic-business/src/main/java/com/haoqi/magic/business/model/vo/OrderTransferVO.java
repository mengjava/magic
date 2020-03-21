package com.haoqi.magic.business.model.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.magic.business.serializer.OrderStatusSerializer;
import com.haoqi.rigger.core.serializer.DateJsonSerializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单、过户信息
 *
 * @author twg
 * @since 2019/12/11
 */
@Data
public class OrderTransferVO implements Serializable {
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

    /**
     * VIN
     */
    @ApiModelProperty(value = "VIN")
    private String vin;

    /**
     * 车型
     */
    @ApiModelProperty(value = "车型")
    private String sysCarModelName;

    /**
     * 颜色
     */
    @ApiModelProperty(value = "颜色")
    private String color;

    /**
     * 车辆售价
     */
    @ApiModelProperty(value = "车辆售价")
    private BigDecimal salPrice;

    /**
     * 服务费
     */
    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceFee;

    /**
     * 过户状态
     */
    @ApiModelProperty(value = "过户状态")
    private Integer transferStatus;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 【冗余】排量
     */
    @ApiModelProperty(value = "【冗余】排量")
    private Double displacement;
    private String displacementType;
    /**
     * 【冗余】排放标准
     */
    @ApiModelProperty(value = "【冗余】排放标准")
    private String emissionStandard;
    /**
     * 【冗余】变速箱
     */
    @ApiModelProperty(value = "【冗余】变速箱")
    private String gearBox;
    /**
     * 【冗余】表显里程
     */
    @ApiModelProperty(value = "【冗余】表显里程")
    private BigDecimal instrumentShowDistance;

    /**
     * 车牌号归属地
     */
    @ApiModelProperty(value = "车牌号归属地")
    private String plateNoShortName;

    /**
     * 车商名
     */
    @ApiModelProperty(value = "车商名")
    private String carDealerName;

    /**
     * 车商联系人
     */
    @ApiModelProperty(value = "车商联系人")
    private String carDealerUser;

    /**
     * 车商联系人手机号
     */
    @ApiModelProperty(value = "车商联系人手机号")
    private String carDealerUserTel;

    /**
     * 买方姓名
     */
    @ApiModelProperty(value = "买方姓名")
    private String buyerName;
    /**
     * 买方电话
     */
    @ApiModelProperty(value = "买方电话")
    private String buyerTel;

    /**
     * 过户是否超时
     */
    @ApiModelProperty(value = "过户是否超时")
    private Integer transferDelay;

    /**
     * 打款是否超时
     */
    @ApiModelProperty(value = "打款是否超时")
    private Integer paymentDelay;

    /**
     * 卖出时间
     */
    @ApiModelProperty(value = "卖出时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date sellTime;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date auditTime;


    /**
     * 初始登记日期
     */
    @ApiModelProperty(value = "初始登记日期")
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date initDate;

    /**
     * 发布时间
     */
    @ApiModelProperty(value = "发布时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date publishTime;

    /**
     * 订单状态
     */
    @JsonSerialize(using = OrderStatusSerializer.class)
    @ApiModelProperty(value = "订单状态")
    private Integer status;

    /**
     * 购买时间
     */
    @ApiModelProperty(value = "购买时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date buyTime;

    /**
     * 过户审核时间
     */
    @ApiModelProperty(value = "过户审核时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date transferAuditTime;

    /**
     * 申请过户时间
     */
    @ApiModelProperty(value = "申请过户时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date transferApplyTime;

    /**
     * 过户审核状态
     */
    @ApiModelProperty(value = "过户审核状态")
    private Integer transferAuditStatus;

    /**
     * 过户审核人
     */
    @ApiModelProperty(value = "过户审核人")
    private String transferAuditUser;

    /**
     * 过户审核备注
     */
    @ApiModelProperty(value = "过户审核备注")
    private String transferAuditRemark;

    /**
     * 订单过户附件
     */
    @ApiModelProperty(value = "订单过户附件")
    private List<TransferFileVO> transferFiles;


    /**
     * 过户日期
     */
    @ApiModelProperty(value = "过户日期")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date transferTime;

    /**
     * 过户备注
     */
    @ApiModelProperty(value = "过户备注")
    private String transferRemark;
    /**
     * 车辆交易标识（1：交易中，0：未交易，2：交易结束，默认为0）
     */
    private Integer tradeFlag;
}
