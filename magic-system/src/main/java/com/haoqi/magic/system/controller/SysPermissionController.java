package com.haoqi.magic.system.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haoqi.magic.system.common.utils.TreeUtil;
import com.haoqi.magic.system.model.dto.MenuDTO;
import com.haoqi.magic.system.model.entity.SysPermission;
import com.haoqi.magic.system.model.vo.MenuTree;
import com.haoqi.magic.system.model.vo.MenuVO;
import com.haoqi.magic.system.service.ISysPermissionService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/permission")
@Api(tags = "系统权限菜单管理")
public class SysPermissionController extends BaseController {

    @Autowired
    private ISysPermissionService permissionService;

    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 通过用户id、角色名获取菜单
     *
     * @param userId   当前用户
     * @param roleCode 角色编号
     * @return
     */
    @GetMapping(value = "/findMenuByRole")
    @ApiOperation(value = "通过用户id、角色名获取菜单(单个角色)")
    public List<MenuDTO> findMenuByRole(@RequestParam("userId") Long userId, @RequestParam("roleCode") String roleCode) {
        return permissionService.findMenuByRoleCode(userId, roleCode);
    }

    /**
     * 通过用户id、角色名获取菜单
     *
     * @param userId   当前用户
     * @param roleCodes 角色编号
     * @return
     */
    @PostMapping(value = "findMenuByRoles")
    @ApiOperation(value = "通过用户id、角色名获取菜单(多个角色)")
    public Result<List<MenuDTO>> findMenuByRoles(@RequestParam("userId") Long userId, @RequestParam("roleCodes") String... roleCodes) {
        return Result.buildSuccessResult(permissionService.findMenuByRoleCodes(userId, roleCodes));
    }

    /**
     * 返回树形菜单集合
     *
     * @return 树形菜单
     */
    @GetMapping("/tree")
    @ApiOperation(value = "返回树形菜单集合")
    public Result<List<MenuTree>> getTree() {
        return Result.buildSuccessResult(getMenuTree(permissionService.selectList(new EntityWrapper<SysPermission>().eq("is_deleted", CommonConstant.STATUS_NORMAL).orderAsc(Arrays.asList("menu_sort"))), 0L));
    }

    /**
     * 通过ID查询菜单的详细信息
     *
     * @param id 菜单ID
     * @return 菜单详细信息
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询菜单的详细信息")
    public Result<MenuVO> menu(@PathVariable Long id) {
        MenuVO menu = new MenuVO();
        SysPermission sysMenu = permissionService.selectById(id);
        BeanUtil.copyProperties(sysMenu, menu);
        return Result.buildSuccessResult(menu);
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单信息
     * @return success/false
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增菜单")
    public Result<String> menu(@RequestBody MenuVO menu) {
        validatorHandler.validator(menu);
        SysPermission sysMenu = new SysPermission();
        BeanUtil.copyProperties(menu, sysMenu);
        permissionService.insertMenu(sysMenu);
        return Result.buildSuccessResult("添加成功");
    }

    /**
     * 删除菜单
     *
     * @param id 菜单ID
     * @return success/false
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "通过菜单id删除菜单")
    public Result<Boolean> menuDel(@PathVariable Long id) {
        return Result.buildSuccessResult(permissionService.deleteMenu(id, findRoles().toArray(new String[]{})));
    }


    /**
     * 更新菜单
     *
     * @param menu
     * @param
     * @return
     */
    @PutMapping("/update")
    @ApiOperation(value = "更新菜单")
    public Result<String> menuUpdate(@RequestBody MenuVO menu) {
        validatorHandler.validator(menu);
        SysPermission sysMenu = new SysPermission();
        BeanUtil.copyProperties(menu, sysMenu);
        permissionService.updateMenuById(sysMenu);
        return Result.buildSuccessResult("");
    }





    private List<MenuTree> getMenuTree(List<SysPermission> menus, Long root) {
        List<MenuTree> trees = new ArrayList<MenuTree>();
        MenuTree node = null;
        for (SysPermission menu : menus) {
            node = new MenuTree();
            node.setId(menu.getId());
            node.setParentId(menu.getParentId());
            node.setName(menu.getMenuName());
            node.setUrl(menu.getMenuUrl());
            node.setCode(menu.getPermission());
            node.setIsShow(menu.getIsShow());
            node.setLabel(menu.getMenuName());
            node.setIcon(menu.getMenuIcon());
            trees.add(node);
        }
        return TreeUtil.build(trees, root);
    }

}

