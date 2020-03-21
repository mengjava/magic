package com.haoqi.magic.business.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author twg
 * @since 2019/11/28
 */
@Data
public class UserDealerRegisterVO implements Serializable {
    /**
     * 验证码
     */
    @NotBlank(message = "验证码不能为空")
    @ApiModelProperty(value = "验证码",required = true)
    private String messageCode;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    private String username;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空")
    private String tel;

    /**
     * 城市
     */
    @NotNull(message = "城市不能为空")
    private Long sysAreaId;


    /**
     * 推荐人
     */
    private String introducer;

    /**
     * 是否成为车商
     */
    private Boolean toCarDealer;

    private CarDealerRegisterVO dealer;
}
