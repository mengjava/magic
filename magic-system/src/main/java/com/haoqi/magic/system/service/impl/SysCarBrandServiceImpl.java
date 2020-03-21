package com.haoqi.magic.system.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.system.mapper.SysCarBrandMapper;
import com.haoqi.magic.system.model.entity.SysCarBrand;
import com.haoqi.magic.system.model.vo.CarBrandVO;
import com.haoqi.magic.system.service.ISysCarBrandService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.PinyinUtil;
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
 * 品牌表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Service
public class SysCarBrandServiceImpl extends ServiceImpl<SysCarBrandMapper, SysCarBrand> implements ISysCarBrandService {

    @Autowired
    private SysCarBrandMapper sysCarBrandMapper;

    @Override
    public Page selectCarBrandPage(Map<String, Object> params) {
        Query query = new Query(params);
        query.setOrderByField("brand_initial").setAsc(true);
        List<SysCarBrand> list = sysCarBrandMapper.selectCarBrandPage(query, query.getCondition());
        return query.setRecords(list);
    }

    @Override
    @Cached(name = "carBrandList:carBrandList", expire = 30000)
    public List<SysCarBrand> selectCarBrandList() {
        return sysCarBrandMapper.selectCarBrandList();
    }

    @Override
    @Deprecated
    @Transactional(rollbackFor = Exception.class)
    public Boolean addCarBrand(CarBrandVO vo) {
        if (isExist(vo.getBrandName())) {
            throw new RiggerException(vo.getBrandName() + "品牌名已经存在");
        }
        String brandInitial = PinyinUtil.getAllFirstLetter(vo.getBrandName()).substring(0, 1).toUpperCase();
        Integer brandId = sysCarBrandMapper.selectMaxBrandId();
        SysCarBrand sysCarBrand = new SysCarBrand();
        sysCarBrand.setCompName("浩韵控股集团");
        sysCarBrand.setCompId(1L);
        sysCarBrand.setCreator(1L);
        sysCarBrand.setModifier(1L);
        sysCarBrand.setIsDeleted(CommonConstant.STATUS_NORMAL);
        sysCarBrand.setGmtModified(DateUtil.date());
        sysCarBrand.setGmtCreate(DateUtil.date());
        sysCarBrand.setBrandId(brandId + 10);
        sysCarBrand.setBrandName(vo.getBrandName());
        sysCarBrand.setBrandInitial(brandInitial);
        return this.insert(sysCarBrand);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean setCarBrandValid(Long id, Integer isDeleted) {
        SysCarBrand sysCarBrand = sysCarBrandMapper.selectById(id);
        if (Objects.isNull(sysCarBrand)) {
            return Boolean.FALSE;
        }
        sysCarBrand.setModifier(0L);
        sysCarBrand.setGmtModified(DateUtil.date());
        sysCarBrand.setIsDeleted(isDeleted);
        sysCarBrandMapper.updateById(sysCarBrand);
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCarBrand(CarBrandVO vo) {
        Assert.notNull(vo.getId(), "id not be null");
        //品牌去除
        SysCarBrand sysCarBrand = sysCarBrandMapper.selectById(vo.getId());
        if (Objects.isNull(sysCarBrand)) {
            return Boolean.FALSE;
        }
        if (isExist(vo.getBrandName(), vo.getId())) {
            throw new RiggerException(vo.getBrandName() + "品牌名已经存在");
        }
        sysCarBrand.setBrandName(vo.getBrandName());
        sysCarBrand.setBrandInitial(PinyinUtil.getAllFirstLetter(vo.getBrandName()).substring(0, 1).toUpperCase());
        return sysCarBrandMapper.updateById(sysCarBrand) > 0 ? true : false;
    }

    @Cached(name = "brandName:isExist", key = "#brandName", expire = 3000)
    public Boolean isExist(String brandName) {
        Assert.notBlank(brandName, "品牌名不能为空");
        SysCarBrand carBrand = new SysCarBrand();
        carBrand.setBrandName(brandName);
        if (sysCarBrandMapper.selectCount(new EntityWrapper<>(carBrand)) > 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public Boolean isExist(String brandName, Long id) {
        Assert.notBlank(brandName, "品牌名不能为空");
        Assert.notNull(id, "id not be null");
        SysCarBrand carBrand = new SysCarBrand();
        carBrand.setBrandName(brandName);
        SysCarBrand sysCarBrand = sysCarBrandMapper.selectOne(carBrand);
        if (sysCarBrand != null && sysCarBrand.getId().equals(id)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
