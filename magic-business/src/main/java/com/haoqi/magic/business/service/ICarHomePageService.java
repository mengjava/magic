package com.haoqi.magic.business.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsTagDTO;
import com.haoqi.magic.business.model.dto.HomePageDTO;
import com.haoqi.magic.business.model.dto.SysAdvertDTO;
import com.haoqi.magic.business.model.dto.SysCarBrandDTO;
import com.haoqi.magic.business.model.dto.SysDictDTO;
import com.haoqi.magic.business.model.vo.HomePageBaseVO;
import com.haoqi.magic.business.model.vo.HomePageVO;

import java.util.List;
import java.util.Map;

/**
 * Created by yanhao on 2019/5/7.
 */
public interface ICarHomePageService {
    /***
     * 首页查询 诚信联盟和今日推荐
     * @param param
     * @return
     */
    List<HomePageDTO> selectIndexByParam(HomePageBaseVO param);

    /***
     * 分页条件查询车辆信息列表
     * @param param
     * @return
     */
    Page selectIndexSearchParam(HomePageVO param);

    /***
     * 通过位置获取首页banner图
     * @param code
     * @return
     */
    List<SysAdvertDTO> selectAdvertByPositionCode(String code);

    /***
     * 获取今日推荐标签
     * @return
     */
    List<CsTagDTO> selectTodayTags();

    /***
     * 首页筛选的标签
     * @return
     */
    List<CsTagDTO> selectFilterTags();

    /**
     * 获取车辆品牌列表
     *
     * @return
     */
    Map<String, List<SysCarBrandDTO>> selectMoreBrands();

    /***
     * 获取首页全部筛选数据字典树
     * @return
     */
    Object getSelectTree();

    /**
     * 通过车系获取车型列表
     *
     * @param seriesId
     * @return
     */
    Object selectCarModelListBySeriesId(Integer seriesId);

    /***
     * 通过车品牌获取车系列表
     * @param brandId
     * @return
     */
    Object selectCarSeriesListByBrandId(Integer brandId);

    /***
     * 获取数据字典
     * @param classType
     * @return
     */
    List<SysDictDTO> getDictByClass(String classType);

    /***
     * 获取所有的车品牌列表
     * @return
     */
    List<SysCarBrandDTO> selectCarBrandList();

    /**
     * 获取品牌首字母分组
     *
     * @return
     */
    Object selectBrandLetterList();
}
