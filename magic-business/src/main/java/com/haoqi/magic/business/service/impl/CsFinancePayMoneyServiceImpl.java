package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.*;
import com.haoqi.magic.business.mapper.CsFinancePayActionMapper;
import com.haoqi.magic.business.mapper.CsFinancePayMoneyMapper;
import com.haoqi.magic.business.model.dto.CsFinancePageDTO;
import com.haoqi.magic.business.model.dto.CsFinancePayMoneyDTO;
import com.haoqi.magic.business.model.entity.CsCarOrder;
import com.haoqi.magic.business.model.entity.CsFinancePayAction;
import com.haoqi.magic.business.model.entity.CsFinancePayMoney;
import com.haoqi.magic.business.model.vo.CsFinancePayMoneyVO;
import com.haoqi.magic.business.service.*;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 财务打款表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
@Service
@Slf4j
public class CsFinancePayMoneyServiceImpl extends ServiceImpl<CsFinancePayMoneyMapper, CsFinancePayMoney> implements ICsFinancePayMoneyService {

    @Autowired
    private ICsCarOrderService carOrderService;

    @Autowired
    private ICsCarInfoService carInfoService;

    @Autowired
    private CsFinancePayActionMapper csFinancePayActionMapper;

    @Autowired
    private ICsAccountDetailService accountDetailService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Override
    public Page selectByPayPage(Query query) {
        List<CsFinancePayMoneyDTO> list = this.baseMapper.selectByPage(query, query.getCondition());
        return query.setRecords(list);
    }


    /**
     * 保存打款信息(帐号 附件)
     * <p>
     * 买家车款(买家付款后买家违约、买家付款后卖家违约、买家付款后协商平退)
     * <p>
     * 卖家车款
     * 1）先过户后收款：过户后
     * 2）先收款后过户：买家付款后)
     * <p>
     * 收款信息(1.如果收款项为“车款+服务费”，且买卖交易状态为卖出还未买家付款，此时把该买卖交易状态改为“买家支付”，对应的买家的账单新增一条“车款+服务费”账单。)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean add(CsFinancePayMoneyVO vo) {
        CsFinancePayAction csFinancePayAction = csFinancePayActionMapper.selectById(vo.getId());
        if (Objects.isNull(csFinancePayAction)) {
            throw new RiggerException("订单不存在");
        }
        if (PaymentStatusEnum.HAS_PAYMENT.getKey().equals(csFinancePayAction.getPaymentStatus())) {
            throw new RiggerException("该订单已打款,请勿重复提交");
        }
        CsCarOrder order = carOrderService.getById(vo.getCsCarOrderId());
        Boolean flag = checkProcessType(order.getDisputeProcessType());
        Integer paymentType = csFinancePayAction.getPaymentType();
        //赔偿金
        if (PayMoneyTypeEnum.PAY_BUYER_TYPE.getKey().equals(vo.getPayMoneyType())) {
            //买家车款
            saveBuyerPayMoney(order, csFinancePayAction, flag);
        } else if (PayMoneyTypeEnum.PAY_SALER_TYPE.getKey().equals(vo.getPayMoneyType())) {
            //卖家车款
            saveSalerPayMoney(order, paymentType, vo, csFinancePayAction);
        } else if (PayMoneyTypeEnum.PAY_BUYER_TYPE.getKey().equals(vo.getPayMoneyType())) {
            //收款
            saveReceivePayMoney(vo, order);
        }

        CsFinancePayMoney csFinancePayMoney = BeanUtils.beanCopy(vo, CsFinancePayMoney.class);
        return this.insert(csFinancePayMoney);
    }

    private Boolean checkProcessType(Integer disputeProcessType) {
        Boolean flag = Boolean.FALSE;
        if (DisputeProcessTypeEnum.BUYER_BREAK_CAR_TYPE.getKey().equals(disputeProcessType)
                || DisputeProcessTypeEnum.SELLER_BREAK_CAR_TYPE.getKey().equals(disputeProcessType)
                || DisputeProcessTypeEnum.NEGOTIATE_BACK_TYPE.getKey().equals(disputeProcessType)
                ) {
            flag = true;
        }
        return flag;
    }

    /**
     * 打款买家
     *
     * @param order
     * @param csFinancePayAction
     * @param flag
     */
    private void saveBuyerPayMoney(CsCarOrder order, CsFinancePayAction csFinancePayAction, Boolean flag) {
        if (DisputeFlagEnum.DISPUTED.getKey().equals(order.getDisputeFlag())) {
            CsCarOrder update = new CsCarOrder();
            update.setId(order.getId());
            update.setGmtModified(DateUtil.date());
            if (!DisputeProcessTypeEnum.INDEMNIFY_TYPE.getKey().equals(order.getDisputeProcessType())) {
                carInfoService.completeAndPutOffCar(order.getCsCarInfoId(), flag);
                update.setStatus(OrderStatusEnum.COMPLETE.getKey());
            }
            carOrderService.updateById(update);
            csFinancePayAction.setGmtModified(DateUtil.date());
            csFinancePayAction.setPaymentStatus(PaymentStatusEnum.HAS_PAYMENT.getKey());
            csFinancePayAction.setPaymentTime(DateUtil.date());
            csFinancePayActionMapper.updateById(csFinancePayAction);
            //赔偿金
            if (PayTypeEnum.THREE_TYPE.getKey().equals(csFinancePayAction.getPayType())) {
                accountDetailService.buildingOrder(order.getBuyerUserId(), order.getId(), null,
                        csFinancePayAction.getPayMoney(), "", TradeTypeEnum.INDEMNIFY_TYPE.getKey(), null, null, null);
            }

        } else {
            throw new RiggerException("订单状态非法");
        }

    }

    /**
     * 收款
     *
     * @param vo
     * @param order
     */
    private void saveReceivePayMoney(CsFinancePayMoneyVO vo, CsCarOrder order) {
        if (Constants.YES.equals(vo.getReceiveItemType()) && !OrderStatusEnum.PAID.getKey().equals(order.getStatus())) {
            order.setStatus(OrderStatusEnum.PAID.getKey());
            carOrderService.updateById(order);
            carInfoService.completeAndPutOffCar(order.getCsCarInfoId(), Boolean.FALSE);
        }
    }

    /**
     * 打款卖家
     *
     * @param order
     * @param paymentType
     * @param vo
     * @param csFinancePayAction
     */
    private void saveSalerPayMoney(CsCarOrder order, Integer paymentType, CsFinancePayMoneyVO vo, CsFinancePayAction csFinancePayAction) {
        //1：先打款后过户 22 ，0：先过户后打款 19
        CsCarOrder update = new CsCarOrder();
        update.setId(order.getId());
        if (Constants.YES.equals(paymentType) && OrderStatusEnum.PAID.getKey().equals(order.getStatus())) {
            //1：先打款后过户 买家支付 待打款
            update.setStatus(OrderStatusEnum.PAYMENT.getKey());
            carOrderService.updateById(update);
            carInfoService.completeAndPutOffCar(order.getCsCarInfoId(), Boolean.FALSE);
            csFinancePayAction.setGmtModified(DateUtil.date());
            csFinancePayAction.setPaymentStatus(PaymentStatusEnum.HAS_PAYMENT.getKey());
            csFinancePayAction.setPaymentTime(DateUtil.date());
            csFinancePayActionMapper.updateById(csFinancePayAction);
        } else if (Constants.NO.equals(paymentType) && OrderStatusEnum.TRANSFERRED.getKey().equals(order.getStatus())) {
            //0：先过户后打款 19
            update.setStatus(OrderStatusEnum.COMPLETE.getKey());
            update.setFinishTime(DateUtil.date());
            carOrderService.updateById(update);
            carInfoService.completeAndPutOffCar(order.getCsCarInfoId(), Boolean.FALSE);
            csFinancePayAction.setGmtModified(DateUtil.date());
            csFinancePayAction.setPaymentStatus(PaymentStatusEnum.HAS_PAYMENT.getKey());
            csFinancePayAction.setPaymentTime(DateUtil.date());
            csFinancePayActionMapper.updateById(csFinancePayAction);

            //买家冻结金额
            Long buyerUserId = order.getBuyerUserId();
            //车商冻结金额
            Long carDealerUserId = order.getCarDealerUserId();
            //订单完成 解除冻结保证金 生成解冻保证金账户记录
            accountDetailService.buildingOrder(carDealerUserId, order.getId(), null, order.getCarDealerFrozenMoney(), "", TradeTypeEnum.DRAW_BACK_TYPE.getKey(), null, "", null);
            accountDetailService.buildingOrder(buyerUserId, order.getId(), null, order.getBuyerFrozenMoney(), "", TradeTypeEnum.DRAW_BACK_TYPE.getKey(), null, "", null);

            Result<String> buyerResult = systemServiceClient.unFreezeByUserId(order.getBuyerUserId(), order.getBuyerFrozenMoney().toString());
            if (!buyerResult.isSuccess()) {
                throw new RiggerException(buyerResult.getMessage());
            }
            Result<String> dealerResult = systemServiceClient.unFreezeByUserId(order.getCarDealerUserId(), order.getCarDealerFrozenMoney().toString());
            if (!dealerResult.isSuccess()) {
                throw new RiggerException(dealerResult.getMessage());
            }
            //Result<String> r = systemServiceClient.addBalanceToDealer(order.getBuyerUserId(), order.getBuyerFrozenMoney().toString(), carDealerUserId, order.getCarDealerFrozenMoney().toString());

        } else {
            throw new RiggerException("订单状态非法");
        }


    }

    /**
     * 3.	出现争议时，才会把车款+服务费、赔偿金打给买家；正常情况时最后把车款支付给卖家。
     * 4.	如果待打款项为车款时，跳转到车源审核详情-财务信息-打款卖家；反之跳转到车源审核详情-财务信息-打款买家。
     * 1.   如果收款项为“车款+服务费”，且买卖交易状态为卖出还未买家付款，此时把该买卖交易状态改为“买家支付”，对应的买家的账单新增一条“车款+服务费”账单。
     */
    @Override
    public Page selectFinanceByPage(Query query) {
        List<CsFinancePageDTO> list = csFinancePayActionMapper.selectFinanceByPage(query, query.getCondition());
        list.forEach(e -> {
            //新的打款中间表
            if (PayTypeEnum.ZEOR_TYPE.getKey().equals(e.getPayType())) {
                e.setPayMethodType(Constants.TWO);
            } else {
                e.setPayMethodType(Constants.YES);
            }
            e.setFinancePayMoney(e.getPayMoney());
            e.setPaymentTypeStr(PaymentTypeEnum.getNameByKey(e.getPaymentType()));
            e.setDisputeProcessMoneyStr(PayTypeEnum.getNameByKey(e.getPayType()));
            e.setDisputeProcessTypeStr(DisputeProcessTypeEnum.getNameByKey(e.getDisputeProcessType()));
            e.setStatusStr(OrderStatusEnum.getNameByKey(e.getStatus()));

        });
        return query.setRecords(list);
    }

    @Override
    public CsFinancePayMoneyDTO selectPayInfoById(Long id) {
        CsFinancePayAction csFinancePayAction = csFinancePayActionMapper.selectById(id);
        if (Objects.isNull(csFinancePayAction)) {
            return new CsFinancePayMoneyDTO();
        }
        CsFinancePayMoneyDTO dto = BeanUtils.beanCopy(csFinancePayAction, CsFinancePayMoneyDTO.class);
        dto.setPayMoney(csFinancePayAction.getPayMoney());
        if (PayTypeEnum.ZEOR_TYPE.getKey().equals(csFinancePayAction.getPayType())) {
            dto.setType(OrderFileTypeEnum.SELLER_TYPE.getKey());
            dto.setPayMoneyType(PayMoneyTypeEnum.PAY_SALER_TYPE.getKey());
        } else {
            dto.setType(OrderFileTypeEnum.BUYER_TYPE.getKey());
            dto.setPayMoneyType(PayMoneyTypeEnum.PAY_BUYER_TYPE.getKey());
        }
        if (PaymentStatusEnum.HAS_PAYMENT.getKey().equals(csFinancePayAction.getPaymentStatus())) {
            List<CsFinancePayMoney> list = this.baseMapper.selectList(new EntityWrapper<CsFinancePayMoney>()
                    .eq("pay_money_type", dto.getType())
                    .eq("cs_car_order_id", csFinancePayAction.getCsCarOrderId()).orderBy("id", false));
            if (CollectionUtil.isNotEmpty(list)) {
                CsFinancePayMoney csFinancePayMoney = list.get(0);
                dto.setPayDate(csFinancePayMoney.getPayDate());
                dto.setRemark(csFinancePayMoney.getRemark());
            }
        }
        return dto;
    }



}
