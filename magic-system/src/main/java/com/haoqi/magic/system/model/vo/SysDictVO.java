package com.haoqi.magic.system.model.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by yanhao on 2018/7/2 13:53.
 */
@Data
public class SysDictVO implements Serializable {

    private Long id;

    @NotBlank(message = "数据字典关键字不能为空")
    private String keyworld;
    @NotBlank(message = "数据字典描述不能为空")
    private String valueDesc;
    @NotNull(message = "数据字典父级id不能为空")
    private Integer parentId;

    private Integer classLevel;
    @NotNull(message = "排序不能为空")
    private Integer classOrder;
    @NotBlank(message = "数据字典类别不能为空")
    private String classType;

    private Integer isDeleted;
}
