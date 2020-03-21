package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ClassName:com.haoqi.magic.business.model.vo <br/>
 * Function:调拨记录 <br/>
 * Date:     2019/5/6 9:56 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsTransferRecordVO implements Serializable {


    private static final long serialVersionUID = 1298558016698155096L;

    //主键
    private Long id;

    //创建人
    @ApiModelProperty(value = "创建人")
    private Long creator;

    //修改人
    @ApiModelProperty(value = "修改人")
    private Long modifier;

    //车辆基础信息表id
    @ApiModelProperty(value = "车辆基础信息表id")
    private Long csCarInfoId;

    //调拨经销商Id(from)
    @ApiModelProperty(value = "调拨经销商Id(from)")
    private Long csCarDearlerIdFrom;


    //【冗余】调拨经销商名称(from)
    @ApiModelProperty(value = "调拨经销商名称(from)")
    private String csCarDearlerNameFrom;

    //【冗余】调拨经销商名称(to)
    @ApiModelProperty(value = "调拨经销商名称(to)")
    private String csCarDearlerNameTo;

    //调拨经销商Id(to)
    @ApiModelProperty(value = "调拨经销商Id(to)")
    private Long csCarDearlerIdTo;


    //调拨状态（1：已申请，2：申请通过，-1：申请拒绝，取消调拨-2）
    @ApiModelProperty(value = "调拨状态（1：已申请，2：申请通过，-1：申请拒绝，取消调拨-2）")
    private Integer transferStatus;

    //调拨发起用户id
    @ApiModelProperty(value = "调拨发起用户id")
    private Long transferUserId;

    //调拨审核用户id
    @ApiModelProperty(value = "调拨审核用户id")
    private Long transferAuditUserId;

    //调拨发起时间
    @ApiModelProperty(value = "调拨发起时间")
    private Date transferTime;

    //调拨审核时间
    @ApiModelProperty(value = "调拨审核时间")
    private Date transferAuditTime;

    @ApiModelProperty(value = "审核信息")
    private String remark;

    //调拨取消时间
    @ApiModelProperty(value = "调拨取消时间")
    private Date transferCancelTime;
}
