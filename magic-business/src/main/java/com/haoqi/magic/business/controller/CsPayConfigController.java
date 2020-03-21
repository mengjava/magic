package com.haoqi.magic.business.controller;


import com.haoqi.magic.business.model.dto.CsPayConfigDTO;
import com.haoqi.magic.business.model.dto.CsPayConfigListVO;
import com.haoqi.magic.business.model.vo.CsPayConfigVO;
import com.haoqi.magic.business.service.ICsPayConfigService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 支付配置 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-12-12
 */
@RestController
@RequestMapping("/csPayConfig")
@Api(tags = "支付设置")
public class CsPayConfigController extends BaseController {
	@Autowired
	private ICsPayConfigService csPayConfigService;

	/**
	 * 功能描述: 支付设置列表
	 * @return com.haoqi.rigger.core.Result<java.util.List<com.haoqi.magic.business.model.dto.CsPayConfigListVO>>
	 * @auther mengyao
	 * @date 2019/12/12 0012 下午 2:46
	 */
	@GetMapping("/payConfigList")
	@ApiOperation(value = "支付设置列表")
	public Result<List<CsPayConfigListVO>> payConfigList(){
		List<CsPayConfigListVO> list = csPayConfigService.payConfigList();
		return Result.buildSuccessResult(list);
	}

	@GetMapping("/payTypeList")
	@ApiOperation(value = "支付类型列表 APP用")
	public Result<List<CsPayConfigVO>> payTypeList(){
		List<CsPayConfigVO> list = csPayConfigService.payTypeList();
		return Result.buildSuccessResult(list);
	}


	/**
	 * 功能描述: 修改支付设置
	 * @param list
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/12/12 0012 下午 2:53
	 */
	@PutMapping("/edit")
	@ApiOperation(value = "修改")
	public Result<String> update(@RequestBody List<CsPayConfigDTO> list){
		Boolean success = csPayConfigService.edit(list);
		return success ? Result.buildSuccessResult("修改成功") : Result.buildErrorResult("修改失败") ;
	}

	/**
	 * 功能描述: 启用禁用
	 * @param dto
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/12/12 0012 下午 3:00
	 */
	@PutMapping("/updateStatus")
	@ApiOperation(value = "启用禁用",notes = "有id传递id,没有则不传")
	public Result<String> updateStatus(@RequestBody CsPayConfigDTO dto) {
		csPayConfigService.updateEnabled(dto);
		return Result.buildSuccessResult("操作成功");
	}


	@PutMapping("/setRecommend/{id}/{isRecommend}")
	@ApiOperation(value = "是否推荐 （0：不推荐，1：推荐，默认为0）",notes = "启用后 才可以设置是否推荐")
	public Result<String> setRecommend(@PathVariable("isRecommend") Integer isRecommend,@PathVariable("isRecommend") Long id) {
		csPayConfigService.setRecommend(id,isRecommend);
		return Result.buildSuccessResult("操作成功");
	}
}

