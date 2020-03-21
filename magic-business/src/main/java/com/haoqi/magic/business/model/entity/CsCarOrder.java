package com.haoqi.magic.business.model.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易订单表
 * </p>
 *
 * @author twg
 * @since 2019-12-03
 */
@Data
@Accessors(chain = true)
public class CsCarOrder extends Model<CsCarOrder> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 创建人
     */
    @TableField(value = "creator", fill = FieldFill.INSERT)
    private Long creator;
    /**
     * 修改人
     */
    @TableField(value = "modifier", fill = FieldFill.INSERT_UPDATE)
    private Long modifier;
    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
    /**
     * 注释
     */
    @TableField("remark")
    private String remark;
    /**
     * 是否删除 0否1是
     */
    @TableField("is_deleted")
    private Integer isDeleted;
    /**
     * 关联的车辆
     */
    @TableField("cs_car_info_id")
    private Long csCarInfoId;
    /**
     * 1买入、4撤销买入申请、7撤销买入通过、10拒绝卖出、13卖出、16买家支付、19过户（已过户待打款）、22 打款（已打款待过户）、25 完成（下架），默认为1
     */
    @TableField("status")
    private Integer status;

    /**
     * 付款是否超时(1:超时，0：未超时，默认为0）
     */
    @TableField("payment_delay")
    private Integer paymentDelay;
    /**
     * 过户状态（2：已过户，1：过户中，0：未过户。默认为0）
     */
    @TableField("transfer_status")
    private Integer transferStatus;
    /**
     * 过户时间（手动输入的）
     */
    @TableField("transfer_time")
    private Date transferTime;
    /**
     * 过户备注
     */
    @TableField("transfer_remark")
    private String transferRemark;
    /**
     * 申请过户的人
     */
    @TableField("transfer_apply_user")
    private String transferApplyUser;
    /**
     * 申请过户时间
     */
    @TableField("transfer_apply_time")
    private Date transferApplyTime;
    /**
     * 过户是否超时(1:超时，0：未超时，默认为0）
     */
    @TableField("transfer_delay")
    private Integer transferDelay;
    /**
     * 申请撤销买入时间
     */
    @TableField("cancel_buy_apply_time")
    private Date cancelBuyApplyTime;
    /**
     * 申请撤销买入原因
     */
    @TableField("cancel_buy_apply_reason")
    private String cancelBuyApplyReason;
    /**
     * 是否是同城（1：同城，0：跨城，默认为0）
     */
    @TableField("same_city_flag")
    private Integer sameCityFlag;
    /**
     * 申请复检人
     */
    @TableField("recheck_apply_user")
    private String recheckApplyUser;
    /**
     * 申请复检时间
     */
    @TableField("recheck_apply_time")
    private Date recheckApplyTime;
    /**
     * 复检类型（1：常规复检，2：争议复检，默认为0）
     */
    @TableField("recheck_type")
    private Integer recheckType;
    /**
     * 是否复检（1：复检，0：不需要复检，默认为0）
     */
    @TableField("recheck_flag")
    private Integer recheckFlag;
    /**
     * 复检结果（正常，异常,1:正常，2：异常，默认0）
     */
    @TableField("recheck_result")
    private Integer recheckResult;
    /**
     * 复检状态（1完成，2未完成，0默认）
     */
    @TableField("recheck_status")
    private Integer recheckStatus;
    /**
     * 复检时间
     */
    @TableField("recheck_time")
    private Date recheckTime;
    /**
     * 复检员
     */
    @TableField("recheck_user_id")
    private Long recheckUserId;
    /**
     * 订单编号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 【冗余字段】车型名称
     */
    @TableField("sys_car_model_name")
    private String sysCarModelName;
    /**
     * 【冗余字段】车辆品牌名称
     */
    @TableField("sys_car_brand_name")
    private String sysCarBrandName;
    /**
     * 【冗余字段】车系名称
     */
    @TableField("sys_car_series_name")
    private String sysCarSeriesName;
    /**
     * 【冗余】颜色
     */
    @TableField("color")
    private String color;
    /**
     * 车价（销售价）
     */
    @TableField("sal_price")
    private BigDecimal salPrice;
    /**
     * 服务费
     */
    @TableField("service_fee")
    private BigDecimal serviceFee;
    /**
     * 【冗余】VIN
     */
    @TableField("vin")
    private String vin;
    /**
     * 【冗余】车辆所在地
     */
    @TableField("locate")
    private String locate;
    /**
     * 【冗余】车辆归属地（浙A）
     */
    @TableField("plate_no_short_name")
    private String plateNoShortName;
    /**
     * 【冗余】初始登记日期
     */
    @TableField("init_date")
    private Date initDate;
    /**
     * 【冗余】排量
     */
    @TableField("displacement")
    private Double displacement;
    /**
     * 【冗余】排放标准
     */
    @TableField("emission_standard")
    private String emissionStandard;
    /**
     * 【冗余】变速箱
     */
    @TableField("gear_box")
    private String gearBox;
    /**
     * 【冗余】表显里程
     */
    @TableField("instrument_show_distance")
    private BigDecimal instrumentShowDistance;
    /**
     * 【冗余】车辆发布时间
     */
    @TableField("publish_time")
    private Date publishTime;
    /**
     * 车商id
     */
    @TableField("sys_car_dealer_id")
    private Long sysCarDealerId;
    /**
     * 【冗余】车商所在省
     */
    @TableField("province")
    private String province;
    /**
     * 【冗余】车商所在市
     */
    @TableField("city")
    private String city;
    /**
     * 【冗余】车商中文名称
     */
    @TableField("car_dealer_name")
    private String carDealerName;
    /**
     * 【冗余】车商联系人
     */
    @TableField("car_dealer_user")
    private String carDealerUser;
    /**
     * 车商userId
     */
    @TableField("car_dealer_user_id")
    private Long carDealerUserId;
    /**
     * 【冗余】车商联系人电话
     */
    @TableField("car_dealer_user_tel")
    private String carDealerUserTel;
    /**
     * 【冗余】车商冻结金额
     */
    @TableField("car_dealer_frozen_money")
    private BigDecimal carDealerFrozenMoney;
    /**
     * 【冗余】买方冻结金额
     */
    @TableField("buyer_frozen_money")
    private BigDecimal buyerFrozenMoney;
    /**
     * 买方user_id
     */
    @TableField("buyer_user_id")
    private Long buyerUserId;
    /**
     * 【冗余】买方姓名
     */
    @TableField("buyer_name")
    private String buyerName;
    /**
     * 【冗余】买方手机号
     */
    @TableField("buyer_tel")
    private String buyerTel;
    /**
     * 【冗余】检测员姓名
     */
    @TableField("checker_name")
    private String checkerName;
    /**
     * 【冗余】车辆审核员姓名
     */
    @TableField("audit_name")
    private String auditName;
    /**
     * 【冗余】车辆审核描述
     */
    @TableField("audit_remark")
    private String auditRemark;
    /**
     * 【冗余】车辆审核时间
     */
    @TableField("audit_time")
    private Date auditTime;
    /**
     * 卖出时间
     */
    @TableField("sell_time")
    private Date sellTime;
    /**
     * 拒绝卖出时间
     */
    @TableField("refuse_sell_time")
    private Date refuseSellTime;
    /**
     * 完成时间
     */
    @TableField("finish_time")
    private Date finishTime;
    /**
     * 申请争议的人
     */
    @TableField("dispute_apply_user")
    private String disputeApplyUser;
    /**
     * 申请争议时间
     */
    @TableField("dispute_apply_time")
    private Date disputeApplyTime;
    /**
     * 是否有争议（2：争议完成 1：争议中，0：没有争议，默认为0）
     */
    @TableField("dispute_flag")
    private Integer disputeFlag;
    /**
     * 争议备注
     */
    @TableField("dispute_remark")
    private String disputeRemark;
    /**
     * 争议补录
     */
    @TableField("dispute_additional")
    private String disputeAdditional;
    /**
     * 争议补录人
     */
    @TableField("dispute_additional_user")
    private String disputeAdditionalUser;
    /**
     * 争议补录userid
     */
    @TableField("dispute_additional_user_id")
    private Long disputeAdditionalUserId;
    /**
     * 争议补录时间
     */
    @TableField("dispute_additional_time")
    private Date disputeAdditionalTime;
    /**
     * 争议处理方式（默认为0    ，不属实1，赔偿2，买家违约3、卖家违约4、协商平退5）
     */
    @TableField("dispute_process_type")
    private Integer disputeProcessType;

    /**
     * 【冗余】维保URL
     */
    @TableField("maintenance_url")
    private String maintenanceUrl;
    /**
     * 【冗余】出险URL
     */
    @TableField("insurance_url")
    private String insuranceUrl;
    /**
     * 占位人
     */
    @TableField("operationing_user")
    private String operationingUser;
    /**
     * 占位时间
     */
    @TableField("operationing_time")
    private Date operationingTime;
    /**
     * 【冗余】赔偿总金额
     */
    @TableField("compensate_money")
    private BigDecimal compensateMoney;

    /**
     * 复检人员姓名
     */
    @TableField("recheck_user_name")
    private String recheckUserName;

    /**
     * 争议处理时间
     */
    @TableField("dispute_process_time")
    private Date disputeProcessTime;

    /**
     * 争议待审状态(1:待初审，2待终审，0默认）
     */
    @TableField("dispute_audit_status")
    private Integer disputeAuditStatus;

    /***
     * 排量类型 : 排量类型: L T
     */
    @TableField("displacement_type")
    private String displacementType;

    /**
     * 争议处理人
     */
    @TableField("dispute_process_user")
    private String disputeProcessUser;

    /**
     * 争议处理人id
     */
    @TableField("dispute_process_user_id")
    private Long disputeProcessUserId;

    /**
     * 过户费
     */
    @TableField("transfer_money")
    private BigDecimal transferMoney;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
