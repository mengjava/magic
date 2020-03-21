package com.haoqi.magic.business.controller;


import com.haoqi.magic.business.model.dto.CarConfigDTO;
import com.haoqi.magic.business.service.ICsCarConfigService;
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
 * 配置信息 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/carConfig")
@Api(tags = "获取车辆配置信息")
public class CsCarConfigController extends BaseController {

    @Autowired
    private ICsCarConfigService carConfigService;

    /**
     * 通过车辆id，获取车辆配置信息
     *
     * @param carId
     * @return
     */
    @GetMapping("getConfigByCarId/{carId}")
    @ApiOperation(value = "通过车辆id，获取车辆配置信息")
    public Result<CarConfigDTO> getConfigByCarId(@PathVariable("carId") Long carId) {
        Optional<CarConfigDTO> carConfigDTO = carConfigService.getConfigByCarId(carId);
        if (carConfigDTO.isPresent()) {
            return Result.buildSuccessResult(carConfigDTO.get());
        }
        return Result.buildSuccessResult("");
    }

}

