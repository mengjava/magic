package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.mapper.CsCarCheckMapper;
import com.haoqi.magic.business.model.dto.CarCheckDTO;
import com.haoqi.magic.business.model.dto.CsCarCheckItemDTO;
import com.haoqi.magic.business.model.entity.CsCarCheck;
import com.haoqi.magic.business.model.entity.CsCarCheckItem;
import com.haoqi.magic.business.service.ICsCarCheckItemService;
import com.haoqi.magic.business.service.ICsCarCheckService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * <p>
 * 车辆检测信息 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
@Service
public class CsCarCheckServiceImpl extends ServiceImpl<CsCarCheckMapper, CsCarCheck> implements ICsCarCheckService {

    @Autowired
    private CsCarCheckMapper carCheckMapper;

    @Autowired
    private ICsCarCheckItemService carCheckItemService;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteNotInTheseId(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return;
        }
        carCheckMapper.deleteNotInTheseId(ids);
    }

    @Override
    public List<CarCheckDTO> findByCarIdCheckItemId(Long carId, Long itemId) {
        List<CsCarCheck> carChecks = this.selectList(new EntityWrapper<CsCarCheck>().eq("cs_car_info_id", carId).eq("cs_car_check_item_id", itemId));
        return BeanUtils.beansToList(carChecks, CarCheckDTO.class);
    }

    @Override
    public List<CarCheckDTO> findWithCheckItemByCarId(Long carId) {
        List<CarCheckDTO> checkDTOS = carCheckMapper.findWithCheckItemByCarId(carId);
        handlerLastItem(checkDTOS);
        return checkDTOS;
    }


    @Override
    @Cached(name = "carCheckDTO:findByCarIdType:", key = "#carId+'|'+#type", expire = 3600)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)
    public List<CarCheckDTO> findWithCheckItemByCarIdType(Long carId, Integer type) {
        Assert.notNull(carId, "车辆ID不能为空");
        Assert.notNull(type, "车辆检查类型不能为空");
        List<CsCarCheckItem> checkItems = carCheckItemService.findCheckItemByType(type);
        List<Long> checkItemIds = checkItems.stream().map(CsCarCheckItem::getId).collect(Collectors.toList());
        List<CarCheckDTO> checkDTOS = carCheckMapper.findWithCheckItemByCarIdItemIds(carId, checkItemIds);
        handlerLastItem(checkDTOS);
        return checkDTOS;
    }

    @Override
    public CarCheckDTO saveOrUpdate(CarCheckDTO carCheckDTO) {
        CsCarCheck carCheck = BeanUtils.beanCopy(carCheckDTO, CsCarCheck.class);
        this.insertOrUpdate(carCheck);
        carCheckDTO.setId(carCheck.getId());
        return carCheckDTO;
    }

    @Override
    @Cached(name = "carCheckDTO:findByCarIdCheckType:", key = "#carId+'|'+#type", expire = 3600)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)
    public List<Map<String, Object>> findByCarIdCheckType(Long carId, Integer type) {
        List<Map<String, Object>> list = new ArrayList();
        Assert.notNull(carId, "车辆ID不能为空");
        Assert.notNull(type, "车辆检查类型不能为空");
        List<CsCarCheckItem> checkItems = carCheckItemService.findCheckItemByType(type);
        List<Long> checkItemIds = checkItems.stream().map(CsCarCheckItem::getId).collect(Collectors.toList());
        List<CarCheckDTO> checkDTOS = carCheckMapper.findWithCheckItemByCarIdItemIds(carId, checkItemIds);
        setCarCheckItem(list, checkDTOS);
        return list;
    }

    @Override
    public List<Map<String, Object>> findByCarIdCheckAll(Long carId) {
        List<Map<String, Object>> list = new ArrayList<>();
        Assert.notNull(carId, "车辆ID不能为空");
        List<CarCheckDTO> checkDTOS = carCheckMapper.findWithCheckItemByCarId(carId);
        setCarCheckItem(list, checkDTOS);
        return list;
    }

    private void setCarCheckItem(List<Map<String, Object>> list, List<CarCheckDTO> checkDTOS) {
        Map<String, List<CarCheckDTO>> collect = checkDTOS.stream().collect(Collectors.groupingBy(CarCheckDTO::getCheckItemName, LinkedHashMap::new, Collectors.toList()));
        Set<String> strings = collect.keySet();
        for (String key : strings) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", key);
            List<CarCheckDTO> value = collect.get(key);
            value.forEach(e -> {
                e.setFilePath(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), e.getFilePath()));
            });
            map.put("value", value);
            list.add(map);
        }
    }


    /**
     * 处理最后检查部位项名、父节点名（右前车门 变形）
     *
     * @param checkDTOS
     */
    private void handlerLastItem(List<CarCheckDTO> checkDTOS) {
        checkDTOS.stream()
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
}
