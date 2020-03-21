package com.haoqi.magic.business.model.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.magic.business.serializer.OrderStatusSerializer;
import com.haoqi.rigger.core.serializer.DateJsonSerializer;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单、过户、争议信息
 *
 * @author twg
 * @since 2019/12/11
 */
@Data
public class OrderTransferDisputeVO implements Serializable {
    /**
     * 订单编号
     */
    private String orderNo;

    /**
     * 车型
     */
    private String sysCarModelName;

    /**
     * 颜色
     */
    private String color;

    /**
     * 车辆售价
     */
    private BigDecimal salPrice;

    /**
     * 服务费
     */
    private BigDecimal serviceFee;


    /**
     * 争议状态
     */
    private Integer disputeFlag;
    /**
     * 复检状态
     */
    private Integer recheckFlag;

    /**
     * 过户状态
     */
    private Integer transferStatus;

    /**
     * 城市
     */
    private String city;

    /**
     * 【冗余】排量
     */
    private Double displacement;
    /**
     * 【冗余】排放标准
     */
    private String emissionStandard;
    /**
     * 【冗余】变速箱
     */
    private String gearBox;
    /**
     * 【冗余】表显里程
     */
    private BigDecimal instrumentShowDistance;

    /**
     * 车牌号归属地
     */
    private String plateNoShortName;

    /**
     * 车商名
     */
    private String carDealerName;

    /**
     * 车商联系人
     */
    private String carDealerUser;

    /**
     * 车商联系人手机号
     */
    private String carDealerUserTel;

    /**
     * 买方姓名
     */
    private String buyerName;
    /**
     * 买方电话
     */
    private String buyerTel;

    /**
     * 过户是否超时
     */
    private Integer transferDelay;

    /**
     * 打款是否超时
     */
    private Integer paymentDelay;

    /**
     * 争议处理方式
     */
    private Integer disputeProcessType;

    /**
     * 复检时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date recheckTime;

    /**
     * 申请争议时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date disputeApplyTime;

    /**
     * 申请复检时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date recheckApplyTime;

    /**
     * 卖出时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date sellTime;

    /**
     * 审核时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date auditTime;


    /**
     * 初始登记日期
     */
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date initDate;

    /**
     * 发布时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date publishTime;

    /**
     * 订单状态
     */
    @JsonSerialize(using = OrderStatusSerializer.class)
    private Integer status;

    /**
     * 购买时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date buyTime;

    /**
     * 争议初审时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date disputeFirstAuditTime;
    /**
     * 争议初审状态
     */
    private Integer disputeFirstAuditStatus;

    /**
     * 争议初审人
     */
    private String disputeFirstAuditUser;

    /**
     * 争议终审时间
     */
    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date disputeFinishAuditTime;


    /**
     * 争议终审状态
     */
    private Integer disputeFinishAuditStatus;

    /**
     * 争议终审人
     */
    private String disputeFinishAuditUser;


    /**
     * 订单争议项
     */
    private List<DisputeItemVO> disputeItems;

    /**
     * 订单过户附件
     */
    private List<TransferFileVO> transferFiles;
}
