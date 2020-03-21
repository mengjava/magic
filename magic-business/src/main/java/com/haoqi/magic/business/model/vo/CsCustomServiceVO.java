package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: mengyao
 * @Date: 2019/5/17 0017 15:21
 * @Description:
 */
@Data
public class CsCustomServiceVO  implements Serializable {
	private static final long serialVersionUID = 263911174359790694L;

	@ApiModelProperty(value = "意向id",required = true)
	@NotNull(message = "意向id")
	private  Long id;

	@ApiModelProperty(value = "处理意见",required = true)
	@NotNull(message = "处理意见")
	private  String processRemark;

	@ApiModelProperty(value = "是否下架: 1 下架 2: 调拨")
	private Integer isPullOff;

	@ApiModelProperty(value = "车源id",required = true)
	private  Long carId;



	/**
	 * 处理用户id
	 */
	private Long processUserId;
	/**
	 * 【冗余字段】处理用户名称
	 */
	private String processLoginName;
	/**
	 * 【冗余字段】处理用户姓名
	 */
	private String processUserName;


}
