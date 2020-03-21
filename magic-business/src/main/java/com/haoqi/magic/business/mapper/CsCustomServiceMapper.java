package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsCustomServiceDTO;
import com.haoqi.magic.business.model.entity.CsCustomService;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户服务（咨询）表 Mapper 接口
 * </p>
 *
 * @author mengyao
 * @since 2019-05-14
 */
public interface CsCustomServiceMapper extends BaseMapper<CsCustomService> {

	/**
	 * 功能描述:客户意向表查询
	 * @param query
	 * @param condition
	 * @return java.util.List<com.haoqi.magic.business.model.dto.CsCustomServiceDTO>
	 * @auther mengyao
	 * @date 2019/5/17 0017 下午 2:15
	 */
	List<CsCustomServiceDTO> findByPage(Query query, Map condition);

	/**
	 * 功能描述:获取最后一个处理用户名称
	 * @param csCarDealerId
	 * @return com.haoqi.magic.business.model.dto.CsCustomServiceDTO
	 * @auther mengyao
	 * @date 2019/7/10 0010 上午 11:54
	 */
	CsCustomServiceDTO getLastProcessUserName(Long csCarDealerId);
}
