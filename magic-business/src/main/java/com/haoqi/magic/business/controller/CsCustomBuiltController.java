package com.haoqi.magic.business.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.vo.CsCustomBuiltVO;
import com.haoqi.magic.business.service.ICsCustomBuiltService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 客户定制表 前端控制器
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/csCustomBuilt")
@Api(tags = "后台定制Controller")
public class CsCustomBuiltController extends BaseController {

    @Autowired
    private ICsCustomBuiltService csCustomBuiltService;

    /**
     * 分页获取定制列表信息
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/5/5 14:07
     */
    @PostMapping("/csCustomBuiltPage")
    @ApiOperation(value = "分页获取定制列表信息")
    public Result<Page> csCustomBuiltPage(@RequestBody CsCustomBuiltVO param) {
        currentUser();
        Map<String, Object> params = new HashMap<>();
        params.put("applyTimeStart", StrUtil.isBlank(param.getApplyTimeStart()) ? StrUtil.emptyToNull("") :
                DateUtil.beginOfDay(DateUtil.parseDate(param.getApplyTimeStart())));
        params.put("applyTimeEnd", StrUtil.isBlank(param.getApplyTimeEnd()) ? StrUtil.emptyToNull("") :
                DateUtil.endOfDay(DateUtil.parseDate(param.getApplyTimeEnd())));
        params.put("sysCarBrandId", param.getSysCarBrandId());
        params.put("dealerName", StrUtil.emptyToNull(param.getDealerName()));
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        Page page = csCustomBuiltService.findPage(query);
        return Result.buildSuccessResult(page);

    }
}

