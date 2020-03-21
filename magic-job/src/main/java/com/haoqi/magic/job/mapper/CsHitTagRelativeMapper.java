package com.haoqi.magic.job.mapper;

import java.util.Map;

/**
 * <p>
 * 车辆标签命中 Mapper 接口
 * </p>
 *@author huming
 * @date 2019/5/13 14:05
 */
public interface CsHitTagRelativeMapper {

    /**
     * 删除标签命中项
     * @param param
     * @return
     * @author huming
     * @date 2019/5/13 14:24
     */
    Boolean deleteOldHit(Map<String,Object> param);

    /**
     * 批量插入命中标签
     * @param param
     * @return
     * @author huming
     * @date 2019/5/13 14:29
     */
    Boolean insertHitTagRelative(Map<String,Object> param);

    /**
     * 删除旧的车辆标签数据
     * @return
     * @author huming
     * @date 2019/5/15 16:56
     */
    Boolean deleteAllOld();
}
