package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ClassName:com.haoqi.magic.business.model.vo <br/>
 * Function: <br/>
 * Date:     2019/5/7 11:37 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class CsTransferRecordPageVO  extends Page {
    private static final long serialVersionUID = 162137911956373453L;


    @ApiModelProperty(value = "1:已调拨Tab 2：待调拨Tab")
    private Integer tab;

    @ApiModelProperty(value = "调拨状态（1：已申请，2：申请通过，-1：申请拒绝，取消调拨-2） 全部传空")
    private Integer transferStatus;

    @ApiModelProperty(value = "调拨发起时间--开始")
    private String transferTimeStart;
    @ApiModelProperty(value = "调拨发起时间--结束")
    private String transferTimeEnd;

    @ApiModelProperty(value = "调拨审核时间--开始")
    private String transferAuditTimeStart;
    @ApiModelProperty(value = "调拨审核时间--结束")
    private String transferAuditTimeEnd;

    @ApiModelProperty(value = "车型车款")
    private String sysCarModelName;
}
