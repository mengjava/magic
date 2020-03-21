package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.enums.AuditStatusEnum;
import com.haoqi.magic.business.model.dto.CsCarDealerAuditDTO;
import com.haoqi.magic.business.model.dto.CsCarDealerDTO;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.vo.CsCarDealerAuditVO;
import com.haoqi.magic.business.model.vo.CsCarDealerPageVO;
import com.haoqi.magic.business.model.vo.CsCarDealerVO;
import com.haoqi.magic.business.service.ICsCarDealerService;
import com.haoqi.magic.business.service.ICsUserVipService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.PUT;
import java.util.Arrays;
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
@RequestMapping("/csCarSeller")
@Api(tags = "卖家管理类")
public class CsCarSellerController extends BaseController {

    @Autowired
    private ICsCarDealerService csCarDealerService;

    @Autowired
    private FastDfsFileService fastDfsFileService;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix:}")
    private String prefix;


    @PostMapping("carSellerPage")
    @ApiOperation(value = "卖家分页列表")
    public Result<Page> carDealerPage(@RequestBody CsCarDealerPageVO csCarDealerVO) {
        UserInfo userInfo = currentUser();
        Map<String, Object> params = Maps.newHashMap();
        super.setUserLevelParam(params, userInfo);
	    BeanUtil.beanToMap(csCarDealerVO, params, false, true);
        return Result.buildSuccessResult(csCarDealerService.findCarSellerByPage(new Query(params)));
    }


	@DeleteMapping("/delete/{ids}")
	@ApiOperation(value = "删除商户")
	public Result<String> deleted(@PathVariable Long[] ids) {
		csCarDealerService.deleteBatchIds(Arrays.asList(ids));
		return Result.buildSuccessResult("删除成功");
	}

	@PostMapping("/insertOrUpdate")
	@ApiOperation(value = "新增或编辑卖家")
	public Result<String> insertOrUpdate(@RequestBody CsCarDealerVO csCarDealerVO) {
		CsCarDealer carDealer = BeanUtils.beanCopy(csCarDealerVO, CsCarDealer.class);
		csCarDealerService.register(carDealer);
		return Result.buildSuccessResult("新增卖家成功");
	}









}

