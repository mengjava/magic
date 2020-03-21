package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.model.dto.CarAuditDTO;
import com.haoqi.magic.business.model.vo.CarAuditPageVO;
import com.haoqi.magic.business.service.ICsCarAuditService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 车辆信息审核表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-05-07
 */
@RestController
@RequestMapping("/carAudit")
@Api(tags = "车辆信息审核")
@Slf4j
public class CsCarAuditController extends BaseController {

    @Autowired
    private ICsCarAuditService carAuditService;
    @Autowired
    private BeanValidatorHandler validatorHandler;

    /**
     * 通过车辆id，获取车辆审核记录
     *
     * @param carId
     * @return
     */
    @GetMapping("carAuditByCarId/{carId}")
    @ApiOperation(value = "通过车辆id，获取车辆审核记录")
    public Result<CarAuditDTO> carAuditByCarId(@PathVariable("carId") Long carId) {
        currentUser();
        Optional<CarAuditDTO> optional = carAuditService.getLastCarAuditByCarId(carId);
        if (optional.isPresent()) {
            return Result.buildSuccessResult(optional.get());
        }
        return Result.buildSuccessResult("");
    }

    /**
     * 车辆信息审核操作
     *
     * @param carAuditDTO
     * @return
     */
    @PostMapping("add")
    @ApiOperation(value = "车辆信息审核操作")
    public Result<String> carAuditAdd(@RequestBody CarAuditDTO carAuditDTO) {
        UserInfo userInfo = currentUser();
        //TODO 当前车的状态
        carAuditDTO.setAuditUserId(userInfo.getId());
        carAuditDTO.setAuditLoginName(userInfo.getUserName());
        carAuditDTO.setAuditUserName(userInfo.getRealName());
        try{
	        carAuditService.add(carAuditDTO);
        } catch (Exception e) {
	        log.error(e.getMessage());
        }
        return Result.buildSuccessResult("操作成功");
    }

    /***
     * 通过车辆id获取车辆审核记录
     * @param vo
     * @return
     */
    @PostMapping("/carAuditListByCarId")
    @ApiOperation(value = "通过车辆id，获取车辆审核历史记录")
    public Result<Page> carAuditListByCarId(@RequestBody CarAuditPageVO vo) {
        currentUser();
        validatorHandler.validator(vo);
        Map<String, Object> params = Maps.newHashMap();
        BeanUtil.beanToMap(vo, params, false, true);
        Query query = new Query(params);
        Page page = carAuditService.getCarAuditListByCarId(query);
        return Result.buildSuccessResult(page);
    }
}

