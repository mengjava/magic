package com.haoqi.magic.system.mapper;

import com.haoqi.magic.system.model.dto.SysRegionDTO;
import com.haoqi.magic.system.model.entity.SysRegion;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 大区管理表 Mapper 接口
 * </p>
 *
 * @author mengyao
 * @since 2019-04-28
 */
public interface SysRegionMapper extends BaseMapper<SysRegion> {
	/**
	 * 功能描述: 根据条件获取所有的大区
	 * @return java.util.List<com.haoqi.magic.system.model.dto.SysRegionDTO>
	 * @auther mengyao
	 * @date 2019/4/28 0028 下午 5:02
	 */
	List<SysRegionDTO> selectAllRegionName();
}
