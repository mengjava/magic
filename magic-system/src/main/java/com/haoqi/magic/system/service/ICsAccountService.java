package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.CsAccountDTO;
import com.haoqi.magic.system.model.entity.CsAccount;

import java.math.BigDecimal;

/**
 * <p>
 * 账户表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
public interface ICsAccountService extends IService<CsAccount> {

    /**
     * 通过用户ID，获取账户信息
     *
     * @param userId
     * @return
     */
    CsAccount getByUserId(Long userId);

    /**
     * 是否有可用金额
     *
     * @param userId
     * @param money
     * @return
     */
    BigDecimal enableBalance(Long userId, BigDecimal money);

    /**
     * 可冻结金额
     *
     * @param userId
     * @param type   保证金买入还是卖出
     * @return
     */
    BigDecimal enableFrozenAmount(Long userId, Integer type);

    /**
     * 账户余额加操作
     *
     * @param userId
     * @param balance
     * @param money
     */
    void addBalance(Long userId, BigDecimal balance, BigDecimal money);

    /**
     * 通过用户id、金额，给账户余额加操作
     *
     * @param userId
     * @param money
     */
    void addBalance(Long userId, BigDecimal money);

    /**
     * 买方账户冻结金额操作
     *
     * @param buyerId
     * @param buyerFrozenMoney
     * @param dealerId
     * @param dealerFrozenMoney
     */
    void addBalanceToBuyer(Long buyerId, BigDecimal buyerFrozenMoney, Long dealerId, BigDecimal dealerFrozenMoney);


    /**
     * 卖方账户冻结金额操作
     *
     * @param buyerId
     * @param buyerFrozenMoney
     * @param dealerId
     * @param dealerFrozenMoney
     */
    void addBalanceToDealer(Long buyerId, BigDecimal buyerFrozenMoney, Long dealerId, BigDecimal dealerFrozenMoney);

    /**
     * 通过用户id、金额，给账户余额减操作
     *
     * @param userId
     * @param money
     */
    void subBalance(Long userId, BigDecimal money);

    /**
     * 通过用户id、金额，给账户余额减，加冻结金额操作
     *
     * @param userId
     * @param money
     */
    void subBalanceAddFrozenAmount(Long userId, BigDecimal money);

    /**
     * 账户余额减操作
     *
     * @param userId
     * @param balance
     * @param money
     */
    void subBalance(Long userId, BigDecimal balance, BigDecimal money);

    /**
     * 通过id，获取账户、用户信息
     *
     * @param id
     * @return
     */
    CsAccountDTO getAccountWithUserById(Long id);

    /**
     * 通过用户id，获取账户、用户信息
     *
     * @param userId
     * @return
     */
    CsAccountDTO getAccountByUserId(Long userId);

    /**
     * 解冻保证金
     *
     * @param userId 用户id
     * @param money  解冻保证金
     */
    void unFreezeByUserId(Long userId, BigDecimal money);

    /**
     * 解冻保证金
     *
     * @param buyerId
     * @param buyerFrozenMoney
     * @param dealerId
     * @param dealerFrozenMoney
     */
    void unFreezeByUserIdAndDealerId(Long buyerId, BigDecimal buyerFrozenMoney, Long dealerId, BigDecimal dealerFrozenMoney);

}
