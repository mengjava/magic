package com.haoqi.magic.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.TradeTypeEnum;
import com.haoqi.magic.business.model.dto.UserDTO;
import com.haoqi.magic.business.model.dto.UserVipDTO;
import com.haoqi.magic.business.model.entity.CsVip;
import com.haoqi.magic.business.service.ICsAccountDetailService;
import com.haoqi.magic.business.service.ICsUserVipService;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.magic.common.enums.ErrorsEnum;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.ErrorCodeEnum;
import com.haoqi.rigger.core.error.RiggerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * @author twg
 * @since 2019/12/25
 */
public abstract class BaseServiceImpl {

    @Autowired
    private ICsAccountDetailService accountDetailService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Autowired
    private ICsUserVipService userVipService;

    @Autowired
    private ICsVipService vipService;

    @Transactional(rollbackFor = Exception.class)
    protected void buildPayOrderAndSubBalance(Long userId, Integer tradeType, String device, Integer source) {
        Result<UserDTO> result = systemServiceClient.getUserByUserId(userId);
        if (!result.isSuccess()) {
            throw new RiggerException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "获取用户信息失败");
        }
        UserDTO userDTO = result.getData();
        Integer userType = userDTO.getType();
        if (UserLevelEnum.USER_LEVEL.getLevel().equals(userType) || UserLevelEnum.SELLER_LEVEL.getLevel().equals(userType)) {
            String numText = "";
            BigDecimal amount = BigDecimal.ZERO;
            UserVipDTO userVip = userVipService.getEnableUserVipByUserId(userId);
            if (Objects.isNull(userVip)) {
                amount = getNonMemberAmount(tradeType, amount);
            } else {
                switch (tradeType) {
                    case 1:
                        if (userVip.getMaintenanceFreeNum() > 0) {
                            numText = StrUtil.format("{} +{}次", TradeTypeEnum.MAINTENANCE_TYPE.getName(), 1);
                            userVipService.subOneMaintenanceFreeNum(userId, userVip.getMaintenanceFreeNum());
                        } else {
                            amount = userVip.getMaintenancePrice();
                        }
                        break;
                    case 2:
                        if (userVip.getEmissionFreeNum() > 0) {
                            numText = StrUtil.format("{} +{}次", TradeTypeEnum.EMISSION_TYPE.getName(), 1);
                            userVipService.subOneEmissionFreeNum(userId, userVip.getEmissionFreeNum());
                        } else {
                            amount = userVip.getEmissionPrice();
                        }
                        break;
                    case 3:
                        if (userVip.getInsuranceFreeNum() > 0) {
                            numText = StrUtil.format("{} +{}次", TradeTypeEnum.INSURANCE_TYPE.getName(), 1);
                            userVipService.subOneInsuranceFreeNum(userId, userVip.getInsuranceFreeNum());
                        } else {
                            amount = userVip.getInsurancePrice();
                        }
                        break;
                    case 4:
                        if (userVip.getCarModelFreeNum() > 0) {
                            numText = StrUtil.format("{} +{}次", TradeTypeEnum.CAR_MODEL_TYPE.getName(), 1);
                            userVipService.subOneCarModelFreeNum(userId, userVip.getCarModelFreeNum());
                        } else {
                            amount = userVip.getCarModelPrice();
                        }
                        break;
                    case 5:
                        if (userVip.getEvaluateFreeNum() > 0) {
                            numText = StrUtil.format("{} +{}次", TradeTypeEnum.EVALUATE_TYPE.getName(), 1);
                            userVipService.subOneEvaluateFreeNum(userId, userVip.getEvaluateFreeNum());
                        } else {
                            amount = userVip.getEvaluatePrice();
                        }
                        break;
                    default:
                        break;
                }
            }
            if (userDTO.getFreezeMoney().compareTo(amount) < 0) {
                throw new RiggerException(ErrorsEnum.ERRORS_300001.getKey(), "您账户余额不足" + amount + "元，请先充值！");
            }
            if (!hasPayPassword(userDTO.getPayPassword())) {
                throw new RiggerException(ErrorsEnum.ERRORS_300003.getKey(), ErrorsEnum.ERRORS_300003.getName());
            }
            accountDetailService.buildingOrder(userId, null, null, amount, numText, tradeType, null, device, source);
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                Result<String> out = systemServiceClient.subBalanceByUserId(userId, amount.toString());
                if (!out.isSuccess()) {
                    throw new RiggerException(10000, out.getMessage());
                }
            }
        }
    }

    /**
     * 非会员
     *
     * @param tradeType
     * @param amount
     * @return
     */
    private BigDecimal getNonMemberAmount(Integer tradeType, BigDecimal amount) {
        CsVip vip = vipService.selectOne(new EntityWrapper<CsVip>()
                .eq("type", CommonConstant.STATUS_NORMAL)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        switch (tradeType) {
            case 1:
                amount = vip.getMaintenancePrice();
                break;
            case 2:
                amount = vip.getEmissionPrice();
                break;
            case 3:
                amount = vip.getInsurancePrice();
                break;
            case 4:
                amount = vip.getCarModelPrice();
                break;
            case 5:
                amount = vip.getEvaluatePrice();
                break;
            default:
                break;
        }
        return amount;
    }

    /**
     * 保证金
     *
     * @param type
     * @param message
     * @return
     */
    protected BigDecimal enableFrozenAmount(Integer type, String message) {
        Result<BigDecimal> frozenAmount = systemServiceClient.enableFrozenAmount(type);
        if (!frozenAmount.isSuccess() || frozenAmount.getData().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RiggerException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), message);
        }
        return frozenAmount.getData();
    }

    /**
     * 是否有密码
     *
     * @param payPassword
     * @return
     */
    protected Boolean hasPayPassword(String payPassword) {
        if (StrUtil.isNotEmpty(payPassword)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
