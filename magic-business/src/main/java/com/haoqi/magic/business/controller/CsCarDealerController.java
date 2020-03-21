package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.AuditStatusEnum;
import com.haoqi.magic.business.model.dto.CsCarDealerAuditDTO;
import com.haoqi.magic.business.model.dto.CsCarDealerDTO;
import com.haoqi.magic.business.model.dto.CsVipDTO;
import com.haoqi.magic.business.model.dto.SysProvinceAndCityDTO;
import com.haoqi.magic.business.model.dto.UserDTO;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.entity.CsUserVip;
import com.haoqi.magic.business.model.entity.CsVip;
import com.haoqi.magic.business.model.vo.CsCarDealerAuditVO;
import com.haoqi.magic.business.model.vo.CsCarDealerPageVO;
import com.haoqi.magic.business.service.ICsCarDealerService;
import com.haoqi.magic.business.service.ICsUserVipService;
import com.haoqi.magic.business.service.ICsVipService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>
 * 商家表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-05-05
 */
@Slf4j
@RestController
@RequestMapping("/csCarDealer")
@Api(tags = "商家管理类")
public class CsCarDealerController extends BaseController {

    @Autowired
    private ICsCarDealerService csCarDealerService;
	@Autowired
	private ICsVipService csVipService;
    @Autowired
    private FastDfsFileService fastDfsFileService;
    @Autowired
    private ICsUserVipService csUserVipService;
	@Autowired
	private SystemServiceClient systemServiceClient;
    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix:}")
    private String prefix;


    @PostMapping("carDealerPage")
    @ApiOperation(value = "车商分页列表")
    public Result<Page> carDealerPage(@RequestBody CsCarDealerPageVO csCarDealerVO) {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = Maps.newHashMap();
        super.setUserLevelParam(params, userInfo);
	    BeanUtil.beanToMap(csCarDealerVO, params, false, true);
        return Result.buildSuccessResult(csCarDealerService.findCarDealerByPage(new Query(params)));
    }

    /**
     * 功能描述: 商家启用禁用
     *
     * @param id
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/8 0008 上午 9:51
     */
    @PutMapping("/edit/{id}")
    @ApiOperation(value = "商家启用禁用")
    public Result<String> updateStatus(@PathVariable Long id) {
        csCarDealerService.updateEnabledById(id);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 功能描述: 诚信联盟开启关闭
     *
     * @param id
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/8 0008 上午 10:41
     */
    @PutMapping("/updateCreditUnion/{id}")
    @ApiOperation(value = "诚信联盟开启关闭")
    public Result<String> updateCreditUnion(@PathVariable Long id) {
        csCarDealerService.updateCreditUnionById(id);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 功能描述:商家审核
     *
     * @param csCarDealerAuditVO
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/8 0008 下午 2:37
     */
    @PutMapping("/audit")
    @ApiOperation("商家审核")
    public Result<String> audit(@RequestBody CsCarDealerAuditVO csCarDealerAuditVO) {
        UserInfo userInfo = currentUser();
        csCarDealerAuditVO.setLastAuditLoginName(userInfo.getUserName());
        csCarDealerAuditVO.setLastAuditUserId(userInfo.getId());
        csCarDealerService.audit(csCarDealerAuditVO);
        return Result.buildSuccessResult("更新成功");
    }


    /**
     * 功能描述:获取详情
     *
     * @param id
     * @return com.haoqi.rigger.core.Result<com.haoqi.magic.business.model.entity.CsCarDealer>
     * @auther mengyao
     * @date 2019/5/9 0009 下午 1:38
     */
    @GetMapping("getDealerById/{id}")
    @ApiOperation(value = "获取详情")
    public Result<CsCarDealerAuditDTO> getDealerById(@PathVariable("id") Long id) {
        currentUser();
        Optional<CsCarDealer> optional = csCarDealerService.getOneById(id);
        CsCarDealer carDealer = optional.get();
        CsCarDealerAuditDTO csCarDealerAuditDTO = BeanUtils.beanCopy(carDealer, CsCarDealerAuditDTO.class);
        csCarDealerAuditDTO.setPictureURL(fastDfsFileService.getFastWebUrl());
	    Result<SysProvinceAndCityDTO> areaById = systemServiceClient.getAreaById(csCarDealerAuditDTO.getSysAreaId());
	    csCarDealerAuditDTO.setProvinceCode(areaById.getData().getProvinceCode());
	    csCarDealerAuditDTO.setCityCode(areaById.getData().getCityCode());
        return Result.buildSuccessResult(csCarDealerAuditDTO);
    }

    /**
     * 获取全部商家
     *
     * @return
     * @author huming
     * @date 2019/5/20 13:57
     */
    @GetMapping("/getApproveCarDealer")
    @ApiOperation(value = "获取全部车商")
    public Result<List<CsCarDealerDTO>> getAllCarDealer() {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userInfo.getId());
        params.put("userName", userInfo.getUserName());
        params.put("userType", userInfo.getUserType());
        params.put("dealerStatus", AuditStatusEnum.PASS.getKey());
        return Result.buildSuccessResult(csCarDealerService.getAllCarDealer(params));
    }


    /**
     * 功能描述: 设置收款方式
     * @param id
     * @param paymentType
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/12/24 0024 下午 2:47
     */
	@PutMapping("/setPaymentMethod/{id}/{paymentType}")
	@ApiOperation(value = "设置收款方式", notes="根据商家id修改收款方式")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "paymentType", value = "收款类型 交易收款方式（1：先打款后过户，0：先过户后打款，默认0）"),
	})
	public Result<String> setPaymentMethod(@PathVariable Long id,@PathVariable Integer paymentType) {
		csCarDealerService.setPaymentMethod(id,paymentType);
		return Result.buildSuccessResult("操作成功");
	}

	/**
	 * 功能描述: 获取会员列表
	 * @param 
	 * @return com.haoqi.rigger.core.Result<java.util.List<com.haoqi.magic.business.model.dto.CsVipDTO>>
	 * @auther mengyao
	 * @date 2019/12/30 0030 下午 4:11
	 */
	@GetMapping("getVipTypeList")
	@ApiOperation(value = "获取会员列表")
	public Result<List<CsVipDTO>> getVipTypeList() {
		List<CsVip> csVipList = csVipService.selectList(new EntityWrapper<CsVip>().eq("is_deleted", Constants.NO).ne("type",Constants.NO));
		return Result.buildSuccessResult(BeanUtils.beansToList(csVipList, CsVipDTO.class));
	}


	/**
	 * 功能描述: 会员赠送
	 * @param vipId,userId,username
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/12/30 0030 下午 4:05
	 */
	@PutMapping("/insertUserVip/{vipId}/{userId}")
	@ApiOperation(value = "会员赠送")
	public Result<String> insertUserVip(@PathVariable Long vipId,@PathVariable Long userId) {
		Result<UserDTO> userByUserId = systemServiceClient.getUserByUserId(userId);
		csUserVipService.bindingMember(vipId,userId,userByUserId.getData().getUsername());
		return Result.buildSuccessResult("操作成功");
	}


	/**
	 * 功能描述: 会员开启关闭
	 * @param useVipId
	 * @param isDeleted
	 * @return com.haoqi.rigger.core.Result<java.lang.String>
	 * @auther mengyao
	 * @date 2019/12/30 0030 下午 4:05
	 */
	@PutMapping("/setIsVip/{useVipId}/{isDeleted}")
	@ApiOperation(value = "会员开启关闭")
	public Result<String> setIsVip(@PathVariable Long useVipId,@PathVariable Integer isDeleted) {
		CsUserVip userVip = new CsUserVip();
		userVip.setId(useVipId);
		userVip.setIsDeleted(isDeleted);
		csUserVipService.updateById(userVip);
		return Result.buildSuccessResult("操作成功");
	}

}

