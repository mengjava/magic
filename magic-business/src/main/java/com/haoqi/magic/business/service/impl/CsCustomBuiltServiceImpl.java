package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.common.utils.KeyValueDescUtil;
import com.haoqi.magic.business.mapper.CsCustomBuiltMapper;
import com.haoqi.magic.business.model.dto.CsCustomBuiltDTO;
import com.haoqi.magic.business.model.dto.SysDictDTO;
import com.haoqi.magic.business.model.entity.CsCustomBuilt;
import com.haoqi.magic.business.model.vo.CsCustomBuiltVO;
import com.haoqi.magic.business.service.ICsCustomBuiltService;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 客户定制表 服务实现类
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@Service
public class CsCustomBuiltServiceImpl
        extends ServiceImpl<CsCustomBuiltMapper, CsCustomBuilt>
        implements ICsCustomBuiltService {

    @Autowired
    private CsCustomBuiltMapper csCustomBuiltMapper;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Override
    public Page<List<CsCustomBuiltDTO>> findPage(Query query) {
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> result = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        List<CsCustomBuiltDTO> customBuiltDTOS = csCustomBuiltMapper.findPage(query, query.getCondition());
        customBuiltDTOS.forEach(csCustomBuiltDTO -> {
            csCustomBuiltDTO.setColorCodeName(KeyValueDescUtil.handleValueDesc(csCustomBuiltDTO.getColorCode(), colorCode.getData()));
            csCustomBuiltDTO.setEmissionStandardCodeName(KeyValueDescUtil.handleValueDesc(csCustomBuiltDTO.getEmissionStandardCode(), result.getData()));

            String priceName = null;
            if (null != csCustomBuiltDTO.getMinPrice() && null != csCustomBuiltDTO.getMaxPrice()){
                priceName = csCustomBuiltDTO.getMinPrice() + "万-" + csCustomBuiltDTO.getMaxPrice() + "万";
            }else if (null != csCustomBuiltDTO.getMinPrice() && null == csCustomBuiltDTO.getMaxPrice()){
                priceName = csCustomBuiltDTO.getMinPrice() + "万以上";
            }else if (null == csCustomBuiltDTO.getMinPrice() && null != csCustomBuiltDTO.getMaxPrice()){
                priceName = csCustomBuiltDTO.getMaxPrice() + "万以内";
            }
            csCustomBuiltDTO.setPriceName(priceName);
        });
        return query.setRecords(customBuiltDTOS);
    }

    @Override
    public Boolean insert(CsCustomBuiltVO vo) {
        CsCustomBuilt tag = BeanUtils.beanCopy(vo, CsCustomBuilt.class);
        tag.setCustomBuiltTime(new Date());
        return super.insert(tag);
    }

    @Override
    public Boolean updateCsCustomBuiltById(CsCustomBuiltVO vo) {
        Assert.notNull(vo, "更新定制：参数不能");
        Assert.notNull(vo.getId(), "更新定制：id不能为空");
        Assert.notNull(vo.getCsCarDealerId(), "更新定制：经销商ID不能为空");
        CsCustomBuilt filter = BeanUtils.beanCopy(vo, CsCustomBuilt.class);
        return csCustomBuiltMapper.updateCsLoanCreditById(filter);
    }

    @Override
    public CsCustomBuiltDTO getOneById(Long id, Long csCarDealerId) {
        Assert.notNull(id, "获取分期数据：主键id不能为空");
        Assert.notNull(csCarDealerId, "获取分期数据：经销商ID不能为空");
        CsCustomBuilt csCustomBuilt =
                super.selectOne(new EntityWrapper<CsCustomBuilt>().
                        eq("id", id).
                        eq("cs_car_dealer_id", csCarDealerId).
                        eq("is_deleted", 0));
        if (Objects.isNull(csCustomBuilt)) {
            return new CsCustomBuiltDTO();
        }
        CsCustomBuiltDTO customBuiltDTO = BeanUtils.beanCopy(csCustomBuilt, CsCustomBuiltDTO.class);
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> result = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        customBuiltDTO.setColorCodeName(KeyValueDescUtil.handleValueDesc(customBuiltDTO.getColorCode(), colorCode.getData()));
        customBuiltDTO.setEmissionStandardCodeName(KeyValueDescUtil.handleValueDesc(customBuiltDTO.getEmissionStandardCode(), result.getData()));
        return customBuiltDTO;
    }

    @Override
    @Transactional
    public Boolean deleteByIds(List<Long> lIds) {
        if (CollectionUtil.isNotEmpty(lIds)) {
            List<CsCustomBuilt> list = new ArrayList<>(lIds.size());
            for (Long i : lIds) {
                CsCustomBuilt c = new CsCustomBuilt();
                c.setId(i);
                c.setIsDeleted(CommonConstant.STATUS_DEL);
                list.add(c);
            }
            super.updateBatchById(list);
        }
        return Boolean.TRUE;
    }
}
