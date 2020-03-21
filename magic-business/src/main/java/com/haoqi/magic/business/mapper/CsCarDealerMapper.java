package com.haoqi.magic.business.mapper;

import com.haoqi.magic.business.model.dto.CsCarDealerDTO;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商家表 Mapper 接口
 * </p>
 *
 * @author mengyao
 * @since 2019-05-05
 */
public interface CsCarDealerMapper extends BaseMapper<CsCarDealer> {
	/**
	 * 功能描述: 商家审核分页列表
	 * @param query
	 * @param condition
	 * @return java.util.List<com.haoqi.magic.business.model.dto.CsCarDealerDTO>
	 * @auther mengyao
	 * @date 2019/5/7 0007 下午 3:34
	 */
	List<CsCarDealerDTO> findCarDealerByPage(Query query, Map condition);

	/**
	 * 获取全部车商信息
	 * @param params
	 * @return
	 * @author huming
	 * @date 2019/5/13 10:53
	 */
    List<CsCarDealerDTO> getAllCarDealer(Map<String, Object> params);

	/**
	 * 功能描述: 卖家分页列表
	 * @param query
	 * @param condition
	 * @return java.util.List<com.haoqi.magic.business.model.dto.CsCarDealerDTO>
	 * @auther mengyao
	 * @date 2019/12/25 0025 上午 11:41
	 */
	List<CsCarDealerDTO> findCarSellerByPage(Query query, Map condition);
}
