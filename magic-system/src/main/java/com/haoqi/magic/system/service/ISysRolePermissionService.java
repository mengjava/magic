package com.haoqi.magic.system.service;

import com.haoqi.magic.system.model.entity.SysRolePermission;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface ISysRolePermissionService extends IService<SysRolePermission> {

    /**
     * 更新角色菜单关系
     *
     * @param roleId  角色id
     * @param menuIds 菜单列表
     * @return
     */
    Boolean insertRoleMenus(Long roleId, Long[] menuIds);

}
