package com.haoqi.magic.system.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.system.mapper.CsAccountMapper;
import com.haoqi.magic.system.model.dto.CsAccountDTO;
import com.haoqi.magic.system.model.entity.CsAccount;
import com.haoqi.magic.system.model.entity.SysConfig;
import com.haoqi.magic.system.model.enums.ConfigTypeEnum;
import com.haoqi.magic.system.service.ICsAccountService;
import com.haoqi.magic.system.service.ISysConfigService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.error.RiggerException;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@Slf4j
@Service
public class CsAccountServiceImpl extends ServiceImpl<CsAccountMapper, CsAccount> implements ICsAccountService {
    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;


    @Override
    public CsAccount getByUserId(Long userId) {
        CsAccount account = super.selectOne(new EntityWrapper<CsAccount>()
                .eq("sys_user_id", userId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        return Optional.ofNullable(account).orElseThrow(() -> new RiggerException("账户不存在"));
    }

    @Override
    public BigDecimal enableBalance(Long userId, BigDecimal money) {
        CsAccount account = getByUserId(userId);
        BigDecimal balance = Objects.isNull(account.getBalanceMoney()) ? BigDecimal.ZERO : account.getBalanceMoney();
        if (balance.compareTo(money) < 0) {
            throw new RiggerException("账户可用余额不足，请充值");
        }
        return balance;
    }

    @Override
    public BigDecimal enableFrozenAmount(Long userId, Integer type) {
        SysConfig config = configService.getByTypeAndName(ConfigTypeEnum.SECURITY_DEPOSIT.getKey(), type);
        return NumberUtil.toBigDecimal(config.getGlobalValue());
    }

    @Override
    public void addBalance(Long userId, BigDecimal balance, BigDecimal money) {
        CsAccount account = new CsAccount();
        account.setBalanceMoney(balance.add(money));
        Boolean result = super.update(account, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", userId)
                .eq("balance_money", balance)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("账户余额充值失败");
        }
    }

    @Override
    public void addBalance(Long userId, BigDecimal money) {
        CsAccount account = this.getByUserId(userId);
        BigDecimal balance = account.getBalanceMoney();
        addBalance(userId, balance, money);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBalanceToBuyer(Long buyerId, BigDecimal buyerFrozenMoney, Long dealerId, BigDecimal dealerFrozenMoney) {
        CsAccount buyerAccount = this.getByUserId(buyerId);
        CsAccount dealerAccount = this.getByUserId(dealerId);
        BigDecimal buyerBalanceMoney = buyerAccount.getBalanceMoney();
        BigDecimal dealerBalanceMoney = dealerAccount.getBalanceMoney();
        BigDecimal buyerFMoney = buyerAccount.getFreezeMoney();
        BigDecimal dealerFMoney = dealerAccount.getFreezeMoney();

        if (buyerFMoney.compareTo(buyerFrozenMoney) < 0) {
            throw new RiggerException("冻结金额有误");
        }
        CsAccount bAccount = new CsAccount();
        bAccount.setBalanceMoney(buyerBalanceMoney.add(buyerFrozenMoney).add(dealerFrozenMoney));
        bAccount.setFreezeMoney(buyerFMoney.subtract(buyerFrozenMoney));
        Boolean buyerResult = super.update(bAccount, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", buyerId)
                .eq("freeze_money", buyerFMoney)
                .eq("balance_money", buyerBalanceMoney)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));

        CsAccount dAccount = new CsAccount();
        dAccount.setFreezeMoney(dealerFMoney.subtract(dealerFrozenMoney));
        Boolean dealerResult = super.update(dAccount, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", dealerId)
                .eq("freeze_money", dealerFMoney)
                .eq("balance_money", dealerBalanceMoney)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));

        if (!buyerResult || !dealerResult) {
            throw new RiggerException("账户余额扣款失败");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addBalanceToDealer(Long buyerId, BigDecimal buyerFrozenMoney, Long dealerId, BigDecimal dealerFrozenMoney) {
        CsAccount buyerAccount = this.getByUserId(buyerId);
        CsAccount dealerAccount = this.getByUserId(dealerId);
        BigDecimal buyerBalanceMoney = buyerAccount.getBalanceMoney();
        BigDecimal dealerBalanceMoney = dealerAccount.getBalanceMoney();
        BigDecimal buyerFMoney = buyerAccount.getFreezeMoney();
        BigDecimal dealerFMoney = dealerAccount.getFreezeMoney();

        if (dealerFMoney.compareTo(dealerFrozenMoney) < 0) {
            throw new RiggerException("冻结金额有误");
        }
        CsAccount bAccount = new CsAccount();
        bAccount.setFreezeMoney(buyerFMoney.subtract(buyerFrozenMoney));
        Boolean buyerResult = super.update(bAccount, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", buyerId)
                .eq("freeze_money", buyerFMoney)
                .eq("balance_money", buyerBalanceMoney)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));

        CsAccount dAccount = new CsAccount();
        dAccount.setBalanceMoney(dealerBalanceMoney.add(buyerFrozenMoney).add(dealerFrozenMoney));
        dAccount.setFreezeMoney(dealerFMoney.subtract(dealerFrozenMoney));
        Boolean dealerResult = super.update(dAccount, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", dealerId)
                .eq("freeze_money", dealerFMoney)
                .eq("balance_money", dealerBalanceMoney)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));

        if (!buyerResult || !dealerResult) {
            throw new RiggerException("账户余额扣款失败");
        }

    }

    @Override
    public void subBalance(Long userId, BigDecimal money) {
        CsAccount account = this.getByUserId(userId);
        BigDecimal balance = account.getBalanceMoney();
        if (balance.compareTo(money) < 0) {
            throw new RiggerException("账户可用余额不足，请充值");
        }
        RLock lock = redissonClient.getLock(prefix + ":lock:" + userId);
        try {
            lock.tryLock(1, 5, TimeUnit.SECONDS);
            subBalance(userId, balance, money);
        } catch (Exception e) {
            throw new RiggerException(e.getMessage());
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void subBalanceAddFrozenAmount(Long userId, BigDecimal money) {
        CsAccount account = this.getByUserId(userId);
        BigDecimal balance = account.getBalanceMoney();
        BigDecimal freezeMoney = account.getFreezeMoney();
        if (balance.compareTo(money) < 0) {
            throw new RiggerException("账户可用余额不足，请充值");
        }
        CsAccount csAccount = new CsAccount();
        csAccount.setBalanceMoney(balance.subtract(money));
        csAccount.setFreezeMoney(freezeMoney.add(money));
        Boolean result = super.update(csAccount, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", userId)
                .eq("balance_money", balance)
                .eq("freeze_money", freezeMoney)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("账户余额扣款失败");
        }
    }

    @Override
    public void subBalance(Long userId, BigDecimal balance, BigDecimal money) {
        CsAccount account = new CsAccount();
        account.setBalanceMoney(balance.subtract(money));
        Boolean result = super.update(account, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", userId)
                .eq("balance_money", balance)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("账户余额扣款失败");
        }
    }

    @Override
    public CsAccountDTO getAccountWithUserById(Long id) {
        return super.baseMapper.getAccountWithUserById(id);
    }

    @Override
    public CsAccountDTO getAccountByUserId(Long userId) {
        return super.baseMapper.getAccountByUserId(userId);
    }

    @Override
    public void unFreezeByUserId(Long userId, BigDecimal money) {
        CsAccount account = this.getByUserId(userId);
        BigDecimal balance = account.getBalanceMoney();
        BigDecimal freezeMoney = account.getFreezeMoney();
        CsAccount csAccount = new CsAccount();
        csAccount.setBalanceMoney(account.getBalanceMoney().add(money));
        csAccount.setFreezeMoney(account.getFreezeMoney().subtract(money));
        Boolean result = super.update(csAccount, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", userId)
                .eq("balance_money", balance)
                .eq("freeze_money", freezeMoney)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("账户余额扣款失败");
        }
    }

    @Override
    public void unFreezeByUserIdAndDealerId(Long buyerId, BigDecimal buyerFrozenMoney, Long dealerId, BigDecimal dealerFrozenMoney) {
        CsAccount buyerAccount = this.getByUserId(buyerId);
        CsAccount dealerAccount = this.getByUserId(dealerId);
        BigDecimal buyerBalanceMoney = buyerAccount.getBalanceMoney();
        BigDecimal dealerBalanceMoney = dealerAccount.getBalanceMoney();
        BigDecimal buyerFMoney = buyerAccount.getFreezeMoney();
        BigDecimal dealerFMoney = dealerAccount.getFreezeMoney();

        CsAccount bAccount = new CsAccount();
        bAccount.setBalanceMoney(buyerBalanceMoney.add(buyerFrozenMoney));
        bAccount.setFreezeMoney(buyerFMoney.subtract(buyerFrozenMoney));
        Boolean buyerResult = super.update(bAccount, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", buyerId)
                .eq("freeze_money", buyerFMoney)
                .eq("balance_money", buyerBalanceMoney)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));

        CsAccount dAccount = new CsAccount();
        dAccount.setBalanceMoney(dealerBalanceMoney.add(dealerFrozenMoney));
        dAccount.setFreezeMoney(dealerFMoney.subtract(dealerFrozenMoney));
        Boolean dealerResult = super.update(dAccount, new EntityWrapper<CsAccount>()
                .eq("sys_user_id", dealerId)
                .eq("freeze_money", dealerFMoney)
                .eq("balance_money", dealerBalanceMoney)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));

        if (!buyerResult || !dealerResult) {
            throw new RiggerException("账户余额扣款失败");
        }
    }
}
