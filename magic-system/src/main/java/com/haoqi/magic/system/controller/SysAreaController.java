package com.haoqi.magic.system.controller;


import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Maps;
import com.haoqi.magic.system.common.enums.RegionLevelTypeEnum;
import com.haoqi.magic.system.model.dto.CsAppHotCityDTO;
import com.haoqi.magic.system.model.dto.SysAreaDTO;
import com.haoqi.magic.system.model.dto.SysProvinceAndCityDTO;
import com.haoqi.magic.system.service.ISysAreaService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 城市区域表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/base/sysArea")
@Api(tags = "区域管理类")
public class SysAreaController extends BaseController {

    @Autowired
    private ISysAreaService sysAreaService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;

    /**
     * 功能描述:获取省份列表
     *
     * @param
     * @return java.util.List<com.haoqi.magic.system.model.dto.SysAreaDTO>
     * @auther mengyao
     * @date 2019/4/29 0029 上午 10:33
     */
    @ApiOperation(value = "获取省份列表")
    @PostMapping("getProvinceList")
    public Result<List<SysAreaDTO>> getProvinceList() {
        String provinceKey = String.format("%s:%s", prefix, "province");
        List<SysAreaDTO> sysAreaProvinceList = (List<SysAreaDTO>) redisTemplate.opsForValue().get(provinceKey);
        if (CollectionUtil.isNotEmpty(sysAreaProvinceList)) {
            return Result.buildSuccessResult(sysAreaProvinceList);
        }
        Map maps = Maps.newHashMap();
        maps.put("level", RegionLevelTypeEnum.PROVINCE.getKey());
        List<SysAreaDTO> sysAreaDTOS = sysAreaService.getSysAreaDTOS(maps);
        if (CollectionUtil.isNotEmpty(sysAreaDTOS)) {
            redisTemplate.opsForValue().set(provinceKey, sysAreaDTOS, 30, TimeUnit.DAYS);
        }
        return Result.buildSuccessResult(sysAreaDTOS);
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "获取省份和城市code")
    public Result<SysProvinceAndCityDTO> getAreaById(@PathVariable("id") Long id) {
        return Result.buildSuccessResult(sysAreaService.getAreaById(id));
    }

    @GetMapping("/getByCode/{code}")
    @ApiOperation(value = "获取省份和城市code")
    public Result<SysProvinceAndCityDTO> getAreaByCityCode(@PathVariable("code") String code) {
        return Result.buildSuccessResult(sysAreaService.getAreaByCityCode(code));
    }

    /**
     * 通过ID获取省份和城市
     *
     * @param id 地区ID
     * @return
     * @author huming
     * @date 2019/5/30 16:18
     */
    @GetMapping("/getById/{id}")
    @ApiOperation(value = "通过ID获取省份和城市")
    public Result<SysProvinceAndCityDTO> getAreaByCityId(@PathVariable("id") Long id) {
        return Result.buildSuccessResult(sysAreaService.getAreaByCityId(id));
    }


    /***
     * 热门城市
     */
    @GetMapping("/hotCity")
    @ApiOperation(value = "热门城市")
    public Result<List<CsAppHotCityDTO>> getHotCity() {
        return Result.buildSuccessResult(sysAreaService.getHotCity());
    }

    /***
     * 获取所有城市带首字母
     */
    @GetMapping("/getAllCity")
    @ApiOperation(value = "获取所有城市(带首字母)")
    public Result<List<CsAppHotCityDTO>> getAllCity() {
        return Result.buildSuccessResult(sysAreaService.getAllCity());
    }


    /**
     * 测试类生成首字母-上线后使用
     */
    @GetMapping("/makeInitial")
    public Result<Object> makeInitial() {
        return Result.buildSuccessResult(sysAreaService.makeInitial());
    }

}

