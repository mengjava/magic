package com.haoqi.magic.system.controller;


import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.magic.system.common.utils.TreeUtil;
import com.haoqi.magic.system.model.dto.MenuDTO;
import com.haoqi.magic.system.model.vo.MenuTree;
import com.haoqi.magic.system.model.vo.RoleMenuVO;
import com.haoqi.magic.system.service.ISysPermissionService;
import com.haoqi.magic.system.service.ISysRolePermissionService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色权限关联表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/roleMenu")
@Api(tags = "系统角色菜单")
public class SysRolePermissionController extends BaseController {

    @Autowired
    private ISysRolePermissionService rolePermissionService;

    @Autowired
    private ISysPermissionService permissionService;


    /**
     * 更新角色菜单
     *
     * @param roleMenu
     * @return
     */
    @ApiOperation(value = "通过角色id更新角色菜单")
    @PostMapping("/roleMenuUpd")
    public Result<Boolean> roleMenuUpd(@RequestBody RoleMenuVO roleMenu) {
        return Result.buildSuccessResult(rolePermissionService.insertRoleMenus(roleMenu.getRoleId(), roleMenu.getMenuIds()));
    }


    /**
     * 返回指定角色的菜单集合
     *
     * @param roleId 角色id
     * @return 属性集合
     */
    @GetMapping("/roleTree/{roleId}")
    @ApiOperation(value = "通过角色id获取菜单集合")
    public List<String> roleTree(@PathVariable("roleId") Long roleId) {
        List<MenuDTO> menus = permissionService.findMenuByRoleId(roleId);
        List<String> menuList = new ArrayList<>();
        for (MenuDTO menu : menus) {
            menuList.add(menu.getMenuId().toString());
        }
        return menuList;
    }


    /**
     * 获取当前用户拥有的角色菜单
     *
     * @return
     */
    @ApiOperation(value = "获取当前用户拥有的角色菜单")
    @GetMapping("/roleTree")
    public List<MenuTree> roleTree() {
        Integer userLevel = currentUser().getUserType();
        if (UserLevelEnum.SUPER_ADMIN_LEVEL.getLevel().equals(userLevel)) {
            return getMenuTree(permissionService.findAll(), 0L);
        }
        return getMenuTree(permissionService.findMenuByRoleCodes(currentUser().getId(), findRoles().toArray(new String[]{})), 0L);
    }

    private List<MenuTree> getMenuTree(List<MenuDTO> menus, Long root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (MenuDTO menu : menus) {
            node = new MenuTree();
            node.setId(menu.getMenuId());
            node.setParentId(menu.getParentId());
            node.setName(menu.getName());
            node.setUrl(menu.getUrl());
            node.setCode(menu.getPermission());
            node.setLabel(menu.getName());
            node.setIcon(menu.getIcon());
            node.setIsShow(menu.getIsShow());
            trees.add(node);
        }
        return TreeUtil.build(trees, root);
    }

}

