package com.haoqi.magic.business.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.haoqi.magic.business.service.ICsCarInfoService;
import com.haoqi.magic.business.service.ICsHitTagRelativeService;
import com.haoqi.magic.business.service.ICsMarkedCarTagService;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:com.haoqi.magic.system.service.impl <br/>
 * Function: 打标签服务实现类<br/>
 * Date:     2019/5/14 16:19 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class CsMarkedCarTagServiceImpl implements ICsMarkedCarTagService {

    private static final Integer DEFAULT_PAGE_SIZE = 10000;

    @Autowired
    private ICsHitTagRelativeService csHitTagRelativeService;

    @Autowired
    private ICsCarInfoService carInfoService;



    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean markedCarWithTagId(Long tagId, String sqlStr) {
        Integer page = 1;
        Map<String, Object> params = new HashMap<>();
        params.put("sqlStr", sqlStr);
        params.put("page", page);
        params.put("limit", DEFAULT_PAGE_SIZE);
        log.info("begin to markedCar tagId:{},sqlStr:{}",tagId,sqlStr);
        List<Long> cars = carInfoService.getCarWithSqlStr(new Query(params));
        while (CollectionUtil.isNotEmpty(cars)) {
            //3、删除原先的车辆标签命中项
            csHitTagRelativeService.deleteOldHit(cars,tagId);

            //4、添加符合条件的车辆标签数据
            csHitTagRelativeService.insertHitTagRelative(cars, tagId);

            //5、循环处理剩下的数据
            params.put("page", ++page);
            params.put("limit", DEFAULT_PAGE_SIZE);
            cars.clear();
            cars = carInfoService.getCarWithSqlStr(new Query(params));
        }
        log.info("end to markedCar");
        return Boolean.TRUE;
    }
}
