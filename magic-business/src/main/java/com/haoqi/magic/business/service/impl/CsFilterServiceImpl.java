package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.enums.FilterTypeEnum;
import com.haoqi.magic.business.mapper.CsFilterMapper;
import com.haoqi.magic.business.model.dto.CsFilterDTO;
import com.haoqi.magic.business.model.entity.CsFilter;
import com.haoqi.magic.business.model.vo.CsFilterVO;
import com.haoqi.magic.business.service.ICsFilterService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 筛选管理表 服务实现类
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
@Service
public class CsFilterServiceImpl
        extends ServiceImpl<CsFilterMapper, CsFilter>
        implements ICsFilterService {

    @Autowired
    private CsFilterMapper csFilterMapper;

    @Autowired
    private FastDfsFileService fastDfsFileService;

    @Override
    public Page<List<CsFilterDTO>> findPage(Query query) {
        List<CsFilter> list = csFilterMapper.findPage(query, query.getCondition());
        List<CsFilterDTO> filterDTOS = BeanUtils.beansToList(list, CsFilterDTO.class);
        return query.setRecords(filterDTOS);
    }

    @Override
    public Boolean insert(CsFilterVO vo) {
        //1、参数校验
        validateParam(vo);
        //2根据筛选名称、类型判断重名与否
        Integer num = this.countByFilterName(null, vo.getFilterName(), vo.getFilterType());
        if (num > 0) {
            throw new RiggerException("新增筛选【" + vo.getFilterName() + "】已存在");
        }

        //3、新增筛选
        CsFilter tag = BeanUtils.beanCopy(vo, CsFilter.class);
        return super.insert(tag);
    }

    @Override
    public Integer countByFilterName(Long id, String cfFilterName, Integer filterType) {
        if (null != id) {
            return super.selectCount(new EntityWrapper<CsFilter>()
                    .ne("id", id)
                    .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                    .eq("filter_type", filterType)
                    .eq("filter_name", cfFilterName));
        } else {
            return super.selectCount(new EntityWrapper<CsFilter>()
                    .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                    .eq("filter_type", filterType)
                    .eq("filter_name", cfFilterName));
        }
    }

    @Override
    public Boolean updateCsFilterById(CsFilterVO vo) {
        validateParam(vo);
        Assert.notNull(vo.getId(), "更新筛选：id不能为空");
        //根据筛选名、类型称判断重名与否
        Integer num = this.countByFilterName(vo.getId(), vo.getFilterName(), vo.getFilterType());
        if (num > 0) {
            throw new RiggerException("更新筛选【" + vo.getFilterName() + "】已存在");
        }
        CsFilter filter = BeanUtils.beanCopy(vo, CsFilter.class);
        return super.updateById(filter);
    }

    @Override
    public CsFilterDTO getOneById(Long id) {
        CsFilter csFilter = super.selectById(id);
        CsFilterDTO dto = BeanUtils.beanCopy(csFilter, CsFilterDTO.class);
        dto.setPictureURL(fastDfsFileService.getFastWebUrl());
        return dto;
    }

    @Override
    public List<CsFilter> getAllCsFilter() {
        return super.selectList(new EntityWrapper<CsFilter>()
                .eq("is_deleted", CommonConstant.STATUS_NORMAL)
                .orderAsc(Arrays.asList("filter_type,order_no")));
    }

    @Override
    public Boolean deleteCsFilterByIds(List<Long> lIds) {
        if (CollectionUtil.isNotEmpty(lIds)) {
            List<CsFilter> entities = new ArrayList<>(lIds.size());
            for (Long i : lIds) {
                CsFilter c = new CsFilter();
                c.setId(i);
                c.setIsDeleted(CommonConstant.STATUS_DEL);
                c.setGmtModified(new Date());
                entities.add(c);
            }
            super.updateBatchById(entities);
        }

        return Boolean.TRUE;
    }

    @Override
    public Map<String, List<CsFilterDTO>> getMapCsFilter() {
        List<CsFilter> csFilters = this.getAllCsFilter();
        Map<String, List<CsFilterDTO>> map = new HashMap<>();
        if (CollectionUtil.isNotEmpty(csFilters)) {
            Map<Integer, List<CsFilter>> groupBy = csFilters.stream().collect(Collectors.groupingBy(CsFilter::getFilterType));
            groupBy.forEach((k, v) -> {
                List<CsFilterDTO> listDTO = BeanUtils.beansToList(v, CsFilterDTO.class);
                listDTO.forEach(e -> {
                    e.setFilePath(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), e.getFilePath()));
                    e.setPictureURL(fastDfsFileService.getFastWebUrl());
                });
                map.put(FilterTypeEnum.getTypeValue(k), listDTO);
            });
        }
        return map;
    }

    @Override
    public List<CsFilterDTO> getCsFilterWithCondition(CsFilterVO vo) {
        return csFilterMapper.getCsFilterWithCondition(vo);
    }


    /**
     * 参数校验
     *
     * @param vo
     * @author huming
     * @date 2019/4/26 17:47
     */
    protected void validateParam(CsFilterVO vo) {
        Assert.notNull(vo, "参数不能为空");
        if (FilterTypeEnum.CAR_SERIAL.getKey().equals(vo.getFilterType())) {
            Assert.notNull(vo.getSysCarSeries(), "指定车系不能为空");
            Assert.notBlank(vo.getSysCarSeriesName(), "车系名称不能为空");
        } else if (FilterTypeEnum.BRAND.getKey().equals(vo.getFilterType())) {
            Assert.notNull(vo.getSysCarBrandId(), "指定品牌不能为空");
            Assert.notNull(vo.getSysCarBrandName(), "品牌名称不能为空");
            Assert.notNull(vo.getFilePath(), "品牌LOGO不能为空");
        } else if (FilterTypeEnum.PRICE.getKey().equals(vo.getFilterType()) &&
                Objects.isNull(vo.getMinPrice()) && Objects.isNull(vo.getMaxPrice())) {
            throw new RiggerException("零售价格不能为空");
        }
        if (Objects.nonNull(vo.getOrderNo()) && (vo.getOrderNo() > 255 || vo.getOrderNo() <= 0)) {
            throw new RiggerException("排序号限制在[1-255]之间");
        }
    }
}
