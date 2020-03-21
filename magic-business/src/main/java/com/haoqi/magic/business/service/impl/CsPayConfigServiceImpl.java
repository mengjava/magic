package com.haoqi.magic.business.service.impl;

import cn.hutool.core.lang.Assert;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.business.client.PayCenterBusinessServiceClient;
import com.haoqi.magic.business.enums.PayMoneyTypeEnum;
import com.haoqi.magic.business.mapper.CsPayConfigMapper;
import com.haoqi.magic.business.model.dto.CsPayConfigDTO;
import com.haoqi.magic.business.model.dto.CsPayConfigListVO;
import com.haoqi.magic.business.model.entity.CsPayConfig;
import com.haoqi.magic.business.model.vo.CsPayConfigVO;
import com.haoqi.magic.business.service.ICsPayConfigService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.RiggerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 支付配置 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-12-12
 */
@Service
public class CsPayConfigServiceImpl extends ServiceImpl<CsPayConfigMapper, CsPayConfig> implements ICsPayConfigService {

    @Value("${appKey}")
    private String appKey;
    @Value("${appSecret}")
    private String appSecret;
    @Autowired
    private PayCenterBusinessServiceClient payCenterBusinessServiceClient;

    @Override
    public List<CsPayConfigListVO> payConfigList() {


        List<CsPayConfigListVO> list = new ArrayList<>(16);
        Result<List<Map<String, String>>> payWays = payCenterBusinessServiceClient.getPayWays(appKey, appSecret);
        List<Map<String, String>> data = payWays.getData();
        if (Objects.isNull(data)) {
            return list;
        }
        List<CsPayConfig> payConfigList = new ArrayList<>();
        List<CsPayConfig> inComeList = new ArrayList<>();
        for (Map<String, String> datum : data) {
            CsPayConfig payConfig = new CsPayConfig(datum.get("desc"), datum.get("code"), datum.get("logoPath"));
            CsPayConfig inCome = new CsPayConfig(datum.get("desc"), datum.get("code"), datum.get("logoPath"));
            CsPayConfig csPayConfigYes = this.selectOne(new EntityWrapper<CsPayConfig>().eq("product_code", datum.get("code")).eq("pay_type", Constants.YES));
            CsPayConfig csPayConfigNo = this.selectOne(new EntityWrapper<CsPayConfig>().eq("product_code", datum.get("code")).eq("pay_type", Constants.NO));
            if (Objects.nonNull(csPayConfigYes)) {
                payConfig.setId(csPayConfigYes.getId());
                payConfig.setIsDeleted(csPayConfigYes.getIsDeleted());
                payConfig.setShowName(csPayConfigYes.getShowName());
            }
            if (Objects.nonNull(csPayConfigNo)) {
                inCome.setId(csPayConfigNo.getId());
                inCome.setIsDeleted(csPayConfigNo.getIsDeleted());
                inCome.setShowName(csPayConfigNo.getShowName());
            }
            payConfigList.add(payConfig);
            inComeList.add(inCome);
        }
        List<CsPayConfigVO> csPayConfigVOs = BeanUtils.beansToList(payConfigList, CsPayConfigVO.class);
        List<CsPayConfigVO> inComeListVOs = BeanUtils.beansToList(inComeList, CsPayConfigVO.class);

        list.add(new CsPayConfigListVO(Constants.YES, csPayConfigVOs));
        list.add(new CsPayConfigListVO(Constants.NO, inComeListVOs));

        return list;
    }

    @Override
    public Boolean add(CsPayConfigDTO dto) {
        // 1 查询添加信息,判断是否已经存在
        Integer count = this.selectCount(new EntityWrapper<CsPayConfig>().eq("show_name", dto.getShowName()));
        // 2 如果已经存在,返回
        if (count > CommonConstant.STATUS_NORMAL.intValue()) {
            throw new RiggerException("此支付产品名称已存在");
        }
        return this.insert(BeanUtils.beanCopy(dto, CsPayConfig.class));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean edit(List<CsPayConfigDTO> list) {
        for (CsPayConfigDTO dto : list) {
            this.updateById(BeanUtils.beanCopy(dto, CsPayConfig.class));
            // 1 查询添加信息,判断是否已经存在
            Integer count = this.selectCount(new EntityWrapper<CsPayConfig>().eq("show_name", dto.getShowName()));
            // 2 如果已经存在,返回
            if (count > CommonConstant.BUTTON) {
                throw new RiggerException("此支付产品名称已存在");
            }
        }
        return true;
    }


    @Override
    public void deletePayConfig(Long[] ids) {
        Assert.notEmpty(ids, "id不能为空");
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void updateEnabled(CsPayConfigDTO dto) {
        // 查看是否存在 如果存在则修改 不存在则插入
        if (Objects.nonNull(dto.getId())) {
            CsPayConfig csPayConfig = this.selectById(dto.getId());
            Optional<CsPayConfig> optional = Optional.of(csPayConfig);
            optional.ifPresent(m -> {
                m.setIsDeleted(dto.getIsDeleted());
                this.updateById(m);
            });
            return;
        }
        dto.setIsDeleted(CommonConstant.STATUS_NORMAL);
        this.add(dto);
    }

    @Override
    @Cached(name = "payConfig:getById", key = "#id", expire = 3600)
    @CacheRefresh(refresh = 100, stopRefreshAfterLastAccess = 300)
    public CsPayConfigDTO getById(Long id) {
        CsPayConfig payConfig = this.selectById(id);
        Optional.ofNullable(payConfig).orElseThrow(() -> new RiggerException("支付方式配置不存在"));
        return BeanUtils.beanCopy(payConfig, CsPayConfigDTO.class);
    }

	@Override
	public List<CsPayConfigVO> payTypeList() {
		List<CsPayConfig> payConfigList = this.selectList(new EntityWrapper<CsPayConfig>().eq("pay_type", Constants.NO).eq("is_deleted", Constants.NO));
		return BeanUtils.beansToList(payConfigList,CsPayConfigVO.class);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void setRecommend(Long id, Integer isRecommend) {
		CsPayConfig csPayConfigOne = this.selectOne(new EntityWrapper<CsPayConfig>().eq("pay_type", Constants.NO).eq("recommend", Constants.YES));
		if (Objects.nonNull(csPayConfigOne)){
			csPayConfigOne.setRecommend(Constants.NO);
			this.updateById(csPayConfigOne);
		}
		CsPayConfig csPayConfig = new CsPayConfig();
		csPayConfig.setRecommend(isRecommend);
		csPayConfig.setId(id);
		this.updateById(csPayConfig);
	}
}
