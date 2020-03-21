package com.haoqi.magic.system.model.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author twg
 * @since 2019/5/5
 */
@Data
public class RoleMenuVO implements Serializable {
    @NotNull(message = "角色id不能为空")
    Long roleId;
    Long[] menuIds;

}
