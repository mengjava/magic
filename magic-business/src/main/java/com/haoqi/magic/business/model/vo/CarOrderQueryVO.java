package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 条件查询实体
 *
 * @author twg
 * @since 2019/12/5
 */
@Data
public class CarOrderQueryVO extends Page {

    /**
     * vin码
     */
    @ApiModelProperty(value = "vin码")
    private String vin;

    /**
     * 车商id
     */
    @ApiModelProperty(value = "车商id")
    private Long sysCarDealerId;

    /**
     * 车商名
     */
    @ApiModelProperty(value = "车商名")
    private String carDealerName;

    /**
     * 【冗余字段】车型名称
     */
    @ApiModelProperty(value = "【冗余字段】车型名称")
    private String sysCarModelName;
    /**
     * 【冗余字段】车辆品牌名称
     */
    @ApiModelProperty(value = "【冗余字段】车辆品牌名称")
    private String sysCarBrandName;
    /**
     * 【冗余字段】车系名称
     */
    @ApiModelProperty(value = "【冗余字段】车系名称")
    private String sysCarSeriesName;

    /**
     * 变速箱
     */
    @ApiModelProperty(value = "变速箱")
    private String gearBox;

    /**
     * 排放标准
     */
    @ApiModelProperty(value = "排放标准")
    private String emissionStandard;

    /**
     * 订单状态
     */
    @ApiModelProperty(value = "订单状态")
    private Integer status;

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
     * 争议初审审核状态(1:通过，2拒绝，0默认）
     */
    @ApiModelProperty(value = "争议初审审核状态(1:通过，2拒绝，0默认）")
    private Integer disputeFirstAuditStatus;

    /**
     * 争议终审审核状态(1:通过，2拒绝，0默认）
     */
    @ApiModelProperty(value = "争议终审审核状态(1:通过，2拒绝，0默认）")
    private Integer disputeFinishAuditStatus;

    /**
     * 撤销买入审核状态(1:通过，2拒绝，0默认）
     */
    @ApiModelProperty(value = "撤销买入审核状态(1:通过，2拒绝，0默认）")
    private Integer cancelBuyAuditStatus;

    /**
     * 过户是否超时(1:超时，0：未超时，默认为0）
     */
    @ApiModelProperty(value = "过户是否超时(1:超时，0：未超时，默认为0）")
    private Integer transferDelay;

    /**
     * 过户状态（1：已过户，0：未过户。默认为0）
     */
    @ApiModelProperty(value = "过户状态（1：已过户，0：未过户。默认为0）")
    private Integer transferStatus;

    /**
     * 过户审核状态(1:通过，2拒绝，0默认）
     */
    @ApiModelProperty(value = "过户审核状态(1:通过，2拒绝，0默认）")
    private Integer transferAuditStatus;

    /**
     * 打款是否超时(1:超时，0：未超时，默认为0）
     */
    @ApiModelProperty(value = "打款是否超时(1:超时，0：未超时，默认为0）")
    private Integer paymentDelay;

    /**
     * 初始登记日期开始
     */
    @ApiModelProperty(value = "初始登记日期开始")
    private String initDateStart;
    /**
     * 初始登记日期结束
     */
    @ApiModelProperty(value = "初始登记日期结束")
    private String initDateEnd;

    /**
     * 卖出时间开始
     */
    @ApiModelProperty(value = "卖出时间开始")
    private String sellTimeStart;
    /**
     * 卖出时间结束
     */
    @ApiModelProperty(value = "卖出时间结束")
    private String sellTimeEnd;

    /**
     * 车价开始
     */
    @ApiModelProperty(value = "车价开始")
    private String salPriceStart;
    /**
     * 车价结束
     */
    @ApiModelProperty(value = "车价结束")
    private String salPriceEnd;

    /**
     * 表显里程数开始
     */
    @ApiModelProperty(value = "表显里程数开始")
    private String mileageNumberStart;

    /**
     * 表显里程数结束
     */
    @ApiModelProperty(value = "表显里程数结束")
    private String mileageNumberEnd;

    /**
     * 复检结果
     */
    @ApiModelProperty(value = "复检结果")
    private Integer recheckResult;

    /**
     * 复检状态
     */
    @ApiModelProperty(value = "复检状态")
    private Integer recheckStatus;

    /**
     * 买入时间开始
     */
    @ApiModelProperty(value = "买入时间开始")
    private String buyTimeStart;
    /**
     * 买入时间结束
     */
    @ApiModelProperty(value = "买入时间结束")
    private String buyTimeEnd;

    /**
     * 申请复检时间开始
     */
    @ApiModelProperty(value = "申请复检时间开始")
    private String recheckApplyTimeStart;
    /**
     * 申请复检时间结束
     */
    @ApiModelProperty(value = "申请复检时间结束")
    private String recheckApplyTimeEnd;

    /**
     * 复检时间开始
     */
    @ApiModelProperty(value = "复检时间开始")
    private String recheckTimeStart;

    /**
     * 复检时间结束
     */
    @ApiModelProperty(value = "复检时间结束")
    private String recheckTimeEnd;

    /**
     * 申请争议时间开始
     */
    @ApiModelProperty(value = "申请争议时间开始")
    private String disputeApplyTimeStart;
    /**
     * 申请争议时间结束
     */
    @ApiModelProperty(value = "申请争议时间结束")
    private String disputeApplyTimeEnd;

    /**
     * 申请过户时间开始
     */
    @ApiModelProperty(value = "申请过户时间开始")
    private String transferApplyTimeStart;
    /**
     * 申请过户时间结束
     */
    @ApiModelProperty(value = "申请过户时间结束")
    private String transferApplyTimeEnd;

    /**
     * 过户审核时间开始
     */
    @ApiModelProperty(value = "过户审核时间开始")
    private String transferAuditTimeStart;

    /**
     * 过户审核时间结束
     */
    @ApiModelProperty(value = "过户审核时间结束")
    private String transferAuditTimeEnd;

    /**
     * 争议初审时间开始
     */
    @ApiModelProperty(value = "争议初审时间开始")
    private String disputeFirstAuditTimeStart;
    /**
     * 争议初审时间结束
     */
    @ApiModelProperty(value = "争议初审时间结束")
    private String disputeFirstAuditTimeEnd;

    /**
     * 争议终审时间开始
     */
    @ApiModelProperty(value = "争议终审时间开始")
    private String disputeFinishAuditTimeStart;
    /**
     * 争议终审时间结束
     */
    @ApiModelProperty(value = "争议终审时间结束")
    private String disputeFinishAuditTimeEnd;

    /**
     * 申请撤销买入时间开始
     */
    @ApiModelProperty(value = "申请撤销买入时间开始")
    private String cancelBuyApplyTimeStart;
    /**
     * 申请撤销买入时间结束
     */
    @ApiModelProperty(value = "申请撤销买入时间结束")
    private String cancelBuyApplyTimeEnd;

    /**
     * 撤销买入审核时间开始
     */
    @ApiModelProperty(value = "撤销买入审核时间开始")
    private String cancelBuyAuditAuditTimeStart;

    /**
     * 撤销买入审核时间结束
     */
    @ApiModelProperty(value = "撤销买入审核时间结束")
    private String cancelBuyAuditAuditTimeEnd;

    /**
     * 争议处理时间开始
     */
    @ApiModelProperty(value = "争议处理时间开始")
    private String disputeProcessTimeStart;

    /**
     * 争议处理时间结束
     */
    @ApiModelProperty(value = "争议处理时间结束")
    private String disputeProcessTimeEnd;

    /**
     * 买方手机号
     */
    @ApiModelProperty(value = "买方手机号")
    private String buyerTel;

    /**
     * 卖方手机号
     */
    @ApiModelProperty(value = "卖方手机号")
    private String carDealerUserTel;

    @ApiModelProperty(value = "状态（0：保存，1：已提交/待审/发布  ， 2：上架/审核通过 ，-1审核退回，-2下架，3调拨）")
    private Integer publishStatus;

	/**
	 * 车辆号码
	 */
	@ApiModelProperty(value = "车辆号码")
	private String plateNoShortName;
}
