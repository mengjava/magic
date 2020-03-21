package com.haoqi.magic.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.system.model.dto.UserAreaDTO;
import com.haoqi.magic.system.model.entity.SysUserArea;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统用户区域关联表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface SysUserAreaMapper extends BaseMapper<SysUserArea> {

    /**
     * 通过用户id，获取关联城市信息
     *
     * @param userId
     * @return
     */
    UserAreaDTO getByUserId(@Param("userId") Long userId);

}
