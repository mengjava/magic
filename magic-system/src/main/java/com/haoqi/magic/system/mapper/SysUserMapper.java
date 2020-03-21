package com.haoqi.magic.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.system.model.dto.UserDTO;
import com.haoqi.magic.system.model.entity.SysUser;
import com.haoqi.rigger.mybatis.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 用户分页列表
     *
     * @param query
     * @param param
     * @return
     */
    List<UserDTO> findUserByPage(Query query, Map param);

    /**
     * 通过用户ID，获取用户、角色
     *
     * @param userId
     * @return
     */
    UserDTO getUserWithRoleById(@Param("userId") Long userId);

    /**
     * 通过用户名，获取用户角色信息
     *
     * @param loginName
     * @return
     */
    UserDTO getUserWithRoleByLoginName(@Param("loginName") String loginName);


    /***
     * 功能描述:根绝角色获取用户信息
     * @param typeName
     * @return java.util.List<com.haoqi.magic.system.model.dto.UserDTO>
     * @auther mengyao
     * @date 2019/8/16 0016 下午 2:32
     */
    List<UserDTO> getUserListByRole(@Param("typeName") String typeName);


    /**
     * 功能描述: 买家用户列表
     *
     * @param query
     * @param condition
     * @return java.util.List<com.haoqi.magic.system.model.dto.UserDTO>
     * @auther mengyao
     * @date 2019/12/25 0025 下午 2:41
     */
    List<UserDTO> findBuyerUserByPage(Query query, Map condition);

    /**
     * 通过用户id，获取用户、是否会员、商家信息
     *
     * @param userId
     * @return
     */
    UserDTO getUserAreaWithVipAndDealer(@Param("userId") Long userId);
}
