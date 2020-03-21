package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.MenuDTO;
import com.haoqi.magic.system.model.entity.SysPermission;
import com.haoqi.magic.system.model.vo.MenuTree;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface ISysPermissionService extends IService<SysPermission> {

    /**
     * 获取全部菜单
     *
     * @return
     */
    List<MenuDTO> findAll();

    /**
     * 返回用户、角色的菜单
     *
     * @param userId    用户id
     * @param roleCodes 角色编号
     * @return 菜单列表
     */
    List<MenuTree> findMenuTreeByRoleCodes(Long userId, String... roleCodes);

    /**
     * 通过用户、角色获取菜单权限列表
     *
     * @param userId 用户id
     * @param roles  角色
     * @return 权限列表
     */
    String[] findPermission(Long userId, String... roles);

    /**
     * 通过角色id，查询菜单 权限
     *
     * @param roleId 角色ID
     * @return
     */
    List<MenuDTO> findMenuByRoleId(Long roleId);

    /**
     * 通过用户、角色code，获取菜单列表
     *
     * @param userId    用户id
     * @param roleCodes 角色编号
     * @return
     */
    List<MenuDTO> findMenuByRoleCodes(Long userId, String... roleCodes);

    /**
     * 通过角色名称查询URL 权限
     *
     * @param userId   用户id
     * @param roleCode 角色名称
     * @return 菜单列表
     */
    List<MenuDTO> findMenuByRoleCode(Long userId, String roleCode);

    /**
     * 级联删除菜单
     *
     * @param id    菜单ID
     * @param roles 角色
     * @return 成功、失败
     */
    Boolean deleteMenu(Long id, String... roles);


    /**
     * 更新菜单信息
     *
     * @param sysMenu 菜单信息
     * @return 成功、失败
     */
    Boolean updateMenuById(SysPermission sysMenu);

    /**
     * 通过菜单名，判断菜单是否存在
     *
     * @param menuName
     * @return
     */
    Boolean isExist(String menuName);

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    Boolean insertMenu(SysPermission menu);

}
