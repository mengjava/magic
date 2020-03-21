package com.haoqi.magic.business.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <p>
 * 车辆检测项
 * </p>
 *
 * @author yanhao
 * @since 2019-05-05
 */
@Getter
@Setter
public class CsCarCheckDTO implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 检测项名称
     */
    @ApiModelProperty(value = "检测项名称")
    @NotEmpty(message = "检测项名称不能为空")
    private String name;
    /**
     * 检测项code(type_pinyin)
     */
    private String code;
    /**
     * 父类检测项id
     */
    @ApiModelProperty(value = "父类检测项id")
    @NotNull(message = "父类检测项id不能为空")
    private Long parentId;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer orderNo;
    /**
     * 1事故检测，2外观检测，3检测信息
     */
    @ApiModelProperty(value = "1事故检测，2外观检测，3检测信息")
    @NotNull(message = "类型不能为空")
    private Integer type;

}
