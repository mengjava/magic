package com.haoqi.magic.business.controller;

import com.haoqi.magic.business.service.ICsHitTagRelativeService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 车辆命中标签关系表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-05-08
 */
@RestController
@RequestMapping("/csHitTagRelative")
@Api(tags = "车辆命中标签关系Controller")
public class CsHitTagRelativeController extends BaseController {

    @Autowired
    private ICsHitTagRelativeService csHitTagRelativeService;


    /**
     * 通过车辆ID给车辆打标签
     * @param carId 车辆ID
     * @return
     * @author huming
     * @date 2019/5/15 9:54
     */
    @GetMapping("/htiTagByCarId")
    @ApiOperation(value = "通过车辆ID给车辆打标签")
    public Result<String> htiTagByCarId(@RequestParam("carId") Long carId) {
        csHitTagRelativeService.htiTagByCarId(carId);
        return Result.buildSuccessResult("操作成功");

    }

}

