package com.haoqi.magic.business.model.dto;

import com.haoqi.magic.business.model.vo.DisputeItemVO;
import com.haoqi.magic.business.model.vo.RecheckFileVO;
import com.haoqi.magic.business.model.vo.TransferFileVO;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author twg
 * @since 2019/12/4
 */
@Data
public class CarOrderDTO implements Serializable {

    /**
     * 订单id
     */
    private Long id;
    /**
     * 订单创建时间
     */
    private Date gmtCreate;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * VIN
     */
    private String vin;

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
     * 撤销买入原因
     */
    private String cancelBuyApplyReason;

    /**
     * 撤销买入审核描述
     */
    private String cancelBuyAuditAuditRemark;

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
    private String displacementType;
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
     * 是否买方
     */
    private Boolean isBuyer = false;

    /**
     * 买方
     */
    private Long buyerUserId;

    /**
     * 车辆id
     */
    private Long csCarInfoId;

    /**
     * 车辆图片路径
     */
    private String carImagePath;

    /**
     * 占位人
     */
    private String operationingUser;

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
     * 检测员
     */
    private String checkerName;
    /**
     * 审核员
     */
    private String auditName;

    /**
     * 过户是否超时
     */
    private Integer transferDelay;

    /**
     * 打款是否超时
     */
    private Integer paymentDelay;

    /**
     * 复检结果
     */
    private String recheckResult;

    /**
     * 争议处理方式
     */
    private Integer disputeProcessType;

    /**
     * 复检时间
     */
    private Date recheckTime;

    /**
     * 申请争议时间
     */
    private Date disputeApplyTime;

    /**
     * 申请复检时间
     */
    private Date recheckApplyTime;

    /**
     * 卖出时间
     */
    private Date sellTime;

    /**
     * 拒绝卖出时间
     */
    private Date refuseSellTime;


    /**
     * 审核时间
     */
    private Date auditTime;


    /**
     * 初始登记日期
     */
    private Date initDate;

    /**
     * 发布时间
     */
    private Date publishTime;

    /**
     * 撤销买入审核时间
     */
    private Date cancelBuyAuditAuditTime;

    /**
     * 订单状态
     */
    private Integer status;

    /**
     * 购买时间
     */
    private Date buyTime;

    /**
     * 过户审核时间
     */
    private Date transferAuditTime;

    /**
     * 申请过户时间
     */
    private Date transferApplyTime;
    /**
     * 争议初审时间
     */
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
    private Date disputeFinishAuditTime;

    /**
     * 撤销买入时间
     */
    private Date cancelBuyApplyTime;

    /**
     * 争议终审状态
     */
    private Integer disputeFinishAuditStatus;

    /**
     * 争议终审人
     */
    private String disputeFinishAuditUser;

    /**
     * 过户审核状态
     */
    private Integer transferAuditStatus;

    /**
     * 过户审核人
     */
    private String transferAuditUser;

    /**
     * 过户审核备注
     */
    private String transferAuditRemark;


    /**
     * 撤销买入审核状态
     */
    private Integer cancelBuyAuditStatus;

    /**
     * 车商id
     */
    private Long sysCarDealerId;

    /**
     * 打款状态
     */
    private Integer paymentStatus;


    /**
     * 是否显示过户按钮
     */
    private boolean showTransferButton;

    /**
     * 是否显示争议按钮
     */
    private boolean showDisputeButton;

    /**
     * 是否显示再次争议按钮
     */
    private boolean showReDisputeButton;


    /**
     * 赔偿总金额
     */
    private BigDecimal compensateMoney;

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
     * 争议备注
     */
    private String disputeRemark;

    /**
     * 审核详情
     */
    private String auditJsonContent;

    /**
     * 订单过户附件
     */
    private List<TransferFileVO> transferFiles;

    /**
     * 订单争议项
     */
    private List<DisputeItemVO> disputeItems;

    /**
     * 复检信息
     */
    private List<RecheckFileVO> recheckFiles;

    /**
     * 过户日期
     */
    private Date transferTime;
    /**
     * 过户备注
     */
    private String transferRemark;

    /**
     * 争议审核详情
     */
    private String disputeAuditJsonContent;

    /**
     * 争议处理人
     */
    private String disputeProcessUser;

    /**
     * 处理时间
     */
    private Date disputeProcessTime;

    /**
     * 车辆状态
     */
    private Integer publishStatus;
    /**
     * 交易状态
     */
    private Integer tradeFlag;
    /**
     * 车辆编号
     */
    private String carNo;
    /**
     * 复检结果（正常，异常,1:正常，2：异常，默认0）
     */
    private Integer recheckStatus;

    /**
     * 完成时间
     */
    private Date finishTime;

    /**
     * 复检类型（1：常规复检，2：争议复检，默认为0）
     */
    private Integer recheckType;

    /**
     * 常规复检费
     */
    private BigDecimal recheckFee;
    /**
     * 争议初审备注
     */
    private String disputeFirstAuditRemark;
}
