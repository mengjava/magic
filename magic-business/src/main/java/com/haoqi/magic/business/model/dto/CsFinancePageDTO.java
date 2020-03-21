package com.haoqi.magic.business.model.dto;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 功能描述:
 * 财务打款列表
 *
 * @Author: yanhao
 * @Date: 2019/12/13 10:22
 * @Param:
 * @Description:
 */
@Data
public class CsFinancePageDTO implements Serializable {
    /**
     * 主键
     */
    @ApiModelProperty(value = "打款id")
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long csCarOrderId;
    /**
     * 关联的车辆
     */
    @ApiModelProperty(value = "关联的车辆id")
    private Long csCarInfoId;

    /**
     * 车商id
     */
    @ApiModelProperty(value = "车商id")
    private Long sysCarDealerId;
    /**
     * 车商userId
     */
    @ApiModelProperty(value = "车商userId")
    private Long carDealerUserId;


    /**
     * 1买入、4撤销买入申请、7撤销买入通过、10拒绝卖出、13卖出、16买家支付、19过户（已过户待打款）、22 打款（已打款待过户）、25 完成（下架），默认为1
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "状态str")
    private String statusStr;
    /**
     * 打款状态（1：已打款，0：未打款，默认为0）
     */
    @ApiModelProperty(value = "打款状态（1：已打款，0：未打款，默认为0）")
    private Integer paymentStatus;
    /**
     * 打款时间
     */
    @ApiModelProperty(value = "打款时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date paymentTime;

    /**
     * 是否是同城（1：同城，0：跨城，默认为0）
     */
    @ApiModelProperty(value = "是否是同城（1：同城，0：跨城，默认为0）")
    private Integer sameCityFlag;
    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNo;
    /**
     * 【冗余字段】车型名称
     */
    @ApiModelProperty(value = "车型名称")
    private String sysCarModelName;
    /**
     * 【冗余字段】车辆品牌名称
     */
    @ApiModelProperty(value = "车辆品牌名称")
    private String sysCarBrandName;
    /**
     * 【冗余字段】车系名称
     */
    @ApiModelProperty(value = "车系名称")
    private String sysCarSeriesName;
    /**
     * 车价（销售价）
     */
    @ApiModelProperty(value = "车价（销售价）")
    private BigDecimal salPrice;
    /**
     * 服务费
     */
    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceFee;
    /**
     * 【冗余】VIN
     */
    @ApiModelProperty(value = "VIN")
    private String vin;
    /**
     * 【冗余】车辆所在地
     */
    @ApiModelProperty(value = "车辆所在地")
    private String locate;
    /**
     * 【冗余】车辆号码
     */
    @ApiModelProperty(value = "车辆号码")
    private String plateNoShortName;
    /**
     * 【冗余】初始登记日期
     */
    @ApiModelProperty(value = "初始登记日期")
    @JsonFormat(pattern = DatePattern.NORM_DATE_PATTERN, timezone = "GMT+8")
    private Date initDate;
    /**
     * 【冗余】车辆发布时间
     */
    @ApiModelProperty(value = "车辆发布时间")
    private Date publishTime;

    /**
     * 申请争议时间
     */
    @ApiModelProperty(value = "申请争议时间")
    private Date disputeApplyTime;
    /**
     * 是否有争议（2：争议完成 1：争议中，0：没有争议，默认为0）
     */
    @ApiModelProperty(value = "是否有争议（2：争议完成 1：争议中，0：没有争议，默认为0）")
    private Integer disputeFlag;
    /**
     * 争议处理方式（默认为0    ，不属实1，赔偿2，买家违约3、卖家违约4、协商平退5）
     */
    @ApiModelProperty(value = "争议处理方式（默认为0    ，不属实1，赔偿2，买家违约3、卖家违约4、协商平退5）")
    private Integer disputeProcessType;
    /**
     * 中文 争议处理方式
     */
    @ApiModelProperty(value = "中文 争议处理方式")
    private String disputeProcessTypeStr;
    @ApiModelProperty(value = "款项类别")
    private String disputeProcessMoneyStr;


    /**
     * 【只用于赔偿，冗余】银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /**
     * 【只用于赔偿，冗余】银行卡号
     */
    @ApiModelProperty(value = "银行卡号")
    private String bankCardNo;
    /**
     * 【只用于赔偿，冗余】户名
     */
    @ApiModelProperty(value = "户名")
    private String bankUserName;
    /**
     * 【只用于赔偿，冗余】手机号
     */
    @ApiModelProperty(value = "手机号")
    private String bankUserTel;
    /**
     * 【只用于赔偿，冗余】身份证号（非必填）
     */
    @ApiModelProperty(value = "身份证号（非必填）")
    private String bankUserCardNo;
    /**
     * 占位人
     */
    @ApiModelProperty(value = "占位人")
    private String operationingUser;
    /**
     * 占位时间
     */
    @ApiModelProperty(value = "占位时间")
    private Date operationingTime;

    /**
     * 打款金额
     */
    @ApiModelProperty(value = "打款金额")
    private BigDecimal financePayMoney;

    /**
     * 交易收款方式（1：先打款后过户，0：先过户后打款，默认0）
     */
    @ApiModelProperty(value = "交易收款方式（1：先打款后过户，0：先过户后打款，默认0）")
    private Integer paymentType;
    @ApiModelProperty(value = "交易收款方式字段")
    private String paymentTypeStr;


    /**
     * 过户审核时间
     */
    @ApiModelProperty(value = "过户审核时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date transferAuditTime;
    /**
     * 争议初审时间
     */
    @ApiModelProperty(value = "争议初审时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date disputeFirstAuditTime;
    /**
     * 争议终审时间
     */
    @ApiModelProperty(value = "争议终审时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date disputeFinishAuditTime;
    /**
     * 打款时间
     */
    @ApiModelProperty(value = "买家支付打款时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
    private Date payTime;


    @ApiModelProperty(value = "打款方式(1:打款买家 2:打款卖家 3:收款信息)")
    private Integer payMethodType;

    /**
     * 款项类型（1车款+服务费 + 复检费 - 过户费，0车款-检测费, 3赔偿金）
     */
    private Integer payType;
    /**
     * 打款金额
     */
    private BigDecimal payMoney;
}
