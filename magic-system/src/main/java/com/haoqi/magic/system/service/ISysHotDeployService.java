package com.haoqi.magic.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.system.model.dto.SysHotDeployDTO;
import com.haoqi.magic.system.model.entity.SysHotDeploy;
import com.haoqi.magic.system.model.vo.SysHotDeployVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * <p>
 * APP热更新管理 服务类
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
public interface ISysHotDeployService
        extends IService<SysHotDeploy> {

    /**
     * 分页获取热发布信息
     *
     * @param query
     * @return
     * @author huming
     * @date 2019/4/25 14:40
     */
    Page findPage(Query query);

    /**
     * 新增热发布信息
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/25 14:40
     */
    Boolean insert(SysHotDeployVO vo);

    /**
     * 删除热发布信息
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/4/25 14:41
     */
    Boolean deleteHotDeploy(Long id);

    /**
     * 更新热发布信息
     *
     * @param sysHotDeployVO
     * @return
     * @author huming
     * @date 2019/4/25 14:42
     */
    Boolean updateAppById(SysHotDeployVO sysHotDeployVO);

    /**
     * 通过ID更新热发布信息
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/4/25 14:43
     */
    SysHotDeployVO getHotDeploy(Long id);

    /**
     * type类型
     *
     * @param type APP类型（1:IOS 2:安卓）
     * @return
     * @author huming
     * @date 2019/4/25 14:44
     */
    SysHotDeployDTO checkAppVersion(String type);

    /**
     * 获取全部热发布信息
     *
     * @return
     * @author huming
     * @date 2019/4/25 14:44
     */
    List<SysHotDeployDTO> getAllAppType();
}
