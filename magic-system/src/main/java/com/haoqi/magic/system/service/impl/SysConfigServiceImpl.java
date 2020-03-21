package com.haoqi.magic.system.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.system.mapper.SysConfigMapper;
import com.haoqi.magic.system.model.dto.SysConfigDTO;
import com.haoqi.magic.system.model.entity.SysConfig;
import com.haoqi.magic.system.service.ISysConfigService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.error.RiggerException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author mengyao
 * @since 2019-11-29
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {
    @Override
    public SysConfig getByTypeAndName(Integer type, Integer name) {
        SysConfig config = this.selectOne(new EntityWrapper<SysConfig>()
                .eq("type", type)
                .eq("name", name)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        return Optional.ofNullable(config).orElseThrow(() -> new RiggerException("系统配置信息不存在"));
    }

    @Override
    public List<SysConfigDTO> getByType(Integer type) {
        List<SysConfig> sysConfigs = this.selectList(new EntityWrapper<SysConfig>()
                .eq("type", type)
                .eq("is_deleted", CommonConstant.STATUS_NORMAL));
        return BeanUtils.beansToList(sysConfigs, SysConfigDTO.class);
    }
}
