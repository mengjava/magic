package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsServiceFeeDTO;
import com.haoqi.magic.business.model.entity.CsServiceFee;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务费设置表 Mapper 接口
 * </p>
 *
 * @author mengyao
 * @since 2019-12-18
 */
public interface CsServiceFeeMapper extends BaseMapper<CsServiceFee> {

	/**
	 * 功能描述: 服务费列表
	 * @param query
	 * @param condition
	 * @return java.util.List<com.haoqi.magic.business.model.dto.CsServiceFeeDTO>
	 * @auther mengyao
	 * @date 2019/12/18 0018 上午 11:43
	 */
	List<CsServiceFeeDTO> selectByPage(Query query, Map condition);
}
