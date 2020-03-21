package com.haoqi.magic.business.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsFilterDTO;
import com.haoqi.magic.business.model.entity.CsFilter;
import com.haoqi.magic.business.model.vo.CsFilterVO;
import com.haoqi.magic.business.service.ICsFilterService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 筛选管理表 前端控制器
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
@RestController
@RequestMapping("/csFilter")
@Api(tags = "筛选信息Controller")
public class CsFilterController extends BaseController {

    @Autowired
    private ICsFilterService csFilterService;

    //校验
    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 分页筛选列表
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/4/25 15:54
     */
    @PostMapping("/csFilterPage")
    @ApiOperation(value = "分页筛选列表")
    public Result<Page> csFilterPage(@RequestBody CsFilterVO param) {
        Map<String, Object> params = new HashMap<>();
        params.put("filterName", StrUtil.emptyToNull(param.getFilterName()));
        params.put("filterType", param.getFilterType());
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        return Result.buildSuccessResult(csFilterService.findPage(query));

    }


    /**
     * 新增筛选数据
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/26 17:23
     */
    @PostMapping("/addCsFilter")
    @ApiOperation(value = "新增筛选数据")
    public Result<String> addCsFilter(@RequestBody CsFilterVO vo) {
        validatorHandler.validator(vo);
        csFilterService.insert(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过ID更新筛选信息
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/26 17:25
     */
    @PostMapping("/editCsFilter")
    @ApiOperation(value = "通过ID更新筛选信息")
    public Result<String> editCsFilter(@RequestBody CsFilterVO vo) {
        validatorHandler.validator(vo);
        csFilterService.updateCsFilterById(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过IDs
     *
     * @param ids ids删除筛选信息
     * @return
     * @author huming
     * @date 2019/4/26 17:25
     */
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "ids删除筛选信息")
    public Result<String> deleteByIds(@PathVariable("ids") String ids) {
        List<Long> list = Arrays.stream(StrUtil.split(ids, StrUtil.COMMA)).map(o -> Long.parseLong(o)).collect(Collectors.toList());
        csFilterService.deleteCsFilterByIds(list);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 通过ID获取筛选信息
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/4/26 14:09
     */
    @GetMapping("/getOne/{id}")
    @ApiOperation(value = "通过ID获取筛选信息")
    public Result<CsFilterDTO> getOneById(@PathVariable("id") Long id) {
        return Result.buildSuccessResult(csFilterService.getOneById(id));

    }

    /**
     * 获取全部的筛选信息
     *
     * @return
     * @author huming
     * @date 2019/4/26 14:09
     */
    @GetMapping("/getAllCsFilter")
    @ApiOperation(value = "获取全部的筛选信息")
    public Result<List<CsFilter>> getAllCsFilter() {
        return Result.buildSuccessResult(csFilterService.getAllCsFilter());
    }


    /**
     * 获取全部的筛选信息通过map形式返回
     *
     * @return
     * @author huming
     * @date 2019/5/15 16:18
     */
    @GetMapping("/getMapCsFilter")
    @ApiOperation(value = "获取全部的筛选信息通过map形式返回")
    public Result<Map<String, List<CsFilterDTO>>> getMapCsFilter() {
        return Result.buildSuccessResult(csFilterService.getMapCsFilter());
    }


    /**
     * 通过条件获取标签数据
     *
     * @return
     * @author huming
     * @date 2019/4/30 15:08
     */
    @PostMapping("/getCsFilterWithCondition")
    @ApiOperation(value = "通过条件获取标签数据")
    public Result<List<CsFilterDTO>> getCsFilterWithCondition(@RequestBody CsFilterVO vo) {
        return Result.buildSuccessResult(csFilterService.getCsFilterWithCondition(vo));
    }

}

