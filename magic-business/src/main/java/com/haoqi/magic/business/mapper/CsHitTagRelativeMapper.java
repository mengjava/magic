package com.haoqi.magic.business.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.TagsDTO;
import com.haoqi.magic.business.model.entity.CsHitTagRelative;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆命中标签关系表 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-05-08
 */
public interface CsHitTagRelativeMapper extends BaseMapper<CsHitTagRelative> {
    /**
     * 获取搜有的tags字符串
     * @param carId
     * @return
     */
    List<TagsDTO> getCsTagsByCarId(Long carId);

    /**
     * 删除车辆原先旧标签
     * @param param
     * @return
     * @author huming
     * @date 2019/5/14 16:34
     */
    Boolean deleteOldHit(Map<String, Object> param);

    /**
     * 批量插入命中标签
     * @param param
     * @return
     * @author huming
     * @date 2019/5/14 16:37
     */
    Boolean insertHitTagRelative(Map<String, Object> param);
}
