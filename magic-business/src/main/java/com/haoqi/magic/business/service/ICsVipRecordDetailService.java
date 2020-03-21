package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.entity.CsVipRecordDetail;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.rigger.mybatis.Query;

/**
 * <p>
 * 用户会员查询记录表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
public interface ICsVipRecordDetailService extends IService<CsVipRecordDetail> {


	/**
	 * 功能描述: 插入会员查询记录
	 * @param serviceItemType
	 * @param userId
	 * @param userName
	 * @param vin
	 * @param result
	 * @return java.lang.Boolean
	 * @auther mengyao
	 * @date 2019/12/5 0005 下午 2:27
	 */
	 Boolean insertDetail(Integer serviceItemType,Long userId,String userName,String vin,String result,String outerNo);

	 /**
	  * 功能描述:会员车况查询历史记录列表
	  * @param query
	  * @return com.baomidou.mybatisplus.plugins.Page
	  * @auther mengyao
	  * @date 2020-02-24 15:59
	  */
	Page findByPage(Query query);
}
