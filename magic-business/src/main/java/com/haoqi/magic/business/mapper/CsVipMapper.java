package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsVipDTO;
import com.haoqi.magic.business.model.entity.CsVip;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员配置表 Mapper 接口
 * </p>
 *
 * @author mengyao
 * @since 2019-12-03
 */
public interface CsVipMapper extends BaseMapper<CsVip> {

	/**
	 * 功能描述: 获取分页列表
	 * @param query
	 * @param condition
	 * @return java.util.List<com.haoqi.magic.business.model.dto.CsVipDTO>
	 * @auther mengyao
	 * @date 2019/12/3 0003 下午 3:07
	 */
	List<CsVipDTO> selectByPage(Query query, Map condition);
}
