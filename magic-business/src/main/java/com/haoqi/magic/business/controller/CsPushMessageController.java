package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.entity.CsPushMessage;
import com.haoqi.magic.business.model.vo.PushMessagePageVO;
import com.haoqi.magic.business.service.ICsPushMessageService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.mybatis.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.haoqi.rigger.web.controller.BaseController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 消息推送表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-12-10
 */
@RestController
@RequestMapping("/pushMessage")
@Api(tags = "消息管理")
public class CsPushMessageController extends BaseController {

    @Autowired
    private ICsPushMessageService csPushMessageService;


    @PostMapping("/page")
    @ApiOperation(value = "分页查询某个客户下最新消息")
    public Result<Page> selectByPage(@RequestBody PushMessagePageVO param) {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        return Result.buildSuccessResult(csPushMessageService.selectByPage(new Query(params)));
    }


    /**
     * 设置已读
     *
     * @param id
     * @return
     */
    @PutMapping("/editIsRead/{id}")
    @ApiOperation(value = "通过id,修改已读取消息")
    public Result<String> editIsRead(@PathVariable("id") Long id) {
        CsPushMessage csPushMessage = new CsPushMessage();
        csPushMessage.setId(id);
        csPushMessage.setIsRead(Constants.YES);
        boolean b = csPushMessageService.updateById(csPushMessage);
        return b ? Result.buildSuccessResult("操作成功") : Result.buildErrorResult("操作失败");
    }


    /**
     * 删除消息
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delMessageById/{id}")
    @ApiOperation(value = "根据删除消息")
    public Result<String> delMessageById(@PathVariable("id") Long id) {
        CsPushMessage csPushMessage = new CsPushMessage();
        csPushMessage.setId(id);
        csPushMessage.setIsDeleted(Constants.YES);
        boolean b = csPushMessageService.updateById(csPushMessage);
        return b ? Result.buildSuccessResult("操作成功") : Result.buildErrorResult("操作失败");
    }
}

