package com.haoqi.magic.business.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.dto.CsTagDTO;
import com.haoqi.magic.business.model.entity.CsTag;
import com.haoqi.magic.business.model.vo.CsTagVO;
import com.haoqi.magic.business.service.ICsTagService;
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
 * 标签管理 前端控制器
 * </p>
 *
 * @author huming
 * @since 2019-04-30
 */
@RestController
@RequestMapping("/csTag")
@Api(tags = "标签Controller")
public class CsTagController
        extends BaseController {

    @Autowired
    private ICsTagService csTagService;

    //校验
    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 分页标签列表
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/4/30 15:01
     */
    @PostMapping("/csTagPage")
    @ApiOperation(value = "分页标签列表")
    public Result<Page> csTagPage(@RequestBody CsTagVO param) {
        Map<String, Object> params = new HashMap<>();
        params.put("tagName", StrUtil.emptyToNull(param.getTagName()));
        params.put("type", param.getType());
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        Page page = csTagService.findPage(query);
        return Result.buildSuccessResult(page);

    }


    /**
     * 新增标签数据
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/30 15:01
     */
    @PostMapping("/addCsTag")
    @ApiOperation(value = "新增标签数据")
    public Result<String> addCsTag(@RequestBody CsTagVO vo) {
        validatorHandler.validator(vo);
        csTagService.insert(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过ID更新标签信息
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/30 15:04
     */
    @PostMapping("/editCsTag")
    @ApiOperation(value = "通过ID更新筛选信息")
    public Result<String> editCsTag(@RequestBody CsTagVO vo) {
        validatorHandler.validator(vo);
        csTagService.updateCsTagById(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过IDs删除标签信息
     *
     * @param ids ids
     * @return
     * @author huming
     * @date 2019/4/30 15:05
     */
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "通过IDs删除标签信息")
    public Result<String> deleteByIds(@PathVariable("ids") String ids) {
        List<Long> list = Arrays.stream(StrUtil.split(ids, StrUtil.COMMA)).map(o -> Long.parseLong(o)).collect(Collectors.toList());
        csTagService.deleteCsTagByIds(list);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 通过ID获取标签数据
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/4/30 15:06
     */
    @GetMapping("/getOne/{id}")
    @ApiOperation(value = "通过ID获取标签数据")
    public Result<CsTagDTO> getOneById(@PathVariable("id") Long id) {
        CsTagDTO vo = csTagService.getOneById(id);
        return Result.buildSuccessResult(vo);

    }

    /**
     * 获取全部的标签数据
     *
     * @return
     * @author huming
     * @date 2019/4/26 14:09
     */
    @GetMapping("/getAllCsTag")
    @ApiOperation(value = "获取全部的筛选信息")
    public Result<List<CsTag>> getAllCsTag() {
        return Result.buildSuccessResult(csTagService.getAllCsTag());
    }


    /**
     * 通过条件获取标签数据
     *
     * @return
     * @author huming
     * @date 2019/4/30 15:08
     */
    @PostMapping("/getCsTagWithCondition")
    @ApiOperation(value = "通过条件获取标签数据")
    public Result<List<CsTag>> getCsTagWithCondition(@RequestBody CsTagVO vo) {
        return Result.buildSuccessResult(csTagService.getCsTagWithCondition(vo));
    }

}

