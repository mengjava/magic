package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.entity.CsCash;
import com.haoqi.rigger.mybatis.Query;

import java.math.BigDecimal;

/**
 * <p>
 * 提现管理表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-23
 */
public interface ICsCashService extends IService<CsCash> {

    /**
     * 功能描述: 提现审核列表
     *
     * @param query
     * @return com.baomidou.mybatisplus.plugins.Page
     * @auther mengyao
     * @date 2019/12/23 0023 下午 4:24
     */
    Page findByPage(Query query);

    /**
     * 提现申请
     *
     * @param userId
     * @param username 提现用户名
     * @param bankId   提现的银行卡
     * @param money
     * @param device
     * @param source   来源
     */
    void applyCash(Long userId, String username, Long bankId, BigDecimal money, String device, Integer source);


    /**
     * 提现记录列表
     *
     * @param query
     * @return
     */
    Page applyCashByPage(Query query);

    /**
     * 申请提现的金额
     *
     * @param userId
     * @return
     */
    BigDecimal applyCashAmount(Long userId);

    /**
     * 功能描述: 提现审核
     *
     * @param csCash
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/12/30 0030 上午 9:27
     */
    Boolean audit(CsCash csCash);

    /**
     * 合计
     *
     * @param query
     * @return
     */
    BigDecimal totalAmount(Query query);
}
