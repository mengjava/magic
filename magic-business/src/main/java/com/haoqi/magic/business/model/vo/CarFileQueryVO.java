package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 功能描述:
 * 前端查询车辆照片的vo
 *
 * @auther: yanhao
 * @param:
 * @date: 2019/5/15 10:36
 * @Description:
 */
@Data
public class CarFileQueryVO implements Serializable {

    @ApiModelProperty(value = "车辆id")
    @NotNull(message = "车辆id不能为空")
    private Long id;
    @ApiModelProperty(value = "照片类型: 基本照片:0 事故照片:1 外观照片:2 缺陷照片:3")
    @NotNull(message = "照片类型不能为空")
    private Integer type;
}
