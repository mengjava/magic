package com.haoqi.magic.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.system.model.dto.SysHotDeployDTO;
import com.haoqi.magic.system.model.entity.SysHotDeploy;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * APP热更新管理 Mapper 接口
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
public interface SysHotDeployMapper extends BaseMapper<SysHotDeploy> {

    /**
     * 分页查询
     * @param query
     * @param param
     * @return
     * @author huming
     * @date 2019/4/25 14:50
     */
    List<SysHotDeploy> findHotDeployPage(Query query, Map param);

    /**
     * 获取全部热发布信息
     * @return
     * @author huming
     * @date 2019/4/25 14:56
     */
    List<SysHotDeployDTO> getAllAppType();

}
