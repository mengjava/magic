package com.haoqi.magic.business.client;

import com.haoqi.magic.business.client.fallback.SystemServiceClientFallBack;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.vo.UserVO;
import com.haoqi.magic.common.vo.CsAccountVO;
import com.haoqi.rigger.core.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author twg
 * @since 2019/1/8
 */
@FeignClient(name = "magic-system", fallback = SystemServiceClientFallBack.class)
public interface SystemServiceClient {

    /**
     * 通过用户id，获取关联城市信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/userArea/getUserAreaByUserId")
    Result<UserAreaDTO> getUserAreaByUserId(@RequestParam("userId") Long userId);

    /***
     * 获取全部的数据字典树
     * @return
     */
    @GetMapping(value = "/dict/tree")
    @ApiOperation(value = "获取数据字典树")
    Result<List<Object>> getTree();

    /**
     * 通过code码获取字典值
     *
     * @param keyword
     * @return
     */
    @GetMapping("/dict/getDictValueDesc/{keyword}")
    Result<String> getDictValueDesc(@PathVariable("keyword") String keyword);

    /**
     * 通过classType码获取该类的全部字典值
     *
     * @param classType
     * @return
     * @author huming
     * @date 2019/5/7 14:06
     */
    @GetMapping("/dict/getDictByClass/{classType}")
    Result<List<SysDictDTO>> getDictByClass(@PathVariable("classType") String classType);

    /***
     * 通过key获取
     * @param keyword
     * @return
     */
    @GetMapping("/dict/getDict/{keyword}")
    Result<SysDictDTO> getDict(@PathVariable("keyword") String keyword);

    /**
     * 功能描述: 添加用户
     *
     * @param
     * @return java.lang.Boolean
     * @auther mengyao
     * @date 2019/5/8 0008 下午 4:54
     */
    @PostMapping("/user/add")
    Result<Long> addUser(@RequestBody UserVO user);

    /**
     * 功能描述:发送商家账号密码
     *
     * @param tel
     * @param password
     * @return com.haoqi.rigger.core.Result<java.lang.Boolean>
     * @auther mengyao
     * @date 2019/5/9 0009 上午 11:20
     */
    @PostMapping("/base/sendCarDealerPassword")
    Result<String> sendCarDealerPassword(@RequestParam("tel") String tel, @RequestParam("password") String password);

    /**
     * 功能描述:拒绝后发送短信
     *
     * @param mobile
     * @param auditDetail
     * @return com.haoqi.rigger.core.Result<java.lang.Boolean>
     * @auther mengyao
     * @date 2019/5/9 0009 上午 11:20
     */
    @PostMapping("/base/sendRefuseMessage")
    Result<String> sendRefuseMessage(@RequestParam("mobile") String mobile, @RequestParam("auditDetail") String auditDetail);

    /**
     * 功能描述:
     * 通过广告【来自数据字典】投放位置 获取广告信息
     *
     * @auther: yanhao
     * @param:
     * @date: 2019/5/13 10:47
     * @Description:
     */
    @GetMapping("/sysAdvertConfig/getAdvertByPositionCode/{code}")
    Result<List<SysAdvertDTO>> getAdvertByPositionCode(@PathVariable("code") String code);

    /***
     * 获取车300一级车品牌列表
     * @return
     */
    @GetMapping(value = "/carBrand/getCarBrandList")
    Result<List<SysCarBrandDTO>> getCarBrandList();

    /**
     * 车品牌排序分组(品牌)首字母
     *
     * @return
     */
    @GetMapping(value = "/carBrand/getBrandLetterList")
    Result<Object> getBrandList();

    /***
     * 获取首页全部筛选数据字典树
     * @return
     */
    @GetMapping(value = "/dict/selectTree")
    Result<Object> getSelectTree();


    /**
     * 二级车型列表
     * 通过车品牌获取车系信息
     *
     * @param brandId
     * @return
     */
    @GetMapping(value = "/carBrand/getCarSeriesList/{brandId}")
    Result<List<Object>> getCarSeriesList(@PathVariable("brandId") Integer brandId);


    /**
     * 通过车系id获取车型信息
     * 三级
     *
     * @param
     * @return
     */
    @GetMapping(value = "/carBrand/getCarModelList/{seriesId}")
    Result<List<Object>> getCarModelList(@PathVariable("seriesId") Integer seriesId);

    /**
     * 功能描述:用户启用禁用
     *
     * @param loginName
     * @return com.haoqi.rigger.core.Result<java.lang.String>
     * @auther mengyao
     * @date 2019/5/22 0022 下午 4:39
     */
    @PutMapping("/user/updateEnabledByLoginName/{loginName}")
    Result<String> updateEnabledByLoginName(@PathVariable("loginName") String loginName);

    /***
     * 通过城市code获取城市信息
     * @param code
     * @return
     */
    @GetMapping("/base/sysArea/getByCode/{code}")
    Result<SysProvinceAndCityDTO> getAreaByCityCode(@PathVariable("code") String code);


    /***
     * 通过id获取城市信息
     * @param id
     * @return
     * @author huming
     * @date 2019/5/30 16:15
     */
    @GetMapping("/base/sysArea/getById/{id}")
    Result<SysProvinceAndCityDTO> getAreaByCityId(@PathVariable("id") Long id);


    /**
     * 功能描述:通过id获取区域信息
     *
     * @param id
     * @return com.haoqi.rigger.core.Result<com.haoqi.magic.business.model.dto.SysProvinceAndCityDTO>
     * @auther mengyao
     * @date 2019/6/3 0003 上午 11:10
     */
    @GetMapping("/base/sysArea/{id}")
    Result<SysProvinceAndCityDTO> getAreaById(@PathVariable("id") Long id);

    /**
     * 功能描述:根据用户id获取用户信息
     *
     * @param id
     * @return com.haoqi.rigger.core.Result<UserDTO>
     * @auther mengyao
     * @date 2019/7/10 0010 下午 2:00
     */
    @GetMapping("/user/{id}")
    Result<UserDTO> getUserByUserId(@PathVariable("id") Long id);

    /**
     * 通过用户名获取用户信息
     *
     * @param loginName 用户名称
     * @return
     * @author huming
     * @date 2019/1/15 12:00
     */
    @GetMapping("/user/getUserByName/{loginName}")
    Result<UserDTO> getUserByName(@PathVariable("loginName") String loginName);

    /**
     * 更新用户类型
     *
     * @param id
     * @return
     */
    @PutMapping("/user/updateUserType/{id}")
    Result<String> updateUserType(@PathVariable("id") Long id);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/user/userRegister")
    Result<Long> userRegister(@RequestBody UserVO user);

    /**
     * APP获取热门城市
     *
     * @return
     */
    @GetMapping("/base/sysArea/hotCity")
    Result<List<CsAppHotCityDTO>> getHotCity();

    /**
     * 获取所有的城市
     *
     * @return
     */
    @GetMapping("/base/sysArea/getAllCity")
    Result<List<CsAppHotCityDTO>> getAllCity();

    /**
     * 支付时要获取可用金额
     *
     * @param money
     * @return
     */
    @GetMapping("/csAccount/enableBalance/{money}")
    Result<BigDecimal> enableBalance(@PathVariable("money") String money);

    /**
     * 对账户余额处理
     *
     * @param balance 余额
     * @param money   需要充值的金额
     * @return
     */
    @PostMapping("/csAccount/addBalance/{balance}/{money}")
    Result<String> addBalance(@PathVariable("balance") String balance, @PathVariable("money") String money);

    /**
     * 对买方账户余额加处理
     *
     * @param buyerId
     * @param buyerFrozenMoney
     * @param dealerId
     * @param dealerFrozenMoney
     * @return
     */
    @PostMapping("/csAccount/addBalanceToBuyer")
    Result<String> addBalanceToBuyer(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney,
                                     @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney);

    /**
     * 对卖方账户余额加处理
     *
     * @param buyerId
     * @param buyerFrozenMoney
     * @param dealerId
     * @param dealerFrozenMoney
     * @return
     */
    @PostMapping("/csAccount/addBalanceToDealer")
    Result<String> addBalanceToDealer(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney,
                                      @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney);


    /**
     * 对账户余额处理
     *
     * @param balance 余额
     * @param money   需要扣款的金额
     * @return
     */
    @PostMapping("/csAccount/subBalance/{balance}/{money}")
    Result<String> subBalance(@PathVariable("balance") String balance, @PathVariable("money") String money);

    /**
     * 对账户解冻保证金金额处理
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @PostMapping("/csAccount/unFreezeByUserId/{userId}/{money}")
    Result<String> unFreezeByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money);

    /**
     * 通过用户ID获取用户信息和地区和账户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/user/getUserAreaAndAccount/{id}")
    Result<UserDTO> getUserAreaAndAccount(@PathVariable("id") Long id);

    /**
     * 功能描述: 根据name和type获取配置
     *
     * @param type
     * @param name
     * @return com.haoqi.rigger.core.Result<com.haoqi.magic.business.model.dto.SysConfigDTO>
     * @auther mengyao
     * @date 2019/12/5 0005 上午 10:46
     */
    @GetMapping("/sysConfig/getByTypeAndName/{type}/{name}")
    Result<SysConfigDTO> getByTypeAndName(@PathVariable("type") Integer type, @PathVariable("name") Integer name);

    /**
     * 支付时要获取可冻结金额
     *
     * @param type
     * @return
     */
    @GetMapping("/csAccount/enableFrozenAmount/{type}")
    Result<BigDecimal> enableFrozenAmount(@PathVariable("type") Integer type);

    /**
     * 对账户余额加处理
     *
     * @param userId
     * @param money
     * @return
     */
    @PostMapping("/csAccount/addBalanceByUserId/{userId}/{money}")
    Result<String> addBalanceByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money);

    /**
     * 对账户余额减处理
     *
     * @param userId 用户id
     * @param money  金额
     * @return
     */
    @PostMapping("/csAccount/subBalanceByUserId/{userId}/{money}")
    Result<String> subBalanceByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money);

    /**
     * 对账户余额减、加冻结金额处理
     *
     * @param userId
     * @param money
     * @return
     */
    @PostMapping("/csAccount/subBalanceAddFrozenAmount/{userId}/{money}")
    Result<String> subBalanceAddFrozenAmount(@PathVariable("userId") Long userId, @PathVariable("money") String money);


    /**
     * 通过账户id，获取账户、用户信息
     *
     * @param id
     * @return
     */
    @GetMapping("/csAccount/getAccountWithUser/{id}")
    Result<CsAccountVO> getAccountWithUser(@PathVariable("id") Long id);

    /**
     * 通过用户id，获取账户、用户信息
     *
     * @return
     */
    @GetMapping("/csAccount/getAccountByUserId")
    Result<CsAccountVO> getAccountByUserId();

    /**
     * 对账户解冻保证金金额处理
     *
     * @param buyerId
     * @param buyerFrozenMoney
     * @param dealerId
     * @param dealerFrozenMoney
     * @return
     */
    @PostMapping("/csAccount/unFreezeByUserIdAndDealerId")
    Result<String> unFreezeByUserIdAndDealerId(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney,
                                               @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney);


    /**
     * 更新用户vip标示
     *
     * @param id
     * @param vipFlag
     * @return
     */
    @PutMapping("/user/updateUserVipFlag/{id}/{vipFlag}")
    Result<String> updateUserVipFlag(@PathVariable("id") Long id, @PathVariable("vipFlag") Integer vipFlag);

}
