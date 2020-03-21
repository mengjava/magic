package com.haoqi.magic.system.controller;


import com.haoqi.magic.common.enums.EmissionStandardEnum;
import com.haoqi.magic.system.model.entity.SysCarModel;
import com.haoqi.magic.system.model.vo.CarModelVO;
import com.haoqi.magic.system.service.ISysCarModelService;
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

import java.util.Objects;


/**
 * <p>
 * 车型表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/carModel")
public class SysCarModelController extends BaseController {


    @Autowired
    private ISysCarModelService sysCarModelService;

    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 功能描述:
     * 开启 禁用
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/4/28 17:59
     * @Description:
     */
    @GetMapping(value = "/setValid")
    @ApiOperation(value = "设置有效(车型)")
    public Result<Object> setCarModelValid(@RequestParam("id") Long id, @RequestParam("isDeleted") Integer isDeleted) {
        return Result.buildSuccessResult(sysCarModelService.setCarModelValid(id, isDeleted));
    }


    @GetMapping(value = "/getCarModelById/{id}")
    @ApiOperation(value = "根据id获取车型")
    public Result<Object> getCarModelById(@PathVariable("id") Long id) {
        SysCarModel data = sysCarModelService.selectById(id);
        if (Objects.nonNull(data)) {
            if (Objects.nonNull(data.getDischargeStandard())) {
                data.setEmissionStandardCode(EmissionStandardEnum.getTypeCode(data.getDischargeStandard()));
            }
        }
        return Result.buildSuccessResult(data);
    }


    @PostMapping(value = "/addCarModel")
    @ApiOperation(value = "新增车辆车型(三级)")
    public Result<String> addCarModel(@RequestBody CarModelVO vo) {
        validatorHandler.validator(vo);
        sysCarModelService.addCarModel(vo);
        return Result.buildSuccessResult("车型保存成功");
    }

    //这个东西并无什么用,因为数据库全量copy,会把原来的覆盖(懒得做)
    @PutMapping(value = "/updateCarModel")
    @ApiOperation(value = "更新车辆车型(三级)")
    public Result<String> updateCarModel(@RequestBody CarModelVO vo) {
        validatorHandler.validator(vo);
        sysCarModelService.updateCarModel(vo);
        return Result.buildSuccessResult("车型保存成功");
    }
}

