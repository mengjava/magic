package com.haoqi.magic.business.service.impl;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.enums.ServiceItemTypeEnum;
import com.haoqi.magic.business.enums.TradeTypeEnum;
import com.haoqi.magic.business.model.dto.CarOpenApiDTO;
import com.haoqi.magic.business.model.dto.Che300UsedCarPriceDTO;
import com.haoqi.magic.business.model.entity.Che300CarPrice;
import com.haoqi.magic.business.model.entity.CsVipRecordDetail;
import com.haoqi.magic.business.model.vo.Che300ResultVO;
import com.haoqi.magic.business.open.*;
import com.haoqi.magic.business.open.result.CarConfigResult;
import com.haoqi.magic.business.open.result.CarReportResult;
import com.haoqi.magic.business.open.result.CityListResult;
import com.haoqi.magic.business.service.ICarOpenApiService;
import com.haoqi.magic.business.service.ICsVipRecordDetailService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.constants.ConstantsDictClass;
import com.haoqi.magic.common.enums.Che300CodeEnum;
import com.haoqi.magic.common.enums.EmissionStandardEnum;
import com.haoqi.rigger.core.error.RiggerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yanhao on 2019/8/10.
 */
@Service
@Slf4j
public class CarOpenApiServiceImpl extends BaseServiceImpl implements ICarOpenApiService {

    /**
     * VIN识别车型接口Url
     * prod: http://api.che300.com/service/identifyModelByVIN
     * dev: http://testapi.che300.com/service/identifyModelByVIN
     */
    @Value("${che300.baseUrl}")
    private String baseUrl;
    /**
     * prod: f20b6f30cc3eed313b1d29f6da695891
     * dev: 2db5c45b5a25cfe562627bd29ad5309b
     */
    @Value("${che300.token}")
    private String token;
    /**
     * 保险查询服务
     */
    @Value("${che300.insuranceBaseUrl}")
    private String insuranceBaseUrl;
    /**
     * 维修保养查询服务
     */
    @Value("${che300.maintenanceBaseUrl}")
    private String maintenanceBaseUrl;

    @Value("${che300.customerId}")
    private String customerId;

    @Value("${che300.key}")
    private String key;
    /**
     * 基础估值url
     */
    @Value("${che300.usedCarPrice}")
    private String usedCarPriceUrl;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;
    /**
     * 维保回调接口
     */
    @Value("${che300.maintenanceCallbackUrl}")
    private String maintenanceCallbackUrl;
    /**
     * 出险回调接口
     */
    @Value("${che300.insuranceCallbackUrl}")
    private String insuranceCallbackUrl;


    /**
     * 车架评估返回结果
     */
    private final static String carPriceState = "0";




    @Autowired
    private ICsVipRecordDetailService csVipRecordDetailService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CarIdentifyResult> identifyModelByVIN(Long userId, String vin) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("token", token);
        map.put("vin", vin);
        List<CarIdentifyResult> list = new ArrayList<>();
        try {
            String result = HttpUtil.get(baseUrl + "/identifyModelByVIN", map);
            IdentifyModelByVinResult identifyModelByVinResult = JSONUtil.toBean(result, IdentifyModelByVinResult.class);
            if (identifyModelByVinResult.getStatus().equals(Constants.YES.toString())) {
                List<ModelInfo> modelInfo = identifyModelByVinResult.getModelInfo();
                modelInfo.forEach(
                        model -> {
                            CarIdentifyResult identifyResult = new CarIdentifyResult();
                            identifyResult.setVin(vin);
                            setModelResult(model, identifyResult);
                            list.add(identifyResult);
                        }
                );
            }

        } catch (Exception e) {
            log.error("car 300 identifyModelByVin error : " + e.getMessage());
        }
        return list;
    }

    @Override
    public CarParamConfigResult getModelParameters(Long userId, Long modelId,String vin) {
        AtomicInteger integer = new AtomicInteger(0);
        Map<String, Object> map = Maps.newHashMap();
        map.put("token", token);
        map.put("modelId", modelId);
        try {

            //  判断缓存是否存在结果数据
            String modelParametersKey = String.format("%s:modelParameters:%s", prefix, modelId);
            // 判断用户是否购买
            String modelParametersUserKey = String.format("%s:modelParameters:%s", vin, userId);

            String carParamConfigUserResultStr = (String) redisTemplate.opsForValue().get(modelParametersUserKey);
            String carParamConfigResultStr = (String) redisTemplate.opsForValue().get(modelParametersKey);

            // 缓存里有 此人没有买过 则进行计费 插入账单中
            if (!StringUtils.isEmpty(carParamConfigUserResultStr) && StringUtils.isEmpty(carParamConfigUserResultStr)) {
                buildPayOrder(userId, TradeTypeEnum.CAR_MODEL_TYPE.getKey(), integer);
                csVipRecordDetailService.insertDetail(ServiceItemTypeEnum.VEHICLE_IDENTIFICATION.getKey(), userId, null, vin, carParamConfigResultStr,null);
            }
            // 如果缓存有值 返回结果
            if (!StringUtils.isEmpty(carParamConfigResultStr)) {
                return JSONObject.parseObject(carParamConfigResultStr,CarParamConfigResult.class);
            }


            String result = HttpUtil.get(baseUrl + "/getModelParameters", map);
            CarConfigResult carResult = JSONUtil.toBean(result, CarConfigResult.class);
            if (Constants.YES.toString().equals(carResult.getStatus())) {
                Map<String, Object> maps = carResult.getParamters();
                CarParamConfigResult carParamConfigResult = new CarParamConfigResult();
                CarParamConfigUtils.getCarConfig(maps, carParamConfigResult);
                buildPayOrder(userId, TradeTypeEnum.CAR_MODEL_TYPE.getKey(), integer);
                csVipRecordDetailService.insertDetail(ServiceItemTypeEnum.VEHICLE_IDENTIFICATION.getKey(), userId, null, vin, JSONObject.toJSON(carParamConfigResult).toString(),null);
                // 查询到的订单号保存到redis中
                redisTemplate.opsForValue().set(modelParametersKey, JSONObject.toJSONString(carParamConfigResult), 7, TimeUnit.DAYS);
                redisTemplate.opsForValue().set(modelParametersUserKey, userId.toString(), 7, TimeUnit.DAYS);
                return carParamConfigResult;
            }
        } catch (Exception e) {
            log.error("car 300 getModelParameters error : " + e.getMessage());
        }
        return null;
    }

    /**
     * 功能描述: 获取保险信息报告
     *
     * @param dto
     * @param userId 用户
     * @return java.lang.String
     * @auther mengyao
     * @date 2019/11/22 0022 上午 10:46
     */
    @Override
    //@Transactional(rollbackFor = Exception.class)
    public String getInsuranceReport(Long userId, CarOpenApiDTO dto) {
        /**
         *  1 生成报告
         *    1.1 生成签名
         *      为了确保用户发送的请求不被更改，我们设计了签名算法。该算法基本可以保证
         * 		请求是合法者发送且参数没有被修改，但无法保证不被偷窥。该签名方法由参数
         * 		名称、密钥、当前日期（年月日），三部分组成。签名生成规则如下：
         * 		A） 参数名称 key 按照 ascii 码从小到大排序，以 key=>value 的形式序列化。
         * 		B） 密钥
         * 		C） 当前日期（注：月日前有前导 0， 如： 2017-02-10）
         * 		将以上 ABC 三部分的字符串计算 MD5 值，形成一个 32 位的十六进制（字母小写）
         * 		字符串，即为本次请求 sn（签名）的值。
         * 		本例 sn（签名）生成 Demo 参考如下：sn=md5(customer_id=00000&engine_no=JS3233&id_no=110110190011002010&vin=LZWACAGA8BA014187GWWmuOHS9ZzxAUjM2017-02-10)
         * 		sn： 785d5da60c742a3677dfe2bf0021d119
         */
        AtomicInteger integer = new AtomicInteger(0);
        Map<String, Object> map = getStringObjectMap(dto.getVin(),insuranceCallbackUrl);
        // A)查看是否购买 如果已经购买则从缓存中获取
        String insuranceUserKey = String.format("%s:insuranceUserKey:%s", dto.getVin(), dto.getUserId());
        String insuranceUserKeyResult = (String) redisTemplate.opsForValue().get(insuranceUserKey);


        String insuranceKey = String.format("%s:insuranceKey:%s", prefix, dto.getVin());
        String redis_report_url = (String) redisTemplate.opsForValue().get(insuranceKey);
        // 此账户没有买过 则进行计费 插入账单中


        String insuranceOrderNoKey = String.format("%s:insuranceOrderNoKey:%s", prefix, dto.getVin());
        String order_no = (String) redisTemplate.opsForValue().get(insuranceOrderNoKey);

        if (StringUtils.isEmpty(insuranceUserKeyResult)) {
            //1.2 发送请求
            order_no = getOrderNo(map);
            buildPayOrder(userId, TradeTypeEnum.INSURANCE_TYPE.getKey(), integer);
            if (Constants.NO.equals(dto.getType())){
                csVipRecordDetailService.insertDetail(ServiceItemTypeEnum.OUT_DANGER.getKey(), dto.getUserId(), dto.getUserName(), dto.getVin(), redis_report_url,order_no);
            }
            redisTemplate.opsForValue().set(insuranceUserKey, dto.getUserId().toString(), 7, TimeUnit.DAYS);
        }
        // 如果缓存有值 返回结果
        if (!StringUtils.isEmpty(redis_report_url)) {
            return redis_report_url;
        }
        //1.2 发送请求
        if (StringUtils.isEmpty(order_no)){
            order_no = getOrderNo(map);
        }

        //2 根据order_no获取报告
        map.put("order_no", order_no);
        String reportResultStr = HttpUtil.post(insuranceBaseUrl + "/get_report", map);
        CarReportResult carReportResult = JSONUtil.toBean(reportResultStr, CarReportResult.class);
        String report_url = carReportResult.getData().get("report_url");



        log.info("*********11：{}，*****************22： {} *****: {} ***order_no: {}", redis_report_url, insuranceUserKeyResult,report_url,order_no);

        // 查询到的订单号保存到redis中
        redisTemplate.opsForValue().set(insuranceOrderNoKey, order_no, 7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(insuranceKey, report_url, 7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(insuranceUserKey, dto.getUserId().toString(), 7, TimeUnit.DAYS);
        return getReportUrl(carReportResult, report_url);
    }

    private String getOrderNo(Map<String, Object> map) {
        String result = HttpUtil.post(insuranceBaseUrl, map);
        CarReportResult orderNoResult = JSONUtil.toBean(result, CarReportResult.class);
        if (!orderNoResult.getCode().equals(Che300CodeEnum.CODE_2000.getKey())) {
            throw new RiggerException(orderNoResult.getMsg());
        }
        return orderNoResult.getData().get("order_no");
    }

    private Map<String, Object> getStringObjectMap(String vin,String callback_url) {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String snString = "callback_url=" + callback_url + "&customer_id=" + customerId + "&vin=" + vin + key + df.format(dt);
        String sn = SecureUtil.md5(snString);
        Map<String, Object> map = Maps.newHashMap();
        map.put("callback_url", callback_url);
        map.put("customer_id", customerId);
        map.put("sn", sn);
        map.put("vin", vin);
        return map;
    }


    @Override
    //@Transactional(rollbackFor = Exception.class)
    public String getMaintenanceReport(Long userId, CarOpenApiDTO dto) {
        /**
         * B) 查询品牌是否支持查询
         *    1 生成签名
         * 		为了确保用户发送的请求不被更改，我们设计了签名算法。该算法基本可以保证
         * 		请求是合法者发送且参数没有被修改，但无法保证不被偷窥。该签名方法由参数
         * 		名称、密钥、当前日期（年月日），三部分组成。签名生成规则如下：
         * 		A） 参数名称 key 按照 ascii 码从小到大排序，以 key=>value 的形式序列化。
         * 		B） 密钥
         * 		C） 当前日期（注：月日前有前导 0， 如： 2017-08-24）
         * 		将以上 ABC 三部分的字符串计算 MD5 值，形成一个 32 位的十六进制（字母小
         * 		写）字符串，即为本次请求 sn（签名）的值。
         * 		本例 sn（签名）生成 Demo 参考如下：
         * 		sn=md5(customer_id=99&vin=LDCC13X25B1607426hQpIRqH4dVpjT8OS2017-08-24)
         * 		sn： 39fc71f808f1caabc778e12347258a6b
         *
         */
        AtomicInteger integer = new AtomicInteger(0);
        Map<String, Object> map = getStringObjectMap(dto.getVin(),maintenanceCallbackUrl);
        // 查看是否支持此品牌查询
        String result = HttpUtil.post(maintenanceBaseUrl + "/check_brand", map);
        CarReportResult carReportResult = JSONUtil.toBean(result, CarReportResult.class);
        if (!carReportResult.getCode().equals(Che300CodeEnum.CODE_2000.getKey())) {
            throw new RiggerException(carReportResult.getMsg());
        }
        // A)查看是否购买 如果已经购买则从缓存中获取
        String maintenanceUserKey = String.format("%s:maintenanceUserKey:%s", dto.getVin(), dto.getUserId());
        String maintenanceUserKeyResult = (String) redisTemplate.opsForValue().get(maintenanceUserKey);
        // 如果缓存有值 返回结果
        String maintenanceKey = String.format("%s:maintenanceKey:%s", prefix, dto.getVin());
        String redis_report_url = (String) redisTemplate.opsForValue().get(maintenanceKey);


        // 缓存有但是 此账户没有买过 则进行计费 插入账单中
        //  B) 购买报告 获取订单号(同一vin重复购买,会重复收费,请谨慎使用

        String maintenanceOrderNoKey = String.format("%s:maintenanceOrderNoKey:%s", prefix, dto.getVin());
        String order_no = (String) redisTemplate.opsForValue().get(maintenanceOrderNoKey);


        if (StringUtils.isEmpty(maintenanceUserKeyResult)) {
            if (StringUtils.isEmpty(order_no)){
                order_no = getMaintenanceOrderNo(map);
            }
            redisTemplate.opsForValue().set(maintenanceUserKey, dto.getUserId().toString(), 7, TimeUnit.DAYS);
            buildPayOrder(userId, TradeTypeEnum.MAINTENANCE_TYPE.getKey(), integer);
            if (Constants.NO.equals(dto.getType())) {
                csVipRecordDetailService.insertDetail(ServiceItemTypeEnum.INSURANCE_RECORD.getKey(), dto.getUserId(), dto.getUserName(), dto.getVin(), redis_report_url,order_no);
            }
        }
       if (!StringUtils.isEmpty(redis_report_url)) {
            return redis_report_url;
        }


        if (StringUtils.isEmpty(order_no)){
            order_no = getMaintenanceOrderNo(map);
        }

        //  C) 根据订单号 获取报告
        Map<String, Object> getReportMap = Maps.newHashMap();
        getReportMap.put("customer_id", customerId);
        getReportMap.put("order_no", order_no);
        String getReportResult = HttpUtil.post(maintenanceBaseUrl + "/get_report_v2", getReportMap);
        CarReportResult resultEntity = JSONUtil.toBean(getReportResult, CarReportResult.class);
        String report_url = resultEntity.getData().get("report_url");

        log.info("*********11：{}，*****************22： {} *****: {} ***order_no: {}", redis_report_url, maintenanceUserKeyResult,report_url,order_no);

        // 查询到的订单号保存到redis中
        redisTemplate.opsForValue().set(maintenanceKey, report_url, 7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(maintenanceUserKey, dto.getUserId().toString(), 7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(maintenanceOrderNoKey, order_no, 7, TimeUnit.DAYS);
        return getReportUrl(carReportResult, report_url);
    }

    private String getMaintenanceOrderNo(Map<String, Object> map) {
        String order_no;
        String buyReportResult = HttpUtil.post(maintenanceBaseUrl + "/buy_report", map);
        CarReportResult buyReportResultEntity = JSONUtil.toBean(buyReportResult, CarReportResult.class);
        if (!buyReportResultEntity.getCode().equals(Che300CodeEnum.CODE_2000.getKey())) {
            throw new RiggerException(buyReportResultEntity.getMsg());
        }
        order_no = buyReportResultEntity.getData().get("order_no");
        return order_no;
    }

    private String getReportUrl(CarReportResult carReportResult, String  report_url) {
        Boolean result1 = carReportResult.getCode().equals(Che300CodeEnum.CODE_2001.getKey());
        Boolean result2 = carReportResult.getCode().equals(Che300CodeEnum.CODE_2002.getKey());
        Boolean result3 = !carReportResult.getCode().equals(Che300CodeEnum.CODE_2000.getKey());
        if (result1) {
            return Che300CodeEnum.CODE_2001.getName();
        }
        if (result2) {
            return Che300CodeEnum.CODE_2002.getName();
        }
        if (result3) {
            log.error("获取报告结果：{}", carReportResult.getMsg());
            throw new RiggerException(carReportResult.getMsg());
        }

        return report_url;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getUsedCarPrice(Long userId, Che300UsedCarPriceDTO dto) {
        String url = usedCarPriceUrl;
        AtomicInteger integer = new AtomicInteger(0);

        String usedCarPriceKey = String.format("%s:carPrice:%s", prefix, dto.getModelId());
        String result_url = (String) redisTemplate.opsForValue().get(usedCarPriceKey);


        String usedCarPriceUserKey = String.format("%s:carPrice:%s", dto.getVin(), userId);
        String redis_result_url = (String) redisTemplate.opsForValue().get(usedCarPriceUserKey);
        // 缓存里有 此人没有买过 则进行计费 插入账单中
        if (!StringUtils.isEmpty(result_url) && StringUtils.isEmpty(redis_result_url)) {
            buildPayOrder(userId, TradeTypeEnum.EVALUATE_TYPE.getKey(), integer);
            csVipRecordDetailService.insertDetail(ServiceItemTypeEnum.FAST_EVALUATION.getKey(), userId, null, dto.getVin(), result_url,null);

        }
        if (!StringUtils.isEmpty(result_url)) {
                return result_url;
        }
        // 先进行扣费处理 再查询
        buildPayOrder(userId, TradeTypeEnum.EVALUATE_TYPE.getKey(), integer);
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("token", token);
        map.put("modelId", dto.getModelId());
        map.put("regDate", dto.getRegDate());
        map.put("mile", dto.getMile());
        map.put("zone", dto.getZone());
        String result = HttpUtil.get(url, map, 60000);
        log.info("车300基础估值接口参数{}" ,JSONObject.toJSONString(map));
        if (StringUtils.isEmpty(result)) {
            throw new RiggerException("调用车300基础估值接口异常");
        }
           Che300CarPrice   che300CarPrice = JSON.parseObject(result, Che300CarPrice.class);
        if (che300CarPrice.getStatus().equals(carPriceState)) {
            throw new RiggerException(che300CarPrice.getErrorMsg());
        }
        String report_url = che300CarPrice.getUrl();
        csVipRecordDetailService.insertDetail(ServiceItemTypeEnum.FAST_EVALUATION.getKey(), userId, null, dto.getVin(), report_url,null);
        // 查询到的订单号保存到redis中
        redisTemplate.opsForValue().set(usedCarPriceKey, report_url, 7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(usedCarPriceUserKey, userId.toString(), 7, TimeUnit.DAYS);
        return report_url;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getCarModelInfo(Long userId, Long modelId,String vin) {
        AtomicInteger integer = new AtomicInteger(0);

        String dischargeKey = String.format("%s:discharge:%s", prefix, modelId);
        String discharge = (String) redisTemplate.opsForValue().get(dischargeKey);

        String dischargeUserKey = String.format("%s:discharge:%s", vin, userId);
        String redisDischarge = (String) redisTemplate.opsForValue().get(dischargeUserKey);

        // 缓存里有 此人没有买过 则进行计费 插入账单中
        if (!StringUtils.isEmpty(discharge) && StringUtils.isEmpty(redisDischarge)) {
            buildPayOrder(userId, TradeTypeEnum.EMISSION_TYPE.getKey(), integer);
            csVipRecordDetailService.insertDetail(ServiceItemTypeEnum.EMISSION.getKey(), userId, null, vin, discharge,null);
        }
        if (!StringUtils.isEmpty(discharge)) {
            return discharge;
        }

        // 先进行扣费处理 再查询
        buildPayOrder(userId, TradeTypeEnum.EMISSION_TYPE.getKey(), integer);
        Map<String, Object> map = Maps.newHashMap();
        map.put("token", token);
        map.put("modelId", modelId);
        String result = HttpUtil.get(baseUrl + "/getCarModelInfo", map, 60000);
        CarConfigResult carResult = JSONUtil.toBean(result, CarConfigResult.class);
        if (!Constants.YES.equals(Integer.parseInt(carResult.getStatus()))) {
            throw new RiggerException(carResult.getError_msg());
        }
        Map<String, Object> maps = carResult.getModel();
        discharge = maps.get("discharge").toString();
        csVipRecordDetailService.insertDetail(ServiceItemTypeEnum.EMISSION.getKey(), userId, null, vin, discharge,null);
        // 查询到的订单号保存到redis中
        redisTemplate.opsForValue().set(dischargeKey, discharge, 7, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(dischargeUserKey, userId.toString(), 7, TimeUnit.DAYS);
        return discharge;
    }

    @Override
    public CityListResult getAllCity() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("token", token);
        String result = HttpUtil.get(baseUrl + "/getAllCity", map, 60000);
        CityListResult carResult = JSONUtil.toBean(result, CityListResult.class);
        return carResult;
    }


    @Override
    public Che300ResultVO getInsuranceReportResult(String order_no,Integer ret_code) {
        List<CsVipRecordDetail> csVipRecordDetail = csVipRecordDetailService.selectList(new EntityWrapper<CsVipRecordDetail>().eq("outer_no", order_no));
        Map<String, Object> map = getStringObjectMap(csVipRecordDetail.get(0).getVin(),insuranceBaseUrl);
        map.remove("vin");
        //2 根据order_no获取报告
        map.put("order_no", order_no);
        String reportResultStr = HttpUtil.post(insuranceBaseUrl + "/get_report", map);
        return getChe300ResultVO(csVipRecordDetail, reportResultStr);

    }
    @Override
    public Che300ResultVO getMaintenanceReportResult(String order_no,Integer ret_code) {
        List<CsVipRecordDetail> csVipRecordDetail = csVipRecordDetailService.selectList(new EntityWrapper<CsVipRecordDetail>().eq("outer_no", order_no));
        Map<String, Object> getReportMap = Maps.newHashMap();
        getReportMap.put("customer_id", customerId);
        getReportMap.put("order_no", order_no);
        //2 根据order_no获取报告
        String getReportResult = HttpUtil.post(maintenanceBaseUrl + "/get_report_v2", getReportMap);
        return getChe300ResultVO(csVipRecordDetail, getReportResult);

    }

    private Che300ResultVO getChe300ResultVO(List<CsVipRecordDetail> csVipRecordDetail, String getReportResult) {
        CarReportResult resultEntity = JSONUtil.toBean(getReportResult, CarReportResult.class);
        log.info("结果*************************** " + resultEntity);

        String report_url = resultEntity.getData().get("report_url");
        Integer code = resultEntity.getCode();

        log.info("回调测试***************************url " + report_url);
        Che300ResultVO che300ResultVO = new Che300ResultVO();

        if (Che300CodeEnum.CODE_2000.getKey().equals(code) || Che300CodeEnum.CODE_2002.getKey().equals(code)) {
            if (!StringUtils.isEmpty(report_url)){
                csVipRecordDetail.forEach(m->{
                    m.setResult(report_url);
                });
                csVipRecordDetailService.updateBatchById(csVipRecordDetail);
            }
            che300ResultVO.setCode(Che300CodeEnum.CODE_2000.getKey());
            che300ResultVO.setMsg(Che300CodeEnum.CODE_2000.getName());
            return che300ResultVO;
        }


        che300ResultVO.setCode(Che300CodeEnum.CODE_9000.getKey());
        che300ResultVO.setMsg(Che300CodeEnum.CODE_9000.getName());
        return che300ResultVO;
    }


    private void buildPayOrder(Long userId, Integer type, AtomicInteger integer) {
        try {
            super.buildPayOrderAndSubBalance(userId, type, "", null);
        } catch (RiggerException e) {
            if (10000 != e.getCode() || integer.getAndIncrement() > 3) {
                throw e;
            }
            if (integer.getAndIncrement() < 3) {
                buildPayOrder(userId, type, integer);
            }
        }
    }

    private void setModelResult(ModelInfo model, CarIdentifyResult identifyResult) {
        String model_liter = model.getModelLiter();
        identifyResult.setDisplacement(Double.parseDouble(model_liter.substring(0, model_liter.length() - 1)));
        identifyResult.setDisplacementType(model_liter.substring(model_liter.length() - 1));
        identifyResult.setSysCarModelId(model.getModelId());
        identifyResult.setSysCarBrandName(model.getBrandName());
        identifyResult.setSysCarSeriesName(model.getSeriesName());
        identifyResult.setSysCarModelName(model.getModelName());
        identifyResult.setGearBox(model.getModelGear());
        identifyResult.setGearBoxCode(ConstantsDictClass.GearBoxTypeEnum.getKeyByName(model.getModelGear()));
        identifyResult.setEmissionStandard(model.getModelEmissionStandard());
        identifyResult.setEmissionStandardCode(EmissionStandardEnum.getTypeCode(model.getModelEmissionStandard()));
        identifyResult.setDiffParams(model.getDiff_params());
    }


}
