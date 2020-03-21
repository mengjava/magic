package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * <p>
 * 财务打款表查询条件
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
@Data
public class CsFinancePayMoneyQueryVO extends Page {

    private static final long serialVersionUID = 1L;


    /**
     * 车辆编号
     */
    @ApiModelProperty(value = "车辆编号 订单编号")
    private String orderNo;
    /**
     * VIN
     */
    @ApiModelProperty(value = "vin")
    private String vin;
    /**
     * 车辆号码
     */
    @ApiModelProperty(value = "车辆号码")
    private String plateNoShortName;
    /**
     * 车款车型
     */
    @ApiModelProperty(value = "车款车型")
    private String sysCarModelName;
    /**
     * 买卖状态
     */
    private Integer status;

    /**
     * 争议处理结果
     */
    @ApiModelProperty(value = "争议处理结果")
    private Integer disputeProcessType;
    /**
     * 打款
     */
    private Integer paymentStatus;
    /**
     * 收款方式
     */
    @ApiModelProperty(value = "收款方式")
    private Integer paymentType;


    /**
     * 买家支付时间
     */
    @ApiModelProperty(value = "买家支付时间开始")
    private String payTimeBegin;
    @ApiModelProperty(value = "买家支付时间结束")
    private String payTimeEnd;

    /**
     * 打款时间
     */
    @ApiModelProperty(value = "打款时间开始")
    private String paymentTimeBegin;
    @ApiModelProperty(value = "打款时间结束")
    private String paymentTimeEnd;

    @ApiModelProperty(value = "过户审核时间开始")
    private String transferAuditTimeBegin;
    @ApiModelProperty(value = "过户审核时间结束")
    private String transferAuditTimeEnd;
    @ApiModelProperty(value = "争议初审时间开始")
    private String disputeFirstAuditTimeBegin;
    @ApiModelProperty(value = "争议初审时间结束")
    private String disputeFirstAuditTimeEnd;
    @ApiModelProperty(value = "争议终审时间开始")
    private String disputeFinishAuditTimeBegin;
    @ApiModelProperty(value = "争议终审时间结束")
    private String disputeFinishAuditTimeEnd;
    /**
     * 买卖交易状态
     */
    private Integer tradeFlag;

}
