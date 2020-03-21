package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.system.model.dto.SysAdvertDTO;
import com.haoqi.magic.system.model.entity.SysAdvertConfig;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * <p>
 * 广告配置表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-04-25
 */
public interface ISysAdvertConfigService extends IService<SysAdvertConfig> {
	/**
	 * 功能描述: 分页获取广告消息
	 * @param query
	 * @return com.baomidou.mybatisplus.plugins.Page
	 * @auther mengyao
	 * @date 2019/4/25 0025 下午 12:03
	 */
	Page findAdvertByPage(Query query);

	/***
	 * 功能描述:添加广告
	 * @param advertConfig
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/4/25 0025 下午 2:31
	 */
	Boolean insertAdvert(SysAdvertConfig advertConfig);

	/**
	 * 功能描述: 通过广告标题，判断是否已存在
	 * @param title
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/4/25 0025 下午 3:17
	 */
	Boolean isExist(String title);

	/**
	 * 功能描述: 更新广告
	 * @param advertConfig
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/4/25 0025 下午 3:17
	 */
	Boolean updateAdvert(SysAdvertConfig advertConfig);


	/**
	 * 功能描述: 通过id，获取广告信息
	 * @param id
	 * @return com.haoqi.magic.system.model.dto.SysAdvertDTO
	 * @auther mengyao
	 * @date 2019/4/25 0025 下午 3:20
	 */

	SysAdvertDTO getAdvertById(Long id);

	/**
	 * 功能描述: 通过广告id，更新广告
	 * @param id
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/4/25 0025 下午 4:06
	 */
	Boolean updateStatusById(Long id) ;

	/**
	 * 功能描述: 通过id，删除广告
	 * @param id
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/4/25 0025 下午 4:14
	 */
	Boolean deleteAdvert(Long id);

	/***
	 * 通过投放位置获取广告信息
	 * @param code
	 * @return
	 */
    List<SysAdvertDTO> getAdvertByPositionCode(String code);
}
