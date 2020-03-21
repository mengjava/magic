package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.CsCarOrder;
import com.haoqi.magic.business.model.vo.DisputeItemVO;
import com.haoqi.magic.business.model.vo.OrderDisputeVO;
import com.haoqi.magic.business.model.vo.RecheckFileVO;
import com.haoqi.magic.business.model.vo.TransferApplyVO;
import com.haoqi.rigger.mybatis.Query;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 交易订单表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-12-03
 */
public interface ICsCarOrderService extends IService<CsCarOrder> {

    /**
     * 买入车辆，生成订单
     *
     * @param carId
     * @param userId
     */
    void building(Long carId, Long userId);

    /**
     * 获取买家中心订单数量
     *
     * @param userId
     * @return
     */
    AppBuyerCountDTO getBuyerOrderCount(Long userId);

    /**
     * 获取卖家中心订单数量
     *
     * @param userId
     * @return
     */
    AppSellerCountDTO getSellerOrderCount(Long userId);

    /**
     * 买方订单查询列表
     *
     * @param query
     * @return
     */
    Page<CarOrderDTO> orderPageForBuyer(Query query);


    /**
     * 卖方订单查询列表
     *
     * @param query
     * @return
     */
    Page<CarOrderDTO> orderPageForSeller(Query query);


    /**
     * 申请撤销买入
     *
     * @param orderId
     * @param applyInfo
     */
    void cancelBuying(Long orderId, String applyInfo);

    /**
     * 确认卖出
     *
     * @param orderId
     * @param price
     * @param userId
     */
    void confirmSale(Long orderId, BigDecimal price, Long userId);

    /**
     * 拒绝卖出
     *
     * @param orderId
     */
    void refuseSale(Long orderId);

    /**
     * 买家付款后确认
     *
     * @param serialNo 支付订单号
     * @param payCode  支付订密码(余额支付时用)
     * @return
     */
    PaymentOrderDTO confirmPaid(String serialNo, String payCode);

    /**
     * 发起争议
     *
     * @param userId       发起争议人
     * @param username     用户名
     * @param orderId      订单id
     * @param disputeItems 争议项列表
     */
    void applyDispute(Long userId, String username, Long orderId, List<DisputeItemVO> disputeItems);

    /**
     * 通过订单id，获取信息
     *
     * @param id
     * @return
     */
    CsCarOrder getById(Long id);

    /**
     * 是否存在争议
     *
     * @param orderId 订单id
     * @return
     */
    Boolean hasDisputed(Long orderId);

    /**
     * 是否存在复检
     *
     * @param orderId
     * @return
     */
    Boolean hasRecheck(Long orderId);

    /**
     * 发起复检
     *
     * @param userId
     * @param orderId
     */
    void applyRecheck(Long userId, Long orderId);


    /**
     * 分页获取冻结车辆
     *
     * @param query
     * @return
     */
    Page<CarOrderDTO> freezingCar(Query query);


    /**
     * PC端订单分页查询列表
     *
     * @param query
     * @return
     */
    Page<CarOrderDTO> orderPageForPc(Query query);

    /**
     * 获取过户信息
     *
     * @param orderId
     * @param hasTransfers 是否查询过户信息
     * @return
     */
    CarOrderDTO getTransferInfo(Long orderId, Boolean hasTransfers);

    /**
     * 申请过户
     *
     * @param transferApply
     */
    void applyTransfer(TransferApplyVO transferApply);


    /**
     * 获取争议信息
     *
     * @param orderId
     * @return
     */
    CarOrderDTO getDisputeInfo(Long orderId);


    /**
     * 再次争议
     *
     * @param desc
     * @param userId
     * @param orderId
     * @param username
     */
    void reDispute(Long orderId, Long userId, String username, String desc);


    /**
     * 通过订单id，获取争议、过户附件信息
     *
     * @param orderId
     * @param username 占位人
     * @param lock     是否占位
     * @return
     */
    CarOrderDTO getOrderTransferDisputeInfo(Long orderId, String username, Boolean lock);

    /**
     * 通过订单id，获取争议、复检信息
     *
     * @param orderId
     * @param username 占位人
     * @param lock     是否占位
     * @return
     */
    CarOrderDTO getOrderRecheckDisputeInfo(Long orderId, String username, Boolean lock);

    /**
     * 过户审核
     *
     * @param orderId     订单id
     * @param userId      用户id
     * @param username    用户姓名
     * @param desc        审核说明
     * @param auditStatus 审核状态 1 通过 2 拒绝
     */
    void auditingTransfer(Long orderId, Long userId, String username, String desc, Integer auditStatus);

    /**
     * 通过订单id，获取过户、过户审核信息
     *
     * @param orderId
     * @param username 占位人
     * @param lock     是否占位
     * @return
     */
    CarOrderDTO getOrderTransferInfo(Long orderId, String username, Boolean lock);

    /**
     * 通过订单id，获取订单、订单审核信息
     *
     * @param orderId
     * @return
     */
    CarOrderDTO getOrderAuditInfo(Long orderId);

    /**
     * 通过订单id，获取过户、过户审核信息
     *
     * @param orderId
     * @param username
     * @param lock
     * @param hasTransfers 是否查询过户信息
     * @return
     */
    CarOrderDTO getOrderAuditInfo(Long orderId, String username, Boolean lock, Boolean hasTransfers);

    /**
     * 撤销买入审核
     *
     * @param orderId     订单id
     * @param userId      用户id
     * @param username    用户姓名
     * @param desc        审核说明
     * @param auditStatus 审核状态 1 通过 2 拒绝
     */
    void auditingCancelBuy(Long orderId, Long userId, String username, String desc, Integer auditStatus);


    /**
     * 通过订单id，获取订单、争议、复检信息
     *
     * @param orderId
     * @param username 占位人
     * @param lock     是否占位
     * @return
     */
    CarOrderDTO getOrderRecheckInfo(Long orderId, String username, Boolean lock);

    /**
     * 复检审核
     *
     * @param userId        用户id
     * @param username      用户名
     * @param orderId       订单id
     * @param recheckResult 复检处理结果
     * @param disputeItems  争议项
     * @param recheckFiles  复检项
     */
    void auditingRecheck(Long userId, String username, Long orderId, Integer recheckResult, List<DisputeItemVO> disputeItems, List<RecheckFileVO> recheckFiles);


    /**
     * 通过订单id，获取订单、争议、复检、争议审核信息
     *
     * @param orderId
     * @param username
     * @param lock
     * @return
     */
    CarOrderDTO getDisputeManageInfo(Long orderId, String username, Boolean lock);

    /**
     * 通过订单id，获取复检分页列表
     *
     * @param query
     * @return
     */
    Page<CsOrderRecheckFileDTO> recheckFilePage(Query query);


    /**
     * 争议处理
     *
     * @param userId       用户id
     * @param username     用户姓名
     * @param orderDispute
     */
    void handlerDispute(Long userId, String username, OrderDisputeVO orderDispute);

    /**
     * 争议初审
     *
     * @param userId       用户id
     * @param username     用户姓名
     * @param orderDispute
     */
    void firstAuditingDispute(Long userId, String username, OrderDisputeVO orderDispute);

    /**
     * 争议终审
     *
     * @param userId
     * @param username
     * @param orderDispute
     */
    void finishAuditingDispute(Long userId, String username, OrderDisputeVO orderDispute);

    /**
     * 通过车辆id获取订单信息
     *
     * @param carId
     * @return
     */
    CsCarOrder getByCarId(Long carId);

    /**
     * 功能描述: 过户超时和打款超时处理
     *
     * @param
     * @return void
     * @auther mengyao
     * @date 2019/12/23 0023 上午 11:19
     */
    void overTimeHandle();

    /**
     * 换审核
     *
     * @param ids
     */
    void changeAuditor(List<Long> ids);

    /**
     * 支付确认后的业务处理
     *
     * @param state         支付状态
     * @param tradeType     交易类型
     * @param payType       支付方式
     * @param orderId       订单id
     * @param vipId         会员卡id
     * @param userId        用户id
     * @param money         支付金额
     * @param thirdSerialNo 第三方支付编号
     * @return
     */
    Boolean process(Integer state, Integer tradeType, Long payType, Long orderId, Long vipId, Long userId, BigDecimal money, String thirdSerialNo);


    /**
     * 支付回调批处理
     *
     * @param serialNo
     * @param state
     * @param remark
     */
    void payCallbackHandler(String serialNo, Integer state, Date payTime, String remark);

    /**
     * 通过订单ID、车辆id，获取订单、审核信息
     *
     * @param orderId
     * @param carId
     * @return
     */
    CarOrderDTO getOrderAndAuditByOrderIdAndCarId(Long orderId, Long carId);

    /**
     * 获取复检结果 复检信息 争议项信息
     */
    OrderCheckAndDisputeDTO getOrderCheckAndDisputeByOrderId(Long orderId);
}
