package com.haoqi.magic.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.haoqi.magic.system.mapper.SysAdvertConfigMapper;
import com.haoqi.magic.system.model.dto.SysAdvertDTO;
import com.haoqi.magic.system.model.entity.SysAdvertConfig;
import com.haoqi.magic.system.model.entity.SysDict;
import com.haoqi.magic.system.model.enums.AdvertStatusEnum;
import com.haoqi.magic.system.service.ISysAdvertConfigService;
import com.haoqi.magic.system.service.ISysDictService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广告配置表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-04-25
 */
@Slf4j
@Service
public class SysAdvertConfigServiceImpl extends ServiceImpl<SysAdvertConfigMapper, SysAdvertConfig> implements ISysAdvertConfigService {
    @Autowired
    private SysAdvertConfigMapper sysAdvertConfigMapper;
    @Autowired
    private FastDfsFileService fastDfsFileService;
    @Autowired
    private ISysDictService sysDictService;

    @Override
    public Page findAdvertByPage(Query query) {
        List<SysAdvertDTO> adverts = sysAdvertConfigMapper.findAdvertByPage(query, query.getCondition());
        adverts.parallelStream().forEach(advertDTO -> {
            advertDTO.setPicturePath(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), advertDTO.getPicturePath()));
            SysDict dict = new SysDict();
            dict.setKeyworld(advertDTO.getPositionCode());
            SysDict dicts = sysDictService.selectOne(new EntityWrapper<SysDict>(dict));
            advertDTO.setPositionCode(dicts == null ? "" : dicts.getValueDesc());
        });
        return query.setRecords(adverts);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean insertAdvert(SysAdvertConfig advertConfig) {
        advertConfig.setStatus(AdvertStatusEnum.ON_SHELVES.getKey());
        this.insert(advertConfig);
        return Boolean.TRUE;
    }

    @Override
    @Cached(name = "advert:isExist:", key = "#title", expire = 3000)
    public Boolean isExist(String title) {
        Assert.notBlank(title, "广告标题不能为空");
        SysAdvertConfig advertConfig = new SysAdvertConfig();
        advertConfig.setTitle(title);
        advertConfig.setIsDeleted(CommonConstant.STATUS_NORMAL);
        SysAdvertConfig _advertConfig = this.selectOne(new EntityWrapper<>(advertConfig));
        if (_advertConfig == null) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateAdvert(SysAdvertConfig advertConfig) {
        Assert.notNull(advertConfig.getId(), "广告id不能为空");
        SysAdvertDTO advertDTO = this.getAdvertById(advertConfig.getId());
        advertConfig.setPicturePath(URLUtil.decode(advertConfig.getPicturePath()));
        this.updateById(advertConfig);
        if (!advertDTO.getPicturePath().equals(advertConfig.getPicturePath())) {
            try {
                fastDfsFileService.deleteFile(advertDTO.getPicturePath());
            } catch (RuntimeException e) {
                log.error("删除指定文件路径：{} 异常：", advertDTO.getPicturePath(), e);
            }
        }
        return Boolean.TRUE;

    }


    @Override
    public SysAdvertDTO getAdvertById(Long id) {
        Assert.notNull(id, "广告id不能为空");
        SysAdvertConfig advertConfig = this.selectById(id);
        SysAdvertDTO advertDTO = new SysAdvertDTO();
        BeanUtil.copyProperties(advertConfig, advertDTO);
        advertDTO.setPictureURL(fastDfsFileService.getFastWebUrl());
        return advertDTO;
    }


    @Override
    public Boolean updateStatusById(Long id) {
        Assert.notNull(id, "广告id不能为空");
        SysAdvertConfig advertConfig = this.selectById(id);
        Integer status = AdvertStatusEnum.ON_SHELVES.getKey().equals(advertConfig.getStatus()) ?
                AdvertStatusEnum.OFF_SHELVES.getKey() : AdvertStatusEnum.ON_SHELVES.getKey();
        advertConfig.setStatus(status);
        this.updateById(advertConfig);
        return Boolean.TRUE;
    }


    @Override
    public Boolean deleteAdvert(Long id) {
        Assert.notNull(id, "广告id不能为空");
        SysAdvertConfig advert = this.selectById(id);
        String picture_path = advert.getPicturePath();
        this.deleteById(id);
        if (StrUtil.isNotBlank(picture_path)) {
            try {
                //fastDfsFileService.deleteFile(picture_path);
            } catch (Exception e) {
            }
        }
        return Boolean.TRUE;
    }

    @Override
    @Cached(name = "advert:getAdvertByPositionCode:", key = "#code", expire = 3000)
    public List<SysAdvertDTO> getAdvertByPositionCode(String code) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("positionCode", code);
        map.put("status", AdvertStatusEnum.ON_SHELVES.getKey());
        Query query = new Query(map);
        List<SysAdvertDTO> adverts = sysAdvertConfigMapper.findAdvertByPage(query, query.getCondition());
        adverts.forEach(e -> {
            e.setPicturePath(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), e.getPicturePath()));
        });
        return adverts;
    }
}
