package com.haoqi.magic.business.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.model.vo.CsCarSourcePageVO;
import com.haoqi.magic.business.model.vo.CsCarSourceVO;
import com.haoqi.magic.business.service.ICsCarDealerService;
import com.haoqi.magic.business.service.ICsCarSourceService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:com.haoqi.magic.business.controller <br/>
 * Function: 车商车源管理<br/>
 * Date:     2019/5/8 10:00 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/car/home/carSource")
@Api(tags = "前端车商车源管理")
public class CsCarSourceController extends BaseController {


    @Autowired
    private ICsCarSourceService csCarSourceService;

    @Autowired
    private ICsCarDealerService csCarDealerService;

    /**
     * 分页获取车商的车源信息
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/5/8 11:39
     */
    @PostMapping("/carSourcePage")
    @ApiOperation(value = "分页获取车商的车源信息")
    public Result<Page> carSourcePage(@RequestBody CsCarSourcePageVO param) {
        currentUser();
        Map<String, Object> params = new HashMap<>();
        params.put("sysCarModelName", StrUtil.emptyToNull(param.getSysCarModelName()));
        params.put("displacementStart", param.getDisplacementStart());
        params.put("displacementEnd", param.getDisplacementEnd());
        params.put("displacementType", param.getDisplacementType());
        params.put("colorCode", StrUtil.emptyToNull(param.getColorCode()));
        params.put("wholesalePriceStart", param.getWholesalePriceStart());
        params.put("wholesalePriceEnd", param.getWholesalePriceEnd());
        params.put("travelDistanceStart", param.getTravelDistanceStart());
        params.put("travelDistanceEnd", param.getTravelDistanceEnd());
        params.put("priceStart", param.getPriceStart());
        params.put("priceEnd", param.getPriceEnd());
        params.put("initDateStart", StrUtil.isBlank(param.getInitDateStart()) ? StrUtil.emptyToNull("") :
                DateUtil.beginOfDay(DateUtil.parseDate(param.getInitDateStart())));
        params.put("initDateStart", StrUtil.isBlank(param.getInitDateEnd()) ? StrUtil.emptyToNull("") :
                DateUtil.endOfDay(DateUtil.parseDate(param.getInitDateEnd())));
        params.put("publishStatus", param.getPublishStatus());
        params.put("gearBoxCode", StrUtil.emptyToNull(param.getGearBoxCode()));
        params.put("emissionStandardCode", StrUtil.emptyToNull(param.getEmissionStandardCode()));
        params.put("csCarDealerId", csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        return Result.buildSuccessResult(csCarSourceService.findPage(params));
    }


    /**
     * 下架车源
     *
     * @param id 车辆ID
     * @return
     * @author huming
     * @date 2019/5/8 15:42
     */
    @GetMapping("/pullOff/{id}")
    @ApiOperation(value = "下架车源")
    public Result<String> pullOffCar(@PathVariable("id") Long id) {
        CsCarSourceVO vo = new CsCarSourceVO();
        vo.setId(id);
        vo.setCsCarDealerId(csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        csCarSourceService.pullOffCar(vo);
        return Result.buildSuccessResult("操作成功");
    }
}
