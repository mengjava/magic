package com.haoqi.magic.business.service;

import com.haoqi.magic.business.model.dto.CarOpenApiDTO;
import com.haoqi.magic.business.model.dto.Che300UsedCarPriceDTO;
import com.haoqi.magic.business.model.entity.Che300CarPrice;
import com.haoqi.magic.business.model.vo.Che300ResultVO;
import com.haoqi.magic.business.open.CarIdentifyResult;
import com.haoqi.magic.business.open.CarParamConfigResult;
import com.haoqi.magic.business.open.result.CityListResult;
import springfox.documentation.spring.web.json.Json;

import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 * 第三方公共API 接口
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/8/10 21:26
 * @Description:
 */
public interface ICarOpenApiService {

    /***
     * 车300 根据vin 车型信息
     * @param vin
     * @param userId 用户
     * @return
     */
    List<CarIdentifyResult> identifyModelByVIN(Long userId, String vin);

    /**
     * 通过车型获取车辆配置(车型识别)
     *
     * @param modelId
     * @param userId 用户
     * @param vin 用户
     * @return
     */
    CarParamConfigResult getModelParameters(Long userId, Long modelId,String vin);

    /**
     * 功能描述: 获取保险信息报告
     *
     * @param dto
     * @param userId 用户
     * @return java.lang.String
     * @auther mengyao
     * @date 2019/11/22 0022 上午 10:46
     */
    String getInsuranceReport(Long userId, CarOpenApiDTO dto);


    /**
     * 功能描述: 获取维修保养信息报告
     *
     * @param dto
     * @param userId 用户
     * @return java.lang.String
     * @auther mengyao
     * @date 2019/11/27 0027 下午 2:18
     */
    String getMaintenanceReport(Long userId, CarOpenApiDTO dto);


    /**
     * 功能描述: 基础评估接口
     *
     * @param dto
     * @param userId 用户
     * @return java.lang.String
     * @auther mengyao
     * @date 2019/12/9 0009 下午 2:13
     */
    String getUsedCarPrice(Long userId, Che300UsedCarPriceDTO dto) throws Exception;

    /**
     * 功能描述: 获取车辆排放
     *
     * @param modelId
     * @param userId 用户
     * @param vin 用户
     * @return java.lang.String
     * @auther mengyao
     * @date 2019/12/10 0010 下午 4:55
     */
    String getCarModelInfo(Long userId, Long modelId,String vin) throws Exception;

    /**
     * 功能描述: 获取城市列表
     * @param 
     * @return java.lang.String
     * @auther mengyao
     * @date 2020/1/6 0006 下午 1:38
     */
    CityListResult getAllCity();

    /**
     * 功能描述: 出险回调接口
     * @param order_no
     * @param ret_code
     * @param order_no
     * @return Che300ResultVO
     * @auther mengyao
     * @date 2020-02-25 10:41
     */

    Che300ResultVO getInsuranceReportResult(String order_no, Integer ret_code);
    /**
     * 功能描述: 出险回调接口
     * @param order_no
     * @param ret_code
     * @param order_no
     * @return Che300ResultVO
     * @auther mengyao
     * @date 2020-02-25 10:41
     */

    Che300ResultVO getMaintenanceReportResult(String order_no,Integer ret_code);
}
