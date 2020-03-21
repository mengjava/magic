package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:
 * 前端调拨历史记录DTO
 * @auther: yanhao
 * @param:
 * @date: 2019/5/15 9:56
 * @Description:
 */
@Data
public class CsTSRecordDTO implements Serializable {
    private static final long serialVersionUID = -4210319322203941925L;

    private Long id;
    //车辆ID
    private Long csCarInfoId;

    //车商ID（来源）
    private Long csCarDearlerIdFrom;
    //车商名称（来源）
    @ApiModelProperty(value = "车商名称（来源）")
    private String csCarDearlerNameFrom;

    //车商ID（接收）
    private Long csCarDearlerIdTo;
    //车商名称（接收）
    @ApiModelProperty(value = "车商名称（接收）")
    private String csCarDearlerNameTo;

    //调拨发起时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transferTime;

    //调拨审核时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transferAuditTime;

    //调拨状态（1：已申请，2：申请通过，-1：申请拒绝，取消调拨-2）
    private Integer transferStatus;
}
