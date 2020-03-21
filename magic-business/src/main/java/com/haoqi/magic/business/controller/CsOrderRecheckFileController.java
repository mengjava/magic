package com.haoqi.magic.business.controller;


import cn.hutool.core.util.StrUtil;
import com.haoqi.magic.business.model.dto.CsOrderRecheckFileDTO;
import com.haoqi.magic.business.service.ICsOrderRecheckFileService;
import com.haoqi.rigger.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.haoqi.rigger.web.controller.BaseController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 复检附件 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-11-29
 */
@RestController
@RequestMapping("/csOrderRecheckFile")
@Api(tags = "订单复检项()")
public class CsOrderRecheckFileController extends BaseController {


    @Autowired
    private ICsOrderRecheckFileService csOrderRecheckFileService;


    /**
     * 通过车辆id、检查项id，获取三大类检查部位信息
     *
     * @param orderId
     * @param itemId
     * @return
     */
    @GetMapping("/getCheckByCarOrderIdCheckItemId/{orderId}/{itemId}")
    @ApiOperation(value = "通过车辆id、检查项id，获取三大类检查部位信息")
    public Result<List<CsOrderRecheckFileDTO>> getCheckByCarIdCheckItemId(@PathVariable("orderId") Long orderId, @PathVariable("itemId") Long itemId) {
        currentUser();
        return Result.buildSuccessResult(csOrderRecheckFileService.findByOrderIdCheckItemId(orderId, itemId));
    }

    /**
     * 通过车辆id，获取三大类检查部位信息列表
     *
     * @param orderId
     * @return
     */
    @GetMapping("/getCheckByOrderId/{orderId}")
    @ApiOperation(value = "通过订单id，获取三大类检查部位信息")
    public Result<List<CsOrderRecheckFileDTO>> getCheckByOrderIdCheckItems(@PathVariable("orderId") Long orderId) {
        currentUser();
        return Result.buildSuccessResult(csOrderRecheckFileService.findWithCheckItemByOrderId(orderId));
    }


    /**
     * 保存或者更新复检项
     *
     * @param carCheckDTO
     * @return
     */
    @PostMapping("/saveOrUpdate")
    @ApiOperation(value = "添加三大类检查部位信息")
    public Result<CsOrderRecheckFileDTO> saveOrUpdate(@RequestBody CsOrderRecheckFileDTO carCheckDTO) {
        currentUser();
        return Result.buildSuccessResult(csOrderRecheckFileService.saveOrUpdate(carCheckDTO));
    }

    /***
     * 通过车辆id和检查项类别获取检测项
     * @param orderId 车辆id
     * @param type 三大类检测项
     * @return
     */
    @GetMapping("/getCheckByCarIdCheckType/{carId}/{type}")
    @ApiOperation(value = "通过车辆id、检查项类型，获取三大类检查部位信息")
    public Result<Object> getCheckByOrderIdCheckType(@PathVariable("orderId") Long orderId, @PathVariable("type") Integer type) {
        currentUser();
        return Result.buildSuccessResult(csOrderRecheckFileService.findByCarIdCheckType(orderId, type));
    }


    /**
     * 通过车辆ID、检测ID，删除记录
     *
     * @param orderId 车辆id
     * @param ids   检测id
     * @return
     */
    @DeleteMapping("/deleteByOrderIdCheckId/{orderId}/{ids}")
    @ApiOperation(value = "通过订单id、检查项ids 逗号分隔，删除检查部位信息")
    public Result<String> deleteByOrderIdCheckId(@PathVariable("orderId") Long orderId, @PathVariable("ids") String ids) {
        currentUser();
        List<Long> collectIds = Arrays.stream(ids.split(StrUtil.COMMA)).mapToLong(value -> Long.parseLong(value)).boxed().collect(Collectors.toList());
        csOrderRecheckFileService.deleteByOrderIdCheckId(orderId, collectIds);
        return Result.buildSuccessResult("删除成功");
    }

}

