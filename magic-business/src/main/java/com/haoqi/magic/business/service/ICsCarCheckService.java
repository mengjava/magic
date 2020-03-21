package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CarCheckDTO;
import com.haoqi.magic.business.model.entity.CsCarCheck;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆检测信息 服务类
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
public interface ICsCarCheckService extends IService<CsCarCheck> {

    /**
     * 删除不在这些范围内的数据
     *
     * @param ids
     * @return
     */
    void deleteNotInTheseId(List<Long> ids);

    /**
     * 通过车辆id、检查项id，获取三大类检查部位信息
     *
     * @param carId  车辆id
     * @param itemId 检查项id
     * @return
     */
    List<CarCheckDTO> findByCarIdCheckItemId(Long carId, Long itemId);

    /**
     * 通过车辆id，获取车辆检查信息
     *
     * @param carId 车辆id
     * @return
     */
    List<CarCheckDTO> findWithCheckItemByCarId(Long carId);

    /**
     * 通过车辆id、检查类型，获取车辆检查信息
     *
     * @param carId 车辆id
     * @param type  检查类型 1事故检测，2外观检测，3检测信息
     * @return
     */
    List<CarCheckDTO> findWithCheckItemByCarIdType(Long carId, Integer type);


    /**
     * 添加、修改检查部位
     *
     * @param carCheckDTO
     * @return
     */
    CarCheckDTO saveOrUpdate(CarCheckDTO carCheckDTO);

    /***
     * 通过类型获取所有的检测项目
     * @param carId
     * @param type
     * @return
     */
    List<Map<String, Object>> findByCarIdCheckType(Long carId, Integer type);

    /***
     * 根据车辆id获取所有的检测项
     * @param carId
     * @return
     */
    List<Map<String, Object>> findByCarIdCheckAll(Long carId);
}
