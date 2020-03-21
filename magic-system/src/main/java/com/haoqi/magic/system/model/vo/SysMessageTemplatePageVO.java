package com.haoqi.magic.system.model.vo;


import com.haoqi.rigger.core.page.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author huming
 * @date 2019/4/26 10:17
 * @see
 * @since JDK 1.8
 */
@Data
public class SysMessageTemplatePageVO extends Page {

    private static final long serialVersionUID = 3328684769416464972L;

    @ApiModelProperty(value = "消息唯一标识")
    private String keyword;
}
