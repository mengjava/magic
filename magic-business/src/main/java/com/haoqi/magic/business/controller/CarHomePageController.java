package com.haoqi.magic.business.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.*;
import com.haoqi.magic.business.listener.event.CarCustomEvent;
import com.haoqi.magic.business.listener.event.CarScanEvent;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.haoqi.magic.business.model.vo.*;
import com.haoqi.magic.business.service.*;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.core.error.RiggerException;
import com.haoqi.rigger.core.handler.BeanValidatorHandler;
import com.haoqi.rigger.fastdfs.service.impl.FastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能描述:
 * 首页搜索(首页-买车-每日促销-一口价) (抽取公共方法)
 * //后期采用es
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/5 10:19
 * @Description:
 */
@RestController
@Api(tags = "首页")
@RequestMapping("/car/home")
@Slf4j
public class CarHomePageController extends BaseController {


    @Autowired
    private ICarHomePageService homePageService;

    @Autowired
    private ICsCarInfoService carInfoService;

    @Autowired
    private ICsCarProcedureService carProcedureService;

    @Autowired
    private ICsCarConfigService configService;

    @Autowired
    private ICsTransferRecordService transferRecordService;

    @Autowired
    private ICsCarDealerService dealerService;

    @Autowired
    private ICsCarFileService carFileService;

    @Autowired
    private BeanValidatorHandler validatorHandler;

    @Autowired
    private ICsCarCheckService carCheckService;

    @Autowired
    private ICsFilterService csFilterService;

    @Autowired
    private ICsCarDealerService csCarDealerService;

    @Autowired
    private FastDfsFileService fastDfsFileService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Autowired
    private ICsLoanCreditService csLoanCreditService;

    @Autowired
    private ICsCustomBuiltService csCustomBuiltService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * redis 前缀
     */
    @Value("${spring.redis.prefix}")
    private String prefix;

    /**
     * 功能描述:
     * 车源首页（诚信联盟）查询
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/5/9 14:38
     * @Description:
     */
    @PostMapping("/index")
    @ApiOperation(value = "车源首页（诚信联盟）查询")
    public Result<List<HomePageDTO>> indexCreditUnion(@RequestBody HomePageBaseVO param) {
        param.setCreditUnion(Constants.YES);
        return Result.buildSuccessResult(homePageService.selectIndexByParam(param));
    }

    /**
     * 功能描述:
     * 今日推荐列表（通过tag获取sql）
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/5/8 18:34
     * @Description:
     */
    @PostMapping("/recommend")
    @ApiOperation(value = "今日推荐列表")
    public Result<List<HomePageDTO>> indexRecommend(@RequestBody HomePageBaseVO param) {
        List<HomePageDTO> list = homePageService.selectIndexByParam(param);
        return Result.buildSuccessResult(list);
    }

    /***
     * 搜索
     * @param param
     * @return
     */
    @PostMapping("/search")
    @ApiOperation(value = "搜索（买车 每日促销 一口价）")
    public Result<Page> indexSearch(@RequestBody HomePageVO param) {
        return Result.buildSuccessResult(homePageService.selectIndexSearchParam(param));
    }


    /***
     * 首页banner图
     */
    @GetMapping("/banner/{code}")
    @ApiOperation(value = "首页banner图")
    public Result<List<SysAdvertDTO>> getAdvertByPositionCode(@PathVariable("code") String code) {
        return Result.buildSuccessResult(homePageService.selectAdvertByPositionCode(code));
    }


    /***
     * 获取首页标签选项卡
     */
    @GetMapping("/filterTags")
    @ApiOperation(value = "首页筛选的标签 type=1")
    public Result<List<CsTagDTO>> getFilterTags() {
        return Result.buildSuccessResult(homePageService.selectFilterTags());
    }


    /***
     * 获取首页品牌和价格过滤项目
     */

    @GetMapping("/selectTags")
    @ApiOperation(value = "首页筛选 品牌和价格")
    public Result<Map<String, List<CsFilterDTO>>> getSelectTags() {
        Map<String, List<CsFilterDTO>> mapCsFilter = csFilterService.getMapCsFilter();
        return Result.buildSuccessResult(mapCsFilter);
    }


    /***
     * 今日推荐的tag标签
     */
    @GetMapping("/todayTags")
    @ApiOperation(value = "今日推荐的tag标签 type= 3")
    public Result<List<CsTagDTO>> getTodayTags() {
        return Result.buildSuccessResult(homePageService.selectTodayTags());
    }


    /***
     * 获取更多车辆品牌
     */
    @GetMapping("/moreBrands")
    @ApiOperation(value = "首页获取更多车辆品牌")
    public Result<Object> getMoreBrands() {
        return Result.buildSuccessResult(homePageService.selectMoreBrands());
    }


    /**
     * 功能描述:
     * 一级车型列表
     *
     * @auther: yanhao
     * @param:
     * @date: 2018/11/9 17:15
     * @Description:
     */
    @GetMapping(value = "/getCarBrandList")
    @ApiOperation(value = "获取车300一级车品牌列表(APP使用-不分页)")
    public Result<List<SysCarBrandDTO>> getCarBrandList() {
        return Result.buildSuccessResult(homePageService.selectCarBrandList());
    }

    @GetMapping(value = "/getBrandLetterList")
    @ApiOperation(value = "获取车300首字母品牌分组")
    public Result<Object> getBrandLetterList() {
        return Result.buildSuccessResult(homePageService.selectBrandLetterList());
    }

    /**
     * 二级车型列表
     * 通过车品牌获取车系信息
     *
     * @param brandId
     * @return
     */
    @GetMapping(value = "/getCarSeriesList/{brandId}")
    @ApiOperation(value = "获取车300二级通过车品牌获取车系")
    public Result<Object> getCarSeriesList(@PathVariable("brandId") Integer brandId) {
        return Result.buildSuccessResult(homePageService.selectCarSeriesListByBrandId(brandId), "");
    }

    /**
     * 通过车系id获取车型信息
     * 三级
     *
     * @param
     * @return
     */
    @GetMapping(value = "/getCarModelList/{seriesId}")
    @ApiOperation(value = "获取车300三级通过车品牌获取车型信息")
    public Result<Object> getCarModelList(@PathVariable("seriesId") Integer seriesId) {
        return Result.buildSuccessResult(homePageService.selectCarModelListBySeriesId(seriesId), "");
    }


    /***
     * 获取全部筛选
     */
    @GetMapping(value = "/selectTree")
    @ApiOperation(value = "获取首页全部筛选树")
    public Result<Object> getSelectTree() {
        return Result.buildSuccessResult(homePageService.getSelectTree(), "");
    }


    /***
     * 数据字典
     */

    @GetMapping("/getDictByClass/{classType}")
    @ApiOperation(value = "获取首页全部筛选树（字典）")
    public Result<List<SysDictDTO>> getDictByClass(@PathVariable("classType") String classType) {
        List<SysDictDTO> list = homePageService.getDictByClass(classType);
        return Result.buildSuccessResult(list);
    }

    /**
     ===================================================================
     以下为车辆详情
     ===================================================================
     */
    //车辆信息（区分登陆和非登陆）

    //基本信息0
    //手续信息0
    //配置信息0
    //过户风险与责任（1）
    //商家信息（1）//隐藏
    //历史调拨（1）
    //检测信息（1）（事故检测 - 外观检查 - 动力检查 - 车内功能检查 - 车内环境检查 - 泡水检查 - 过火检查 ： <数据字典>）
    //车辆图片（基本照片 0 ，事故照片 1，外观照片 1，缺陷照片 1： <数据字典>）

    /**
     * 通过车辆ID，获取车辆基本信息
     *
     * @param id
     * @return
     */
    @GetMapping("/carInfo/{id}")
    @ApiOperation(value = "通过车辆id，获取车辆基本信息")
    public Result<CarInfoDTO> getCarById(@PathVariable("id") Long id) {
        Boolean hasLogin = hasLogin();
        CarInfoDTO csCarInfo = carInfoService.getCarInfoById(id);
        if (!hasLogin) {
            csCarInfo.setWholesalePrice(null);
        }
        //监听:浏览量+1
        context.publishEvent(new CarScanEvent(this, id));
        return Result.buildSuccessResult(csCarInfo);
    }

    /***
     * 获取车辆手续信息
     * @param id
     * @return
     */
    @GetMapping("/getProcedure/{id}")
    @ApiOperation(value = "通过车辆id，获取车辆手续信息")
    public Result<CarProcedureDTO> getProcedureByCarId(@PathVariable("id") Long id) {
        Optional<CarProcedureDTO> carProcedureDTO = carProcedureService.getProcedureByCarId(id);
        return Result.buildSuccessResult(carProcedureDTO.get());
    }

    /**
     * 通过车辆id，获取车辆配置信息 (非登录)
     *
     * @param id
     * @return
     */
    @GetMapping("/getConfig/{id}")
    @ApiOperation(value = "通过车辆id，获取车辆配置信息")
    public Result<CarConfigDTO> getConfigByCarId(@PathVariable("id") Long id) {
        Optional<CarConfigDTO> carConfigDTO = configService.getConfigByCarId(id);
        if (carConfigDTO.isPresent()) {
            return Result.buildSuccessResult(carConfigDTO.get());
        }
        return Result.buildSuccessResult("");
    }

    /***
     * 历史调拨 (登录)
     */
    @GetMapping("/getTsRecord/{id}")
    @ApiOperation(value = "通过车辆id，获取历史调拨 (登录)")
    public Result<List<CsTSRecordDTO>> getCsTransferRecordByCarId(@PathVariable("id") Long id) {
        if (!hasLogin()) {
            return Result.buildSuccessResult("无数据");
        }
        List<CsTSRecordDTO> records = transferRecordService.getCsTransferRecordByCarId(id);
        return Result.buildSuccessResult(records);
    }


    /***
     * 获取该车的车商信息 (登录)
     */
    @GetMapping("/getCarDealer/{id}")
    @ApiOperation(value = "通过车辆id， 获取该车的车商信息 (登录)")
    public Result<CsCarDealerBaseDTO> getCarDealerById(@PathVariable("id") Long id) {
        UserInfo userInfo = null;
        try {
            userInfo = currentUser();
        } catch (Exception e) {
            return Result.buildSuccessResult("无数据");
        }
        CsCarInfo car = carInfoService.getOneById(id);
        if (Objects.isNull(car)) {
            return Result.buildSuccessResult("无数据");
        }
        Optional<CsCarDealer> carDealer = dealerService.getOneById(car.getCsCarDealerId());
        //车id 当前登录人 车商id
        if (carDealer.isPresent() && !userInfo.getUserName().equals(carDealer.get().getTel())) {
            Optional<CsCarDealer> dealer = csCarDealerService.getOneByLoginName(userInfo.getUserName());
            if (dealer.isPresent()) {
                context.publishEvent(new CarCustomEvent(this, id, dealer.get().getId()));
            }
        }
        return Result.buildSuccessResult(BeanUtils.beanCopy(carDealer.get(), CsCarDealerBaseDTO.class));
    }


    /***
     * 获取车辆照片  车辆图片（基本照片 0 ，事故照片 1，外观照片 1，缺陷照片1）
     */
    @PostMapping("/getCarFile")
    @ApiOperation(value = "通过车辆id和类型， 获取该车的附件信息")
    public Result<Object> getCarFileById(@RequestBody CarFileQueryVO vo) {
        validatorHandler.validator(vo);
        //基本照片0
        if (CarFileQueryEnum.BASE_PIC_TYPE.getKey().equals(vo.getType())) {
            List<CarFileDTO> baseCarFile = carFileService.findByCarIdAndFileType(vo.getId(), Constants.YES);
            return Result.buildSuccessResult(baseCarFile);
        }
        if (hasLogin()) {
            //事故照片:1 外观照片:1 缺陷照片:1
            List<CarCheckDTO> checkDTOS = carCheckService.findWithCheckItemByCarIdType(vo.getId(), vo.getType());
            return Result.buildSuccessResult(checkDTOS);
        }
        return Result.buildSuccessResult("无信息");
    }


    /***
     * 检测信息（登录）（事故检测 - 外观检查 - 动力检查 - 车内功能检查 - 车内环境检查 - 泡水检查 - 过火检查
     */
    @GetMapping("/getCarCheck/{id}")
    @ApiOperation(value = "通过车辆id， 获取检测信息（登录）")
    public Result<Object> getCarCheck(@PathVariable("id") Long id) {
        //校验该车是否存在
        if (!hasLogin()) {
            return Result.buildSuccessResult("无信息");
        }
        return Result.buildSuccessResult(carCheckService.findByCarIdCheckAll(id));
    }


    /**
     * 功能描述: 商家注册
     *
     * @param csCarDealerVO
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/7 0007 上午 11:18
     */
    @PostMapping("/dealer/register")
    @ApiOperation(value = "商家注册")
    public Result<String> register(@RequestBody CsCarDealerVO csCarDealerVO) {
        checkTelCode(csCarDealerVO.getTel(), csCarDealerVO.getMessageCode());
        validatorHandler.validator(csCarDealerVO);
        CsCarDealer carDealer = BeanUtils.beanCopy(csCarDealerVO, CsCarDealer.class);
        carDealer.setCreator(0L);
        carDealer.setModifier(0L);
        carDealer.setGmtCreate(DateUtil.date());
        carDealer.setGmtModified(DateUtil.date());
	    dealerService.register(carDealer);
        return Result.buildSuccessResult("注册成功");
    }



    /**
     * 通过条件获取标签数据
     *
     * @return
     * @author huming
     * @date 2019/4/30 15:08
     */
    @PostMapping("/getCsFilterWithCondition")
    @ApiOperation(value = "通过条件获取标签数据")
    public Result<List<CsFilterDTO>> getCsFilterWithCondition(@RequestBody CsFilterVO vo) {
        return Result.buildSuccessResult(csFilterService.getCsFilterWithCondition(vo));
    }


    //------------------------------------------商家全部功能-------------------------------------------------------------
    //------------------------------------------商家信息start-------------------------------------------------------------

    /**
     * 获取商家信息
     *
     * @return
     * @author huming
     * @date 2019/5/20 13:52
     */
    @GetMapping("/getOneByLoginName")
    @ApiOperation(value = "获取商家详情")
    public Result<CsCarDealerAuditDTO> getOneByLoginName() {
        Optional<CsCarDealer> optional = csCarDealerService.getOneByLoginName(currentUser().getUserName());
        CsCarDealerAuditDTO csCarDealerAuditDTO = null;
        if (optional.isPresent()) {
            csCarDealerAuditDTO = BeanUtils.beanCopy(optional.get(), CsCarDealerAuditDTO.class);
            csCarDealerAuditDTO.setPictureURL(fastDfsFileService.getFastWebUrl());
            if (null != csCarDealerAuditDTO.getSysAreaId()) {
                Result<SysProvinceAndCityDTO> userArea = systemServiceClient.getAreaByCityId(csCarDealerAuditDTO.getSysAreaId());
                if (userArea.isSuccess()) {
                    csCarDealerAuditDTO.setSysAreaName(
                            userArea.getData().getProvinceName() +
                                    (StrUtil.isBlank(userArea.getData().getCityName()) ? "" : (StrUtil.DASHED + userArea.getData().getCityName())));
                }
            }
        }
        return Result.buildSuccessResult(csCarDealerAuditDTO);
    }
    //------------------------------------------商家信息end-------------------------------------------------------------


    //-------------------------------------------定制管理start-----------------------------------------------------------

    /**
     * 分页获取定制列表信息
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/5/5 14:07
     */
    @PostMapping("/csCustomBuiltPage")
    @ApiOperation(value = "分页获取定制列表信息")
    public Result<Page> csCustomBuiltPage(@RequestBody CsCustomBuiltVO param) {
        currentUser();
        Map<String, Object> params = new HashMap<>();
        params.put("applyTimeStart", StrUtil.isBlank(param.getApplyTimeStart()) ? StrUtil.emptyToNull("") :
                DateUtil.beginOfDay(DateUtil.parseDate(param.getApplyTimeStart())));
        params.put("applyTimeEnd", StrUtil.isBlank(param.getApplyTimeEnd()) ? StrUtil.emptyToNull("") :
                DateUtil.endOfDay(DateUtil.parseDate(param.getApplyTimeEnd())));
        params.put("csCarDealerId", csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        params.put("sysCarBrandId", param.getSysCarBrandId());
        params.put("dealerName", StrUtil.emptyToNull(param.getDealerName()));
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        Page page = csCustomBuiltService.findPage(query);
        return Result.buildSuccessResult(page);

    }


    /**
     * 新增定制
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/5 14:08
     */
    @PostMapping("/addCustomBuilt")
    @ApiOperation(value = "新增定制")
    public Result<String> addCustomBuilt(@RequestBody CsCustomBuiltVO vo) {
        validatorHandler.validator(vo);
        vo.setCsCarDealerId(csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        csCustomBuiltService.insert(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过ID更新定制信息
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/5 14:09
     */
    @PostMapping("/editCustomBuilt")
    @ApiOperation(value = "通过ID更新定制信息")
    public Result<String> editCustomBuilt(@RequestBody CsCustomBuiltVO vo) {
        vo.setCsCarDealerId(csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        csCustomBuiltService.updateCsCustomBuiltById(vo);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 通过ID获取定制信息
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/5/5 14:12
     */
    @GetMapping("/getCustomBuilt/{id}")
    @ApiOperation(value = "通过ID获取定制信息")
    public Result<CsCustomBuiltDTO> getCustomBuiltById(@PathVariable("id") Long id) {
        CsCustomBuiltDTO dto = csCustomBuiltService.getOneById(id,
                csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        return Result.buildSuccessResult(dto);

    }


    /**
     * 通过ids删除定制信息
     *
     * @param ids
     * @return
     * @author huming
     * @date 2019/5/20 14:09
     */
    @DeleteMapping("/deleteCustomBuilt/{ids}")
    @ApiOperation(value = "通过ids删除定制信息")
    public Result<String> deleteCustomBuiltByIds(@PathVariable("ids") String ids) {
        currentUser();
        List<Long> list = Arrays.stream(StrUtil.split(ids, StrUtil.COMMA)).map(o -> Long.parseLong(o)).collect(Collectors.toList());
        csCustomBuiltService.deleteByIds(list);
        return Result.buildSuccessResult("操作成功");
    }
    //-------------------------------------------定制管理end-----------------------------------------------------------


    //-------------------------------------------分期管理start-----------------------------------------------------------

    /**
     * 分页获取分期数据
     *
     * @param param
     * @return
     * @author huming
     * @date 2019/5/5 10:30
     */
    @PostMapping("/csLoanCreditPage")
    @ApiOperation(value = "分页获取分期数据")
    public Result<Page> csLoanCreditPage(@RequestBody CsLoanCreditVO param) {
        Map<String, Object> params = new HashMap<>();
        params.put("keyWord", StrUtil.emptyToNull(param.getKeyWord()));
        params.put("applyTimeStart", StrUtil.isBlank(param.getApplyTimeStart()) ? StrUtil.emptyToNull("") :
                DateUtil.beginOfDay(DateUtil.parseDate(param.getApplyTimeStart())));
        params.put("applyTimeEnd", StrUtil.isBlank(param.getApplyTimeEnd()) ? StrUtil.emptyToNull("") :
                DateUtil.endOfDay(DateUtil.parseDate(param.getApplyTimeEnd())));
        params.put("csCarDealerId", csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        params.put("dealerName", StrUtil.emptyToNull(param.getDealerName()));
        params.put("page", param.getPage());
        params.put("limit", param.getLimit());
        Query query = new Query(params);
        Page page = csLoanCreditService.findPage(query);
        return Result.buildSuccessResult(page);

    }


    /**
     * 新增分期数据
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/5 10:42
     */
    @PostMapping("/addLoanCredit")
    @ApiOperation(value = "新增分期数据")
    public Result<String> addLoanCredit(@RequestBody CsLoanCreditVO vo) {
        validatorHandler.validator(vo);
        vo.setCsCarDealerId(csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        csLoanCreditService.insert(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过ID更新筛选信息
     *
     * @param vo
     * @return
     * @author huming
     * @date 2019/5/5 10:42
     */
    @PostMapping("/editLoanCredit")
    @ApiOperation(value = "通过ID更新筛选信息")
    public Result<String> editLoanCredit(@RequestBody CsLoanCreditVO vo) {
        vo.setCsCarDealerId(csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        csLoanCreditService.updateCsLoanCreditById(vo);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 通过ID获取筛选信息
     *
     * @param id 主键ID
     * @return
     * @author huming
     * @date 2019/5/5 10:42
     */
    @GetMapping("/getLoanCredit/{id}")
    @ApiOperation(value = "通过ID获取筛选信息")
    public Result<CsLoanCreditDTO> getLoanCreditById(@PathVariable("id") Long id) {
        CsLoanCreditDTO dto = csLoanCreditService.getOneById(id,
                csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        return Result.buildSuccessResult(dto);

    }

    /**
     * 通过ids删除分期信息
     *
     * @param ids
     * @return
     * @author huming
     * @date 2019/5/20 14:09
     */
    @DeleteMapping("/deleteLoanCredit/{ids}")
    @ApiOperation(value = "通过ids删除分期信息")
    public Result<String> deleteLoanCreditByIds(@PathVariable("ids") String ids) {
        currentUser();
        List<Long> list = Arrays.stream(StrUtil.split(ids, StrUtil.COMMA)).map(Long::parseLong).collect(Collectors.toList());
        csLoanCreditService.deleteByIds(list);
        return Result.buildSuccessResult("操作成功");
    }
    //--------------------------------------------分期管理end-----------------------------------------------------------


    /**
     * 用户、商家注册
     *
     * @param userDealerRegister
     * @return
     */
    @PostMapping("/userDealerRegister")
    @ApiOperation(value = "用户、商家注册")
    public Result<String> userDealerRegister(@RequestBody UserDealerRegisterVO userDealerRegister) {
        checkTelCode(userDealerRegister.getTel(), userDealerRegister.getMessageCode());
        validatorHandler.validator(userDealerRegister);
        if (userDealerRegister.getToCarDealer()) {
            validatorHandler.validator(userDealerRegister.getDealer());
        }
        return Result.buildSuccessResult(csCarDealerService.userDealerRegister(userDealerRegister));
    }


    /**
     * 获取争议状态下拉列表
     *
     * @return
     */
    @GetMapping("findDisputeFlag")
    @ApiOperation(value = "获取争议状态下拉列表")
    public Result<Map<Integer, String>> findDisputeFlag() {
        DisputeFlagEnum[] enums = DisputeFlagEnum.values();
        Map<Integer, String> result = Maps.newHashMap();
        for (DisputeFlagEnum e : enums) {
            result.put(e.getKey(), e.getName());
        }
        return Result.buildSuccessResult(result);
    }

    /**
     * 获取争议处理方式下拉列表
     *
     * @return
     */
    @GetMapping("findDisputeProcessType")
    @ApiOperation(value = "获取争议处理方式下拉列表")
    public Result<Map<Integer, String>> findDisputeProcessType() {
        DisputeProcessTypeEnum[] enums = DisputeProcessTypeEnum.values();
        Map<Integer, String> result = Maps.newHashMap();
        for (DisputeProcessTypeEnum e : enums) {
            result.put(e.getKey(), e.getName());
        }
        return Result.buildSuccessResult(result);
    }


    /**
     * 获取审核状态下拉列表
     *
     * @return
     */
    @GetMapping("findOrderAuditStatus")
    @ApiOperation(value = "获取审核状态下拉列表")
    public Result<Map<Integer, String>> findOrderAuditStatus() {
        OrderAuditStatusEnum[] enums = OrderAuditStatusEnum.values();
        Map<Integer, String> result = Maps.newHashMap();
        for (OrderAuditStatusEnum e : enums) {
            result.put(e.getKey(), e.getName());
        }
        return Result.buildSuccessResult(result);
    }


    /**
     * 获取订单状态下拉列表
     *
     * @return
     */
    @GetMapping("findOrderStatus")
    @ApiOperation(value = "获取订单状态下拉列表")
    public Result<Map<Integer, String>> findOrderStatus() {
        OrderStatusEnum[] enums = OrderStatusEnum.values();
        Map<Integer, String> result = Maps.newHashMap();
        for (OrderStatusEnum e : enums) {
            result.put(e.getKey(), e.getName());
        }
        return Result.buildSuccessResult(result);
    }


    /**
     * 检测手机验证码
     *
     * @param tel
     * @param code
     */
    private void checkTelCode(String tel, String code) {
        String telCode = (String) redisTemplate.opsForValue().get(String.format("%s:sendCode:%s", prefix, tel));
        if (!code.equals(telCode)) {
            throw new RiggerException("验证码错误");
        }
    }

    /**
     * 校验是否登录
     *
     * @return
     */
    private Boolean hasLogin() {
        Boolean hasLogin = false;
        try {
            UserInfo userInfo = currentUser();
            //判断角色 经销商
            if (Objects.nonNull(userInfo)) {
                hasLogin = true;
            }
        } catch (Exception e) {
            log.error("未登录");
        }
        return hasLogin;
    }
}
