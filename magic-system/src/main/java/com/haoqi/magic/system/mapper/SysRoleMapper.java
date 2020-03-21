package com.haoqi.magic.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.system.model.dto.RoleDTO;
import com.haoqi.magic.system.model.entity.SysRole;
import com.haoqi.rigger.mybatis.Query;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 通过角色ID获取角色信息
     * @param roleId 角色ID
     * @return
     * @author huming
     * @date 2019/1/15 11:34
     */
    RoleDTO getRoleByRoleId(@Param("roleId") Long roleId);


    /**
     * 获取角色列表信息
     * @return
     * @author huming
     * @date 2019/1/15 9:50
     */
    List<RoleDTO> findRoleList();

    /**
     * 分页查询角色列表含有部门信息
     *
     * @param query     查询对象
     * @param condition 条件
     * @return List
     */
    List<RoleDTO> findRoleByPage(Query<Object> query, Map<String, Object> condition);

}
