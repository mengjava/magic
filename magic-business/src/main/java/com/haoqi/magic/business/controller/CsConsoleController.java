package com.haoqi.magic.business.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.model.vo.ConsolePageVO;
import com.haoqi.magic.business.service.ICsCarInfoService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 车辆信息表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/console")
@Api(tags = "控制台")
public class CsConsoleController extends BaseController {
    @Autowired
    private ICsCarInfoService carInfoService;


    /**
     * 控制台
     *
     * @param consolePageVO
     * @return
     */
    @PostMapping("consolePage")
    @ApiOperation(value = "控制台")
    public Result<Page> carPage(@RequestBody ConsolePageVO consolePageVO) {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = Maps.newHashMap();
        params.put("userType", userInfo.getUserType());
        params.put("userId", userInfo.getId());
        params.put("page", consolePageVO.getPage());
        params.put("limit", consolePageVO.getLimit());
	    params.put("carNo", StrUtil.emptyToNull(consolePageVO.getCarNo()));
	    params.put("vin", StrUtil.emptyToNull(consolePageVO.getVin()));
	    params.put("plateNo", StrUtil.emptyToNull(consolePageVO.getPlateNo()));
	    params.put("sysCarModelName", StrUtil.emptyToNull(consolePageVO.getSysCarModelName()));

        params.put("gearBoxCode", StrUtil.emptyToNull(consolePageVO.getGearBoxCode()));
        params.put("carDealerId", consolePageVO.getCarDealerId());
        params.put("publishStatus", consolePageVO.getPublishStatus());

        params.put("publishStartDate", consolePageVO.getPublishStartDate());
        params.put("publishEndDate", consolePageVO.getPublishEndDate());

	    params.put("emissionStandardCode", StrUtil.emptyToNull(consolePageVO.getEmissionStandardCode()));

        params.put("auditStartDate", consolePageVO.getAuditStartDate());
        params.put("auditEndDate", consolePageVO.getAuditEndDate());

        params.put("transferHandleStartDate", consolePageVO.getTransferHandleStartDate());
        params.put("transferHandleEndDate", consolePageVO.getTransferHandleEndDate());

        params.put("checkUserId", consolePageVO.getCheckUserId());
        params.put("auditUserId", consolePageVO.getAuditUserId());
	    params.put("descs", Arrays.asList("audit_time"));
        return Result.buildSuccessResult(carInfoService.findConsoleByPage(new Query(params)));
    }


}

