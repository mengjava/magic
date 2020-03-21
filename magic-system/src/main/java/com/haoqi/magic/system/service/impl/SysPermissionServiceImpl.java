package com.haoqi.magic.system.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.haoqi.magic.system.common.utils.TreeUtil;
import com.haoqi.magic.system.mapper.SysPermissionMapper;
import com.haoqi.magic.system.model.dto.MenuDTO;
import com.haoqi.magic.system.model.entity.SysPermission;
import com.haoqi.magic.system.model.vo.MenuTree;
import com.haoqi.magic.system.service.ISysPermissionService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.error.RiggerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {
    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public List<MenuDTO> findAll() {
        return permissionMapper.findAll();
    }

    @Override
    public List<MenuTree> findMenuTreeByRoleCodes(Long userId, String... roleCodes) {
        Assert.notNull(userId, "用户id不能为空！");
        Assert.notEmpty(roleCodes, "角色编号不能为空！");
        List<MenuDTO> menus = permissionMapper.findMenuByRoleCodes(userId, roleCodes).stream().distinct().collect(Collectors.toList());
        final List<MenuTree> menuTreeList = new ArrayList<>();
        menus.forEach(m -> {
            if (CommonConstant.MENU.equals(m.getType())) {
                menuTreeList.add(new MenuTree(m));
            }
        });
        return TreeUtil.build(menuTreeList, 0L);
    }

    @Override
    public String[] findPermission(Long userId, String... roles) {
        Assert.notNull(userId, "用户id不能为空！");
        List<MenuDTO> menus = permissionMapper.findMenuByRoleCodes(userId, roles).stream().distinct().collect(Collectors.toList());
        Set<String> permissions = Sets.newHashSet();
        for (MenuDTO menu : menus) {
            if (StrUtil.isNotEmpty(menu.getPermission())) {
                String permission = menu.getPermission();
                permissions.add(permission);
            }
        }
        return permissions.toArray(new String[permissions.size()]);
    }

    @Override
    public List<MenuDTO> findMenuByRoleId(Long roleId) {
        Assert.notNull(roleId, "角色ID不能为空");
        return permissionMapper.findMenuByRoleId(roleId);
    }

    @Override
    @Cached(name = "menu:findMenuByRoleCodes:",key = "#userId + '|' + #roleCodes",expire = 300)
    @CacheRefresh(refresh = 50, stopRefreshAfterLastAccess = 200)
    public List<MenuDTO> findMenuByRoleCodes(Long userId, String... roleCodes) {
        Assert.notNull(userId, "用户id不能为空！");
        Assert.notEmpty(roleCodes, "角色编号不能为空！");
        return permissionMapper.findByRoleCodes(userId, roleCodes).stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<MenuDTO> findMenuByRoleCode(Long userId, String roleCode) {
        Assert.notNull(userId, "用户id不能为空！");
        Assert.notBlank(roleCode, "角色编号不能为空！");
        return permissionMapper.findMenuByRoleCode(userId, roleCode);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMenu(Long id, String... roles) {
        Assert.notNull(id, "菜单ID不能为空！");
        Assert.notEmpty(roles, "角色编号不能为空！");
        // 删除当前节点
        this.deleteById(id);
        //删除角色菜单关系
        permissionMapper.deleteRoleMenuByMenuIdAndRoleCodes(id, roles);
        // 删除父节点为当前节点的节点
        return this.delete(new EntityWrapper<SysPermission>().eq("parent_id", id));
    }

    @Override
    public Boolean updateMenuById(SysPermission sysMenu) {
        return this.updateById(sysMenu);
    }

    @Override
    public Boolean isExist(String menuName) {
        Assert.notBlank(menuName, "菜单名不能为空！");
        SysPermission menu = this.selectOne(new EntityWrapper<SysPermission>().eq("menu_name", menuName));
        return Objects.isNull(menu) ? Boolean.FALSE : Boolean.TRUE;
    }

    @Override
    public Boolean insertMenu(SysPermission menu) {
        if (this.isExist(menu.getMenuName())) {
            throw new RiggerException("菜单 [" + menu.getMenuName() + "] 已存在！");
        }
        return this.insert(menu);
    }
}
