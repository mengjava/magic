package com.haoqi.magic.system.controller;


import cn.hutool.core.util.NumberUtil;
import com.haoqi.magic.common.vo.CsAccountVO;
import com.haoqi.magic.system.model.dto.CsAccountDTO;
import com.haoqi.magic.system.service.ICsAccountService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * <p>
 * 账户表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@RestController
@RequestMapping("/csAccount")
public class CsAccountController extends BaseController {

    @Autowired
    private ICsAccountService accountService;



    /**
     * 支付时要获取可用金额
     *
     * @return
     */
    @GetMapping("enableBalance/{money}")
    public Result<BigDecimal> enableBalance(@PathVariable("money") String money) {
        UserInfo userInfo = currentUser();
        return Result.buildSuccessResult(accountService.enableBalance(userInfo.getId(), NumberUtil.toBigDecimal(money)));
    }


    /**
     * 支付时要获取可冻结金额
     *
     * @return
     */
    @GetMapping("enableFrozenAmount/{type}")
    public Result<BigDecimal> enableFrozenAmount(@PathVariable("type") Integer type) {
        UserInfo userInfo = currentUser();
        return Result.buildSuccessResult(accountService.enableFrozenAmount(userInfo.getId(), type));
    }

    /**
     * 对账户余额处理
     *
     * @param balance 余额
     * @param money   需要充值的金额
     * @return
     */
    @PostMapping("addBalance/{balance}/{money}")
    public Result<String> addBalance(@PathVariable("balance") String balance, @PathVariable("money") String money) {
        UserInfo user = currentUser();
        accountService.addBalance(user.getId(), NumberUtil.toBigDecimal(balance), NumberUtil.toBigDecimal(money));
        return Result.buildSuccessResult("充值成功");
    }


    /**
     * 对账户余额加处理
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @PostMapping("addBalanceByUserId/{userId}/{money}")
    public Result<String> addBalanceByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money) {
        accountService.addBalance(userId, NumberUtil.toBigDecimal(money));
        return Result.buildSuccessResult("充值成功");
    }

    /**
     * 对账户解冻保证金金额处理
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @PostMapping("unFreezeByUserId/{userId}/{money}")
    public Result<String> unFreezeByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money) {
        accountService.unFreezeByUserId(userId, NumberUtil.toBigDecimal(money));
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 对账户解冻保证金金额处理
     *
     * @param buyerId
     * @param buyerFrozenMoney
     * @param dealerId
     * @param dealerFrozenMoney
     * @return
     */
    @PostMapping("unFreezeByUserIdAndDealerId")
    public Result<String> unFreezeByUserIdAndDealerId(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney,
                                                      @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney) {
        accountService.unFreezeByUserIdAndDealerId(buyerId, NumberUtil.toBigDecimal(buyerFrozenMoney), dealerId, NumberUtil.toBigDecimal(dealerFrozenMoney));
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 对买方账户余额加处理
     *
     * @param buyerId           买方
     * @param buyerFrozenMoney  买方冻结金额
     * @param dealerId          卖方
     * @param dealerFrozenMoney 卖方冻结金额
     * @return
     */
    @PostMapping("addBalanceToBuyer")
    public Result<String> addBalanceToBuyer(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney,
                                            @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney) {
        accountService.addBalanceToBuyer(buyerId, NumberUtil.toBigDecimal(buyerFrozenMoney), dealerId, NumberUtil.toBigDecimal(dealerFrozenMoney));
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 对卖方账户余额加处理
     *
     * @param buyerId           买方
     * @param buyerFrozenMoney  买方冻结金额
     * @param dealerId          卖方
     * @param dealerFrozenMoney 卖方冻结金额
     * @return
     */
    @PostMapping("addBalanceToDealer")
    public Result<String> addBalanceToDealer(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney,
                                             @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney) {
        accountService.addBalanceToDealer(buyerId, NumberUtil.toBigDecimal(buyerFrozenMoney), dealerId, NumberUtil.toBigDecimal(dealerFrozenMoney));
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 对账户余额减处理
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @PostMapping("subBalanceByUserId/{userId}/{money}")
    public Result<String> subBalanceByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money) {
        accountService.subBalance(userId, NumberUtil.toBigDecimal(money));
        return Result.buildSuccessResult("扣款成功");
    }


    /**
     * 对账户余额减、加冻结金额处理
     *
     * @param userId
     * @param money
     * @return
     */
    @PostMapping("subBalanceAddFrozenAmount/{userId}/{money}")
    public Result<String> subBalanceAddFrozenAmount(@PathVariable("userId") Long userId, @PathVariable("money") String money) {
        accountService.subBalanceAddFrozenAmount(userId, NumberUtil.toBigDecimal(money));
        return Result.buildSuccessResult("扣款成功");
    }

    /**
     * 对账户余额处理
     *
     * @param balance 余额
     * @param money   需要扣款的金额
     * @return
     */
    @PostMapping("subBalance/{balance}/{money}")
    public Result<String> subBalance(@PathVariable("balance") String balance, @PathVariable("money") String money) {
        UserInfo user = currentUser();
        accountService.subBalance(user.getId(), NumberUtil.toBigDecimal(balance), NumberUtil.toBigDecimal(money));
        return Result.buildSuccessResult("扣款成功");
    }

    /**
     * 通过账户id，获取账户、用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("getAccountWithUser/{id}")
    public Result<CsAccountVO> getAccountWithUser(@PathVariable("id") Long id) {
        currentUser();
        CsAccountDTO accountDTO = accountService.getAccountWithUserById(id);
        return Result.buildSuccessResult(BeanUtils.beanCopy(accountDTO, CsAccountVO.class));
    }


    /**
     * 通过用户id，获取账户、用户信息
     *
     * @return
     */
    @GetMapping("getAccountByUserId")
    public Result<CsAccountVO> getAccountByUserId() {
        UserInfo userInfo = currentUser();
        CsAccountDTO accountDTO = accountService.getAccountByUserId(userInfo.getId());
        return Result.buildSuccessResult(BeanUtils.beanCopy(accountDTO, CsAccountVO.class));
    }

}

