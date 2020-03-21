package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.model.dto.CsCashDTO;
import com.haoqi.magic.business.model.dto.SysConfigDTO;
import com.haoqi.magic.business.model.entity.CsCash;
import com.haoqi.magic.business.model.vo.CsCashParamVO;
import com.haoqi.magic.business.model.vo.CsCashVO;
import com.haoqi.magic.business.service.ICsCashService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 提现管理表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/csCash")
@Api(tags = "提现管理")
public class CsCashController extends BaseController {

    @Autowired
    private ICsCashService csCashService;
    @Autowired
    private SystemServiceClient systemServiceClient;

    @PostMapping("/page")
    @ApiOperation(value = "提现管理列表")
    public Result<Page> page(@RequestBody CsCashDTO dto) {
        Map<String, Object> params = Maps.newHashMap();
        BeanUtil.beanToMap(dto, params, false, true);
        return Result.buildSuccessResult(csCashService.findByPage(new Query(params)));
    }

    @GetMapping("getById/{id}")
    @ApiOperation(value = "审核详情")
    public Result<CsCashVO> getCashById(@PathVariable("id") Long id) {
        CsCash csCash = csCashService.selectById(id);
        CsCashVO csCashVO = BeanUtils.beanCopy(csCash, CsCashVO.class);
        return Result.buildSuccessResult(csCashVO);
    }

    @PutMapping("/edit")
    @ApiOperation(value = "审核")
    public Result<String> update(@RequestBody CsCashDTO dto) {
        validatorHandler.validator(dto);
        Boolean success = csCashService.audit(BeanUtils.beanCopy(dto, CsCash.class));
        return success ? Result.buildSuccessResult("审核成功") : Result.buildErrorResult("审核失败");
    }


    /**
     * 显示提现金额最小金额
     *
     * @return
     */
    @GetMapping("enable/amount")
    @ApiOperation(value = "显示可提现金额最小金额")
    public Result<String> enableApplyCashAmount() {
        currentUser();
        //提现金额
        Result<SysConfigDTO> result20 = systemServiceClient.getByTypeAndName(2, 20);
        if (StrUtil.isBlank(result20.getData().getGlobalValue())) {
            throw new RiggerException("提现金额设置不正确");
        }
        return Result.buildSuccessResult(result20.getData().getGlobalValue());
    }

    /**
     * 显示申请提现的金额
     *
     * @return
     */
    @GetMapping("apply/amount")
    @ApiOperation(value = "显示申请提现的金额")
    public Result<BigDecimal> applyCashAmount() {
        UserInfo userInfo = currentUser();
        return Result.buildSuccessResult(csCashService.applyCashAmount(userInfo.getId()));
    }

    /**
     * 申请提现
     *
     * @param cashParam
     * @return
     */
    @PostMapping("/apply")
    @ApiOperation(value = "提现申请")
    public Result<String> applyCash(@RequestBody CsCashParamVO cashParam) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(cashParam);
        csCashService.applyCash(userInfo.getId(), userInfo.getRealName(), cashParam.getBankId(), cashParam.getMoney(), cashParam.getDevice(), cashParam.getSource());
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 提现记录列表
     *
     * @param date
     * @param status
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "提现记录列表")
    public Result<Page> list(@RequestParam("date") String date, @RequestParam(value = "status", required = false) Integer status) {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", userInfo.getId());
        params.put("cashAuditStatus", status);
        params.put("date", DateUtil.beginOfMonth(DateUtil.parseDate(date)));
        return Result.buildSuccessResult(csCashService.applyCashByPage(new Query(params)));
    }

    /**
     * 合计
     *
     * @param date
     * @param status
     * @return
     */
    @PostMapping("totalAmount")
    @ApiOperation(value = "合计")
    public Result<BigDecimal> totalAmount(@RequestParam("date") String date, @RequestParam(value = "status", required = false) Integer status) {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = Maps.newHashMap();
        params.put("userId", userInfo.getId());
        params.put("cashAuditStatus", status);
        params.put("date", DateUtil.beginOfMonth(DateUtil.parseDate(date)));
        return Result.buildSuccessResult(csCashService.totalAmount(new Query(params)));
    }

}

