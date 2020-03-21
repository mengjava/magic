package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 过户申请
 *
 * @author twg
 * @since 2019/12/10
 */
@Data
public class TransferApplyVO implements Serializable {
    /**
     * 订单id
     */
    @NotNull(message = "订单id不能为空")
    @ApiModelProperty(value = "订单id")
    private Long id;

    /**
     * 过户申请人
     */
    private Long transferApplyUserId;

    /**
     * 过户申请人
     */
    private String transferApplyUser;

    /**
     * 过户日期
     */
    @NotNull(message = "过户日期不能为空")
    @ApiModelProperty(value = "过户日期")
    private Date transferTime;

    /**
     * 过户备注
     */
    @ApiModelProperty(value = "过户备注")
    private String transferRemark;

    /**
     * 过户附件
     */
    @Valid
    private List<TransferFileVO> transferFiles;
}
