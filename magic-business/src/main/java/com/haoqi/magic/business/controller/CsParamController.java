package com.haoqi.magic.business.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsParamDTO;
import com.haoqi.magic.business.model.entity.CsParam;
import com.haoqi.magic.business.model.vo.CsParamVO;
import com.haoqi.magic.business.service.ICsParamService;
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
 * 标签管理表 前端控制器
 * </p>
 *
 * @author huming
 * @since 2019-04-26
 */
@RestController
@RequestMapping("/csParam")
@Api(tags = "自定义参数管理Controller")
public class CsParamController extends BaseController {

    @Autowired
    private ICsParamService csParamService;

    //校验
    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 分页获取自定义参数信息
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/4/25 15:54
     */
    @PostMapping("/csParamPage")
    @ApiOperation(value = "分页自定义参数列表")
    public Result<Page> csParamPage(@RequestBody CsParamVO param) {
        Map<String, Object> params = new HashMap<>();
        params.put("paramName", param.getParamName());
        params.put("minPriceStart", param.getMinPriceStart());
        params.put("minPriceEnd", param.getMinPriceEnd());
        params.put("maxPriceStart", param.getMaxPriceStart());
        params.put("maxPriceEnd", param.getMaxPriceEnd());
        params.put("minTravelDistanceStart", param.getMinTravelDistanceStart());
        params.put("minTravelDistanceEnd", param.getMinTravelDistanceEnd());
        params.put("maxTravelDistanceStart", param.getMaxTravelDistanceStart());
        params.put("maxTravelDistanceEnd", param.getMaxTravelDistanceEnd());
        params.put("carAge", param.getCarAge());
        params.put("carTypeCode", param.getCarTypeCode());
        params.put("auditTime", param.getAuditTime());
        params.put("transferNum", param.getTransferNum());
        params.put("creditUnion", param.getCreditUnion());
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        Page page = csParamService.findPage(query);
        return Result.buildSuccessResult(page);

    }


    /**
     * 新增自定义参数数据
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/26 14:11
     */
    @PostMapping("/addCsParam")
    @ApiOperation(value = "新增自定义参数数据")
    public Result<String> addCsParam(@RequestBody CsParamVO vo) {
        validatorHandler.validator(vo);
        csParamService.insert(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过ID更新自定义参数信息
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/26 14:11
     */
    @PostMapping("/editCsParam")
    @ApiOperation(value = "通过ID更新自定义参数信息")
    public Result<String> editCsParam(@RequestBody CsParamVO vo) {
        validatorHandler.validator(vo);
        csParamService.updateCsParamById(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过IDs
     *
     * @param ids ids删除自定义参数信息
     * @return
     * @author huming
     * @date 2019/4/26 14:10
     */
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "ids删除自定义参数信息")
    public Result<String> deleteByIds(@PathVariable("ids") String ids) {
        List<Long> list = Arrays.stream(StrUtil.split(ids, StrUtil.COMMA)).map(o -> Long.parseLong(o)).collect(Collectors.toList());
        csParamService.deleteCsParamByIds(list);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 通过ID获取自定义参数数据
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/4/26 14:09
     */
    @GetMapping("/getOne/{id}")
    @ApiOperation(value = "通过ID获取自定义参数数据")
    public Result<CsParamDTO> getOneById(@PathVariable("id") Long id) {
        CsParamDTO vo = csParamService.getOneById(id);
        return Result.buildSuccessResult(vo);

    }

    /**
     * 获取全部的自定义参数数据
     *
     * @return
     * @author huming
     * @date 2019/4/26 14:09
     */
    @GetMapping("/getAllCsParam")
    @ApiOperation(value = "获取全部自定义参数数据")
    public Result<List<CsParam>> getAllCsParam() {
        return Result.buildSuccessResult(csParamService.getAllCsParam());
    }

}

