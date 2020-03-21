package com.haoqi.magic.system.mapper;

import com.haoqi.magic.system.model.dto.CsAppHotCityDTO;
import com.haoqi.magic.system.model.dto.SysAreaDTO;
import com.haoqi.magic.system.model.dto.SysCityDTO;
import com.haoqi.magic.system.model.entity.SysArea;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 城市区域表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
public interface SysAreaMapper extends BaseMapper<SysArea> {


    /**
     * 根据省code获取城市
     *
     * @param provinceCode
     * @return
     */
    List<SysCityDTO> getCityByProvinceCode(String provinceCode);

    /**
     * 功能描述:根据条件获取省份和省份code
     *
     * @param maps
     * @return java.util.List<com.haoqi.magic.system.model.dto.SysAreaDTO>
     * @auther mengyao
     * @date 2019/5/15 0015 上午 10:57
     */
    List<SysAreaDTO> selectProvinceByMap(Map maps);

    /**
     * 设置大区时,同步到area表
     *
     * @param mpas
     * @return
     */
    Boolean updateRegionIdByProvinceCode(Map<String, Object> mpas);

    /**
     * 设置大区时,清除区域表旧的大区id
     *
     * @param id
     * @return
     */
    Boolean updateRegionIdNullByRegionId(Long id);

    /**
     * 热门承受
     *
     * @return
     */
    List<CsAppHotCityDTO> selectHotCity();

    /**
     * 获取所有的城市(首字母)
     *
     * @return
     */
    List<CsAppHotCityDTO> selectAllCity();
}
