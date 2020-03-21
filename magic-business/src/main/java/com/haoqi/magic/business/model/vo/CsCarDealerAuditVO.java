package com.haoqi.magic.business.model.vo;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author: mengyao
 * @Date: 2019/5/5 0005 10:28
 * @Description:
 */
@Data
public class CsCarDealerAuditVO implements Serializable {


	private static final long serialVersionUID = -3013660959661815789L;

	@ApiModelProperty(value = "商家id")
	private Long id;
	/**
	 * 审核意见
	 */
	@ApiModelProperty(value = "审核备注")
	private String auditRemarks;

	/**
	 * 审核时间
	 */
	@ApiModelProperty(value = "审核时间")
	private Date auditTime;

	/**
	 * 审核人
	 */
	@ApiModelProperty(value = "审核人")
	private String auditPerson;

	/**
	 * 审核类型
	 */
	@ApiModelProperty(value = "审核类型")
	private Integer auditType;



	/**
	 * 最后一次审核用户id
	 */
	@ApiModelProperty(value = "最后一次审核用户id 前端请无视")
	private Long lastAuditUserId;
	/**
	 * 最后一次审核用户名称
	 */
	@ApiModelProperty(value = "最后一次审核用户名称 前端请无视")
	private String lastAuditLoginName;

}
