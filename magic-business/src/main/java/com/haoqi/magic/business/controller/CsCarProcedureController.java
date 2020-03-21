package com.haoqi.magic.business.controller;


import com.haoqi.magic.business.model.dto.CarProcedureDTO;
import com.haoqi.magic.business.service.ICsCarProcedureService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <p>
 * 手续信息 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/carProcedure")
@Api(tags = "获取车辆手续信息")
public class CsCarProcedureController extends BaseController {

    @Autowired
    private ICsCarProcedureService carProcedureService;


    /**
     * 通过车辆id，获取车辆手续信息
     *
     * @param carId
     * @return
     */
    @GetMapping("getProcedureByCarId/{carId}")
    @ApiOperation(value = "通过车辆id，获取车辆手续信息")
    public Result<CarProcedureDTO> getProcedureByCarId(@PathVariable("carId") Long carId) {
        Optional<CarProcedureDTO> carProcedureDTO = carProcedureService.getProcedureByCarId(carId);
        if (carProcedureDTO.isPresent()) {
            return Result.buildSuccessResult(carProcedureDTO.get());
        }
        return Result.buildSuccessResult("");
    }

}

