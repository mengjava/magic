package com.haoqi.magic.business.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.magic.business.model.dto.OrderAuditLogDTO;
import com.haoqi.rigger.core.serializer.DateJsonSerializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单、争议信息
 *
 * @author twg
 * @since 2019/12/11
 */
@Data
public class OrderDisputeVO implements Serializable {
    @NotNull(message = "订单id不能为空")
    private Long orderId;
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;


    @ApiModelProperty(value = "vin")
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
     * 争议状态
     */
    @ApiModelProperty(value = "争议状态")
    private Integer disputeFlag;
    /**
     * 复检状态
     */
    @ApiModelProperty(value = "复检状态")
    private Integer recheckFlag;

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
    @ApiModelProperty(value = "排量")
    private Double displacement;
    /**
     * 【冗余】排放标准
     */
    @ApiModelProperty(value = "排放标准")
    private String emissionStandard;
    /**
     * 【冗余】变速箱
     */
    @ApiModelProperty(value = "变速箱")
    private String gearBox;
    /**
     * 【冗余】表显里程
     */
    @ApiModelProperty(value = "表显里程")
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
     * 过户费
     */
    private BigDecimal transferMoney;

    /**
     * 打款是否超时
     */
    @ApiModelProperty(value = "打款是否超时")
    private Integer paymentDelay;

    /**
     * 争议处理方式（不属实1，赔偿2，买家违约3、卖家违约4、协商平退5）
     */
    @ApiModelProperty(value = "争议处理方式（默认为0 ，不属实1，赔偿2，买家违约3、卖家违约4、协商平退5）")
    private Integer disputeProcessType;

    /**
     * 复检类型（1：常规复检，2：争议复检，默认为0）
     */
    @ApiModelProperty(value = "复检类型（1：常规复检，2：争议复检，默认为0）")
    private Integer recheckType;

    /**
     * 复检时间
     */
    @ApiModelProperty(value = "复检时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date recheckTime;


    /**
     * 常规复检费
     */
    @ApiModelProperty(value = "常规复检费")
    private BigDecimal recheckFee;

    /**
     * 申请争议时间
     */
    @ApiModelProperty(value = "申请争议时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date disputeApplyTime;

    /**
     * 申请复检时间
     */
    @ApiModelProperty(value = "申请复检时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date recheckApplyTime;

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
    @ApiModelProperty(value = "订单状态")
//    @JsonSerialize(using = OrderStatusSerializer.class)
    private Integer status;

    /**
     * 购买时间
     */
    @ApiModelProperty(value = "购买时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date buyTime;

    /**
     * 争议初审时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    @ApiModelProperty(value = "争议初审时间")
    private Date disputeFirstAuditTime;
    /**
     * 争议初审状态(1:通过，2拒绝，3 待审，0默认）
     */
    @ApiModelProperty(value = "争议初审状态(1:通过，2拒绝，3 待审，0默认）")
    private Integer disputeFirstAuditStatus;

    /**
     * 争议初审人
     */
    @ApiModelProperty(value = "争议初审人")
    private String disputeFirstAuditUser;

    /**
     * 争议终审时间
     */
    @ApiModelProperty(value = "争议终审时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date disputeFinishAuditTime;


    /**
     * 争议终审状态
     */
    @ApiModelProperty(value = "争议终审状态")
    private Integer disputeFinishAuditStatus;

    /**
     * 争议终审人
     */
    @ApiModelProperty(value = "争议终审人")
    private String disputeFinishAuditUser;

    /**
     * 赔偿总金额
     */
    @ApiModelProperty(value = "赔偿总金额")
    private BigDecimal compensateMoney;

    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;

    /**
     * 银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;

    /**
     * 收款户名
     */
    @ApiModelProperty(value = "收款户名")
    private String bankUserName;

    /**
     * 争议备注
     */
    @ApiModelProperty(value = "争议备注")
    private String disputeRemark;

    /**
     * 争议初审备注
     */
    @ApiModelProperty(value = "争议初审备注")
    private String disputeFirstAuditRemark;

    /**
     * 争议终审备注
     */
    @ApiModelProperty(value = "争议终审备注")
    private String disputeFinishAuditRemark;

    /**
     * 订单争议项
     */
    @ApiModelProperty(value = "订单争议项")
    private List<DisputeItemVO> disputeItems;

    /**
     * 争议审核明细
     */
    @ApiModelProperty(value = "争议审核明细")
    private List<OrderAuditLogDTO> orderAuditLogs;

    /**
     * 过户时间
     */
    @ApiModelProperty(value = "过户时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date transferTime;
}
