package com.haoqi.magic.business.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.model.dto.CsUserBankCardDTO;
import com.haoqi.magic.business.model.dto.CsVipDTO;
import com.haoqi.magic.business.model.dto.SysDictDTO;
import com.haoqi.magic.business.model.entity.CsUserBankCard;
import com.haoqi.magic.business.model.vo.CsUserVipVO;
import com.haoqi.magic.business.service.ICsUserBankCardService;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.haoqi.rigger.web.controller.BaseController;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 银行卡表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-12-16
 */
@RestController
@RequestMapping("/csUserBankCard")
@Api(tags = "银行卡管理")
public class CsUserBankCardController extends BaseController {

	@Autowired
	private ICsUserBankCardService csUserBankCardService;
	@Autowired
	private SystemServiceClient systemServiceClient;

	@PostMapping("/add")
	@ApiOperation(value = "添加银行卡")
	public Result<String> add(@RequestBody CsUserBankCardDTO dto){
		UserInfo userInfo = currentUser();
		validatorHandler.validator(dto);
		dto.setSysUserId(userInfo.getId());
		Boolean success = csUserBankCardService.add(dto);
		return success ? Result.buildSuccessResult("添加成功") : Result.buildErrorResult("添加失败") ;
	}

	@PutMapping("/edit")
	@ApiOperation(value = "修改")
	public Result<String> update(@RequestBody CsUserBankCardDTO dto){
		currentUser();
		validatorHandler.validator(dto);
		Boolean success = csUserBankCardService.edit(dto);
		return success ? Result.buildSuccessResult("修改成功") : Result.buildErrorResult("修改失败") ;
	}

	@GetMapping("/getUserBankCardInfo")
	@ApiOperation(value = "获取用户银行卡信息")
	public Result<CsUserBankCardDTO> getUserBankCardInfo(){
		UserInfo userInfo = currentUser();
		CsUserBankCard csUserBankCard = csUserBankCardService.selectOne(new EntityWrapper<CsUserBankCard>().eq("sys_user_id", userInfo.getId()).eq("is_deleted",Constants.NO));
		if (Objects.isNull(csUserBankCard)){
			return  Result.buildSuccessResult(null);
		}
		return  Result.buildSuccessResult(BeanUtils.beanCopy(csUserBankCard,CsUserBankCardDTO.class));
	}

	@GetMapping("/getBankCardList")
	@ApiOperation(value = "银行列表")
	public Result<List<SysDictDTO>> getDictByClass(){
		Result<List<SysDictDTO>> listResult = systemServiceClient.getDictByClass(DictClassEnum.BANK_CARD_LIST_300000.getClassCode());
		return listResult;
	}
}

