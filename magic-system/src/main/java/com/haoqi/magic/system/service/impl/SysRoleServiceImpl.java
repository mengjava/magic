package com.haoqi.magic.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.haoqi.magic.system.mapper.SysRoleMapper;
import com.haoqi.magic.system.model.dto.RoleDTO;
import com.haoqi.magic.system.model.entity.SysRole;
import com.haoqi.magic.system.model.entity.SysRolePermission;
import com.haoqi.magic.system.model.entity.SysUserRole;
import com.haoqi.magic.system.service.ISysRolePermissionService;
import com.haoqi.magic.system.service.ISysRoleService;
import com.haoqi.magic.system.service.ISysUserRoleService;
import com.haoqi.rigger.common.util.PinyinUtil;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private ISysUserRoleService userRoleService;

    @Autowired
    private ISysRolePermissionService rolePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteRoleById(Long id) {
        SysUserRole userRole = new SysUserRole();
        userRole.setSysRoleId(id);
        if (userRoleService.selectCount(new EntityWrapper<>(userRole)) > 0) {
            throw new RiggerException("该角色已绑定用户，不能删除");
        }
        userRoleService.delete(new EntityWrapper<>(userRole));
        SysRolePermission roleMenu = new SysRolePermission();
        roleMenu.setSysRoleId(id);
        rolePermissionService.delete(new EntityWrapper<>(roleMenu));
        SysRole role = new SysRole();
        role.setId(id);
        roleMapper.delete(new EntityWrapper<>(role));
        return Boolean.TRUE;
    }

    @Override
    public List<RoleDTO> findRoleList() {
        return roleMapper.findRoleList();
    }

    @Override
    public Page<RoleDTO> findRoleByPage(Query query) {
        return query.setRecords(roleMapper.findRoleByPage(query, query.getCondition()));
    }

    @Override
    public Optional<RoleDTO> getRoleByRoleId(Long roleId) {
        Assert.notNull(roleId, "角色ID不能为空");
        return Optional.ofNullable(roleMapper.getRoleByRoleId(roleId));
    }

    @Override
    public Boolean insertRole(RoleDTO roleDTO) {
        Assert.notNull(roleDTO, "参数不能为空");
        if (isExist(roleDTO.getRoleName())) {
            throw new RiggerException("角色 [" + roleDTO.getRoleName() + "] 已存在");
        }
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(roleDTO, sysRole);
        sysRole.setRoleCode(PinyinUtil.getPinYin(sysRole.getRoleName()));
        return this.insert(sysRole);
    }

    @Override
    public Boolean isExist(String roleName) {
        Assert.notBlank(roleName, "角色名称不为空");
        SysRole role = new SysRole();
        role.setRoleName(roleName);
        if (Objects.isNull(roleMapper.selectOne(role))) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateRole(RoleDTO role) {
        int count = this.selectCount(new EntityWrapper().eq("role_name", role.getRoleName()).ne("id", role.getId()));
        if (count > 0) {
            throw new RiggerException("角色 [" + role.getRoleName() + "] 已存在");
        }
        SysRole sysRole = new SysRole();
        BeanUtil.copyProperties(role, sysRole);
        sysRole.setRoleCode(PinyinUtil.getPinYin(sysRole.getRoleName()));
        return this.updateById(sysRole);
    }
}
