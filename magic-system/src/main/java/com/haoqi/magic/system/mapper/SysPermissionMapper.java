package com.haoqi.magic.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.system.model.dto.MenuDTO;
import com.haoqi.magic.system.model.entity.SysPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 查询所有
     * @return
     */
    List<MenuDTO> findAll();

    /**
     * 通过用户、多个角色名查询菜单
     *
     * @param userId    用户id
     * @param roleCodes 角色编号
     * @return
     */
    List<MenuDTO> findMenuByRoleCodes(@Param("userId") Long userId, @Param("roleCodes") String... roleCodes);

    /**
     * 通过角色id，查询菜单 权限
     *
     * @param roleId
     * @return
     */
    List<MenuDTO> findMenuByRoleId(@Param("roleId") Long roleId);

    /**
     * 通过用户、角色code，获取菜单列表
     *
     * @param userId    用户id
     * @param roleCodes
     * @return
     */
    List<MenuDTO> findByRoleCodes(@Param("userId") Long userId, @Param("roleCodes") String... roleCodes);

    /**
     * 通过用户id、角色名查询菜单
     *
     * @param userId   用户id
     * @param roleCode 角色编号
     * @return 菜单列表
     */
    List<MenuDTO> findMenuByRoleCode(@Param("userId") Long userId, @Param("roleCode") String roleCode);

    /**
     * 通过菜单ID、角色名，删除角色菜单关系
     *
     * @param menuId    菜单ID
     * @param roleCodes 角色编号
     */
    void deleteRoleMenuByMenuIdAndRoleCodes(@Param("menuId") Long menuId, @Param("roleCodes") String... roleCodes);

}
