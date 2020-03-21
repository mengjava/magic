package com.haoqi.magic.system.model.dto;

import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户分页信息
 * @author huming
 * @date 2019/1/14 11:05
 */
@Data
public class RolePageVO extends Page {

    @ApiModelProperty(value = "关键字")
    private String keyword;
}
