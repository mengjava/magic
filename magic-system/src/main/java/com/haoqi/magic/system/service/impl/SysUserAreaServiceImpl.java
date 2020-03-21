package com.haoqi.magic.system.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.system.mapper.SysUserAreaMapper;
import com.haoqi.magic.system.model.dto.UserAreaDTO;
import com.haoqi.magic.system.model.entity.SysUserArea;
import com.haoqi.magic.system.service.ISysUserAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 系统用户区域关联表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@Service
public class SysUserAreaServiceImpl extends ServiceImpl<SysUserAreaMapper, SysUserArea> implements ISysUserAreaService {

    @Autowired
    private SysUserAreaMapper userAreaMapper;

    @Override
    public Optional<UserAreaDTO> getByUserId(Long userId) {
        UserAreaDTO userArea = userAreaMapper.getByUserId(userId);
        return Optional.ofNullable(userArea);
    }
}
