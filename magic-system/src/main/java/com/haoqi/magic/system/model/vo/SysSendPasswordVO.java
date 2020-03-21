package com.haoqi.magic.system.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Function: <br/>
 * Date:     2019/4/17 10:18 <br/>
 * @author mengyao
 * @see
 * @since JDK 1.8
 */
@Data
public class SysSendPasswordVO implements Serializable {
    private static final long serialVersionUID = -3762945943510159651L;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码",required = true)
    @NotBlank(message = "手机号码不能为空")
    private String tel;

    /**
     * 产品前缀
     */
    @ApiModelProperty(value = "产品前缀",required = true)
    @NotBlank(message = "产品前缀不能为空")
    private String prefix;


    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

}
