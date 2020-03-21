package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Date:     2019/4/17 10:18 <br/>
 * @author mengyao
 * @see
 * @since JDK 1.8
 */
@Data
public class SysSendSmsVO implements Serializable {
    private static final long serialVersionUID = -5994641878614567186L;
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

	@ApiModelProperty(value = "密码")
	private String password;
}
