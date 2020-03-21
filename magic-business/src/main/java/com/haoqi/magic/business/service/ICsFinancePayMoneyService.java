package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsFinancePayMoneyDTO;
import com.haoqi.magic.business.model.dto.CsFinancePaySellerQuery;
import com.haoqi.magic.business.model.entity.CsFinancePayMoney;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.vo.CsFinancePayMoneyVO;
import com.haoqi.rigger.mybatis.Query;

/**
 * <p>
 * 财务打款表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
public interface ICsFinancePayMoneyService extends IService<CsFinancePayMoney> {
    /**
     * 分页获取财务打款记录
     *
     * @param query
     * @return
     */
    Page selectByPayPage(Query query);

    /**
     * 保存财务打款信息
     *
     * @param vo
     * @return
     */
    Boolean add(CsFinancePayMoneyVO vo);

    /**
     * @param query
     * @return
     */
    Page selectFinanceByPage(Query query);

    /**
     * 获取买家争议银行卡信息
     *
     * @param id
     * @return
     */
    CsFinancePayMoneyDTO selectPayInfoById(Long id);
}
