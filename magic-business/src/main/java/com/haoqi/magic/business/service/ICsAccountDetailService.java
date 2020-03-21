package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.AccountTotalAmountDTO;
import com.haoqi.magic.business.model.dto.PaymentCallBackDTO;
import com.haoqi.magic.business.model.dto.PaymentOrderDTO;
import com.haoqi.magic.business.model.entity.CsAccountDetail;
import com.haoqi.rigger.mybatis.Query;

import java.math.BigDecimal;

/**
 * <p>
 * 账单明细表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-12-02
 */
public interface ICsAccountDetailService extends IService<CsAccountDetail> {

    /**
     * 生成支付订单
     *
     * @param userId
     * @param orderId   关联的订单
     * @param vipId     充值会员卡id
     * @param money     支付金额
     * @param numText   特定的支付描述
     * @param tradeType 交易类型
     * @param paymentId 支付方式
     * @param device    设备信息
     * @param source    来源
     * @return
     */
    PaymentOrderDTO buildingOrder(Long userId, Long orderId, Long vipId, BigDecimal money, String numText,
                                  Integer tradeType, Long paymentId, String device, Integer source);


    /**
     * 生成支付订单
     *
     * @param userId
     * @param money     支付金额
     * @param tradeType 交易类型
     * @param device    设备信息
     * @param source    来源
     * @return
     */
    PaymentOrderDTO buildingOrder(Long userId, BigDecimal money, Integer tradeType, String device, Integer source);

    /**
     * 通过账单编号，获取账单
     *
     * @param serialNo
     * @return
     */
    CsAccountDetail getBySerialNo(String serialNo);

    /**
     * 通过账单编号，更新账单状态（提供给支付平台）
     *
     * @param paymentCallBack
     */
    void paymentCallBack(PaymentCallBackDTO paymentCallBack);

    /**
     * 分页查询账单明细
     *
     * @param query
     * @return
     */
    Page findPage(Query query);

    /**
     * 通过第三方支付账单编号，获取账单
     *
     * @param serialNo
     * @return
     */
    CsAccountDetail getByThirdSerialNo(String serialNo);

    /**
     * 统计收入和支出
     *
     * @param timeStart
     * @param timeEnd
     * @param tradeType
     * @param userId
     * @return
     */
    AccountTotalAmountDTO totalAmount(String timeStart, String timeEnd, Integer tradeType, Long userId);

    /**
     * 对支付状态为：0、2 处理（定时跑批）
     */
    void paymentInHandler();

    /**
     * 支付账单取消
     *
     * @param serialNo
     */
    void cancel(String serialNo);


}
