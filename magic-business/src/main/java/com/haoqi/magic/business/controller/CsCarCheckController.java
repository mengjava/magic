package com.haoqi.magic.business.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haoqi.magic.business.model.dto.CarCheckDTO;
import com.haoqi.magic.business.model.entity.CsCarCheck;
import com.haoqi.magic.business.service.ICsCarCheckService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 车辆检测信息 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
@RestController
@RequestMapping("/carCheck")
@Api(tags = "车辆检测信息")
public class CsCarCheckController extends BaseController {

    @Autowired
    private ICsCarCheckService carCheckService;

    /**
     * 通过车辆id、检查项id，获取三大类检查部位信息
     *
     * @param carId
     * @param itemId
     * @return
     */
    @GetMapping("getCheckByCarIdCheckItemId/{carId}/{itemId}")
    @ApiOperation(value = "通过车辆id、检查项id，获取三大类检查部位信息")
    public Result<List<CarCheckDTO>> getCheckByCarIdCheckItemId(@PathVariable("carId") Long carId, @PathVariable("itemId") Long itemId) {
        currentUser();
        return Result.buildSuccessResult(carCheckService.findByCarIdCheckItemId(carId, itemId));
    }

    /**
     * 通过车辆id，获取三大类检查部位信息列表
     *
     * @param carId
     * @return
     */
    @GetMapping("getCheckByCarIdCheckItemId/{carId}")
    @ApiOperation(value = "通过车辆id，获取三大类检查部位信息")
    public Result<List<CarCheckDTO>> getCheckByCarIdCheckItems(@PathVariable("carId") Long carId) {
        currentUser();
        return Result.buildSuccessResult(carCheckService.findWithCheckItemByCarId(carId));
    }

    /**
     * 添加、更新检查部位信息
     *
     * @param carCheckDTO
     * @return
     */

    @PostMapping("saveOrUpdate")
    @ApiOperation(value = "添加三大类检查部位信息")
    public Result<CarCheckDTO> saveOrUpdate(@RequestBody CarCheckDTO carCheckDTO) {
        currentUser();
        return Result.buildSuccessResult(carCheckService.saveOrUpdate(carCheckDTO));
    }

    /***
     * 通过车辆id和检查项类别获取检测项
     * @param carId 车辆id
     * @param type 三大类检测项
     * @return
     */
    @GetMapping("/getCheckByCarIdCheckType/{carId}/{type}")
    @ApiOperation(value = "通过车辆id、检查项类型，获取三大类检查部位信息")
    public Result<Object> getCheckByCarIdCheckType(@PathVariable("carId") Long carId, @PathVariable("type") Integer type) {
        currentUser();
        return Result.buildSuccessResult(carCheckService.findByCarIdCheckType(carId, type));
    }

    /**
     * 通过车辆ID、检测ID，删除记录
     *
     * @param carId 车辆id
     * @param id    检测id
     * @return
     */
    @DeleteMapping("/deleteByCarIdCheckId/{carId}/{id}")
    public Result<String> deleteByCarIdCheckId(@PathVariable("carId") Long carId, @PathVariable("id") Long id) {
        currentUser();
        carCheckService.delete(new EntityWrapper<CsCarCheck>().eq("cs_car_info_id", carId).eq("id", id));
        return Result.buildSuccessResult("删除成功");
    }

}

