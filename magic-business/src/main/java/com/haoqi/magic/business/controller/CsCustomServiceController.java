package com.haoqi.magic.business.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.enums.ProcessStatusEnum;
import com.haoqi.magic.business.model.dto.CsCustomServiceDTO;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.vo.CsCustomServiceVO;
import com.haoqi.magic.business.model.vo.CustomPageVO;
import com.haoqi.magic.business.service.ICsCarDealerService;
import com.haoqi.magic.business.service.ICsCarSourceService;
import com.haoqi.magic.business.service.ICsCustomServiceService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 客户服务（咨询）表 前端控制器
 * </p>
 *
 * @author mengyao
 * @since 2019-05-14
 */
@RestController
@Api(tags = "意向管理Controller")
@RequestMapping("/csCustomService")
public class CsCustomServiceController extends BaseController {
    @Autowired
    private ICsCustomServiceService csCustomServiceService;

    @Autowired
    private BeanValidatorHandler beanValidatorHandler;

    @Autowired
    private ICsCarSourceService csCarSourceService;

    @Autowired
    private ICsCarDealerService csCarDealerService ;


    /***
     * 功能描述:意向管理列表
     * @param customPageVO
     * @return com.haoqi.rigger.core.Result<com.baomidou.mybatisplus.plugins.Page>
     * @auther mengyao
     * @date 2019/6/12 0012 上午 10:58
     */
    @PostMapping("/customPage")
    @ApiOperation(value = "意向管理")
    public Result<Page> customPage(@RequestBody CustomPageVO customPageVO) {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = Maps.newHashMap();
        params.put("userType", userInfo.getUserType());
        params.put("userId", userInfo.getId());
        params.put("page", customPageVO.getPage());
        params.put("limit", customPageVO.getLimit());
        params.put("planStartDate", StrUtil.emptyToNull(customPageVO.getPlanStartDate()));
        params.put("planEndDate", StrUtil.emptyToNull(customPageVO.getPlanEndDate()));
        params.put("updateStartDate", StrUtil.emptyToNull(customPageVO.getUpdateStartDate()));
        params.put("updateEndDate", StrUtil.emptyToNull(customPageVO.getUpdateEndDate()));
        params.put("publishStatus", customPageVO.getPublishStatus());
	    params.put("carNo", StrUtil.emptyToNull(customPageVO.getCarNo()));
	    params.put("vin", StrUtil.emptyToNull(customPageVO.getVin()));
	    params.put("plateNo", StrUtil.emptyToNull(customPageVO.getPlateNo()));
	    params.put("sysCarModelName", StrUtil.emptyToNull(customPageVO.getSysCarModelName()));
	    params.put("dealerId", StrUtil.emptyToNull(customPageVO.getSellDealerId()));

	    if (null != customPageVO.getBuyerTel()){
		    CsCarDealer csCarDealer = csCarDealerService.selectOne(new EntityWrapper<CsCarDealer>().eq("tel", customPageVO.getBuyerTel()));
		    params.put("buyerDealerId", Objects.nonNull(csCarDealer) &&  Objects.nonNull(csCarDealer.getId()) ?  csCarDealer.getId(): null);
	    }
	    if (null != customPageVO.getTel()){
		    CsCarDealer csCarDealer = csCarDealerService.selectOne(new EntityWrapper<CsCarDealer>().eq("tel", customPageVO.getTel()));
		    params.put("sellDealerId", Objects.nonNull(csCarDealer) &&  Objects.nonNull(csCarDealer.getId()) ? csCarDealer.getId(): null);
	    }
	    params.put("processStatus", Objects.isNull(customPageVO.getProcessStatus()) ? ProcessStatusEnum.UNPROCESSED.getKey() : customPageVO.getProcessStatus());
        handlerOrderByField(customPageVO.getProcessStatus(), params);
        return Result.buildSuccessResult(csCustomServiceService.findByPage(new Query(params)));
    }


    /***
     * 功能描述:处理接口
     * @param csCustomServiceVO
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/6/12 0012 上午 10:58
     */
    @PutMapping("processCustom")
    @ApiOperation(value = "处理接口")
    public Result<String> processCustom(@RequestBody CsCustomServiceVO csCustomServiceVO) {
	    UserInfo userInfo = currentUser();
        beanValidatorHandler.validator(csCustomServiceVO);
	    csCustomServiceVO.setProcessUserId(userInfo.getId());
	    csCustomServiceVO.setProcessLoginName(userInfo.getUserName());
	    csCustomServiceVO.setProcessUserName( userInfo.getRealName());
        csCustomServiceService.processCustom(csCustomServiceVO);
        return Result.buildSuccessResult("更新成功");
    }



	/***
	 * 功能描述:通过Id获取意向信息
	 * @param id
	 * @return com.haoqi.rigger.core.Result<com.haoqi.magic.business.model.entity.CsCustomService>
	 * @auther mengyao
	 * @date 2019/6/12 0012 上午 10:59
	 */
	@GetMapping("/getCustomServiceById/{id}")
	@ApiOperation(value = "通过Id获取意向信息")
	public Result<CsCustomServiceDTO> getCustomServiceById(@PathVariable("id") Long id) {
		return Result.buildSuccessResult(csCustomServiceService.selectDTOById(id));
	}



    /**
     * 处理排序
     *
     * @param processStatus
     * @param params
     */
    private void handlerOrderByField(Integer processStatus, Map params) {
        if (Objects.isNull(processStatus)) {
            return;
        }
        if (ProcessStatusEnum.PROCESSED.getKey().equals(processStatus)) {
            params.put("orderByField", "gmt_modified");
            params.put("isAsc", false);
        } else if (ProcessStatusEnum.UNPROCESSED.getKey().equals(processStatus)) {
            params.put("orderByField", "intention_time");
        }

    }


}

