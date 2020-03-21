package com.haoqi.magic.business.service;

/**
 * ClassName:com.haoqi.magic.system.service <br/>
 * Function: 打标签服务类 <br/>
 * Date:     2019/5/14 16:18 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public interface ICsMarkedCarTagService {

    /**
     * 通过标签ID给车辆打标签
     * @param tagId 标签ID
     * @param sqlStr 标签关联的自定义参数
     * @return
     * @author huming
     * @date 2019/5/14 16:21
     */
    Boolean markedCarWithTagId(Long tagId,String sqlStr);
}
