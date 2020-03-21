package com.haoqi.magic.job.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.haoqi.magic.job.common.enums.TagTypeEnum;
import com.haoqi.magic.job.model.dto.CarDTO;
import com.haoqi.magic.job.model.dto.TagParamDTO;
import com.haoqi.magic.job.service.ICsCarService;
import com.haoqi.magic.job.service.ICsHitTagRelativeService;
import com.haoqi.magic.job.service.ICsMarkedCarTagService;
import com.haoqi.magic.job.service.ICsTagService;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName:com.haoqi.magic.job.service.impl <br/>
 * Function: 打标签服务实现类<br/>
 * Date:     2019/5/10 10:19 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Service
@Slf4j
public class CsMarkedCarTagServiceImpl
        implements ICsMarkedCarTagService {

    //标签
    @Autowired
    private ICsTagService csTagService;

    //车辆
    @Autowired
    private ICsCarService csCarService;

    //标签命中
    @Autowired
    private ICsHitTagRelativeService csHitTagRelativeService;

    private static final Integer DEFAULT_PAGE_SIZE = 10000;


    @Override
    @Transactional
    public Boolean markCarTag(Integer total, Integer item) {

        log.info("CsMarkedCarTagServiceImpl markCarTag total: {},item :{}",total,item);
        Integer carCount =  csCarService.getCarTotalCount();
        if (carCount.compareTo(0) <= 0){//没有车源存在，直接返回
            return Boolean.TRUE;
        }

        int everyShardingTotal = carCount/total;
        int limit = DEFAULT_PAGE_SIZE;
        if (DEFAULT_PAGE_SIZE > everyShardingTotal){
            limit = everyShardingTotal;
        }

        //1、获取全部的详情标签
        List<TagParamDTO> tags = csTagService.getAllDetailTag(TagTypeEnum.DETAIL_TAG.getKey());
        int startPage = ((item+1)*limit)/limit;
        int endPage = (((item+1)*limit)+everyShardingTotal)/limit;
        if ((item+1) == total){//最后一个分片多计算几页
            endPage += item;
        }
        log.info("CsMarkedCarTagServiceImpl markCarTag startPage: {},endPage :{}",startPage,endPage);

        //1、1 删除旧的车辆标签数据(前一天跑批的数据)
        csHitTagRelativeService.deleteAllOld();
        if (CollectionUtil.isNotEmpty(tags)) {
            log.info("CsMarkedCarTagServiceImpl markCarTag tags: {}", JSONObject.toJSONString(tags));
            int finalLimit = limit;
            int finalEndPage = endPage;
            //2、根据筛选条件获取对应的车辆信息
            tags.stream().filter(dto->StrUtil.isNotBlank(dto.getSqlStr())).forEach(dto->{
                int page = startPage;
                Map<String, Object> params = new HashMap<>();
                params.put("sqlStr", dto.getSqlStr());
                params.put("page", page);
                params.put("limit", finalLimit);
                List<CarDTO> cars = csCarService.getCarWithSqlStr(new Query(params));
                while (CollectionUtil.isNotEmpty(cars)) {
                    //3、删除原先的车辆标签命中项
                    csHitTagRelativeService.deleteOldHit(cars,dto.getTagId());

                    //4、添加符合条件的车辆标签数据
                    csHitTagRelativeService.insertHitTagRelative(cars, dto.getTagId());

                    //5、循环处理剩下的数据
                    params.put("page", ++page);
                    params.put("limit", finalLimit);
                    cars.clear();
                    if (page >= finalEndPage){
                        break;
                    }
                    cars = csCarService.getCarWithSqlStr(new Query(params));
                }
            });
        }
        log.info("CsMarkedCarTagServiceImpl markCarTag end");
        return Boolean.TRUE;
    }
}
