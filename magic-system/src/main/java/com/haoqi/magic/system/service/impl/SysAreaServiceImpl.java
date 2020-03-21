package com.haoqi.magic.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.system.common.enums.RegionLevelTypeEnum;
import com.haoqi.magic.system.mapper.SysAreaMapper;
import com.haoqi.magic.system.model.dto.*;
import com.haoqi.magic.system.model.entity.SysArea;
import com.haoqi.magic.system.service.ISysAreaService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.common.util.PinyinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 城市区域表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@Service
public class SysAreaServiceImpl extends ServiceImpl<SysAreaMapper, SysArea> implements ISysAreaService {
    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Override
    public List<SysAreaDTO> getSysAreaDTOS(Map maps) {
        List<SysAreaDTO> sysAreaDTOList = sysAreaMapper.selectProvinceByMap(maps);
        return sysAreaDTOList;
    }


    @Override
    public List<SysProvinceListDTO> findProvinceAndCityList() {
        List<SysProvinceListDTO> list = new ArrayList<>();
        //获取所有的省份
        List<SysArea> areaList = sysAreaMapper.selectList(new EntityWrapper<SysArea>().eq("level", RegionLevelTypeEnum.PROVINCE.getKey()));
        //遍历所有省份
        areaList.forEach(a -> {
            SysProvinceListDTO sysProvinceListDTO = BeanUtils.beanCopy(a, SysProvinceListDTO.class);
            //根据省份code获取所有的城市
            List<SysCityDTO> cityDTOList = sysAreaMapper.getCityByProvinceCode(a.getProvinceCode());
            sysProvinceListDTO.setCityList(cityDTOList);
            list.add(sysProvinceListDTO);
        });
        return list;

    }

    @Override
    @Cached(name = "area:getAreaByCityCode", key = "#code", expire = 60000)
    public SysProvinceAndCityDTO getAreaByCityCode(String code) {
        Assert.notBlank(code, "区域code不能为空");
        SysArea sysArea = new SysArea();
        sysArea.setCityCode(code);
        SysArea area = sysAreaMapper.selectOne(sysArea);
        if (Objects.nonNull(area)) {
            return BeanUtils.beanCopy(area, SysProvinceAndCityDTO.class);
        }
        return null;
    }

    @Override
    @Cached(name = "area:getAreaByCityId", key = "#id", expire = 3600)
    public SysProvinceAndCityDTO getAreaByCityId(Long id) {
        Assert.notNull(id, "区域id不能为空");
        SysArea sysArea = new SysArea();
        sysArea.setId(id);
        sysArea.setIsDeleted(CommonConstant.STATUS_NORMAL);
        SysArea area = sysAreaMapper.selectOne(sysArea);
        if (Objects.nonNull(area)) {
            return BeanUtils.beanCopy(area, SysProvinceAndCityDTO.class);
        }
        return null;
    }

    @Override
    public List<CsAppHotCityDTO> getHotCity() {
        List<CsAppHotCityDTO> list = sysAreaMapper.selectHotCity();
        list.forEach(
                e -> {
                    e.setValue(String.format("%s-%s", e.getProvinceName(), e.getCityName()));
                }
        );
        return list;
    }

    @Override
    public List<CsAppHotCityDTO> getAllCity() {
        List<CsAppHotCityDTO> list = sysAreaMapper.selectAllCity();
        list.forEach(
                e -> {
                    e.setValue(String.format("%s-%s", e.getProvinceName(), e.getCityName()));
                }
        );
        return list;
    }

    @Override
    public Boolean makeInitial() {
        SysArea sysArea = new SysArea();
        sysArea.setIsDeleted(Constants.NO);
        sysArea.setLevel(2);
        List<SysArea> sysAreas = sysAreaMapper.selectList(new EntityWrapper<>(sysArea));
        sysAreas.forEach(
                area -> {
                    String brandInitial = PinyinUtil.getAllFirstLetter(area.getCityName()).substring(0, 1).toUpperCase();
                    area.setCityInitial(brandInitial);
                    sysAreaMapper.updateById(area);
                }
        );
        return Boolean.TRUE;
    }


    @Override
    @Cached(name = "area:getAreaById", key = "#id", expire = 3600)
    public SysProvinceAndCityDTO getAreaById(Long id) {
        Assert.notNull(id, "区域id不能为空");
        SysProvinceAndCityDTO sysProvinceAndCityDTO = new SysProvinceAndCityDTO();
        SysArea sysArea = this.selectById(id);
        BeanUtil.copyProperties(sysArea, sysProvinceAndCityDTO);
        return sysProvinceAndCityDTO;
    }

}
