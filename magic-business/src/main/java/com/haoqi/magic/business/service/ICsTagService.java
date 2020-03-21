package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CsTagDTO;
import com.haoqi.magic.business.model.dto.CsTagParamDTO;
import com.haoqi.magic.business.model.entity.CsTag;
import com.haoqi.magic.business.model.vo.CsTagVO;
import com.haoqi.rigger.mybatis.Query;

import java.util.List;

/**
 * <p>
 * 标签封装展示管理 服务类
 * </p>
 *
 * @author huming
 * @since 2019-04-30
 */
public interface ICsTagService
        extends IService<CsTag> {

    /**
     * 分页获取标签封装展示信息
     * @param query
     * @return
     * @author huming
     * @date 2019/4/30 9:22
     */
    Page findPage(Query query);

    /**
     * 新增标签封装展示数据
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/30 14:15
     */
    Boolean insert(CsTagVO vo);

    /**
     * 根据标签封装名称获取条数
     * @param id 主键
     * @param csTagName 标签名称
     * @param tageType 标签类型
     * @return
     * @author huming
     * @date 2019/5/20 15:00
     */
    Integer countByCsTagName(Long id,String csTagName,Integer tageType);


    /**
     * 通过ID更新标封装信息
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/30 14:35
     */
    Boolean updateCsTagById(CsTagVO vo);

    /**
     * 通过ID获取标签封装信息
     * @param id 筛选ID
     * @return
     * @author huming
     * @date 2019/4/30 14:35
     */
    CsTagDTO getOneById(Long id);


    /**
     * 获取全部的新筛数据
     * @return
     * @author huming
     * @date 2019/4/30 14:35
     */
    List<CsTag> getAllCsTag();

    /**
     * 通过条件获取标签封装数据
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/30 14:49
     */
    List<CsTag> getCsTagWithCondition(CsTagVO vo);

    /**
     * 通过IDs批量删除标签数据
     * @param lIds
     * @return
     * @author huming
     * @date 2019/4/30 14:36
     */
    Boolean deleteCsTagByIds(List<Long> lIds);

    /**
     * 通过标签id获取命中的标签sql
     * @param id
     * @return
     */
    String getCsSqlStrByTagId(Long id);

    /**
     * 根据位置获取对应的标签以及自定义参数
     * @param tagType 1筛选里的标签，2详情，3今日推荐
     * @return
     * @author huming
     * @date 2019/5/14 14:03
     */
    List<CsTagParamDTO> getAllDetailTag(Integer tagType);
}
