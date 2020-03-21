package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.model.dto.CsVipDTO;
import com.haoqi.magic.business.model.dto.CsVipRecordDetailDTO;
import com.haoqi.magic.business.service.ICsVipRecordDetailService;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.mybatis.Query;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.haoqi.rigger.web.controller.BaseController;

import java.util.Map;

/**
 * <p>
 * 用户会员查询记录表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@RestController
@RequestMapping("/csVipRecordDetail")
public class CsVipRecordDetailController extends BaseController {

    @Autowired
    private ICsVipRecordDetailService csVipRecordDetailService;

    @PostMapping("/page")
    @ApiOperation(value = "会员查询历史记录")
    public Result<Page> page(@RequestBody CsVipRecordDetailDTO dto){
        UserInfo userInfo = currentUser();
        Map<String, Object> params = Maps.newHashMap();
        params.put("sysUserId",userInfo.getId());
        BeanUtil.beanToMap(dto, params, false, true);
        return Result.buildSuccessResult(csVipRecordDetailService.findByPage(new Query(params)));
    }


}

