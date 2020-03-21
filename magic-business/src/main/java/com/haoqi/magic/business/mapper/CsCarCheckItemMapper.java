package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CsCarCheckItemDTO;
import com.haoqi.magic.business.model.entity.CsCarCheckItem;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 车辆检测项 Mapper 接口
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
public interface CsCarCheckItemMapper extends BaseMapper<CsCarCheckItem> {

    /**
     * 通过id，获取带有父节点项名称的检查项
     *
     * @param id
     * @return
     */
    CsCarCheckItemDTO getCheckItemWithParentItemNameById(@Param("id") Long id);

}
