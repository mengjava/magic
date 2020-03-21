package com.haoqi.magic.system.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.haoqi.magic.system.mapper.SysRolePermissionMapper;
import com.haoqi.magic.system.model.entity.SysRolePermission;
import com.haoqi.magic.system.service.ISysRolePermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements ISysRolePermissionService {

    @Override
    public Boolean insertRoleMenus(Long roleId, Long[] menuIds) {
        Assert.notNull(roleId, "角色id不能为空");
        this.delete(new EntityWrapper<SysRolePermission>().eq("sys_role_id", roleId));
        if (menuIds == null) {
            return Boolean.TRUE;
        }
        List<SysRolePermission> roleMenuList = Lists.newArrayList();
        SysRolePermission roleMenu = null;
        for (Long menuId : menuIds) {
            roleMenu = new SysRolePermission();
            roleMenu.setSysRoleId(roleId);
            roleMenu.setSysPermissionId(menuId);
            roleMenuList.add(roleMenu);
        }
        return this.insertBatch(roleMenuList);
    }
}
