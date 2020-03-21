package com.haoqi.magic.business.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.haoqi.magic.business.client.SystemServiceClient;
import com.haoqi.magic.business.enums.*;
import com.haoqi.magic.business.model.dto.*;
import com.haoqi.magic.business.model.vo.*;
import com.haoqi.magic.business.service.ICsCarOrderService;
import com.haoqi.magic.common.constants.Constants;
import com.haoqi.rigger.common.CommonConstant;
import com.haoqi.rigger.common.util.BeanUtils;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.core.UserInfo;
import com.haoqi.rigger.mybatis.Query;
import com.haoqi.rigger.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 交易订单表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-12-03
 */
@RestController
@RequestMapping("/csCarOrder")
@Api(tags = "订单")
@Slf4j
public class CsCarOrderController extends BaseController {

    @Autowired
    private ICsCarOrderService orderService;

    @Autowired
    private SystemServiceClient systemServiceClient;

    /**
     * 买入车辆，生成订单
     *
     * @param carId 车辆id
     * @return
     */
    @PostMapping("buyer/buying/{carId}")
    @ApiOperation(value = "买入车辆，生成订单")
    public Result<String> buying(@PathVariable("carId") Long carId) {
        UserInfo userInfo = currentUser();
        Result<UserDTO> user = systemServiceClient.getUserByUserId(userInfo.getId());
        if (!user.isSuccess()) {
            return Result.buildErrorResult("获取用户信息失败");
        }
        if (StrUtil.isBlank(user.getData().getPayPassword())) {
            return Result.buildErrorResult("请设置支付密码");
        }
        orderService.building(carId, userInfo.getId());
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 买方订单分页列表
     *
     * @param orderPage
     * @return
     */
    @PostMapping("buyer/orderPage")
    @ApiOperation(value = "买方订单分页列表")
    public Result<Page<CarOrderVO>> orderPageForBuyer(@RequestBody OrderPageVO orderPage) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(orderPage);
        orderPage.setBuyerUserId(userInfo.getId());
        Page<CarOrderDTO> page = orderService.orderPageForBuyer(new Query(BeanUtil.beanToMap(orderPage)));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }


    /**
     * 买方申请撤销买入
     *
     * @param carOrder
     * @return
     */
    @PostMapping("buyer/cancelBuying")
    @ApiOperation(value = "买方申请撤销买入")
    public Result<String> cancelBuying(@RequestBody CarOrderParamVO carOrder) {
        currentUser();
        validatorHandler.validator(carOrder);
        orderService.cancelBuying(carOrder.getId(), carOrder.getApplyInfo());
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 买方发起复检
     *
     * @param orderId
     * @return
     */
    @PostMapping("buyer/applyRecheck/{orderId}")
    @ApiOperation(value = "买方发起复检")
    public Result<String> applyRecheck(@PathVariable("orderId") Long orderId) {
        UserInfo userInfo = currentUser();
        orderService.applyRecheck(userInfo.getId(), orderId);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 卖方订单分页列表
     *
     * @param orderPage
     * @return
     */
    @PostMapping("seller/orderPage")
    @ApiOperation(value = "卖方订单分页列表")
    public Result<Page<CarOrderVO>> orderPageForSeller(@RequestBody OrderPageVO orderPage) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(orderPage);
        orderPage.setCarDealerUserId(userInfo.getId());
        Page<CarOrderDTO> page = orderService.orderPageForSeller(new Query(BeanUtil.beanToMap(orderPage)));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }


    /**
     * 卖方确认卖出
     *
     * @param carOrder
     * @return
     */
    @PostMapping("seller/confirmSale")
    @ApiOperation(value = "卖方确认卖出")
    public Result<String> confirmSale(@RequestBody CarOrderParamVO carOrder) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(carOrder);
        orderService.confirmSale(carOrder.getId(), carOrder.getPrice(), userInfo.getId());
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 卖方拒绝卖出
     *
     * @param orderId 订单id
     * @return
     */
    @PostMapping("seller/refuseSale/{orderId}")
    @ApiOperation(value = "卖方拒绝卖出")
    public Result<String> refuseSale(@PathVariable("orderId") Long orderId) {
        currentUser();
        orderService.refuseSale(orderId);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 发起争议
     *
     * @param carOrder
     * @return
     */
    @PostMapping("applyDispute")
    @ApiOperation(value = "发起争议")
    public Result<String> applyDispute(@RequestBody CarOrderParamVO carOrder) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(carOrder);
        orderService.applyDispute(userInfo.getId(), userInfo.getRealName(), carOrder.getId(), carOrder.getDisputeItems());
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 冻结车辆列表
     *
     * @return
     */
    @PostMapping("freezingCar")
    @ApiOperation(value = "冻结车辆列表")
    public Result<Page<CarOrderVO>> freezingCar() {
        UserInfo userInfo = currentUser();
        Map<String, Object> param = Maps.newHashMap();
        param.put("userId", userInfo.getId());
        param.put("descs", Arrays.asList("gmt_create", "sell_time"));
        param.put("statusList", Arrays.asList(OrderStatusEnum.BUYING.getKey(), OrderStatusEnum.CANCEL_BUYING_APPLY.getKey(), OrderStatusEnum.PAID.getKey(),
                OrderStatusEnum.TO_SELL.getKey(), OrderStatusEnum.TRANSFERRED.getKey(), OrderStatusEnum.PAYMENT.getKey()));
        Page<CarOrderDTO> page = orderService.freezingCar(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }


    /**********************************************PC 端功能*************************************************/
    /**
     * PC端同城、跨城成交车辆分页列表
     *
     * @param isSameCity 1 同城
     * @return
     */
    @PostMapping("orderPage/{isSameCity}")
    @ApiOperation(value = "PC端同城、跨城成交车辆分页列表 1：同城，0：跨城")
    public Result<Page<CarOrderVO>> orderPageForPc(@PathVariable("isSameCity") Integer isSameCity, @RequestBody CarOrderQueryVO orderQuery) {
        currentUser();
        Map<String, Object> param = BeanUtil.beanToMap(orderQuery);
        param.put("sameCityFlag", isSameCity);
        param.put("descs", Arrays.asList(CommonConstant.STATUS_DEL.equals(isSameCity) ? "a.gmt_create" : "a.sell_time"));
        if (CommonConstant.STATUS_NORMAL.equals(isSameCity)) {
            param.put("statusList", Arrays.asList(OrderStatusEnum.TO_SELL.getKey(), OrderStatusEnum.PAID.getKey(),
                    OrderStatusEnum.TRANSFERRED.getKey(), OrderStatusEnum.PAYMENT.getKey(), OrderStatusEnum.COMPLETE.getKey()));
        }
        Page<CarOrderDTO> page = orderService.orderPageForPc(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }

    /**
     * 成交车辆中获取订单、过户附件、争议信息
     *
     * @param orderId
     * @return
     */
    @GetMapping("get/orderTransferDisputeInfo/{orderId}")
    @ApiOperation(value = "成交车辆中获取订单、过户附件、争议信息")
    public Result<OrderTransferDisputeVO> getOrderTransferDisputeInfo(@PathVariable("orderId") Long orderId, @RequestParam(value = "lock", required = false) Boolean lock) {
        UserInfo userInfo = currentUser();
        CarOrderDTO carOrderDTO = orderService.getOrderTransferDisputeInfo(orderId, userInfo.getRealName(), lock);
        return Result.buildSuccessResult(BeanUtils.beanCopy(carOrderDTO, OrderTransferDisputeVO.class));
    }

    /**
     * 常规复检分页列表
     * 买家发起的复检且复检完成的订单
     *
     * @param orderQuery
     * @return
     */
    @PostMapping("commonRecheckPage")
    @ApiOperation(value = "常规复检分页列表: 买家发起的复检且复检完成的订单")
    public Result<Page<CarOrderVO>> commonRecheckPageForPc(@RequestBody CarOrderQueryVO orderQuery) {
        currentUser();
        Map<String, Object> param = BeanUtil.beanToMap(orderQuery);
        param.put("descs", Arrays.asList("a.recheck_time"));
        param.put("recheckType", CommonConstant.STATUS_DEL);
        param.put("recheckStatus", CompleteStatusEnum.COMPLETED.getKey());
        param.put("recheckResult", param.getOrDefault("recheckResult", RecheckResultEnum.UN_NORMAL.getKey()));
        Page<CarOrderDTO> page = orderService.orderPageForPc(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }

    /**
     * 常规复检中获取订单、复检、争议信息
     *
     * @param orderId
     * @param lock    详情查看不要带lock
     * @return
     */
    @GetMapping("get/orderRecheckDisputeInfo/{orderId}")
    @ApiOperation(value = "常规复检中获取订单、复检、争议信息 获取详情: lock ->false")
    public Result<OrderRecheckDisputeVO> getOrderRecheckDisputeInfo(@PathVariable("orderId") Long orderId, @RequestParam(value = "lock", required = false) Boolean lock) {
        UserInfo userInfo = currentUser();
        CarOrderDTO carOrderDTO = orderService.getOrderRecheckDisputeInfo(orderId, userInfo.getRealName(), lock);
        return Result.buildSuccessResult(BeanUtils.beanCopy(carOrderDTO, OrderRecheckDisputeVO.class));
    }

    /**
     * 通过订单ID，获取复检信息分页列表（通用）
     *
     * @param orderId
     * @return
     */
    @GetMapping("get/recheckFile/{orderId}")
    @ApiOperation(value = "通过订单ID，获取复检信息分页列表（通用）")
    public Result<Page<RecheckFileVO>> recheckFilePage(@PathVariable("orderId") Long orderId) {
        Map<String, Object> param = Maps.newHashMap();
        param.put("orderId", orderId);
        Page<CsOrderRecheckFileDTO> page = orderService.recheckFilePage(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, RecheckFileVO.class));
    }


    /**
     * 过户审核分页列表
     *
     * @param orderQuery
     * @return
     */
    @PostMapping("transferPage")
    @ApiOperation(value = "过户审核分页列表")
    public Result<Page<CarOrderVO>> transferPageForPc(@RequestBody CarOrderQueryVO orderQuery) {
        currentUser();
        Map<String, Object> param = BeanUtil.beanToMap(orderQuery);
        param.put("transferStatusList", Arrays.asList(TransferStatusEnum.TRANSFER.getKey(), TransferStatusEnum.TRANSFERRED.getKey()));
        param.put("descs", Arrays.asList("a.transfer_apply_time"));
        Page<CarOrderDTO> page = orderService.orderPageForPc(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }

    /**
     * 过户审核
     *
     * @param orderId
     * @param desc        审核意见
     * @param auditStatus 审核状态 1 通过 2 拒绝
     * @return
     */
    @PostMapping("auditingTransfer/{orderId}")
    @ApiOperation(value = "过户审核 auditStatus :1 通过 2 拒绝")
    public Result<String> auditingTransfer(@PathVariable("orderId") Long orderId, @RequestParam("desc") String desc, @RequestParam("auditStatus") Integer auditStatus) {
        UserInfo userInfo = currentUser();
        orderService.auditingTransfer(orderId, userInfo.getId(), userInfo.getRealName(), desc, auditStatus);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 过户审核详情
     *
     * @param orderId
     * @param lock    详情查看不要带lock
     * @return
     */
    @GetMapping("get/orderTransferInfo/{orderId}")
    @ApiOperation(value = "过户审核详情: 详情查看不要带lock")
    public Result<OrderTransferVO> getOrderTransferInfo(@PathVariable("orderId") Long orderId, @RequestParam(value = "lock", required = false) Boolean lock) {
        UserInfo userInfo = currentUser();
        CarOrderDTO carOrderDTO = orderService.getOrderTransferInfo(orderId, userInfo.getRealName(), lock);
        return Result.buildSuccessResult(BeanUtils.beanCopy(carOrderDTO, OrderTransferVO.class));
    }


    /**
     * 过户申请
     *
     * @param transferApply
     * @return
     */
    @PostMapping("applyTransfer")
    @ApiOperation(value = "过户申请")
    public Result<String> applyTransfer(@RequestBody TransferApplyVO transferApply) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(transferApply);
        transferApply.setTransferApplyUserId(userInfo.getId());
        transferApply.setTransferApplyUser(userInfo.getRealName());
        orderService.applyTransfer(transferApply);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 再次争议
     *
     * @param desc
     * @param orderId
     * @return
     */
    @PostMapping("reDispute/{orderId}")
    @ApiOperation(value = "再次争议")
    public Result<String> reDispute(@RequestParam("desc") String desc, @PathVariable("orderId") Long orderId) {
        UserInfo userInfo = currentUser();
        orderService.reDispute(orderId, userInfo.getId(), userInfo.getRealName(), desc);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 撤销买入审核分页列表
     *
     * @param orderQuery
     * @return
     */
    @PostMapping("cancelBuyPage")
    @ApiOperation(value = "撤销买入审核分页列表")
    public Result<Page<CarOrderVO>> cancelBuyPageForPc(@RequestBody CarOrderQueryVO orderQuery) {
        currentUser();
        Map<String, Object> param = BeanUtil.beanToMap(orderQuery);
        param.put("statusList", Arrays.asList(OrderStatusEnum.CANCEL_BUYING_APPLY.getKey(), OrderStatusEnum.CANCEL_BUYING_PASS.getKey()));
        param.put("descs", Arrays.asList("a.cancel_buy_apply_time"));
        Page<CarOrderDTO> page = orderService.orderPageForPc(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }


    /**
     * 撤销买入审核
     *
     * @param orderId
     * @param desc
     * @param auditStatus
     * @return
     */
    @PostMapping("auditingCancelBuy/{orderId}")
    @ApiOperation(value = "撤销买入审核: 1 通过 2 拒绝 撤销买入审核拒绝")
    public Result<String> auditingCancelBuy(@PathVariable("orderId") Long orderId, @RequestParam("desc") String desc, @RequestParam("auditStatus") Integer auditStatus) {
        UserInfo userInfo = currentUser();
        orderService.auditingCancelBuy(orderId, userInfo.getId(), userInfo.getRealName(), desc, auditStatus);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 撤销买入审核详情
     *
     * @param orderId
     * @param lock    详情查看不要带lock
     * @return
     */
    @GetMapping("get/orderCancelBuyInfo/{orderId}")
    @ApiOperation(value = "撤销买入审核详情")
    public Result<CarOrderVO> getOrderCancelBuyInfo(@PathVariable("orderId") Long orderId, @RequestParam(value = "lock", required = false) Boolean lock) {
        UserInfo userInfo = currentUser();
        CarOrderDTO carOrderDTO = orderService.getOrderAuditInfo(orderId, userInfo.getRealName(), lock, false);
        return Result.buildSuccessResult(BeanUtils.beanCopy(carOrderDTO, CarOrderVO.class));
    }


    /**
     * 复检审核分页列表
     * 每个复检员只能查看复检所在地的车辆，其他地区复检车辆看不到
     *
     * @param orderQuery
     * @return
     */
    @PostMapping("recheckPage")
    @ApiOperation(value = "复检审核分页列表")
    public Result<Page<CarOrderVO>> recheckPageForPc(@RequestBody CarOrderQueryVO orderQuery) {
        UserInfo userInfo = currentUser();
        /*Result<UserDTO> result = systemServiceClient.getUserByUserId(userInfo.getId());
        if (!result.isSuccess()) {
            throw new RiggerException("获取用户信息失败");
        }
        if (StrUtil.isBlank(result.getData().getArea())) {
            throw new RiggerException("该用户未设置城市");
        }*/
        //String[] areas = StrUtil.split(result.getData().getArea(), StrUtil.DASHED);
        Map<String, Object> param = BeanUtil.beanToMap(orderQuery);
        /*param.put("city", areas[1]);
        param.put("province", areas[0]);*/
        param.put("recheckFlag", CommonConstant.STATUS_DEL);
        param.put("recheckTypeList", Arrays.asList(CommonConstant.STATUS_DEL, CommonConstant.STATUS_EXPIRE));
        param.put("descs", Constants.YES.equals(orderQuery.getRecheckStatus()) ?
                Arrays.asList("a.recheck_time") : Arrays.asList("a.recheck_apply_time"));
        Page<CarOrderDTO> page = orderService.orderPageForPc(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }

    /**
     * 复检审核
     *
     * @param orderRecheckDispute
     * @return
     */
    @PostMapping("auditingRecheck")
    @ApiOperation(value = "复检审核")
    public Result<String> auditingRecheck(@RequestBody OrderRecheckDisputeVO orderRecheckDispute) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(orderRecheckDispute);
        orderService.auditingRecheck(userInfo.getId(), userInfo.getRealName(), orderRecheckDispute.getOrderId(),
                orderRecheckDispute.getRecheckResult(), orderRecheckDispute.getDisputeItems(), orderRecheckDispute.getRecheckFiles());
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 复检详情
     *
     * @param orderId
     * @param lock    详情查看不要带lock
     * @return
     */
    @GetMapping("get/orderRecheckInfo/{orderId}")
    @ApiOperation(value = "复检详情 详情查看不要带lock")
    public Result<OrderRecheckDisputeVO> getOrderRecheckInfo(@PathVariable("orderId") Long orderId, @RequestParam(value = "lock", required = false) Boolean lock) {
        UserInfo userInfo = currentUser();
        CarOrderDTO carOrderDTO = orderService.getOrderRecheckInfo(orderId, userInfo.getRealName(), lock);
        return Result.buildSuccessResult(BeanUtils.beanCopy(carOrderDTO, OrderRecheckDisputeVO.class));
    }

    /**
     * 争议处理分页查询列表
     *
     * @param orderQuery
     * @return
     */
    @PostMapping("disputeManagePage")
    @ApiOperation(value = "争议处理分页查询列表")
    public Result<Page<CarOrderVO>> disputeManagePageForPc(@RequestBody CarOrderQueryVO orderQuery) {
        currentUser();
        Map<String, Object> param = BeanUtil.beanToMap(orderQuery);
        param.put("isDisputeManage", true);
        param.put("disputeAuditStatus", CommonConstant.STATUS_NORMAL);
        param.put("disputeFlag", Objects.isNull(orderQuery.getDisputeFlag()) ? DisputeFlagEnum.IN_DISPUTE.getKey() : orderQuery.getDisputeFlag());
        param.put("ascs", Arrays.asList("a.recheck_flag"));
        Page<CarOrderDTO> page = orderService.orderPageForPc(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }


    /**
     * 争议处理详情
     *
     * @param orderId
     * @param lock
     * @return
     */
    @GetMapping("get/disputeManageInfo/{orderId}")
    @ApiOperation(value = "争议处理详情(包含审核信息列表)")
    public Result<OrderDisputeVO> getDisputeManageInfo(@PathVariable("orderId") Long orderId, @RequestParam(value = "lock", required = false) Boolean lock) {
        UserInfo userInfo = currentUser();
        CarOrderDTO carOrderDTO = orderService.getDisputeManageInfo(orderId, userInfo.getRealName(), lock);
        OrderDisputeVO orderDispute = BeanUtils.beanCopy(carOrderDTO, OrderDisputeVO.class);
        if (StrUtil.isNotBlank(carOrderDTO.getDisputeAuditJsonContent())) {
            List<OrderAuditLogDTO> orderAuditLogs = JSONUtil.toList(JSONUtil.parseArray(carOrderDTO.getDisputeAuditJsonContent()), OrderAuditLogDTO.class);
            orderDispute.setOrderAuditLogs(orderAuditLogs);
        }
        return Result.buildSuccessResult(orderDispute);
    }

    /**
     * 争议处理
     *
     * @param orderDispute
     * @return
     */
    @PostMapping("handlerDispute")
    @ApiOperation(value = "争议处理")
    public Result<String> handlerDispute(@RequestBody OrderDisputeVO orderDispute) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(orderDispute);
        orderService.handlerDispute(userInfo.getId(), userInfo.getRealName(), orderDispute);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 争议初审分页查询列表
     *
     * @param orderQuery
     * @return
     */
    @PostMapping("firstAuditingDisputePage")
    @ApiOperation(value = "争议初审分页查询列表")
    public Result<Page<CarOrderVO>> firstAuditingDisputePageForPc(@RequestBody CarOrderQueryVO orderQuery) {
        currentUser();
        Map<String, Object> param = BeanUtil.beanToMap(orderQuery);
        param.put("disputeFlag", DisputeFlagEnum.IN_DISPUTE.getKey());
        param.put("disputeAuditStatus", CommonConstant.STATUS_DEL);
        param.put("descs", Arrays.asList("a.dispute_process_time"));
        Page<CarOrderDTO> page = orderService.orderPageForPc(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }

    /**
     * 争议初审
     *
     * @return
     */
    @PostMapping("firstAuditingDispute")
    @ApiOperation(value = "争议初审")
    public Result<String> firstAuditingDispute(@RequestBody OrderDisputeVO orderDispute) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(orderDispute);
        orderService.firstAuditingDispute(userInfo.getId(), userInfo.getRealName(), orderDispute);
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 争议终审分页查询列表
     *
     * @param orderQuery
     * @return
     */
    @PostMapping("finishAuditingDisputePage")
    @ApiOperation(value = "争议终审分页查询列表")
    public Result<Page<CarOrderVO>> finishAuditingDisputePageForPc(@RequestBody CarOrderQueryVO orderQuery) {
        currentUser();
        Map<String, Object> param = BeanUtil.beanToMap(orderQuery);
        param.put("disputeFlagList", Arrays.asList(DisputeFlagEnum.IN_DISPUTE.getKey(), DisputeFlagEnum.DISPUTED.getKey()));
        param.put("disputeAuditStatus", CommonConstant.STATUS_EXPIRE);
        param.put("descs", Arrays.asList("b.dispute_first_audit_time"));
        Page<CarOrderDTO> page = orderService.orderPageForPc(new Query(param));
        return Result.buildSuccessResult(dtoConvertToVo(page, CarOrderVO.class));
    }


    /**
     * 争议终审
     *
     * @return
     */
    @PostMapping("finishAuditingDispute")
    @ApiOperation(value = "争议终审")
    public Result<String> finishAuditingDispute(@RequestBody OrderDisputeVO orderDispute) {
        UserInfo userInfo = currentUser();
        validatorHandler.validator(orderDispute);
        orderService.finishAuditingDispute(userInfo.getId(), userInfo.getRealName(), orderDispute);
        return Result.buildSuccessResult("操作成功");
    }


    /**
     * 换审核员
     *
     * @param ids
     * @return
     */
    @PostMapping("changeAuditor")
    @ApiOperation(value = "换审核员")
    public Result<String> changeAuditor(@RequestParam("ids") String ids) {
        currentUser();
        orderService.changeAuditor(Arrays.stream(ids.split(StrUtil.COMMA)).mapToLong(value -> Long.parseLong(value)).boxed().collect(Collectors.toList()));
        return Result.buildSuccessResult("操作成功");
    }

    /**
     * 订单审核记录
     *
     * @param orderId
     * @return
     */
    @GetMapping("disputeHandler/detail/{orderId}")
    @ApiOperation(value = "订单审核记录")
    public Result<DisputeHandlerDetailVO> detailDisputeHandler(@PathVariable("orderId") Long orderId) {
        currentUser();
        DisputeHandlerDetailVO detail = new DisputeHandlerDetailVO();
        CarOrderDTO carOrder = orderService.getDisputeInfo(orderId);
        if (StrUtil.isBlank(carOrder.getAuditJsonContent())) {
            return Result.buildSuccessResult(detail);
        }
        List<OrderAuditLogDTO> orderAuditLogs = JSONUtil.toList(JSONUtil.parseArray(carOrder.getAuditJsonContent()), OrderAuditLogDTO.class);
        List<OrderAuditLogDTO> disputeHandler = orderAuditLogs.stream().filter(orderAuditLogDTO -> orderAuditLogDTO.getAuditType().equals(AuditTypeEnum.AUDIT_DISPUTE_HANDLE.getKey())).collect(Collectors.toList());
        if (disputeHandler.stream().findFirst().isPresent()) {
            OrderAuditLogDTO dto = disputeHandler.stream().findFirst().get();
            detail = BeanUtils.beanCopy(dto, DisputeHandlerDetailVO.class);
            detail.setDisputeItems(carOrder.getDisputeItems());
        }
        return Result.buildSuccessResult(detail);
    }


    /**
     * 车辆审核交易记录
     *
     * @param carId
     * @return
     */
    @GetMapping("findOrderAudit/{orderId}/{carId}")
    @ApiOperation(value = "车辆审核交易记录")
    public Result<List<OrderAuditLogDTO>> findOrderAudit(@PathVariable("orderId") Long orderId, @PathVariable("carId") Long carId) {
        CarOrderDTO carOrder = orderService.getOrderAndAuditByOrderIdAndCarId(orderId, carId);
        List<OrderAuditLogDTO> list = Lists.newArrayList();
        if (StrUtil.isNotBlank(carOrder.getAuditJsonContent())) {
            list = JSONUtil.toList(JSONUtil.parseArray(carOrder.getAuditJsonContent()), OrderAuditLogDTO.class);
        }
        return Result.buildSuccessResult(list);
    }


    /**
     * 获取复检结果 复检信息 争议项信息
     */

    @GetMapping("/getOrderCheckAndDispute/{orderId}")
    @ApiOperation(value = "获取复检结果 复检信息 争议项信息")
    public Result<OrderCheckAndDisputeDTO> getOrderCheckAndDispute(@PathVariable("orderId") Long orderId) {
        OrderCheckAndDisputeDTO orderCheckAndDisputeDTO = orderService.getOrderCheckAndDisputeByOrderId(orderId);
        return Result.buildSuccessResult(orderCheckAndDisputeDTO);
    }

    /**************************************************************************/


    private <T> Page<T> dtoConvertToVo(Page<?> page, Class<T> clazz) {
        Page<T> result = new Page<T>();
        BeanUtil.copyProperties(page, result, "records");
        result.setRecords(BeanUtils.beansToList(page.getRecords(), clazz));
        return result;
    }

}

