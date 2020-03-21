package com.haoqi.magic.business.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.magic.business.serializer.*;
import com.haoqi.rigger.core.serializer.DateJsonSerializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 车辆订单前端展示信息
 *
 * @author twg
 * @since 2019/12/11
 */
@Data
public class CarOrderVO implements Serializable {

    private Long id;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;

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
     * vin
     */
    @ApiModelProperty(value = "vin")
    private String vin;

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
     * 撤销买入原因
     */
    @ApiModelProperty(value = "撤销买入原因")
    private String cancelBuyApplyReason;

    /**
     * 撤销买入审核描述
     */
    @ApiModelProperty(value = "撤销买入审核描述")
    private String cancelBuyAuditAuditRemark;

    /**
     * 争议状态 1 争议中 2 争议完成
     */
    @ApiModelProperty(value = "争议状态 1 争议中 2 争议完成")
//    @JsonSerialize(using = DisputeFlagSerializer.class)
    private Integer disputeFlag;

    /**
     * 复检状态（0：不复检，1：复检，默认为0）【只有主争议项才有该值】
     */
    @ApiModelProperty(value = "复检状态 0：不复检，1：复检，默认为0")
//    @JsonSerialize(using = CompleteStatusSerializer.class)

    private Integer recheckFlag;


    /**
     * 过户状态
     */
    @ApiModelProperty(value = "过户状态")
    //@JsonSerialize(using = TransferStatusSerializer.class)
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
     * 是否买方
     */
    @ApiModelProperty(value = "是否买方")
    private Boolean isBuyer;

    /**
     * 买方
     */
    @ApiModelProperty(value = "买方")
    private Long buyerUserId;

    /**
     * 车辆id
     */
    @ApiModelProperty(value = "车辆id")
    private Long csCarInfoId;

    /**
     * 车辆图片路径
     */
    @ApiModelProperty(value = "车辆图片路径")
    private String carImagePath;

    /**
     * 占位人
     */
    @ApiModelProperty(value = "占位人")
    private String operationingUser;

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
     * 检测员
     */
    @ApiModelProperty(value = "检测员")
    private String checkerName;
    /**
     * 审核员
     */
    @ApiModelProperty(value = "审核员")
    private String auditName;

    /**
     * 过户是否超时
     */
    @ApiModelProperty(value = "过户是否超时")
    @JsonSerialize(using = YesOrNoSerializer.class)
    private Integer transferDelay;

    /**
     * 打款是否超时
     */
    @ApiModelProperty(value = "打款是否超时")
    @JsonSerialize(using = YesOrNoSerializer.class)
    private Integer paymentDelay;

    /**
     * 复检结果
     */
    @ApiModelProperty(value = "复检结果")
    @JsonSerialize(using = RecheckResultSerializer.class)
    private Integer recheckResult;

    /**
     * 争议处理方式
     */
    @ApiModelProperty(value = "争议处理方式")
//    @JsonSerialize(using = DisputeProcessTypeSerializer.class)
    private Integer disputeProcessType;

    /**
     * 复检时间
     */
    @ApiModelProperty(value = "复检时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date recheckTime;

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
     * 拒绝卖出时间
     */
    @ApiModelProperty(value = "拒绝卖出时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date refuseSellTime;


    /**
     * 完成时间
     */
    @ApiModelProperty(value = "完成时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date finishTime;


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
     * 撤销买入审核时间
     */
    @ApiModelProperty(value = "撤销买入审核时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date cancelBuyAuditAuditTime;

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
     * 争议初审时间
     */
    @ApiModelProperty(value = "争议初审时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date disputeFirstAuditTime;
    /**
     * 争议初审状态
     */
    @ApiModelProperty(value = "争议初审状态")
//    @JsonSerialize(using = OrderAuditStatusSerializer.class)
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
     * 撤销买入时间
     */
    @ApiModelProperty(value = "撤销买入时间")
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date cancelBuyApplyTime;

    /**
     * 争议终审状态
     */
    @ApiModelProperty(value = "争议终审状态")
//    @JsonSerialize(using = OrderAuditStatusSerializer.class)
    private Integer disputeFinishAuditStatus;

    /**
     * 争议终审人
     */
    @ApiModelProperty(value = "争议终审人")
    private String disputeFinishAuditUser;

    /**
     * 过户审核状态
     */
    @ApiModelProperty(value = "过户审核状态")
//    @JsonSerialize(using = OrderAuditStatusSerializer.class)
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
     * 撤销买入审核状态
     */
    @ApiModelProperty(value = "撤销买入审核状态")
//    @JsonSerialize(using = OrderAuditStatusSerializer.class)
    private Integer cancelBuyAuditStatus;

    /**
     * 车商id
     */
    @ApiModelProperty(value = "车商id")
    private Long sysCarDealerId;

    /**
     * 打款状态
     */
    @ApiModelProperty(value = "打款状态")
//    @JsonSerialize(using = PaymentStatusSerializer.class)
    private Integer paymentStatus;


    /**
     * 是否显示过户按钮
     */
    @ApiModelProperty(value = "是否显示过户按钮")
    private Boolean showTransferButton;

    /**
     * 是否显示争议按钮
     */
    @ApiModelProperty(value = "是否显示争议按钮")
    private Boolean showDisputeButton;

    /**
     * 是否显示再次争议按钮
     */
    @ApiModelProperty(value = "是否显示再次争议按钮")
    private Boolean showReDisputeButton;
    /**
     * 争议处理人
     */
    private String disputeProcessUser;

    /**
     * 处理时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date disputeProcessTime;

    /**
     * 车辆状态
     */
    @ApiModelProperty(value = "车辆状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）")
    private Integer publishStatus;
    /**
     * 交易状态
     */
    @ApiModelProperty(value = "车辆交易标识（1：交易中，0：未交易，2：交易结束，默认为0）")
    private Integer tradeFlag;
    /**
     * 车辆编号
     */
    @ApiModelProperty(value = "车辆编号")
    private String carNo;
    /**
     * 订单创建时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date gmtCreate;

    /**
     * 复检状态（1完成，0默认）
     */
    @ApiModelProperty(value = "复检状态（1完成，0默认）")
    private Integer recheckStatus;
}
