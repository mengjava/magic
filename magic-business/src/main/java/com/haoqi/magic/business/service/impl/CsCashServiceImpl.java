package com.haoqi.magic.business.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.AuditStatusEnum;
import com.haoqi.magic.business.enums.OrderAuditStatusEnum;
import com.haoqi.magic.business.enums.TradeTypeEnum;
import com.haoqi.magic.business.mapper.CsCashMapper;
import com.haoqi.magic.business.model.dto.PaymentOrderDTO;
import com.haoqi.magic.business.model.dto.SysConfigDTO;
import com.haoqi.magic.business.model.entity.CsAccountDetail;
import com.haoqi.magic.business.model.entity.CsCash;
import com.haoqi.magic.business.model.entity.CsUserBankCard;
import com.haoqi.magic.business.model.vo.CsCashVO;
import com.haoqi.magic.business.service.ICsAccountDetailService;
import com.haoqi.magic.business.service.ICsCashService;
import com.haoqi.magic.business.service.ICsUserBankCardService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 提现管理表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-23
 */
@Slf4j
@Service
public class CsCashServiceImpl extends ServiceImpl<CsCashMapper, CsCash> implements ICsCashService {

    @Autowired
    private SystemServiceClient systemServiceClient;
    @Autowired
    private ICsUserBankCardService userBankCardService;
    @Autowired
    private ICsAccountDetailService accountDetailService;


    @Override
    public Page findByPage(Query query) {
        List<CsCashVO> csCashVO = super.baseMapper.selectByPage(query, query.getCondition());
        return query.setRecords(csCashVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void applyCash(Long userId, String username, Long bankId, BigDecimal money, String device, Integer source) {
        //提现金额
        Result<SysConfigDTO> result20 = systemServiceClient.getByTypeAndName(2, 20);
        if (StrUtil.isBlank(result20.getData().getGlobalValue())) {
            throw new RiggerException("提现金额设置不正确");
        }
        if (money.compareTo(NumberUtil.toBigDecimal(result20.getData().getGlobalValue())) < 0) {
            throw new RiggerException("提现金额必须大于等于 " + result20.getData().getGlobalValue() + " 元");
        }
        CsUserBankCard bankCard = userBankCardService.selectOne(new EntityWrapper<CsUserBankCard>()
                .eq("id", bankId)
                .eq("sys_user_id", userId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        Optional.ofNullable(bankCard).orElseThrow(() -> new RiggerException("该银行卡不存在，请先绑卡"));
        //提现模式
        Result<SysConfigDTO> result21 = systemServiceClient.getByTypeAndName(2, 21);
        if (!result21.isSuccess()) {
            throw new RiggerException("提现模式设置不正确");
        }
        CsCash cash = new CsCash();
        cash.setMoney(money);
        cash.setCashUserId(userId);
        cash.setCashUser(username);
        cash.setBankName(bankCard.getBankName());
        cash.setBankCardNo(bankCard.getBankCardNo());
        cash.setBankUserTel(bankCard.getBankUserTel());
        cash.setBankUserName(bankCard.getBankUserName());
        cash.setBankUserCardNo(bankCard.getBankUserCardNo());
        PaymentOrderDTO paymentOrder = accountDetailService.buildingOrder(userId, money, TradeTypeEnum.CASH_TYPE.getKey(), device, source);
        cash.setSerialNo(paymentOrder.getSerialNo());
        this.insert(cash);
        if (CommonConstant.STATUS_DEL.equals(Integer.parseInt(result21.getData().getGlobalValue()))) {
            CsCash updateCash = new CsCash();
            updateCash.setId(cash.getId());
            updateCash.setCashAuditStatus(AuditStatusEnum.PASS.getKey());
            updateById(updateCash);
            Result<String> result = systemServiceClient.subBalanceByUserId(userId, money.toString());
            if (!result.isSuccess()) {
                log.error("提现申请失败，用户id：{}，金额：{}，返回结果：{}", userId, money, result.getMessage());
                throw new RiggerException(result.getMessage());
            }
        }
    }

    @Override
    public Page applyCashByPage(Query query) {
        return query.setRecords(super.baseMapper.selectByPage(query, query.getCondition()));
    }

    @Override
    public BigDecimal applyCashAmount(Long userId) {
        Map param = Maps.newHashMap();
        param.put("userId", userId);
        param.put("cashAuditStatus", OrderAuditStatusEnum.SUBMITTED.getKey());
        return super.baseMapper.totalAmount(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean audit(CsCash csCash) {
        this.updateById(csCash);
        // 审核通过 修改账单状态 账户扣费
        CsCash entity = this.selectById(csCash.getId());
        CsAccountDetail csAccountDetail = new CsAccountDetail();
        csAccountDetail.setStatus(AuditStatusEnum.PASS.getKey().equals(csCash.getCashAuditStatus()) ? CommonConstant.STATUS_DEL : CommonConstant.STATUS_LOCK);
        boolean r = accountDetailService.update(csAccountDetail, new EntityWrapper<CsAccountDetail>()
                .eq("serial_no", entity.getSerialNo())
                //.eq("cash_user_id", entity.getCashUserId())
                .eq("trade_type", TradeTypeEnum.CASH_TYPE.getKey()));
        if (!r) {
            return Boolean.FALSE;
        }
        Result<String> result = systemServiceClient.subBalanceByUserId(entity.getCashUserId(), entity.getMoney().toString());
        if (!result.isSuccess()) {
            throw new RiggerException(result.getMessage());
        }
        return Boolean.TRUE;
    }

    @Override
    public BigDecimal totalAmount(Query query) {
        return super.baseMapper.totalAmount(query.getCondition());
    }
}
