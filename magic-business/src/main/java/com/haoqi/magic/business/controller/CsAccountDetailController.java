package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.model.dto.AccountDetailDTO;
import com.haoqi.magic.business.model.dto.AccountDetailVO;
import com.haoqi.magic.business.model.dto.AccountTotalAmountDTO;
import com.haoqi.magic.business.service.ICsAccountDetailService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>
 * 账单明细表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-12-02
 */
@RestController
@Api(tags = "账单明细")
@RequestMapping("/csAccountDetail")
public class CsAccountDetailController extends BaseController {
    @Autowired
    private ICsAccountDetailService accountDetailService;

    /**
     * 分页查询账单明细列表
     *
     * @param time
     * @param type
     * @return
     */
    @PostMapping("findPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前分页数", required = true),
            @ApiImplicitParam(name = "limit", value = "分页数", required = true),
            @ApiImplicitParam(name = "time", value = "日期", required = true),
            @ApiImplicitParam(name = "type", value = "类型")
    })
    @ApiOperation("分页查询账单明细")
    public Result<Page> findPage(@RequestParam(value = "page") Integer page, @RequestParam(value = "limit") Integer limit,
                                 @RequestParam("time") String time, @RequestParam(value = "type", required = false) Integer type) {
        UserInfo userInfo = currentUser();
        Map<String, Object> param = Maps.newHashMap();
        param.put("page", page);
        param.put("limit", limit);
        param.put("tradeType", type);
        param.put("userId", userInfo.getId());
        param.put("descs", Arrays.asList("gmt_create"));
        param.put("timeStart", DateUtil.beginOfMonth(DateUtil.parseDate(time)).toString());
        param.put("timeEnd", DateUtil.endOfMonth(DateUtil.parseDate(time)).toString());
        Page<AccountDetailDTO> accountDetailPage = accountDetailService.findPage(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(accountDetailPage, AccountDetailVO.class));
    }


    /**
     * 统计收入、支出
     *
     * @param time
     * @param type
     * @return
     */
    @PostMapping("totalAmount")
    @ApiOperation("统计收入、支出")
    public Result<AccountTotalAmountDTO> totalAmount(@RequestParam("time") String time, @RequestParam(value = "type", required = false) Integer type) {
        UserInfo userInfo = currentUser();
        String timeStart = DateUtil.beginOfMonth(DateUtil.parseDate(time)).toString();
        String timeEnd = DateUtil.endOfMonth(DateUtil.parseDate(time)).toString();
        return Result.buildSuccessResult(accountDetailService.totalAmount(timeStart, timeEnd, type, userInfo.getId()));
    }


    private <T> Page<T> dtoConvertToVo(Page<?> page, Class<T> clazz) {
        Page<T> result = new Page<T>();
        BeanUtil.copyProperties(page, result, "records");
        result.setRecords(BeanUtils.beansToList(page.getRecords(), clazz));
        return result;
    }
}

