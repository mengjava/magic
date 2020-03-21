package com.haoqi.magic.business.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.VipTypeEnum;
import com.haoqi.magic.business.mapper.CsUserVipMapper;
import com.haoqi.magic.business.model.dto.UserVipDTO;
import com.haoqi.magic.business.model.entity.CsUserVip;
import com.haoqi.magic.business.model.entity.CsVip;
import com.haoqi.magic.business.service.ICsUserVipService;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 用户会员关联表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@Slf4j
@Service
public class CsUserVipServiceImpl extends ServiceImpl<CsUserVipMapper, CsUserVip> implements ICsUserVipService {

    @Autowired
    private ICsUserVipService userVipService;

    @Autowired
    private ICsVipService vipService;

    @Autowired
    private SystemServiceClient systemServiceClient;
    @Autowired
    private CsUserVipMapper csUserVipMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindingMember(Long vipId, Long userId, String username) {
        Assert.notNull(vipId, "会员ID不能为空");
        Assert.notNull(userId, "用户ID不能为空");
        Assert.notBlank(username, "用户名不能为空");
        CsUserVip userVip = userVipService.selectOne(new EntityWrapper<CsUserVip>()
                .eq("sys_user_id", userId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        CsVip vip = vipService.selectOne(new EntityWrapper<CsVip>()
                .eq("id", vipId)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (Objects.isNull(userVip)) {
            /**
             * 1.如果是体验会员就直接绑会员卡
             * 2.如果是充值会员，先生成支付订单，支付完成后再通过支付单号获取支付结果，支付通过才能绑会员卡
             */
            CsUserVip csUserVip = new CsUserVip();
            csUserVip.setCreator(userId);
            csUserVip.setModifier(userId);
            csUserVip.setSysUserId(userId);
            csUserVip.setMoney(vip.getMoney());
            csUserVip.setUsername(username);
            csUserVip.setCsVipId(vipId);
            csUserVip.setBeginDate(DateUtil.date());
            csUserVip.setExpiredDate(DateUtil.offsetDay(DateUtil.date(), vip.getPeriod()));
            csUserVip.setVipNo(StrUtil.format("{}{}", RandomUtil.randomLong(5), DateUtil.currentSeconds()));
            csUserVip.setEvaluateFreeNum(vip.getEvaluateFreeNum());
            csUserVip.setEmissionFreeNum(vip.getEmissionFreeNum());
            csUserVip.setCarModelFreeNum(vip.getCarModelFreeNum());
            csUserVip.setInsuranceFreeNum(vip.getInsuranceFreeNum());
            csUserVip.setMaintenanceFreeNum(vip.getMaintenanceFreeNum());
            csUserVip.setMaintenancePrice(vip.getMaintenancePrice());
            csUserVip.setEmissionPrice(vip.getEmissionPrice());
            csUserVip.setEvaluatePrice(vip.getEvaluatePrice());
            csUserVip.setCarModelPrice(vip.getCarModelPrice());
            csUserVip.setInsurancePrice(vip.getInsurancePrice());
            userVipService.insert(csUserVip);
            Result<String> result = systemServiceClient.updateUserVipFlag(userId, vip.getType());
            if (!result.isSuccess()) {
                throw new RiggerException("更新用户会员标示失败");
            }
            return;
        }
        if (Objects.nonNull(userVip) && VipTypeEnum.EXPERIENCE_VIP.getKey().equals(vip.getType())) {
            throw new RiggerException("体验会员已开通");
        }
        userVip.setCsVipId(vipId);
        userVip.setMoney(vip.getMoney());
        userVip.setExpiredDate(DateUtil.offsetDay(DateUtil.date(), vip.getPeriod()));
        if (userVip.getExpiredDate().before(DateUtil.date())) {
            userVip.setBeginDate(DateUtil.date());
            userVip.setEvaluateFreeNum(vip.getEvaluateFreeNum());
            userVip.setEmissionFreeNum(vip.getEmissionFreeNum());
            userVip.setCarModelFreeNum(vip.getCarModelFreeNum());
            userVip.setInsuranceFreeNum(vip.getInsuranceFreeNum());
            userVip.setMaintenanceFreeNum(vip.getMaintenanceFreeNum());
        } else {
            userVip.setEvaluateFreeNum(userVip.getEvaluateFreeNum() + vip.getEvaluateFreeNum());
            userVip.setEmissionFreeNum(userVip.getEmissionFreeNum() + vip.getEmissionFreeNum());
            userVip.setCarModelFreeNum(userVip.getCarModelFreeNum() + vip.getCarModelFreeNum());
            userVip.setInsuranceFreeNum(userVip.getInsuranceFreeNum() + vip.getInsuranceFreeNum());
            userVip.setMaintenanceFreeNum(userVip.getMaintenanceFreeNum() + vip.getMaintenanceFreeNum());
        }
        userVip.setMaintenancePrice(vip.getMaintenancePrice());
        userVip.setEmissionPrice(vip.getEmissionPrice());
        userVip.setEvaluatePrice(vip.getEvaluatePrice());
        userVip.setCarModelPrice(vip.getCarModelPrice());
        userVip.setInsurancePrice(vip.getInsurancePrice());
        userVipService.updateById(userVip);
        Result<String> result = systemServiceClient.updateUserVipFlag(userId, VipTypeEnum.RECHARGE_VIP.getKey());
        if (!result.isSuccess()) {
            throw new RiggerException("更新用户会员标示失败");
        }

    }


    @Override
    public UserVipDTO getEnableUserVipByUserId(Long userId) {
        return super.baseMapper.getEnableUserVipByUserId(userId);
    }

    @Override
    public void subOneMaintenanceFreeNum(Long userId, Integer maintenanceFreeNum) {
        Assert.notNull(userId, "用户id不能为空");
        Assert.notNull(maintenanceFreeNum, "维保次数不能为空");
        if (maintenanceFreeNum.intValue() < 1) {
            throw new RiggerException("维保次数不正确");
        }
        CsUserVip userVip = new CsUserVip();
        userVip.setMaintenanceFreeNum(maintenanceFreeNum - 1);
        boolean result = this.update(userVip, new EntityWrapper<CsUserVip>()
                .eq("sys_user_id", userId)
                .eq("maintenance_free_num", maintenanceFreeNum)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("扣维保次数失败");
        }
    }

    @Override
    public void subOneEmissionFreeNum(Long userId, Integer emissionFreeNum) {
        Assert.notNull(userId, "用户id不能为空");
        Assert.notNull(emissionFreeNum, "排放次数不能为空");
        if (emissionFreeNum.intValue() < 1) {
            throw new RiggerException("排放次数不正确");
        }
        CsUserVip userVip = new CsUserVip();
        userVip.setEmissionFreeNum(emissionFreeNum - 1);
        boolean result = this.update(userVip, new EntityWrapper<CsUserVip>()
                .eq("sys_user_id", userId)
                .eq("emission_free_num", emissionFreeNum)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("扣排放次数失败");
        }
    }

    @Override
    public void subOneInsuranceFreeNum(Long userId, Integer insuranceFreeNum) {
        Assert.notNull(userId, "用户id不能为空");
        Assert.notNull(insuranceFreeNum, "出险次数不能为空");
        if (insuranceFreeNum.intValue() < 1) {
            throw new RiggerException("出险次数不正确");
        }
        CsUserVip userVip = new CsUserVip();
        userVip.setInsuranceFreeNum(insuranceFreeNum - 1);
        boolean result = this.update(userVip, new EntityWrapper<CsUserVip>()
                .eq("sys_user_id", userId)
                .eq("insurance_free_num", insuranceFreeNum)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("扣出险次数失败");
        }
    }

    @Override
    public void subOneCarModelFreeNum(Long userId, Integer carModelFreeNum) {
        Assert.notNull(userId, "用户id不能为空");
        Assert.notNull(carModelFreeNum, "vin识别次数不能为空");
        if (carModelFreeNum.intValue() < 1) {
            throw new RiggerException("vin识别次数不正确");
        }
        CsUserVip userVip = new CsUserVip();
        userVip.setCarModelFreeNum(carModelFreeNum - 1);
        boolean result = this.update(userVip, new EntityWrapper<CsUserVip>()
                .eq("sys_user_id", userId)
                .eq("car_model_free_num", carModelFreeNum)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("扣vin识别次数失败");
        }
    }

    @Override
    public void subOneEvaluateFreeNum(Long userId, Integer evaluateFreeNum) {
        Assert.notNull(userId, "用户id不能为空");
        Assert.notNull(evaluateFreeNum, "评估次数不能为空");
        if (evaluateFreeNum.intValue() < 1) {
            throw new RiggerException("评估次数不正确");
        }
        CsUserVip userVip = new CsUserVip();
        userVip.setEvaluateFreeNum(evaluateFreeNum - 1);
        boolean result = this.update(userVip, new EntityWrapper<CsUserVip>()
                .eq("sys_user_id", userId)
                .eq("evaluate_free_num", evaluateFreeNum)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        if (!result) {
            throw new RiggerException("扣评估次数失败");
        }
    }

    @Override
    public CsUserVip selectNoVipByUserId(Long id) {
        CsUserVip csUserVip  = csUserVipMapper.selectNoVipByUserId(id);
        return csUserVip;
    }

}
