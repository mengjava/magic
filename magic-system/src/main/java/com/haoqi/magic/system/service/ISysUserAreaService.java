package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.UserAreaDTO;
import com.haoqi.magic.system.model.entity.SysUserArea;

import java.util.Optional;

/**
 * <p>
 * 系统用户区域关联表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface ISysUserAreaService extends IService<SysUserArea> {


    /**
     * 通过用户id，获取关联城市信息
     *
     * @param userId
     * @return
     */
    Optional<UserAreaDTO> getByUserId(Long userId);


}
