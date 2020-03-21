package com.haoqi.magic.gateway.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author twg
 * @since 2019/1/8
 */
@Data
public class RoleDTO implements Serializable {
    private Long id;
    private String roleName;
    private String roleCode;
    private String roleDesc;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;
}
