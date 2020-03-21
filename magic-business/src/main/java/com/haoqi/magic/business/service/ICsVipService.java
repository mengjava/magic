package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsVipDTO;
import com.haoqi.magic.business.model.entity.CsVip;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * <p>
 * 会员配置表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-03
 */
public interface ICsVipService extends IService<CsVip> {

	/**
	 * 功能描述: 获取会员配置分页列表
	 * @param query
	 * @return com.baomidou.mybatisplus.plugins.Page
	 * @auther mengyao
	 * @date 2019/12/3 0003 下午 3:05
	 */
	Page findByPage(Query query);

	/**
	 * 功能描述:添加会员
	 * @param dto
	 * @return boolean
	 * @auther mengyao
	 * @date 2019/12/3 0003 下午 4:05
	 */
	Boolean add(CsVipDTO dto);

	/**
	 * 功能描述: 会员修改
	 * @param dto
	 * @return boolean
	 * @auther mengyao
	 * @date 2019/12/3 0003 下午 4:14
	 */
	Boolean edit(CsVipDTO dto);

	/**
	 * 功能描述: 启用禁用
	 * @param id
	 * @return void
	 * @auther mengyao
	 * @date 2019/12/3 0003 下午 4:22
	 */
	void updateEnabledById(Long id);

	/**
	 * 功能描述: 会员开启关闭
	 * @param id
	 * @param isShow
	 * @return void
	 * @auther mengyao
	 * @date 2019/12/4 0004 下午 4:10
	 */
	void setIsShow(Long id, Integer isShow);

	/**
	 * 功能描述: 删除vip
	 * @param ids
	 * @return void
	 * @auther mengyao
	 * @date 2019/12/5 0005 上午 10:09
	 */
	void deleteVip(Long[] ids);

	
	/**
	 * 功能描述: 获取会员列表
	 * @param id
	 * @return java.util.List<com.haoqi.magic.business.model.dto.CsVipDTO>
	 * @auther mengyao
	 * @date 2019/12/30 0030 上午 10:09
	 */
	List<CsVipDTO> getVipTypeList(Long id);
}
