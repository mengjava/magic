package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.TagsDTO;
import com.haoqi.magic.business.model.entity.CsHitTagRelative;

import java.util.List;

/**
 * <p>
 * 车辆命中标签关系表 服务类
 * </p>
 *
 * @author yanhao
 * @since 2019-05-08
 */
public interface ICsHitTagRelativeService extends IService<CsHitTagRelative> {
    /***
     * 获取车id对应命中的标签list
     * @param carId
     * @return
     */
    List<TagsDTO> getCsTagsByCarId(Long carId);

    /**
     * 通过车辆ID给车辆打标签
     *
     * @param carId 车辆ID
     * @return
     * @author huming
     * @date 2019/5/14 13:56
     */
    Boolean htiTagByCarId(Long carId);

    /**
     * 删除车辆原先旧标签
     *
     * @param cars  车辆IDs
     * @param tagId 标签ID
     * @return
     * @author huming
     * @date 2019/5/14 16:30
     */
    Boolean deleteOldHit(List<Long> cars, Long tagId);

    /**
     * 新增标签
     *
     * @param cars  车辆IDs
     * @param tagId 标签ID
     * @return
     * @author huming
     * @date 2019/5/14 16:31
     */
    Boolean insertHitTagRelative(List<Long> cars, Long tagId);
}
