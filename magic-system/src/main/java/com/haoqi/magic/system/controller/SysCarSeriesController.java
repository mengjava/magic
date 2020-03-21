package com.haoqi.magic.system.controller;


import com.haoqi.magic.system.model.vo.CarSeriesVO;
import com.haoqi.magic.system.service.ISysCarSeriesService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 车系表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/carSeries")
public class SysCarSeriesController extends BaseController {


    @Autowired
    private ISysCarSeriesService sysCarSeriesService;

    @Autowired
    private BeanValidatorHandler validatorHandler;
    /**
     * 功能描述:
     * 开启 禁用
     * @auther: yanhao
     * @param:
     * @date: 2019/4/28 17:59
     * @Description:
     */
    @GetMapping(value = "/setValid")
    @ApiOperation(value = "设置有效(车系)")
    public Result<Object> setCarSeriesValid(@RequestParam("id") Long id, @RequestParam("isDeleted") Integer isDeleted)
    {
        return Result.buildSuccessResult(sysCarSeriesService.setCarSeriesValid(id,isDeleted));
    }


    @GetMapping(value = "/getCarSeriesById/{id}")
    @ApiOperation(value = "根据id获取车系")
    public Result<Object> getCarSeriesById(@PathVariable("id") Long id) {
        return Result.buildSuccessResult(sysCarSeriesService.selectById(id));
    }


    @PostMapping(value = "/addCarSeries")
    @ApiOperation(value = "新增车辆车系(二级)")
    public Result<String> addCarSeries(@RequestBody CarSeriesVO vo) {
        validatorHandler.validator(vo);
        sysCarSeriesService.addCarSeries(vo);
        return Result.buildSuccessResult("品牌保存成功");
    }

    @PutMapping(value = "/updateCarSeries")
    @ApiOperation(value = "更新车辆车系(二级)")
    public Result<String> updateCarSeries(@RequestBody CarSeriesVO vo) {
        validatorHandler.validator(vo);
        sysCarSeriesService.updateCarSeries(vo);
        return Result.buildSuccessResult("品牌保存成功");
    }

}

