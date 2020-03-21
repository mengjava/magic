package com.haoqi.magic.business.service;

import com.baomidou.mybatisplus.service.IService;
import com.haoqi.magic.business.model.dto.CarFileDTO;
import com.haoqi.magic.business.model.entity.CsCarFile;

import java.util.List;

/**
 * <p>
 * 车辆图片信息 服务类
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
public interface ICsCarFileService extends IService<CsCarFile> {

    /**
     * 通过车辆id，文件类型，获取车辆文件信息
     *
     * @param carId    车辆id
     * @param fileType 文件类型
     * @return
     */
    List<CarFileDTO> findByCarIdAndFileType(Long carId, Integer fileType);

    /**
     * 通过车辆id，获取左前45图
     *
     * @param carId
     * @return
     */
    CarFileDTO getLeftFront45FileByCarId(Long carId);

}
