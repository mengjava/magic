package com.haoqi.magic.system.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户信息VO
 *
 * @author huming
 * @date 2019/1/14 10:14
 */
@Data
public class UserVO {
    private static final long serialVersionUID = 6151491293299693607L;

    private Long id;
    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "登录账号")
    private String loginName;
    @ApiModelProperty(value = "真实姓名")
    private String username;
    @ApiModelProperty(value = "用户类型（1:超级管理员，2：普通管理，3：检测员，4：商家，5：普通用户，6：复检员）")
    @NotNull(message = "用户类型不能为空")
    private Integer type;
    private String salt;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 12, message = "密码长度必须在6-12位之间")
    private String password;
    @ApiModelProperty(value = "用户头像")
    private String headImageUrl;
    @ApiModelProperty(value = "用户手机号")
    private String tel;
    @ApiModelProperty(value = "状态 0-正常，1-失效，2-过期，3-锁定，4-密码过期")
    @NotNull(message = "用户状态不能为空")
    private Integer isEnabled;

    /**
     * 角色id集合
     */
    private String[] roleIds;

    @ApiModelProperty(value = "用户区域id")
    private Long areaId;


    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 推荐人
     */
    private String introducer;
}
