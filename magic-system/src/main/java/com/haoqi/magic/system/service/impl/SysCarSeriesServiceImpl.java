package com.haoqi.magic.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.system.mapper.SysCarSeriesMapper;
import com.haoqi.magic.system.model.entity.SysCarSeries;
import com.haoqi.magic.system.model.vo.CarSeriesVO;
import com.haoqi.magic.system.service.ISysCarSeriesService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 车系表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Service
public class SysCarSeriesServiceImpl extends ServiceImpl<SysCarSeriesMapper, SysCarSeries> implements ISysCarSeriesService {

    @Autowired
    private SysCarSeriesMapper sysCarSeriesMapper;

    @Override
    public Page selectCarSeriesPage(Map<String, Object> params) {
        Query query = new Query<>(params);
        query.setOrderByField("series_id").setAsc(true);
        List<SysCarSeries> list = sysCarSeriesMapper.selectCarSeriesPage(query, query.getCondition());
        return query.setRecords(list);
    }

    @Override
    @Cached(name = "carSeriesList:seriesListByBrandId", key = "#brandId", expire = 10000)
    public List<SysCarSeries> selectCarSeriesListByBrandId(Integer brandId) {
        return sysCarSeriesMapper.selectCarSeriesListByBrandId(brandId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setCarSeriesValid(Long id, Integer isDeleted) {
        SysCarSeries sysCarSeries = sysCarSeriesMapper.selectById(id);
        if (Objects.isNull(sysCarSeries)) {
            return Boolean.FALSE;
        }
        sysCarSeries.setModifier(1L);
        sysCarSeries.setGmtModified(DateUtil.date());
        sysCarSeries.setIsDeleted(isDeleted);
        sysCarSeriesMapper.updateById(sysCarSeries);
        return Boolean.TRUE;
    }

    @Override

    public Boolean addCarSeries(CarSeriesVO vo) {
        if (isExist(vo.getSeriesName(), null)) {
            throw new RiggerException(vo.getSeriesName() + "车系名已经存在");
        }
        SysCarSeries series = new SysCarSeries();
        series.setCompId(1L);
        series.setCompName("浩韵控股集团");
        series.setCreator(1L);
        series.setModifier(1L);
        series.setIsDeleted(CommonConstant.STATUS_NORMAL);
        series.setGmtModified(DateUtil.date());
        series.setGmtCreate(DateUtil.date());
        //TODO brandName
        series.setBrandId(vo.getBrandId());
        series.setSeriesName(vo.getSeriesName());
        series.setSeriesId(sysCarSeriesMapper.selectMaxSeriesId() + 100);
        sysCarSeriesMapper.insert(series);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateCarSeries(CarSeriesVO vo) {
        Assert.notNull(vo.getId(), "id不能为空");
        SysCarSeries sysCarSeries = sysCarSeriesMapper.selectById(vo.getId());
        if (Objects.isNull(sysCarSeries)) {
            return Boolean.FALSE;
        }
        if (isExist(vo.getSeriesName(), vo.getId())) {
            throw new RiggerException(vo.getSeriesName() + "车系名已经存在");
        }
        sysCarSeries.setSeriesName(vo.getSeriesName());
        sysCarSeries.setModifier(1L);
        sysCarSeries.setGmtModified(DateUtil.date());
        sysCarSeriesMapper.updateById(sysCarSeries);
        return sysCarSeriesMapper.updateById(sysCarSeries) > 0 ? true : false;
    }


    public Boolean isExist(String seriesName, Long id) {
        Assert.notBlank(seriesName, "车系名不能为空");
        SysCarSeries series = new SysCarSeries();
        series.setBrandName(seriesName);
        SysCarSeries sysCarSeries = sysCarSeriesMapper.selectOne(series);
        if (Objects.isNull(id) && Objects.nonNull(sysCarSeries)) {
            return Boolean.TRUE;
        }
        if (Objects.nonNull(id) && sysCarSeries != null && sysCarSeries.getId().equals(id)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
