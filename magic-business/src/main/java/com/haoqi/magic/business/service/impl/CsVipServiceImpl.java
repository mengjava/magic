package com.haoqi.magic.business.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.enums.VipTypeEnum;
import com.haoqi.magic.business.mapper.CsVipMapper;
import com.haoqi.magic.business.model.dto.CsVipDTO;
import com.haoqi.magic.business.model.entity.CsUserVip;
import com.haoqi.magic.business.model.entity.CsVip;
import com.haoqi.magic.business.service.ICsUserVipService;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import org.apache.poi.ss.formula.functions.Now;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 会员配置表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-03
 */
@Service
public class CsVipServiceImpl extends ServiceImpl<CsVipMapper, CsVip> implements ICsVipService {
	@Autowired
	private CsVipMapper csVipMapper;
	@Autowired
	private ICsUserVipService csUserVipService;

	@Override
	public Page findByPage(Query query) {
		List<CsVipDTO> csVipDTO = csVipMapper.selectByPage(query, query.getCondition());
		return query.setRecords(csVipDTO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean add(CsVipDTO dto) {
		// 1 查询添加信息,判断是否已经存在
		Integer count = this.selectCount(new EntityWrapper<CsVip>().eq("name", dto.getName()));
		// 2 如果已经存在,返回
		if (count > CommonConstant.STATUS_NORMAL.intValue()) {
			throw new RiggerException("此会员已存在");
		}
		return this.insert(BeanUtils.beanCopy(dto, CsVip.class));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean edit(CsVipDTO dto) {
		this.updateById(BeanUtils.beanCopy(dto, CsVip.class));
		// 1 查询添加信息,判断是否已经存在
		Integer count = this.selectCount(new EntityWrapper<CsVip>().eq("name", dto.getName()));
		// 2 如果已经存在,返回
		if (count > CommonConstant.BUTTON.intValue()) {
			throw new RiggerException("此会员名称已存在");
		}
		return true;
	}

	@Override
	public void updateEnabledById(Long id) {
		Assert.notNull(id, "会员Id不能为空");
		CsVip csVip = this.selectById(id);
		Optional<CsVip> optional = Optional.of(csVip);
		optional.ifPresent(m -> {
			Integer isDeleted = CommonConstant.STATUS_NORMAL.equals(csVip.getIsDeleted()) ?
					CommonConstant.STATUS_DEL : CommonConstant.STATUS_NORMAL;
			m.setIsDeleted(isDeleted);
			this.updateById(m);
		});
	}

	@Override
	public void setIsShow(Long id, Integer isShow) {
		CsUserVip csUserVip = new CsUserVip();
		csUserVip.setId(id);
		csUserVip.setIsDeleted(CommonConstant.STATUS_DEL);
		csUserVipService.updateById(csUserVip);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteVip(Long[] ids) {
		for (Long id : ids) {
			Assert.notNull(id, "会员id不能为空");
			this.deleteById(id);
		}
	}

	@Override
	public List<CsVipDTO> getVipTypeList(Long id) {
		CsUserVip csUserVip = csUserVipService.selectOne(new EntityWrapper<CsUserVip>().eq("sys_user_id", id).ge("expired_date",new Date()));
		CsUserVip isVip = csUserVipService.selectOne(new EntityWrapper<CsUserVip>().eq("sys_user_id", id));
		List<CsVip> csVip = this.selectList(new EntityWrapper<CsVip>().eq("is_deleted", CommonConstant.STATUS_NORMAL).ne("type",VipTypeEnum.NON_VIP.getKey()).orderAsc(Arrays.asList("type,period")));
		CsUserVip noVip = csUserVipService.selectNoVipByUserId(id);
		// 如果是非会员  或者会员过期了 需要展示
		if (Objects.isNull(csUserVip)){
			csVip.forEach(m ->{
				m.setIsShow(CommonConstant.STATUS_DEL);
				//曾经是会员 体验会员不展示
				if (m.getType().equals(VipTypeEnum.EXPERIENCE_VIP.getKey()) && !Objects.isNull(isVip)){
					m.setIsShow(CommonConstant.STATUS_NORMAL);
				}
			});


		} else {
			// 如果当前是充值会员 则所有都不展示
			Iterator<CsVip> it = csVip.iterator();
			while(it.hasNext()) {
				CsVip m = it.next();
				m.setIsShow(CommonConstant.STATUS_NORMAL);
				//如果当前是体验会员  则需要展示充值会员
				if (!Objects.isNull(noVip) && !m.getType().equals(VipTypeEnum.EXPERIENCE_VIP.getKey()) ){
					m.setIsShow(CommonConstant.STATUS_DEL);
				}
			}

		}
		return 	BeanUtils.beansToList(csVip, CsVipDTO.class);
	}
}