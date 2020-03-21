package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.RoleDTO;
import com.haoqi.magic.system.model.entity.SysRole;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 通过ID，删除角色信息
     *
     * @param id 角色ID
     * @return
     * @author huming
     * @date 2019/1/15 11:31
     */
    Boolean deleteRoleById(Long id);

    /**
     * 获取角色信息
     * @return
     * @author huming
     * @date 2019/1/15 9:48
     */
    List<RoleDTO> findRoleList();

    /**
     * 通过分页查询带有部门信息的角色列表
     *
     * @param query
     * @return
     * @author huming
     * @date 2019/1/15 11:31
     */
    Page<RoleDTO> findRoleByPage(Query query);

    /**
     * 通过角色ID，获取角色、部门信息
     *
     * @param roleId 角色ID
     * @return
     * @author huming
     * @date 2019/1/15 11:26
     */
    Optional<RoleDTO> getRoleByRoleId(Long roleId);

    /**
     * 通过公司id、角色名，判断角色是否存在
     *
     * @param roleName
     * @return
     * @author huming
     * @date 2019/1/15 11:31
     */
    Boolean isExist(String roleName);


    /**
     * 添加角色
     *
     * @param roleDTO
     * @return
     * @author huming
     * @date 2019/1/15 11:28
     */
    Boolean insertRole(RoleDTO roleDTO);


    /**
     * 更新角色
     *
     * @param role
     * @return
     * @author huming
     * @date 2019/1/15 11:28
     */
    Boolean updateRole(RoleDTO role);

}
