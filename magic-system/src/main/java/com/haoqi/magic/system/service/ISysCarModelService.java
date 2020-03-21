package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.entity.SysCarModel;
import com.haoqi.magic.system.model.vo.CarModelVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车型表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface ISysCarModelService extends IService<SysCarModel> {
    /***
     * 车型分页列表
     * @param params
     * @return
     */
    Page selectCarModelPage(Map<String, Object> params);

    /***
     * 通过车型id获取车型信息
     * @param modelId
     * @return
     */
    SysCarModel selectCarModelByModelId(Long modelId);

    /***
     * 通过车系id获取所有的车型
     * @param seriesId
     * @return
     */
    List<SysCarModel> selectCarModelListBySeriesId(Integer seriesId);

    /***
     * 设置是否有效
     * @param id
     * @param isDeleted
     * @return
     */
    Boolean setCarModelValid(Long id, Integer isDeleted);
    
    Boolean updateCarModel(CarModelVO vo);

    Boolean addCarModel(CarModelVO vo);
}
