package com.haoqi.magic.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.haoqi.magic.business.model.dto.CarOpenApiDTO;
import com.haoqi.magic.business.model.dto.Che300UsedCarPriceDTO;
import com.haoqi.magic.business.model.vo.Che300ResultVO;
import com.haoqi.magic.business.open.CarIdentifyResult;
import com.haoqi.magic.business.open.CarParamConfigResult;
import com.haoqi.magic.business.open.result.CityListResult;
import com.haoqi.magic.business.service.ICarOpenApiService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.enums.Che300CodeEnum;
import com.haoqi.magic.common.utils.ValidUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


/**
 * 功能描述:
 * 对外车300API
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/8/9 16:49
 * @Description:
 */
@RestController
@Slf4j
@RequestMapping("/open")
@Api(tags = "车300接口")
public class CarOpenApiController extends BaseController {

    @Autowired
    private ICarOpenApiService carOpenApiService;

    /**
     * 【正式服务器】http://api.che300.com/service/identifyModelByVIN?vin=LFV3A24F3A3026881&token=LJ8F2D5DXGE078108
     * <p> LSGJE54HXHW396586
     * http://testapi.che300.com/service/identifyModelByVIN?vin=LFV3A24F3A3026881&token=2db5c45b5a25cfe562627bd29ad5309b
     * 【测试服务器】http://testapi.che300.com/service/identifyModelByVIN?vin=LFV3A24F3A3026881&token=LGXC16AF6H0105057
     */

    /**
     * 功能描述: 获取出险信息
     * @param dto
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/12/9 0009 下午 1:57
     */
    @PostMapping("/getInsuranceReport")
    @ApiOperation(value = "获取出险信息")
    public Result<String> getInsuranceReport(@RequestBody CarOpenApiDTO dto) {
        UserInfo userInfo = currentUser();
        dto.setUserId(userInfo.getId());
        dto.setUserName(userInfo.getUserName());
        dto.setType(Constants.NO);
        validatorHandler.validator(dto);
        String reportUrl = null;
        try {
            reportUrl = carOpenApiService.getInsuranceReport(userInfo.getId(), dto);
            if (Che300CodeEnum.CODE_2001.getName().equals(reportUrl)) {
                return Result.buildErrorResult(Che300CodeEnum.CODE_2001.getKey(), Che300CodeEnum.CODE_2001.getName());
            }
            if (Che300CodeEnum.CODE_2002.getName().equals(reportUrl)) {
                return Result.buildErrorResult(Che300CodeEnum.CODE_2002.getKey(), Che300CodeEnum.CODE_2002.getName());
            }
        } catch (Exception e) {
            log.error("参数：{}，车300保险信息接口异常： {}", JSONObject.toJSONString(dto), e);
            return Result.buildErrorResult(e.getMessage());
        }
        return Result.buildSuccessResult(reportUrl, "操作成功");
    }


    /**
     * 功能描述: 获取维修保养信息
     *
     * @param dto
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/12/9 0009 下午 1:57
     */
    @PostMapping("/getMaintenanceReport")
    @ApiOperation(value = "获取维修保养信息")
    public Result<String> getMaintenanceReport(@RequestBody CarOpenApiDTO dto) {
        UserInfo userInfo = currentUser();
        dto.setUserId(userInfo.getId());
        dto.setUserName(userInfo.getUserName());
        dto.setType(Constants.NO);
        validatorHandler.validator(dto);
        String reportUrl = null;
        try {
            reportUrl = carOpenApiService.getMaintenanceReport(userInfo.getId(), dto);
            if (Che300CodeEnum.CODE_2001.getName().equals(reportUrl)) {
                return Result.buildErrorResult(Che300CodeEnum.CODE_2001.getKey(), Che300CodeEnum.CODE_2001.getName());
            }
            if (Che300CodeEnum.CODE_2002.getName().equals(reportUrl)) {
                return Result.buildErrorResult(Che300CodeEnum.CODE_2002.getKey(), Che300CodeEnum.CODE_2002.getName());
            }
        } catch (Exception e) {
            log.error("参数：{}，车300维修保养信息接口异常： {}", JSONObject.toJSONString(dto), e);
            return Result.buildErrorResult(e.getMessage());
        }
        return Result.buildSuccessResult(reportUrl, "操作成功");
    }


    /**
     * 功能描述: 根据vin 进行车型识别
     *
     * @param vin
     * @return com.haoqi.rigger.core.Result<java.util.List < com.haoqi.magic.business.open.CarIdentifyResult>>
     * @auther mengyao
     * @date 2019/12/9 0009 下午 1:57
     */
    @ApiOperation(value = "根据vin 进行车型识别 ", notes = "查询车辆排放时,先调用此接口,返回一组车型,手动选择所要查询的车辆,再调用getCarModelInfo接口")
    @GetMapping("/identifyModelByVin/{vin}")
    public Result<List<CarIdentifyResult>> identifyModelByVIN(@PathVariable("vin") String vin) {
        UserInfo userInfo = currentUser();
        if (!ValidUtils.isCarVin(vin)) {
            return Result.buildErrorResult("VIN不合法");
        }
        List<CarIdentifyResult> list = carOpenApiService.identifyModelByVIN(userInfo.getId(), vin);
        return Result.buildSuccessResult(list);
    }


    /**
     * 功能描述: 通过车型识别返回的modelId获取具体信息
     *
     * @param vin,modelId
     * @return com.haoqi.rigger.core.Result<com.haoqi.magic.business.open.CarParamConfigResult>
     * @auther mengyao
     * @date 2019/12/9 0009 下午 1:58
     */
    @GetMapping("/getModelParameters/{vin}/{modelId}")
    @ApiOperation(value = "根据modelId车型识别接口", notes = "根据identifyModelByVin接口返回的modelId,查询具体车型")
    public Result<CarParamConfigResult> getModelParameters(@PathVariable("vin") String vin, @PathVariable("modelId") Long modelId) {
        UserInfo userInfo = currentUser();
        CarParamConfigResult configResult = null;
        try {
            configResult = carOpenApiService.getModelParameters(userInfo.getId(), modelId, vin);
        } catch (Exception e) {
            log.error("通过车型ID{},查询车300车型识别接口异常: {}", modelId, e);
            return Result.buildErrorResult(e.getMessage());
        }
        return Result.buildSuccessResult(configResult);
    }


    /**
     * 功能描述: 根据modelId查询排放
     *
     * @param vin,modelId
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/12/10 0010 下午 5:20
     */
    @GetMapping("/getCarModelInfo/{vin}/{modelId}")
    @ApiOperation(value = "排放查询接口", notes = "根据identifyModelByVin接口返回的modelId,查询排放")
    public Result<String> getCarModelInfo(@PathVariable("vin") String vin, @PathVariable("modelId") Long modelId) {
        UserInfo userInfo = currentUser();
        String discharge = null;
        try {
            discharge = carOpenApiService.getCarModelInfo(userInfo.getId(), modelId, vin);
        } catch (Exception e) {
            log.error("通过车型ID{},查询车300排放接口异常 {}", modelId, e);
            return Result.buildErrorResult(e.getMessage());
        }
        return Result.buildSuccessResult(discharge);
    }


    /**
     * 功能描述: 根据参数查询评估价格
     *
     * @param dto
     * @return com.haoqi.rigger.core.Result<com.haoqi.magic.business.model.entity.Che300CarPrice>
     * @auther mengyao
     * @date 2019/12/10 0010 下午 5:20
     */
    @RequestMapping(value = "/che300UsedCarPrice", method = RequestMethod.POST)
    @ApiOperation(value = "车300基础估值")
    public Result<String> getUsedCarPrice(@RequestBody Che300UsedCarPriceDTO dto) {
        UserInfo userInfo = currentUser();
        String report_url;
        try {
            validatorHandler.validator(dto);
             report_url  = carOpenApiService.getUsedCarPrice(userInfo.getId(), dto);
        } catch (Exception e) {
            log.error("参数：{},车300基础估值接口异常：{}", JSONObject.toJSONString(dto), e);
            return Result.buildErrorResult(e.getMessage());
        }
        return Result.buildSuccessResult(report_url,"查询成功");
    }

    @GetMapping("/getAllCity")
    @ApiOperation(value = "城市列表接口")
    public Result<CityListResult> getAllCity() {
        CityListResult allCity = carOpenApiService.getAllCity();
        return Result.buildSuccessResult(allCity, "查询成功");
    }


    @PostMapping("/getInsuranceReportResult")
    @ApiOperation(value = "出险回调接口")
    public Che300ResultVO getInsuranceReportResult(@RequestParam("order_no") String order_no, @RequestParam("ret_code")Integer ret_code) {
        Che300ResultVO result = carOpenApiService.getInsuranceReportResult(order_no, ret_code);
        List<String> strings = new ArrayList<>();
        result.setData(strings);
        return result;

    }


    @PostMapping("/getMaintenanceReportResult")
    @ApiOperation(value = "维保回调接口")
    public Che300ResultVO getMaintenanceReportResult(@RequestParam("order_no") String order_no, @RequestParam("ret_code")Integer ret_code) {
        Che300ResultVO result = carOpenApiService.getMaintenanceReportResult(order_no, ret_code);
        List<String> strings = new ArrayList<>();
        result.setData(strings);
        return result;
    }



}
