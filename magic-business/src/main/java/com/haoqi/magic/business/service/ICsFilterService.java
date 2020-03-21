package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsFilterDTO;
import com.haoqi.magic.business.model.entity.CsFilter;
import com.haoqi.magic.business.model.vo.CsFilterVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 筛选管理表 服务类
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
public interface ICsFilterService
        extends IService<CsFilter> {

    /**
     * 分页获取标签列表信息
     * @param query
     * @return
     * @author huming
     * @date 2019/4/26 10:35
     */
    Page findPage(Query query);


    /**
     * 新增筛选数据
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/26 16:05
     */
    Boolean insert(CsFilterVO vo);

    /**
     * 根据筛选名称获取筛选的条数
     * @param id 主键ID
     * @param cfFilterName 筛选名称
     * @param filterType 筛选位置
     * @return
     * @author huming
     * @date 2019/4/26 16:29
     */
    Integer countByFilterName(Long id,String cfFilterName,Integer filterType);


    /**
     * 通过ID更新筛选信息
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/26 16:09
     */
    Boolean updateCsFilterById(CsFilterVO vo);

    /**
     * 通过ID获取新筛选信息
     * @param id 筛选ID
     * @return
     * @author huming
     * @date 2019/4/26 10:38
     */
    CsFilterDTO getOneById(Long id);


    /**
     * 获取全部的新筛数据
     * @return
     * @author huming
     * @date 2019/4/26 10:39
     */
    List<CsFilter> getAllCsFilter();

    /**
     * 通过IDs批量删除标签数据
     * @param lIds
     * @return
     * @author huming
     * @date 2019/4/26 14:17
     */
    Boolean deleteCsFilterByIds(List<Long> lIds);

    /**
     * 获取全部的筛选信息通过map形式返回
     * @return
     * @author huming
     * @date 2019/5/15 16:20
     */
    Map<String,List<CsFilterDTO>> getMapCsFilter();

    /**
     * 通过条件获取筛选信息
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/20 17:25
     */
    List<CsFilterDTO> getCsFilterWithCondition(CsFilterVO vo);
}
