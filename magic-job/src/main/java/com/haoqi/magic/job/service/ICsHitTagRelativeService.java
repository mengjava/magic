package com.haoqi.magic.job.service;

import com.haoqi.magic.job.model.dto.CarDTO;

import java.util.List;

/**
 * ClassName:com.haoqi.magic.job.service <br/>
 * Function: 车辆标签命中服务<br/>
 * Date:     2019/5/13 11:51 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
public interface ICsHitTagRelativeService {

    /**
     * 添加标签命中信息
     * @param cars 命中的车辆
     * @param tagId 标签ID
     * @return
     * @author huming
     * @date 2019/5/13 11:55
     */
    Boolean insertHitTagRelative(List<CarDTO> cars, Long tagId);

    /**
     * 删除命中的标签项
     * @param cars 命中的车辆
     * @param tagId 标签ID
     * @return
     * @author huming
     * @date 2019/5/13 14:22
     */
    Boolean deleteOldHit(List<CarDTO> cars, Long tagId);

    /**
     * 删除旧的车辆标签数据
     * @return
     * @author huming
     * @date 2019/5/15 16:55
     */
    Boolean deleteAllOld();

}
