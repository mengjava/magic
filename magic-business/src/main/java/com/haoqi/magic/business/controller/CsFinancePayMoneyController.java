package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.haoqi.magic.business.enums.DisputeFlagEnum;
import com.haoqi.magic.business.model.dto.CsFinancePageDTO;
import com.haoqi.magic.business.model.dto.CsFinancePayMoneyDTO;
import com.haoqi.magic.business.model.vo.CsFinancePayFileQueryVO;
import com.haoqi.magic.business.model.vo.CsFinancePayMoneyQueryVO;
import com.haoqi.magic.business.model.vo.CsFinancePayMoneyVO;
import com.haoqi.magic.business.service.ICsFinancePayMoneyService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.mybatis.Query;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.haoqi.rigger.web.controller.BaseController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 财务打款表 前端控制器
 * </p>
 *
 * @author yanhao
 * @since 2019-12-12
 */
@RestController
@RequestMapping("/csFinancePayMoney")
@Api(tags = "财务打款")
public class CsFinancePayMoneyController extends BaseController {

    @Autowired
    private ICsFinancePayMoneyService financePayMoneyService;


    /**
     * 财务打款管理列表
     */
    @PostMapping("/page")
    @ApiOperation(value = "财务打款管理列表")
    public Result<Page<List<CsFinancePageDTO>>> selectByPage(@RequestBody CsFinancePayMoneyQueryVO vo) {
        validatorHandler.validator(vo);
        Map<String, Object> params = BeanUtil.beanToMap(vo);
        params.put("disputeFlagList", Arrays.asList(DisputeFlagEnum.NON_DISPUTE.getKey(), DisputeFlagEnum.DISPUTED.getKey()));
        Page page = financePayMoneyService.selectFinanceByPage(new Query(params));
        return Result.buildSuccessResult(page);
    }


    /**
     * 保存打款信息(帐号 附件)
     * <p>
     * 买家车款(买家付款后买家违约、买家付款后卖家违约、买家付款后协商平退)
     * <p>
     * 卖家车款
     * 1）先过户后收款：过户后
     * 2）先收款后过户：买家付款后)
     * <p>
     * 收款信息(1.如果收款项为“车款+服务费”，且买卖交易状态为卖出还未买家付款，此时把该买卖交易状态改为“买家支付”，对应的买家的账单新增一条“车款+服务费”账单。)
     */
    @PostMapping("/add")
    @ApiOperation(value = "保存打款信息(帐号 附件)")
    public Result<Page> add(@RequestBody CsFinancePayMoneyVO vo) {
        validatorHandler.validator(vo);
        vo.setOperatorUser(currentUser().getUserName());
        financePayMoneyService.add(vo);
        return Result.buildSuccessResult("保存成功");
    }


    /**
     * 财务明细(打款,收款)记录列表
     */
    @PostMapping("/payPage")
    @ApiOperation(value = "分页财务明细(打款,收款)记录列表")
    public Result<Page<List<CsFinancePayMoneyDTO>>> selectByPayPage(@RequestBody CsFinancePayFileQueryVO vo) {
        validatorHandler.validator(vo);
        Map<String, Object> params = BeanUtil.beanToMap(vo);
        if (Constants.YES.equals(vo.getPayMoneyType())) {
            params.put("payMoneyTypeList", Arrays.asList(1, 2));
        } else {
            params.put("payMoneyTypeList", Arrays.asList(3));
        }
        Page page = financePayMoneyService.selectByPayPage(new Query(params));
        return Result.buildSuccessResult(page);
    }

    /**
     * 获取打款信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getPayInfoById/{id}")
    @ApiOperation(value = "获取买家和卖家打款信息 id:打款列表id")
    public Result<CsFinancePayMoneyDTO> getPayInfoById(@PathVariable("id") Long id) {
        CsFinancePayMoneyDTO dto = financePayMoneyService.selectPayInfoById(id);
        return Result.buildSuccessResult(dto);
    }

}

