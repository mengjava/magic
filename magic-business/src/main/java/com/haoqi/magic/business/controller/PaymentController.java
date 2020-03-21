package com.haoqi.magic.business.controller;

import com.haoqi.magic.business.model.dto.PaymentOrderDTO;
import com.haoqi.magic.business.model.vo.PaymentParamVO;
import com.haoqi.magic.business.service.ICsAccountDetailService;
import com.haoqi.magic.business.service.ICsCarOrderService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 支付相关控制器
 * 1.生成支付订单
 * 2.支付接口
 *
 * @author twg
 * @since 2019/12/2
 */
@Slf4j
@RestController
@RequestMapping("payment")
@Api(tags = "支付相关Controller")
public class PaymentController extends BaseController {

    @Autowired
    private ICsAccountDetailService accountDetailService;
    @Autowired
    private ICsCarOrderService orderService;

    /**
     * 通过交易类型、金额，构建支付订单
     *
     * @return
     */
    @PostMapping("buildingOrder")
    @ApiOperation(value = "生成支付订单")
    public Result<PaymentOrderDTO> buildingOrder(@RequestBody PaymentParamVO paymentParam) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(paymentParam);
        return Result.buildSuccessResult(accountDetailService.buildingOrder(userInfo.getId(), paymentParam.getOrderId(),
                paymentParam.getVipId(), paymentParam.getMoney(), "", paymentParam.getTradeType(),
                paymentParam.getPaymentId(), paymentParam.getDeviceInfo(), paymentParam.getDataFrom()));
    }

    /**
     * 买方付款成功后修改订单状态
     *
     * @param serialNo 支付订单号
     * @param payCode  余额时支付密码
     * @return
     */
    @PostMapping("confirm")
    @ApiOperation(value = "买方付款成功后回调用，修改订单状态")
    public Result<PaymentOrderDTO> confirmPaid(@RequestParam("serialNo") String serialNo, @RequestParam(value = "payCode", required = false) String payCode) {
        currentUser();
        return Result.buildSuccessResult(orderService.confirmPaid(serialNo, payCode));
    }

    /**
     * 支付取消
     *
     * @param serialNo
     * @return
     */
    @PostMapping("/cancel/{serialNo}")
    @ApiOperation(value = "支付取消")
    public Result<String> cancel(@PathVariable("serialNo") String serialNo) {
        try {
            accountDetailService.cancel(serialNo);
        } catch (Exception e) {
            log.error("支付取消异常：{}", e);
        }
        return Result.buildSuccessResult("操作成功");
    }
}


