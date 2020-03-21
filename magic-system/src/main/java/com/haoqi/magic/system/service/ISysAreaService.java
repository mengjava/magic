package com.haoqi.magic.system.service;

import com.haoqi.magic.system.model.dto.CsAppHotCityDTO;
import com.haoqi.magic.system.model.dto.SysAreaDTO;
import com.haoqi.magic.system.model.dto.SysProvinceAndCityDTO;
import com.haoqi.magic.system.model.dto.SysProvinceListDTO;
import com.haoqi.magic.system.model.entity.SysArea;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 城市区域表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface ISysAreaService extends IService<SysArea> {

    /**
     * 功能描述: 获取区域列表
     *
     * @param maps
     * @return java.util.List<com.haoqi.magic.system.model.dto.SysAreaDTO>
     * @auther mengyao
     * @date 2019/4/29 0029 上午 10:30
     */
    List<SysAreaDTO> getSysAreaDTOS(Map maps);

    /**
     * 功能描述:获取省和市code
     *
     * @param id
     * @return com.haoqi.magic.system.model.dto.SysProvinceAndCityDTO
     * @auther mengyao
     * @date 2019/5/6 0006 下午 5:22
     */
    SysProvinceAndCityDTO getAreaById(Long id);

    /**
     * 功能描述:获取省份城市二级列表
     *
     * @param
     * @return java.util.List<com.haoqi.magic.system.model.dto.SysProvinceListDTO>
     * @auther mengyao
     * @date 2019/5/5 0005 下午 6:45
     */
    List<SysProvinceListDTO> findProvinceAndCityList();

    /***
     * 通过城市code获取城市信息
     * @param code
     * @return
     */
    SysProvinceAndCityDTO getAreaByCityCode(String code);

    /**
     * 通过Id获取城市信息
     *
     * @param id 地区ID
     * @return
     * @author huming
     * @date 2019/5/30 16:19
     */
    SysProvinceAndCityDTO getAreaByCityId(Long id);

    /**
     * 热门城市
     *
     * @return
     */
    List<CsAppHotCityDTO> getHotCity();

    /**
     * 获取所有城市(首字母排序)
     *
     * @return
     */
    List<CsAppHotCityDTO> getAllCity();

    /**
     * 生成首字母
     * @return
     */
    Boolean makeInitial();
}
