package com.haoqi.magic.system.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.system.model.dto.SysHotDeployDTO;
import com.haoqi.magic.system.model.vo.SysHotDeployVO;
import com.haoqi.magic.system.service.ISysHotDeployService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * APP热更新管理 前端控制器
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/sysHotDeploy")
@Api(tags = "APP热发布Controller")
public class SysHotDeployController
        extends BaseController {

    //热发布服务
    @Autowired
    private ISysHotDeployService sysHotDeployService;

    //校验
    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 分页获取热发布信息
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/4/25 15:54
     */
    @PostMapping("/hotDeployPage")
    @ApiOperation(value = "分页热发布列表")
    public Result<Page> hotDeployPage(@RequestBody SysHotDeployVO param) {

        Map<String, Object> params = new HashMap<>();

        if (StrUtil.isNotEmpty(param.getName())) {
            params.put("name", param.getName());
        }

        if (null != param.getIsDeleted()) {
            params.put("isDeleted", param.getIsDeleted());
        }
        if (StrUtil.isNotBlank(param.getStartDate())) {
            params.put("startDate",
                    DateUtil.formatDateTime(DateUtil.beginOfDay(DateUtil.parseDate(param.getStartDate()))));
        }
        if (StrUtil.isNotBlank(param.getType())) {
            params.put("type", param.getType());
        }
        if (StrUtil.isNotBlank(param.getEndDate())) {
            params.put("endDate", DateUtil.formatDateTime(DateUtil.endOfDay(DateUtil.parseDate(param.getEndDate()))));
        }
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        Page page = sysHotDeployService.findPage(query);
        return Result.buildSuccessResult(page);

    }

    /**
     * 新增热发布
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/25 15:55
     */
    @PostMapping("/addHotDeploy")
    @ApiOperation(value = "插入热更新信息")
    public Result<String> addApp(@RequestBody @Valid SysHotDeployVO vo) {
        validatorHandler.validator(vo);
        sysHotDeployService.insert(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 更新热更新信息
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/4/25 15:55
     */
    @PostMapping("/editHotDeploy")
    @ApiOperation(value = "更新热更新信息")
    public Result<String> updateApp(@RequestBody SysHotDeployVO vo) {
//        validatorHandler.validator(vo);
        sysHotDeployService.updateAppById(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 删除热发布信息
     *
     * @param ids
     * @return
     * @author huming
     * @date 2019/4/25 15:55
     */
    @DeleteMapping("/delete/{ids}")
    @ApiOperation(value = "热更新删除")
    public Result<String> delete(@PathVariable("ids") String ids) {
        String[] deIds = ids.split(",");
        for (int i = 0; i < deIds.length; i++) {
            sysHotDeployService.deleteHotDeploy(Long.valueOf(deIds[i]));
        }
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 获取热发布信息
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/4/25 15:57
     */
    @GetMapping("/getHotDeploy/{id}")
    @ApiOperation(value = "根据id获取更新信息")
    public Result<SysHotDeployVO> delete(@RequestParam("id") Long id) {
        SysHotDeployVO vo = sysHotDeployService.getHotDeploy(id);
        return Result.buildSuccessResult(vo);

    }

    /**
     * 获取全部App类型
     *
     * @return
     * @author huming
     * @date 2019/4/25 15:58
     */
    @GetMapping("/getAllAppType")
    @ApiOperation(value = "获取全部App类型")
    public Result<List<SysHotDeployDTO>> getAllAppType() {
        return Result.buildSuccessResult(sysHotDeployService.getAllAppType());
    }

}

