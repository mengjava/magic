package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.common.utils.KeyValueDescUtil;
import com.haoqi.magic.business.mapper.CsLoanCreditMapper;
import com.haoqi.magic.business.model.dto.CsLoanCreditDTO;
import com.haoqi.magic.business.model.dto.SysDictDTO;
import com.haoqi.magic.business.model.entity.CsLoanCredit;
import com.haoqi.magic.business.model.vo.CsLoanCreditVO;
import com.haoqi.magic.business.service.ICsLoanCreditService;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 分期表 服务实现类
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@Service
public class CsLoanCreditServiceImpl
        extends ServiceImpl<CsLoanCreditMapper, CsLoanCredit>
        implements ICsLoanCreditService {

    @Autowired
    private CsLoanCreditMapper csLoanCreditMapper;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Override
    public Page<List<CsLoanCreditDTO>> findPage(Query query) {
        List<CsLoanCreditDTO> loanCreditDTOS = csLoanCreditMapper.findPage(query, query.getCondition());
        Result<List<SysDictDTO>> workCode = systemServiceClient.getDictByClass(DictClassEnum.WORK_CODE_170000.getClassCode());
        Result<List<SysDictDTO>> incomeCode = systemServiceClient.getDictByClass(DictClassEnum.INCOME_CODE_180000.getClassCode());
        loanCreditDTOS.forEach(csLoanCreditDTO -> {
            csLoanCreditDTO.setWorkCodeName(KeyValueDescUtil.handleValueDesc(csLoanCreditDTO.getWorkCode(), workCode.getData()));
            csLoanCreditDTO.setIncomeCodeName(KeyValueDescUtil.handleValueDesc(csLoanCreditDTO.getIncomeCode(), incomeCode.getData()));
        });
        return query.setRecords(loanCreditDTOS);
    }

    @Override
    public Boolean insert(CsLoanCreditVO vo) {
        CsLoanCredit tag = BeanUtils.beanCopy(vo, CsLoanCredit.class);
        return super.insert(tag);
    }

    @Override
    public Boolean updateCsLoanCreditById(CsLoanCreditVO vo) {
        Assert.notNull(vo, "更新分期数据：参数不能");
        Assert.notNull(vo.getId(), "更新分期数据：id不能为空");
        Assert.notNull(vo.getCsCarDealerId(), "更新分期：经销商ID不能为空");
        CsLoanCredit filter = BeanUtils.beanCopy(vo, CsLoanCredit.class);
        return csLoanCreditMapper.updateCsLoanCreditById(filter);
    }

    @Override
    public CsLoanCreditDTO getOneById(Long id, Long csCarDealerId) {
        Assert.notNull(id, "获取分期数据：主键id不能为空");
        Assert.notNull(csCarDealerId, "获取分期数据：经销商ID不能为空");
        CsLoanCredit csLoanCredit =
                super.selectOne(new EntityWrapper<CsLoanCredit>()
                        .eq("id", id).eq("cs_car_dealer_id", csCarDealerId)
                        .eq("is_deleted", 0));
        if (Objects.isNull(csLoanCredit)) {
            return new CsLoanCreditDTO();
        }
        CsLoanCreditDTO loanCreditDTO = BeanUtils.beanCopy(csLoanCredit, CsLoanCreditDTO.class);
        Result<List<SysDictDTO>> workCode = systemServiceClient.getDictByClass(DictClassEnum.WORK_CODE_170000.getClassCode());
        Result<List<SysDictDTO>> incomeCode = systemServiceClient.getDictByClass(DictClassEnum.INCOME_CODE_180000.getClassCode());
        loanCreditDTO.setWorkCodeName(KeyValueDescUtil.handleValueDesc(loanCreditDTO.getWorkCode(), workCode.getData()));
        loanCreditDTO.setIncomeCodeName(KeyValueDescUtil.handleValueDesc(loanCreditDTO.getIncomeCode(), incomeCode.getData()));
        return loanCreditDTO;
    }

    @Override
    @Transactional
    public Boolean deleteByIds(List<Long> lIds) {
        if (CollectionUtil.isNotEmpty(lIds)) {
            List<CsLoanCredit> list = new ArrayList<>(lIds.size());
            for (Long i : lIds) {
                CsLoanCredit c = new CsLoanCredit();
                c.setId(i);
                c.setIsDeleted(CommonConstant.STATUS_DEL);
                list.add(c);
            }
            super.updateBatchById(list);
        }
        return Boolean.TRUE;
    }
}
