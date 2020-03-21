package com.haoqi.magic.system.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * ClassName:com.haoyun.mirage.user.model.vo <br/>
 * Function: <br/>
 * Date:     2018/8/1 15:19 <br/>
 *
 * @author huming
 * @see
 * @since JDK 1.8
 */
@Data
public class UserPassVO implements Serializable {
    private static final long serialVersionUID = 6151491293299693607L;

    @ApiModelProperty(value = "原始密码")
    @NotBlank(message = "原始密码不能为空")
    private String oldPass;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String pass;

}
