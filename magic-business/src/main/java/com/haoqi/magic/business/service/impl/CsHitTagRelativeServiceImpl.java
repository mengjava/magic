package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.enums.TagTypeEnum;
import com.haoqi.magic.business.mapper.CsHitTagRelativeMapper;
import com.haoqi.magic.business.model.dto.CsTagParamDTO;
import com.haoqi.magic.business.model.dto.TagsDTO;
import com.haoqi.magic.business.model.entity.CsHitTagRelative;
import com.haoqi.magic.business.service.ICsCarInfoService;
import com.haoqi.magic.business.service.ICsHitTagRelativeService;
import com.haoqi.magic.business.service.ICsTagService;
import com.haoqi.rigger.common.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * 车辆命中标签关系表 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-05-08
 */
@Service
@Slf4j
public class CsHitTagRelativeServiceImpl
        extends ServiceImpl<CsHitTagRelativeMapper, CsHitTagRelative>
        implements ICsHitTagRelativeService {

    @Autowired
    private CsHitTagRelativeMapper csHitTagRelativeMapper;

    @Autowired
    private ICsTagService csTagService;

    @Autowired
    private ICsCarInfoService carInfoService;


    @Override
    @Cached(name = "csHitTagRelative:getCsTagsByCarId:", key = "#carId", expire = 3600)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)
    public List<TagsDTO> getCsTagsByCarId(Long carId) {
        return csHitTagRelativeMapper.getCsTagsByCarId(carId);
    }

    @Override
    public Boolean htiTagByCarId(Long carId) {
        Assert.notNull(carId, "给车辆打标签：车辆ID不能为空");
        CompletableFuture.runAsync(() -> {
            List<CsTagParamDTO> tags = csTagService.getAllDetailTag(TagTypeEnum.DETAIL_TAG.getKey());
            if (CollectionUtil.isNotEmpty(tags)) {
                tags.stream()
                        .filter(csTagParamDTO -> StrUtil.isNotBlank(csTagParamDTO.getSqlStr()))
                        .forEach(csTagParamDTO -> {
                            csHitTagRelativeMapper.delete(new EntityWrapper<CsHitTagRelative>()
                                    .eq("cs_car_info_id", carId)
                                    .eq("cs_tag_id", csTagParamDTO.getTagId())
                                    .eq("is_deleted", CommonConstant.STATUS_NORMAL));
                            Boolean result = carInfoService.checkTagWithSqlStr(carId, csTagParamDTO.getSqlStr());
                            if (result) {
                                CsHitTagRelative entity = new CsHitTagRelative();
                                entity.setCreator(1L);
                                entity.setModifier(1L);
                                entity.setGmtCreate(new Date());
                                entity.setGmtModified(new Date());
                                entity.setIsDeleted(CommonConstant.STATUS_NORMAL);
                                entity.setCsCarInfoId(carId);
                                entity.setCsTagId(csTagParamDTO.getTagId());
                                csHitTagRelativeMapper.insert(entity);
                            }
                        });
            }
        }).exceptionally(throwable -> {
            log.error("给车辆打标签异常：{}",throwable);
            return null;
        });

        return Boolean.TRUE;
    }

    @Override
    public Boolean deleteOldHit(List<Long> cars, Long tagId) {
        Map<String, Object> param = new HashMap<>();
        param.put("cars", cars);
        param.put("tagId", tagId);
        return csHitTagRelativeMapper.deleteOldHit(param);
    }

    @Override
    public Boolean insertHitTagRelative(List<Long> cars, Long tagId) {
        Map<String, Object> param = new HashMap<>();
        param.put("cars", cars);
        param.put("tagId", tagId);
        return csHitTagRelativeMapper.insertHitTagRelative(param);
    }
}
