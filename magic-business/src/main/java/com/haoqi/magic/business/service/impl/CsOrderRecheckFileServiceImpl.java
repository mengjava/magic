package com.haoqi.magic.business.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.mapper.CsOrderRecheckFileMapper;
import com.haoqi.magic.business.model.dto.CsCarCheckItemDTO;
import com.haoqi.magic.business.model.dto.CsOrderRecheckFileDTO;
import com.haoqi.magic.business.model.entity.CsCarCheckItem;
import com.haoqi.magic.business.model.entity.CsOrderRecheckFile;
import com.haoqi.magic.business.service.ICsCarCheckItemService;
import com.haoqi.magic.business.service.ICsOrderRecheckFileService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * <p>
 * 复检附件 服务实现类
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
@Service
public class CsOrderRecheckFileServiceImpl extends ServiceImpl<CsOrderRecheckFileMapper, CsOrderRecheckFile> implements ICsOrderRecheckFileService {

    @Autowired
    private CsOrderRecheckFileMapper csOrderRecheckFileMapper;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Autowired
    private ICsCarCheckItemService carCheckItemService;


    @Override
    public List<CsOrderRecheckFileDTO> findByOrderIdCheckItemId(Long orderId, Long itemId) {
        List<CsOrderRecheckFile> csOrderRecheckFiles = csOrderRecheckFileMapper.selectList(new EntityWrapper<CsOrderRecheckFile>().eq("cs_car_order_id", orderId).eq("cs_car_check_item_id", itemId));
        /*csOrderRecheckFiles.forEach(
                check -> {

                }
        );*/
        return BeanUtils.beansToList(csOrderRecheckFiles, CsOrderRecheckFileDTO.class);
    }

    @Override
    public List<CsOrderRecheckFileDTO> findWithCheckItemByOrderId(Long orderId) {
        List<CsOrderRecheckFileDTO> recheckDTOs = csOrderRecheckFileMapper.findWithCheckItemByOrderId(orderId);
        recheckDTOs.forEach(file -> {
            file.setWebUrl(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), StrUtil.EMPTY));
        });
        return recheckDTOs;
    }

    @Override
    public CsOrderRecheckFileDTO saveOrUpdate(CsOrderRecheckFileDTO carCheckDTO) {
        CsOrderRecheckFile csOrderRecheckFile = BeanUtils.beanCopy(carCheckDTO, CsOrderRecheckFile.class);
        this.insertOrUpdate(csOrderRecheckFile);
        carCheckDTO.setId(csOrderRecheckFile.getId());
        return carCheckDTO;
    }

    @Override
    /*@Cached(name = "orderRecheck:findByCarIdCheckType:", key = "#orderId+'|'+#type", expire = 3600)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)*/
    public List<Map<String, Object>> findByCarIdCheckType(Long orderId, Integer type) {
        List<Map<String, Object>> list = new ArrayList();
        Assert.notNull(orderId, "订单ID不能为空");
        Assert.notNull(type, "车辆检查类型不能为空");
        List<CsCarCheckItem> checkItems = carCheckItemService.findCheckItemByType(type);
        List<Long> checkItemIds = checkItems.stream().map(CsCarCheckItem::getId).collect(Collectors.toList());
        List<CsOrderRecheckFileDTO> checkDTOS = csOrderRecheckFileMapper.findWithCheckItemByOrderIdItemIds(orderId, checkItemIds);
        setCarCheckItem(list, checkDTOS);
        return list;
    }

    @Override
    public Page<CsOrderRecheckFileDTO> findPage(Query query) {
        List<CsOrderRecheckFileDTO> recheckFiles = csOrderRecheckFileMapper.findPage(query, query.getCondition());
        recheckFiles.forEach(fileDTO -> {
            fileDTO.setWebUrl(fastDfsFileService.getFastWebUrl());
        });
        return query.setRecords(recheckFiles);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByOrderIdCheckId(Long orderId, List<Long> ids) {
        ids.forEach(
                id -> {
                    csOrderRecheckFileMapper.delete(new EntityWrapper<CsOrderRecheckFile>().eq("cs_car_order_id", orderId).eq("id", id));
                }
        );
        return Boolean.TRUE;
    }


    private void handlerLastItem(List<CsOrderRecheckFileDTO> recheckDTOs) {
        recheckDTOs.stream()
                .forEach(carCheckDTO -> {
                    carCheckDTO.setWebUrl(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), StrUtil.EMPTY));
                    if (StrUtil.isNotBlank(carCheckDTO.getCsCarCheckLastItemId())) {
                        long[] lastItemId = StrUtil.splitToLong(carCheckDTO.getCsCarCheckLastItemId(), StrUtil.COMMA);
                        LongStream.of(lastItemId).forEach(id -> {
                            CsCarCheckItemDTO checkItemDTO = carCheckItemService.getCheckItemWithParentItemNameById(id);
                            carCheckDTO.getCheckItems().add(checkItemDTO);
                        });
                    }
                });
    }

    private void setCarCheckItem(List<Map<String, Object>> list, List<CsOrderRecheckFileDTO> checkDTOS) {
        Map<String, List<CsOrderRecheckFileDTO>> collect = checkDTOS.stream().collect(Collectors.groupingBy(CsOrderRecheckFileDTO::getCheckItemName, LinkedHashMap::new, Collectors.toList()));
        Set<String> strings = collect.keySet();
        for (String key : strings) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            List<CsOrderRecheckFileDTO> value = collect.get(key);
            value.forEach(e -> {
                e.setFilePath(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), e.getFilePath()));
            });
            map.put("value", value);
            list.add(map);
        }
    }
}
