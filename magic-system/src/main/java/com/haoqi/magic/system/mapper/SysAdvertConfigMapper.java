package com.haoqi.magic.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.system.model.dto.SysAdvertDTO;
import com.haoqi.magic.system.model.entity.SysAdvertConfig;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广告配置表 Mapper 接口
 * </p>
 *
 * @author mengyao
 * @since 2019-04-25
 */
public interface SysAdvertConfigMapper extends BaseMapper<SysAdvertConfig> {
	/**
	 * 功能描述: 获取广告列表
	 * @param query
	 * @param condition
	 * @return java.util.List<com.haoqi.magic.system.model.dto.SysAdvertDTO>
	 * @auther mengyao
	 * @date 2019/4/25 0025 下午 3:14
	 */
	List<SysAdvertDTO> findAdvertByPage(Query query, Map condition);
}
