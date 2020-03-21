package com.haoqi.magic.system.controller;

import com.haoqi.magic.system.model.dto.FileVersionDTO;
import com.haoqi.magic.system.model.vo.FileVersionVO;
import com.haoqi.magic.system.service.ISysFileVersionDetailService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author twg
 * @since 2018/8/10
 */
@RestController
@RequestMapping("/fileVersion")
public class SysFileVersionDetailController extends BaseController {
    @Autowired
    private ISysFileVersionDetailService fileVersionDetailService;

    /**
     * @param fileVersionVO
     * @return
     */
//    @PostMapping("/add")
    @Deprecated
    public Result<String> add(@RequestBody FileVersionVO fileVersionVO) {
        fileVersionDetailService.insertFileVersion(BeanUtils.beanCopy(fileVersionVO,FileVersionDTO.class));
        return Result.buildSuccessResult("添加成功");
    }




}
