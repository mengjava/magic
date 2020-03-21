package com.haoqi.magic.business.client.fallback;

import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.vo.UserVO;
import com.haoqi.magic.common.vo.CsAccountVO;
import com.haoqi.rigger.core.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author twg
 * @since 2019/4/25
 */
@Component
public class SystemServiceClientFallBack implements SystemServiceClient {
    @Override
    public Result<UserAreaDTO> getUserAreaByUserId(@RequestParam Long userId) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<List<Object>> getTree() {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> getDictValueDesc(@PathVariable("keyword") String keyword) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<List<SysDictDTO>> getDictByClass(@PathVariable("classType") String classType) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<SysDictDTO> getDict(@PathVariable("keyword") String keyword) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<Long> addUser(UserVO user) {
        return Result.buildSuccessResult(0L);
    }

    @Override
    public Result<String> sendCarDealerPassword(@RequestParam("tel") String tel, @RequestParam("password") String password) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> sendRefuseMessage(@RequestParam("mobile") String mobile, @RequestParam("auditDetail") String auditDetail) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<List<SysAdvertDTO>> getAdvertByPositionCode(@PathVariable("code") String code) {
        return Result.buildSuccessResult("");
    }

    @Override
    public Result<List<SysCarBrandDTO>> getCarBrandList() {
        return Result.buildSuccessResult("");
    }

    @Override
    public Result<Object> getBrandList() {
        return Result.buildErrorResult("");
    }

    @Override
    public Result<Object> getSelectTree() {
        return Result.buildSuccessResult("");
    }

    @Override
    public Result<List<Object>> getCarSeriesList(@PathVariable("brandId") Integer brandId) {
        return Result.buildSuccessResult("");
    }

    @Override
    public Result<List<Object>> getCarModelList(@PathVariable("seriesId") Integer seriesId) {
        return Result.buildSuccessResult("");
    }

    @Override
    public Result<String> updateEnabledByLoginName(@PathVariable("loginName") String loginName) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<SysProvinceAndCityDTO> getAreaByCityCode(@PathVariable("code") String code) {
        return Result.buildSuccessResult("");
    }

    @Override
    public Result<SysProvinceAndCityDTO> getAreaByCityId(@PathVariable("id") Long id) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<SysProvinceAndCityDTO> getAreaById(Long id) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<UserDTO> getUserByUserId(@PathVariable("id") Long id) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<UserDTO> getUserByName(@PathVariable("loginName") String loginName) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<List<CsAppHotCityDTO>> getHotCity() {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> updateUserType(@PathVariable("id") Long id) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<Long> userRegister(@RequestBody UserVO user) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<List<CsAppHotCityDTO>> getAllCity() {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<BigDecimal> enableBalance(@PathVariable("money") String money) {
        return Result.buildSuccessResult(BigDecimal.ZERO);
    }

    @Override
    public Result<String> addBalance(@PathVariable("balance") String balance, @PathVariable("money") String money) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> addBalanceToBuyer(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney, @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> addBalanceToDealer(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney, @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> subBalance(@PathVariable("balance") String balance, @PathVariable("money") String money) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> unFreezeByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<UserDTO> getUserAreaAndAccount(Long id) {
        return Result.buildErrorResult("获取失败");
    }

	@Override
    public Result<BigDecimal> enableFrozenAmount(@PathVariable("type") Integer type) {
        return Result.buildSuccessResult(BigDecimal.ZERO);
    }

	@Override
	public Result<SysConfigDTO> getByTypeAndName(Integer type, Integer name) {
		return Result.buildSuccessResult(new SysConfigDTO());
	}

    @Override
    public Result<String> addBalanceByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> subBalanceByUserId(@PathVariable("userId") Long userId, @PathVariable("money") String money) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> subBalanceAddFrozenAmount(@PathVariable("userId") Long userId, @PathVariable("money") String money) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<CsAccountVO> getAccountWithUser(@PathVariable("id") Long id) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<CsAccountVO> getAccountByUserId() {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> unFreezeByUserIdAndDealerId(@RequestParam("buyerId") Long buyerId, @RequestParam("buyerFrozenMoney") String buyerFrozenMoney, @RequestParam("dealerId") Long dealerId, @RequestParam("dealerFrozenMoney") String dealerFrozenMoney) {
        return Result.buildErrorResult("获取失败");
    }

    @Override
    public Result<String> updateUserVipFlag(@PathVariable("id") Long id, @PathVariable("vipFlag") Integer vipFlag) {
        return Result.buildErrorResult("获取失败");
    }
}
