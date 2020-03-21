package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.model.dto.CsVipDTO;
import com.haoqi.magic.business.model.entity.CsVip;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 会员配置表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-12-03
 */
@RestController
@RequestMapping("/csVip")
@Api(tags = "会员管理")
public class CsVipController extends BaseController {

	@Autowired
	private ICsVipService csVipService;

	@PostMapping("/page")
	@ApiOperation(value = "会员管理")
	public Result<Page> page(@RequestBody CsVipDTO dto){
		Map<String, Object> params = Maps.newHashMap();
		BeanUtil.beanToMap(dto, params, false, true);
		return Result.buildSuccessResult(csVipService.findByPage(new Query(params)));
	}

	@PostMapping("/add")
	@ApiOperation(value = "添加会员")
	public Result<String> add(@RequestBody CsVipDTO dto){
		validatorHandler.validator(dto);
		boolean success = csVipService.add(dto);
		return success ? Result.buildSuccessResult("添加成功") : Result.buildErrorResult("添加失败") ;
	}

	@GetMapping("getById/{id}")
	@ApiOperation(value = "获取详情")
	public Result<CsVipDTO> getDealerById(@PathVariable("id") Long id) {
		CsVip csVip = csVipService.selectById(id);
		CsVipDTO csVipDTO = BeanUtils.beanCopy(csVip, CsVipDTO.class);
		return Result.buildSuccessResult(csVipDTO);
	}

	@PutMapping("/edit")
	@ApiOperation(value = "修改")
	public Result<String> update(@RequestBody CsVipDTO dto){
		validatorHandler.validator(dto);
		boolean success = csVipService.edit(dto);
		return success ? Result.buildSuccessResult("修改成功") : Result.buildErrorResult("修改失败") ;
	}
	@PutMapping("/updateStatus/{id}")
	@ApiOperation(value = "启用禁用")
	public Result<String> updateStatus(@PathVariable Long id) {
		csVipService.updateEnabledById(id);
		return Result.buildSuccessResult("操作成功");
	}



	@PutMapping("/isShow/{id}/{isShow}")
	@ApiOperation(value = "会员开启关闭", notes="是否展示（1：展示，默认，0：不展示）")
	public Result<String> setPaymentMethod(@PathVariable Long id,@PathVariable Integer isShow) {
		csVipService.setIsShow(id,isShow);
		return Result.buildSuccessResult("操作成功");
	}

	@DeleteMapping("/delete/{ids}")
	@ApiOperation(value = "删除会员")
	public Result<String> deleted(@PathVariable Long[] ids) {
			csVipService.deleteVip(ids);
			Object a= new Object();
a.
		return Result.buildSuccessResult("删除成功");
	}


	@GetMapping("getVipTypeList")
	@ApiOperation(value = "获取会员列表 ",notes = "is_show 是否展示（1：展示，0：不展示 ")
	public Result<List<CsVipDTO>> getVipTypeList() {
		UserInfo userInfo = currentUser();
		List<CsVipDTO> csVipDTO = csVipService.getVipTypeList(userInfo.getId());
		return Result.buildSuccessResult(csVipDTO);
	}


	@GetMapping("getVipList")
	@ApiOperation(value = "获取会员类型列表（赠送会员下来列表）")
	public Result<List<CsVip>> getVipList() {
		currentUser();
		List<CsVip> csVipList = csVipService.selectList(new EntityWrapper<CsVip>().eq("is_deleted", Constants.NO));
		return Result.buildSuccessResult(csVipList);
	}

}

