package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by yanhao on 2020/1/20.14:51
 */
@Data
public class ServiceFreeDTO implements Serializable {

    @ApiModelProperty(value = "服务费")
    private BigDecimal serviceFree;

    @ApiModelProperty(value = "服务费 1：单笔/0服务费比例")
    private Integer serviceType;
}
