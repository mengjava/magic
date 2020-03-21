package com.haoqi.magic.business.model.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by yanhao on 2019/7/15.
 */
@Getter
@Setter
public class PullCarVO {
    @NotNull(message = "车辆id不能为空")
    private Long id;

    private String remark;
}
