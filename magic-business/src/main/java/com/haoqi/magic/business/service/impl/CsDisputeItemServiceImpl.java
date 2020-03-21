package com.haoqi.magic.business.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.enums.DisputeItemTypeEnum;
import com.haoqi.magic.business.model.dto.CsItemBaseDTO;
import com.haoqi.magic.business.model.dto.CsDisputeItemDTO;
import com.haoqi.magic.business.model.entity.CsDisputeItem;
import com.haoqi.magic.business.mapper.CsDisputeItemMapper;
import com.haoqi.magic.business.service.ICsDisputeItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 争议项管理 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
@Service
public class CsDisputeItemServiceImpl extends ServiceImpl<CsDisputeItemMapper, CsDisputeItem> implements ICsDisputeItemService {

    @Autowired
    private CsDisputeItemMapper csDisputeItemMapper;

    @Override
    public List<CsItemBaseDTO> getAllItemType() {
        List<CsItemBaseDTO> list = new ArrayList<>();
        DisputeItemTypeEnum[] values = DisputeItemTypeEnum.values();
        for (DisputeItemTypeEnum typeEnum : values) {
            CsItemBaseDTO dto = new CsItemBaseDTO();
            dto.setKey(typeEnum.getKey());
            dto.setValue(typeEnum.getValue());
            list.add(dto);
        }
        return list;
    }

    @Override
    public Page selectPageByParam(Query query) {
        List<CsDisputeItemDTO> list = csDisputeItemMapper.selectPageByParam(query, query.getCondition());
        return query.setRecords(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addOrUpdate(CsDisputeItem csDisputeItem) {

        return this.insertOrUpdate(csDisputeItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByIds(Long id) {
        //如果是父节点-删除下面的子节点
        CsDisputeItem csDisputeItem = csDisputeItemMapper.selectById(id);
        if (Objects.isNull(csDisputeItem)) {
            return Boolean.FALSE;
        }
        //删除本级和子级
        csDisputeItemMapper.deleteByIdAndParentId(id);
        return Boolean.TRUE;
    }

    @Override
    public List<CsDisputeItemDTO> selectListByParam(Map<String, Object> params) {
        List<CsDisputeItemDTO> list = csDisputeItemMapper.selectListByParam(params);
        return list;
    }
}
