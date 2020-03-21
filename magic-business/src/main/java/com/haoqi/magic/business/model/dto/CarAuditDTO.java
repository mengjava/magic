package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author twg
 * @since 2019/5/7
 */
@Data
public class CarAuditDTO implements Serializable {
    private Long id;

    @ApiModelProperty(value = "审核信息")
    private String remark;

    @JsonSerialize(using = DateTimeJsonSerializer.class)
    private Date auditTime;

    @ApiModelProperty(value = "审核状态")
    @NotNull(message = "审核状态不能为空")
    private Integer auditStatus;

    private Long auditUserId;

    /**
     * 审核员帐号
     */
    private String auditLoginName;

    /**
     * 审核员姓名
     */
    private String auditUserName;

    @NotNull(message = "车辆id不能为空")
    private Long csCarInfoId;

	private String vin;

}
