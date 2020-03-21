package com.haoqi.magic.business.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.ConfigNameEnum;
import com.haoqi.magic.business.enums.ConfigTypeEnum;
import com.haoqi.magic.business.enums.ServiceItemTypeEnum;
import com.haoqi.magic.business.enums.VipTypeEnum;
import com.haoqi.magic.business.model.dto.CsVipDTO;
import com.haoqi.magic.business.model.dto.CsVipRecordDetailDTO;
import com.haoqi.magic.business.model.dto.SysConfigDTO;
import com.haoqi.magic.business.model.entity.CsUserVip;
import com.haoqi.magic.business.model.entity.CsVip;
import com.haoqi.magic.business.model.entity.CsVipRecordDetail;
import com.haoqi.magic.business.mapper.CsVipRecordDetailMapper;
import com.haoqi.magic.business.model.vo.CsUserVipVO;
import com.haoqi.magic.business.service.ICsUserVipService;
import com.haoqi.magic.business.service.ICsVipRecordDetailService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户会员查询记录表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@Service
public class CsVipRecordDetailServiceImpl extends ServiceImpl<CsVipRecordDetailMapper, CsVipRecordDetail> implements ICsVipRecordDetailService {
	@Autowired
	private ICsUserVipService csUserVipService;

	@Autowired
	private SystemServiceClient systemServiceClient;

	@Autowired
	private ICsVipService csVipService;
	@Autowired
	private CsVipRecordDetailMapper csVipRecordDetailMapper;


	@Override
	public Boolean insertDetail(Integer serviceItemType, Long userId, String userName, String vin, String result,String order_no ) {
		CsVipRecordDetail csVipRecordDetail = new CsVipRecordDetail();
		csVipRecordDetail.setServiceItemName(ServiceItemTypeEnum.getNameByKey(serviceItemType));
		CsUserVip csUserVip = csUserVipService.selectOne(new EntityWrapper<CsUserVip>().eq("sys_user_id", userId));
		CsUserVipVO csUserVipVO = null;
		if (Objects.nonNull(csUserVip)) {
			csUserVipVO =  BeanUtils.beanCopy(csUserVip, CsUserVipVO.class);
		} else {
			CsVip csVip = csVipService.selectOne(new EntityWrapper<CsVip>().eq("type", VipTypeEnum.NON_VIP.getKey()));
			csUserVipVO = BeanUtils.beanCopy(csVip, CsUserVipVO.class);
		}


		Integer  configName = new Integer(0);
		switch (serviceItemType) {
			case 1:
				csVipRecordDetail.setMoney(csUserVipVO.getMaintenancePrice());
				configName = ConfigNameEnum.INSURANCE_COST.getKey();
				break;
			case 2:
				csVipRecordDetail.setMoney(csUserVipVO.getEmissionPrice());
				configName = ConfigNameEnum.EMISSION_COST.getKey();
				break;
			case 3:
				csVipRecordDetail.setMoney(csUserVipVO.getInsurancePrice());
				configName = ConfigNameEnum.INSURANCE_COST.getKey();
				break;
			case 4:
				csVipRecordDetail.setMoney(csUserVipVO.getCarModelPrice());
				configName = ConfigNameEnum.IDENTIFY_COST.getKey();
				break;
			case 5:
				csVipRecordDetail.setMoney(csUserVipVO.getEvaluatePrice());
				configName = ConfigNameEnum.VALUATION_COST.getKey();
				break;
			default:
				break;
		}
		csVipRecordDetail.setUsername(userName);
		csVipRecordDetail.setStatus(StringUtils.isEmpty(result) ? Constants.NO.intValue():Constants.YES.intValue());
		csVipRecordDetail.setResult(result);
		csVipRecordDetail.setVin(vin);
		csVipRecordDetail.setSysUserId(userId);
		csVipRecordDetail.setOuterNo(order_no);
		Result<SysConfigDTO> byTypeAndName = systemServiceClient.getByTypeAndName(ConfigTypeEnum.CAR_INFORMATION.getKey(), configName);
		csVipRecordDetail.setCostPrice(BigDecimal.valueOf(Long.valueOf(byTypeAndName.getData().getGlobalValue())));
		this.insert(csVipRecordDetail);
		return  Boolean.TRUE;
	}

	@Override
	public Page findByPage(Query query) {
		List<CsVipRecordDetailDTO> csVipRecordDetailDTO = csVipRecordDetailMapper.selectByPage(query, query.getCondition());
		return query.setRecords(csVipRecordDetailDTO);
	}


}
