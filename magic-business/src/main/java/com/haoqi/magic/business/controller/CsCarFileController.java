package com.haoqi.magic.business.controller;


import com.haoqi.magic.business.model.dto.CarFileDTO;
import com.haoqi.magic.business.service.ICsCarFileService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 车辆图片信息 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/carFile")
@Api(tags = "获取车辆关联文件信息")
public class CsCarFileController extends BaseController {

    @Autowired
    private ICsCarFileService carFileService;


    /**
     * 通过车辆id，获取车辆关联文件
     *
     * @param carId 车辆id
     * @return
     */
    @GetMapping("getCarFileByCarId/{carId}")
    @ApiOperation(value = "通过车辆id，获取车辆关联文件")
    public Result<List<CarFileDTO>> getCarFileByCarId(@PathVariable("carId") Long carId) {
        return Result.buildSuccessResult(carFileService.findByCarIdAndFileType(carId, null));
    }


    /**
     * 通过车辆id、文件类型，获取车辆关联文件
     *
     * @param carId    车辆id
     * @param fileType 文件类型
     * @return
     */
    @GetMapping("getCarFileByCarId/{carId}/{type}")
    @ApiOperation(value = "通过车辆id、文件类型，获取车辆关联文件")
    public Result<List<CarFileDTO>> getCarFileByCarIdType(@PathVariable("carId") Long carId, @PathVariable("type") Integer fileType) {
        return Result.buildSuccessResult(carFileService.findByCarIdAndFileType(carId, fileType));
    }

}

