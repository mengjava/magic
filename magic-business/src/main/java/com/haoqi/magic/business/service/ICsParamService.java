package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsParamDTO;
import com.haoqi.magic.business.model.entity.CsParam;
import com.haoqi.magic.business.model.vo.CsParamVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * <p>
 * 标签管理表 服务类
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
public interface ICsParamService
        extends IService<CsParam> {

    /**
     * 分页获取标签列表信息
     * @param query
     * @return
     * @author huming
     * @date 2019/4/26 10:35
     */
    Page findPage(Query query);

    /**
     * 新增标签信息
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/26 10:35
     */
    Boolean insert(CsParamVO vo);

    /**
     * 根据标签名称，统计标签数据条数
     * @param paramName
     * @return
     * @author huming
     * @date 2019/4/26 10:43
     */
    Integer countByParamName(String paramName);

    /**
     * 通过ID删除标签信息
     * @param id 标签ID
     * @return
     * @author huming
     * @date 2019/4/26 10:36
     */
    Boolean deleteCsParamById(Long id);

    /**
     * 通过ID更新标签信息
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/26 10:37
     */
    Boolean updateCsParamById(CsParamVO vo);

    /**
     * 通过ID获取标签信息
     * @param id 标签ID
     * @return
     * @author huming
     * @date 2019/4/26 10:38
     */
    CsParamDTO getOneById(Long id);

    /**
     * 获取全部的标签数据
     * @return
     * @author huming
     * @date 2019/4/26 10:39
     */
    List<CsParam> getAllCsParam();

    /**
     * 通过IDs批量删除标签数据
     * @param lIds
     * @return
     * @author huming
     * @date 2019/4/26 14:17
     */
    Boolean deleteCsParamByIds(List<Long> lIds);
}
