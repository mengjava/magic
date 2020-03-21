package com.haoqi.magic.business.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 消息推送DTO
 */
@Data
public class OrderPushMessageDTO implements Serializable {
    /**
     * 操作人id
     */
    protected Long sender;

    /**
     * 接收方
     */
    protected Long receiver;

    /**
     * 操作人公司id
     */
    protected Long compId;

    /**
     * 消息标题
     */
    protected String title;

    /**
     * 订单、评估状态
     */
    protected String status;
    /**
     * 备注
     */
    protected String remark;

    /**
     * 消息类型
     */
    protected Integer messageType;

    /**
     * app类型 1车商版 2接单版
     */
    protected Integer appType = 1;

    /**
     * 车款车型
     */
    protected String carBrandName;

    /**
     * 车价(万元)
     */
    private BigDecimal price;

    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 车辆id
     */
    private Long carInfoId;
}
