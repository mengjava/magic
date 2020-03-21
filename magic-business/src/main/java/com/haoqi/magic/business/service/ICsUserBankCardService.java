package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsUserBankCardDTO;
import com.haoqi.magic.business.model.entity.CsUserBankCard;

/**
 * <p>
 * 银行卡表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-16
 */
public interface ICsUserBankCardService extends IService<CsUserBankCard> {

    /**
     * 功能描述: 银行卡保存
     *
     * @param dto
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/12/16 0016 下午 3:30
     */
    Boolean add(CsUserBankCardDTO dto);

    /**
     * 功能描述: 银行卡修改
     *
     * @param dto
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/12/16 0016 下午 3:36
     */
    Boolean edit(CsUserBankCardDTO dto);


    /**
     * 根据用户id获取用户信息
     *
     * @param userId
     * @return
     */
    CsUserBankCardDTO getBankCardByUserId(Long userId);

    /**
     * 是否绑定银行卡
     *
     * @param userId
     * @return
     */
    Boolean binding(Long userId);

}
