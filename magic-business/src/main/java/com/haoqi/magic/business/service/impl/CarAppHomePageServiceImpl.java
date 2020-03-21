package com.haoqi.magic.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.common.utils.KeyValueDescUtil;
import com.haoqi.magic.business.enums.CarPublishStatusEnum;
import com.haoqi.magic.business.enums.CarPullOffTypeEnum;
import com.haoqi.magic.business.enums.UserTypeEnum;
import com.haoqi.magic.business.mapper.CarAppHomePageMapper;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.haoqi.magic.business.model.vo.AppHomePageVO;
import com.haoqi.magic.business.model.vo.HomePageBaseVO;
import com.haoqi.magic.business.model.vo.PullOffCarVO;
import com.haoqi.magic.business.service.*;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.constants.ConstantsDictClass;
import com.haoqi.magic.common.enums.DictClassEnum;
import com.haoqi.magic.common.enums.EmissionStandardEnum;
import com.haoqi.magic.common.enums.ErrorsEnum;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.error.ErrorCodeEnum;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能描述:
 *
 * @Author: yanhao
 * @Date: 2019/12/3 15:05
 * @Param:
 * @Description:
 */
@Service
@Slf4j
public class CarAppHomePageServiceImpl extends BaseServiceImpl implements ICarAppHomePageService {


    @Autowired
    private ICsTagService csTagService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Autowired
    private ICsHitTagRelativeService csHitTagRelativeService;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Autowired
    private CarAppHomePageMapper carAppHomePageMapper;

    @Autowired
    private ICsCarInfoService carInfoService;

    @Autowired
    private ICsCarDealerService csCarDealerService;

    @Autowired
    private ICsCarOrderService carOrderService;

    @Autowired
    private ICsServiceFeeService csServiceFeeService;

    @Autowired
    private ICsUserBankCardService userBankCardService;

    @Override
    public List<CsAppHotCityDTO> getHotCity() {
        Result<List<CsAppHotCityDTO>> result = systemServiceClient.getHotCity();
        if (result.isSuccess()) {
            List<CsAppHotCityDTO> data = result.getData();
            if (Objects.nonNull(data) && !data.isEmpty()) {
                CsAppHotCityDTO dto = new CsAppHotCityDTO();
                dto.setCityName("全国");
                data.add(dto);
            }
            return data;
        }
        return new ArrayList<>();
    }

    @Override
    public List<CsHotCityDTO> getAllCity() {
        Map<String, List<CsAppHotCityDTO>> map = Maps.newHashMap();
        List<CsHotCityDTO> list = new ArrayList<>();
        Result<List<CsAppHotCityDTO>> result = systemServiceClient.getAllCity();
        if (!result.isSuccess()) {
            return list;
        }
        List<CsAppHotCityDTO> cityDTOS = result.getData();
        Map<String, List<CsAppHotCityDTO>> collect = cityDTOS.stream().collect(Collectors.groupingBy(CsAppHotCityDTO::getCityInitial));
        for (String city : collect.keySet()) {
            CsHotCityDTO dto = new CsHotCityDTO();
            dto.setLetter(city);
            dto.setList(collect.get(city));
            list.add(dto);
        }
        return list;
    }

    @Override
    public Page selectCarSearchParam(AppHomePageVO param, UserDTO currentUserInfo) {
        setLocate(param);
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        Query query = new Query(params);
        setSearchQueryParam(param, query);
        List<HomePageDTO> list = selectCarBase(query, currentUserInfo);
        return query.setRecords(list);
    }

    /**
     * 查询参数封装
     *
     * @param param
     * @param query
     */
    private void setSearchQueryParam(AppHomePageVO param, Query query) {

        //1:买车 2:每日促销 3:一口价

        //最新发布:1 价格最高:2 价格最低:3 车龄最短:4 里程最短:5 诚信联盟:6

        //标签id
        if (Objects.nonNull(param.getTagId())) {
            //首页今日推荐标签 默认10条
            String tags = csTagService.getCsSqlStrByTagId(param.getTagId());
            if (StringUtils.isNotEmpty(tags)) {
                query.getCondition().put("tagStr", tags);
            }
        }
        //车龄(1) 级别 变速箱(1) 排量 里程(1) 驱动方式 颜色  排放标准 座位 配置

        //车龄范围(单选)(获取sql)
        if (StringUtils.isNotEmpty(param.getAge())) {
            try {
                Result<SysDictDTO> result = systemServiceClient.getDict(param.getAge());
                if (result.isSuccess() && Objects.nonNull(result.getData()) && StringUtils.isNotEmpty(result.getData().getRemark())) {
                    query.getCondition().put("carAgeStr", result.getData().getRemark());
                }
            } catch (Exception e) {
                //log
            }
        }
        //级别(车辆类型)多选
        if (StringUtils.isNotEmpty(param.getCarType())) {
            List<String> list = getList(param.getCarType());
            query.getCondition().put("carTypeList", list);
        }
        //变速箱(单选)
        if (StringUtils.isNotEmpty(param.getGearBox())) {
            query.getCondition().put("gearBox", param.getGearBox());
        }
        //排量(单选)(获取sql)
        if (StringUtils.isNotEmpty(param.getDisplacement())) {
            try {
                Result<SysDictDTO> result = systemServiceClient.getDict(param.getDisplacement());
                if (result.isSuccess() && Objects.nonNull(result.getData()) && StringUtils.isNotEmpty(result.getData().getRemark())) {
                    query.getCondition().put("carDisplacementStr", result.getData().getRemark());
                }
            } catch (Exception e) {
                //log
            }
        }
        //驱动方式(单选)
        if (StringUtils.isNotEmpty(param.getDriveMethod())) {
            query.getCondition().put("driveMethod", param.getDriveMethod());
        }
        //颜色(多选)
        if (StringUtils.isNotEmpty(param.getColor())) {
            List<String> list = getList(param.getColor());
            query.getCondition().put("carColorList", list);
        }
        //排放标准(多选)
        if (StringUtils.isNotEmpty(param.getEmissionStandard())) {
            List<String> list = getList(param.getEmissionStandard());
            query.getCondition().put("emissionStandardList", list);
        }
        //生产方式 (进口 国产)
        if (StringUtils.isNotEmpty(param.getImportType())) {
            List<String> list = getList(param.getImportType());
            List<Integer> importList = new ArrayList<>();
            list.forEach(e -> {
                importList.add(ConstantsDictClass.ImportTypeEnum.getValue(e));
            });
            query.getCondition().put("importTypeList", importList);
        }
        //配置

        //车辆年龄（主要是为了车辆定制中的[匹配车辆]）
        if (null != param.getAgeNum()) {
            query.getCondition().put("ageNum", param.getAgeNum());
        }

        //行驶里数（主要是为了车辆定制中的[行驶里数]）
        if (null != param.getTravelDistanceNum()) {
            query.getCondition().put("travelDistanceNum", param.getTravelDistanceNum());
        }

        //座位数
        if (StrUtil.isNotEmpty(param.getSeatNum())) {
            try {
                Result<SysDictDTO> result = systemServiceClient.getDict(param.getSeatNum());
                if (result.isSuccess() && Objects.nonNull(result.getData()) && StringUtils.isNotEmpty(result.getData().getRemark())) {
                    query.getCondition().put("seatNumStr", result.getData().getRemark());
                }
            } catch (Exception e) {
                //log
            }
        }

    }


    public List<HomePageDTO> selectCarBase(Query query, UserDTO currentUserInfo) {
        List<HomePageDTO> list = carAppHomePageMapper.selectIndexPageParam(query, query.getCondition());
        setAppCarResult(list, currentUserInfo);
        return list;
    }


    @Override
    public CarInfoDTO getCarInfoById(Long id, UserDTO currentUserInfo) {
        Boolean anonymous = Boolean.TRUE;//匿名
        Boolean vipFlag = Boolean.FALSE;//是否vip
        String cityName = "";
        BigDecimal balanceMoney = BigDecimal.ZERO;
        if (Objects.nonNull(currentUserInfo)) {
            anonymous = Boolean.FALSE;
            cityName = currentUserInfo.getArea();
            vipFlag = Constants.YES.equals(currentUserInfo.getVipFlag()) ? false : true;
            balanceMoney = currentUserInfo.getBalanceMoney();
        }
        CarInfoDTO carInfo = carInfoService.getCarInfoById(id);
        CsCarDealer carDearIdByType = getCarDearIdByType(currentUserInfo);
        Long carDealerId = Objects.isNull(carDearIdByType) ? null : carDearIdByType.getId();
        if (Objects.nonNull(carDearIdByType)) {
            BigDecimal wholesalePrice = carInfo.getWholesalePrice().multiply(new BigDecimal(10000));
            try {
                ServiceFreeDTO carServiceFree = csServiceFeeService.getCarServiceFree(carDearIdByType.getSysAreaId(), wholesalePrice);
                carInfo.setServiceFree(carServiceFree.getServiceFree());
                carInfo.setServiceType(carServiceFree.getServiceType());
            } catch (Exception e) {
                log.warn("通过车商城市id：{}，批发价格：{}，获取服务费信息异常。{}", carDearIdByType.getSysAreaId(), wholesalePrice, e);
            }
        }
        carInfo.setPriceStr(setHomePageResult(carInfo.getWholesalePrice(), carInfo.getLocate(), carInfo.getCsCarDealerId(), anonymous, vipFlag, cityName, balanceMoney, carDealerId));
        carInfo.setPrice(null);
        //其他详情
        //买入按钮判断(自己的车,交易的车都不能买)
        if (!carInfo.getCsCarDealerId().equals(carDealerId) && Constants.NO.equals(carInfo.getTradeFlag()) &&
                CarPublishStatusEnum.PURT_AWAY.getKey().equals(carInfo.getPublishStatus())) {
            carInfo.setBuyButton(Constants.YES);
        }
        return carInfo;
    }

    @Override
    public Map<String, Object> getOrderCount(UserDTO currentUserInfo) {
        Map<String, Object> map = Maps.newHashMap();
        if (Objects.isNull(currentUserInfo)) {
            return map;
        }
        AppBuyerCountDTO buyerCountDTO = new AppBuyerCountDTO();
        AppSellerCountDTO sellerCountDTO = new AppSellerCountDTO();
        if (UserLevelEnum.SELLER_LEVEL.getLevel().equals(currentUserInfo.getType())) {
            buyerCountDTO = carOrderService.getBuyerOrderCount(currentUserInfo.getId());
            sellerCountDTO = carOrderService.getSellerOrderCount(currentUserInfo.getId());
            //商家(买家和卖家中心)
            map.put("buyerCount", buyerCountDTO);
            map.put("sellerCount", sellerCountDTO);
        }
        if (UserLevelEnum.USER_LEVEL.getLevel().equals(currentUserInfo.getType())) {
            //买家(只有买家中心)
            buyerCountDTO = carOrderService.getBuyerOrderCount(currentUserInfo.getId());
            map.put("buyerCount", buyerCountDTO);
        }
        return map;
    }

    @Override
    public Boolean pullOffCar(PullOffCarVO vo, UserDTO currentUserInfo) {
        if (Objects.isNull(currentUserInfo) || !UserLevelEnum.SELLER_LEVEL.getLevel().equals(currentUserInfo.getType())) {
            return Boolean.FALSE;
        }
        CsCarHomeDTO homeDTO = new CsCarHomeDTO();
        homeDTO.setPullOffType(CarPullOffTypeEnum.PULLOFF_USER_TYPE.getKey());
        homeDTO.setId(vo.getId());
        homeDTO.setRemark(vo.getRemark());
        homeDTO.setPullOffLoginName(currentUserInfo.getLoginName());
        homeDTO.setUserType(currentUserInfo.getType());
        return carInfoService.pullOffCar(homeDTO);
    }

    @Override
    public Page findByPage(Query query) {
        List<AppCarPageDTO> list = carAppHomePageMapper.selectByPage(query, query.getCondition());
        Result<List<SysDictDTO>> standardCode = systemServiceClient.getDictByClass(DictClassEnum.EMISSION_STANDARD_160000.getClassCode());
        Result<List<SysDictDTO>> colorCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_COLOR_140000.getClassCode());
        Result<List<SysDictDTO>> boxCode = systemServiceClient.getDictByClass(DictClassEnum.TRANSMISSION_100000.getClassCode());
        Result<List<SysDictDTO>> carTypeCode = systemServiceClient.getDictByClass(DictClassEnum.CAR_TYPE_110000.getClassCode());
        Result<List<SysDictDTO>> driveTypeCode = systemServiceClient.getDictByClass(DictClassEnum.DRIVE_WAY_120000.getClassCode());
        Result<List<SysDictDTO>> fuelTypeCode = systemServiceClient.getDictByClass(DictClassEnum.FUEL_TYPE_130000.getClassCode());
        list.forEach(
                dto -> {
                    dto.setPictureURL(fastDfsFileService.getFastWebUrl());
                    dto.setEmissionStandardCode(KeyValueDescUtil.handleValueDesc(dto.getEmissionStandardCode(), standardCode.getData()));
                    dto.setGearBoxCode(KeyValueDescUtil.handleValueDesc(dto.getGearBoxCode(), boxCode.getData()));
                    dto.setColorCode(KeyValueDescUtil.handleValueDesc(dto.getColorCode(), colorCode.getData()));
                    dto.setCarTypeCode(KeyValueDescUtil.handleValueDesc(dto.getCarTypeCode(), carTypeCode.getData()));
                    dto.setDriveMethodCode(KeyValueDescUtil.handleValueDesc(dto.getDriveMethodCode(), driveTypeCode.getData()));
                    dto.setFuelTypeCode(KeyValueDescUtil.handleValueDesc(dto.getFuelTypeCode(), fuelTypeCode.getData()));
                    dto.setTravelDistance(dto.getInstrumentShowDistance());
                }
        );
        return query.setRecords(list);
    }

    /**
     * 车辆买入
     * 7.匿名用户
     * 1）买入时，直接跳转到登录界面
     * 8.普通用户：
     * 1)买入时：
     * 优先判断如果账户余额 <保证金金额时，弹出提示“您非会员，无法操作，请成为会员！”，点击会员按钮，去充值成为会员;
     * 其次当账户余额 >=保证金金额且绑定银行未成功时，弹出提示“你未实名绑定银行卡，请先实名绑卡!”，点击“确定”按钮跳转到绑卡页面。
     * 9.会员：
     * 1)同城买入时：
     * 优先判断该用户绑定银行卡成功否，没成功时弹出提示“你未实名绑定银行卡，请先实名绑卡!”，点击“确定”按钮跳转到绑卡页面；否则可直接做买入操作。
     * 2)跨城买入时：
     * 优先判断账户余额  < 保证金金额时，弹出提示“您账户余额不足XXX元，请先充值！”，点击充值按钮，去充值页面，XXX为保证金金额;
     * <p>
     * 其次当账户余额 >=保证金金额且绑定银行未成功时, 弹出提示“你未实名绑定银行卡，请先实名绑卡!”，点击“确定”按钮跳转到绑卡页面。
     * <p>
     * 最后当账户余额 >=保证金金额且绑定银行成功时, 没有设置支付密码，优先跳转到“支付密码(设置)”，反之弹出支付密码界面。
     */
    @Override
    public Boolean checkCarBuyRule(Long id, UserDTO currentUserInfo) {
        CsCarInfo csCarInfo = carInfoService.selectById(id);
        if (Objects.isNull(csCarInfo)) {
            throw new RiggerException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "车辆不存在!");
        }
        if (!Constants.NO.equals(csCarInfo.getTradeFlag())) {
            throw new RiggerException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "该车辆正在交易中!");
        }
        BigDecimal balanceMoney = currentUserInfo.getBalanceMoney();
        BigDecimal frozenAmount = enableFrozenAmount(10, "请设置买入保证金金额");
        if (isVip(currentUserInfo.getVipFlag())) {
            if (!sameCity(currentUserInfo.getArea(), csCarInfo.getLocate())) {
                if (!compareMoney(balanceMoney, frozenAmount)) {
                    throw new RiggerException(ErrorsEnum.ERRORS_300001.getKey(), "您账户余额不足" + frozenAmount + "元，请先充值！");
                }
                if (compareMoney(balanceMoney, frozenAmount) && !isBindingBankCard(currentUserInfo.getId())) {
                    throw new RiggerException(ErrorsEnum.ERRORS_300002.getKey(), "你未实名绑定银行卡，请先实名绑卡!");
                }
                if (compareMoney(balanceMoney, frozenAmount) && isBindingBankCard(currentUserInfo.getId()) &&
                        !hasPayPassword(currentUserInfo.getPayPassword())) {
                    throw new RiggerException(ErrorsEnum.ERRORS_300003.getKey(), "你未设置支付密码，请先支付密码!");
                }
            }
            //vip 同城 未绑定银行卡
            if (sameCity(currentUserInfo.getArea(), csCarInfo.getLocate()) && !isBindingBankCard(currentUserInfo.getId())) {
                throw new RiggerException(ErrorsEnum.ERRORS_300002.getKey(), "你未实名绑定银行卡，请先实名绑卡!");
            }
        } else {
            if (!compareMoney(balanceMoney, frozenAmount)) {
                throw new RiggerException(ErrorsEnum.ERRORS_300004.getKey(), "您非会员，无法操作，请成为会员！");
            }
            if (compareMoney(balanceMoney, frozenAmount) && !isBindingBankCard(currentUserInfo.getId())) {
                throw new RiggerException(ErrorsEnum.ERRORS_300002.getKey(), "你未实名绑定银行卡，请先实名绑卡!");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 2.卖出操作
     * 1)同城卖出时：
     * 优先判断该用户绑定银行卡成功否，没成功时弹出提示“你未实名绑定银行卡，请先实名绑卡!”，点击“确定”按钮跳转到绑卡页面；否则可直接做卖出操作。
     * 2)跨城卖出时：
     * 优先判断账户余额  < 保证金金额时，弹出提示“您账户余额不足XXX元，请先充值！”，点击充值按钮，去充值页面，XXX为保证金金额;
     * 其次当账户余额 >=保证金金额且绑定银行未成功时, 弹出提示“你未实名绑定银行卡，请先实名绑卡!”，点击“确定”按钮跳转到绑卡页面；
     * 最后当账户余额 >=保证金金额且绑定银行成功时, 没有设置支付密码，优先跳转到“支付密码(设置)”，反之弹出支付密码界面。
     */
    @Override
    public Boolean checkCarSaleRule(Long id, UserDTO currentUserInfo) {
        CsCarInfo csCarInfo = carInfoService.selectById(id);
        BigDecimal balanceMoney = currentUserInfo.getBalanceMoney();
        if (Objects.isNull(csCarInfo)) {
            throw new RiggerException(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "车辆不存在!");
        }
        if (sameCity(currentUserInfo.getArea(), csCarInfo.getLocate())) {
            if (!isBindingBankCard(currentUserInfo.getId())) {
                throw new RiggerException(ErrorsEnum.ERRORS_300002.getKey(), "你未实名绑定银行卡，请先实名绑卡!");
            }
        } else {
            BigDecimal frozenAmount = enableFrozenAmount(12, "请设置卖出保证金金额");
            if (!compareMoney(balanceMoney, frozenAmount)) {
                throw new RiggerException(ErrorsEnum.ERRORS_300001.getKey(), "您账户余额不足" + frozenAmount + "元，请先充值！");
            }
            if (compareMoney(balanceMoney, frozenAmount) && !isBindingBankCard(currentUserInfo.getId())) {
                throw new RiggerException(ErrorsEnum.ERRORS_300002.getKey(), "你未实名绑定银行卡，请先实名绑卡!");
            }
            if (compareMoney(balanceMoney, frozenAmount) && isBindingBankCard(currentUserInfo.getId()) &&
                    !hasPayPassword(currentUserInfo.getPayPassword())) {
                throw new RiggerException(ErrorsEnum.ERRORS_300003.getKey(), "您未设置支付密码，请先支付密码!");
            }
        }
        return Boolean.TRUE;
    }

    /**
     * 返回结果封装
     * <p>
     *
     * @param list
     * @param currentUserInfo
     */
    private void setAppCarResult(List<HomePageDTO> list, UserDTO currentUserInfo) {
        Boolean anonymous = Boolean.TRUE;//匿名
        Boolean vipFlag = Boolean.FALSE;//是否vip
        String cityName = "";
        BigDecimal balanceMoney = BigDecimal.ZERO;
        if (Objects.nonNull(currentUserInfo)) {
            anonymous = Boolean.FALSE;
            cityName = currentUserInfo.getArea();
            vipFlag = Constants.YES.equals(currentUserInfo.getVipFlag()) ? false : true;
            balanceMoney = currentUserInfo.getBalanceMoney();
        }
        Long carDealerId = null;
        CsCarDealer carDearIdByType = getCarDearIdByType(currentUserInfo);
        if (Objects.nonNull(carDearIdByType)) {
            carDealerId = carDearIdByType.getId();
        }
        Boolean finalVipFlag = vipFlag;
        Boolean finalAnonymous = anonymous;
        String finalCityName = cityName;
        BigDecimal finalBalanceMoney = balanceMoney;
        Long finalCarDealerId = carDealerId;
        list.forEach(e -> {
            List<TagsDTO> tags = csHitTagRelativeService.getCsTagsByCarId(e.getId());
            e.setTagNames(tags);
            String url = URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), e.getIconFilePath());
            e.setIconFilePath(url);
            e.setPriceStr(setHomePageResult(e.getPrice(), e.getLocate(), e.getCsCarDealerId(), finalAnonymous, finalVipFlag, finalCityName, finalBalanceMoney, finalCarDealerId));
            e.setEmissionStandardCodeName(EmissionStandardEnum.getTypeDesc(e.getEmissionStandardCode()));
            e.setPrice(null);
        });
    }

    private CsCarDealer getCarDearIdByType(UserDTO currentUserInfo) {
        CsCarDealer currentCarDealerInfo = null;
        if (Objects.nonNull(currentUserInfo) && UserTypeEnum.USER_DEALER.getKey().equals(currentUserInfo.getType())) {
            //商家获取车商id
            try {
                currentCarDealerInfo = csCarDealerService.getCurrentCarDealerInfo(currentUserInfo.getLoginName());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return currentCarDealerInfo;
    }

    /**
     * 车价显示规则
     * 1）匿名：车价显示5.XX
     * 2）普通用户且同城：账户余额 >= 保证金金额时，显示5.98，否则显示5.XX
     * 3）会员同城：显示5.98
     * 4）会员跨城：显示5.XX
     * 5）跨城：显示5.XX
     * <p>
     * 6）卖家自己的车辆：显示5.99
     */
    private String setHomePageResult(BigDecimal price, String locate, Long csCarDealerId, Boolean finalAnonymous, Boolean finalVipFlag, String finalCityName, BigDecimal finalBalanceMoney, Long carDealerId) {
        if ((finalVipFlag && locate.equals(finalCityName)) || (!finalVipFlag && locate.equals(finalCityName) && price.compareTo(finalBalanceMoney) <= 0) || csCarDealerId.equals(carDealerId)) {
            return getMoneyStr(price);
        }
        return dealPrice(price);
    }


    private void setLocate(HomePageBaseVO param) {
        //无位置默认全国
        String defaultLocate = null;
        //app采用这个字段
        String locateStr = param.getLocateStr();
        if (StringUtils.isNotEmpty(param.getLocate())) {
            Result<SysProvinceAndCityDTO> result = systemServiceClient.getAreaByCityCode(param.getLocate());
            if (result.isSuccess() && Objects.nonNull(result.getData())) {
                SysProvinceAndCityDTO data = result.getData();
                if (Objects.nonNull(data.getProvinceName()) && Objects.nonNull(data.getCityName())) {
                    defaultLocate = String.format("%s-%s", data.getProvinceName(), data.getCityName());
                }
            }
        }
        param.setLocate(defaultLocate);
    }

    private static String dealPrice(BigDecimal price) {
        String[] split = getMoneyStr(price).split("\\.");
        String first = split[0];
        return first + ".XX";
    }

    private static String getMoneyStr(BigDecimal price) {
        return price.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }


    private List<String> getList(String param) {
        return StrUtil.splitTrim(param, StrUtil.COMMA);

    }

    /***
     * 是否同城
     * @param buyArea
     * @param carArea
     * @return
     */
    private Boolean sameCity(String buyArea, String carArea) {
        if (buyArea.equals(carArea)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * @param frozenAmount
     * @param balanceMoney
     * @return
     */
    private Boolean compareMoney(BigDecimal balanceMoney, BigDecimal frozenAmount) {
        if (balanceMoney.compareTo(frozenAmount) >= 0) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 是否vip
     */
    private Boolean isVip(Integer vipFlag) {
        if (Constants.NO.equals(vipFlag)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    /**
     * 是否绑定银行卡
     *
     * @param userId
     * @return
     */
    private Boolean isBindingBankCard(Long userId) {
        return userBankCardService.binding(userId);
    }
}
