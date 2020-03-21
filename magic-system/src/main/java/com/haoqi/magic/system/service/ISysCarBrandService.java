package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.entity.SysCarBrand;
import com.haoqi.magic.system.model.vo.CarBrandVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface ISysCarBrandService extends IService<SysCarBrand> {
    /**
     * 分页查询品牌
     * @param params
     * @return
     */
    Page selectCarBrandPage(Map<String, Object> params);

    /***
     * 获取所有车辆品牌
     * @return
     */
    List<SysCarBrand> selectCarBrandList();


    /***
     * 添加新的一级品牌
     * @param vo
     * @return
     */
    Boolean addCarBrand(CarBrandVO vo);

    /**
     * 有效
     * @param id
     * @return
     */
    Boolean setCarBrandValid(Long id,Integer isDeleted);

    /***
     * 更新车品牌
     * @param vo
     * @return
     */
    Boolean updateCarBrand(CarBrandVO vo);
}
