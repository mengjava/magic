package com.haoqi.magic.business.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.PayCenterBusinessServiceClient;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.TradeTypeEnum;
import com.haoqi.magic.business.mapper.CsAccountDetailMapper;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.CsAccountDetail;
import com.haoqi.magic.business.model.entity.CsCarOrder;
import com.haoqi.magic.business.service.ICsAccountDetailService;
import com.haoqi.magic.business.service.ICsCarOrderService;
import com.haoqi.magic.business.service.ICsPayConfigService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.mybatis.provider.OrderNumberProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 账单明细表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-12-02
 */
@Slf4j
@Service
@RefreshScope
public class CsAccountDetailServiceImpl extends ServiceImpl<CsAccountDetailMapper, CsAccountDetail> implements ICsAccountDetailService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Autowired
    private ICsPayConfigService payConfigService;

    @Autowired
    @Qualifier("paymentOrderNumberProvider")
    private OrderNumberProvider paymentOrderNumberProvider;

    @Value("${appKey:PAYAPP0014}")
    private String appKey;
    @Value("${appSecret:1d734176d03b49678074e748f3b680bc}")
    private String appSecret;

    /**
     * 支付回调
     */
    @Value(("${payCallbackUrl}"))
    private String payCallbackUrl;

    @Autowired
    private PayCenterBusinessServiceClient payCenterBusinessServiceClient;

    @Autowired
    private ICsCarOrderService orderService;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentOrderDTO buildingOrder(Long userId, Long orderId, Long vipId, BigDecimal money, String numText,
                                         Integer tradeType, Long paymentId, String device, Integer source) {
        Result<UserDTO> user = systemServiceClient.getUserByUserId(userId);
        if (!user.isSuccess()) {
            throw new RiggerException("获取用户信息失败，请稍后再试");
        }
        String body = "";
        String thirdSerialNo = "";
        CsPayConfigDTO payConfigDTO = null;
        String paySerialNo = paymentOrderNumberProvider.building();
        String text = TradeTypeEnum.getNameByKey(tradeType) + moneyFormat(money) + "元 " + numText;
        if (TradeTypeEnum.CAR_MONEY_TYPE.getKey().equals(tradeType)) {
            CsCarOrder carOrder = orderService.getById(orderId);
            BigDecimal price = NumberUtil.add(carOrder.getSalPrice().multiply(new BigDecimal(10000)), carOrder.getServiceFee());
            if (price.compareTo(money) != 0) {
                throw new RiggerException("请支付车款" + price + "元");
            }
        } else if (TradeTypeEnum.CAR_RECHECK_TYPE.getKey().equals(tradeType)) {
            Result<SysConfigDTO> result = systemServiceClient.getByTypeAndName(8, 80);
            if (StrUtil.isBlank(result.getData().getGlobalValue()) ||
                    NumberUtil.toBigDecimal(result.getData().getGlobalValue()).compareTo(money) != 0) {
                throw new RiggerException("请支付复检金额" + result.getData().getGlobalValue() + "元");
            }
        }
        //0 默认 余额支付
        if (paymentIdNonNullAndEqZero(paymentId)) {
            Result<BigDecimal> decimalResult = systemServiceClient.enableBalance(money.toString());
            if (!decimalResult.isSuccess()) {
                throw new RiggerException(decimalResult.getMessage());
            }
        } else if (Objects.nonNull(paymentId) && !CommonConstant.STATUS_NORMAL.equals(paymentId.intValue())) {
            payConfigDTO = payConfigService.getById(paymentId);
            PayBaseDTO payBase = new PayBaseDTO();
            payBase.setSubject(text);
            payBase.setAppKey(appKey);
            payBase.setDeviceInfo(device);
            payBase.setAppSecret(appSecret);
            payBase.setSerialNo(paySerialNo);
            payBase.setNotifyUrl(payCallbackUrl);
            payBase.setClientIp(NetUtil.getLocalhostStr());
            payBase.setDataFrom(Objects.isNull(source) ? 0 : source);
            payBase.setMoney(NumberUtil.mul(money, new BigDecimal(100)).intValue());
            payBase.setProductCode(Integer.parseInt(payConfigDTO.getProductCode()));
            Result<PayOrderDTO> result = payCenterBusinessServiceClient.produceOrder(payBase);
            if (!result.isSuccess()) {
                log.error("生成支付订单失败，支付产品code：{}，支付回调地址：{} 异常：{}", payConfigDTO.getProductCode(), payCallbackUrl, result.getMessage());
                throw new RiggerException("生成支付订单失败");
            }
            PayOrderDTO dto = result.getData();
            thirdSerialNo = dto.getSerialNo();
            body = dto.getBody();
        }
        Integer state = CommonConstant.STATUS_NORMAL;
        if (TradeTypeEnum.DRAW_BACK_TYPE.getKey().equals(tradeType) || Objects.isNull(paymentId) ||
                TradeTypeEnum.FREEZE_AMOUNT_TYPE.getKey().equals(tradeType) ||
                TradeTypeEnum.BREAK_CAR_TYPE.getKey().equals(tradeType) ||
                (paymentIdNonNullAndEqZero(paymentId) && !TradeTypeEnum.CASH_TYPE.getKey().equals(tradeType))) {
            state = CommonConstant.STATUS_DEL;
        } else if (TradeTypeEnum.CASH_TYPE.getKey().equals(tradeType)) {
            state = CommonConstant.STATUS_EXPIRE;
        }
        CsAccountDetail accountDetail = new CsAccountDetail();
        accountDetail.setVipId(vipId);
        accountDetail.setStatus(state);
        accountDetail.setSysUserId(userId);
        accountDetail.setTradeRemark(text);
        accountDetail.setPayType(paymentId);
        accountDetail.setTradeType(tradeType);
        accountDetail.setSerialNo(paySerialNo);
        accountDetail.setCsCarOrderId(orderId);
        accountDetail.setThirdSerialNo(thirdSerialNo);
        accountDetail.setPayTypeDesc(Objects.nonNull(paymentId) && paymentId > 0 ? payConfigDTO.getShowName() : "余额支付");
        accountDetail.setType(tradeType >= TradeTypeEnum.BALANCE_RECHARGE_TYPE.getKey() ? CommonConstant.STATUS_DEL : CommonConstant.STATUS_EXPIRE);
        accountDetail.setMoney(tradeType >= TradeTypeEnum.BALANCE_RECHARGE_TYPE.getKey() ? money : NumberUtil.mul(money, NumberUtil.toBigDecimal(-1)));
        this.insert(accountDetail);
        if (StrUtil.isNotBlank(thirdSerialNo)) {
            redisTemplate.opsForList().leftPush(StrUtil.format("{}:thirdSerialNo", prefix), thirdSerialNo);
        }
        PaymentOrderDTO paymentOrder = new PaymentOrderDTO();
        paymentOrder.setBody(body);
        paymentOrder.setMoney(money);
        paymentOrder.setSerialNo(accountDetail.getSerialNo());
        paymentOrder.setTradeDesc(accountDetail.getTradeRemark());
        return paymentOrder;
    }

    @Override
    public PaymentOrderDTO buildingOrder(Long userId, BigDecimal money, Integer tradeType, String device, Integer source) {
        return this.buildingOrder(userId, null, null, money, "", tradeType, 0L, device, source);
    }

    @Override
    public CsAccountDetail getBySerialNo(String serialNo) {
        CsAccountDetail accountDetail = this.selectOne(new EntityWrapper<CsAccountDetail>()
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("serial_no", serialNo));
        return Optional.ofNullable(accountDetail).orElseThrow(() -> new RiggerException("支付订单不存在"));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paymentCallBack(PaymentCallBackDTO paymentCallBack) {
        CsAccountDetail accountDetail = this.selectOne(new EntityWrapper<CsAccountDetail>()
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("third_serial_no", paymentCallBack.getSerialNo()));
        Optional.ofNullable(accountDetail).orElseThrow(() -> new RiggerException("支付订单不存在"));
        if (CommonConstant.STATUS_NORMAL.equals(paymentCallBack.getStatus())) {
            return;
        }
        accountDetail.setPayTime(DateUtil.date());
        accountDetail.setStatus(paymentCallBack.getStatus());
        this.updateById(accountDetail);
        Boolean result = orderService.process(accountDetail.getStatus(), accountDetail.getTradeType(), accountDetail.getPayType(), accountDetail.getCsCarOrderId(),
                accountDetail.getVipId(), accountDetail.getSysUserId(), accountDetail.getMoney(), accountDetail.getThirdSerialNo());
        if (result) {
            redisTemplate.opsForList().remove(StrUtil.format("{}:thirdSerialNo", prefix), 0, accountDetail.getThirdSerialNo());
        }
    }

    @Override
    public Page<AccountDetailDTO> findPage(Query query) {
        List<AccountDetailDTO> accountDetails = super.baseMapper.findPage(query, query.getCondition());
        return query.setRecords(accountDetails);
    }

    @Override
    public CsAccountDetail getByThirdSerialNo(String serialNo) {
        CsAccountDetail accountDetail = this.selectOne(new EntityWrapper<CsAccountDetail>()
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .eq("third_serial_no", serialNo));
        return Optional.ofNullable(accountDetail).orElseThrow(() -> new RiggerException("支付订单不存在"));
    }

    @Override
    public AccountTotalAmountDTO totalAmount(String timeStart, String timeEnd, Integer tradeType, Long userId) {
        return super.baseMapper.totalAmount(timeStart, timeEnd, tradeType, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void paymentInHandler() {
        List<CsAccountDetail> details = this.selectList(new EntityWrapper<CsAccountDetail>()
                .le("gmt_create", DateUtil.offsetHour(DateUtil.date(), 5))
                .in("status", Arrays.asList(CommonConstant.STATUS_NORMAL, CommonConstant.STATUS_EXPIRE))
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        details.forEach(o -> {
            o.setRemark("未支付超时后关闭");
            o.setStatus(6);
            boolean r = this.updateById(o);
            if (r && StrUtil.isNotBlank(o.getThirdSerialNo())) {
                redisTemplate.opsForList().remove(StrUtil.format("{}:thirdSerialNo", prefix), 0, o.getThirdSerialNo());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(String serialNo) {
        CsAccountDetail accountDetail = getBySerialNo(serialNo);
        accountDetail.setStatus(6);
        this.updateById(accountDetail);
        PayBaseDTO payBaseDTO = new PayBaseDTO();
        payBaseDTO.setSerialNo(accountDetail.getThirdSerialNo());
        Result<PayResultDTO> result = payCenterBusinessServiceClient.closePay(payBaseDTO);
        if (!result.isSuccess()) {
            throw new RiggerException(result.getMessage());
        }
    }

    private boolean paymentIdNonNullAndEqZero(Long paymentId) {
        return Objects.nonNull(paymentId) && CommonConstant.STATUS_NORMAL.equals(paymentId.intValue());
    }

    private BigDecimal moneyFormat(BigDecimal money) {
        return money.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
