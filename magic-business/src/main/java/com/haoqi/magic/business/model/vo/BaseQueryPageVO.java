package com.haoqi.magic.business.model.vo;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by yanhao on 2019/12/11.15:14
 */
@Getter
@Setter
public class BaseQueryPageVO extends Page {

    @ApiModelProperty(value = "模糊搜索字段")
    private String keyword;
}
