package com.haoqi.magic.business.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 争议处理详情
 *
 * @author twg
 * @since 2019/12/24
 */
@Data
public class DisputeHandlerDetailVO implements Serializable {
    /**
     * 审核类型
     */
    private String auditTypeName;
    /**
     * 审核结果
     */
    private String auditResult;
    /**
     * 审核备注
     */
    private String auditRemark;

    /**
     * 审核人
     */
    private String auditUser;

    /**
     * 审核时间
     */
    private String auditTime;


    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 收款户名
     */
    private String bankUserName;

    /**
     * 车辆售价
     */
    private BigDecimal salPrice;

    /**
     * 服务费
     */
    private BigDecimal serviceFee;

    /**
     * 订单争议项
     */
    private List<DisputeItemVO> disputeItems;

}
