package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsCustomServiceDTO;
import com.haoqi.magic.business.model.entity.CsCustomService;
import com.haoqi.magic.business.model.vo.CsCustomServiceVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.Optional;

/**
 * <p>
 * 客户服务（咨询）表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-05-14
 */
public interface ICsCustomServiceService extends IService<CsCustomService> {

	/**
	 * 功能描述: 客户意向列表
	 * @param query
	 * @return com.baomidou.mybatisplus.plugins.Page<com.haoqi.magic.business.model.dto.CsCustomServiceDTO>
	 * @auther mengyao
	 * @date 2019/5/17 0017 下午 2:11
	 */
	Page<CsCustomServiceDTO> findByPage(Query query);



	/**
	 * 功能描述: 新增意向
	 * @param csCustomService
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/6/3 0003 上午 10:50
	 */
	Boolean Insert(CsCustomService csCustomService);

	/***
	 * 功能描述: 意向处理
	 * @param csCustomServiceVO
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/6/13 0013 上午 11:48
	 */
	Boolean processCustom(CsCustomServiceVO csCustomServiceVO);

	/***
	 * 功能描述: 根据id获取处理信息
	 * @param id
	 * @return com.haoqi.magic.business.model.dto.CsCustomServiceDTO
	 * @auther mengyao
	 * @date 2019/6/13 0013 下午 3:19
	 */
	CsCustomServiceDTO selectDTOById(Long id);

	/***
	 * 功能描述: 获取最后一个处理用户名称
	 * @param csCarInfoId
	 * @return java.util.Optional<com.haoqi.magic.business.model.dto.CsCustomServiceDTO>
	 * @auther mengyao
	 * @date 2019/7/10 0010 上午 11:53
	 */
	Optional<CsCustomServiceDTO> getLastProcessUserName(Long csCarInfoId);
}
