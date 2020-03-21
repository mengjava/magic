package com.haoqi.magic.system.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * @author twg
 * @since 2019/1/8
 */
@Data
public class RoleDTO implements Serializable {
    private Long id;
    @NotBlank(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称",required = true)
    private String roleName;
    private String roleCode;
    private String roleDesc;
    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;
}
