package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.model.dto.CsServiceFeeDTO;
import com.haoqi.magic.business.model.dto.CsServiceFeeListDTO;
import com.haoqi.magic.business.model.entity.CsServiceFee;
import com.haoqi.magic.business.service.ICsServiceFeeService;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.haoqi.rigger.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务费设置表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-12-18
 */
@RestController
@RequestMapping("/csServiceFee")
@Api(tags = "服务费设置")
public class CsServiceFeeController extends BaseController {
	@Autowired
	private ICsServiceFeeService csServiceFeeService;

	@PostMapping("/page")
	@ApiOperation(value = "服务费列表")
	public Result<Page> page(@RequestBody CsServiceFeeDTO dto){
		Map<String, Object> params = Maps.newHashMap();
		BeanUtil.beanToMap(dto, params, false, true);
		return Result.buildSuccessResult(csServiceFeeService.findByPage(new Query(params)));
	}


	@PostMapping("/add")
	@ApiOperation(value = "添加服务费设置")
	public Result<String> add(@RequestBody CsServiceFeeListDTO listDTO){
		validatorHandler.validator(listDTO);
		Boolean success = csServiceFeeService.add(listDTO);
		return success ? Result.buildSuccessResult("添加成功") : Result.buildErrorResult("添加失败") ;
	}

	@DeleteMapping("/delete/{sysAreaIds}")
	@ApiOperation(value = "批量删除服务费设置",notes = "列表的批量删除,编辑里的删除是另一个方法")
	public Result<String> deleted(@PathVariable Long[] sysAreaIds) {
		csServiceFeeService.deleteServiceFee(sysAreaIds);
		return Result.buildSuccessResult("删除成功");
	}

	@DeleteMapping("/deletedById/{id}")
	@ApiOperation(value = "单个删除服务费",notes = "用在编辑的时候删除服务费")
	public Result<String> deletedById(@PathVariable Long[] id) {
		Boolean success = csServiceFeeService.deleteById(id);
		return success ? Result.buildSuccessResult("删除成功") : Result.buildErrorResult("删除失败") ;
	}

	@PutMapping("/edit")
	@ApiOperation(value = "修改")
	public Result<String> update(@RequestBody CsServiceFeeListDTO listDTO){
		validatorHandler.validator(listDTO);
		Boolean success = csServiceFeeService.edit(listDTO);
		return success ? Result.buildSuccessResult("修改成功") : Result.buildErrorResult("修改失败") ;
	}

	@GetMapping("getBySysAreaId/{sysAreaId}")
	@ApiOperation(value = "根据区域id获取服务费设置详情")
	public Result<List<CsServiceFeeDTO>> getDealerById(@PathVariable("sysAreaId") Long sysAreaId) {
		List<CsServiceFee> list = csServiceFeeService.selectList(new EntityWrapper<CsServiceFee>().eq("sys_area_id",sysAreaId));
		List<CsServiceFeeDTO> csServiceFeeDTOList = BeanUtils.beansToList(list, CsServiceFeeDTO.class);
		return Result.buildSuccessResult(csServiceFeeDTOList);
	}
}

