package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.SysRegionDTO;
import com.haoqi.magic.system.model.entity.SysRegion;
import com.haoqi.magic.system.model.vo.SysRegionVO;

import java.util.List;

/**
 * <p>
 * 大区管理表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-04-28
 */
public interface ISysRegionService extends IService<SysRegion> {
	/**
	 * 功能描述: 获取大区列表
	 * @param
	 * @return java.util.List<com.haoqi.magic.system.model.entity.SysRegion>
	 * @auther mengyao
	 * @date 2019/4/28 0028 上午 9:39
	 */
	List<SysRegionDTO> getRegionList();


	/**
	 * 功能描述: 插入大区
	 * @param sysRegionVO
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/4/28 0028 下午 4:36
	 */
	Boolean insertRegion(SysRegionVO sysRegionVO);

	/**
	 * 功能描述:删除大区
	 * @param id
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/4/29 0029 上午 10:43
	 */
	Boolean deleteRegion(Long id);

	/**
	 * 功能描述: 修改大区
	 * @param sysRegionVO
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/4/29 0029 上午 10:44
	 */
	Boolean updateRegion(SysRegionVO sysRegionVO);


}
