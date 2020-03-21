package com.haoqi.magic.system.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.system.model.dto.SysAdvertDTO;
import com.haoqi.magic.system.model.entity.SysAdvertConfig;
import com.haoqi.magic.system.model.vo.SysAdvertConfigVO;
import com.haoqi.magic.system.service.ISysAdvertConfigService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 广告配置表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-04-25
 */
@RestController
@Api(tags = "广告配置类")
@RequestMapping("/sysAdvertConfig")
public class SysAdvertConfigController extends BaseController {

	@Autowired
	private ISysAdvertConfigService sysAdvertConfigService;
	/**
	 * 广告分页列表
	 *
	 * @param advertVO
	 * @return
	 */
	@PostMapping("advertPage")
	@ApiOperation(value = "广告分页列表")
	public Result<Page>  advertPage(@RequestBody SysAdvertConfigVO advertVO) {
		UserInfo userInfo = currentUser();
		Map params = Maps.newHashMap();
		super.setUserLevelParam(params, userInfo);
		params.put("title", advertVO.getTitle());
		params.put("status", advertVO.getStatus());
		params.put("page", advertVO.getPage());
		params.put("limit", advertVO.getLimit());
		return Result.buildSuccessResult(sysAdvertConfigService.findAdvertByPage(new Query(params)));
	}


	/**
	 * 广告新增
	 *
	 * @param advertVO
	 * @param bindingResult
	 * @return
	 */
	@PostMapping("add")
	@ApiOperation(value = "广告新增")
	public Result<String> add(@RequestBody @Valid SysAdvertConfigVO advertVO, BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			return Result.buildErrorResult(fieldError.getDefaultMessage());
		}
		UserInfo userInfo = currentUser();
		SysAdvertConfig advertConfig = new SysAdvertConfig();
		BeanUtil.copyProperties(advertVO, advertConfig);
		advertConfig.setCreator(userInfo.getId());
		advertConfig.setModifier(userInfo.getId());
		advertConfig.setGmtCreate(new Date());
		advertConfig.setGmtModified(new Date());
		sysAdvertConfigService.insertAdvert(advertConfig);
		return Result.buildSuccessResult("添加成功");
	}

	/**
	 * 广告更新
	 *
	 * @param advertVO
	 * @param bindingResult
	 * @return
	 */
	@PutMapping("edit")
	@ApiOperation(value = "广告编辑")
	public Result<String> edit(@RequestBody @Valid SysAdvertConfigVO advertVO, BindingResult bindingResult) {
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		for (FieldError fieldError : fieldErrors) {
			return Result.buildErrorResult(fieldError.getDefaultMessage());
		}
		UserInfo userInfo = currentUser();
		SysAdvertConfig advertConfig = new SysAdvertConfig();
		BeanUtil.copyProperties(advertVO, advertConfig);
		advertConfig.setCreator(userInfo.getId());
		advertConfig.setModifier(userInfo.getId());
		advertConfig.setGmtCreate(new Date());
		advertConfig.setGmtModified(new Date());
		sysAdvertConfigService.updateAdvert(advertConfig);
		return Result.buildSuccessResult("更新成功");
	}

	/**
	 * 广告上下架
	 *
	 * @param id
	 * @return
	 */
	@PutMapping("/edit/{id}")
	@ApiOperation(value = "上下架广告")
	public Result<String> updateStatus(@PathVariable Long id) {
		sysAdvertConfigService.updateStatusById(id);
		return Result.buildSuccessResult("操作成功");
	}


	/**
	 * 获取广告
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "获取广告")
	public Result<SysAdvertDTO> advert(@PathVariable("id") Long id) {
		return Result.buildSuccessResult(sysAdvertConfigService.getAdvertById(id));
	}


	/**
	 * 删除广告
	 *
	 * @param ids
	 * @return
	 */
	@DeleteMapping("/delete/{ids}")
	@ApiOperation(value = "删除广告")
	public Result<String> deleted(@PathVariable Long[] ids) {
		for (Long id : ids) {
			sysAdvertConfigService.deleteAdvert(id);
		}
		return Result.buildSuccessResult("删除成功");
	}


	/**
	 * 功能描述:
	 * 广告管理 通过广告【来自数据字典】投放位置 获取广告信息
	 * @auther: yanhao
	 * @param:
	 * @date: 2019/5/13 10:09
	 * @Description:
	 */
	@GetMapping("/getAdvertByPositionCode/{code}")
	@ApiOperation(value = "通过广告【来自数据字典】投放位置 获取广告信息")
	public Result<List<SysAdvertDTO>> getAdvertByPositionCode(@PathVariable("code") String code) {
        List<SysAdvertDTO> list = sysAdvertConfigService.getAdvertByPositionCode(code);
		return Result.buildSuccessResult(list);
	}
}

