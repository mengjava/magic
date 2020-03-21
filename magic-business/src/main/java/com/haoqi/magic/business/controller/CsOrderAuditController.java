package com.haoqi.magic.business.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.haoqi.magic.business.model.dto.OrderAuditLogDTO;
import com.haoqi.magic.business.model.entity.CsOrderAudit;
import com.haoqi.magic.business.service.ICsOrderAuditService;
import com.haoqi.rigger.core.Result;
import com.haoqi.rigger.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 审核信息表 前端控制器
 * </p>
 *
 * @author twg
 * @since 2019-12-05
 */
@RestController
@RequestMapping("/csOrderAudit")
public class CsOrderAuditController extends BaseController {

    @Autowired
    private ICsOrderAuditService orderAuditService;

    /**
     * 通过订单id，获取订单争议初审、终审审核信息
     *
     * @param orderId
     * @return
     */
    @GetMapping("list/dispute/{orderId}")
    public Result<List<OrderAuditLogDTO>> disputeAuditList(@PathVariable("orderId") Long orderId) {
        CsOrderAudit orderAudit = orderAuditService.getByOrderId(orderId);
        String content = orderAudit.getDisputeAuditJsonContent();
        List<OrderAuditLogDTO> vos = Lists.newArrayList();
        if (StrUtil.isNotBlank(content)) {
            vos = JSONUtil.toList(JSONUtil.parseArray(content), OrderAuditLogDTO.class);
        }
        return Result.buildSuccessResult(vos);
    }

    /**
     * 订单交易审核记录
     *
     * @param orderId
     * @return
     */
    @GetMapping("list/{orderId}")
    public Result<List<OrderAuditLogDTO>> orderAuditList(@PathVariable("orderId") Long orderId) {
        CsOrderAudit orderAudit = orderAuditService.getByOrderId(orderId);
        String content = orderAudit.getAuditJsonContent();
        List<OrderAuditLogDTO> vos = Lists.newArrayList();
        if (StrUtil.isNotBlank(content)) {
            vos = JSONUtil.toList(JSONUtil.parseArray(content), OrderAuditLogDTO.class);
        }
        return Result.buildSuccessResult(vos);
    }





}

