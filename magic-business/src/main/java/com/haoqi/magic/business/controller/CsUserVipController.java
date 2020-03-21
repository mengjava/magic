package com.haoqi.magic.business.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.haoqi.magic.business.enums.VipTypeEnum;
import com.haoqi.magic.business.model.entity.CsUserVip;
import com.haoqi.magic.business.model.entity.CsVip;
import com.haoqi.magic.business.model.vo.CsUserVipVO;
import com.haoqi.magic.business.service.ICsUserVipService;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 用户会员关联表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-11-29
 */
@RestController
@RequestMapping("/csUserVip")
@Api(tags = "用户会员Controller")
public class CsUserVipController extends BaseController {

    @Autowired
    private ICsUserVipService userVipService;
    @Autowired
    private ICsVipService csVipService;


    /**
     * 功能描述: 获取用户会员信息
     * @param 
     * @return com.haoqi.rigger.core.Result<java.util.List<com.haoqi.magic.business.model.dto.CsVipDTO>>
     * @auther mengyao
     * @date 2020/1/5 0005 上午 10:36
     */
	@GetMapping("getUserVipInfo")
	@ApiOperation(value = "获取用户会员信息")
	public Result<CsUserVipVO> getVipTypeList() {
		UserInfo userInfo = currentUser();
		CsUserVip userVip = userVipService.selectOne(new EntityWrapper<CsUserVip>().eq("sys_user_id", userInfo.getId()).eq("is_deleted",Constants.NO).ge("expired_date",new Date()));
		if (Objects.isNull(userVip)){
			CsVip type = csVipService.selectOne(new EntityWrapper<CsVip>().eq("type", VipTypeEnum.NON_VIP.getKey()));
			return Result.buildSuccessResult(BeanUtils.beanCopy(type,CsUserVipVO.class));
		}
		return Result.buildSuccessResult(BeanUtils.beanCopy(userVip,CsUserVipVO.class));
	}

}

