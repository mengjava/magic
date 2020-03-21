package com.haoqi.magic.system.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.magic.system.common.utils.TreeUtil;
import com.haoqi.magic.system.model.dto.MenuDTO;
import com.haoqi.magic.system.model.dto.RoleDTO;
import com.haoqi.magic.system.model.dto.RolePageVO;
import com.haoqi.magic.system.model.entity.SysPermission;
import com.haoqi.magic.system.model.vo.MenuTree;
import com.haoqi.magic.system.service.ISysPermissionService;
import com.haoqi.magic.system.service.ISysRoleService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/role")
@Api(tags = "系统角色管理")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPermissionService permissionService;

    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 通过ID查询角色信息
     *
     * @param id 角色ID
     * @return 角色信息
     * @author huming
     * @date 2019/1/14 16:37
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过用角色ID查询角色详细信息")
    public Result<RoleDTO> role(@PathVariable Long id) {
        currentUser();
        Optional<RoleDTO> role = roleService.getRoleByRoleId(id);
        return Result.buildSuccessResult(role.get());
    }


    /**
     * 添加角色
     *
     * @param roleDTO 角色信息
     * @return
     * @author huming
     * @date 2019/1/14 16:39
     */
    @PostMapping
    @ApiOperation(value = "添加角色")
    public Result<String> addRole(@RequestBody RoleDTO roleDTO) {
        currentUser();
        validatorHandler.validator(roleDTO);
        roleService.insertRole(roleDTO);
        return Result.buildSuccessResult("添加成功");
    }

    /**
     * 修改角色
     *
     * @param roleDTO 角色信息
     * @author huming
     * @date 2019/1/15 11:13
     */
    @PutMapping
    @ApiOperation(value = "修改角色信息")
    public Result<String> roleUpdate(@RequestBody RoleDTO roleDTO) {
        currentUser();
        validatorHandler.validator(roleDTO);
        roleService.updateRole(roleDTO);
        return Result.buildSuccessResult("修改成功");
    }

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @return
     * @author huming
     * @date 2019/1/15 11:13
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value = "通过ID删除角色信息")
    public Result<String> roleDel(@PathVariable Long id) {
        currentUser();
        roleService.deleteRoleById(id);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 获取角色信息
     *
     * @return 角色列表
     * @author huming
     * @date 2019/1/14 16:48
     */
    @GetMapping("/roleList")
    @ApiOperation(value = "获取角色信息列表")
    public Result<List<RoleDTO>> roleList() {
        currentUser();
        return Result.buildSuccessResult(roleService.findRoleList());
    }


    /**
     * 分页查询角色信息
     *
     * @param vo
     * @return 分页对象
     * @author huming
     * @date 2019/1/15 11:14
     */
    @PostMapping("/rolePage")
    @ApiOperation(value = "分页查询角色信息")
    public Result<Page> rolePage(@RequestBody RolePageVO vo) {
        currentUser();
        Map params = Maps.newHashMap();
        params.put("isDeleted", CommonConstant.STATUS_NORMAL);
        params.put("page", vo.getPage());
        params.put("limit", vo.getLimit());
        if (StringUtils.isEmpty(vo.getKeyword())) {
            params.put("keyword", vo.getKeyword());
        }
        return Result.buildSuccessResult(roleService.findRoleByPage(new Query<>(params)));
    }

    /**
     * 返回指定角色的菜单集合
     *
     * @param roleId 角色id
     * @return 属性集合
     */
    @GetMapping("/roleTree/{roleId}")
    @ApiOperation(value = "返回指定角色的菜单集合")
    public Result<List<String>> roleTree(@PathVariable("roleId") Long roleId) {
        currentUser();
        List<MenuDTO> menus = permissionService.findMenuByRoleId(roleId);
        List<String> menuList = new ArrayList<>();
        for (MenuDTO menu : menus) {
            menuList.add(menu.getMenuId().toString());
        }
        return Result.buildSuccessResult(menuList);
    }

    /**
     * 获取当前用户拥有的角色菜单
     *
     * @return
     */
    @GetMapping("/roleTree")
    @ApiOperation(value = "获取当前用户拥有的角色菜单")
    public Result<List<MenuTree>> roleTree() {
        Integer userLevel = currentUser().getUserType();
        if (UserLevelEnum.SUPER_ADMIN_LEVEL.getLevel().equals(userLevel)) {
            List<SysPermission> list = permissionService.selectList(new EntityWrapper<>());
            List<MenuDTO> menus = new ArrayList<>();
            list.forEach(permission -> {
                MenuDTO dto = new MenuDTO();
                dto.setMenuId(permission.getId());
                dto.setParentId(permission.getParentId());
                dto.setName(permission.getMenuName());
                dto.setUrl(permission.getMenuUrl());
                dto.setPermission(permission.getPermission());
                dto.setIcon(permission.getMenuIcon());
                menus.add(dto);
            });
            return Result.buildSuccessResult(getMenuTree(menus, 0L));
        }
        return Result.buildSuccessResult(getMenuTree(permissionService.findMenuByRoleCodes(currentUser().getId(), findRoles().toArray(new String[]{})), 0L));
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
            trees.add(node);
        }
        return TreeUtil.build(trees, root);
    }

}

