package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yanhao on 2019/12/11.9:58
 */
@Getter
@Setter
public class PullOffCarVO implements Serializable {

    @ApiModelProperty(value = "车辆id")
    @NotNull(message = "车辆id不能为空")
    private Long id;

    @ApiModelProperty(value = "下架备注")
    @NotNull(message = "下架备注不能为空")
    private String remark;
}
