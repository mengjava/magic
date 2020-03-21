package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.UserDTO;
import com.haoqi.magic.system.model.entity.SysUser;
import com.haoqi.magic.system.model.entity.SysUserRole;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface ISysUserService extends IService<SysUser> {

    /**
     * 通过用户id，删除用户信息
     *
     * @param userId
     * @return
     */
    Boolean deleteUserById(Long userId);

    /**
     * 用户分页查询
     *
     * @param query
     * @return
     */
    Page<UserDTO> findUserByPage(Query query);

    /**
     * 获取用户角色集合
     *
     * @param userId
     * @return
     */
    List<SysUserRole> findUserRoleTreeByUserId(Long userId);

    /**
     * 通过用户名获取用户信息
     *
     * @param loginName
     * @return
     */
    Optional<UserDTO> getUserByLoginName(String loginName);

    /**
     * 通过用户id，获取用户、角色
     *
     * @param userId
     * @return
     */
    Optional<UserDTO> getUserWithRoleById(Long userId);

    /**
     * 通过用户id，获取用户、角色、所在城市
     *
     * @param userId
     * @return
     */
    Optional<UserDTO> getUserWithRoleAreaById(Long userId);

    /**
     * 通过用户名获取用户、角色信息
     *
     * @param loginName
     * @return
     */
    Optional<UserDTO> getUserWithRoleByLoginName(String loginName);


    /**
     * 添加用户信息
     *
     * @param userDTO
     * @return
     */
    Long insertUser(UserDTO userDTO);


    /**
     * @param loginName
     * @return
     */
    Boolean isExist(String loginName);

    /**
     * 更新用户
     *
     * @param userDTO
     * @return
     */
    Boolean updateUser(UserDTO userDTO);

    /**
     * 更新密码
     *
     * @param userDTO
     * @return
     */
    Boolean updateWithPassword(UserDTO userDTO);

    /**
     * 更新用户角色信息
     *
     * @param userId
     * @param roleIds
     * @return
     */
    Boolean updateUserRole(Long userId, String[] roleIds);

    /**
     * 功能描述: 根据手机号重设密码
     *
     * @param mobile
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/5/7 0007 上午 11:39
     */
    Boolean resetPassword(String mobile);

    /**
     * 功能描述: 商家启用禁用
     *
     * @param loginName
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/5/22 0022 下午 4:32
     */
    Boolean updateEnabledByLoginName(String loginName);

    /***
     * 功能描述: 根据角色获取用户列表
     * @param role
     * @return java.util.List<com.haoqi.magic.system.model.entity.SysUser>
     * @auther mengyao
     * @date 2019/8/16 0016 下午 2:26
     */
    List<UserDTO> getUserListByRole(Integer role);

    /**
     * 通过用户ID获取用户信息和地区和账户信息
     *
     * @param id
     * @return
     */
    Optional<UserDTO> getUserAreaAndAccountById(Long id);

    /**
     * 更新支付密码
     *
     * @param userDTO
     * @return
     */
    Boolean updateWithPayPassword(UserDTO userDTO);

    /**
     * 功能描述: 买家用户列表
     *
     * @param query
     * @return com.baomidou.mybatisplus.plugins.Page
     * @auther mengyao
     * @date 2019/12/25 0025 下午 2:40
     */
    Page findBuyerUserByPage(Query query);


    /**
     * 通过用户id，获取用户、是否会员、商家信息
     *
     * @param userId
     * @return
     */
    UserDTO getUserAreaWithVipAndDealer(Long userId);

    /**
     * 功能描述: 批量重置用户密码
     *
     * @param ids
     * @return void
     * @auther mengyao
     * @date 2020/1/3 0003 上午 11:54
     */
    void resetPwd(String ids);

    /**
     * 功能描述: 重置用户支付密码
     *
     * @param ids
     * @return void
     * @auther mengyao
     * @date 2020/1/3 0003 上午 11:57
     */
    void resetPayPwd(String ids);

    /**
     * 确认支付密码
     *
     * @param userId
     * @param password
     */
    void confirmPayPwd(Long userId, String password);
}
