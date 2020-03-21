package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.SysConfigDTO;
import com.haoqi.magic.system.model.entity.SysConfig;

import java.util.List;

/**
 * <p>
 * 系统配置表 服务类
 * </p>
 *
 * @author mengyao
 * @since 2019-11-29
 */
public interface ISysConfigService extends IService<SysConfig> {

    /**
     * 通过类型、名称，获取配置信息
     *
     * @param type
     * @param name
     * @return
     */
    SysConfig getByTypeAndName(Integer type, Integer name);

    /**
     * 通过类型获取配置信息
     *
     * @param type
     * @return
     */
    List<SysConfigDTO> getByType(Integer type);

}
