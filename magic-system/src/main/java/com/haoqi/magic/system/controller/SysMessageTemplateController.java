package com.haoqi.magic.system.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.system.model.dto.SysMessageTemplateDTO;
import com.haoqi.magic.system.model.entity.SysMessageTemplate;
import com.haoqi.magic.system.model.vo.SysMessageTemplateEditVO;
import com.haoqi.magic.system.model.vo.SysMessageTemplatePageVO;
import com.haoqi.magic.system.model.vo.SysMessageTemplateVO;
import com.haoqi.magic.system.service.ISysMessageTemplateService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 消息模板 前端控制器
 * </p>
 *
 * @author huming
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/sysMessageTemplate")
@Api(tags = "短信模板配置Controller")
public class SysMessageTemplateController extends BaseController {

    @Autowired
    private ISysMessageTemplateService sysMessageTemplateService;

    //校验
    @Autowired
    private BeanValidatorHandler validatorHandler;


    /**
     * 分页查询消息模板
     *
     * @return 分页对象
     * @author huming
     * @date 2019/4/25 16:53
     */
    @PostMapping("/page")
    @ApiOperation(value = "分页获取消息模板")
    public Result<Page> messageTemplatePage(@RequestBody SysMessageTemplatePageVO paramVO) {
        Map<String, Object> params = new HashMap<>();
        setUserLevelParam(params, currentUser());
        params.put("isDeleted", CommonConstant.STATUS_NORMAL);
        params.put("page", paramVO.getPage());
        params.put("limit", paramVO.getLimit());
        if (StrUtil.isNotEmpty(paramVO.getKeyword())) {
            params.put("keyword", paramVO.getKeyword());
        }
        return Result.buildSuccessResult(sysMessageTemplateService.findMessageTemplateByPage(new Query(params)));
    }

    /**
     * 通过模板id获取模板信息
     *
     * @param id 模板主键id
     * @return
     * @author huming
     * @date 2019/4/25 16:53
     */
    @GetMapping("/selectById/{id}")
    @ApiOperation(value = "通过id获取模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息模板id", required = true, paramType = "path"),
    })
    public Result<SysMessageTemplateDTO> selectById(@PathVariable("id") Long id) {
        SysMessageTemplate param = new SysMessageTemplate();
        param.setId(id);
        SysMessageTemplate messageTemplate =
                sysMessageTemplateService.selectOne(new EntityWrapper<SysMessageTemplate>(param));
        return Result.buildSuccessResult(BeanUtils.beanCopy(messageTemplate, SysMessageTemplateDTO.class));
    }

    /**
     * 通过模板code获取模板信息
     *
     * @param code 模板code
     * @return CommonMessageTemplateDTO
     * @author huming
     * @date 2019/4/25 16:53
     */
    @GetMapping("/selectByCode/{code}")
    @ApiOperation(value = "通过code获取模板信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "消息模板code", required = true, paramType = "path")
    })
    public Result<SysMessageTemplateDTO> selectByCode(@PathVariable("code") String code) {
        SysMessageTemplate param = new SysMessageTemplate();
        param.setTemplateCode(code);
        SysMessageTemplate messageTemplate =
                sysMessageTemplateService.selectOne(new EntityWrapper<SysMessageTemplate>(param));
        return Result.buildSuccessResult(BeanUtils.beanCopy(messageTemplate, SysMessageTemplateDTO.class));
    }


    /**
     * 添加消息模板
     *
     * @param vo 请求参数VO
     * @return Result
     * @author huming
     * @date 2019/4/25 16:56
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加消息")
    public Result<String> addMessageTemplate(@RequestBody @Valid SysMessageTemplateVO vo) {
        //参数校验
        sysMessageTemplateService.addMessageTemplate(BeanUtils.beanCopy(vo, SysMessageTemplate.class));
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 修改消息模板
     *
     * @param messageTemplateVO 请求参数VO
     * @return Result
     * @author huming
     * @date 2019/4/25 16:56
     */
    @PutMapping("/updateById")
    @ApiOperation(value = "修改消息模板")
    public Result<String> updateMessageTemplate(@RequestBody SysMessageTemplateEditVO messageTemplateVO) {
        validatorHandler.validator(messageTemplateVO);
        sysMessageTemplateService.updateMessageTemplate(BeanUtils.beanCopy(messageTemplateVO, SysMessageTemplate.class));
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 删除消息模板
     *
     * @param ids 消息模板
     * @return Result
     * @author huming
     * @date 2019/4/25 16:56
     */
    @DeleteMapping("/delByIds/{ids}")
    @ApiOperation(value = "删除消息模板,传消息模板ids,格式 1,2,3")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "需要删除的消息模板id", required = true, paramType = "path"),
    })
    public Result<String> delMessageTemplateByIds(@PathVariable("ids") String ids) {
        Map<String, Object> map = new HashMap<>();
        UserInfo userInfo = currentUser();
        setUserLevelParam(map, userInfo);
        map.put("ids", StrUtil.split(ids, StrUtil.COMMA));
        sysMessageTemplateService.delMessageTemplateByIds(map);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 删除消息模板
     *
     * @param codes 消息模板codes
     * @return Result
     * @author huming
     * @date 2019/4/25 16:56
     */
    @DeleteMapping("/delByCodes/{codes}")
    @ApiOperation(value = "删除消息模板,传消息模板codes,格式 1,2,3")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "codes", value = "需要删除的消息模板codes", required = true, paramType = "path"),
    })
    public Result<String> delMessageTemplateByCodes(@PathVariable("codes") String codes) {
        Map<String, Object> map = new HashMap<>();
        UserInfo userInfo = currentUser();
        setUserLevelParam(map, userInfo);
        map.put("codes", StrUtil.splitTrim(codes, StrUtil.COMMA));
        sysMessageTemplateService.delMessageTemplateByCodes(map);
        return Result.buildSuccessResult("操作成功");
    }
}

