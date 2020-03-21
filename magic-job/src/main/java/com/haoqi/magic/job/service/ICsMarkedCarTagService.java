package com.haoqi.magic.job.service;

/**
 * ClassName:com.haoqi.magic.system.service <br/>
 * Function: 给车辆打标签 <br/>
 * Date:     2019/5/10 10:11 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public interface ICsMarkedCarTagService {

    /**
     * 给所有的车辆打标签
     * @param total 总分片
     * @param item 当前分片
     * @return
     * @author huming
     * @date 2019/5/10 10:25
     */
    Boolean markCarTag(Integer total, Integer item);
}
