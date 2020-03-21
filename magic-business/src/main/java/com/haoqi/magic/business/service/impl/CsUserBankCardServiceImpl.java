package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.mapper.CsUserBankCardMapper;
import com.haoqi.magic.business.model.dto.CsUserBankCardDTO;
import com.haoqi.magic.business.model.entity.CsUserBankCard;
import com.haoqi.magic.business.service.ICsUserBankCardService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.error.RiggerException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 银行卡表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-16
 */
@Service
public class CsUserBankCardServiceImpl extends ServiceImpl<CsUserBankCardMapper, CsUserBankCard> implements ICsUserBankCardService {

    @Override
    public Boolean add(CsUserBankCardDTO dto) {
        // 1 查询添加信息,判断是否已经存在
        Integer count = this.selectCount(new EntityWrapper<CsUserBankCard>().eq("bank_card_no", dto.getBankCardNo()));
        // 2 如果已经存在,返回
        if (count > CommonConstant.STATUS_NORMAL) {
            throw new RiggerException("此银行卡已存在");
        }
        return this.insert(BeanUtils.beanCopy(dto, CsUserBankCard.class));
    }

    @Override
    public Boolean edit(CsUserBankCardDTO dto) {
        this.updateById(BeanUtils.beanCopy(dto, CsUserBankCard.class));
        // 1 查询添加信息,判断是否已经存在
        Integer count = this.selectCount(new EntityWrapper<CsUserBankCard>().eq("bank_card_no", dto.getBankCardNo()));
        // 2 如果已经存在,返回
        if (count > CommonConstant.BUTTON.intValue()) {
            throw new RiggerException("此银行卡已存在");
        }
        return true;
    }

    @Override
    public CsUserBankCardDTO getBankCardByUserId(Long userId) {
        List<CsUserBankCard> list = this.selectList(new EntityWrapper<CsUserBankCard>().
                eq("sys_user_id", userId).
                eq("is_deleted", Constants.NO));
        if (CollectionUtil.isEmpty(list)) {
            throw new RiggerException("用户暂无银行卡");
        }
        return BeanUtils.beanCopy(list.get(0), CsUserBankCardDTO.class);
    }

    @Override
    public Boolean binding(Long userId) {
        List<CsUserBankCard> list = this.selectList(new EntityWrapper<CsUserBankCard>().
                eq("sys_user_id", userId).
                eq("is_deleted", Constants.NO));
        return CollectionUtil.isNotEmpty(list) ? Boolean.TRUE : Boolean.FALSE;
    }
}
