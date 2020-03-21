package com.haoqi.magic.job.mapper;

import com.haoqi.magic.job.model.dto.TagParamDTO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 标签 Mapper 接口
 * </p>
 *@author huming
 * @date 2019/5/13 14:05
 */
public interface CsTagMapper{

    /**
     * 根据类型获取对应的标签以及关联的自定义参数
     * @param Map 1筛选里的标签，2详情，3今日推荐
     * @return
     * @author huming
     * @date 2019/5/13 14:45
     */
    List<TagParamDTO> getAllDetailTag(Map<String,Object> param);
}
