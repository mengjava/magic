package com.haoqi.magic.job.service;

import com.haoqi.magic.job.model.dto.TagParamDTO;

import java.util.List;

/**
 * ClassName:com.haoqi.magic.job.service.impl <br/>
 * Function: 标签服务<br/>
 * Date:     2019/5/13 10:29 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public interface ICsTagService {

    /**
     * 根据类型获取对应的标签以及关联的自定义参数
     * @param tagType 1筛选里的标签，2详情，3今日推荐
     * @return
     * @author huming
     * @date 2019/5/13 11:21
     */
    List<TagParamDTO> getAllDetailTag(Integer tagType);
}
