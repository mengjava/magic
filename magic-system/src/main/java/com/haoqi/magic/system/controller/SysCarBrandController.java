package com.haoqi.magic.system.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.system.model.dto.CarBrand;
import com.haoqi.magic.system.model.dto.CfCarBrandDTO;
import com.haoqi.magic.system.model.entity.SysCarBrand;
import com.haoqi.magic.system.model.entity.SysCarModel;
import com.haoqi.magic.system.model.entity.SysCarSeries;
import com.haoqi.magic.system.model.vo.CarBrandPageQueryVO;
import com.haoqi.magic.system.model.vo.CarBrandVO;
import com.haoqi.magic.system.model.vo.CarModelPageQueryVO;
import com.haoqi.magic.system.model.vo.CarSeriesPageQueryVO;
import com.haoqi.magic.system.service.ISysCarBrandService;
import com.haoqi.magic.system.service.ISysCarModelService;
import com.haoqi.magic.system.service.ISysCarSeriesService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 车辆品牌统一管理 (品牌 车系 车型)
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/carBrand")
@Api(tags = "车辆品牌相关")
public class SysCarBrandController extends BaseController {

    @Autowired
    private ISysCarBrandService sysCarBrandService;

    @Autowired
    private ISysCarSeriesService sysCarSeriesService;

    @Autowired
    private ISysCarModelService sysCarModelService;

    @Autowired
    private BeanValidatorHandler validatorHandler;


    /**
     * 功能描述:
     * 分页品牌管理分页 (一级)
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/4/25 18:03
     * @Description:
     */

    @PostMapping("/carBrandPage")
    @ApiOperation(value = "分页获取品牌管理分页(品牌一级)")
    public Result<Page> carBrandPage(@RequestBody CarBrandPageQueryVO param) {
        currentUser();
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        return Result.buildSuccessResult(sysCarBrandService.selectCarBrandPage(params));
    }

    /**
     * 功能描述:
     * 车系管理分页 (二级)
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/4/26 9:43
     * @Description:
     */
    @PostMapping("/carSeriesPage")
    @ApiOperation(value = "分页获取车系管理分页(车系二级)")
    public Result<Page> carSeriesPage(@RequestBody CarSeriesPageQueryVO param) {
        currentUser();
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        return Result.buildSuccessResult(sysCarSeriesService.selectCarSeriesPage(params));
    }

    /**
     * 功能描述:
     * 车型管理分页 (三级)
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/4/26 10:09
     * @Description:
     */
    @PostMapping("/carModelPage")
    @ApiOperation(value = "分页获取车系管理分页(车型三级)")
    public Result<Page> carModelPage(@RequestBody CarModelPageQueryVO param) {
        currentUser();
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        return Result.buildSuccessResult(sysCarModelService.selectCarModelPage(params));
    }

    /**
     * 功能描述:
     * 一级车型列表
     *
     * @auther: yanhao
     * @param:
     * @date: 2018/11/9 17:15
     * @Description:
     */
    @GetMapping(value = "/getCarBrandList")
    @ApiOperation(value = "获取车300一级车品牌列表(APP使用-不分页)")
    public Result<List<SysCarBrand>> getCarBrandList() {
        return Result.buildSuccessResult(sysCarBrandService.selectCarBrandList());
    }

    /**
     * 二级车型列表
     * 通过车品牌获取车系信息
     *
     * @param brandId
     * @return
     */
    @GetMapping(value = "/getCarSeriesList/{brandId}")
    @ApiOperation(value = "获取车300二级通过车品牌获取车系")
    public Result<List<SysCarSeries>> getCarSeriesList(@PathVariable("brandId") Integer brandId) {
        return Result.buildSuccessResult(sysCarSeriesService.selectCarSeriesListByBrandId(brandId));
    }

    /**
     * 通过车系id获取车型信息
     * 三级
     *
     * @param
     * @return
     */
    @GetMapping(value = "/getCarModelList/{seriesId}")
    @ApiOperation(value = "获取车300三级通过车品牌获取车型信息")
    public Result<List<SysCarModel>> getCarModelList(@PathVariable("seriesId") Integer seriesId) {
        return Result.buildSuccessResult(sysCarModelService.selectCarModelListBySeriesId(seriesId));
    }

    /***
     * 获取指定车型信息
     * @param modelId
     * @return
     */
    @GetMapping(value = "/getCarModelByModelId/{modelId}")
    @ApiOperation(value = "获取车300通过车型modelId获取车型信息")
    public Result<SysCarModel> getCarModelByModelId(@PathVariable("modelId") Long modelId) {
        SysCarModel data = sysCarModelService.selectCarModelByModelId(modelId);
        return Result.buildSuccessResult(data);
    }


    /**
     * 功能描述:
     * 新增车辆品牌(一级) 重复性校验 首字母
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/4/28 14:58
     * @Description:
     */
    @PostMapping(value = "/addCarBrand")
    @ApiOperation(value = "新增车辆品牌(一级)")
    public Result<String> addCarBrand(@RequestBody CarBrandVO vo) {
        validatorHandler.validator(vo);
        sysCarBrandService.addCarBrand(vo);
        return Result.buildSuccessResult("品牌保存成功");
    }

    @PutMapping(value = "/updateCarBrand")
    @ApiOperation(value = "更新车辆品牌(一级)")
    public Result<String> updateCarBrand(@RequestBody CarBrandVO vo) {
        validatorHandler.validator(vo);
        sysCarBrandService.updateCarBrand(vo);
        return Result.buildSuccessResult("品牌保存成功");
    }


    /**
     * 功能描述:
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/4/28 16:20
     * @Description:
     */
    @GetMapping(value = "/setValid")
    @ApiOperation(value = "设置有效(品牌)")
    public Result<Object> setCarBrandValid(@RequestParam("id") Long id, @RequestParam("isDeleted") Integer isDeleted) {
        return Result.buildSuccessResult(sysCarBrandService.setCarBrandValid(id, isDeleted));
    }


    /**
     * 功能描述:
     * 车品牌排序分组
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/7/22 9:33
     * @Description:
     */

    @GetMapping(value = "/getBrandLetterList")
    @ApiOperation(value = "车品牌排序分组(品牌)")
    public Result<Object> getBrandList() {
        List<SysCarBrand> carBrandList = sysCarBrandService.selectCarBrandList();
        Map<String, List<CfCarBrandDTO>> result = new HashMap<String, List<CfCarBrandDTO>>();
        List<CarBrand> carList = new ArrayList<>();
        List<String> listString = new ArrayList<>();
        for (int i = 0; i < carBrandList.size(); i++) {
            if (result.get(carBrandList.get(i).getBrandInitial()) != null) {
                List<CfCarBrandDTO> cbList = result.get(carBrandList.get(i).getBrandInitial());
                CfCarBrandDTO cfCarBrand = new CfCarBrandDTO();
                cfCarBrand.setBrandNameMain(carBrandList.get(i).getBrandName());
                cfCarBrand.setBrandId(carBrandList.get(i).getBrandId());
                cbList.add(cfCarBrand);
            } else {
                List<CfCarBrandDTO> list = new ArrayList<>();
                CfCarBrandDTO cfCarBrand = new CfCarBrandDTO();
                cfCarBrand.setBrandNameMain(carBrandList.get(i).getBrandName());
                cfCarBrand.setBrandId(carBrandList.get(i).getBrandId());
                list.add(cfCarBrand);
                result.put(carBrandList.get(i).getBrandInitial(), list);
                listString.add(carBrandList.get(i).getBrandInitial());
            }
        }
        for (int a = 0; a < listString.size(); a++) {
            CarBrand carBrand = new CarBrand();
            carBrand.setLetter(listString.get(a));
            carBrand.setList(result.get(listString.get(a)));
            carList.add(carBrand);
        }
        return Result.buildSuccessResult(carList);
    }

}

