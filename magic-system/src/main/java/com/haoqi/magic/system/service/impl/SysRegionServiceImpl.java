package com.haoqi.magic.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.haoqi.magic.system.common.enums.RegionLevelTypeEnum;
import com.haoqi.magic.system.mapper.SysAreaMapper;
import com.haoqi.magic.system.mapper.SysRegionMapper;
import com.haoqi.magic.system.model.dto.SysAreaDTO;
import com.haoqi.magic.system.model.dto.SysRegionDTO;
import com.haoqi.magic.system.model.entity.SysRegion;
import com.haoqi.magic.system.model.vo.SysRegionVO;
import com.haoqi.magic.system.service.ISysAreaService;
import com.haoqi.magic.system.service.ISysRegionService;
import com.haoqi.rigger.common.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 大区管理表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-04-28
 */
@Service
public class SysRegionServiceImpl extends ServiceImpl<SysRegionMapper, SysRegion> implements ISysRegionService {

	@Autowired
	private SysRegionMapper sysRegionMapper;

	@Autowired
	private SysAreaMapper sysAreaMapper;
	@Autowired
	private ISysAreaService sysAreaService;


	@Override
	public List<SysRegionDTO> getRegionList() {
		//获取所有大区
		List<SysRegionDTO> list = sysRegionMapper.selectAllRegionName();
		//设置每个大区下的省份
		list.forEach(m -> {
			Map maps = Maps.newHashMap();
			maps.put("sysRegionId", m.getId());
			maps.put("level", RegionLevelTypeEnum.PROVINCE.getKey());
			List<SysAreaDTO> sysAreaDTOList = sysAreaService.getSysAreaDTOS(maps);
			m.setAreaList(sysAreaDTOList);
		});
		return list;
	}


	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean insertRegion(SysRegionVO sysRegionVO) {
		SysRegion sysRegion = BeanUtils.beanCopy(sysRegionVO, SysRegion.class);
		this.insert(sysRegion);
		//获取大区id
		SysRegion region = this.selectOne(new EntityWrapper<SysRegion>().eq("name", sysRegion.getName()));
		//修改省份所属大区
		Map<String,Object> param = Maps.newHashMap();
		param.put("provinceCode",sysRegionVO.getProvinceCode());
		param.put("regionId",sysRegion.getId());
		sysAreaMapper.updateRegionIdByProvinceCode(param);
		return Boolean.TRUE;
	}


	@Override
	public Boolean deleteRegion(Long id) {
		Assert.notNull(id, "大区id不能为空");
		return this.deleteById(id);
	}

	@Override
	@Transactional
	public Boolean updateRegion(SysRegionVO sysRegionVO) {
		SysRegion sysRegion = BeanUtils.beanCopy(sysRegionVO, SysRegion.class);
		this.updateById(sysRegion);
		//先清除原来大区下的省份
		sysAreaMapper.updateRegionIdNullByRegionId(sysRegion.getId());
		//再添加大区下新的省份
		Map<String,Object> param = Maps.newHashMap();
		param.put("provinceCode",sysRegionVO.getProvinceCode());
		param.put("regionId",sysRegion.getId());
		return sysAreaMapper.updateRegionIdByProvinceCode(param);
	}


}
