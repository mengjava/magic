package com.haoqi.magic.business.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.mapper.CsServiceFeeMapper;
import com.haoqi.magic.business.model.dto.CsServiceFeeDTO;
import com.haoqi.magic.business.model.dto.CsServiceFeeListDTO;
import com.haoqi.magic.business.model.dto.ServiceFreeDTO;
import com.haoqi.magic.business.model.entity.CsServiceFee;
import com.haoqi.magic.business.service.ICsServiceFeeService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 服务费设置表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-18
 */
@Service
public class CsServiceFeeServiceImpl extends ServiceImpl<CsServiceFeeMapper, CsServiceFee> implements ICsServiceFeeService {
	@Autowired
	private CsServiceFeeMapper csServiceFeeMapper;

	@Override
	public Page findByPage(Query query) {
		List<CsServiceFeeDTO> csServiceFeeDTO = csServiceFeeMapper.selectByPage(query, query.getCondition());
		return query.setRecords(csServiceFeeDTO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean add(CsServiceFeeListDTO listDTO) {
		// 1 查询添加信息,判断是否已经存在
		Integer count = this.selectCount(new EntityWrapper<CsServiceFee>().eq("sys_area_id", listDTO.getSysAreaId()));
		// 2 如果已经存在,返回
		if (count > CommonConstant.STATUS_NORMAL) {
			throw new RiggerException("此城市服务费已设置,请进行编辑");
		}
		listDTO.getList().forEach(m ->{
			m.setSysAreaId(listDTO.getSysAreaId());
			this.insert(BeanUtils.beanCopy(m, CsServiceFee.class));
		});
		return Boolean.TRUE;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteServiceFee(Long[] sysAreaIds) {
		for (Long sysAreaId : sysAreaIds) {
			Assert.notNull(sysAreaId, "城市id不能为空");
			this.delete(new EntityWrapper<CsServiceFee>().eq("sys_area_id",sysAreaId));
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean edit(CsServiceFeeListDTO listDTO) {
		this.delete(new EntityWrapper<CsServiceFee>().eq("sys_area_id",listDTO.getSysAreaId()));
		listDTO.getList().forEach(m ->{
			m.setSysAreaId(listDTO.getSysAreaId());
			if (Objects.nonNull(m.getId())){
				this.updateById(BeanUtils.beanCopy(m, CsServiceFee.class));
			}
			this.insert(BeanUtils.beanCopy(m, CsServiceFee.class));
		});
		return Boolean.TRUE;
	}

	@Override
	public CsServiceFee getByAreaIdAndPrice(Long areaId, BigDecimal price) {
		CsServiceFee serviceFee = this.selectOne(new EntityWrapper<CsServiceFee>()
				.eq("sys_area_id", areaId)
				.le("min_money", price)
				.ge("max_money", price)
				.eq("is_deleted", CommonConstant.STATUS_NORMAL));
		return Optional.ofNullable(serviceFee).orElseThrow(() -> new RiggerException("服务费信息不存在"));
	}

    @Override
    public BigDecimal getServiceFree(Long areaId, BigDecimal price) {
        CsServiceFee serviceFee = this.getByAreaIdAndPrice(areaId, price);
        //1：单笔/0服务费比例，默认0
        if (CommonConstant.STATUS_DEL.equals(serviceFee.getType())) {
            return serviceFee.getFeeMoney();
        }
        BigDecimal decimal = NumberUtil.mul(price, NumberUtil.div(serviceFee.getFeeRatio(), new BigDecimal(100), 2));
        BigDecimal z0 = decimal.multiply(BigDecimal.TEN).divideAndRemainder(BigDecimal.TEN)[0];
        BigDecimal z1 = decimal.multiply(BigDecimal.TEN).divideAndRemainder(BigDecimal.TEN)[1];
        if (z1.compareTo(BigDecimal.ZERO) > 0) {
            z0 = z0.add(BigDecimal.ONE);
        }
        return z0;
    }

    @Override
    public ServiceFreeDTO getCarServiceFree(Long areaId, BigDecimal price) {
        ServiceFreeDTO serviceFreeDTO = new ServiceFreeDTO();
        CsServiceFee serviceFee = this.getByAreaIdAndPrice(areaId, price);
        serviceFreeDTO.setServiceType(serviceFee.getType());
        //1：单笔/0服务费比例，默认0
        if (CommonConstant.STATUS_DEL.equals(serviceFee.getType())) {
            serviceFreeDTO.setServiceFree(serviceFee.getFeeMoney());
            return serviceFreeDTO;
        }
        serviceFreeDTO.setServiceFree(serviceFee.getFeeRatio());
        return serviceFreeDTO;
    }
}
