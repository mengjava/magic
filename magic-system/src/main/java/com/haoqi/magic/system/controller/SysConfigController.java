package com.haoqi.magic.system.controller;


import com.google.common.collect.Maps;
import com.haoqi.magic.system.model.dto.SysConfigDTO;
import com.haoqi.magic.system.model.entity.SysConfig;
import com.haoqi.magic.system.model.enums.ConfigNameEnum;
import com.haoqi.magic.system.model.enums.ConfigTypeEnum;
import com.haoqi.magic.system.model.vo.SysConfigEnumsVO;
import com.haoqi.magic.system.model.vo.SysConfigVO;
import com.haoqi.magic.system.service.ISysConfigService;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统配置表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-11-29
 */
@RestController
@RequestMapping("/sysConfig")
@Api(tags = "设置")
public class SysConfigController extends BaseController {

    @Autowired
    private ISysConfigService sysConfigService;

    @PostMapping("/list")
    @ApiOperation(value = "设置列表", notes = "type:类别(1:保证金金额，2提现，3违约超时,4争议，5推送 6 车况查询，7电话,8复检）  name:名称（10：买入，12卖出，20最低提现金额，21 提现模式 30买家付款违约周期，31过户超时周期，40终审赔偿金额，50二次提醒付款周期，60维保成本价，61排放成本价，62出险成本价，63车型识别成本价，64快速估值成本价，70客服电话，80客服电话）")
    public Result<List<SysConfigVO>> list(@RequestBody SysConfigDTO dto) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("is_deleted", CommonConstant.STATUS_NORMAL.intValue());
        map.put("name", dto.getName());
        map.put("type", dto.getType());
        List<SysConfig> data = sysConfigService.selectByMap(map);
        List<SysConfigVO> sysConfigVOS = BeanUtils.beansToList(data, SysConfigVO.class);
        sysConfigVOS.forEach(m -> {
            m.setTypeStr(ConfigTypeEnum.getValue(m.getType()));
            m.setNameStr(ConfigNameEnum.getValue(m.getName()));
        });
        return Result.buildSuccessResult(sysConfigVOS);
    }

    @PostMapping("/typeList")
    @ApiOperation(value = "类别列表接口")
    public Result<List<SysConfigEnumsVO>> typeList() {
        List<SysConfigEnumsVO> list = new ArrayList<>(16);
        ConfigTypeEnum[] values = ConfigTypeEnum.values();
        for (ConfigTypeEnum value : values) {
            SysConfigEnumsVO vo = new SysConfigEnumsVO();
            vo.setId(value.getKey());
            vo.setName(value.getName());
            list.add(vo);
        }
        return Result.buildSuccessResult(list, "查询成功");
    }

    @PostMapping("/nameList")
    @ApiOperation(value = "名称列表接口")
    public Result<List<SysConfigEnumsVO>> nameList() {
        ConfigNameEnum[] values = ConfigNameEnum.values();
        List<SysConfigEnumsVO> list = new ArrayList<>(16);
        for (ConfigNameEnum value : values) {
            SysConfigEnumsVO vo = new SysConfigEnumsVO();
            vo.setId(value.getKey());
            vo.setName(value.getName());
            list.add(vo);
        }
        return Result.buildSuccessResult(list, "查询成功");
    }


    @ApiOperation(value = "设置")
    @PostMapping("/update")
    public Result<String> update(@RequestBody SysConfigDTO dto) {
        validatorHandler.validator(dto);
        //更新记录
        Boolean success = sysConfigService.updateById(BeanUtils.beanCopy(dto, SysConfig.class));
        //返回结果
        return success ? Result.buildSuccessResult("设置成功") : Result.buildErrorResult("设置失败");
    }


    @GetMapping("/getByTypeAndName/{type}/{name}")
    @ApiOperation(value = "根据name获取配置信息")
    public Result<SysConfigDTO> getByTypeAndName(@PathVariable("type") Integer type, @PathVariable("name") Integer name) {
        SysConfig byTypeAndName = sysConfigService.getByTypeAndName(type, name);
        return Result.buildSuccessResult(BeanUtils.beanCopy(byTypeAndName, SysConfigDTO.class));
    }

    @GetMapping("findByType/{type}")
    public Result<List<SysConfigDTO>> findByType(@PathVariable("type") Integer type) {
        return Result.buildSuccessResult(sysConfigService.getByType(type));
    }


}

