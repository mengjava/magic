package com.haoqi.magic.business.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.vo.CsLoanCreditVO;
import com.haoqi.magic.business.service.ICsLoanCreditService;
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
 * 分期表 前端控制器
 * </p>
 *
 * @author huming
 * @since 2019-05-05
 */
@RestController
@RequestMapping("/csLoanCredit")
@Api(tags = "后台分期Controller")
public class CsLoanCreditController
        extends BaseController {

    @Autowired
    private ICsLoanCreditService csLoanCreditService;

    /**
     * 分页获取分期数据
     * @param param
     * @return
     * @author huming
     * @date 2019/5/5 10:30
     */
    @PostMapping("/csLoanCreditPage")
    @ApiOperation(value = "分页获取分期数据")
    public Result<Page> csLoanCreditPage(@RequestBody CsLoanCreditVO param) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyWord", StrUtil.emptyToNull(param.getKeyWord()));
        params.put("applyTimeStart",StrUtil.isBlank(param.getApplyTimeStart()) ? StrUtil.emptyToNull(""):
                DateUtil.beginOfDay(DateUtil.parseDate(param.getApplyTimeStart())));
        params.put("applyTimeEnd",StrUtil.isBlank(param.getApplyTimeEnd()) ? StrUtil.emptyToNull(""):
                DateUtil.endOfDay(DateUtil.parseDate(param.getApplyTimeEnd())));
        params.put("dealerName",StrUtil.emptyToNull(param.getDealerName()));
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        Page page = csLoanCreditService.findPage(query);
        return Result.buildSuccessResult(page);

    }
}

