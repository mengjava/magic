package com.haoqi.magic.business.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpStatus;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.CarFileQueryEnum;
import com.haoqi.magic.business.enums.TradeTypeEnum;
import com.haoqi.magic.business.listener.event.CarCustomEvent;
import com.haoqi.magic.business.listener.event.CarScanEvent;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.entity.CsCarDealer;
import com.haoqi.magic.business.model.entity.CsCarInfo;
import com.haoqi.magic.business.model.vo.*;
import com.haoqi.magic.business.service.*;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.magic.common.enums.UserLevelEnum;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.core.error.ErrorCodeEnum;
import com.haoqi.rigger.fastdfs.service.IFastDfsFileService;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by yanhao on 2019/11/28.10:47
 */
@RestController
@Api(tags = "APP首页")
@RequestMapping("/car/app")
@Slf4j
public class CarAppHomePageController extends BaseController {


    @Autowired
    private ICarAppHomePageService carAppHomePageService;

    @Autowired
    private ICarHomePageService homePageService;

    @Autowired
    private ICsFilterService csFilterService;

    @Autowired
    private ICsCarDealerService csCarDealerService;

    @Autowired
    private ICsCustomBuiltService csCustomBuiltService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ICsCarCheckService carCheckService;

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
    private ICsCarInfoService carInfoService;

    @Autowired
    private IFastDfsFileService fastDfsFileService;

    @Autowired
    private ICsAccountDetailService accountDetailService;

    @Autowired
    private RedisTemplate redisTemplate;

    /***
     * APP首页banner图 190010 首页
     */
    @GetMapping("/banner/{code}")
    @ApiOperation(value = "首页banner图 code:190010 首页")
    public Result<List<SysAdvertDTO>> getAdvertByPositionCode(@PathVariable("code") String code) {
        return Result.buildSuccessResult(homePageService.selectAdvertByPositionCode(code));
    }

    /**
     * 热门城市 (后期可以做redis热点查询排序)
     */
    @GetMapping("/hotCity")
    @ApiOperation(value = "热门城市")
    public Result<List<CsAppHotCityDTO>> getHotCity() {
        return Result.buildSuccessResult(carAppHomePageService.getHotCity());
    }

    /**
     * 获取所有城市 首字母排序
     *
     * @return
     */
    @GetMapping("/getAllCity")
    @ApiOperation(value = "获取所有的城市")
    public Result getAllCity() {
        return Result.buildSuccessResult(carAppHomePageService.getAllCity());
    }

    /**
     * 品牌 价格
     */
    @GetMapping("/selectTags")
    @ApiOperation(value = "首页筛选 品牌和价格")
    public Result<Map<String, List<CsFilterDTO>>> getSelectTags() {
        Map<String, List<CsFilterDTO>> mapCsFilter = csFilterService.getMapCsFilter();
        return Result.buildSuccessResult(mapCsFilter);
    }

    /***
     * 获取全部筛选
     */
    @GetMapping(value = "/selectTree")
    @ApiOperation(value = "获取首页全部筛选树")
    public Result<Object> getSelectTree() {
        return Result.buildSuccessResult(homePageService.getSelectTree(), "");
    }


    //-------------------------------------------定制管理start-----------------------------------------------------------

    /**
     * APP新增定制
     *
     * @param vo
     * @return
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
     * APP分页获取定制列表信息
     *
     * @param param
     * @return
     */
    @PostMapping("/csCustomBuiltPage")
    @ApiOperation(value = "分页获取定制列表信息")
    public Result<Page> csCustomBuiltPage(@RequestBody CsCustomBuiltVO param) {
        currentUser();
        Map<String, Object> params = new HashMap<>();
        BeanUtil.beanToMap(param, params, false, true);
        params.put("csCarDealerId", csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        params.put("dealerName", StrUtil.emptyToNull(param.getDealerName()));
        Query query = new Query(params);
        Page page = csCustomBuiltService.findPage(query);
        return Result.buildSuccessResult(page);

    }

    /**
     * APP通过ID更新定制信息
     *
     * @param vo
     * @return
     */
    @PostMapping("/editCustomBuilt")
    @ApiOperation(value = "通过ID更新定制信息")
    public Result<String> editCustomBuilt(@RequestBody CsCustomBuiltVO vo) {
        vo.setCsCarDealerId(csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        csCustomBuiltService.updateCsCustomBuiltById(vo);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * APP通过ID获取定制信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getCustomBuilt/{id}")
    @ApiOperation(value = "通过ID获取定制信息")
    public Result<CsCustomBuiltDTO> getCustomBuiltById(@PathVariable("id") Long id) {
        CsCustomBuiltDTO dto = csCustomBuiltService.getOneById(id, csCarDealerService.getCurrentCarDealerInfo(currentUser().getUserName()).getId());
        return Result.buildSuccessResult(dto);

    }


    /**
     * APP通过ids删除定制信息
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


    /**
     * APP定制匹配即为(条件搜索 search)
     */


    //-------------------------------------------定制管理end-----------------------------------------------------------


    /*********************************************以下为买车页面*******************************************************/


    /***
     * APP 搜索
     * @param param
     * @return
     */
    @PostMapping("/search")
    @ApiOperation(value = "APP搜索 - 需要根据会员非会员显示不同的数据")
    public Result<Page<List<HomePageDTO>>> indexSearch(@RequestBody AppHomePageVO param) {
        UserDTO currentUserInfo = getCurrentUserInfo();
        Page data = null;
        try {
            data = carAppHomePageService.selectCarSearchParam(param, currentUserInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return Result.buildSuccessResult(data);
    }


    /********************************************车辆详情************************************************************/

    /**
     * 概况 基本信息 手续信息 配置 风险与责任
     * <p>
     * 车辆详情
     * 2.匿名用户模块：概况、基本信息、手续信息、配置、风险与责任
     * 3.登录用户（非自己商家）：、、、、、商家信息、检测报告、历史记录。
     * 4.登录用户（自己商家）：、、、、、          检测报告、历史记录。概况中无买入按钮。
     *
     * @param id
     * @return
     */
    @GetMapping("/carInfo/{id}")
    @ApiOperation(value = "APP通过车辆id，获取车辆基本信息")
    public Result<CarInfoDTO> getCarById(@PathVariable("id") Long id) {
        UserDTO currentUserInfo = getCurrentUserInfo();
        CarInfoDTO csCarInfo = carAppHomePageService.getCarInfoById(id, currentUserInfo);
        //监听:浏览量+1
        context.publishEvent(new CarScanEvent(this, id));
        return Result.buildSuccessResult(csCarInfo);
    }

    /***
     * 手续信息
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
     * 配置信息
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
     * 获取该车的车商信息 (登录)
     */
    @GetMapping("/getCarDealer/{id}")
    @ApiOperation(value = "通过车辆id， 获取该车的车商信息 (登录)")
    public Result<CsCarDealerBaseDTO> getCarDealerById(@PathVariable("id") Long id) {
        UserDTO currentUserInfo = getCurrentUserInfo();
        if (Objects.isNull(currentUserInfo)) {
            return Result.buildSuccessResult("无数据");
        }
        CsCarInfo car = carInfoService.getOneById(id);
        if (Objects.isNull(car)) {
            return Result.buildSuccessResult("无数据");
        }
        Optional<CsCarDealer> carDealer = dealerService.getOneById(car.getCsCarDealerId());
        //车id 当前登录人 车商id
        if (carDealer.isPresent() && !currentUserInfo.getLoginName().equals(carDealer.get().getTel())) {
            Optional<CsCarDealer> dealer = csCarDealerService.getOneByLoginName(currentUserInfo.getLoginName());
            if (dealer.isPresent()) {
                //意向车源
                context.publishEvent(new CarCustomEvent(this, id, dealer.get().getId()));
            }
        }
        return Result.buildSuccessResult(BeanUtils.beanCopy(carDealer.get(), CsCarDealerBaseDTO.class));
    }


    /***
     * 历史调拨 (登录)
     */
    @GetMapping("/getTsRecord/{id}")
    @ApiOperation(value = "通过车辆id，获取历史调拨 (登录)")
    public Result<List<CsTSRecordDTO>> getCsTransferRecordByCarId(@PathVariable("id") Long id) {
        UserDTO currentUserInfo = getCurrentUserInfo();
        if (Objects.isNull(currentUserInfo)) {
            return Result.buildSuccessResult("无数据");
        }
        List<CsTSRecordDTO> records = transferRecordService.getCsTransferRecordByCarId(id);
        return Result.buildSuccessResult(records);
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

        UserDTO currentUserInfo = getCurrentUserInfo();
        if (Objects.nonNull(currentUserInfo)) {
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
        UserDTO currentUserInfo = getCurrentUserInfo();
        if (Objects.isNull(currentUserInfo)) {
            return Result.buildSuccessResult("无数据");
        }
        List<Map<String, Object>> byCarIdCheckAll = carCheckService.findByCarIdCheckAll(id);
        if (CollectionUtil.isNotEmpty(byCarIdCheckAll)) {
            CsCarInfo csCarInfo = carInfoService.selectById(id);
            if (Objects.isNull(csCarInfo)) {
                HashMap<String, Object> map = Maps.newHashMap();
                map.put("carModelFileUrl", URLUtil.complateUrl(fastDfsFileService.getFastWebUrl(), csCarInfo.getCarModelFilePath()));
                byCarIdCheckAll.add(map);
            }
        }
        return Result.buildSuccessResult(byCarIdCheckAll);
    }


    /**
     * 非余额支付确认回调（支付平台用）
     *
     * @param paymentOrder
     * @return
     */
    @PostMapping("/payment/callBack")
    public Result<String> paymentCallBack(@RequestBody PaymentCallBackDTO paymentOrder) {
        validatorHandler.validator(paymentOrder);
        accountDetailService.paymentCallBack(paymentOrder);
        return Result.buildSuccessResult("操作成功");
    }

    /**************************************************详情结束*********************************************************/


    /**
     * 卖车列表 (自己的车源信息-上架车辆)
     */
    @PostMapping("/carPage")
    @ApiOperation(value = "卖车列表(自己的车源信息)")
    public Result<Page> carPage(@RequestBody BaseQueryPageVO param) {
        UserDTO currentUserInfo = getCurrentUserInfo();
        if (Objects.isNull(currentUserInfo)) {
            return Result.buildErrorResult(HttpStatus.HTTP_UNAUTHORIZED, "请登录!!!");
        }
        if (!UserLevelEnum.SELLER_LEVEL.getLevel().equals(currentUserInfo.getType())) {
            return Result.buildErrorResult("你暂不是卖家，确认是要成为卖家?");
        }
        Map<String, Object> params = Maps.newHashMap();
        BeanUtil.beanToMap(param, params, false, true);
        params.put("carDealerId", csCarDealerService.getCurrentCarDealerInfo(currentUserInfo.getLoginName()).getId());
        params.put("orderByField", "publish_time");
        params.put("isAsc", false);
        return Result.buildSuccessResult(carAppHomePageService.findByPage(new Query(params)));
    }


    /**
     * 下架 (商户下架自己的车)
     */
    @PostMapping("/pullOffCar")
    @ApiOperation(value = "APP下架 (商户下架自己的车)")
    public Result<CarInfoDTO> pullOffCar(@RequestBody PullOffCarVO vo) {
        validatorHandler.validator(vo);
        UserDTO currentUserInfo = getCurrentUserInfo();
        Boolean flag = carAppHomePageService.pullOffCar(vo, currentUserInfo);
        return Result.buildSuccessResult(flag ? "下架成功" : "下架失败");
    }


    /***
     * 我的页面 (数量计算) 买家只有买家中心 商家有买家和卖家中心
     *
     买入 代付款 过户 完成 争议 已取消(买家中心)

     待卖出 待过户 待收款 完成 争议 已取消(卖家中心)
     */
    @GetMapping("/getOrderCount")
    @ApiOperation(value = "我的页面 (买家&卖家中心)")
    public Result<Map> getOrderCount() {
        UserDTO currentUserInfo = getCurrentUserInfo();
        Map<String, Object> csCarInfo = carAppHomePageService.getOrderCount(currentUserInfo);
        return Result.buildSuccessResult(csCarInfo);
    }


    /**
     * 功能描述: 商家注册
     *
     * @param csCarDealerVO
     */
    @PostMapping("/dealer/register")
    @ApiOperation(value = "商家注册")
    public Result<String> registerNonCode(@RequestBody CsCarDealerVO csCarDealerVO) {
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
     * 交易类型下拉列表
     *
     * @return
     */
    @GetMapping("findTradeType")
    @ApiOperation(value = "交易类型下拉列表")
    public Result<Map<Integer, String>> findTradeType() {
        Map<Integer, String> tradeTypeMap = Maps.newHashMap();
        TradeTypeEnum[] tradeTypeEnums = TradeTypeEnum.values();
        for (TradeTypeEnum tradeTypeEnum : tradeTypeEnums) {
            tradeTypeMap.put(tradeTypeEnum.getKey(), tradeTypeEnum.getName());
        }
        return Result.buildSuccessResult(tradeTypeMap);
    }

    /**
     * 检验买入规则
     *
     * @param id
     * @return
     */
    @GetMapping("/checkBuyRule/{id}")
    @ApiOperation(value = "检测买入规则 id为车辆id")
    public Result<Object> checkCarBuyRule(@PathVariable("id") Long id) {
        UserDTO currentUserInfo = getCurrentUserInfo();
        if (Objects.isNull(currentUserInfo)) {
            return Result.buildErrorResult(ErrorCodeEnum.UNAUTHORIZED.getCode(), "请登录!");
        }
        Boolean rule = carAppHomePageService.checkCarBuyRule(id, currentUserInfo);
        return Result.buildSuccessResult(rule);
    }

    /**
     * 检验卖出规则
     *
     * @param id
     * @return
     */
    @GetMapping("/checkSaleRule/{id}")
    @ApiOperation(value = "检测卖出规则 id为车辆id")
    public Result<Object> checkCarSaleRule(@PathVariable("id") Long id) {
        UserDTO currentUserInfo = getCurrentUserInfo();
        if (Objects.isNull(currentUserInfo)) {
            return Result.buildErrorResult(ErrorCodeEnum.UNAUTHORIZED.getCode(), "请登录!");
        }
        if (!UserLevelEnum.SELLER_LEVEL.getLevel().equals(currentUserInfo.getType())) {
            return Result.buildErrorResult(ErrorCodeEnum.SYSTEM_EXCEPTION.getCode(), "您非商家,请申请成为商家!");
        }
        Boolean rule = carAppHomePageService.checkCarSaleRule(id, currentUserInfo);
        return Result.buildSuccessResult(rule);
    }


    /*************************************************************************************************************
     工具查询 (校验)
     2.点击“开始查询”时，如果账户余额不足，弹出提示“您账户余额不足XXX元，请先充值！”,XXX为查询费用金额点击确认跳转到充值界面。
     如果账户余额充足时，没有设置支付密码，优先跳转到“支付密码(设置)”。
     如果已经设置了支付密码，直接跳出支付界面。

     **************************************************************************************************************/




    private UserDTO getCurrentUserInfo() {
        UserInfo userInfo = null;
        try {
            userInfo = currentUser();
        } catch (Exception e) {
            log.error("currentUser: {}", e.getMessage());
            return null;
        }
        Result<UserDTO> result = systemServiceClient.getUserAreaAndAccount(userInfo.getId());
        if (!result.isSuccess() || Objects.isNull(result.getData())) {
            return null;
        }
        return result.getData();
    }

}
