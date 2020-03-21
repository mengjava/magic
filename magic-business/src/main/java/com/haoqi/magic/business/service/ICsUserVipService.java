package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.UserVipDTO;
import com.haoqi.magic.business.model.entity.CsUserVip;

/**
 * <p>
 * 用户会员关联表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
public interface ICsUserVipService extends IService<CsUserVip> {

    /**
     * 通过会员卡ID，绑定会员
     *
     * @param vipId          会员配置id
     * @param userId         用户id
     * @param username       用户名
     */
    void bindingMember(Long vipId, Long userId, String username);

    /**
     * 获取有效的用户会员卡信息
     *
     * @param userId
     * @return
     */
    UserVipDTO getEnableUserVipByUserId(Long userId);

    /**
     * 维保次数减一
     *
     * @param userId
     * @param maintenanceFreeNum 原维保次数
     */
    void subOneMaintenanceFreeNum(Long userId, Integer maintenanceFreeNum);

    /**
     * 排放次数减一
     *
     * @param userId
     * @param emissionFreeNum 原维保次数
     */
    void subOneEmissionFreeNum(Long userId, Integer emissionFreeNum);

    /**
     * 出险次数减一
     *
     * @param userId
     * @param insuranceFreeNum 原维保次数
     */
    void subOneInsuranceFreeNum(Long userId, Integer insuranceFreeNum);


    /**
     * VIN识别次数减一
     *
     * @param userId
     * @param carModelFreeNum 原维保次数
     */
    void subOneCarModelFreeNum(Long userId, Integer carModelFreeNum);


    /**
     * VIN识别次数减一
     *
     * @param userId
     * @param evaluateFreeNum 原维保次数
     */
    void subOneEvaluateFreeNum(Long userId, Integer evaluateFreeNum);

    /***
     * 功能描述: 查询用户是否开通过体验会员
     * @param id
     * @return CsUserVip
     * @auther mengyao
     * @date 2020-02-28 21:47
     */
    CsUserVip selectNoVipByUserId(Long id);
}
