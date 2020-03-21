package com.haoqi.magic.system.controller;


import com.haoqi.magic.system.model.dto.SysRegionDTO;
import com.haoqi.magic.system.model.vo.SysRegionVO;
import com.haoqi.magic.system.service.ISysRegionService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 大区管理表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-04-28
 */
@Api(tags = "大区管理类")
@RestController
@RequestMapping("/sysRegion")
public class SysRegionController extends BaseController {

	@Autowired
	private ISysRegionService sysRegionService;

	@Autowired
	private BeanValidatorHandler beanValidatorHandler;

	/**
	 * 功能描述: 大区列表
	 *
	 * @auther mengyao
	 * @date 2019/4/28 0028 上午 9:34
	 */
	@PostMapping("regionList")
	@ApiOperation(value = "获取大区列表")
	public Result<List<SysRegionDTO>> regionList() {
		return Result.buildSuccessResult(sysRegionService.getRegionList(), "添加成功");
	}



	/**
	 * 功能描述: 新增大区
	 *
	 * @param sysRegionVO
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/4/29 0029 上午 10:02
	 */
	@PostMapping("add")
	@ApiOperation(value = "新增大区")
	public Result<String> add(@RequestBody @Valid SysRegionVO sysRegionVO) {
		currentUser();
		beanValidatorHandler.validator(sysRegionVO);
		sysRegionService.insertRegion(sysRegionVO);
		return Result.buildSuccessResult("添加成功");
	}


	/**
	 * 功能描述: 编辑大区
	 *
	 * @param sysRegionVO
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/4/29 0029 上午 10:04
	 */
	@PutMapping("edit")
	@ApiOperation(value = "编辑大区")
	public Result<String> edit(@RequestBody @Valid SysRegionVO sysRegionVO) {
		currentUser();
		beanValidatorHandler.validator(sysRegionVO);
		sysRegionService.updateRegion(sysRegionVO);
		return Result.buildSuccessResult("更新成功");
	}


	/**
	 * 功能描述: 删除大区
	 *
	 * @param ids
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/4/29 0029 上午 10:45
	 */
	@DeleteMapping("/delete/{ids}")
	@ApiOperation(value = "删除大区")
	public Result<String> deleted(@PathVariable Long[] ids) {
		for (Long id : ids) {
			sysRegionService.deleteRegion(id);
		}
		return Result.buildSuccessResult("删除成功");
	}




}

