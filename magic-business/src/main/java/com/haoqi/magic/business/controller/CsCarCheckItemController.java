package com.haoqi.magic.business.controller;


import com.haoqi.magic.business.model.dto.CsCarCheckItemDTO;
import com.haoqi.magic.business.model.entity.CsCarCheckItem;
import com.haoqi.magic.business.model.vo.CarCheckItemTree;
import com.haoqi.magic.business.service.ICsCarCheckItemService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 车辆检测项 前端控制器
 * </p>
 * 事故检查
 * 外观检查
 * 检测信息
 *
 * @author yanhao
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/carCheckItem")
@Api(tags = "事故检查-外观检查-检测信息配置Controller")
public class CsCarCheckItemController extends BaseController {


    @Autowired
    private ICsCarCheckItemService csCarCheckItemService;
    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 功能描述:
     * 获取树形事故检查外观检测信息
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/5/5 18:43
     * @Description:
     */
    @GetMapping(value = "/tree")
    @ApiOperation(value = "获取树形事故检查外观检测信息")
    public Result<List<CarCheckItemTree>> getTree() {
        currentUser();
        return Result.buildSuccessResult(csCarCheckItemService.findByType(null));
    }

    @GetMapping(value = "/typeTree")
    @ApiOperation(value = "根据类型获取树形事故检查外观检测信息")
    public Result<List<CarCheckItemTree>> getTreeByType(@RequestParam("type") Integer type) {
        currentUser();
        return Result.buildSuccessResult(csCarCheckItemService.findByType(type));
    }


    /**
     * 功能描述:
     * 添加 1事故检测，2外观检测，3检测信息
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/5/5 18:46
     * @Description:
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加type: 1事故检测，2外观检测，3检测信息")
    public Result<String> addCarCheckItem(@RequestBody CsCarCheckItemDTO vo) {
        currentUser();
        validatorHandler.validator(vo);
        csCarCheckItemService.saveOrUpdate(BeanUtils.beanCopy(vo, CsCarCheckItem.class));
        return Result.buildSuccessResult("添加成功");
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "通过id删除检测数据")
    public Result<Boolean> delCarCheckItemById(@PathVariable Long id) {
        currentUser();
        return Result.buildSuccessResult(csCarCheckItemService.delCarCheckItemById(id));
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "通过id获取检测数据")
    public Result<CsCarCheckItem> getCarCheckItemById(@PathVariable Long id) {
        currentUser();
        return Result.buildSuccessResult(csCarCheckItemService.getCarCheckItemById(id));
    }

    @PutMapping("/update")
    @ApiOperation(value = "更新检测数据")
    public Result<String> updateDict(@RequestBody CsCarCheckItemDTO vo) {
        currentUser();
        validatorHandler.validator(vo);
        csCarCheckItemService.saveOrUpdate(BeanUtils.beanCopy(vo, CsCarCheckItem.class));
        return Result.buildSuccessResult("更新成功");
    }


}

