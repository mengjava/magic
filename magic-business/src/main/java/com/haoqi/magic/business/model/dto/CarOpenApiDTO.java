package com.haoqi.magic.business.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.haoqi.rigger.core.serializer.DateTimeJsonSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author twg
 * @since 2019/5/7
 */
@Data
public class CarOpenApiDTO implements Serializable {

	private static final long serialVersionUID = 4707956535036081362L;
	@ApiModelProperty(value = "车架号")
    @NotBlank(message = "车架号不能为空")
    private String vin;

	@ApiModelProperty(value = "购 买 报 告 后 回 调 地 址")
    private String callbackUrl;

    @ApiModelProperty(value = "发动机号")
    private String engineNo;

	@ApiModelProperty(value = "身份证号")
    private String idNo;

	@ApiModelProperty(value = "车牌号")
    private String carNo;

	@ApiModelProperty(value = "用户id")
	private Long userId;

	@ApiModelProperty(value = "用户姓名")
	private String userName;


	@ApiModelProperty(value = "查询类型  1 车源审核查询 0 vip查询")
	private Integer type;



}
