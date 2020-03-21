package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.*;
import com.haoqi.magic.business.mapper.CsCarOrderMapper;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.*;
import com.haoqi.magic.business.model.vo.*;
import com.haoqi.magic.business.service.*;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.common.util.SecureUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.mybatis.provider.OrderNumberProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>
 * 交易订单表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-12-03
 */
@Slf4j
@Service
public class CsCarOrderServiceImpl extends ServiceImpl<CsCarOrderMapper, CsCarOrder> implements ICsCarOrderService {
    @Autowired
    private ICsCarInfoService carInfoService;
    @Autowired
    private ICsCarDealerService dealerService;
    @Autowired
    private SystemServiceClient systemServiceClient;

    @Autowired
    private ICsOrderAuditService orderAuditService;

    @Autowired
    private ICsServiceFeeService serviceFeeService;

    @Autowired
    private ICsAccountDetailService accountDetailService;

    @Autowired
    private ICsOrderDisputeService orderDisputeService;

    @Autowired
    private ICsCarFileService carFileService;

    @Autowired
    private ICsOrderFileService orderFileService;

    @Autowired
    @Qualifier("carOrderNumberProvider")
    private OrderNumberProvider carOrderNumberProvider;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Autowired
    private ICsOrderRecheckFileService orderRecheckFileService;

    @Autowired
    private ICsUserVipService userVipService;

    @Autowired
    private ICsVipService vipService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ICsUserBankCardService userBankCardService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ICsFinancePayActionService financePayActionService;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;


    private static Map<String, AtomicInteger> payThreadLocal = Maps.newConcurrentMap();


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void building(Long carId, Long userId) {
        UserDTO userDTO = getUserById(userId);
        UserAreaDTO userArea = getUserAreaByUserId(userId);
        CarDTO carDTO = carInfoService.getCarByIdAndStatusTradeFlag(carId, CommonConstant.STATUS_EXPIRE, CommonConstant.STATUS_NORMAL);
        Optional<CsCarDealer> optional = dealerService.getOneById(carDTO.getCsCarDealerId());
        optional.orElseThrow(() -> new RiggerException("车商信息不存在"));
        CsCarDealer carDealer = optional.get();
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setVin(carDTO.getVin());
        carOrder.setLocate(carDTO.getLocate());
        carOrder.setCsCarInfoId(carDTO.getId());
        carOrder.setInitDate(carDTO.getInitDate());
        carOrder.setColor(carDTO.getColorCodeName());
        carOrder.setGearBox(carDTO.getGearBoxCodeName());
        carOrder.setPlateNoShortName(carDTO.getPlateNo());
        carOrder.setDisplacement(carDTO.getDisplacement());
        carOrder.setOrderNo(carOrderNumberProvider.building());
        carOrder.setSysCarBrandName(carDTO.getSysCarBrandName());
        carOrder.setSysCarModelName(carDTO.getSysCarModelName());
        carOrder.setSysCarSeriesName(carDTO.getSysCarSeriesName());
        carOrder.setSysCarDealerId(carDTO.getCsCarDealerId());
        carOrder.setEmissionStandard(carDTO.getEmissionStandardCodeName());
        carOrder.setStatus(OrderStatusEnum.BUYING.getKey());
        carOrder.setPublishTime(carDTO.getPublishTime());
        carOrder.setCarDealerName(carDealer.getShortName());
        carOrder.setCarDealerUser(carDealer.getContactName());
        carOrder.setCarDealerUserId(carDealer.getSysUserId());
        carOrder.setCarDealerUserTel(carDealer.getTel());
        carOrder.setSalPrice(carDTO.getWholesalePrice());
        carOrder.setBuyerUserId(userId);
        carOrder.setBuyerTel(userDTO.getTel());
        carOrder.setBuyerName(userDTO.getUsername());
        carOrder.setCheckerName(carDTO.getCheckLoginName());
        carOrder.setAuditTime(carDTO.getAuditTime());
        carOrder.setAuditName(carDTO.getAuditLoginName());
        carOrder.setAuditRemark(carDTO.getAuditRemark());
        carOrder.setInstrumentShowDistance(carDTO.getInstrumentShowDistance());
        carOrder.setDisplacementType(carDTO.getDisplacementType());
        boolean isSameCity = userArea.getAreaId().equals(carDealer.getSysAreaId());
        carOrder.setSameCityFlag(isSameCity ? CommonConstant.STATUS_DEL : CommonConstant.STATUS_NORMAL);
        if (Objects.nonNull(carDealer.getSysAreaId())) {
            Result<SysProvinceAndCityDTO> area = systemServiceClient.getAreaById(carDealer.getSysAreaId());
            if (!area.isSuccess()) {
                throw new RiggerException("获取车商所在城市信息失败");
            }
            carOrder.setCity(area.getData().getCityName());
            carOrder.setProvince(area.getData().getProvinceName());
        }
        BigDecimal frozenAmount = enableFrozenAmount(10, "请设置买入保证金金额");
        carOrder.setBuyerFrozenMoney(isSameCity ? BigDecimal.ZERO : frozenAmount);
        this.insert(carOrder);
        CsOrderAudit orderAudit = new CsOrderAudit();
        orderAudit.setCsCarOrderId(carOrder.getId());
        orderAuditService.insert(orderAudit);
        updateCarTradeFlag(carId, CommonConstant.STATUS_NORMAL, CommonConstant.STATUS_DEL, null);
        //非同城交易需要扣除保证金
        if (!isSameCity) {
            Result<String> out = systemServiceClient.subBalanceAddFrozenAmount(userId, frozenAmount.toString());
            if (!out.isSuccess()) {
                throw new RiggerException(out.getMessage());
            }
            accountDetailService.buildingOrder(userId, carOrder.getId(), null, frozenAmount, "", TradeTypeEnum.FREEZE_AMOUNT_TYPE.getKey(), 0L, "", null);
        }
        buildMessagePush(userId, carOrder.getCarDealerUserId(), carOrder.getSalPrice(), carOrder.getCsCarInfoId(), carOrder.getSysCarModelName(), MessagePushTypeEnum.BUYING.getKey());

    }


    @Override
    public Page<CarOrderDTO> orderPageForBuyer(Query query) {
        Map<String, Object> param = query.getCondition();
        if (!param.containsKey("buyerUserId") || Objects.isNull(param.get("buyerUserId"))) {
            return query;
        }
        setStatusParam(param);
        List<CarOrderDTO> carOrders = super.baseMapper.findPage(query, param);
        return query.setRecords(carOrders);
    }


    @Override
    public Page<CarOrderDTO> orderPageForSeller(Query query) {
        Map<String, Object> param = query.getCondition();
        if (!param.containsKey("carDealerUserId") || Objects.isNull(param.get("carDealerUserId"))) {
            return query;
        }
        setStatusParam(param);
        List<CarOrderDTO> carOrders = super.baseMapper.findPage(query, param);
        return query.setRecords(carOrders);
    }

    @Override
    public AppBuyerCountDTO getBuyerOrderCount(Long userId) {
        return super.baseMapper.selectBuyerOrderCount(userId);
    }

    @Override
    public AppSellerCountDTO getSellerOrderCount(Long userId) {
        return super.baseMapper.selectSellerOrderCount(userId);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelBuying(Long orderId, String applyInfo) {
        Assert.notNull(orderId, "订单id不能为空");
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setCancelBuyApplyReason(applyInfo);
        carOrder.setCancelBuyApplyTime(DateUtil.date());
        carOrder.setStatus(OrderStatusEnum.CANCEL_BUYING_APPLY.getKey());
        boolean result = this.update(carOrder, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("status", OrderStatusEnum.BUYING.getKey())
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("该订单不存在");
        }
        //清除撤销买入审核状态时间

        CsOrderAudit csOrderAudit = new CsOrderAudit();
        csOrderAudit.setCancelBuyAuditAuditRemark("");
        csOrderAudit.setCancelBuyAuditAuditTime(null);
        csOrderAudit.setCancelBuyAuditStatus(Constants.NO);
        csOrderAudit.setCancelBuyAuditAuditUser("");
        csOrderAudit.setCancelBuyAuditAuditUserId(null);
        orderAuditService.update(csOrderAudit, new EntityWrapper<CsOrderAudit>()
                .eq("cs_car_order_id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void confirmSale(Long orderId, BigDecimal price, Long userId) {
        Assert.notNull(orderId, "订单id不能为空");
        Assert.notNull(userId, "用户id不能为空");
        Assert.notNull(price, "价格不能为空");
        CsCarOrder order = getById(orderId);
        Date date = DateUtil.date();
        Boolean isSameCity = CommonConstant.STATUS_DEL.equals(order.getSameCityFlag()) ? Boolean.TRUE : Boolean.FALSE;
        BigDecimal frozenAmount = BigDecimal.ZERO;
        BigDecimal enableBalance = BigDecimal.ZERO;
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setSalPrice(price);
        carOrder.setSellTime(date);
        if (!isSameCity) {
            frozenAmount = enableFrozenAmount(12, "请设置卖出保证金金额");
            enableBalance = enableBalance(frozenAmount);
            carOrder.setCarDealerFrozenMoney(frozenAmount);
            UserAreaDTO userArea = getUserAreaByUserId(userId);
            carOrder.setStatus(OrderStatusEnum.TO_SELL.getKey());
            carOrder.setServiceFee(serviceFeeService.getServiceFree(userArea.getAreaId(), price.multiply(new BigDecimal(10000))));
        } else {
            carOrder.setFinishTime(date);
            carOrder.setStatus(OrderStatusEnum.COMPLETE.getKey());
            updateCarTradeFlag(order.getCsCarInfoId(), CommonConstant.STATUS_DEL, CommonConstant.STATUS_EXPIRE, CarPublishStatusEnum.SOLD_OUT.getKey());
        }
        boolean result = this.update(carOrder, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .in("status", Arrays.asList(OrderStatusEnum.BUYING.getKey(), OrderStatusEnum.CANCEL_BUYING_APPLY.getKey())));
        if (!result) {
            throw new RiggerException("该订单不存在");
        }
        //非同城交易需要扣除保证金
        if (!isSameCity) {
            if (!userBankCardService.binding(userId)) {
                throw new RiggerException("你未实名绑定银行卡，请先实名绑卡!");
            }
            UserDTO user = getUserById(userId);
            if (StrUtil.isBlank(user.getPayPassword())) {
                throw new RiggerException("请设置支付密码");
            }
            Result<String> out = systemServiceClient.subBalanceAddFrozenAmount(userId, frozenAmount.toString());
            if (!out.isSuccess()) {
                throw new RiggerException(out.getMessage());
            }
            accountDetailService.buildingOrder(userId, carOrder.getId(), null, frozenAmount, "", TradeTypeEnum.FREEZE_AMOUNT_TYPE.getKey(), 0L, "", null);
            buildMessagePush(userId, order.getBuyerUserId(), order.getSalPrice(), order.getCsCarInfoId(), order.getSysCarModelName(), MessagePushTypeEnum.TO_SELL.getKey());
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void refuseSale(Long orderId) {
        CsCarOrder order = getById(orderId);
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setRefuseSellTime(DateUtil.date());
        carOrder.setStatus(OrderStatusEnum.REFUSE_TO_SELL.getKey());
        boolean result = this.update(carOrder, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .in("status", Arrays.asList(OrderStatusEnum.BUYING.getKey(), OrderStatusEnum.CANCEL_BUYING_APPLY.getKey())));
        if (!result) {
            throw new RiggerException("该订单不存在");
        }
        updateCarTradeFlag(order.getCsCarInfoId(), CommonConstant.STATUS_DEL, CommonConstant.STATUS_NORMAL, null);
        if (CommonConstant.STATUS_NORMAL.equals(order.getSameCityFlag())) {
            accountDetailService.buildingOrder(order.getBuyerUserId(), orderId, null, order.getBuyerFrozenMoney(), "", TradeTypeEnum.DRAW_BACK_TYPE.getKey(), null, "", 0);
            Result<String> r = systemServiceClient.unFreezeByUserId(order.getBuyerUserId(), order.getBuyerFrozenMoney().toString());
            if (!r.isSuccess()) {
                throw new RiggerException("解冻保证金失败");
            }
        }
        buildMessagePush(order.getCarDealerUserId(), order.getBuyerUserId(), order.getSalPrice(), order.getCsCarInfoId(), order.getSysCarModelName(), MessagePushTypeEnum.REFUSE_TO_SELL.getKey());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentOrderDTO confirmPaid(String serialNo, String payCode) {
        Assert.notBlank(serialNo, "支付订单号不能为空");
        CsAccountDetail accountDetail = accountDetailService.getBySerialNo(serialNo);
        PaymentOrderDTO paymentOrder = new PaymentOrderDTO();
        paymentOrder.setSerialNo(serialNo);
        paymentOrder.setMoney(accountDetail.getMoney());
        paymentOrder.setStatus(accountDetail.getStatus());
        paymentOrder.setTradeDesc(accountDetail.getTradeRemark());
        if (CommonConstant.STATUS_DEL.equals(accountDetail.getStatus()) && Objects.nonNull(accountDetail.getPayType())
                && !CommonConstant.STATUS_NORMAL.equals(accountDetail.getPayType().intValue())) {
            return paymentOrder;
        }
        /**
         * 1.余额支付需要操作账户、支付密码校验，并修改支付订单状态
         * 2.非余额支付，通过支付平台异步调用修改支付订单状态
         */
        if (Objects.isNull(accountDetail.getPayType()) || CommonConstant.STATUS_NORMAL.equals(accountDetail.getPayType().intValue())) {
            if (StrUtil.isBlank(payCode)) {
                throw new RiggerException("支付密码不能为空");
            }
            UserDTO user = getUserById(accountDetail.getSysUserId());
            try {
                payCode = SecureUtils.decryptRSAByPrivateKey(payCode, Constants.privateKey);
            } catch (Exception e) {
                log.error("支付密码确认异常：", e);
                throw new RiggerException("支付密码不正确");
            }
            if (!DigestUtil.bcryptCheck(payCode, user.getPayPassword())) {
                throw new RiggerException("支付密码不正确");
            }
            if (TradeTypeEnum.OPEN_VIP_TYPE.getKey().equals(accountDetail.getTradeType()) && isExperienceMember(accountDetail.getVipId())) {
                accountDetail.setPayTime(DateUtil.date());
                accountDetail.setStatus(CommonConstant.STATUS_DEL);
                accountDetailService.updateById(accountDetail);
                userVipService.bindingMember(accountDetail.getVipId(), accountDetail.getSysUserId(), user.getUsername());
                paymentOrder.setStatus(accountDetail.getStatus());
                return paymentOrder;
            }
            Result<BigDecimal> result = systemServiceClient.enableBalance(accountDetail.getMoney().toString());
            if (!result.isSuccess()) {
                throw new RiggerException(result.getMessage());
            }
            accountDetail.setPayTime(DateUtil.date());
            accountDetail.setStatus(CommonConstant.STATUS_DEL);
            accountDetailService.updateById(accountDetail);
            if (TradeTypeEnum.CAR_RECHECK_TYPE.getKey().equals(accountDetail.getTradeType())) {
                //支付成功后，复检，修改订单复检状态
                CsCarOrder order = super.baseMapper.selectById(accountDetail.getCsCarOrderId());
                updateRecheckFlagToRecheck(accountDetail.getCsCarOrderId(), order.getBuyerName());
            } else if (TradeTypeEnum.OPEN_VIP_TYPE.getKey().equals(accountDetail.getTradeType())) {
                userVipService.bindingMember(accountDetail.getVipId(), accountDetail.getSysUserId(), user.getUsername());
            }
            //支付账单的金额如果是负数要取反
            BigDecimal bigDecimal = accountDetail.getMoney().compareTo(BigDecimal.ZERO) < 0 ? accountDetail.getMoney().multiply(NumberUtil.toBigDecimal(-1)) : accountDetail.getMoney();
            Result<String> out = systemServiceClient.subBalance(result.getData().toString(), bigDecimal.toString());
            if (!out.isSuccess()) {
                log.error("余额支付时，账户余额：{}，扣款：{}，变更异常。{}", result.getData(), bigDecimal, out.getMessage());
                throw new RiggerException("支付失败");
            }
            paymentOrder.setStatus(accountDetail.getStatus());
            return paymentOrder;
        }
        BasicThreadFactory threadFactory = new BasicThreadFactory.Builder()
                .namingPattern("confirmPaid-%d")
                .uncaughtExceptionHandler(((t, e) -> {
                    log.error("支付确认线程：{}，异常信息：{}", t.getName(), e);
                }))
                .build();
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, threadFactory);
        boolean flag = false;
        try {
            flag = getBooleanFuture(serialNo, executorService);
        } catch (Exception e) {
            log.error("支付确认异常：", e);
        }
        executorService.shutdown();
        payThreadLocal.remove(serialNo);
        if (!flag) {
            throw new RiggerException("支付正在交易中，请稍后");
        }
        paymentOrder.setStatus(CommonConstant.STATUS_DEL);
        return paymentOrder;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyDispute(Long userId, String username, Long orderId, List<DisputeItemVO> disputeItems) {
        Assert.notNull(userId, "用户ID不能为空");
        Assert.notBlank(username, "用户名不能为空");
        Assert.notNull(orderId, "订单ID不能为空");
        Assert.notEmpty(disputeItems, "争议项不能为空");
        final boolean[] isRecheck = {false};
        Date date = DateUtil.date();
        UserDTO user = getUserById(userId);
        List<CsOrderDispute> orderDisputes = Lists.newArrayList();
        disputeItems.forEach(disputeItemVO -> {
            if (!isRecheck[0] && CommonConstant.STATUS_DEL.equals(disputeItemVO.getRecheckFlag())) {
                isRecheck[0] = true;
            }
            CsOrderDispute orderDispute = new CsOrderDispute();
            orderDispute.setCreator(userId);
            orderDispute.setModifier(userId);
            orderDispute.setGmtCreate(date);
            orderDispute.setGmtModified(date);
            orderDispute.setCsCarOrderId(orderId);
            orderDispute.setTextDecs(disputeItemVO.getTextDecs());
            orderDispute.setShowText(disputeItemVO.getShowText());
            orderDispute.setCsDisputeItemId(disputeItemVO.getId());
            orderDispute.setRecheckFlag(disputeItemVO.getRecheckFlag());
            orderDispute.setCsDisputeItemParentId(disputeItemVO.getParentId());
            orderDisputes.add(orderDispute);
        });
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setOperationingUser("");
        carOrder.setDisputeApplyTime(date);
        carOrder.setDisputeApplyUser(user.getUsername());
        carOrder.setDisputeFlag(CommonConstant.STATUS_DEL);
        if (isRecheck[0]) {
            carOrder.setRecheckApplyTime(date);
            carOrder.setRecheckApplyUser(user.getUsername());
            carOrder.setRecheckFlag(CommonConstant.STATUS_DEL);
            carOrder.setRecheckType(CommonConstant.STATUS_EXPIRE);
        }
        boolean result = this.update(carOrder, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("dispute_flag", CommonConstant.STATUS_NORMAL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        deleteLockKey(orderId);
        if (!result) {
            throw new RiggerException("该订单已争议");
        }
        if (CollectionUtil.isNotEmpty(orderDisputes)) {
            orderDisputeService.insertBatch(orderDisputes);
        }

    }

    @Override
    public CsCarOrder getById(Long id) {
        CsCarOrder order = this.selectById(id);
        return Optional.ofNullable(order).orElseThrow(() -> new RiggerException("订单不存在"));
    }

    @Override
    public Boolean hasDisputed(Long orderId) {
        CsCarOrder carOrder = selectOne(new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("dispute_flag", CommonConstant.STATUS_DEL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        return Objects.nonNull(carOrder) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Boolean hasRecheck(Long orderId) {
        CsCarOrder carOrder = selectOne(new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("recheck_flag", CommonConstant.STATUS_DEL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        return Objects.nonNull(carOrder) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public void applyRecheck(Long userId, Long orderId) {
        Assert.notNull(userId, "用户ID不能为空");
        Assert.notNull(orderId, "订单ID不能为空");
        UserDTO user = getUserById(userId);
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setRecheckApplyTime(DateUtil.date());
        carOrder.setRecheckApplyUser(user.getUsername());
        carOrder.setRecheckType(CommonConstant.STATUS_DEL);
        carOrder.setRecheckFlag(CommonConstant.STATUS_DEL);
        boolean result = this.update(carOrder, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("recheck_flag", CommonConstant.STATUS_NORMAL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("该订单已复检");
        }
    }

    @Override
    public Page<CarOrderDTO> freezingCar(Query query) {
        Map<String, Object> param = query.getCondition();
        Long userId = (Long) param.get("userId");
        List<CarOrderDTO> orders = super.baseMapper.freezingCar(query, param);
        orders.forEach(carOrderDTO -> {
            CarFileDTO carFile = carFileService.getLeftFront45FileByCarId(carOrderDTO.getCsCarInfoId());
            carOrderDTO.setCarImagePath(carFile.getWebUrl() + StrUtil.SLASH + carFile.getFilePath());
            if (userId.equals(carOrderDTO.getBuyerUserId())) {
                carOrderDTO.setIsBuyer(Boolean.TRUE);
            }
        });
        return query.setRecords(orders);
    }

    @Override
    public Page<CarOrderDTO> orderPageForPc(Query query) {
        Map<String, Object> param = query.getCondition();
        Integer sameCityFlag = (Integer) param.get("sameCityFlag");
        List<CarOrderDTO> orderDTOS = super.baseMapper.findPage(query, param);
        orderDTOS.forEach(carOrderDTO -> {
            //争议按钮
            if (isShowDisputeButton(carOrderDTO.getStatus()) &&
                    CommonConstant.STATUS_NORMAL.equals(carOrderDTO.getDisputeFlag())) {
                carOrderDTO.setShowDisputeButton(Boolean.TRUE);
            }
            if (CommonConstant.STATUS_NORMAL.equals(sameCityFlag)) {
                //根据过户状态查询
                Integer transferAuditStatus = (Integer) param.get("transferAuditStatus");
                if (OrderAuditStatusEnum.SUBMITTED.getKey().equals(transferAuditStatus)) {
                    param.put("transferStatus", TransferStatusEnum.TRANSFER.getKey());
                }
                //根据争议初审审核状态(1:通过，2拒绝，0默认）查询
                Integer disputeFirstAuditStatus = (Integer) param.get("disputeFirstAuditStatus");
                Integer disputeFinishAuditStatus = (Integer) param.get("disputeFinishAuditStatus");
                if (OrderAuditStatusEnum.SUBMITTED.getKey().equals(disputeFirstAuditStatus) ||
                        OrderAuditStatusEnum.SUBMITTED.getKey().equals(disputeFinishAuditStatus)) {
                    param.put("disputeFlag", DisputeFlagEnum.IN_DISPUTE.getKey());
                }
                //撤销买入审核待审核
                Integer cancelBuyAuditStatus = (Integer) param.get("cancelBuyAuditStatus");
                if (OrderAuditStatusEnum.SUBMITTED.getKey().equals(cancelBuyAuditStatus)) {
                    param.put("status", OrderStatusEnum.CANCEL_BUYING_APPLY.getKey());
                }
                //复检未完成时，需要查询的是复检状态是复检的订单
                Integer recheckStatus = (Integer) param.get("recheckStatus");
                if (OrderAuditStatusEnum.SUBMITTED.getKey().equals(recheckStatus)) {
                    param.put("recheckFlag", CommonConstant.STATUS_DEL);
                }

                CsCarDealer dealer = dealerService.getOneById(carOrderDTO.getSysCarDealerId()).get();
                //1 先打款后过户 2 先过户后打款，并且未争议或争议已处理完成
                if ((isFistTransfer(dealer.getPaymentType()) || isFistPayment(carOrderDTO.getStatus(), dealer.getPaymentType())) &&
                        !DisputeFlagEnum.IN_DISPUTE.getKey().equals(carOrderDTO.getDisputeFlag()) &&
                        isShowTransferButton(carOrderDTO.getStatus(), carOrderDTO.getTransferStatus())) {
                    carOrderDTO.setShowTransferButton(Boolean.TRUE);
                }
                //再次争议
                if (CommonConstant.STATUS_EXPIRE.equals(carOrderDTO.getDisputeFlag())) {
                    carOrderDTO.setShowReDisputeButton(Boolean.TRUE);
                }
            }
        });
        return query.setRecords(orderDTOS);
    }

    @Override
    public CarOrderDTO getTransferInfo(Long orderId, Boolean hasTransfers) {
        CarOrderDTO carOrderDTO = getOrderAuditInfo(orderId);
        //买家银行卡信息
        if (userBankCardService.binding(carOrderDTO.getBuyerUserId())) {
            CsUserBankCardDTO bankCard = userBankCardService.getBankCardByUserId(carOrderDTO.getBuyerUserId());
            carOrderDTO.setBankName(bankCard.getBankName());
            carOrderDTO.setBankCardNo(bankCard.getBankCardNo());
            carOrderDTO.setBankUserName(bankCard.getBankUserName());
        }
        if (!hasTransfers) {
            return carOrderDTO;
        }
        List<CsOrderFile> transferFiles = orderFileService.selectList(new EntityWrapper<CsOrderFile>()
                .eq("cs_car_order_id", orderId)
                .eq("type", Constants.YES)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        carOrderDTO.setTransferFiles(BeanUtils.beansToList(transferFiles, TransferFileVO.class));
        carOrderDTO.getTransferFiles().forEach(file -> {
            file.setWebUrl(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), StrUtil.EMPTY));
        });
        return carOrderDTO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyTransfer(TransferApplyVO transferApply) {
        Date date = DateUtil.date();
        CsCarOrder order = new CsCarOrder();
        order.setOperationingUser("");
        order.setTransferApplyTime(date);
        order.setTransferTime(transferApply.getTransferTime());
        order.setTransferRemark(transferApply.getTransferRemark());
        order.setTransferStatus(TransferStatusEnum.TRANSFER.getKey());
        order.setTransferApplyUser(transferApply.getTransferApplyUser());

        List<TransferFileVO> transferFileVOS = transferApply.getTransferFiles();
        List<CsOrderFile> transferFiles = Lists.newArrayList();
        transferFileVOS.forEach(transferFileVO -> {
            CsOrderFile transferFile = new CsOrderFile();
            transferFile.setGmtCreate(date);
            transferFile.setGmtModified(date);
            transferFile.setType(Constants.YES);
            transferFile.setCsCarOrderId(transferApply.getId());
            transferFile.setFileName(transferFileVO.getFileName());
            transferFile.setFilePath(transferFileVO.getFilePath());
            transferFile.setCreator(transferApply.getTransferApplyUserId());
            transferFile.setModifier(transferApply.getTransferApplyUserId());
            transferFiles.add(transferFile);
        });
        if (CollectionUtil.isNotEmpty(transferFiles)) {
            orderFileService.insertBatch(transferFiles);
        }
        boolean result = this.update(order, new EntityWrapper<CsCarOrder>()
                .eq("id", transferApply.getId())
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("operationing_user", transferApply.getTransferApplyUser())
                .eq("transfer_status", TransferStatusEnum.UN_TRANSFERRED.getKey())
                .in("status", Arrays.asList(OrderStatusEnum.PAID.getKey(), OrderStatusEnum.PAYMENT.getKey())));
        deleteLockKey(transferApply.getId());
        if (!result) {
            throw new RiggerException("该订单已过户");
        }

    }


    @Override
    public CarOrderDTO getDisputeInfo(Long orderId) {
        CarOrderDTO carOrderDTO = getOrderAuditInfo(orderId);
        List<CsOrderDispute> disputes = orderDisputeService.selectList(new EntityWrapper<CsOrderDispute>()
                .eq("cs_car_order_id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        carOrderDTO.setDisputeItems(BeanUtils.beansToList(disputes, DisputeItemVO.class));
        return carOrderDTO;
    }

    @Override
    public void reDispute(Long orderId, Long userId, String username, String desc) {
        CsCarOrder order = new CsCarOrder();
        order.setId(orderId);
        order.setDisputeAdditional(desc);
        order.setDisputeAdditionalUser(username);
        order.setDisputeAdditionalUserId(userId);
        order.setDisputeAdditionalTime(DateUtil.date());
        this.updateById(order);
    }

    @Override
    public CarOrderDTO getOrderTransferDisputeInfo(Long orderId, String username, Boolean lock) {
        CarOrderDTO carOrderDTO = getOrderAuditInfo(orderId, username, lock, true);
        List<CsOrderDispute> disputes = orderDisputeService.selectList(new EntityWrapper<CsOrderDispute>()
                .eq("cs_car_order_id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        carOrderDTO.setDisputeItems(BeanUtils.beansToList(disputes, DisputeItemVO.class));
        return carOrderDTO;
    }

    @Override
    public CarOrderDTO getOrderRecheckDisputeInfo(Long orderId, String username, Boolean lock) {
        CarOrderDTO carOrderDTO = getOrderAuditInfo(orderId, username, lock, false);
        if (RecheckResultEnum.NORMAL.getKey().equals(carOrderDTO.getRecheckType())) {
            carOrderDTO.setRecheckFee(getRecheckMoneyByOrderId(orderId));
        }
        List<CsOrderDispute> disputes = orderDisputeService.selectList(new EntityWrapper<CsOrderDispute>()
                .eq("cs_car_order_id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        carOrderDTO.setDisputeItems(BeanUtils.beansToList(disputes, DisputeItemVO.class));
        return carOrderDTO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditingTransfer(Long orderId, Long userId, String username, String desc, Integer auditStatus) {
        Assert.notNull(orderId, "订单id不能为空");
        Assert.notNull(userId, "用户id不能为空");
        Assert.notBlank(username, "用户姓名不能为空");
        Assert.notBlank(desc, "审核说明不能为空");
        Assert.notNull(auditStatus, "审核状态不能为空");
        CsCarOrder carOrder = getById(orderId);
        if (DisputeFlagEnum.IN_DISPUTE.getKey().equals(carOrder.getDisputeFlag())) {
            throw new RiggerException("订单争议中,暂不能过户审核");
        }
        CsCarDealer dealer = dealerService.getByUserId(carOrder.getCarDealerUserId());
        Date date = DateUtil.date();
        CsCarOrder order = new CsCarOrder();
        order.setOperationingUser("");
        order.setTransferStatus(CommonConstant.STATUS_NORMAL);
        //1 通过 2 拒绝 过户审核拒绝,可用重新发起过户申请
        if (CommonConstant.STATUS_DEL.equals(auditStatus) && isFistTransfer(dealer.getPaymentType())) {
            order.setStatus(OrderStatusEnum.TRANSFERRED.getKey());
            order.setTransferStatus(TransferStatusEnum.TRANSFERRED.getKey());
            if (Objects.isNull(carOrder.getDisputeProcessType()) || CommonConstant.STATUS_NORMAL.equals(carOrder.getDisputeProcessType()) ||
                    DisputeProcessTypeEnum.INDEMNIFY_TYPE.getKey().equals(carOrder.getDisputeProcessType()) ||
                    DisputeProcessTypeEnum.NOT_TRUE_TYPE.getKey().equals(carOrder.getDisputeProcessType())) {
                saveFinanceCarMoney(orderId, carOrder.getSalPrice().add(carOrder.getServiceFee()), dealer.getPaymentType(), dealer.getSysUserId());
            }
        } else if (CommonConstant.STATUS_DEL.equals(auditStatus) && !isFistTransfer(dealer.getPaymentType())) {
            order.setFinishTime(date);
            order.setStatus(OrderStatusEnum.COMPLETE.getKey());
            order.setTransferStatus(TransferStatusEnum.TRANSFERRED.getKey());
        }
        OrderAuditLogDTO orderAuditLog = new OrderAuditLogDTO();
        orderAuditLog.setOrderId(orderId);
        orderAuditLog.setAuditUser(username);
        orderAuditLog.setAuditRemark(desc);
        orderAuditLog.setAuditType(AuditTypeEnum.AUDIT_TRANSFER.getKey());
        orderAuditLog.setAuditTypeName(AuditTypeEnum.AUDIT_TRANSFER.getName());
        orderAuditLog.setAuditResult(OrderAuditStatusEnum.getNameByKey(auditStatus));
        orderAuditLog.setAuditTime(DateUtil.formatDateTime(date));
        CsOrderAudit orderAudit = orderAuditService.getByOrderId(orderId);
        orderAudit.setTransferAuditRemark(desc);
        orderAudit.setTransferAuditUserId(userId);
        orderAudit.setTransferAuditUser(username);
        orderAudit.setTransferAuditStatus(auditStatus);
        orderAudit.setTransferAuditTime(date);
        parseOrderAuditLog(orderAudit, orderAuditLog);

        boolean orderResult = this.update(order, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("operationing_user", username)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("transfer_status", TransferStatusEnum.TRANSFER.getKey())
                .in("status", Arrays.asList(OrderStatusEnum.PAID.getKey(), OrderStatusEnum.PAYMENT.getKey())));
        boolean orderAuditResult = orderAuditService.update(orderAudit, new EntityWrapper<CsOrderAudit>()
                .eq("cs_car_order_id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .ne("transfer_audit_status", CommonConstant.STATUS_DEL));
        deleteLockKey(orderId);
        if (!orderResult || !orderAuditResult) {
            throw new RiggerException("该订单已过户");
        }
        //订单完成后未争议处理的，退回冻结保证金，车商如果是先打款后过户就需要解冻
        if (OrderStatusEnum.COMPLETE.getKey().equals(order.getStatus())) {
            updateCarTradeFlag(carOrder.getCsCarInfoId(), CommonConstant.STATUS_DEL, CommonConstant.STATUS_EXPIRE, CarPublishStatusEnum.SOLD_OUT.getKey());
            if (DisputeFlagEnum.NON_DISPUTE.getKey().equals(carOrder.getDisputeFlag()) || !isFistTransfer(dealer.getPaymentType())) {
                Result<String> result = systemServiceClient.unFreezeByUserIdAndDealerId(carOrder.getBuyerUserId(), carOrder.getBuyerFrozenMoney().toString(),
                        carOrder.getCarDealerUserId(), carOrder.getCarDealerFrozenMoney().toString());
                if (!result.isSuccess()) {
                    throw new RiggerException(result.getMessage());
                }
            }

        }
    }


    @Override
    public CarOrderDTO getOrderTransferInfo(Long orderId, String username, Boolean lock) {
        return getOrderAuditInfo(orderId, username, lock, true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditingCancelBuy(Long orderId, Long userId, String username, String desc, Integer auditStatus) {
        Assert.notNull(orderId, "订单id不能为空");
        Assert.notNull(userId, "用户id不能为空");
        Assert.notBlank(username, "用户姓名不能为空");
        Assert.notBlank(desc, "审核说明不能为空");
        Assert.notNull(auditStatus, "审核状态不能为空");
        Date date = DateUtil.date();
        CsCarOrder carOrder = this.getById(orderId);
        CsCarOrder order = new CsCarOrder();
        order.setOperationingUser("");
        order.setStatus(OrderStatusEnum.BUYING.getKey());
        //1 通过 2 拒绝 撤销买入审核拒绝
        boolean isTrue = CommonConstant.STATUS_DEL.equals(auditStatus);
        if (isTrue) {
            order.setStatus(OrderStatusEnum.CANCEL_BUYING_PASS.getKey());
            updateCarTradeFlag(carOrder.getCsCarInfoId(), CommonConstant.STATUS_DEL, CommonConstant.STATUS_NORMAL, null);
        }

        OrderAuditLogDTO orderAuditLog = new OrderAuditLogDTO();
        orderAuditLog.setOrderId(orderId);
        orderAuditLog.setAuditUser(username);
        orderAuditLog.setAuditRemark(desc);
        orderAuditLog.setAuditType(AuditTypeEnum.AUDIT_CANCEL_BUYING.getKey());
        orderAuditLog.setAuditTypeName(AuditTypeEnum.AUDIT_CANCEL_BUYING.getName());
        orderAuditLog.setAuditResult(OrderAuditStatusEnum.getNameByKey(auditStatus));
        orderAuditLog.setAuditTime(DateUtil.formatDateTime(date));
        CsOrderAudit orderAudit = orderAuditService.getByOrderId(orderId);
        orderAudit.setCancelBuyAuditAuditRemark(desc);
        orderAudit.setCancelBuyAuditStatus(auditStatus);
        orderAudit.setCancelBuyAuditAuditUserId(userId);
        orderAudit.setCancelBuyAuditAuditUser(username);
        orderAudit.setCancelBuyAuditAuditTime(DateUtil.date());
        parseOrderAuditLog(orderAudit, orderAuditLog);

        boolean orderResult = this.update(order, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("operationing_user", username)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("status", OrderStatusEnum.CANCEL_BUYING_APPLY.getKey()));
        boolean orderAuditResult = orderAuditService.update(orderAudit, new EntityWrapper<CsOrderAudit>()
                .eq("cs_car_order_id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .ne("cancel_buy_audit_status", CommonConstant.STATUS_DEL));
        deleteLockKey(orderId);
        if (!orderResult || !orderAuditResult) {
            throw new RiggerException("该订单已撤销买入");
        }
        if (isTrue && CommonConstant.STATUS_NORMAL.equals(carOrder.getSameCityFlag())) {
            accountDetailService.buildingOrder(carOrder.getBuyerUserId(), orderId, null, carOrder.getBuyerFrozenMoney(), "", TradeTypeEnum.DRAW_BACK_TYPE.getKey(), null, "", 0);
            Result<String> result = systemServiceClient.unFreezeByUserId(carOrder.getBuyerUserId(), carOrder.getBuyerFrozenMoney().toString());
            if (!result.isSuccess()) {
                throw new RiggerException("解冻保证金失败");
            }
        }
        buildMessagePush(userId, carOrder.getBuyerUserId(), carOrder.getSalPrice(), carOrder.getCsCarInfoId(), carOrder.getSysCarModelName(), isTrue ? MessagePushTypeEnum.CANCEL_BUYING_PASS.getKey() : MessagePushTypeEnum.CANCEL_BUYING_REFUSE.getKey());
    }

    @Override
    public CarOrderDTO getOrderAuditInfo(Long orderId) {
        CarOrderDTO carOrderDTO = super.baseMapper.getOrderAuditInfo(orderId);
        Optional.ofNullable(carOrderDTO).orElseThrow(() -> new RiggerException("订单信息不存在"));
        return carOrderDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CarOrderDTO getOrderAuditInfo(Long orderId, String username, Boolean lock, Boolean hasTransfers) {
        if (Objects.isNull(lock) ? Boolean.FALSE : lock) {
            String lockKey = prefix + ":orderLock:" + orderId;
            if (!redisTemplate.hasKey(lockKey)) {
                CsCarOrder order = new CsCarOrder();
                order.setOperationingUser(username);
                order.setOperationingTime(DateUtil.date());
                boolean result = this.update(order, new EntityWrapper<CsCarOrder>()
                        .eq("id", orderId)
                        .eq("operationing_user", "")
                        .eq("is_deleted", CommonConstant.STATUS_NORMAL));
                redisTemplate.opsForValue().set(lockKey, username, 365, TimeUnit.DAYS);
                if (!result) {
                    throw new RiggerException("该订单已被占位");
                }
            } else {
                String value = (String) redisTemplate.opsForValue().get(lockKey);
                if (!username.equals(value)) {
                    throw new RiggerException("该订单已被占位");
                }
            }

        }
        return getTransferInfo(orderId, hasTransfers);
    }

    @Override
    public CarOrderDTO getOrderRecheckInfo(Long orderId, String username, Boolean lock) {
        CarOrderDTO carOrderDTO = getOrderAuditInfo(orderId, username, lock, false);
        //争议复检只显示需要复检的信息
        List<CsOrderDispute> disputes = orderDisputeService.selectList(new EntityWrapper<CsOrderDispute>()
                .eq("cs_car_order_id", orderId)
                .eq("recheck_flag", CommonConstant.STATUS_DEL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        carOrderDTO.setDisputeItems(BeanUtils.beansToList(disputes, DisputeItemVO.class));
        return carOrderDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditingRecheck(Long userId, String username, Long orderId, Integer recheckResult, List<DisputeItemVO> disputeItems, List<RecheckFileVO> recheckFiles) {
        CsCarOrder order = new CsCarOrder();
        order.setOperationingUser("");
        order.setRecheckUserId(userId);
        order.setRecheckUserName(username);
        order.setRecheckResult(recheckResult);
        order.setRecheckStatus(CommonConstant.STATUS_DEL);
        order.setRecheckTime(DateUtil.date());
        List<CsOrderRecheckFile> orderRecheckFiles = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(recheckFiles)) {
            recheckFiles.forEach(recheckFileVO -> {
                CsOrderRecheckFile orderRecheckFile = BeanUtils.beanCopy(recheckFileVO, CsOrderRecheckFile.class);
                orderRecheckFile.setCreator(userId);
                orderRecheckFile.setModifier(userId);
                orderRecheckFile.setGmtCreate(DateUtil.date());
                orderRecheckFile.setGmtModified(DateUtil.date());
                orderRecheckFile.setCsCarOrderId(orderId);
                orderRecheckFiles.add(orderRecheckFile);
            });
        }

        List<CsOrderDispute> orderDisputes = Lists.newArrayList();
        if (CollectionUtil.isNotEmpty(disputeItems)) {
            disputeItems.forEach(disputeItemVO -> {
                if (Objects.isNull(disputeItemVO.getId()) || Objects.isNull(disputeItemVO.getRecheckResult())) {
                    throw new RiggerException("请核查争议项");
                }
                CsOrderDispute orderDispute = new CsOrderDispute();
                orderDispute.setModifier(userId);
                orderDispute.setId(disputeItemVO.getId());
                orderDispute.setGmtModified(DateUtil.date());
                orderDispute.setRecheckResult(disputeItemVO.getRecheckResult());
                orderDisputes.add(orderDispute);
            });
        }
        boolean orderResult = this.update(order, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("operationing_user", username)
                .eq("recheck_flag", CommonConstant.STATUS_DEL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("recheck_status", CommonConstant.STATUS_NORMAL));
        deleteLockKey(orderId);
        if (!orderResult) {
            throw new RiggerException("该订单已复检审核");
        }
        if (CollectionUtil.isNotEmpty(orderDisputes)) {
            orderDisputeService.updateBatchById(orderDisputes);
        }
        if (CollectionUtil.isNotEmpty(orderRecheckFiles)) {
            orderRecheckFileService.insertBatch(orderRecheckFiles);
        }
    }

    @Override
    public CarOrderDTO getDisputeManageInfo(Long orderId, String username, Boolean lock) {
        return this.getOrderRecheckDisputeInfo(orderId, username, lock);
    }

    @Override
    public Page<CsOrderRecheckFileDTO> recheckFilePage(Query query) {
        return orderRecheckFileService.findPage(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void handlerDispute(Long userId, String username, OrderDisputeVO orderDispute) {
        List<DisputeItemVO> disputeItems = orderDispute.getDisputeItems();
        if (CollectionUtil.isEmpty(disputeItems)) {
            throw new RiggerException("争议项不能为空");
        }
        CsCarOrder carOrder = getById(orderDispute.getOrderId());
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<CsOrderDispute> orderDisputes = Lists.newArrayList();
        for (DisputeItemVO disputeItem : disputeItems) {
            orderDisputes.add(BeanUtils.beanCopy(disputeItem, CsOrderDispute.class));
            totalAmount = totalAmount.add(Objects.isNull(disputeItem.getMoney()) ? BigDecimal.ZERO : disputeItem.getMoney());
        }
        CsCarOrder order = new CsCarOrder();
        order.setOperationingUser("");
        order.setCompensateMoney(totalAmount);
        order.setDisputeProcessUser(username);
        order.setDisputeProcessUserId(userId);
        order.setDisputeProcessTime(DateUtil.date());
        order.setTransferMoney(orderDispute.getTransferMoney());
        order.setDisputeRemark(orderDispute.getDisputeRemark());
        order.setDisputeProcessType(orderDispute.getDisputeProcessType());
        //争议处理不属实
        if (DisputeProcessTypeEnum.NOT_TRUE_TYPE.getKey().equals(orderDispute.getDisputeProcessType())) {
            order.setDisputeFlag(DisputeFlagEnum.DISPUTED.getKey());
        } else if (DisputeProcessTypeEnum.INDEMNIFY_TYPE.getKey().equals(orderDispute.getDisputeProcessType()) ||
                DisputeProcessTypeEnum.BUYER_BREAK_CAR_TYPE.getKey().equals(orderDispute.getDisputeProcessType()) ||
                DisputeProcessTypeEnum.SELLER_BREAK_CAR_TYPE.getKey().equals(orderDispute.getDisputeProcessType()) ||
                DisputeProcessTypeEnum.NEGOTIATE_BACK_TYPE.getKey().equals(orderDispute.getDisputeProcessType())) {
            order.setDisputeAuditStatus(CommonConstant.STATUS_DEL);
        }

        //审核日志json
        OrderAuditLogDTO orderAuditLog = new OrderAuditLogDTO();
        orderAuditLog.setOrderId(orderDispute.getOrderId());
        orderAuditLog.setAuditUser(username);
        orderAuditLog.setAuditRemark(orderDispute.getDisputeRemark());
        orderAuditLog.setAuditType(AuditTypeEnum.AUDIT_DISPUTE_HANDLE.getKey());
        orderAuditLog.setAuditTypeName(AuditTypeEnum.AUDIT_DISPUTE_HANDLE.getName());
        orderAuditLog.setAuditResult(DisputeProcessTypeEnum.getNameByKey(orderDispute.getDisputeProcessType()));
        orderAuditLog.setAuditTime(DateUtil.formatDateTime(DateUtil.date()));
        CsOrderAudit orderAudit = orderAuditService.getByOrderId(orderDispute.getOrderId());
        orderAudit.setDisputeFirstAuditRemark("");
        orderAudit.setDisputeFirstAuditUser("");
        orderAudit.setDisputeFirstAuditStatus(Constants.NO);
        orderAudit.setDisputeFirstAuditUserId(null);
        orderAudit.setDisputeFirstAuditTime(null);
        orderAudit.setDisputeFinishAuditRemark("");
        orderAudit.setDisputeFinishAuditUser("");
        orderAudit.setDisputeFinishAuditStatus(Constants.NO);
        orderAudit.setDisputeFinishAuditUserId(null);
        orderAudit.setDisputeFinishAuditTime(null);
        parseOrderAuditLog(orderAudit, orderAuditLog);

        boolean result = this.update(order, new EntityWrapper<CsCarOrder>()
                .eq("id", orderDispute.getOrderId())
                .eq("operationing_user", username)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("dispute_flag", DisputeFlagEnum.IN_DISPUTE.getKey()));
        boolean orderDisputeResult = orderDisputeService.updateBatchById(orderDisputes);
        boolean orderAuditResult = orderAuditService.updateAllColumnById(orderAudit);
        deleteLockKey(orderDispute.getOrderId());
        if (!result || !orderDisputeResult || !orderAuditResult) {
            throw new RiggerException("该订单已争议处理");
        }
        if (DisputeFlagEnum.DISPUTED.getKey().equals(carOrder.getDisputeFlag())) {
            Result<String> re = systemServiceClient.unFreezeByUserIdAndDealerId(carOrder.getBuyerUserId(), carOrder.getBuyerFrozenMoney().toString(),
                    carOrder.getCarDealerUserId(), carOrder.getCarDealerFrozenMoney().toString());
            if (!re.isSuccess()) {
                throw new RiggerException(re.getMessage());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void firstAuditingDispute(Long userId, String username, OrderDisputeVO orderDispute) {
        CsCarOrder carOrder = this.getById(orderDispute.getOrderId());
        Integer orderAuditStatus = orderDispute.getDisputeFirstAuditStatus();
        if (Objects.isNull(orderAuditStatus)) {
            throw new RiggerException("初审审核状态不能为空");
        }
        if (!username.equals(carOrder.getOperationingUser()) ||
                !CommonConstant.STATUS_DEL.equals(carOrder.getDisputeAuditStatus())) {
            throw new RiggerException("该订单已争议初审");
        }
        Date date = DateUtil.date();
        carOrder.setOperationingUser("");
        //退回
        if (OrderAuditStatusEnum.REJECTION.getKey().equals(orderAuditStatus)) {
            carOrder.setDisputeAuditStatus(CommonConstant.STATUS_NORMAL);
            carOrder.setDisputeProcessTime(null);
        } else {
            if (DisputeProcessTypeEnum.INDEMNIFY_TYPE.getKey().equals(carOrder.getDisputeProcessType()) &&
                    carOrder.getDisputeAuditStatus().equals(CommonConstant.STATUS_DEL)) {
                //赔偿
                Result<SysConfigDTO> result = systemServiceClient.getByTypeAndName(ConfigTypeEnum.DISPUTE.getKey(), ConfigNameEnum.COMPENSATION_AMOUNT.getKey());
                if (!result.isSuccess() || StrUtil.isBlank(result.getData().getGlobalValue())) {
                    throw new RiggerException("获取争议赔偿金设置失败");
                }
                //如果赔偿金额 >= 终审赔偿金额(设置)，经过争议初审、终审；反之只要争议初审
                String value = result.getData().getGlobalValue();
                if (carOrder.getCompensateMoney().compareTo(new BigDecimal(value)) >= 0) {
                    carOrder.setDisputeAuditStatus(CommonConstant.STATUS_EXPIRE);
                } else {
                    carOrder.setDisputeFlag(DisputeFlagEnum.DISPUTED.getKey());
                    CsCarDealer dealer = dealerService.getOneById(carOrder.getSysCarDealerId()).get();
                    accountDetailService.buildingOrder(carOrder.getBuyerUserId(), carOrder.getId(), null,
                            carOrder.getCompensateMoney(), "", TradeTypeEnum.INDEMNIFY_TYPE.getKey(),
                            null, "", null);
                    saveFinancePayMoney(carOrder.getId(), carOrder.getSalPrice().add(carOrder.getServiceFee()),
                            carOrder.getTransferMoney(), carOrder.getDisputeProcessType(), dealer.getPaymentType(), carOrder.getBuyerUserId());
                }
            } else if (carOrder.getDisputeAuditStatus().equals(CommonConstant.STATUS_DEL) &&
                    (DisputeProcessTypeEnum.BUYER_BREAK_CAR_TYPE.getKey().equals(carOrder.getDisputeProcessType()) ||
                            DisputeProcessTypeEnum.SELLER_BREAK_CAR_TYPE.getKey().equals(carOrder.getDisputeProcessType()) ||
                            DisputeProcessTypeEnum.NEGOTIATE_BACK_TYPE.getKey().equals(carOrder.getDisputeProcessType()))) {
                carOrder.setDisputeAuditStatus(CommonConstant.STATUS_EXPIRE);
            }
        }

        //审核日志json
        OrderAuditLogDTO orderAuditLog = new OrderAuditLogDTO();
        orderAuditLog.setOrderId(orderDispute.getOrderId());
        orderAuditLog.setAuditUser(username);
        orderAuditLog.setAuditType(AuditTypeEnum.AUDIT_DISPUTE_FIRST.getKey());
        orderAuditLog.setAuditTypeName(AuditTypeEnum.AUDIT_DISPUTE_FIRST.getName());
        orderAuditLog.setAuditRemark(orderDispute.getDisputeFirstAuditRemark());
        orderAuditLog.setAuditResult(OrderAuditStatusEnum.getNameByKey(orderDispute.getDisputeFirstAuditStatus()));
        orderAuditLog.setAuditTime(DateUtil.formatDateTime(date));

        CsOrderAudit orderAudit = orderAuditService.getByOrderId(orderDispute.getOrderId());
        orderAudit.setDisputeFirstAuditUserId(userId);
        orderAudit.setDisputeFirstAuditUser(username);
        orderAudit.setDisputeFirstAuditTime(date);
        orderAudit.setDisputeFirstAuditStatus(orderDispute.getDisputeFirstAuditStatus());
        orderAudit.setDisputeFirstAuditRemark(orderDispute.getDisputeFirstAuditRemark());
        parseOrderAuditLog(orderAudit, orderAuditLog);
        parseOrderDisputeAuditLog(orderAudit, orderAuditLog);

        boolean result = this.updateAllColumnById(carOrder);
        boolean orderAuditResult = orderAuditService.update(orderAudit, new EntityWrapper<CsOrderAudit>()
                .eq("cs_car_order_id", orderDispute.getOrderId())
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        deleteLockKey(orderDispute.getOrderId());
        if (!result || !orderAuditResult) {
            throw new RiggerException("该订单已争议初审");
        }
        if (DisputeFlagEnum.DISPUTED.getKey().equals(carOrder.getDisputeFlag())) {
            Result<String> re = systemServiceClient.unFreezeByUserIdAndDealerId(carOrder.getBuyerUserId(), carOrder.getBuyerFrozenMoney().toString(),
                    carOrder.getCarDealerUserId(), carOrder.getCarDealerFrozenMoney().toString());
            if (!re.isSuccess()) {
                throw new RiggerException(re.getMessage());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishAuditingDispute(Long userId, String username, OrderDisputeVO orderDispute) {
        CsCarOrder carOrder = this.getById(orderDispute.getOrderId());
        Integer orderAuditStatus = orderDispute.getDisputeFinishAuditStatus();
        if (Objects.isNull(orderAuditStatus)) {
            throw new RiggerException("终审审核状态不能为空");
        }
        if (!username.equals(carOrder.getOperationingUser()) ||
                !CommonConstant.STATUS_EXPIRE.equals(carOrder.getDisputeAuditStatus())) {
            throw new RiggerException("该订单已争议终审");
        }
        carOrder.setOperationingUser("");
        //退回
        if (OrderAuditStatusEnum.REJECTION.getKey().equals(orderAuditStatus)) {
            carOrder.setDisputeAuditStatus(CommonConstant.STATUS_NORMAL);
            carOrder.setDisputeProcessTime(null);
        } else {
            carOrder.setDisputeFlag(DisputeFlagEnum.DISPUTED.getKey());
            CsCarDealer dealer = dealerService.getOneById(carOrder.getSysCarDealerId()).get();
            saveFinancePayMoney(carOrder.getId(), carOrder.getSalPrice().add(carOrder.getServiceFee()), BigDecimal.ZERO,
                    carOrder.getDisputeProcessType(), dealer.getPaymentType(), carOrder.getBuyerUserId());
        }

        //审核日志json
        OrderAuditLogDTO orderAuditLog = new OrderAuditLogDTO();
        orderAuditLog.setOrderId(orderDispute.getOrderId());
        orderAuditLog.setAuditUser(username);
        orderAuditLog.setAuditType(AuditTypeEnum.AUDIT_DISPUTE_FINISH.getKey());
        orderAuditLog.setAuditTypeName(AuditTypeEnum.AUDIT_DISPUTE_FINISH.getName());
        orderAuditLog.setAuditRemark(orderDispute.getDisputeFinishAuditRemark());
        orderAuditLog.setAuditResult(OrderAuditStatusEnum.getNameByKey(orderDispute.getDisputeFinishAuditStatus()));
        orderAuditLog.setAuditTime(DateUtil.formatDateTime(DateUtil.date()));
        CsOrderAudit orderAudit = orderAuditService.getByOrderId(orderDispute.getOrderId());
        orderAudit.setDisputeFinishAuditUserId(userId);
        orderAudit.setDisputeFinishAuditUser(username);
        orderAudit.setDisputeFinishAuditTime(DateUtil.date());
        orderAudit.setDisputeFinishAuditStatus(orderDispute.getDisputeFinishAuditStatus());
        orderAudit.setDisputeFinishAuditRemark(orderDispute.getDisputeFinishAuditRemark());
        parseOrderAuditLog(orderAudit, orderAuditLog);
        parseOrderDisputeAuditLog(orderAudit, orderAuditLog);

        boolean result = this.updateAllColumnById(carOrder);
        boolean orderAuditResult = orderAuditService.update(orderAudit, new EntityWrapper<CsOrderAudit>()
                .eq("cs_car_order_id", orderDispute.getOrderId())
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        deleteLockKey(orderDispute.getOrderId());
        if (!result || !orderAuditResult) {
            throw new RiggerException("该订单已争议终审");
        }

        //如果是买家违约时，争议审核完了，自动把买家的冻结保证金打到卖家账户余额，卖家的冻结保证金自动回到账号账户余额
        if (DisputeProcessTypeEnum.BUYER_BREAK_CAR_TYPE.getKey().equals(carOrder.getDisputeProcessType())) {
            Long dealerUserId = carOrder.getCarDealerUserId();
            accountDetailService.buildingOrder(dealerUserId, carOrder.getId(), null, carOrder.getCarDealerFrozenMoney(), "", TradeTypeEnum.DRAW_BACK_TYPE.getKey(), null, "", null);
            accountDetailService.buildingOrder(dealerUserId, carOrder.getId(), null, carOrder.getBuyerFrozenMoney(), "", TradeTypeEnum.BREAK_CAR_TYPE.getKey(), null, "", null);
            Result<String> r = systemServiceClient.addBalanceToDealer(carOrder.getBuyerUserId(), carOrder.getBuyerFrozenMoney().toString(), dealerUserId, carOrder.getCarDealerFrozenMoney().toString());
            if (!r.isSuccess()) {
                throw new RiggerException(r.getMessage());
            }
        } else if (DisputeProcessTypeEnum.SELLER_BREAK_CAR_TYPE.getKey().equals(carOrder.getDisputeProcessType())) {
            //卖家违约时，争议审核完了，自动把卖家的冻结保证金打到买家账户余额，买家的冻结保证金自动回到账号账户余额
            Long buyerUserId = carOrder.getBuyerUserId();
            accountDetailService.buildingOrder(buyerUserId, carOrder.getId(), null, carOrder.getBuyerFrozenMoney(), "", TradeTypeEnum.DRAW_BACK_TYPE.getKey(), null, "", null);
            accountDetailService.buildingOrder(buyerUserId, carOrder.getId(), null, carOrder.getCarDealerFrozenMoney(), "", TradeTypeEnum.BREAK_CAR_TYPE.getKey(), null, "", null);
            Result<String> r = systemServiceClient.addBalanceToBuyer(buyerUserId, carOrder.getBuyerFrozenMoney().toString(), carOrder.getCarDealerUserId(), carOrder.getCarDealerFrozenMoney().toString());
            if (!r.isSuccess()) {
                throw new RiggerException(r.getMessage());
            }
        }
    }

    @Override
    public CsCarOrder getByCarId(Long carId) {
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setCsCarInfoId(carId);
        carOrder.setIsDeleted(CommonConstant.STATUS_NORMAL);
        CsCarOrder order = super.baseMapper.selectOne(carOrder);
        return Optional.ofNullable(order).orElseThrow(() -> new RiggerException("该车辆订单不存在"));
    }

    @Override
    public void overTimeHandle() {
        Result<SysConfigDTO> payMoneyOverTimeDTO = systemServiceClient.getByTypeAndName(ConfigTypeEnum.OVERTIME.getKey(), ConfigNameEnum.DEFAULT_CYCLE.getKey());
        Result<SysConfigDTO> transferOverTimeDTO = systemServiceClient.getByTypeAndName(ConfigTypeEnum.OVERTIME.getKey(), ConfigNameEnum.TIMEOUT_PERIOD.getKey());
        String payMoneyOverTime = payMoneyOverTimeDTO.getData().getGlobalValue();
        String transferOverTime = transferOverTimeDTO.getData().getGlobalValue();
        super.baseMapper.updatePayMoneyOverTimeOrder(payMoneyOverTime);
        super.baseMapper.updateTransferOverTimeOrder(transferOverTime);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeAuditor(List<Long> ids) {
        super.baseMapper.changeAuditor(ids);
        List<String> lockKeys = Lists.newArrayList();
        ids.forEach(o -> {
            lockKeys.add(prefix + ":orderLock:" + o);
        });
        redisTemplate.delete(lockKeys);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean process(Integer state, Integer tradeType, Long payType, Long orderId, Long vipId, Long userId, BigDecimal money, String thirdSerialNo) {
        //支付成功后，车款、有关联订单，修改订单状态为已支付
        if (CommonConstant.STATUS_DEL.equals(state) && Objects.nonNull(orderId) && Objects.nonNull(payType) &&
                !CommonConstant.STATUS_NORMAL.equals(payType.intValue()) &&
                TradeTypeEnum.CAR_MONEY_TYPE.getKey().equals(tradeType)) {
            updateOrderStatusToPaid(orderId);
            CsCarOrder order = super.baseMapper.selectById(orderId);
            CsCarDealer dealer = dealerService.getOneById(order.getSysCarDealerId()).get();
            if (CommonConstant.STATUS_DEL.equals(dealer.getPaymentType())) {
                saveFinanceCarMoney(orderId, money, dealer.getPaymentType(), dealer.getSysUserId());
            }
            return Boolean.TRUE;
        } else if (CommonConstant.STATUS_DEL.equals(state) && Objects.nonNull(orderId) &&
                Objects.nonNull(payType) &&
                !CommonConstant.STATUS_NORMAL.equals(payType.intValue()) &&
                TradeTypeEnum.CAR_RECHECK_TYPE.getKey().equals(tradeType)) {
            //支付成功后，复检，修改订单复检状态
            CsCarOrder order = super.baseMapper.selectById(orderId);
            updateRecheckFlagToRecheck(orderId, order.getBuyerName());
            return Boolean.TRUE;
        } else if (CommonConstant.STATUS_DEL.equals(state) && Objects.nonNull(vipId) &&
                Objects.nonNull(payType) &&
                !CommonConstant.STATUS_NORMAL.equals(payType.intValue()) &&
                TradeTypeEnum.OPEN_VIP_TYPE.getKey().equals(tradeType)) {
            //支付成功后，充值会员卡，绑定会员卡
            UserDTO user = getUserById(userId);
            userVipService.bindingMember(vipId, userId, user.getUsername());
            return Boolean.TRUE;
        } else if (CommonConstant.STATUS_DEL.equals(state) && Objects.nonNull(payType) &&
                !CommonConstant.STATUS_NORMAL.equals(payType.intValue()) &&
                TradeTypeEnum.BALANCE_RECHARGE_TYPE.getKey().equals(tradeType)) {
            //余额充值
            Result<String> result = systemServiceClient.addBalanceByUserId(userId, money.toString());
            if (!result.isSuccess()) {
                log.error("余额充值时，账户余额变更异常。{}", result.getMessage());
                throw new RiggerException("支付失败");
            }
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payCallbackHandler(String serialNo, Integer state, Date payTime, String remark) {
        CsAccountDetail accountDetail = accountDetailService.getByThirdSerialNo(serialNo);
        CsAccountDetail detail = new CsAccountDetail();
        detail.setStatus(state);
        detail.setPayTime(payTime);
        detail.setRemark(remark);
        boolean result = accountDetailService.update(detail, new EntityWrapper<CsAccountDetail>()
                .eq("id", accountDetail.getId())
                .eq("status", accountDetail.getStatus())
                .eq("sys_user_id", accountDetail.getSysUserId())
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (result) {
            Boolean re = this.process(accountDetail.getStatus(), accountDetail.getTradeType(), accountDetail.getPayType(), accountDetail.getCsCarOrderId(),
                    accountDetail.getVipId(), accountDetail.getSysUserId(), accountDetail.getMoney(), accountDetail.getThirdSerialNo());
            if (re || state.intValue() == 3 || state.intValue() == 6) {
                redisTemplate.opsForList().remove(StrUtil.format("{}:thirdSerialNo", prefix), 0, accountDetail.getThirdSerialNo());
            }
        }
    }

    @Override
    public CarOrderDTO getOrderAndAuditByOrderIdAndCarId(Long orderId, Long carId) {
        return super.baseMapper.getOrderAndAuditByOrderIdAndCarId(orderId, carId);
    }

    @Override
    public OrderCheckAndDisputeDTO getOrderCheckAndDisputeByOrderId(Long orderId) {
        OrderCheckAndDisputeDTO dto = new OrderCheckAndDisputeDTO();
        CsCarOrder carOrder = this.getById(orderId);
        if (Objects.isNull(carOrder)) {
            return dto;
        }
        dto.setId(carOrder.getId());
        dto.setRecheckResult(carOrder.getRecheckResult());
        List<CsOrderDispute> disputes = orderDisputeService.selectList(new EntityWrapper<CsOrderDispute>()
                .eq("cs_car_order_id", orderId)
                .eq("recheck_flag", CommonConstant.STATUS_DEL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        dto.setDisputeItems(BeanUtils.beansToList(disputes, DisputeItemVO.class));
        return dto;
    }

    /*******************************************************************************************************/

    private BigDecimal getRecheckMoneyByOrderId(Long orderId) {
        CsAccountDetail accountDetail = accountDetailService.selectOne(new EntityWrapper<CsAccountDetail>()
                .eq("cs_car_order_id", orderId)
                .eq("status", CommonConstant.STATUS_DEL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("trade_type", TradeTypeEnum.CAR_RECHECK_TYPE.getKey()));
        return Objects.isNull(accountDetail) ? BigDecimal.ZERO : accountDetail.getMoney();
    }

    /**
     * 保存车款数据
     *
     * @param orderId
     * @param money
     * @param paymentType
     */
    private void saveFinanceCarMoney(Long orderId, BigDecimal money, Integer paymentType, Long dealerUserId) {
        CsUserBankCardDTO bankCard = userBankCardService.getBankCardByUserId(dealerUserId);
        Result<SysConfigDTO> result = systemServiceClient.getByTypeAndName(ConfigTypeEnum.CHECK.getKey(), ConfigNameEnum.CHECK_AMOUN.getKey());
        CsFinancePayAction financePayAction = new CsFinancePayAction();
        financePayAction.setBankName(bankCard.getBankName());
        financePayAction.setBankCardNo(bankCard.getBankCardNo());
        financePayAction.setBankUserTel(bankCard.getBankUserTel());
        financePayAction.setBankUserName(bankCard.getBankUserName());
        financePayAction.setBankUserCardNo(bankCard.getBankUserCardNo());
        financePayAction.setCsCarOrderId(orderId);
        financePayAction.setPaymentType(paymentType);
        BigDecimal decimal = StrUtil.isBlank(result.getData().getGlobalValue()) ? BigDecimal.ZERO : NumberUtil.toBigDecimal(result.getData().getGlobalValue());
        financePayAction.setPayMoney(money.subtract(decimal));
        financePayAction.setPayType(CommonConstant.STATUS_NORMAL);
        financePayActionService.insert(financePayAction);
    }


    /**
     * 保存打款金额
     *
     * @param orderId
     * @param money              车款+ 服务费
     * @param transferMoney      过户费
     * @param disputeProcessType 争议处理方式
     * @param paymentType        车商支付方式
     * @param buyerUserId        买方用户id
     */
    private void saveFinancePayMoney(Long orderId, BigDecimal money, BigDecimal transferMoney, Integer disputeProcessType, Integer paymentType, Long buyerUserId) {
        CsUserBankCardDTO bankCard = userBankCardService.getBankCardByUserId(buyerUserId);
        Integer payType = DisputeProcessTypeEnum.INDEMNIFY_TYPE.getKey().equals(disputeProcessType) ? CommonConstant.STATUS_LOCK : CommonConstant.STATUS_DEL;
        if (DisputeProcessTypeEnum.BUYER_BREAK_CAR_TYPE.getKey().equals(disputeProcessType)) {
            //车款+服务费 + 复检费 - 过户费（处理方式：买家违约）
            money = money.add(getRecheckMoneyByOrderId(orderId)).subtract(transferMoney);
        } else if (DisputeProcessTypeEnum.NEGOTIATE_BACK_TYPE.getKey().equals(disputeProcessType) ||
                DisputeProcessTypeEnum.SELLER_BREAK_CAR_TYPE.getKey().equals(disputeProcessType)) {
            money = money.add(getRecheckMoneyByOrderId(orderId));
        }
        CsFinancePayAction financePayAction = new CsFinancePayAction();
        financePayAction.setBankName(bankCard.getBankName());
        financePayAction.setBankCardNo(bankCard.getBankCardNo());
        financePayAction.setBankUserTel(bankCard.getBankUserTel());
        financePayAction.setBankUserName(bankCard.getBankUserName());
        financePayAction.setBankUserCardNo(bankCard.getBankUserCardNo());
        financePayAction.setCsCarOrderId(orderId);
        financePayAction.setPaymentType(paymentType);
        financePayAction.setPayMoney(money);
        financePayAction.setPayType(payType);
        financePayActionService.insert(financePayAction);
        if (DisputeProcessTypeEnum.INDEMNIFY_TYPE.getKey().equals(payType)) {
            financePayActionService.delete(new EntityWrapper<CsFinancePayAction>()
                    .eq("cs_car_order_id", orderId)
                    .eq("pay_type", CommonConstant.STATUS_NORMAL)
                    .eq("payment_status", CommonConstant.STATUS_NORMAL));
        }
    }

    private void buildMessagePush(Long sender, Long receiver, BigDecimal salPrice, Long carInfoId, String brandName, Integer messageType) {
        OrderPushMessageDTO pushMessage = new OrderPushMessageDTO();
        pushMessage.setSender(sender);
        pushMessage.setPrice(salPrice);
        pushMessage.setCarInfoId(carInfoId);
        pushMessage.setReceiver(receiver);
        pushMessage.setCarBrandName(brandName);
        pushMessage.setMessageType(messageType);
        pushMessage.setTitle(MessagePushTypeEnum.getNameByKey(messageType));
        pushMessage.setStatus(MessagePushTypeEnum.getNameByKey(messageType));
        applicationEventPublisher.publishEvent(pushMessage);
    }


    private void deleteLockKey(Long orderId) {
        String lockKey = prefix + ":orderLock:" + orderId;
        if (redisTemplate.hasKey(lockKey)) {
            redisTemplate.delete(lockKey);
        }
    }

    private Boolean getBooleanFuture(String serialNo, ScheduledExecutorService executorService) throws ExecutionException, InterruptedException {
        if (!payThreadLocal.containsKey(serialNo)) {
            payThreadLocal.put(serialNo, new AtomicInteger(1));
        } else {
            AtomicInteger integer = payThreadLocal.get(serialNo);
            integer.getAndAdd(1);
            payThreadLocal.put(serialNo, integer);
        }
        if (payThreadLocal.get(serialNo).get() > 3) {
            return Boolean.FALSE;
        }
        return executorService.schedule(() -> {
            CsAccountDetail accountDetail = accountDetailService.getBySerialNo(serialNo);
            return process(accountDetail.getStatus(), accountDetail.getTradeType(), accountDetail.getPayType(),
                    accountDetail.getCsCarOrderId(), accountDetail.getVipId(), accountDetail.getSysUserId(),
                    accountDetail.getMoney(), accountDetail.getThirdSerialNo());
        }, 3, TimeUnit.SECONDS).get();
    }

    /**
     * 是否是体验会员
     *
     * @param vipId
     * @return
     */
    boolean isExperienceMember(Long vipId) {
        CsVip vip = vipService.selectById(vipId);
        return CommonConstant.STATUS_DEL.equals(vip.getType()) ? true : false;
    }


    /**
     * 支付后更新订单状态为复检
     *
     * @param orderId
     */
    private void updateRecheckFlagToRecheck(Long orderId, String username) {
        if (Objects.isNull(orderId)) {
            return;
        }
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setRecheckApplyUser(username);
        carOrder.setRecheckApplyTime(DateUtil.date());
        carOrder.setRecheckType(CommonConstant.STATUS_DEL);
        carOrder.setRecheckFlag(CommonConstant.STATUS_DEL);
        boolean result = this.update(carOrder, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("recheck_type", CommonConstant.STATUS_NORMAL)
                .eq("recheck_flag", CommonConstant.STATUS_NORMAL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("该订单已复检");
        }
    }

    /**
     * 支付后更新订单状态为已付款
     *
     * @param orderId
     */
    private void updateOrderStatusToPaid(Long orderId) {
        if (Objects.isNull(orderId)) {
            return;
        }
        CsCarOrder carOrder = new CsCarOrder();
        carOrder.setStatus(OrderStatusEnum.PAID.getKey());
        boolean result = this.update(carOrder, new EntityWrapper<CsCarOrder>()
                .eq("id", orderId)
                .eq("status", OrderStatusEnum.TO_SELL.getKey())
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("该订单不存在");
        }
    }

    /**
     * 解析订单审核日志信息
     *
     * @param orderAudit
     * @param orderAuditLog
     * @return
     */
    private void parseOrderAuditLog(CsOrderAudit orderAudit, OrderAuditLogDTO orderAuditLog) {
        List<OrderAuditLogDTO> orderAudits = Lists.newArrayList();
        if (StrUtil.isNotBlank(orderAudit.getAuditJsonContent())) {
            orderAudits = JSONUtil.toList(JSONUtil.parseArray(orderAudit.getAuditJsonContent()), OrderAuditLogDTO.class);
        }
        orderAudits.add(orderAuditLog);
        orderAudit.setAuditJsonContent(JSONUtil.toJsonStr(orderAudits));
    }

    /**
     * 解析订单争议初审、终审审核日志信息
     *
     * @param orderAudit
     * @param orderAuditLog
     * @return
     */
    private void parseOrderDisputeAuditLog(CsOrderAudit orderAudit, OrderAuditLogDTO orderAuditLog) {
        List<OrderAuditLogDTO> orderAudits = Lists.newArrayList();
        if (StrUtil.isNotBlank(orderAudit.getDisputeAuditJsonContent())) {
            orderAudits = JSONUtil.toList(JSONUtil.parseArray(orderAudit.getDisputeAuditJsonContent()), OrderAuditLogDTO.class);
        }
        orderAudits.add(orderAuditLog);
        orderAudit.setDisputeAuditJsonContent(JSONUtil.toJsonStr(orderAudits));
    }


    /**
     * 是否先打款后过户
     *
     * @param status      订单状态
     * @param paymentType 车商付款模式
     * @return
     */
    private boolean isFistPayment(Integer status, Integer paymentType) {
        return CommonConstant.STATUS_DEL.equals(paymentType) && OrderStatusEnum.PAYMENT.getKey().equals(status);
    }

    /**
     * 是否先过户后打款
     *
     * @param paymentType
     * @return
     */
    private boolean isFistTransfer(Integer paymentType) {
        return CommonConstant.STATUS_NORMAL.equals(paymentType);
    }


    /**
     * 是否显示争议按钮
     *
     * @param status
     * @return
     */
    private boolean isShowDisputeButton(Integer status) {
        return status.equals(OrderStatusEnum.PAID.getKey()) ||
                status.equals(OrderStatusEnum.TRANSFERRED.getKey()) ||
                status.equals(OrderStatusEnum.PAYMENT.getKey()) ||
                status.equals(OrderStatusEnum.COMPLETE.getKey());
    }

    /**
     * 是否显示过户按钮
     *
     * @param status
     * @param transferStatus
     * @return
     */
    private boolean isShowTransferButton(Integer status, Integer transferStatus) {
        return (OrderStatusEnum.PAID.getKey().equals(status) ||
                OrderStatusEnum.PAYMENT.getKey().equals(status)) &&
                TransferStatusEnum.UN_TRANSFERRED.getKey().equals(transferStatus);
    }


    /**
     * 可用余额
     *
     * @param frozenAmount
     * @return
     */
    private BigDecimal enableBalance(BigDecimal frozenAmount) {
        Result<BigDecimal> enableBalance = systemServiceClient.enableBalance(frozenAmount.toString());
        if (!enableBalance.isSuccess()) {
            throw new RiggerException(enableBalance.getMessage());
        }
        return enableBalance.getData();
    }

    /**
     * 保证金
     *
     * @param type
     * @param message
     * @return
     */
    private BigDecimal enableFrozenAmount(Integer type, String message) {
        Result<BigDecimal> frozenAmount = systemServiceClient.enableFrozenAmount(type);
        if (!frozenAmount.isSuccess() || frozenAmount.getData().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RiggerException(message);
        }
        return frozenAmount.getData();
    }

    private UserAreaDTO getUserAreaByUserId(Long userId) {
        Result<UserAreaDTO> result = systemServiceClient.getUserAreaByUserId(userId);
        if (!result.isSuccess()) {
            throw new RiggerException("获取用户所在城市信息失败");
        }
        return result.getData();
    }

    private UserDTO getUserById(Long userId) {
        Result<UserDTO> userDTOResult = systemServiceClient.getUserByUserId(userId);
        if (!userDTOResult.isSuccess()) {
            throw new RiggerException("获取用户信息失败");
        }
        return userDTOResult.getData();
    }

    /**
     * 更新车辆交易状态
     *
     * @param carId
     * @param oldTradeFlag    原先的状态
     * @param targetTradeFlag 目标修改的状态
     * @param publishStatus   车辆的状态
     */
    private boolean updateCarTradeFlag(Long carId, Integer oldTradeFlag, Integer targetTradeFlag, Integer publishStatus) {
        CsCarInfo carInfo = new CsCarInfo();
        carInfo.setTradeFlag(targetTradeFlag);
        carInfo.setPublishStatus(publishStatus);
        return carInfoService.update(carInfo, new EntityWrapper<CsCarInfo>()
                .eq("id", carId)
                .eq("trade_flag", oldTradeFlag)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
    }


    /**
     * 过户申请时，需要清除过户审核信息
     *
     * @param orderId
     */
    private void cleanTransferAuditInfo(Long orderId) {
        CsOrderAudit orderAudit = orderAuditService.selectOne(new EntityWrapper<CsOrderAudit>()
                .eq("cs_car_order_id", orderId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        orderAudit.setTransferAuditStatus(null);
        orderAudit.setTransferAuditTime(null);
        orderAudit.setTransferAuditUserId(null);
        orderAudit.setTransferAuditUser("");
        orderAudit.setTransferAuditRemark("");
        orderAuditService.updateAllColumnById(orderAudit);
    }


    //1：买入(待卖出)，2：待付款(跨城)，3：未过户(待过户)，4：过户，5：待收款，6：完成，7：争议，8：已取消
    private void setStatusParam(Map<String, Object> param) {
        Integer tradeType = (Integer) param.get("tradeType");
        param.put("orderByField", "a.sell_time desc");
        switch (tradeType) {
            case 1:
                param.put("desc", "a.gmt_create desc");
                param.put("statusList", Arrays.asList(OrderStatusEnum.BUYING.getKey(),
                        OrderStatusEnum.CANCEL_BUYING_APPLY.getKey()));
                break;
            case 2:
                param.put("status", OrderStatusEnum.TO_SELL.getKey());
                param.put("sameCityFlag", CommonConstant.STATUS_NORMAL);
                break;
            case 3:
                param.put("statusList", Arrays.asList(OrderStatusEnum.PAID.getKey(),
                        OrderStatusEnum.PAYMENT.getKey()));
                param.put("transferStatus", TransferStatusEnum.UN_TRANSFERRED.getKey());
             /*   if (param.containsKey("carDealerUserId") && Objects.nonNull(param.get("carDealerUserId"))) {
                    CsCarDealer dealer = dealerService.getByUserId((Long) param.get("carDealerUserId"));
                    if (CommonConstant.STATUS_DEL.equals(dealer.getPaymentType())) {
                        param.put("paymentStatus", PaymentStatusEnum.HAS_PAYMENT.getKey());
                    }
                }*/
                break;
            case 4:
                param.put("statusList", Arrays.asList(OrderStatusEnum.PAID.getKey(), OrderStatusEnum.TRANSFERRED.getKey(),
                        OrderStatusEnum.PAYMENT.getKey()));
//                param.put("transferStatusList", Arrays.asList(TransferStatusEnum.TRANSFERRED.getKey(), TransferStatusEnum.TRANSFERRED.getKey()));
                break;
            case 5:
                param.put("statusList", Arrays.asList(OrderStatusEnum.PAID.getKey(),
                        OrderStatusEnum.TRANSFERRED.getKey()));
//                param.put("paymentStatus", CommonConstant.STATUS_NORMAL);
                if (param.containsKey("carDealerUserId") && Objects.nonNull(param.get("carDealerUserId"))) {
                    CsCarDealer dealer = dealerService.getByUserId((Long) param.get("carDealerUserId"));
                    param.put("transferStatus", isFistTransfer(dealer.getPaymentType()) ?
                            TransferStatusEnum.TRANSFERRED.getKey() : TransferStatusEnum.UN_TRANSFERRED.getKey());
                }
                break;
            case 6:
                param.put("orderByField", "a.finish_time desc");
                param.put("status", OrderStatusEnum.COMPLETE.getKey());
                break;
            case 7:
                param.put("orderByField", "a.dispute_apply_time desc");
                param.put("disputeFlagList", Arrays.asList(DisputeFlagEnum.IN_DISPUTE.getKey(), DisputeFlagEnum.DISPUTED.getKey()));
                break;
            case 8:
                param.put("orderByField", "a.refuse_sell_time desc,b.cancel_buy_audit_audit_time desc");
                param.put("statusList", Arrays.asList(OrderStatusEnum.CANCEL_BUYING_PASS.getKey(),
                        OrderStatusEnum.REFUSE_TO_SELL.getKey()));
                break;
            default:
                break;
        }
    }
}
