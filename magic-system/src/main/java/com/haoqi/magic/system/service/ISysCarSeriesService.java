package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.system.model.entity.SysCarSeries;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.vo.CarSeriesVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车系表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface ISysCarSeriesService extends IService<SysCarSeries> {
    /***
     * 分页车系管理
     * @param params
     * @return
     */
    Page selectCarSeriesPage(Map<String, Object> params);

    /***
     * 通过品牌id获取车信息
     * @param brandId
     * @return
     */
    List<SysCarSeries> selectCarSeriesListByBrandId(Integer brandId);

    /***
     *
     * @param id
     * @param isDeleted
     * @return
     */
    Boolean setCarSeriesValid(Long id, Integer isDeleted);

    /***
     * 添加
     * @param vo
     * @return
     */
    Boolean addCarSeries(CarSeriesVO vo);

    /***
     * 更新
     * @param vo
     * @return
     */
    Boolean updateCarSeries(CarSeriesVO vo);
}
