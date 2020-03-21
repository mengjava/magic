package com.haoqi.magic.business.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.haoqi.magic.business.model.dto.CarCheckDTO;
import com.haoqi.magic.business.model.entity.CsCarCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 车辆检测信息 Mapper 接口
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
public interface CsCarCheckMapper extends BaseMapper<CsCarCheck> {

    /**
     * 删除不在这些范围内的数据
     *
     * @param ids
     */
    void deleteNotInTheseId(@Param("checkIds") List<Long> ids);

    /**
     * 通过车辆id，获取车辆检查信息
     *
     * @param carId
     * @return
     */
    List<CarCheckDTO> findWithCheckItemByCarId(@Param("carId") Long carId);

    /**
     * 通过车辆id、检查项id，获取车辆检查信息
     *
     * @param carId
     * @param checkItemIds
     * @return
     */
    List<CarCheckDTO> findWithCheckItemByCarIdItemIds(@Param("carId") Long carId, @Param("checkItemIds") List<Long> checkItemIds);

}
