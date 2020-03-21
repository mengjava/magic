package com.haoqi.magic.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.mapper.CarHomePageMapper;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.CsTag;
import com.haoqi.magic.business.model.vo.CsTagVO;
import com.haoqi.magic.business.model.vo.HomePageBaseVO;
import com.haoqi.magic.business.model.vo.HomePageVO;
import com.haoqi.magic.business.service.ICarHomePageService;
import com.haoqi.magic.business.service.ICsHitTagRelativeService;
import com.haoqi.magic.business.service.ICsTagService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.constants.ConstantsDictClass;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yanhao on 2019/5/7.
 */
@Service
public class CarHomePageServiceImpl implements ICarHomePageService {

    @Autowired
    private CarHomePageMapper homePageMapper;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Autowired
    private ICsTagService csTagService;

    @Autowired
    private ICsHitTagRelativeService csHitTagRelativeService;

    @Override
    //@Cached(name = "car:IndexByParam", key = "#param.getLocate()+'|'+#param.getCreditUnion()+'|'+#param.getTagId()", cacheType = CacheType.BOTH, expire = 7200)
    public List<HomePageDTO> selectIndexByParam(HomePageBaseVO param) {
        setLocate(param);
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        Query query = new Query(params);
        if (Objects.nonNull(param.getCreditUnion()) && Constants.YES.equals(param.getCreditUnion())) {
            query.setSize(5);
            List<HomePageDTO> list = selectCreditUnion(query);
            return list;
        } else if (Objects.nonNull(param.getTagId())) {
            String tags = csTagService.getCsSqlStrByTagId(param.getTagId());
            if (StringUtils.isNotEmpty(tags)) {
                query.getCondition().put("tagStr", tags);
            }
            // 今日推荐排序
            //query.getCondition().put("orderByStr", " ORDER BY (cs_car_info.scan_base_num + cs_car_info.scan_num) DESC");
            query.getCondition().put("orderByStr", " ORDER BY cs_car_info.publish_time DESC");
        }
        List<HomePageDTO> list = selectCarBase(query);
        return list;
    }


    /***
     * 首页搜索
     * @param param
     * @return
     */
    @Override
    public Page selectIndexSearchParam(HomePageVO param) {
        setLocate(param);
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        Query query = new Query(params);
        setSearchQueryParam(param, query);
        List<HomePageDTO> list = selectCarBase(query);
        return query.setRecords(list);
    }

    /***
     * 组装搜索参数
     * @param param
     * @param query
     */
    private void setSearchQueryParam(HomePageVO param, Query query) {

        //1:买车 2:每日促销 3:一口价

        //最新发布:1 价格最高:2 价格最低:3 车龄最短:4 里程最短:5 诚信联盟:6

        //标签id
        if (Objects.nonNull(param.getTagId())) {
            //首页今日推荐标签 默认10条
            String tags = csTagService.getCsSqlStrByTagId(param.getTagId());
            if (StringUtils.isNotEmpty(tags)) {
                query.getCondition().put("tagStr", tags);
            }
        }
        //车龄(1) 级别 变速箱(1) 排量 里程(1) 驱动方式 颜色  排放标准 座位 配置

        //车龄范围(单选)(获取sql)
        if (StringUtils.isNotEmpty(param.getAge())) {
            try {
                Result<SysDictDTO> result = systemServiceClient.getDict(param.getAge());
                if (result.isSuccess() && Objects.nonNull(result.getData()) && StringUtils.isNotEmpty(result.getData().getRemark())) {
                    query.getCondition().put("carAgeStr", result.getData().getRemark());
                }
            } catch (Exception e) {
                //log
            }
        }
        //级别(车辆类型)多选
        if (StringUtils.isNotEmpty(param.getCarType())) {
            List<String> list = getList(param.getCarType());
            query.getCondition().put("carTypeList", list);
        }
        //变速箱(单选)
        if (StringUtils.isNotEmpty(param.getGearBox())) {
            query.getCondition().put("gearBox", param.getGearBox());
        }
        //排量(单选)(获取sql)
        if (StringUtils.isNotEmpty(param.getDisplacement())) {
            try {
                Result<SysDictDTO> result = systemServiceClient.getDict(param.getDisplacement());
                if (result.isSuccess() && Objects.nonNull(result.getData()) && StringUtils.isNotEmpty(result.getData().getRemark())) {
                    query.getCondition().put("carDisplacementStr", result.getData().getRemark());
                }
            } catch (Exception e) {
                //log
            }
        }
        //驱动方式(单选)
        if (StringUtils.isNotEmpty(param.getDriveMethod())) {
            query.getCondition().put("driveMethod", param.getDriveMethod());
        }
        //颜色(多选)
        if (StringUtils.isNotEmpty(param.getColor())) {
            List<String> list = getList(param.getColor());
            query.getCondition().put("carColorList", list);
        }
        //排放标准(多选)
        if (StringUtils.isNotEmpty(param.getEmissionStandard())) {
            List<String> list = getList(param.getEmissionStandard());
            query.getCondition().put("emissionStandardList", list);
        }
        //生产方式 (进口 国产)
        if (StringUtils.isNotEmpty(param.getImportType())) {
            List<String> list = getList(param.getImportType());
            List<Integer> importList = new ArrayList<>();
            list.forEach(e -> {
                importList.add(ConstantsDictClass.ImportTypeEnum.getValue(e));
            });
            query.getCondition().put("importTypeList", importList);
        }
        //配置

        //车辆年龄（主要是为了车辆定制中的[匹配车辆]）
        if (null != param.getAgeNum()) {
            query.getCondition().put("ageNum", param.getAgeNum());
        }

        //行驶里数（主要是为了车辆定制中的[行驶里数]）
        if (null != param.getTravelDistanceNum()) {
            query.getCondition().put("travelDistanceNum", param.getTravelDistanceNum());
        }

        //座位数
        if (StrUtil.isNotEmpty(param.getSeatNum())) {
            try {
                Result<SysDictDTO> result = systemServiceClient.getDict(param.getSeatNum());
                if (result.isSuccess() && Objects.nonNull(result.getData()) && StringUtils.isNotEmpty(result.getData().getRemark())) {
                    query.getCondition().put("seatNumStr", result.getData().getRemark());
                }
            } catch (Exception e) {
                //log
            }
        }

    }

    private List<String> getList(String param) {
        return StrUtil.splitTrim(param, StrUtil.COMMA);

    }

    @Override
    public List<SysAdvertDTO> selectAdvertByPositionCode(String code) {
        Result<List<SysAdvertDTO>> result = systemServiceClient.getAdvertByPositionCode(code);
        if (!result.isSuccess()) {
            return Lists.newArrayList();
        }
        return result.getData();
    }

    @Override
    public List<CsTagDTO> selectTodayTags() {
        CsTagVO vo = new CsTagVO();
        //1筛选里的标签/2详情/3今日推荐
        vo.setType(3);
        return getCsTagDTOS(vo);
    }

    @Override
    public List<CsTagDTO> selectFilterTags() {
        CsTagVO vo = new CsTagVO();
        //1筛选里的标签/2详情/3今日推荐
        vo.setType(1);
        return getCsTagDTOS(vo);
    }

    @Override
    public Map<String, List<SysCarBrandDTO>> selectMoreBrands() {
        Result<List<SysCarBrandDTO>> result = systemServiceClient.getCarBrandList();
        HashMap<String, List<SysCarBrandDTO>> map = Maps.newHashMap();
        if (result.isSuccess() && CollectionUtil.isNotEmpty(result.getData())) {
            List<SysCarBrandDTO> carBrandDTOS = result.getData();
            return carBrandDTOS.stream().collect(Collectors.groupingBy(SysCarBrandDTO::getBrandInitial));
        }
        return map;
    }

    @Override
    public Object getSelectTree() {
        Result<Object> selectTree = systemServiceClient.getSelectTree();
        return selectTree.getData();
    }

    @Override
    public Object selectCarModelListBySeriesId(Integer seriesId) {
        Result<List<Object>> carModelList = systemServiceClient.getCarModelList(seriesId);
        return carModelList.getData();
    }

    @Override
    public Object selectCarSeriesListByBrandId(Integer brandId) {
        Result<List<Object>> carSeriesList = systemServiceClient.getCarSeriesList(brandId);
        return carSeriesList.getData();
    }

    @Override
    public List<SysDictDTO> getDictByClass(String classType) {
        Result<List<SysDictDTO>> result = systemServiceClient.getDictByClass(classType);
        return result.getData();
    }

    @Override
    public List<SysCarBrandDTO> selectCarBrandList() {
        Result<List<SysCarBrandDTO>> carBrandList = systemServiceClient.getCarBrandList();
        return carBrandList.getData();
    }

    @Override
    @Cached(name = "home:selectBrandLetterList:", expire = 3000)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)
    public Object selectBrandLetterList() {
        Result<Object> brandList = systemServiceClient.getBrandList();
        if (brandList.isSuccess()) {
            return brandList.getData();
        }
        return "";
    }

    @Cached(name = "home:csTags:", key = "#vo.getType()", expire = 3000)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)
    private List<CsTagDTO> getCsTagDTOS(CsTagVO vo) {
        List<CsTag> csTags = csTagService.getCsTagWithCondition(vo);
        csTags.forEach(tag -> {
            if (StringUtils.isNotEmpty(tag.getFilePath())) {
                tag.setFilePath(URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), tag.getFilePath()));
            }
        });
        return BeanUtils.beansToList(csTags, CsTagDTO.class);
    }

    /***
     * 所有查询类的基类
     * @param query
     * @return
     */
    public List<HomePageDTO> selectCarBase(Query query) {
        List<HomePageDTO> list = homePageMapper.selectIndexPageParam(query, query.getCondition());
        setCarResult(list);
        return list;
    }

    /***
     * 首页诚信联盟
     * @param query
     * @return
     */
    public List<HomePageDTO> selectCreditUnion(Query query) {
        List<HomePageDTO> list = homePageMapper.selectCreditUnionPage(query, query.getCondition());
        setCarResult(list);
        return list;
    }

    private void setCarResult(List<HomePageDTO> list) {
        list.forEach(e -> {
            List<TagsDTO> tags = csHitTagRelativeService.getCsTagsByCarId(e.getId());
            e.setTagNames(tags);
            String url = URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), e.getIconFilePath());
            e.setIconFilePath(url);
        });
    }

    private void setLocate(HomePageBaseVO param) {
        String defaultLocate = "浙江省-杭州";
        if (StringUtils.isNotEmpty(param.getLocate())) {
            Result<SysProvinceAndCityDTO> result = systemServiceClient.getAreaByCityCode(param.getLocate());
            if (result.isSuccess() && Objects.nonNull(result.getData())) {
                SysProvinceAndCityDTO data = result.getData();
                if (Objects.nonNull(data.getProvinceName()) && Objects.nonNull(data.getCityName())) {
                    defaultLocate = String.format("%s-%s", data.getProvinceName(), data.getCityName());
                }
            }
        }
        param.setLocate(defaultLocate);
    }
}
