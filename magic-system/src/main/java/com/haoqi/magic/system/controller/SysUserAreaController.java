package com.haoqi.magic.system.controller;


import com.haoqi.magic.system.model.dto.UserAreaDTO;
import com.haoqi.magic.system.service.ISysUserAreaService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * <p>
 * 系统用户区域关联表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-04-25
 */
@RestController
@RequestMapping("/userArea")
public class SysUserAreaController extends BaseController {

    @Autowired
    private ISysUserAreaService userAreaService;

    /**
     * 通过用户id，获取关联城市信息
     *
     * @param userId
     * @return
     */
    @GetMapping("getUserAreaByUserId")
    public Result<UserAreaDTO> getUserAreaByUserId(@RequestParam("userId") Long userId) {
        Optional<UserAreaDTO> userAreaDTO = userAreaService.getByUserId(userId);
        return Result.buildSuccessResult(userAreaDTO.get());
    }

}

